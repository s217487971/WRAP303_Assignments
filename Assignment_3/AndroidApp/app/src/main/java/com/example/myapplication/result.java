package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.INotificationSideChannel;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.listeners.ChangeListener;
import com.example.myapplication.task1data.SudokuGrid;
import com.example.myapplication.task1data.box;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class result extends AppCompatActivity {

    static volatile Vector<SudokuGrid> sudokuGrids = new Vector<>();
    static volatile Vector<SudokuGrid> uniqueGrids = new Vector<>();
    static ChangeListener listener = new ChangeListener(sudokuGrids.size());
    static ChangeListener listenToo = new ChangeListener(uniqueGrids.size());

    static String directory = "";
    static Thread Generate;
    static Thread Check;
    static Thread Save;
    int limit = 10;

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        textView = findViewById(R.id.textView5);

        directory = Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files";


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
        ChangeListener.listener RecieveGridd = new ChangeListener.listener() {
            @Override
            public void onChange(Object b) {

                    updateUI();

            }
        };

        listener.setChangeListener(RecieveGrid);

        Button button = findViewById(R.id.button5);
        Button button1 = findViewById(R.id.button6);


        limit = getIntent().getIntExtra("limit",10);

        Runnable GenerateGrids = new Runnable() {
            SudokuGrid grid = new SudokuGrid();
            SudokuGrid solvedGrid;
            int minRow;
            int minCol;


            @Override
            public void run() {

                boolean noSolution = true;
                while (uniqueGrids.size() < limit) {
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
                while (uniqueGrids.size() < limit) {
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
                int start = 0;
                while (start < limit )
                {
                    if(uniqueGrids.size()>0 && start< uniqueGrids.size())
                    {
                        String filename = directory + "/" +String.valueOf(start) + ".txt";
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
                listenToo.somethingChanged();
            }
        };


        Generate = new Thread(GenerateGrids);
        Check = new Thread(CheckUniqueNess);
        Save = new Thread(SaveGridFiles);

        Generate.start();

        textView.setText("");

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), view.class);
                startActivity(intent);
            }
        };


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
                Save.start();
            }

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
    public void updateUI()
    {
        String datee = "Generated Grids : "+sudokuGrids.size()+"\nUnique Grids : "+uniqueGrids.size();
        textView.setText(datee);
    }
}