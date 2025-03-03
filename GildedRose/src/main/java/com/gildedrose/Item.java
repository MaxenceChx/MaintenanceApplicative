package com.gildedrose;

public class Item {

    public String name;

    public int sellIn;

    public int quality;

    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

   @Override
   public String toString() {
        return this.name + ", " + this.sellIn + ", " + this.quality;
    }

    public void updateItem() {
        this.quality = Math.max(0, this.quality - (this.sellIn >= 0 ? 1 : 2));
        this.sellIn -= 1;
    }
}
