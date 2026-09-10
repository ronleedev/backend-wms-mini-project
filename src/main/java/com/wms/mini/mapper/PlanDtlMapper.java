package com.wms.mini.mapper;

import com.wms.mini.domain.PlanDtl;

import java.util.List;

public interface PlanDtlMapper {
    List<PlanDtl> selectPlanDtlList(String planNo);
    int insertPlanDtl(PlanDtl planDtl);
    int deletePlanDtlByPlanNo(String planNo);
}