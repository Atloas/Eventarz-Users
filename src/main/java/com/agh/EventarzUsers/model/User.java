package com.agh.EventarzUsers.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
@Table(name = "users")
public class User {

    @Id
    private String username;
    @Column(name = "password_hash")
    private String passwordHash;
    @Column(name = "register_date")
    private String registerDate;
    private String role;
    private boolean banned;
}
