package org.benjaminsmith.boardselector.boardreview;

public class TrustedSite {
    private long id;
    private String name;

    private TrustedSite() {}

    public TrustedSite(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
