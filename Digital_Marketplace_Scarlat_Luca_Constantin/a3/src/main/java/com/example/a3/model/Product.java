package com.example.a3.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="products")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Column
    private String name;

    @Column
    private String description;

}
