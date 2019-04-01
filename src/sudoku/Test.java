package sudoku;

import java.util.ArrayList;

public class Test 
{

	public boolean testing(int[][] values)
	{
		ArrayList<Integer> holder = new ArrayList<Integer>();	
		for(int n = 0; n<9; n+=3)
		{
		for(int l = 0; l<9; l+=3)
		{
			for(int i = 0; i<3; i++)
			{
				for(int j = 0; j<3; j++)
				{
					for(int k : holder)
					{
						if(holder.contains(k) && k == values[i+l][j+n])
						{
							System.out.println(holder);
							return false;
						}
					}
					if(values[i+l][j+n]>0)
					{
					holder.add(values[i+l][j+n]);
					}
				}
			}
			System.out.println(holder);
			holder.clear();
		}
		}
		return true;
	}
	public static void main(String[] args)
	{
		Test meme = new Test();
		int[][] lol = new int[][]
				{
			{7,4,3,6,1,2,5,9,8},
			{9,6,2,5,8,7,3,4,1},
			{1,5,8,4,9,3,6,2,7},
			{6,8,4,3,7,1,9,5,2},
			{5,2,7,9,6,4,8,1,3},
			{3,9,1,8,2,5,4,7,6},
			{8,1,9,2,5,6,7,3,4},
			{2,3,5,7,4,8,1,6,9},
			{4,7,6,1,3,9,2,8,5}
				};
		if(meme.testing(lol))
		{
			System.out.println("True!");
		}
	}
	
}
