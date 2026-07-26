package com.metesevim.staj2026.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(
        name = "products",
        indexes = {
                @Index(
                        name = "idx_products_name",
                        columnList = "name"
                ),
                @Index(
                        name = "idx_products_seller_active",
                        columnList = "seller_id, active"
                )
        }
)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stock;

    private Boolean active;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private AppUser seller;
}
