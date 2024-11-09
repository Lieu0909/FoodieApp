package com.example.foodie.Recipes;

class Information {
    private String id;
    private String name;
    private String ingredient;
    private String instruction;
    private String dateTime;
    // private int IconImage;
    public Information(String id, String name, String ingredient, String instruction, String dateTime) {
        this.id = id;
        this.name = name;
        this.ingredient = ingredient;
        this.instruction = instruction;
        this.dateTime = dateTime;
    }

    public void setName(String subject) {
        this.name = subject;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getId() {
        return id;
    }
}
