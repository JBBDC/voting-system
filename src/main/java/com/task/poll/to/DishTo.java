package com.task.poll.to;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;
import java.util.Objects;

public class DishTo extends BaseTo {

    private final String name;

    private final BigDecimal price;

    @ConstructorProperties({"id", "name", "price"})
    public DishTo(int id, String name, BigDecimal price) {
        super(id);
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishTo dishTo = (DishTo) o;
        return name.equals(dishTo.name) &&
                price.equals(dishTo.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    @Override
    public String toString() {
        return "DishTo{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", id=" + id +
                '}';
    }
}
