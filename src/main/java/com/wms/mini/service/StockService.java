package com.wms.mini.service;

import com.wms.mini.domain.Stock;
import com.wms.mini.mapper.StockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockMapper stockMapper;

    public List<Stock> getStockList() {
        return stockMapper.selectStockList();
    }
}