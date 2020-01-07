package com.task.poll.to;

import com.task.poll.model.User;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class RestaurantTo extends BaseTo {
    private final String name;
    private final List<DishTo> menu;
    private final boolean voted;

    @ConstructorProperties({"id", "name", "menu", "voted"})
    public RestaurantTo(int id, String name, List<DishTo> menu, boolean voted) {
        super(id);
        this.name = name;
        this.menu = menu;
        this.voted = voted;
    }

    public String getName() {
        return name;
    }

    public List<DishTo> getMenu() {
        return menu;
    }

    public boolean isVoted() {
        return voted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantTo that = (RestaurantTo) o;
        return voted == that.voted &&
                name.equals(that.name) &&
                Objects.equals(menu, that.menu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, menu, voted);
    }
}
