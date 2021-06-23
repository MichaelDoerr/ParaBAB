package de.doerr;

import java.util.*;
import java.util.stream.IntStream;

public class SudokuNode implements BranchNode {

    private static long id = Long.MIN_VALUE;
    private double cost;
    private long myID;
    private int[][] sudoku;
    private Map<Integer, List<Integer>> possible;

    public SudokuNode(int[][] sudoku, Map<Integer, List<Integer>> possible) {
        myID = id;
        ++id;
        boolean contains = false;
        for (int i = 0; !contains && i < 9; i++) {
            contains = IntStream.of(sudoku[i]).anyMatch(x -> x == 0);
        }

        this.cost = contains ? 1 : 0;

        this.sudoku = new int[9][9];
        for (int i = 0; i < sudoku.length; i++)
            System.arraycopy(sudoku[i], 0, this.sudoku[i], 0, sudoku[i].length);

        if (possible == null) {
            generatePossible();
        } else {
            this.possible = possible;
        }
        //System.out.println(this.possible);
    }

    private void generatePossible() {
        List<Integer> numbers;
        this.possible = new HashMap<>();
        for (int i = 0; i < this.sudoku.length; i++) {
            for (int j = 0; j < this.sudoku[0].length; j++) {
                if (this.sudoku[i][j] == 0) {
                    numbers = new ArrayList<>(9);
                    this.possible.put(10 * i + j, numbers);
                    for (int k = 1; k <= 9; k++) {
                        this.sudoku[i][j] = k;

                        if (this.square(i, j) && this.rowsandcols(i, j)) {
                            numbers.add(k);
                        }
                    }
                    this.sudoku[i][j] = 0;
                }
            }
        }
    }

    @Override
    public boolean isSolution() {
        return this.cost == 0;
    }

    @Override
    public boolean hasSubnodes() {
        return this.cost != 0;
    }

    @Override
    public List<BranchNode> getSubNodes(Solution currentBest) {
        List<BranchNode> subs = new ArrayList<>(9);
        for (int i = 0; i < this.sudoku.length; i++) {
            for (int j = 0; j < this.sudoku[0].length; j++) {
                if (this.sudoku[i][j] == 0) {
                    for (Integer k : possible.get(10 * i + j)) {
                        this.sudoku[i][j] = k;
                        if (this.square(i, j) && this.rowsandcols(i, j)) {
                            subs.add(new SudokuNode(sudoku, possible));
                        }
                    }
                    return subs;
                }
            }
        }
        return subs;
    }

    @Override
    public double getMinimalCost() {
        return 0;
    }

    @Override
    public double cost() {
        return this.cost;
    }

    public double prio() {
        return myID;
    }

    @Override
    public int compareTo(BranchNode o) {
        return Double.compare(o.prio(), myID);
    }

    private boolean square(int row, int col) {
        int sqZ = 0;
        int sqS = 0;
        int[] low = {3, 4, 5};
        int[] high = {6, 7, 8};
        boolean contains;
        contains = IntStream.of(low).anyMatch(x -> x == row);
        if (contains) {
            sqZ = 3;
        }
        contains = IntStream.of(low).anyMatch(x -> x == col);
        if (contains) {
            sqS = 3;
        }
        contains = IntStream.of(high).anyMatch(x -> x == row);
        if (contains) {
            sqZ = 6;
        }
        contains = IntStream.of(high).anyMatch(x -> x == col);
        if (contains) {
            sqS = 6;
        }
        for (int i = sqZ; i < sqZ + 3; i++) {
            for (int j = sqS; j < sqS + 3; j++) {
                if (!(i == row && j == col) && sudoku[i][j] == sudoku[row][col]) {
                    //System.out.println(this.toString());
                    //System.out.println("SQ");
                    return false;
                }
            }
        }
        return true;
    }

    private boolean rowsandcols(int row, int col) {
        for (int i = 0; i < 9; i++) {
            if (!(i == row) && this.sudoku[i][col] == this.sudoku[row][col]) {
                return false;
            }
            if (!(i == col) && this.sudoku[row][i] == this.sudoku[row][col]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String out = "";
        for (int i = 0; i < 9; i++) {
            out += Arrays.toString(this.sudoku[i]) + "\n";
        }
        return out;
    }
}
