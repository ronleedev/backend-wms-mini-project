package com.wms.mini.controller;

import com.wms.mini.domain.ShipHdr;
import com.wms.mini.service.ShipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ships")
@RequiredArgsConstructor
public class ShipController {

    private final ShipService shipService;

    @GetMapping
    public List<ShipHdr> list() {
        return shipService.getShipList();
    }

    @GetMapping("/{shipNo}")
    public ShipHdr get(@PathVariable String shipNo) {
        return shipService.getShip(shipNo);
    }

    @PostMapping
    public void create(@RequestBody ShipHdr shipHdr) {
        shipService.createShip(shipHdr);
    }

    @PutMapping("/{shipNo}")
    public void update(@PathVariable String shipNo, @RequestBody ShipHdr shipHdr) {
        shipService.updateShip(shipNo, shipHdr);
    }

    @DeleteMapping("/{shipNo}")
    public void delete(@PathVariable String shipNo) {
        shipService.deleteShip(shipNo);
    }
}
