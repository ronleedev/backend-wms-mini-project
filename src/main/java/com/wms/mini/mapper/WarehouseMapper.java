package com.wms.mini.mapper;

import com.wms.mini.domain.Warehouse;

import java.util.List;

public interface WarehouseMapper {
    List<Warehouse> selectWarehouseList();
    Warehouse selectWarehouse(String whCd);
    int insertWarehouse(Warehouse warehouse);
    int updateWarehouse(Warehouse warehouse);
    int deleteWarehouse(String whCd);
}
