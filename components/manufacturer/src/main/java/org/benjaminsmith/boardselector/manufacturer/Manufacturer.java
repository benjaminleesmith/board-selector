package org.benjaminsmith.boardselector.manufacturer;

public class Manufacturer {
    private String name;
    private long id;

    public Manufacturer(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}
