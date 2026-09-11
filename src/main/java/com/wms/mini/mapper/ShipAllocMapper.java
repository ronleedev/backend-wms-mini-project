package com.wms.mini.mapper;

import com.wms.mini.domain.ShipAlloc;

import java.util.List;

public interface ShipAllocMapper {
    List<ShipAlloc> selectShipAllocListByShipNo(String shipNo);
    int insertShipAlloc(ShipAlloc shipAlloc);
}