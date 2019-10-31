package com.task.poll.model;

import javax.persistence.*;


@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String password;



}
