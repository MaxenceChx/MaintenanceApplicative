package com.gildedrose;

public class ConjuredItem extends StandardItem {

    public ConjuredItem(Item item) {
        super(item);
    }

    @Override
    public void updateState() {
        int degradeRate = (item.sellIn > 0) ? 2 : 4;
        item.quality = Math.max(0, item.quality - degradeRate);
        item.sellIn -= 1;
    }
}