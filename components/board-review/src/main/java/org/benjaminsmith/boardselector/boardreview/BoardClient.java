package org.benjaminsmith.boardselector.boardreview;

import org.springframework.web.client.RestOperations;

import java.util.List;

public class BoardClient {
    private final RestOperations restOperations;
    private final String endpoint;

    public BoardClient(RestOperations restOperations, String endpoint) {
        this.restOperations = restOperations;
        this.endpoint = endpoint;
    }

    public Board[] getBoards() {
        return restOperations.getForObject(endpoint+"/admin/boards", Board[].class);
    }
}
