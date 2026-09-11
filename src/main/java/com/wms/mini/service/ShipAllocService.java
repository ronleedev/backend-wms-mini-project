package com.wms.mini.service;

import com.wms.mini.domain.ShipAlloc;
import com.wms.mini.domain.ShipDtl;
import com.wms.mini.domain.ShipHdr;
import com.wms.mini.domain.Stock;
import com.wms.mini.mapper.ShipAllocMapper;
import com.wms.mini.mapper.ShipDtlMapper;
import com.wms.mini.mapper.ShipHdrMapper;
import com.wms.mini.mapper.StockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShipAllocService {

    private final ShipHdrMapper shipHdrMapper;
    private final ShipDtlMapper shipDtlMapper;
    private final ShipAllocMapper shipAllocMapper;
    private final StockMapper stockMapper;

    public List<ShipAlloc> getAllocList(String shipNo) {
        return shipAllocMapper.selectShipAllocListByShipNo(shipNo);
    }

    /**
     * 자동 할당 핵심 로직 (MVP 단순화 버전)
     * - 출고지시 라인마다, 같은 창고+품목의 재고를 로케이션 코드 순으로 훑으면서 필요한 수량만큼 할당
     * - 한 라인이 여러 로케이션으로 나뉘어 할당될 수 있음 (SHIP_ALLOC 여러 건 생성)
     * - 전량 할당이 불가능하면 예외 발생 (재고 부족)
     * - 이 단계에서는 STOCK 수량을 아직 차감하지 않음 (실제 차감은 피킹 단계에서)
     */
    @Transactional
    public List<ShipAlloc> allocate(String shipNo) {
        ShipHdr shipHdr = shipHdrMapper.selectShipHdr(shipNo);
        List<ShipDtl> details = shipDtlMapper.selectShipDtlList(shipNo);
        List<Stock> allStocks = stockMapper.selectStockList();

        String base = "AL" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        int counter = 0;
        List<ShipAlloc> result = new ArrayList<>();

        for (ShipDtl detail : details) {
            int remaining = detail.getShipQty();

            List<Stock> candidates = allStocks.stream()
                    .filter(s -> s.getWhCd().equals(shipHdr.getWhCd())
                            && s.getItemCd().equals(detail.getItemCd())
                            && s.getQty() > 0)
                    .sorted(Comparator.comparing(Stock::getLocCd))
                    .collect(Collectors.toList());

            for (Stock stock : candidates) {
                if (remaining <= 0) break;

                int allocQty = Math.min(remaining, stock.getQty());
                ShipAlloc alloc = new ShipAlloc();
                alloc.setAllocNo(base + "-" + (counter++));
                alloc.setShipNo(shipNo);
                alloc.setItemCd(detail.getItemCd());
                alloc.setLocCd(stock.getLocCd());
                alloc.setAllocQty(allocQty);
                shipAllocMapper.insertShipAlloc(alloc);
                result.add(alloc);

                remaining -= allocQty;
            }

            if (remaining > 0) {
                throw new IllegalStateException(
                        detail.getItemCd() + " 품목의 재고가 부족하여 전량 할당할 수 없습니다. (부족수량: " + remaining + ")");
            }
        }

        ShipHdr updateHdr = new ShipHdr();
        updateHdr.setShipNo(shipNo);
        updateHdr.setStatus("할당완료");
        shipHdrMapper.updateShipHdrStatus(updateHdr);

        return result;
    }
}