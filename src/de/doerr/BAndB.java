package de.doerr;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BAndB implements Runnable {

    Solution currentBest;
    PriorityBlockingQueue<BranchNode> set;

    public BAndB(Solution currentBest, PriorityBlockingQueue<BranchNode> set) {
        this.currentBest = currentBest;
        this.set = set;
    }

    public void run() {
        BranchNode currentNode;
        while (!set.isEmpty()) {
            currentNode = null;
            try {
                currentNode = set.poll(100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }

            if (currentNode == null) {
                continue;
            }

            for (BranchNode s : currentNode.getSubNodes(currentBest)) {
                synchronized (currentBest) {
                    if (s.isSolution() && s.cost() < currentBest.cost()) {
                        System.out.println("SOLUTION");
                        currentBest.update(s);
                    }
                }
                if (s.hasSubnodes() && s.getMinimalCost() < currentBest.cost()) {
                    set.put(s);
                }
            }
        }
        synchronized (set) {
            set.notifyAll();
        }
    }
}
