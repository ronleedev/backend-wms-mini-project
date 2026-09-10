package com.wms.mini.mapper;

import com.wms.mini.domain.PlanHdr;

import java.util.List;

public interface PlanHdrMapper {
    List<PlanHdr> selectPlanHdrList();
    PlanHdr selectPlanHdr(String planNo);
    int insertPlanHdr(PlanHdr planHdr);
    int updatePlanHdr(PlanHdr planHdr);
    int deletePlanHdr(String planNo);
}