import listeners.ChangeListener;
import sudoku.objects.SudokuGrid;
import sudoku.objects.box;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class main {


    static volatile Vector<SudokuGrid> sudokuGrids = new Vector<>();
    static volatile Vector<SudokuGrid> uniqueGrids = new Vector<>();
    static volatile Vector<String> RunningThreads = new Vector<>();
    static volatile String mainMenu = "\n===== Threaded Soduku Solver Task 2 ===== \n1.Generate Unique Grids\n2.Display All Grids\n3.Display Unique Grids\n4.View Saved Unique Grids\n0.Exit App\n";
    static volatile String lastSavedfile = "";
    static String directory = "";


    static ChangeListener listener = new ChangeListener(sudokuGrids.size());

    static Thread Generate;
    static Thread Check;
    static Thread Save;
    static int limit = 10;

    public static void main(String[] args) {
        directory = System.getProperty("user.dir");
        directory = directory + "\\Saved_Unique_Grids";

        ChangeListener.listener RecieveGrid = new ChangeListener.listener() {
            @Override
            public void onChange(Object b) {
                try {
                    StartChecking();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        listener.setChangeListener(RecieveGrid);

        Runnable GenerateGrids = new Runnable() {
            SudokuGrid grid = new SudokuGrid();
            SudokuGrid solvedGrid;
            int minRow;
            int minCol;


            @Override
            public void run() {

                main.RunningThreads.add("G");
                boolean noSolution = true;
                while (uniqueGrids.size() < main.limit) {
                    while (noSolution) {

                        CustomGenerator();
                        grid.DisplayGrid();
                        System.out.println("");
                        noSolution = Validate();
                        if (noSolution) {
                            grid = new SudokuGrid();
                        }
                    }
                    sudokuGrids.add(solvedGrid);
                    listener.somethingChanged();
                    grid = new SudokuGrid();
                    noSolution = true;
                }
                System.out.print(main.mainMenu);
            }

            protected void CustomGenerator() {
                //Enhanced Generator
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        FindMinimum();
                        box current = grid.GetGrid()[minRow][minCol];
                        if (current.getPossibilityCount() > 0) {
                            int val = GetRandomPossiblility(current.getPossibilities());
                            grid.SetValue(val, minRow, minCol);
                        }
                    }
                }

            }

            protected void FindMinimum() {
                int min = 9;
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (grid.GetGrid()[i][j].getPossibilityCount() < min && grid.GetGrid()[i][j].getPossibilityCount() > 0 && grid.GetGrid()[i][j].getFact() == 0) {
                            min = grid.GetGrid()[i][j].getPossibilityCount();
                            minRow = i;
                            minCol = j;
                        }
                    }
                }
            }

            protected boolean Validate() {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        box current = grid.GetGrid()[i][j];
                        if (current.getPossibilityCount() == 0 && current.getFact() == 0) {
                            System.out.println("Soduku Has no solution");
                            return true;
                        }
                    }
                }
                System.out.println("Sudoku Solved");
                solvedGrid = grid;
                return false;
            }

            public int GetRandomPossiblility(String values) {
                ArrayList<Integer> k = new ArrayList<>();
                String parts[] = values.split(" ");
                for (String part : parts) {
                    k.add(Integer.valueOf(part));
                }
                Random random = new Random();
                int low = 0;
                int high = k.size();
                int result = random.nextInt(high - low) + low;
                return k.get(result);
            }
        };
        Runnable CheckUniqueNess = new Runnable() {

            @Override
            public void run() {
                main.RunningThreads.add("C");
                while (uniqueGrids.size() < main.limit) {
                    SudokuGrid prev = new SudokuGrid();
                    while (sudokuGrids.size() > 0) {
                        SudokuGrid potential = sudokuGrids.get(sudokuGrids.size() - 1);
                        if (!(potential.equals(prev))) {
                            prev = potential;
                            if (IsUnique(potential)) {
                                uniqueGrids.add(potential);
                            }
                        }
                    }
                }
                if(main.RunningThreads.size()>0)
                    main.RunningThreads.remove("C");
            }


            public boolean IsUnique(SudokuGrid grid) {

                if (uniqueGrids.size() > 0) {
                    for (SudokuGrid gr : uniqueGrids
                    ) {
                        if (grid.equals(gr))
                            return false;
                        SudokuGrid Mirror = ColumnMirror(gr);
                        SudokuGrid Mirror2 = RowMirror(gr);
                        if (grid.equals(Mirror) || grid.equals(Mirror2))
                            return false;
                        SudokuGrid rotate90 = Rotate(gr);
                        SudokuGrid rotate180 = Rotate(rotate90);
                        SudokuGrid rotate270 = Rotate(rotate180);
                        if (grid.equals(rotate90) || grid.equals(rotate180) || grid.equals(rotate270))
                            return false;
                    }
                }
                return true;
            }

            protected SudokuGrid ColumnMirror(SudokuGrid grid) {
                SudokuGrid grid1 = new SudokuGrid();
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        grid1.SetValue(grid.GetValue(i, j), i, 8 - j);
                    }
                }
                return grid1;
            }

            protected SudokuGrid RowMirror(SudokuGrid grid) {
                SudokuGrid grid1 = new SudokuGrid();
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        grid1.SetValue(grid.GetValue(i, j), 8 - i, j);
                    }
                }
                return grid1;
            }

            protected SudokuGrid Rotate(SudokuGrid grid) {
                SudokuGrid grid1 = new SudokuGrid();
                int size = grid.GetGrid().length;

                for (int i = 0; i < size; ++i)
                    for (int j = 0; j < size; ++j)
                        grid1.SetValue(grid.GetValue(size - j - 1, i), i, j);

                return grid1;
            }
        };
        Runnable SaveGridFiles = new Runnable() {

            @Override
            public void run() {
                main.RunningThreads.add("S");
                int start = 0;
                while (start < main.limit )
                {
                    if(main.uniqueGrids.size()>0 && start<main.uniqueGrids.size())
                    {
                        String filename = main.directory + "\\" +String.valueOf(start) + ".txt";
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(filename);
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                            SudokuGrid gridnow = uniqueGrids.get(start);
                            for (int i = 0; i < 9; i++) {
                                for (int j = 0; j < 9; j++) {
                                    bw.write(gridnow.GetValue(i, j) + " ");
                                }
                                bw.newLine();
                            }
                            bw.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        start++;
                    }
                }
            }
        };

        Generate = new Thread(GenerateGrids);
        Check = new Thread(CheckUniqueNess);
        Save = new Thread(SaveGridFiles);

        menu();
    }

    public static void menu() {

        System.out.print(mainMenu);
        Scanner scanner = new Scanner(System.in);
        String val = scanner.nextLine();

        if (val.equals("1")) {
            System.out.print("Enter The number Grids You need: ");
            scanner = new Scanner(System.in);
            String val2 = scanner.nextLine();
            try {
                limit = Integer.valueOf(val2);
            } catch (Exception e) {

            }
            Generator();
            //grid.DisplayGrid();

        } else if (val.equals("2")) {
            ViewAllGrids(sudokuGrids);
        } else if (val.equals("3")) {
            AdjustList();
            ViewAllGrids(uniqueGrids);
        } else if (val.equals("45")) {
            SavedGrids();
        } else if (val.equals("0")) {
            return;
        }
        else if(val.equals("4"))
        {
            ViewSavedGrids();
        }

        menu();

    }

    private static void AdjustList() {
        while (uniqueGrids.size()>limit)
        {
            uniqueGrids.remove(uniqueGrids.size()-1);
        }
    }

    public static void ViewAllGrids(Vector<SudokuGrid> list) {
        if (list.size() > 0) {
            int numberedGrids = 0;
            for (SudokuGrid gr : list
            ) {
                System.out.print("Grid Number :" + numberedGrids + "\n");
                gr.DisplayGrid();
                System.out.println("======================================");
                numberedGrids++;
            }
        }

    }

    public static void Generator() {
        Generate.start();
    }

    public static void StartChecking() throws IOException {
        if (sudokuGrids.size() == 1) {
            Check.start();
            SavedGrids();
        }
    }

    public static void SavedGrids()  {
        File f = new File(directory);
        if (f.exists()) {
            System.out.println("Directory : " + directory + " Already Exists");
            try {
                deleteDirectoryRecursionJava6(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
            File x = new File(directory);
            x.mkdir();
            Save.start();
        } else if (!(f.exists())) {
            if (f.mkdir()) {
                System.out.println("Directory : " + directory + " Successfully Created");
                Save.start();
            } else
                System.out.println("Directory : " + directory + "Could not Be Created");

        }

    }

    public static void ViewSavedGrids() {

        try (Stream<Path> files = Files.list(Paths.get(directory))) {
            long count = files.count();
            if (count > 0) {
                File file = new File(directory);
                String filename = directory + "\\" + String.valueOf(0)+".txt";
                for (int i = 0; i < count; i++) {
                    FileInputStream fis = null;
                    try {
                        filename = directory + "\\" + String.valueOf(i)+".txt";
                        fis = new FileInputStream(filename);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (fis != null) {
                        InputStreamReader inputStreamReader =
                                new InputStreamReader(fis, StandardCharsets.UTF_8);
                        StringBuilder stringBuilder = new StringBuilder();
                        BufferedReader reader;
                        try {
                            reader = new BufferedReader(inputStreamReader);
                            String line = reader.readLine();
                            System.out.println("Grid: " + filename + "\n");
                            while (line != null) {
                                stringBuilder.append(line).append('\n');
                                String string = line;
                                //String[] parts = string.split(" ");
                                System.out.println(line);
                                line = reader.readLine();
                            }
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("");
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void deleteDirectoryRecursionJava6(File file) throws IOException {
        if (file.isDirectory()) {
            File[] entries = file.listFiles();
            if (entries != null) {
                for (File entry : entries) {
                    deleteDirectoryRecursionJava6(entry);
                }
            }
        }
        if (!file.delete()) {
            throw new IOException("Failed to delete " + file);
        }
    }

}
