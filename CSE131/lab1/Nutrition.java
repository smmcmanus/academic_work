package lab1;

import cse131.ArgsProcessor;

public class Nutrition {

	public static void main(String[] args) {
		ArgsProcessor ap = new ArgsProcessor(args);
		String name = ap.nextString("Food Name?");
		double carbs = ap.nextDouble("Grams of Carbohydrates?");
		double fat = ap.nextDouble("Grams of Fat?");
		double protein = ap.nextDouble("Grams of Protein?");
		int calories = ap.nextInt("Stated Calories?");
		
		System.out.println(name + " has");
		
		double carbCal = (carbs * 4);
		double carbRound = Math.round(carbCal * 10.0) / 10.0;
		double proteinCal = (protein * 4);
		double fatCal = (fat * 9);
		double fatRound = Math.round(fatCal * 10.0) / 10.0;
		double totalCal = (carbRound + proteinCal + fatRound);
		double unavailCal = (totalCal - calories);
		double unavailRound = Math.round(unavailCal * 10.0) / 10.0;
		double dietFiber = (unavailRound / 4);
		double perCarb = Math.round(((carbCal / calories) * 100) * 10.0) / 10.0;
		double perFat = Math.round(((fatCal / calories) * 100) * 10.0) / 10.0;
		double perPro = Math.round(((proteinCal / calories) * 100) * 10.0) / 10.0;
		
		System.out.println("Approximately");
		System.out.println(	carbs + " grams of carbohydrates = " + carbRound + " Calories");
		System.out.println(	fat + " grams of fat = " + fatRound + " Calories");
		System.out.println(	protein + " grams of protein = " + proteinCal + " Calories");
		System.out.println();
		System.out.println("This food is said to have " + calories + " (Available) Calories." );
		System.out.println("With " + unavailRound + " unavailable Calories, this food has "+ dietFiber + " grams of fiber." );
		System.out.println();
		System.out.println(" " + perCarb + "% of your food is carbohydrates");
		System.out.println(" " + perFat + "% of your food is fat");
		System.out.println(" " + perPro + "% of your food is protein");
		System.out.println();
		
		boolean lowCarb = (perCarb <= 25);
		boolean lowFat = (perFat <= 15);
		
		System.out.println("This food is acceptable for a low-carb diet? " + lowCarb);
		System.out.println("This food is acceptable for a low-fat diet? " + lowFat);
		
		double r = Math.random();
		boolean heads = (r < 0.5);
		
		System.out.println("By coin flip, should you eat this food? " + heads);
				
	}

}
