package com.wms.mini.domain;

import lombok.Data;

@Data
public class RecvRslt {
    private String recvNo;
    private String planNo;
    private String itemCd;
    private Integer actualQty;
    private String inspectResult; // 정상 / 불량

    // 입고확정 요청 시에만 사용 (STOCK 갱신용, RECV_RSLT 테이블에는 저장 안 함)
    private String whCd;
    private String locCd;

    // 조회 시 화면 표시용 (JOIN 결과)
    private String itemNm;
}