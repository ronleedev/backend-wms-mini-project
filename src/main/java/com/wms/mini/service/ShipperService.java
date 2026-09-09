package com.wms.mini.service;

import com.wms.mini.domain.Shipper;
import com.wms.mini.mapper.ShipperMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipperService {

    private final ShipperMapper shipperMapper;

    public List<Shipper> getShipperList() {
        return shipperMapper.selectShipperList();
    }

    public Shipper getShipper(String shipperCd) {
        return shipperMapper.selectShipper(shipperCd);
    }

    public void createShipper(Shipper shipper) {
        shipper.setUseYn("Y");
        shipperMapper.insertShipper(shipper);
    }

    public void updateShipper(Shipper shipper) {
        shipperMapper.updateShipper(shipper);
    }

    public void deleteShipper(String shipperCd) {
        shipperMapper.deleteShipper(shipperCd);
    }
}
