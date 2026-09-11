package com.wms.mini.controller;

import com.wms.mini.domain.PickListHdr;
import com.wms.mini.service.PickListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pick-lists")
@RequiredArgsConstructor
public class PickListController {

    private final PickListService pickListService;

    @GetMapping
    public List<PickListHdr> list() {
        return pickListService.getPickListList();
    }

    @GetMapping("/{pickListNo}")
    public PickListHdr get(@PathVariable String pickListNo) {
        return pickListService.getPickList(pickListNo);
    }

    @PostMapping
    public PickListHdr create(@RequestBody PickListHdr req) {
        return pickListService.createPickList(req.getShipNo(), req.getWorkerNm());
    }
}