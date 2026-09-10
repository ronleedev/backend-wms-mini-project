package com.wms.mini.domain;

import lombok.Data;

@Data
public class PlanDtl {
    private String planNo;
    private String itemCd;
    private Integer planQty;

    // 조회 시 화면 표시용 (JOIN 결과)
    private String itemNm;
}