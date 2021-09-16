package com.mediamarktsaturn.MediaMarktSaturn.domain.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(min=2, message="Name should have at least 2 characters")
    private String name;

    @Pattern(regexp = "(ACTIVE|BLOCKED|DELETED)")
    private String onlineStatus;

    @ApiModelProperty(notes = "Product long description")
    private String longDescription;

    @ApiModelProperty(notes = "Product short description")
    private String shortDescription;

    public Product updateWith(Product item) {
        return new Product(
            this.id,
            item.name,
            item.onlineStatus,
            item.longDescription,
            item.shortDescription
        );
    }
}
