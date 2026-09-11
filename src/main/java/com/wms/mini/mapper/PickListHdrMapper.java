package com.wms.mini.mapper;

import com.wms.mini.domain.PickListHdr;

import java.util.List;

public interface PickListHdrMapper {
    List<PickListHdr> selectPickListHdrList();
    PickListHdr selectPickListHdr(String pickListNo);
    int insertPickListHdr(PickListHdr pickListHdr);
    int updatePickListHdrStatus(PickListHdr pickListHdr);
}