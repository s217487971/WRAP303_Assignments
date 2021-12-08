import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

public class main  {

    static SudokuGrid grid;
    static SudokuGenerator generator;

    public static void main(String[] args)  {

        grid = new SudokuGrid();
        //grid.DisplayGrid();
        generator = new SudokuGenerator(grid);
        menu();

    }
    public static void menu()
    {
        String mainMenu = "\n===== Soduku Solver Task 1 ===== \n1.Display Current Grid\n2.Generate Values\n3.Insert Value Into Grid\n4. Get value\n5.Clear Grid\n.Any other Key will Exit\n";
        System.out.print(mainMenu);
        Scanner scanner = new Scanner(System.in);
        String val = scanner.nextLine();
        if(val.equals("1"))
        {
            grid.DisplayGrid();
            menu();
        }
        else if(val.equals("2"))
        {
            Generator();
            //grid.DisplayGrid();
            menu();
        }
        else if(val.equals("3"))
        {
            InsertValue();
        }
        else if(val.equals("4"))
        {
            GetValue();
        }
        else if(val.equals("5"))
        {
            ClearGrid();
        }
    }

    public static void InsertValue()
    {
        System.out.println("=== Insertion of Value into Sudoku Grid ===");
        String mainMenu = "Enter Row :";
        System.out.print(mainMenu);
        Scanner scanner = new Scanner(System.in);
        String val = scanner.nextLine();
        mainMenu = "Enter Col :";
        System.out.print(mainMenu);
        scanner = new Scanner(System.in);
        String val2 = scanner.nextLine();

        try {
            int k = Integer.valueOf(val);
            int kk =  Integer.valueOf(val2);
            grid.ShowBox(k,kk);


            mainMenu = "Enter Value :";
            System.out.print(mainMenu);
            scanner = new Scanner(System.in);
            String val3 = scanner.nextLine();
            int kkk = Integer.valueOf(val3);

            grid.SetValue(kkk,k,kk);
            grid.DisplayGrid();
            System.out.println(" ");
            menu();
        }
        catch (NumberFormatException e)
        {
            System.out.println("Invalid Row or Column value ");
        }

    }

    public static void GetValue()
    {
        System.out.print("Enter Row");
        Scanner scanner = new Scanner(System.in);
        String val = scanner.nextLine();

        System.out.print("Enter Col");
        scanner = new Scanner(System.in);
        String val2 = scanner.nextLine();
        try {
            int k = Integer.valueOf(val);
            int kk =  Integer.valueOf(val2);
            grid.ShowBox(k,kk);
            System.out.println(" ");

        }
        catch (NumberFormatException e)
        {
            System.out.println("Invalid Row or Column value ");
        }
    }

    public static void ClearGrid()
    {
        grid.ClearBoxes();
        grid.DisplayGrid();
        System.out.println(" ");
        menu();

    }

    public static void Generator()
    {

        System.out.println("=====Select Solution Method==== \n1.Wave Collapse Algorithm\n2.BruteForce Algorithm\n(make sure the Grid is clear)\n");
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
        }
    }
}
