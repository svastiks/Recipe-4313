package dev.gwozdz.DemoRecipe.model;

import javax.persistence.*;

@Entity
public class Ingredient {

    public Ingredient() {
    }

    public Ingredient(String description, Double quantity, UnitOfMeasure uom) {
        this.description = description;
        this.quantity = quantity;
        this.uom = uom;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Double quantity;


    @OneToOne(fetch = FetchType.EAGER)
    private UnitOfMeasure uom;

    @ManyToOne
    private Recipe recipe;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public String toString(){
        if(quantity==null || uom.getDescription()==null){
            return description;
        }
        return quantity + " x " + uom.getDescription() + " of " +description;
    }
    public UnitOfMeasure getUom() {
        return uom;
    }

    public void setUom(UnitOfMeasure uom) {
        this.uom = uom;
    }

}
