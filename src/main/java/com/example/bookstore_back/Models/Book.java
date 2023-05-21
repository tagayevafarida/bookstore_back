package com.example.bookstore_back.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String author;
    @Column(columnDefinition = "TEXT")
    private String description;
    @JsonIgnore
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] preview;
    @Column
    private Double cost;
    @OneToOne
    @JoinColumn(name = "file_id")
    private File file;

    public Book(
            String name,
            String author,
            String description,
            byte[] preview,
            Double cost,
            File file
    ) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.preview = preview;
        this.cost = cost;
        this.file = file;
    }
}
