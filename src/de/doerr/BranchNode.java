package de.doerr;

import java.util.List;

public interface BranchNode extends Comparable<BranchNode> {
    boolean isSolution();

    boolean hasSubnodes();

    List<BranchNode> getSubNodes(Solution currentBest);

    double getMinimalCost();

    double cost();

    double prio();
}
