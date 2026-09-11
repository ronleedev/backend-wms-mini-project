package com.wms.mini.domain;

import lombok.Data;

@Data
public class PickRslt {
    private String pickRsltNo;
    private String pickListNo;
    private String allocNo;
    private Integer pickQty;

    // 조회 시 화면 표시용 (JOIN 결과)
    private String itemCd;
    private String itemNm;
    private String locCd;
    private Integer allocQty;
}