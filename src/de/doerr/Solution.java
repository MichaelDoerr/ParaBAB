package de.doerr;


public class Solution {
    private BranchNode incubant;

    public Solution(BranchNode incubant) {
        this.incubant = incubant;
    }

    public void update(BranchNode incubant) {
        this.incubant = incubant;
    }

    boolean isSolution() {
        return incubant.isSolution();
    }

    public boolean hasSubnodes() {
        return incubant.hasSubnodes();
    }

    double getMinimalCost() {
        return incubant.getMinimalCost();
    }

    double cost() {
        return incubant.cost();
    }

    @Override
    public String toString() {
        return incubant.toString();
    }
}
