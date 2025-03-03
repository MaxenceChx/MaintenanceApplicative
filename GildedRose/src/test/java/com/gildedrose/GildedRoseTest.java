package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {
    @Test
    void testUpdateZeroItemQuality() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
        assertEquals(-1, app.items[0].sellIn);
    }

    @Test
    void testUpdateItemQuality() {
        Item[] items = new Item[] { new Item("foo", 1, 2) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(1, app.items[0].quality);
        assertEquals(0, app.items[0].sellIn);
    }

    @Test
    void testUpdatePassedItemQuality() {
        Item[] items = new Item[] { new Item("foo", -1, 2) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
        assertEquals(-2, app.items[0].sellIn);
    }

    @Test
    void testUpdateAgedBrieItemQuality() {
        Item[] items = new Item[] { new Item(GildedRose.AGED_BRIE, 2, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(1, app.items[0].quality);
        assertEquals(1, app.items[0].sellIn);
    }

    @Test
    void testUpdatePassedAgedBrieItemQuality() {
        Item[] items = new Item[] { new Item(GildedRose.AGED_BRIE, 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(2, app.items[0].quality);
        assertEquals(-1, app.items[0].sellIn);
    }

    @Test
    void testUpdateHighAgedBrieItemQuality() {
        Item[] items = new Item[] { new Item(GildedRose.AGED_BRIE, 0, 49) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
        assertEquals(-1, app.items[0].sellIn);
    }

    @Test
    void testUpdate50AgedBrieItemQuality() {
        Item[] items = new Item[] { new Item(GildedRose.AGED_BRIE, 1, 50) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
        assertEquals(0, app.items[0].sellIn);
    }

    @Test
    void testUpdateSulfurasItemQuality() {
        Item[] items = new Item[] { new Item(GildedRose.SULFURAS, 2, 80) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(80, app.items[0].quality);
        assertEquals(2, app.items[0].sellIn);
    }

    @Test
    void testUpdatePassedSulfurasItemQuality() {
        Item[] items = new Item[] { new Item(GildedRose.SULFURAS, -1, 80) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(80, app.items[0].quality);
        assertEquals(-1, app.items[0].sellIn);
    }

    @Test
    void testUpdate20BackstageItemQuality() {
        Item[] items = new Item[] { new Item(GildedRose.BACKSTAGE_PASSES, 20, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(11, app.items[0].quality);
        assertEquals(19, app.items[0].sellIn);
    }

    @Test
    void testUpdate20BackstageItemQuality50() {
        Item[] items = new Item[] { new Item(GildedRose.BACKSTAGE_PASSES, 20, 49) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
        assertEquals(19, app.items[0].sellIn);
    }

    @Test
    void testUpdate10BackstageItemQuality() {
        Item[] items = new Item[] { new Item(GildedRose.BACKSTAGE_PASSES, 10, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(12, app.items[0].quality);
        assertEquals(9, app.items[0].sellIn);
    }

    @Test
    void testUpdate10BackstageItemQuality50() {
        Item[] items = new Item[] { new Item(GildedRose.BACKSTAGE_PASSES, 10, 49) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
        assertEquals(9, app.items[0].sellIn);
    }

    @Test
    void testUpdate5BackstageItemQuality() {
        Item[] items = new Item[] { new Item(GildedRose.BACKSTAGE_PASSES, 5, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(13, app.items[0].quality);
        assertEquals(4, app.items[0].sellIn);
    }

    @Test
    void testUpdate5BackstageItemQuality50() {
        Item[] items = new Item[] { new Item(GildedRose.BACKSTAGE_PASSES, 5, 49) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
        assertEquals(4, app.items[0].sellIn);
    }

    @Test
    void testUpdatePassedBackstageItemQuality() {
        Item[] items = new Item[] { new Item(GildedRose.BACKSTAGE_PASSES, 0, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
        assertEquals(-1, app.items[0].sellIn);
    }
}
