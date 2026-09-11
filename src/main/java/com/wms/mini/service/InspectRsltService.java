package com.wms.mini.service;

import com.wms.mini.domain.InspectRslt;
import com.wms.mini.domain.PickListHdr;
import com.wms.mini.domain.PickRslt;
import com.wms.mini.domain.ShipHdr;
import com.wms.mini.domain.Stock;
import com.wms.mini.mapper.InspectRsltMapper;
import com.wms.mini.mapper.PickListHdrMapper;
import com.wms.mini.mapper.PickRsltMapper;
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
public class InspectRsltService {

    private final InspectRsltMapper inspectRsltMapper;
    private final PickListHdrMapper pickListHdrMapper;
    private final PickRsltMapper pickRsltMapper;
    private final ShipHdrMapper shipHdrMapper;
    private final StockMapper stockMapper;

    public List<InspectRslt> getInspectList(String pickListNo) {
        return inspectRsltMapper.selectInspectRsltListByPickListNo(pickListNo);
    }

    /**
     * 검품확정 핵심 로직 — 입고 검수와 반대 방향
     * - 정상: 그대로 둠 (이미 피킹 시점에 STOCK 차감 완료, 추가 반영 없음)
     * - 불량: 출고 불가 판정이므로 STOCK에 되돌림 (upsert)
     * 처리 후 출고지시 상태를 '검품완료'로 변경
     */
    @Transactional
    public List<InspectRslt> confirmInspection(String pickListNo, List<InspectRslt> lines) {
        PickListHdr pickListHdr = pickListHdrMapper.selectPickListHdr(pickListNo);
        ShipHdr shipHdr = shipHdrMapper.selectShipHdr(pickListHdr.getShipNo());

        Map<String, PickRslt> pickMap = pickRsltMapper.selectPickRsltListByPickListNo(pickListNo)
                .stream()
                .collect(Collectors.toMap(PickRslt::getPickRsltNo, p -> p));

        String base = "IN" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        int counter = 0;

        for (InspectRslt line : lines) {
            PickRslt pick = pickMap.get(line.getPickRsltNo());

            line.setInspectNo(base + "-" + (counter++));
            inspectRsltMapper.insertInspectRslt(line);

            if ("불량".equals(line.getInspectResult())) {
                Stock returnStock = new Stock();
                returnStock.setWhCd(shipHdr.getWhCd());
                returnStock.setItemCd(pick.getItemCd());
                returnStock.setLocCd(pick.getLocCd());
                returnStock.setQty(line.getInspectQty());
                stockMapper.upsertStock(returnStock);
            }
        }

        ShipHdr updateShip = new ShipHdr();
        updateShip.setShipNo(shipHdr.getShipNo());
        updateShip.setStatus("검품완료");
        shipHdrMapper.updateShipHdrStatus(updateShip);

        return getInspectList(pickListNo);
    }
}