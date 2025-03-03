package com.gildedrose;

public class AgedBrie extends StandardItem {

    public AgedBrie(Item item) {
        super(item);
    }

    @Override
    public void updateState() {
        int increaseRate = (item.sellIn > 0) ? 1 : 2;
        item.quality = Math.min(50, item.quality + increaseRate);
        item.sellIn -= 1;
    }
}