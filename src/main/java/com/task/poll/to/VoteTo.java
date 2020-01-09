package com.task.poll.to;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class VoteTo extends BaseTo {
    private final int userId;
    private final int restaurantId;
    private final LocalDate date;

    @ConstructorProperties({"id", "userId", "restaurantId", "dateTime"})
    public VoteTo(int id, int userId, int restaurantId, LocalDate dateTime) {
        super(id);
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.date = dateTime;
    }

    public int getUserId() {
        return userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteTo voteTo = (VoteTo) o;
        return userId == voteTo.userId &&
                restaurantId == voteTo.restaurantId &&
                date.equals(voteTo.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, restaurantId, date);
    }
}
