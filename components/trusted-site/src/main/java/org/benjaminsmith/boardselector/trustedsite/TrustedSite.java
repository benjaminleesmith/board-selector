package org.benjaminsmith.boardselector.trustedsite;

public class TrustedSite {
    private String name;
    private long id;

    public TrustedSite(String name) {
        this.name = name;
    }

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

    public void setId(long id) {
        this.id = id;
    }
}
