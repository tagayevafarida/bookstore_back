package com.example.bookstore_back.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String firstname;
    @Column
    private String secondname;
    @Column
    private String username;
    @JsonIgnore
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private String token;
    @Column
    private String role;
    @JsonIgnore
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "favourites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> favourites = new ArrayList<>();
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_baskets",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "basket_id")
    )
    private List<Basket> baskets = new ArrayList<>();

    public User(
            String firstname,
            String secondname,
            String username,
            String password,
            String email,
            String token,
            String role,
            byte[] image
    ){
        this.firstname = firstname;
        this.secondname = secondname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.token = token;
        this.role = role;
        this.image = image;
    }
}
