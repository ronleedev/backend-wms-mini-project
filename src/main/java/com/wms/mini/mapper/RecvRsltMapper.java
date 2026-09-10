package com.wms.mini.mapper;

import com.wms.mini.domain.RecvRslt;

import java.util.List;

public interface RecvRsltMapper {
    List<RecvRslt> selectRecvRsltListByPlanNo(String planNo);
    int insertRecvRslt(RecvRslt recvRslt);
}