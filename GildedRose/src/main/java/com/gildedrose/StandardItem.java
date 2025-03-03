package com.gildedrose;

public class StandardItem implements CustomisedItem {
    protected Item item;

    public StandardItem(Item item) {
        this.item = item;
    }

    @Override
    public void updateState() {
        int degradeRate = (item.sellIn > 0) ? 1 : 2;
        item.quality = Math.max(0, item.quality - degradeRate);
        item.sellIn -= 1;
    }
}