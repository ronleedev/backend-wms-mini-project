package com.wms.mini.controller;

import com.wms.mini.domain.StockMoveHist;
import com.wms.mini.service.StockMoveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-moves")
@RequiredArgsConstructor
public class StockMoveController {

    private final StockMoveService stockMoveService;

    @GetMapping
    public List<StockMoveHist> list() {
        return stockMoveService.getMoveHistList();
    }

    @PostMapping
    public void move(@RequestBody StockMoveHist req) {
        stockMoveService.moveStock(req);
    }
}