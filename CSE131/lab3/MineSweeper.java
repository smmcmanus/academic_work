package lab3;

import cse131.ArgsProcessor;

public class MineSweeper {

	public static void main (String[] args) {
		ArgsProcessor ap = new ArgsProcessor(args);
		int cols = ap.nextInt("How many columns?");
		int rows = ap.nextInt("How many rows?");
		double percent = ap.nextDouble("What is the probability of a bomb?");
		String[][] board = new String[cols + 2][rows + 2];
		String[][] answers = new String[cols + 2][rows + 2];
		int adjBombs = 0;
		for(int i = 1; i <= cols; i++){
			for(int j = 1; j <= rows; j++){
				double bombProb = Math.random() * 100;
				if(bombProb <= percent) board[i][j] = "*";
				else board[i][j] = ".";
			}
		}
		for(int k = 1; k <= cols; k++){
			for(int l = 1; l <= rows; l++){
				adjBombs = 0;
				for(int m = k - 1; m <= k +1; m++){
					for(int n = l - 1; n <= l + 1; n++){
						if(board[m][n] == "*"){
							adjBombs++;
						}
					}
				}
				answers[k][l] ="" + adjBombs;
				if(board[k][l] == "*"){
					answers[k][l] = "*";
				}
			}
		}
		for(int o = 1; o <= cols; o++){
			for(int p = 1; p <= rows; p++){
				System.out.print(board[o][p] + " ");
			}
			System.out.print(" ");
			for(int q = 1; q <= rows; q++){
				System.out.print(answers[o][q] + " ");
			}
			System.out.println("");
		}
	}
}



