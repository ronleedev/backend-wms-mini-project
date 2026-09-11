package com.wms.mini.controller;

import com.wms.mini.domain.InspectRslt;
import com.wms.mini.service.InspectRsltService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pick-lists/{pickListNo}/inspection")
@RequiredArgsConstructor
public class InspectRsltController {

    private final InspectRsltService inspectRsltService;

    @GetMapping
    public List<InspectRslt> list(@PathVariable String pickListNo) {
        return inspectRsltService.getInspectList(pickListNo);
    }

    @PostMapping("/confirm")
    public List<InspectRslt> confirm(@PathVariable String pickListNo, @RequestBody List<InspectRslt> lines) {
        return inspectRsltService.confirmInspection(pickListNo, lines);
    }
}