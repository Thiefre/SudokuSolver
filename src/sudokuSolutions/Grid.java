package sudokuSolutions;

import java.util.*;


public class Grid 
{
	private int[][]						values;
	
	
	//
	// Ctor for converting string array to a Grid instance. Intended for use with
	// puzzles and test cases generated by TestGridSupplier.  See TestGridSupplier for 
	// examples of input format. Dots in input strings become 0s in values[][].
	//
	Grid(String[] rows)
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
	// Copy ctor. Duplicates its source.
	//
	Grid(Grid src)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
			for (int i=0; i<9; i++)
				values[j][i] = src.values[j][i];
	}
	
	
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
	
	
	// Finds indices of a cell that contains 0. 
	// Returns indices in a size=2 array.
	private int[] xyOfNextEmptyCell()
	{
		int[] xy = new int[2];
		
		// Search every member of values[][], looking 
		// for any zero.
		for (xy[0]=0; xy[0]<9; xy[0]++)
			for (xy[1]=0; xy[1]<9; xy[1]++)
				if (values[xy[0]][xy[1]] == 0)
					return xy;
		
		return null;
	}
	
	
	// Returns a list of 9 Grid instances that are 
	// identical to this instance except that the 
	// empty cell has been filled with 1, 2, through 9.
	ArrayList<Grid> next9Grids()
	{		
		// Find x and y of next empty cell.
		int[] xyOfNext = xyOfNextEmptyCell();
		int xOfNext = xyOfNext[0];
		int yOfNext = xyOfNext[1];
		
		// List will contain the next 9 grids.
		ArrayList<Grid> next9 = new ArrayList<Grid>();
		
		for (int n=1; n<=9; n++)
		{
			Grid nextGrid = new Grid(this);	// nextGrid 
											// is identical
											// to this
			nextGrid.values[xOfNext][yOfNext] = n;
			next9.add(nextGrid);
		}
		
		return next9;
	}
	
	
	//
	// Returns true if the input array contains a 
	// repeated value in the range [1..9]. 
	//
	private boolean containsRepeated1Thru9(int[] ints)
	{
		assert ints.length == 9;
		
		boolean[] observed = new boolean[10];
		
		for (int i: ints)
		{
			if (i == 0)
				continue;			// repeated 0s are ok, so ignore 0s
			else if (observed[i] == true)
				return true;			// i has been seen before
			else
				observed[i] = true;	// first observation of i
		}
		return false;
	}
	
	
	//
	// Returns true if this Grid is legal, i.e. no repeated values in any row,
	// column, or block. For each row, column, and block,
	// puts all 9 numbers into an array and makes
	// containsRepeated1Thru9() do the work.
	//
	boolean isLegal()
	{
		// Check all rows.
		for (int j=0; j<9; j++)
		{
			int[] ints = new int[9];
			for (int i=0; i<9; i++)
				ints[i] = values[j][i];
			if (containsRepeated1Thru9(ints))
				return false;
		}
		
		// Check all cols.
		for (int j=0; j<9; j++)
		{
			int[] ints = new int[9];
			for (int i=0; i<9; i++)
				ints[i] = values[i][j];
			if (containsRepeated1Thru9(ints))
				return false;
		}
		
		// Check all blocks. The two outer loops generate
		// i = { 0, 3, 6 } and j = { 0, 3, 6 }.
		// These i,j pairs are the upper-left corners of 
		// each block. The two inner loops compute all 9
		// index pairs for the cells in the block defined
		// by i,j.
		for (int j=0; j<9; j+=3)
		{
			for (int i=0; i<9; i+=3)		// i,j = top-left corner of block
			{
				int[] ints = new int[9];
				int n = 0;
				for (int jj=j; jj<j+3; jj++)
				{
					for (int ii=i; ii<i+3; ii++)
					{
						ints[n++] = values[jj][ii];
					}
				}
				if (containsRepeated1Thru9(ints))
					return false;
			}
		}
		
		return true;
	}  // isLegal()
	
	
	// A full grid contains no zeros, so look at all cells and 
	// return false if any cell is zero.
	public boolean isFull()
	{
		for (int j=0; j<9; j++)
			for (int i=0; i<9; i++)
				if (values[j][i] == 0) 
					return false;
		return true;
	}
	
	
	// 2 instances are deep-equal if their values[][] arrays are identical.
	public boolean equals(Object x)
	{
		Grid that = (Grid)x;
		for (int i=0; i<9; i++)
			for (int j=0; j<9; j++)
				if (this.values[i][j] != that.values[i][j])
					return false;
		return true;
	}
	
	
	static void sop(Object x) { System.out.println(x); }
	
	
	// Since Grid is not the main class of this app, you can put 
	// any test code you like in main().
	public static void main(String[] args)
	{
		sop("START");
		/*
		Collection<Grid> validGrids = new ArrayList<>();
		validGrids.add(TestGridSupplier.getPuzzle1());
		validGrids.add(TestGridSupplier.getSolution1());
		validGrids.add(TestGridSupplier.getPuzzle2());
		validGrids.add(TestGridSupplier.getSolution2());
		validGrids.add(TestGridSupplier.getPuzzle3());
		validGrids.add(TestGridSupplier.getSolution3());
		validGrids.add(TestGridSupplier.getContinue());
		validGrids.add(TestGridSupplier.getInkala());
		for (Grid g: validGrids)
			assert g.isLegal();
		
		Collection<Grid> invalidGrids = new ArrayList<>();
		invalidGrids.add(TestGridSupplier.getReject1());
		invalidGrids.add(TestGridSupplier.getReject2());
		invalidGrids.add(TestGridSupplier.getReject3());
		invalidGrids.add(TestGridSupplier.getReject4());
		for (Grid g: invalidGrids)
			assert !g.isLegal();

		Collection<Grid> solutions = new ArrayList<>();
		validGrids.add(TestGridSupplier.getSolution1());
		validGrids.add(TestGridSupplier.getSolution2());
		validGrids.add(TestGridSupplier.getSolution3());
		for (Grid g: solutions)
			assert g.isFull();
		
		sop("DONE");
		*/
		/***/
		Grid puzzle1 = TestGridSupplier.getPuzzle1();
		sop("Original\n" + puzzle1 + "\n=================");
		for (Grid g: puzzle1.next9Grids())
			sop(g + "\n=================");
		/***/
			
	}
}