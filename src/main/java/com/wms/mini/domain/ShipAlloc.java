package com.wms.mini.domain;

import lombok.Data;

@Data
public class ShipAlloc {
    private String allocNo;
    private String shipNo;
    private String itemCd;
    private String locCd;
    private Integer allocQty;

    // 조회 시 화면 표시용 (JOIN 결과)
    private String itemNm;
}