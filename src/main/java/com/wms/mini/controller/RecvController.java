package com.wms.mini.controller;

import com.wms.mini.domain.RecvRslt;
import com.wms.mini.service.RecvService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans/{planNo}/receipt")
@RequiredArgsConstructor
public class RecvController {

    private final RecvService recvService;

    @GetMapping
    public List<RecvRslt> list(@PathVariable String planNo) {
        return recvService.getRecvRsltList(planNo);
    }

    @PostMapping("/confirm")
    public void confirm(@PathVariable String planNo, @RequestBody List<RecvRslt> lines) {
        recvService.confirmReceipt(planNo, lines);
    }
}