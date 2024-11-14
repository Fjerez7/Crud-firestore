package com.example.proyecto013;

public class Establecimiento {
    String name;
    String owner;
    String Id;

    public Establecimiento() {
    }

    public Establecimiento(String name, String owner, String id) {
        this.name = name;
        this.owner = owner;
        Id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
