package com.wms.mini.controller;

import com.wms.mini.domain.PickRslt;
import com.wms.mini.service.PickRsltService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pick-lists/{pickListNo}/picking")
@RequiredArgsConstructor
public class PickRsltController {

    private final PickRsltService pickRsltService;

    @GetMapping
    public List<PickRslt> list(@PathVariable String pickListNo) {
        return pickRsltService.getPickRsltList(pickListNo);
    }

    @PostMapping("/confirm")
    public List<PickRslt> confirm(@PathVariable String pickListNo, @RequestBody List<PickRslt> lines) {
        return pickRsltService.confirmPicking(pickListNo, lines);
    }
}