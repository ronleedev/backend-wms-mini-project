package com.wms.mini.mapper;

import com.wms.mini.domain.StockMoveHist;

import java.util.List;

public interface StockMoveHistMapper {
    List<StockMoveHist> selectStockMoveHistList();
    int insertStockMoveHist(StockMoveHist stockMoveHist);
}