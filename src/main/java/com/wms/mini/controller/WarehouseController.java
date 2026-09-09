package com.wms.mini.controller;

import com.wms.mini.domain.Warehouse;
import com.wms.mini.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping
    public List<Warehouse> list() {
        return warehouseService.getWarehouseList();
    }

    @GetMapping("/{whCd}")
    public Warehouse get(@PathVariable String whCd) {
        return warehouseService.getWarehouse(whCd);
    }

    @PostMapping
    public void create(@RequestBody Warehouse warehouse) {
        warehouseService.createWarehouse(warehouse);
    }

    @PutMapping("/{whCd}")
    public void update(@PathVariable String whCd, @RequestBody Warehouse warehouse) {
        warehouse.setWhCd(whCd);
        warehouseService.updateWarehouse(warehouse);
    }

    @DeleteMapping("/{whCd}")
    public void delete(@PathVariable String whCd) {
        warehouseService.deleteWarehouse(whCd);
    }
}
