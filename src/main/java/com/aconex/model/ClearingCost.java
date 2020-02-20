package com.aconex.model;

import java.util.LinkedHashSet;
import java.util.Set;

public class ClearingCost {

    private Set<Item> items;

    public Set<Item> getItems() {
        if (this.items == null) {
            this.items = new LinkedHashSet<>();
        }

        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public int getTotalCost() {
        return items.stream().mapToInt(Item::getCost).sum();
    }
}
