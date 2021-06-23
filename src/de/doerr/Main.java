package de.doerr;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

public class Main {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        int cores = Runtime.getRuntime().availableProcessors();
        //cores = 1;
        PriorityBlockingQueue<BranchNode> set = new PriorityBlockingQueue<>();

        List<Thread> threads = new ArrayList<>(cores);
        //int [][] sud = {{0, 0, 0, 0, 0, 0, 0, 1, 0},
        //                {8, 0, 4, 0, 2, 0, 3, 0, 7},
        //                {0, 6, 0, 9, 0, 7, 0, 2, 0},
        //                {0, 0, 5, 0, 3, 0, 1, 0, 0},
        //                {0, 7, 0, 5, 0, 1, 0, 3, 0},
        //                {0, 0, 3, 0, 9, 0, 8, 0, 0},
        //                {0, 2, 0, 8, 0, 5, 0, 6, 0},
        //                {1, 0, 7, 0, 6, 0, 4, 0, 9},
        //                {0, 3, 0, 0, 0, 0, 0, 8, 0}};

        //int [][] sud = {{0, 0, 0, 0, 0, 0, 0, 0, 0},
        //                {0, 0, 0, 0, 0, 0, 0, 0, 0},
        //                {0, 0, 0, 0, 0, 0, 0, 0, 0},
        //                {0, 0, 0, 0, 0, 0, 0, 0, 0},
        //                {0, 0, 0, 0, 0, 0, 0, 0, 0},
        //                {0, 0, 0, 0, 0, 0, 0, 0, 0},
        //                {0, 0, 0, 0, 0, 0, 0, 0, 0},
        //                {0, 0, 0, 0, 0, 0, 0, 0, 0},
        //                {0, 0, 0, 0, 0, 0, 0, 0, 0}};

        int[][] sud = {{0, 3, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 0, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 0},
                {4, 0, 0, 8, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 2, 0, 0, 0, 0},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 0, 0, 0, 7, 0}};

        //int [][] sud = {{8, 0, 0,  0, 0, 0,  0, 0, 0},
        //                {0, 0, 3,  6, 0, 0,  0, 0, 0},
        //                {0, 7, 0,  0, 9, 0,  2, 0, 0},
        //                {0, 5, 0,  0, 0, 7,  0, 0, 0},
        //                {0, 0, 0,  0, 4, 5,  7, 0, 0},
        //                {0, 0, 0,  1, 0, 0,  0, 3, 0},
        //                {0, 0, 1,  0, 0, 0,  0, 6, 8},
        //                {0, 0, 8,  5, 0, 0,  0, 1, 0},
        //                {0, 9, 0,  0, 0, 0,  4, 0, 0}};

        BranchNode root = new SudokuNode(sud, null);

        Solution currentBest = new Solution(root);
        set.put(root);

        //ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(cores);

        //for (int i = 0; i < cores; i++) {
        //    BAndB current = new BAndB(currentBest,set);
        //    executor.execute(current);
        //}
        //synchronized (set) {
        //    while (!set.isEmpty()) {
        //        try {
        //            set.wait();
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }
        //    }
        //}
        //executor.shutdownNow();


        for (int i = 0; i < cores; i++) {
            threads.add(new Thread(new BAndB(currentBest, set)));
            threads.get(i).start();
        }
        for (int i = 0; i < cores; i++) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(currentBest);
        long endTime = System.currentTimeMillis();

        System.out.println("That took " + ((endTime - startTime)) + " milliseconds");
    }


}
