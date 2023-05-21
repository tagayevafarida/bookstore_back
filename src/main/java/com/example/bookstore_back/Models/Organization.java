package com.example.bookstore_back.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "organizations")
@Getter
@Setter
@NoArgsConstructor
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "organization_books",
            joinColumns = @JoinColumn(name = "organization_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books = new ArrayList<>();
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] icon;
    @Column
    private String type;

    public Organization (
            String name,
            User user,
            byte[] icon,
            String type
    ) {
        this.name = name;
        this.user = user;
        this.icon = icon;
        this.type = type;
    }
}
