package com.wms.mini.service;

import com.wms.mini.domain.ItemMst;
import com.wms.mini.mapper.ItemMstMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemMstService {

    private final ItemMstMapper itemMstMapper;

    public List<ItemMst> getItemMstList() {
        return itemMstMapper.selectItemMstList();
    }

    public ItemMst getItemMst(String itemCd) {
        return itemMstMapper.selectItemMst(itemCd);
    }

    public void createItemMst(ItemMst itemMst) {
        itemMst.setUseYn("Y");
        itemMstMapper.insertItemMst(itemMst);
    }

    public void updateItemMst(ItemMst itemMst) {
        itemMstMapper.updateItemMst(itemMst);
    }

    public void deleteItemMst(String itemCd) {
        itemMstMapper.deleteItemMst(itemCd);
    }
}