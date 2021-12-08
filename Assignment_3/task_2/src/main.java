import listeners.ChangeListener;
import sudoku.objects.SudokuGrid;
import threads.CheckIfUnique;
import threads.GenerateGrid;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class main  {

    static SudokuGrid grid = new SudokuGrid();



    static volatile Vector<SudokuGrid> sudokuGrids = new Vector<>();
    static volatile Vector<SudokuGrid> uniqueGrids = new Vector<>();



    static ChangeListener uniqueListener = new ChangeListener(uniqueGrids.size());
    static ChangeListener listener = new ChangeListener(sudokuGrids.size());
    static ChangeListener teller = new ChangeListener(sudokuGrids);
    static ChangeListener gridteller = new ChangeListener(sudokuGrids);
    static GenerateGrid generateGrid;
    static CheckIfUnique thread2;

    public static void main(String[] args)  {


        ChangeListener.listener RecieveGrid = new ChangeListener.listener() {
            @Override
            public void onChange(Object b) {
                sudokuGrids.add((SudokuGrid)b);
                UniqueNessCheckingMethod((SudokuGrid) b);
                //gridteller.setBoo(sudokuGrids);
                //gridteller.somethingChanged();
            }
        };
        ChangeListener.listener RecieveUniqueGrid = new ChangeListener.listener() {
            @Override
            public void onChange(Object b) {
                uniqueGrids.add((SudokuGrid)b);
                teller.somethingChanged();
            }
        };
        listener.setChangeListener(RecieveGrid);
        uniqueListener.setChangeListener(RecieveUniqueGrid);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        Thread smallThread = new Thread(runnable);
        smallThread.start();
        menu();
    }
    public static void menu()
    {
        String mainMenu = "\n===== Threaded Soduku Solver Task 2 ===== \n1.Generate Values Grids\n2.Display All Grids\n3.Display Unique Grids\n4.Display Saved Grids\n0.Exit App\n";
        System.out.print(mainMenu);
        Scanner scanner = new Scanner(System.in);
        String val = scanner.nextLine();

        if(val.equals("1"))
        {
            /**System.out.print("Enter The number Grids You need: ");
            scanner = new Scanner(System.in);*/
            String val2 = "";
            Generator(val2);
            //grid.DisplayGrid();

        }
        else if(val.equals("2"))
        {
            ViewAllGrids(sudokuGrids);
        }
        else if(val.equals("3"))
        {
           ViewAllGrids(uniqueGrids);
        }
        else if(val.equals("4"))
        {
            SavedGrids();
        }
        else if(val.equals("0"))
        {
            return;
        }
        menu();

    }

    public static void ViewAllGrids(Vector<SudokuGrid> list)
    {
        if(list.size()>0)
        {
            int numberedGrids = 0;
            for (SudokuGrid gr:list
            ) {
                System.out.print("Grid Number :"+numberedGrids+"\n");
                gr.DisplayGrid();
                System.out.println("==========================");
                numberedGrids++;
            }
        }

    }


    public static void SavedGrids()
    {

    }

    public static void Generator(String val)
    {
        int Nogrids = 50;
        /**try
        {
            Nogrids = Integer.valueOf(val);
        }
        catch (NumberFormatException e)
        {

        }*/

        generateGrid = new GenerateGrid(listener, teller);
        thread2 = new CheckIfUnique(uniqueListener,gridteller, sudokuGrids,uniqueGrids);
        generateGrid.SetGrid(grid);
        thread2.SetUniqueGrids(uniqueGrids);
        thread2.SetNumberOfGrids(Nogrids);
        generateGrid.SetLimit(Nogrids);
        //generateGrid.start();
        generateGrid.start();
        //thread2.start();
        /**System.out.println("=====Select Solution Method==== \n1.Wave Collapse Algorithm\n2.BruteForce Algorithm\n(make sure the Grid is clear)\n");
        Scanner scanner = new Scanner(System.in);
        String val = scanner.nextLine();
        try {
            int k =  Integer.valueOf(val);
            if(k==1)
            {
                generator.WaveCollapseAlgorithm();
            }
            else if(k==2)
            {
                generator.TrialAndErrorAlgorithm();
            }
        }
        catch (NumberFormatException e)
        {
            System.out.println("Invalid Selection ");
        }*/
    }
    static public void UniqueNessCheckingMethod(SudokuGrid grid)
    {

        if(uniqueGrids.size()>0)
        {
            for (SudokuGrid gr:uniqueGrids
            ) {
                if(gr.equals(grid))
                {
                    return;
                }
            }
        }
        uniqueGrids.add(grid);
        teller.somethingChanged();
    }

}
