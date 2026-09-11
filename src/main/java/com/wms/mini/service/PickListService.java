package com.wms.mini.service;

import com.wms.mini.domain.PickListHdr;
import com.wms.mini.domain.ShipHdr;
import com.wms.mini.mapper.PickListHdrMapper;
import com.wms.mini.mapper.ShipAllocMapper;
import com.wms.mini.mapper.ShipHdrMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PickListService {

    private final PickListHdrMapper pickListHdrMapper;
    private final ShipHdrMapper shipHdrMapper;
    private final ShipAllocMapper shipAllocMapper;

    public List<PickListHdr> getPickListList() {
        return pickListHdrMapper.selectPickListHdrList();
    }

    public PickListHdr getPickList(String pickListNo) {
        PickListHdr hdr = pickListHdrMapper.selectPickListHdr(pickListNo);
        if (hdr != null) {
            hdr.setAllocations(shipAllocMapper.selectShipAllocListByShipNo(hdr.getShipNo()));
        }
        return hdr;
    }

    /**
     * 피킹리스트 생성 — 출고지시가 '할당완료' 상태여야만 생성 가능
     * 별도 디테일 테이블 없이, 조회 시 해당 출고지시의 SHIP_ALLOC을 그대로 참조
     */
    @Transactional
    public PickListHdr createPickList(String shipNo, String workerNm) {
        ShipHdr shipHdr = shipHdrMapper.selectShipHdr(shipNo);
        if (shipHdr == null || !"할당완료".equals(shipHdr.getStatus())) {
            throw new IllegalStateException("할당완료 상태의 출고지시만 피킹리스트를 생성할 수 있습니다.");
        }

        String pickListNo = "PL" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));

        PickListHdr hdr = new PickListHdr();
        hdr.setPickListNo(pickListNo);
        hdr.setShipNo(shipNo);
        hdr.setWorkerNm(workerNm);
        hdr.setStatus("대기");
        pickListHdrMapper.insertPickListHdr(hdr);

        return getPickList(pickListNo);
    }
}