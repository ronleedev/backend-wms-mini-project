package com.wms.mini.service;

import com.wms.mini.domain.ShipDtl;
import com.wms.mini.domain.ShipHdr;
import com.wms.mini.mapper.ShipDtlMapper;
import com.wms.mini.mapper.ShipHdrMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipService {

    private final ShipHdrMapper shipHdrMapper;
    private final ShipDtlMapper shipDtlMapper;

    public List<ShipHdr> getShipList() {
        return shipHdrMapper.selectShipHdrList();
    }

    public ShipHdr getShip(String shipNo) {
        ShipHdr shipHdr = shipHdrMapper.selectShipHdr(shipNo);
        if (shipHdr != null) {
            shipHdr.setDetails(shipDtlMapper.selectShipDtlList(shipNo));
        }
        return shipHdr;
    }

    @Transactional
    public void createShip(ShipHdr shipHdr) {
        String shipNo = "SH" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        shipHdr.setShipNo(shipNo);
        shipHdr.setStatus("대기");
        shipHdrMapper.insertShipHdr(shipHdr);

        for (ShipDtl detail : shipHdr.getDetails()) {
            detail.setShipNo(shipNo);
            shipDtlMapper.insertShipDtl(detail);
        }
    }

    @Transactional
    public void updateShip(String shipNo, ShipHdr shipHdr) {
        shipHdr.setShipNo(shipNo);
        shipHdrMapper.updateShipHdr(shipHdr);

        shipDtlMapper.deleteShipDtlByShipNo(shipNo);
        for (ShipDtl detail : shipHdr.getDetails()) {
            detail.setShipNo(shipNo);
            shipDtlMapper.insertShipDtl(detail);
        }
    }

    public void deleteShip(String shipNo) {
        shipHdrMapper.deleteShipHdr(shipNo); // SHIP_DTL은 ON DELETE CASCADE로 함께 삭제됨
    }
}