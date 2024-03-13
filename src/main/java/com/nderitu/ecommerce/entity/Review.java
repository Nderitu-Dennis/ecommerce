package com.nderitu.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Table(name="reviews")

public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long rating;

    @Lob
    private String description;

    @Lob
    private byte[] img;

    @ManyToOne(fetch=FetchType.LAZY, optional = false)  //relationship between the current entity and another entity is many-to-one
    /*LAZY, which means that the associated entity will be loaded from the database
    only when it's accessed for the first time. This can help improve performance by avoiding
    unnecessary loading of associated entities until they are actually needed.*/

    @JoinColumn(name="user_id", nullable = false) /*used to specify the column in the current entity's table that
     is used to store the foreign key referencing the associated entity. */

    @OnDelete(action = OnDeleteAction.CASCADE) /* configures the behavior of the database when a referenced entity is deleted. In this case,
       it's set to CASCADE, which means that if a referenced entity is deleted, all associated entities will also be deleted automatically by the database. */

    private User user;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="product_id", nullable=false)
    @OnDelete(action=OnDeleteAction.CASCADE)
    private Product product;

}
