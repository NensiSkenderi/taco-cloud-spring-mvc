package com.taco.cloud.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Taco {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Date createdAt;

    @NotNull
    @Size(min=5, message="Name must be at least 5 characters long")
    private String name;

    /*
    A Taco can have many TacoIngredient objects,
    and an TacoIngredient can be a part of many Taco
     */
    @ManyToMany(targetEntity=TacoIngredient.class)
    @Size(min=1, message="You must choose at least 1 ingredient")
    private List<TacoIngredient> ingredients;

    @PrePersist // before Taco is persisted
    void createdAt() {
        this.createdAt = new Date();
    }

}
