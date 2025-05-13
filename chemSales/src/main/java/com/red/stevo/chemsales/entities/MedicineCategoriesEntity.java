package com.red.stevo.chemsales.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="medicine_category_table")
public class MedicineCategoriesEntity {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String categoryId;

    @Column(unique = true)
    private String categoryName;

}
