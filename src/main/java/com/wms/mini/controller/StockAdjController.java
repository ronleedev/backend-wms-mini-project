package com.wms.mini.controller;

import com.wms.mini.domain.StockAdjHist;
import com.wms.mini.service.StockAdjService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-adjustments")
@RequiredArgsConstructor
public class StockAdjController {

    private final StockAdjService stockAdjService;

    @GetMapping
    public List<StockAdjHist> list() {
        return stockAdjService.getAdjHistList();
    }

    @PostMapping
    public void adjust(@RequestBody StockAdjHist req) {
        stockAdjService.adjustStock(req);
    }
}