/*
package com.nderitu.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nderitu.ecommerce.dto.ProductDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="product")
@Data

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long price;

    @Lob
    private String description;

    @Lob
    @Column(columnDefinition = "bytea") //todo check on this and resolve the DB
    private byte[] img;

    //relation of this product entity with category
    @ManyToOne(fetch=FetchType.LAZY, optional = false)  //many products in one category
    @JoinColumn(name="category_id", nullable= false)
    @OnDelete(action= OnDeleteAction.CASCADE) //todo research on this
    @JsonIgnore
    private Category category;

    public ProductDto getDto(){
        ProductDto productDto=new ProductDto();
        productDto.setId(id);
        productDto.setName(name);
        productDto.setPrice(price);
        productDto.setDescription(description);
        productDto.setByteImg(img);
        productDto.setCategoryId(category.getId());
        productDto.setCategoryName(category.getName());

        return productDto;
    }

}
*/

package com.nderitu.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nderitu.ecommerce.dto.ProductDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal price;

    @Lob
    private String description;

    @Lob
    private byte[] img;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category;

    public ProductDto getDto() {
        ProductDto productDto = new ProductDto();
        productDto.setId(id);
        productDto.setName(name);
        productDto.setPrice(price);
        productDto.setDescription(description);
        productDto.setByteImg(img);
      //  productDto.setCategoryId(new BigDecimal(category.getId()));
        productDto.setCategoryId(category.getId());
        productDto.setCategoryName(category.getName());

        return productDto;
    }

}
