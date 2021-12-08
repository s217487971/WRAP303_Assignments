public class SudokuGrid {
    box[][] grid;

    public SudokuGrid()
    {
        grid = new box[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = new box();
            }
        }
    }
    public void DisplayGrid()
    {
        int temp = 0;

        for (int i = 0; i < 9; i++) {
            System.out.print("  _   _   _   _   _   _   _   _   _ \n|");
            for (int j = 0; j < 9; j++) {
                temp = grid[i][j].getFact();
                if(temp==0)
                {
                    System.out.print(" _ |");
                }
                else if(temp!=0)
                {
                    System.out.print(" "+temp+" |");
                }
            }
            System.out.print("\n");
        }
    }
    public box[][] GetGrid()
    {
        return grid;
    }
    public void UpdatePossibilities(int v, int row, int col)
    {
        UpdateRow(v,row);
        UpdateColumn(v,col);
        UpdateBox(v,row,col);
    }

    public void SetValue(int value, int row, int col)
    {
        if(row<9 && row>-1 && col<9 && col>-1)
        {
            String conflicts ="";
            if(RowConflict(value,row))
            {
                conflicts = conflicts + "Row Conflict";
            }
            if(ColConflict(value,col))
            {
                if(conflicts.length()>0)
                {
                    conflicts = conflicts+" and ";
                }
                conflicts = conflicts + "Column Conflicts";
            }
            if(BoxConflict(value,row,col))
            {
                if(conflicts.length()>0)
                {
                    conflicts = conflicts+" and ";
                }
                conflicts = conflicts + "Box Conflict";
            }

            System.out.println(conflicts);

            if(conflicts.length()>1)
                return;
            else
            {
                grid[row][col].put(value);
                UpdatePossibilities(value,row,col);
                System.out.println("Value: "+value+" Successfully Added to Grid");
            }
            
        }
    }
    public int GetValue(int row, int col)
    {
        if(row<9 && row>-1 && col<9 && col>-1)
        {
            return grid[row][col].getFact();
        }
        return -1;
    }
    public void ClearBoxes()
    {
        grid = new box[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = new box();
            }
        }

    }
    public boolean RowConflict(int value, int row)
    {
        for (int i = 0; i < 9; i++) {
            if(grid[row][i].getFact()==value)
            {return true;}
        }
        return false;
    }
    public boolean ColConflict(int value, int col)
    {
        for (int i = 0; i < 9; i++) {
            if(grid[i][col].getFact()==value)
            {return true;}
        }
        return false;
    }

    public boolean BoxConflict(int value, int row, int col)
    {
        int rowRange = getLowerBound(row);
        int colRange = getLowerBound(col);

        for (int i = 0; i <3 ; i++) {
            for (int j = 0; j < 3; j++) {
                if(value == grid[rowRange+i][colRange+j].getFact())
                {
                    return true;
                }
            }
        }
        return false;
    }

    public int getLowerBound(int b)
    {
        int range = -1;
        if(b>-1 && b<3)
        {
            range = 0;
        }
        else if(b>2 && b<6)
        {
            range = 3;
        }
        else if(b>5 && b<9)
        {
            range  = 6;
        }
        return range;
    }

    public void ShowBox(int row, int col)
    {

        if(row<9 && row>-1 && col<9 && col>-1)
        {
            box current  =  grid[row][col];
            if(current.getPossibilityCount()>1)
            {
                System.out.println("Possibile numbers: "+current.getPossibilities());
            }
            else
            {
                System.out.println("Box Value: "+current.getFact());
            }
        }
    }
    public void UpdateRow(int value, int row)
    {
        for (int i = 0; i < 9; i++) {
            grid[row][i].remove(value);
        }
    }
    public void UpdateColumn(int value, int col)
    {
        for (int i = 0; i < 9; i++) {
            grid[i][col].remove(value);
        }
    }

    public void UpdateBox(int value, int row, int col)
    {
        int rowRange = getLowerBound(row);
        int colRange = getLowerBound(col);

        for (int i = 0; i <3 ; i++) {
            for (int j = 0; j < 3; j++) {
                grid[rowRange+i][colRange+j].remove(value);
        }}

    }
}
