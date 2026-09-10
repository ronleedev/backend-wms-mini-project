package com.wms.mini.service;

import com.wms.mini.domain.Stock;
import com.wms.mini.domain.StockAdjHist;
import com.wms.mini.mapper.StockAdjHistMapper;
import com.wms.mini.mapper.StockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockAdjService {

    private final StockMapper stockMapper;
    private final StockAdjHistMapper stockAdjHistMapper;

    public List<StockAdjHist> getAdjHistList() {
        return stockAdjHistMapper.selectStockAdjHistList();
    }

    /**
     * 재고조정 핵심 로직
     * 1) 조정전 수량 조회 (해당 로케이션에 재고가 없으면 0으로 간주 → 신규 생성 가능)
     * 2) 조정후 수량이 0 미만이면 예외 (마이너스 재고 방지)
     * 3) STOCK에 증감값만큼 반영 (upsert)
     * 4) 조정 이력 기록 (조정전/조정후/사유 모두 남김 — 감사 추적용)
     */
    @Transactional
    public void adjustStock(StockAdjHist req) {
        Stock current = stockMapper.selectStock(req.getWhCd(), req.getItemCd(), req.getLocCd());
        int beforeQty = current != null ? current.getQty() : 0;
        int afterQty = beforeQty + req.getAdjQty();

        if (afterQty < 0) {
            throw new IllegalStateException("조정 후 재고 수량은 0보다 작을 수 없습니다. (현재: " + beforeQty + ")");
        }

        Stock deltaStock = new Stock();
        deltaStock.setWhCd(req.getWhCd());
        deltaStock.setItemCd(req.getItemCd());
        deltaStock.setLocCd(req.getLocCd());
        deltaStock.setQty(req.getAdjQty());
        stockMapper.upsertStock(deltaStock);

        req.setAdjNo("ADJ" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        req.setBeforeQty(beforeQty);
        req.setAfterQty(afterQty);
        stockAdjHistMapper.insertStockAdjHist(req);
    }
}