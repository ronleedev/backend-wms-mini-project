package com.wms.mini.service;

import com.wms.mini.domain.Stock;
import com.wms.mini.domain.StockMoveHist;
import com.wms.mini.mapper.StockMapper;
import com.wms.mini.mapper.StockMoveHistMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockMoveService {

    private final StockMapper stockMapper;
    private final StockMoveHistMapper stockMoveHistMapper;

    public List<StockMoveHist> getMoveHistList() {
        return stockMoveHistMapper.selectStockMoveHistList();
    }

    /**
     * 재고이동 핵심 로직
     * 1) 출발 로케이션 재고가 이동수량 이상인지 확인
     * 2) 출발 로케이션 수량 차감
     * 3) 도착 로케이션 수량 반영 (upsert)
     * 4) 이동 이력 기록
     */
    @Transactional
    public void moveStock(StockMoveHist req) {
        Stock source = stockMapper.selectStock(req.getWhCd(), req.getItemCd(), req.getFromLocCd());
        if (source == null || source.getQty() < req.getMoveQty()) {
            throw new IllegalStateException("출발 로케이션의 재고 수량이 부족합니다.");
        }

        Stock decreaseParam = new Stock();
        decreaseParam.setWhCd(req.getWhCd());
        decreaseParam.setItemCd(req.getItemCd());
        decreaseParam.setLocCd(req.getFromLocCd());
        decreaseParam.setQty(req.getMoveQty());
        stockMapper.decreaseStock(decreaseParam);

        Stock increaseParam = new Stock();
        increaseParam.setWhCd(req.getWhCd());
        increaseParam.setItemCd(req.getItemCd());
        increaseParam.setLocCd(req.getToLocCd());
        increaseParam.setQty(req.getMoveQty());
        stockMapper.upsertStock(increaseParam);

        req.setMoveNo("MV" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        stockMoveHistMapper.insertStockMoveHist(req);
    }
}