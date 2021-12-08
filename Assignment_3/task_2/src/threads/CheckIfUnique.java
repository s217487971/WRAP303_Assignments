package threads;

import listeners.ChangeListener;
import sudoku.objects.SudokuGrid;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

public class CheckIfUnique extends Thread{

    public static volatile  Vector<SudokuGrid> grids;
    public static volatile Vector<SudokuGrid> unique;
    private int limit;
    ChangeListener ExportUniqueGrids;
    ChangeListener tellerr;
    int numberOfGrids = 0;

    public CheckIfUnique(ChangeListener listener,ChangeListener teller, Vector<SudokuGrid> gridss, Vector<SudokuGrid> uniques)
    {
        grids = gridss;
        unique = uniques;
        ExportUniqueGrids = listener;
        tellerr = teller;
        ExportUniqueGrids.setBoo(unique);
        ChangeListener.listener listener1 = new ChangeListener.listener() {
            @Override
            public void onChange(Object b) {

                grids.add((SudokuGrid)b);
            }
        };
        ExportUniqueGrids.setChangeListener(listener1);
        ChangeListener.listener listener2 = new ChangeListener.listener() {
            @Override
            public void onChange(Object b) {
                grids = (Vector<SudokuGrid>)b;
                run();
            }
        };
        tellerr.setChangeListener(listener2);

    }

    public void SetUniqueGrids(Vector<SudokuGrid> uniqueGrids)
    {
        unique = uniqueGrids;
    }

    public void SetGrids(Vector<SudokuGrid> grids)
    {

        this.grids = grids;
    }

    public void run()
    {
        if(unique==null)
            unique = new Vector<>();

        //Check if Grid is Unique, then Remove Add to Unique Grids
        //Update the number of Unique Grids
        int k = 0;
        numberOfGrids = unique.size();
        while (k<limit)
        {
            while (grids.size()>0 && k<=limit)
            {
                SudokuGrid potential = grids.get(grids.size()-1);
                {
                    if(IsUnique(potential))
                    {
                        unique.add(potential);
                        potential.IsUnique();
                        ExportUniqueGrids.setBoo(potential);
                        ExportUniqueGrids.somethingChanged();
                    }
                }
                k++;
            }
            numberOfGrids = unique.size();
        }

    }
    public void SetNumberOfGrids(int numberOfGrids)
    {
        limit = numberOfGrids;
    }

    public boolean IsUnique(SudokuGrid grid)
    {
        /**Check If Grid is Unique
        if(unique.size()>0)
        {
            for (SudokuGrid gr:unique
                 ) {
                //SudokuGrid rowMirror = RowMirror(grid);
                //SudokuGrid ColMirror = ColumnMirror(grid);
                //SudokuGrid rotated = Rotate(grid)
                if(gr.equals(grid))
                    return false;
            }
        }
        return true;*/
        return true;
    }
    protected SudokuGrid ColumnMirror(SudokuGrid grid)
    {
        SudokuGrid grid1 = new SudokuGrid();
        for (int i = 0; i <9 ; i++) {
            for (int j = 0; j <9 ; j++) {
                grid1.SetValue(grid.GetValue(i,j),i,8-j);
            }
        }
        return grid1;
    }
    protected SudokuGrid RowMirror(SudokuGrid grid)
    {
        SudokuGrid grid1 = new SudokuGrid();
        for (int i = 0; i <9 ; i++) {
            for (int j = 0; j <9 ; j++) {
                grid1.SetValue(grid.GetValue(i,j),8-i,j);
            }
        }
        return grid1;
    }
    protected SudokuGrid Rotate(SudokuGrid grid)
    {
        SudokuGrid grid1 = new SudokuGrid();
        for (int i = 0; i <9 ; i++) {
            for (int j = 0; j <9 ; j++) {

            }
        }
        return grid1;
    }
    private SudokuGrid LeftDiagonal(SudokuGrid grid)
    {
        SudokuGrid grid1 = new SudokuGrid();
        for (int i = 0; i <9 ; i++) {
            for (int j = 0; j <9 ; j++) {

            }
        }
        return grid1;
    }
}
