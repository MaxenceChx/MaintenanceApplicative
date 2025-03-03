package com.gildedrose;

public class BackstagePassesItem extends StandardItem {

    public BackstagePassesItem(Item item) {
        super(item);
    }

    @Override
    public void updateState() {
        if (item.sellIn < 1) {
            item.quality = 0;
        } else {
            int increaseRate = (item.sellIn < 6) ? 3 : (item.sellIn < 11) ? 2 : 1;
            item.quality = Math.min(50, item.quality + increaseRate);
        }
        item.sellIn -= 1;
    }
}