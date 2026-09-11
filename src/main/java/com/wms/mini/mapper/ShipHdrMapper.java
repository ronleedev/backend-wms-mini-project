package com.wms.mini.mapper;

import com.wms.mini.domain.ShipHdr;

import java.util.List;

public interface ShipHdrMapper {
    List<ShipHdr> selectShipHdrList();
    ShipHdr selectShipHdr(String shipNo);
    int insertShipHdr(ShipHdr shipHdr);
    int updateShipHdr(ShipHdr shipHdr);
    int updateShipHdrStatus(ShipHdr shipHdr);
    int deleteShipHdr(String shipNo);
}