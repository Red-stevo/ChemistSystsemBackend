package com.red.stevo.chemsales.Entities;

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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String categoryId;

    @Size(max = 250, message = "Category Name was Too Long")
    private String categoryName;

}
