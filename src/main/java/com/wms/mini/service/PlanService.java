package com.wms.mini.service;

import com.wms.mini.domain.PlanDtl;
import com.wms.mini.domain.PlanHdr;
import com.wms.mini.mapper.PlanDtlMapper;
import com.wms.mini.mapper.PlanHdrMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanHdrMapper planHdrMapper;
    private final PlanDtlMapper planDtlMapper;

    public List<PlanHdr> getPlanList() {
        return planHdrMapper.selectPlanHdrList();
    }

    public PlanHdr getPlan(String planNo) {
        PlanHdr planHdr = planHdrMapper.selectPlanHdr(planNo);
        if (planHdr != null) {
            planHdr.setDetails(planDtlMapper.selectPlanDtlList(planNo));
        }
        return planHdr;
    }

    @Transactional
    public void createPlan(PlanHdr planHdr) {
        String planNo = "PL" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        planHdr.setPlanNo(planNo);
        planHdr.setStatus("대기");
        planHdrMapper.insertPlanHdr(planHdr);

        for (PlanDtl detail : planHdr.getDetails()) {
            detail.setPlanNo(planNo);
            planDtlMapper.insertPlanDtl(detail);
        }
    }

    @Transactional
    public void updatePlan(String planNo, PlanHdr planHdr) {
        planHdr.setPlanNo(planNo);
        planHdrMapper.updatePlanHdr(planHdr);

        // 디테일은 전체 삭제 후 재등록 (헤더-디테일 화면에서 자주 쓰는 단순한 패턴)
        planDtlMapper.deletePlanDtlByPlanNo(planNo);
        for (PlanDtl detail : planHdr.getDetails()) {
            detail.setPlanNo(planNo);
            planDtlMapper.insertPlanDtl(detail);
        }
    }

    public void deletePlan(String planNo) {
        planHdrMapper.deletePlanHdr(planNo); // PLAN_DTL은 ON DELETE CASCADE로 함께 삭제됨
    }
}