package com.wms.mini.mapper;

import com.wms.mini.domain.Shipper;

import java.util.List;

public interface ShipperMapper {
    List<Shipper> selectShipperList();
    Shipper selectShipper(String shipperCd);
    int insertShipper(Shipper shipper);
    int updateShipper(Shipper shipper);
    int deleteShipper(String shipperCd);
}
