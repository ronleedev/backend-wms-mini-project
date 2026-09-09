package com.wms.mini.controller;

import com.wms.mini.domain.ItemMst;
import com.wms.mini.service.ItemMstService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemMstController {

    private final ItemMstService itemMstService;

    @GetMapping
    public List<ItemMst> list() {
        return itemMstService.getItemMstList();
    }

    @GetMapping("/{itemCd}")
    public ItemMst get(@PathVariable String itemCd) {
        return itemMstService.getItemMst(itemCd);
    }

    @PostMapping
    public void create(@RequestBody ItemMst itemMst) {
        itemMstService.createItemMst(itemMst);
    }

    @PutMapping("/{itemCd}")
    public void update(@PathVariable String itemCd, @RequestBody ItemMst itemMst) {
        itemMst.setItemCd(itemCd);
        itemMstService.updateItemMst(itemMst);
    }

    @DeleteMapping("/{itemCd}")
    public void delete(@PathVariable String itemCd) {
        itemMstService.deleteItemMst(itemCd);
    }
}