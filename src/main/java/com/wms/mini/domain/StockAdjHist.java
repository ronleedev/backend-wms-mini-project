package com.wms.mini.domain;

import lombok.Data;

@Data
public class StockAdjHist {
    private String adjNo;
    private String whCd;
    private String itemCd;
    private String locCd;
    private Integer beforeQty;
    private Integer adjQty;   // 증감값 (+/-)
    private Integer afterQty;
    private String reason;

    // 조회 시 화면 표시용 (JOIN 결과)
    private String itemNm;
    private String whNm;
}
