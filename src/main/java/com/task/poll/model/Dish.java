package com.task.poll.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "dishes")
public class Dish extends AbstractNamedEntity {

    @Column(name = "price")
    @NotNull
    @Range(min = 0, max = Integer.MAX_VALUE)
    private BigDecimal price;

    @Column(name = "created", nullable = false, columnDefinition = "timestamp default CURRENT_DATE")
    private LocalDate created = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(Dish copy) {
        this(copy.getId(), copy.getName(), copy.getPrice());
    }

    public Dish(String name, BigDecimal price) {
        super(name);
        this.price = price;
    }

    public Dish(int id, String name, BigDecimal price) {
        super(id, name);
        this.price = price;
    }

    public Dish(String name, BigDecimal price, Restaurant restaurant) {
        this(name, price);
        this.restaurant = restaurant;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}

