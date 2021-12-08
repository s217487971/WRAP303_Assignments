package com.example.myapplication.task1data;

import java.util.ArrayList;
import java.util.Random;

public class SudokuGenerator {

    SudokuGrid grid;
    SudokuGrid solvedGrid;
    SudokuGrid oldgrid;
    int minRow;
    int minCol;

    public SudokuGenerator(SudokuGrid grid)
    {
        this.grid = grid;
        solvedGrid = null;
    }
    public SudokuGrid WaveCollapseAlgorithm()
    {
        /**
         * Generates New Sudoku Grid
         * Fills Up the Positions using Wave Collapse Function
         */
        boolean noSolution = true;
        solvedGrid = null;
    while (noSolution)
    {
        //BruteForceGenerator();
        CustomGenerator();
        grid.DisplayGrid();
        System.out.println("");
        noSolution = Validate();
        if(noSolution) {
            grid = new SudokuGrid();
        }
    }
    if(solvedGrid!=null)
        return solvedGrid;
    return grid;
    }

    public SudokuGrid TrialAndErrorAlgorithm()
    {
        /**
         * Generates New Sudoku Grid
         * Fills Up the Positions using Wave Collapse Function
         */
        boolean noSolution = true;
        while (noSolution)
        {
            BruteForceGenerator();
            grid.DisplayGrid();
            System.out.println("");
            noSolution = Validate();
            if(noSolution) {
                grid = new SudokuGrid();
            }
        }
        if(solvedGrid!=null)
            return solvedGrid;
        return grid;

    }

    public void FindMinimum()
    {
        int min = 9;
        for (int i = 0; i < 9 ; i++) {
            for (int j = 0; j < 9 ; j++) {
                if(grid.GetGrid()[i][j].getPossibilityCount()< min && grid.GetGrid()[i][j].getPossibilityCount()>0 && grid.GetGrid()[i][j].getFact()==0 )
                {
                    min = grid.GetGrid()[i][j].getPossibilityCount();
                    minRow = i;
                    minCol = j;
                }
            }
        }
    }
    public void CustomGenerator()
    {
        //Enhanced Generator
        for (int i = 0; i < 9 ; i++) {
            for (int j = 0; j < 9 ; j++) {
                FindMinimum();
                box current = grid.GetGrid()[minRow][minCol];
                if(current.getPossibilityCount()>0)
                {
                    int val = GetRandomPossiblility(current.getPossibilities());
                    grid.SetValue(val,minRow,minCol);
                }
            }
        }

    }
    protected void BruteForceGenerator()
    {
        for (int i = 0; i < 9 ; i++) {
            for (int j = 0; j < 9 ; j++) {
                box current = grid.GetGrid()[i][j];
                if(current.getPossibilityCount()>0 && current.getFact()==0)
                {
                    int val = GetRandomPossiblility(current.getPossibilities());
                    grid.SetValue(val,i,j);
                }
            }
        }
    }
    protected boolean Validate()
    {
        if(oldgrid!=null && grid == oldgrid)
            return true;
        for (int i = 0; i <9 ; i++) {
            for (int j = 0; j < 9; j++) {
                box current = grid.GetGrid()[i][j];
                if(current.getPossibilityCount()==0 && current.getFact()==0)
                {
                    System.out.println("Soduku Has no solution");
                    return true;
                }
            }
        }
        System.out.println("Sudoku Solved");
        solvedGrid = grid;
        oldgrid = solvedGrid;
        return false;
    }

    public SudokuGrid GeneratedGrid()
    {

        return grid;
    }
    public int GetRandomPossiblility(String values)
    {
        ArrayList<Integer> k = new ArrayList<>();
        String parts[] = values.split(" ");
        for(String part: parts) {
            k.add(Integer.valueOf(part));
        }
        Random random = new Random();
        int low = 0;
        int high = k.size();
        int result = random.nextInt(high-low) + low;
        return k.get(result);
    }


}
