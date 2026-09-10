package com.wms.mini.mapper;

import com.wms.mini.domain.Stock;

import java.util.List;

public interface StockMapper {
    List<Stock> selectStockList();
    int upsertStock(Stock stock);
}