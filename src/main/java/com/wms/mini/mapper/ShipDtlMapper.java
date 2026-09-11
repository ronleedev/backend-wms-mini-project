package com.wms.mini.mapper;

import com.wms.mini.domain.ShipDtl;

import java.util.List;

public interface ShipDtlMapper {
    List<ShipDtl> selectShipDtlList(String shipNo);
    int insertShipDtl(ShipDtl shipDtl);
    int deleteShipDtlByShipNo(String shipNo);
}