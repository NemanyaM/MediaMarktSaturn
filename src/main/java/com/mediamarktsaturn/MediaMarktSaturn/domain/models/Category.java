package com.mediamarktsaturn.MediaMarktSaturn.domain.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "The unique id of the category")
    private Long id;

    @NotBlank
    @Size(min=2, message="Name should have at least 2 characters")
    @ApiModelProperty(notes = "Category Name")
    private String name;

    @ApiModelProperty(notes = "Category parent id")
    private Integer parentId;

    public Category updateWith(Category item) {
        return new Category(
                this.id,
                item.name,
                item.parentId
        );
    }
}
