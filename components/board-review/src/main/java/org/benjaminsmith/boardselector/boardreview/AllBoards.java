package org.benjaminsmith.boardselector.boardreview;

import java.util.Arrays;

public class AllBoards {
    private final Board[] boards;
    private final TrustedSite[] trustedSites;

    public AllBoards(Board[] boards, TrustedSite[] trustedSites) {
        this.boards = boards;
        this.trustedSites = trustedSites;
    }

    public Board[] getBoards() {
        return boards;
    }

    public TrustedSite[] getTrustedSites() {
        return trustedSites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllBoards allBoards = (AllBoards) o;
        return Arrays.equals(boards, allBoards.boards) &&
            Arrays.equals(trustedSites, allBoards.trustedSites);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(boards);
        result = 31 * result + Arrays.hashCode(trustedSites);
        return result;
    }
}
