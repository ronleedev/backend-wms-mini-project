package com.wms.mini.mapper;

import com.wms.mini.domain.InspectRslt;

import java.util.List;

public interface InspectRsltMapper {
    List<InspectRslt> selectInspectRsltListByPickListNo(String pickListNo);
    int insertInspectRslt(InspectRslt inspectRslt);
}