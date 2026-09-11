package com.wms.mini.mapper;

import com.wms.mini.domain.PickRslt;

import java.util.List;

public interface PickRsltMapper {
    List<PickRslt> selectPickRsltListByPickListNo(String pickListNo);
    int insertPickRslt(PickRslt pickRslt);
}