package com.wms.mini.service;

import com.wms.mini.domain.PickListHdr;
import com.wms.mini.domain.PickRslt;
import com.wms.mini.domain.ShipAlloc;
import com.wms.mini.domain.ShipHdr;
import com.wms.mini.domain.Stock;
import com.wms.mini.mapper.PickListHdrMapper;
import com.wms.mini.mapper.PickRsltMapper;
import com.wms.mini.mapper.ShipAllocMapper;
import com.wms.mini.mapper.ShipHdrMapper;
import com.wms.mini.mapper.StockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PickRsltService {

    private final PickRsltMapper pickRsltMapper;
    private final PickListHdrMapper pickListHdrMapper;
    private final ShipHdrMapper shipHdrMapper;
    private final ShipAllocMapper shipAllocMapper;
    private final StockMapper stockMapper;

    public List<PickRslt> getPickRsltList(String pickListNo) {
        return pickRsltMapper.selectPickRsltListByPickListNo(pickListNo);
    }

    /**
     * 피킹확정 핵심 로직 (출고 모듈의 핵심 트랜잭션 — 실물이 빠져나가는 시점에 재고 차감)
     * 1) 할당 정보(로케이션, 품목)를 기준으로 실제 피킹수량만큼 STOCK 차감
     * 2) 차감 전 재고가 부족하면 예외 (할당 이후 다른 처리로 재고가 줄었을 가능성 방지)
     * 3) 피킹실적 기록
     * 4) 피킹리스트 상태 '완료', 출고지시 상태 '피킹완료'로 변경
     */
    @Transactional
    public List<PickRslt> confirmPicking(String pickListNo, List<PickRslt> lines) {
        PickListHdr pickListHdr = pickListHdrMapper.selectPickListHdr(pickListNo);
        ShipHdr shipHdr = shipHdrMapper.selectShipHdr(pickListHdr.getShipNo());

        Map<String, ShipAlloc> allocMap = shipAllocMapper.selectShipAllocListByShipNo(shipHdr.getShipNo())
                .stream()
                .collect(Collectors.toMap(ShipAlloc::getAllocNo, a -> a));

        String base = "PK" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        int counter = 0;

        for (PickRslt line : lines) {
            ShipAlloc alloc = allocMap.get(line.getAllocNo());

            Stock currentStock = stockMapper.selectStock(shipHdr.getWhCd(), alloc.getItemCd(), alloc.getLocCd());
            if (currentStock == null || currentStock.getQty() < line.getPickQty()) {
                throw new IllegalStateException(
                        alloc.getItemCd() + " (" + alloc.getLocCd() + ") 로케이션의 재고가 부족하여 피킹할 수 없습니다.");
            }

            line.setPickRsltNo(base + "-" + (counter++));
            line.setPickListNo(pickListNo);
            pickRsltMapper.insertPickRslt(line);

            Stock decreaseParam = new Stock();
            decreaseParam.setWhCd(shipHdr.getWhCd());
            decreaseParam.setItemCd(alloc.getItemCd());
            decreaseParam.setLocCd(alloc.getLocCd());
            decreaseParam.setQty(line.getPickQty());
            stockMapper.decreaseStock(decreaseParam);
        }

        PickListHdr updatePickList = new PickListHdr();
        updatePickList.setPickListNo(pickListNo);
        updatePickList.setStatus("완료");
        pickListHdrMapper.updatePickListHdrStatus(updatePickList);

        ShipHdr updateShip = new ShipHdr();
        updateShip.setShipNo(shipHdr.getShipNo());
        updateShip.setStatus("피킹완료");
        shipHdrMapper.updateShipHdrStatus(updateShip);

        return getPickRsltList(pickListNo);
    }
}