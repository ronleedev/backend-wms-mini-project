package com.wms.mini.domain;

import lombok.Data;

import java.util.List;

@Data
public class PickListHdr {
    private String pickListNo;
    private String shipNo;
    private String workerNm;
    private String status;

    // 조회 시 화면 표시용 (JOIN 결과)
    private String whNm;
    private String shipperNm;

    // 상세 조회 시에만 채움 (SHIP_ALLOC을 그대로 참조 — 별도 디테일 테이블 없음)
    private List<ShipAlloc> allocations;
}