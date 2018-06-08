package org.benjaminsmith.boardselector.construction;

public class Construction {
    private long id;
    private String name;

    public Construction() {
    }

    public Construction(String name) {
        this.name = name;
    }

    public Construction(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }
}
