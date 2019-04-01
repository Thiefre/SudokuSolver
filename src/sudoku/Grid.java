package sudoku;

import java.util.*;


public class Grid 
{
	private int[][]						values;
	

	//
	// DON'T CHANGE THIS.
	//
	// Constructs a Grid instance from a string[] as provided by TestGridSupplier.
	// See TestGridSupplier for examples of input.
	// Dots in input strings represent 0s in values[][].
	//
	public Grid(String[] rows)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
		{
			String row = rows[j];
			char[] charray = row.toCharArray();
			for (int i=0; i<9; i++)
			{
				char ch = charray[i];
				if (ch != '.')
					values[j][i] = ch - '0';
			}
		}
	}
	
	
	//
	// DON'T CHANGE THIS.
	//
	public String toString()
	{
		String s = "";
		for (int j=0; j<9; j++)
		{
			for (int i=0; i<9; i++)
			{
				int n = values[j][i];
				if (n == 0)
					s += '.';
				else
					s += (char)('0' + n);
			}
			s += "\n";
		}
		return s;
	}


	//
	// DON'T CHANGE THIS.
	// Copy ctor. Duplicates its source. You’ll call this 9 times in next9Grids.
	//
	Grid(Grid src)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
			for (int i=0; i<9; i++)
				values[j][i] = src.values[j][i];
	}
	
	
	// Finds an empty member of values[][]. Returns an array list of 9 grids that look like the current grid,
	// except the empty member contains 1, 2, 3 .... 9. Returns null if the current grid is full. Don’t change
	// “this� grid. Build 9 new grids.
	// 
	//
	// Example: if this grid = 1........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//
	// Then the returned array list would contain:
	//
	// 11.......          12.......          13.......          14.......    and so on     19.......
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	//
	public ArrayList<Grid> next9Grids()
	{		
		int xOfNextEmptyCell = 0;
		int yOfNextEmptyCell = 0;

		// Find x,y of an empty cell.
		for(int i = 0; i<values.length; i++)
		{
			for(int j =0; j<values[i].length; j++)
			{
				if(values[i][j] == 0)
				{
					xOfNextEmptyCell = i;
					yOfNextEmptyCell = j;
					break;
				}
			}
		}
		// Construct array list to contain 9 new grids.
		ArrayList<Grid> grids = new ArrayList<Grid>();
		
		// Create 9 new grids as described in the comments above. Add them to grids.
		for(int k = 0; k < values.length; k++)
		{
			Grid temp = new Grid(this);
			temp.values[xOfNextEmptyCell][yOfNextEmptyCell] = k+1;
			grids.add(temp);
		}
		return grids;
	}
	
	
	// Returns true if this grid is legal. A grid is legal if no row, column, or
	// 3x3 block contains a repeated 1, 2, 3, 4, 5, 6, 7, 8, or 9.
	//
	public boolean isLegal()
	{
		ArrayList<Integer> holder = new ArrayList<Integer>();
		// Check every row. If you find an illegal row, return false.
		for(int i = 0; i<values.length; i++)
		{
			for(int j =0; j<values[i].length; j++)
			{
				for(int k : holder)
				{
					if(holder.contains(k) && k == values[i][j])
					{
						return false;
					}
				}
				if(values[i][j]>0)
				{
				holder.add(values[i][j]);
				}
			}
			holder.clear();
		}

		// Check every column. If you find an illegal column, return false.
		for(int j = 0; j<values[1].length; j++)
		{
			for(int i =0; i<values.length; i++)
			{
				for(int k : holder)
				{
					if(holder.contains(k) && k == values[i][j])
					{
						return false;
					}
				}
				if(values[i][j]>0)
				{
				holder.add(values[i][j]);
				}
			}
			holder.clear();
		}
		// Check every block. If you find an illegal block, return false.
		for(int l = 0; l<9; l+=3)
		{
			for(int n = 0; n<9; n+=3)
			{
			for(int i = 0; i<3; i++)
			{
				for(int j = 0; j<3; j++)
				{
					for(int k : holder)
					{
						if(holder.contains(k) && k == values[i+l][j+n])
						{
							return false;
						}
					}
					if(values[i+l][j+n]>0)
					{
					holder.add(values[i+l][j+n]);
					}
				}
			}
			holder.clear();
			}
		}
		// All rows/cols/blocks are legal.
		return true;
	}

	
	// Returns true if every cell member of values[][] is a digit from 1-9.
	//
	public boolean isFull()
	{
		for(int i = 0; i<values.length; i++)
		{
			for(int j = 0; j<values[i].length; j++)
			{
				if(values[i][j] > 9 || values[i][j] < 1)
				{
					return false;
				}
			}
		}
		return true;
	}
	

	// Returns true if x is a Grid and, for every (i,j), 
	// x.values[i][j] == this.values[i][j].
	//
	public boolean equals(Object x)
	{
		Grid grid = (Grid) x;
		for(int i = 0; i<values.length; i++)
		{
			for(int j = 0; j<values[i].length; j++)
			{
				if(grid.values[i][j] != this.values[i][j])
				{
					return false;
				}
			}
		}
		return true;
	}

}
