package com.wms.mini.service;

import com.wms.mini.domain.Warehouse;
import com.wms.mini.mapper.WarehouseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseMapper warehouseMapper;

    public List<Warehouse> getWarehouseList() {
        return warehouseMapper.selectWarehouseList();
    }

    public Warehouse getWarehouse(String whCd) {
        return warehouseMapper.selectWarehouse(whCd);
    }

    public void createWarehouse(Warehouse warehouse) {
        warehouse.setUseYn("Y");
        warehouseMapper.insertWarehouse(warehouse);
    }

    public void updateWarehouse(Warehouse warehouse) {
        warehouseMapper.updateWarehouse(warehouse);
    }

    public void deleteWarehouse(String whCd) {
        warehouseMapper.deleteWarehouse(whCd);
    }
}
