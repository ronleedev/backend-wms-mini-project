package com.wms.mini.controller;

import com.wms.mini.domain.ShipAlloc;
import com.wms.mini.service.ShipAllocService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ships/{shipNo}/allocations")
@RequiredArgsConstructor
public class ShipAllocController {

    private final ShipAllocService shipAllocService;

    @GetMapping
    public List<ShipAlloc> list(@PathVariable String shipNo) {
        return shipAllocService.getAllocList(shipNo);
    }

    @PostMapping
    public List<ShipAlloc> allocate(@PathVariable String shipNo) {
        return shipAllocService.allocate(shipNo);
    }
}