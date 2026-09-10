package com.wms.mini.mapper;

import com.wms.mini.domain.StockAdjHist;

import java.util.List;

public interface StockAdjHistMapper {
    List<StockAdjHist> selectStockAdjHistList();
    int insertStockAdjHist(StockAdjHist stockAdjHist);
}