package com.wms.mini.domain;

import lombok.Data;

@Data
public class InspectRslt {
    private String inspectNo;
    private String pickRsltNo;
    private Integer inspectQty;
    private String inspectResult; // 정상 / 불량

    // 조회 시 화면 표시용 (JOIN 결과)
    private String itemCd;
    private String itemNm;
    private String locCd;
    private Integer pickQty;
}