package com.wms.mini.domain;

import lombok.Data;

@Data
public class ShipDtl {
    private String shipNo;
    private String itemCd;
    private Integer shipQty;

    // 조회 시 화면 표시용 (JOIN 결과)
    private String itemNm;
}