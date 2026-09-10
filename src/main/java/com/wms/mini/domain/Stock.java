package com.wms.mini.domain;

import lombok.Data;

@Data
public class Stock {
    private String whCd;
    private String itemCd;
    private String locCd;
    private Integer qty;

    // 조회 시 화면 표시용 (JOIN 결과)
    private String itemNm;
    private String whNm;
}