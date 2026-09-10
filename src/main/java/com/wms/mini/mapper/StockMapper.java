package com.wms.mini.mapper;

import com.wms.mini.domain.Stock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockMapper {
    List<Stock> selectStockList();
    Stock selectStock(@Param("whCd") String whCd, @Param("itemCd") String itemCd, @Param("locCd") String locCd);
    int upsertStock(Stock stock);
    int decreaseStock(Stock stock);
}