package com.wms.mini.domain;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ShipHdr {
    private String shipNo;
    private String whCd;
    private String shipperCd;
    private LocalDate shipDate;
    private String status;

    // 조회 시 화면 표시용 (JOIN 결과)
    private String whNm;
    private String shipperNm;

    // 등록/수정 요청 시 헤더+디테일을 함께 전달받기 위한 필드
    private List<ShipDtl> details;
}