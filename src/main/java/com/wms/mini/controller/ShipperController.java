package com.wms.mini.controller;

import com.wms.mini.domain.Shipper;
import com.wms.mini.service.ShipperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shippers")
@RequiredArgsConstructor
public class ShipperController {

    private final ShipperService shipperService;

    @GetMapping
    public List<Shipper> list() {
        return shipperService.getShipperList();
    }

    @GetMapping("/{shipperCd}")
    public Shipper get(@PathVariable String shipperCd) {
        return shipperService.getShipper(shipperCd);
    }

    @PostMapping
    public void create(@RequestBody Shipper shipper) {
        shipperService.createShipper(shipper);
    }

    @PutMapping("/{shipperCd}")
    public void update(@PathVariable String shipperCd, @RequestBody Shipper shipper) {
        shipper.setShipperCd(shipperCd);
        shipperService.updateShipper(shipper);
    }

    @DeleteMapping("/{shipperCd}")
    public void delete(@PathVariable String shipperCd) {
        shipperService.deleteShipper(shipperCd);
    }
}
