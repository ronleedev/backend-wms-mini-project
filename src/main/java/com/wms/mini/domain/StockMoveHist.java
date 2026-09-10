package com.wms.mini.domain;

import lombok.Data;

@Data
public class StockMoveHist {
    private String moveNo;
    private String whCd;
    private String itemCd;
    private String fromLocCd;
    private String toLocCd;
    private Integer moveQty;

    // 조회 시 화면 표시용 (JOIN 결과)
    private String itemNm;
    private String whNm;
}