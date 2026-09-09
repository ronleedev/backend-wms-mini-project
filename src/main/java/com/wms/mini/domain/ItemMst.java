package com.wms.mini.domain;

import lombok.Data;

@Data
public class ItemMst {
    private String itemCd;
    private String shipperCd;
    private String itemNm;
    private String useYn;

    // 조회 시 화면 표시용 (JOIN 결과)
    private String shipperNm;
}