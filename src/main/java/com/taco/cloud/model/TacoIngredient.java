package com.taco.cloud.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TacoIngredient {

    @Id
    private String id;
    private String name;
    private Type type;

    // JPA requires that entities have a no-arguments constructor
    public TacoIngredient() {
    }

    public TacoIngredient(String id, String name, Type type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public enum Type {
        VEGGIE, WRAP, PROTEIN, CHEESE
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TacoIngredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
