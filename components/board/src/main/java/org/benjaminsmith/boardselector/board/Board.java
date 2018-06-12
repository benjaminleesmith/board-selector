package org.benjaminsmith.boardselector.board;

public class Board {
    private String model;
    private long id;
    private long constructionId;
    private long manufacturerId;

    public Board() {}

    public Board(long id, String model, long constructionId, long manufacturerId) {
        this.id = id;
        this.model = model;
        this.constructionId = constructionId;
        this.manufacturerId = manufacturerId;
    }

    public Board(String model, long constructionId, long manufacturerId) {
        this.model = model;
        this.constructionId = constructionId;
        this.manufacturerId = manufacturerId;
    }

    public long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public long getManufacturerId() {
        return manufacturerId;
    }

    public long getConstructionId() {
        return constructionId;
    }

    public void setId(long id) {
        this.id = id;
    }
}
