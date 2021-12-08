package threads;

import listeners.ChangeListener;
import sudoku.objects.SudokuGrid;
import sudoku.objects.box;

import java.util.ArrayList;
import java.util.Random;

public class GenerateGrid extends Thread {

    SudokuGrid grid;
    SudokuGrid solvedGrid;
    int minRow;
    int minCol;
    ChangeListener listener;
    int numberOfUnique = 0;
    int limit = 200;

    public GenerateGrid(ChangeListener listener, ChangeListener teller)
    {
       this.listener = listener;
       ChangeListener.listener tester = new ChangeListener.listener() {
           @Override
           public void onChange(Object b) {
               numberOfUnique++;
           }
       };
       teller.setBoo(numberOfUnique);
       teller.setChangeListener(tester);
    }

    public void SetGrid(SudokuGrid Solvedgrid)
    {
        Solvedgrid = solvedGrid;
        grid = new SudokuGrid();
    }
    public void SetLimit(int limit)
    {
        this.limit = limit;
    }

    public SudokuGrid GetGrid()
    {
        return grid;
    }
    public void run()
    {

        boolean noSolution = true;
        int k = 0;
        while(numberOfUnique<=limit) {
            while (noSolution) {
                //BruteForceGenerator();
                CustomGenerator();
                grid.DisplayGrid();
                System.out.println("");
                noSolution = Validate();
                if (noSolution) {
                    grid = new SudokuGrid();
                }
            }
            //listener.somethingChanged();
            grid = new SudokuGrid();
            noSolution = true;
        }
        String mainMenu = "\n===== Threaded Soduku Solver Task 2 ===== \n1.Generate Values Grids\n2.Display All Grids\n3.Display Unique Grids\n4.Display Saved Grids\n0.Exit App\n";
        System.out.print(mainMenu);
    }

    protected void FindMinimum()
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
    protected void CustomGenerator()
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
    protected boolean Validate()
    {
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
        listener.setBoo(solvedGrid);
        listener.somethingChanged();
        return false;
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
