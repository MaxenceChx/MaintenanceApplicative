package com.gildedrose;

class GildedRose {
    public static final String AGED_BRIE = "Aged Brie";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String CONJURED = "Conjured";

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            updateItem(item);
        }
    }
    
    private void updateItem(Item item) {
        if (item.name.equals(SULFURAS)) return;
    
        item.sellIn -= 1;
    
        switch (item.name) {
            case AGED_BRIE:
                increaseQuality(item, (item.sellIn >= 0) ? 1 : 2);
                break;
    
            case BACKSTAGE_PASSES:
                if (item.sellIn < 0) {
                    item.quality = 0;
                } else {
                    int increase = (item.sellIn < 5) ? 3 : (item.sellIn < 10) ? 2 : 1;
                    increaseQuality(item, increase);
                }
                break;

            case CONJURED:
                decreaseQuality(item, (item.sellIn >= 0) ? 2 : 4);
                break;
    
            default:
                decreaseQuality(item, (item.sellIn >= 0) ? 1 : 2);
                break;
        }
    }
    
    private void increaseQuality(Item item, int amount) {
        item.quality = Math.min(50, item.quality + amount);
    }
    
    private void decreaseQuality(Item item, int amount) {
        item.quality = Math.max(0, item.quality - amount);
    }
}
