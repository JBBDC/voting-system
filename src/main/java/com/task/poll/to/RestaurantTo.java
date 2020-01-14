package com.task.poll.to;

import com.task.poll.model.User;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Objects;

public class RestaurantTo extends BaseTo {
    private final String name;
    private final List<DishTo> menu;

    @ConstructorProperties({"id", "name", "menu"})
    public RestaurantTo(int id, String name, List<DishTo> menu) {
        super(id);
        this.name = name;
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public List<DishTo> getMenu() {
        return menu;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantTo that = (RestaurantTo) o;
        return name.equals(that.name) &&
                Objects.equals(menu, that.menu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, menu);
    }
}
