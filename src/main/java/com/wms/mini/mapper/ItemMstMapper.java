package com.wms.mini.mapper;

import com.wms.mini.domain.ItemMst;

import java.util.List;

public interface ItemMstMapper {
    List<ItemMst> selectItemMstList();
    ItemMst selectItemMst(String itemCd);
    int insertItemMst(ItemMst itemMst);
    int updateItemMst(ItemMst itemMst);
    int deleteItemMst(String itemCd);
}