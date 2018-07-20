package org.benjaminsmith.boardselector.trustedsite;

public class TrustedSite {
    private final String name;
    private long id;
    private String url;

    public TrustedSite(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public TrustedSite(long id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() { return url; }

    public void setId(long id) {
        this.id = id;
    }
}
