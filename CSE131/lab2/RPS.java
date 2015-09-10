package lab2;

import cse131.ArgsProcessor;

public class RPS {

	public static void main(String[] args) {
		ArgsProcessor ap = new ArgsProcessor(args);
		int rounds = ap.nextInt("Number of Games");
		String choicePlayer1 = "scissors";
		String choicePlayer2 = "rock";
		int winsPlayer1 = 0;
		int winsPlayer2 = 0;
		int draw = 0;
		for(int i = 0; i < rounds; i++){
			int R = (int)(Math.random() * 3);
			if (R == 0){
				choicePlayer1 = "rock";
			}
			else if (R == 1){
				choicePlayer1 = "paper";
			}
			else{
				choicePlayer1 = "scissors";
				
		}
			/*if (choicePlayer1 == "scissors"){
				choicePlayer1 = "rock";
			}
			else if (choicePlayer1 == "rock"){
				choicePlayer1 = "paper";
			}
			else if (choicePlayer1 == "paper"){
				choicePlayer1 = "scissors";
		}*/
		
			if (choicePlayer2 == "scissors"){
				choicePlayer2 = "rock";
			}
			else if (choicePlayer2 == "rock"){
				choicePlayer2 = "paper";
			}
			else if (choicePlayer2 == "paper"){
				choicePlayer2 = "scissors";
		}
			/*int M = (int)(Math.random() * 3);
			if (M == 0){
				choicePlayer2 = "rock";
			}
			else if (M == 1){
				choicePlayer2 = "paper";
			}
			else{
				choicePlayer2 = "scissors";
		}*/
			if (choicePlayer1 == "rock" && choicePlayer2 == "rock"){
				draw++;
			}
			else if(choicePlayer1 == "rock" && choicePlayer2 == "paper"){
				winsPlayer2++;
			}
			else if(choicePlayer1 == "rock" && choicePlayer2 == "scissors"){
				winsPlayer1++;
			}
			else if(choicePlayer1 == "paper" && choicePlayer2 == "rock"){
				winsPlayer1++;
			}
			else if(choicePlayer1 == "paper" && choicePlayer2 == "paper"){
				draw++;
			}
			else if(choicePlayer1 == "paper" && choicePlayer2 == "scissors"){
				winsPlayer2++;
			}
			else if(choicePlayer1 == "scissors" && choicePlayer2 == "rock"){
				winsPlayer2++;
			}
			else if(choicePlayer1 == "scissors" && choicePlayer2 == "paper"){
				winsPlayer1++;
			}
			else if(choicePlayer1 == "scissors" && choicePlayer2 == "scissors"){
				draw++;
			}
		}
		System.out.println("A total of " + rounds + " games were played");
		System.out.println("Randomly Picking Player won " + winsPlayer1 + " times");
		System.out.println("Faithfully Rotating Player won " + winsPlayer2 + " times");
		System.out.println("The two Players produced " + draw + " draws");
		double per1 = Math.round((((winsPlayer1 + 0.0) / rounds) * 100) * 10) / 10;
		double per2 = Math.round((((winsPlayer2 + 0.0) / rounds) * 100)* 10) / 10;
		double perd = Math.round((((draw + 0.0) / rounds) * 100) * 10) / 10;
		System.out.println("Randomly Picking Player won " + winsPlayer1 + " times, approximately " + per1 + "% of the games");
		System.out.println("Faithfully Rotating Player won " + winsPlayer2 + " times, approximately " + per2 + "% of the games");
		System.out.println("The two Players produced " + draw + " draws, approximately " + perd + "% of the games");
	}
}
