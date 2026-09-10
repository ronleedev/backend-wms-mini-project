package com.wms.mini.controller;

import com.wms.mini.domain.PlanHdr;
import com.wms.mini.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @GetMapping
    public List<PlanHdr> list() {
        return planService.getPlanList();
    }

    @GetMapping("/{planNo}")
    public PlanHdr get(@PathVariable String planNo) {
        return planService.getPlan(planNo);
    }

    @PostMapping
    public void create(@RequestBody PlanHdr planHdr) {
        planService.createPlan(planHdr);
    }

    @PutMapping("/{planNo}")
    public void update(@PathVariable String planNo, @RequestBody PlanHdr planHdr) {
        planService.updatePlan(planNo, planHdr);
    }

    @DeleteMapping("/{planNo}")
    public void delete(@PathVariable String planNo) {
        planService.deletePlan(planNo);
    }
}