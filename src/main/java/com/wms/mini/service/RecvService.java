package com.wms.mini.service;

import com.wms.mini.domain.PlanHdr;
import com.wms.mini.domain.RecvRslt;
import com.wms.mini.domain.Stock;
import com.wms.mini.mapper.PlanHdrMapper;
import com.wms.mini.mapper.RecvRsltMapper;
import com.wms.mini.mapper.StockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecvService {

    private final RecvRsltMapper recvRsltMapper;
    private final StockMapper stockMapper;
    private final PlanHdrMapper planHdrMapper;

    public List<RecvRslt> getRecvRsltList(String planNo) {
        return recvRsltMapper.selectRecvRsltListByPlanNo(planNo);
    }

    /**
     * 입고확정 처리 핵심 로직
     * 1) 라인마다 입고실적(RECV_RSLT) 기록
     * 2) 검수결과가 '정상'인 라인만 재고(STOCK)에 반영 (불량은 재고 미반영)
     * 3) 처리 완료 후 입고예정 상태를 '완료'로 변경
     */
    @Transactional
    public void confirmReceipt(String planNo, List<RecvRslt> lines) {
        String base = "RC" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));

        for (int i = 0; i < lines.size(); i++) {
            RecvRslt line = lines.get(i);
            line.setPlanNo(planNo);
            line.setRecvNo(base + "-" + i);
            recvRsltMapper.insertRecvRslt(line);

            if ("정상".equals(line.getInspectResult())) {
                Stock stock = new Stock();
                stock.setWhCd(line.getWhCd());
                stock.setItemCd(line.getItemCd());
                stock.setLocCd(line.getLocCd());
                stock.setQty(line.getActualQty());
                stockMapper.upsertStock(stock);
            }
        }

        PlanHdr planHdr = new PlanHdr();
        planHdr.setPlanNo(planNo);
        planHdr.setStatus("완료");
        planHdrMapper.updatePlanHdrStatus(planHdr);
    }
}