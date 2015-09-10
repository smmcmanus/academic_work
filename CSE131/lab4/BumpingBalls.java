/*
 * TA: JD
 * Could have a few more comments
 * Spacing lines/breaking up sections would improve readability
 * Style grade: 13/15
 */

package lab4;
import sedgewick.*;
import cse131.ArgsProcessor;

/**Sean McManus 
 * Simulate 1 or more balls
 * bouncing around the standard draw window.
 * @author seanmcmanus
 *
 */
public class BumpingBalls {
	/**
	 * Animates given number of balls moving at provided frame rate.
	 * REQUIRES: Balls>0
	 * @param number of balls, pause length
	 * @return animation
	 */

	public static void main(String[] args) {
		ArgsProcessor ap = new ArgsProcessor(args);
		int rate = ap.nextInt("Enter Frame Rate");
		int numBalls = ap.nextInt("Number of Balls");
		StdDraw.setXscale(-1.0, 1.0);
		StdDraw.setYscale(-1.0, 1.0);
		double [][] balls = new double[numBalls][4];	//TA: Should tell the person reading the code what the array represents
		double RADIUS = 0.05;
		double radii = RADIUS * 2;
		double[] interim = new double[2];
		for(int i = 0; i < numBalls; i++){
			balls[i][0] = 1 - (Math.random() * 1.9);
			balls[i][1] = 1 - (Math.random() * 1.9);
			balls[i][2] = Math.random() * 0.07;
			balls[i][3] = Math.random() * 0.07;
		}
		StdDraw.setPenColor(StdDraw.GRAY);
		StdDraw.setPenRadius(0.5);
		StdDraw.square(0.0, 0.0, 1);
		while(true){
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.filledSquare(0, 0, 1.0);
			for(int j = 0; j < numBalls; j++){
				for(int l = 0; l < numBalls; l++){
					double distance = Math.sqrt(((balls[j][0]-balls[l][0]) * (balls[j][0]-balls[l][0])) + ((balls[j][1]-balls[l][1]) * (balls[j][1]-balls[l][1])));
					if((distance <= radii)){
						interim[0] = balls[j][2]; //Switch the velocities of one ball with the other
						interim[1] = balls[j][3]; //On contact using a placeholder array to store the 
						balls[j][2] = balls[l][2]; //the first balls values
						balls[j][3] = balls[l][3];
						balls[l][2] = interim[0];
						balls[l][3] = interim[1];
					}
				}
				if (Math.abs(balls[j][0] + balls[j][2]) > 1.0 - RADIUS){	//detect contact with right and left
					balls[j][2] = -balls[j][2];
					StdAudio.play("sound/boing.au");
				}
				if (Math.abs(balls[j][1] + balls[j][3]) > 1.0 - RADIUS){ 	//detect contact with top and bottom
					balls[j][3] = -balls[j][3];
					StdAudio.play("sound/boing.au");
				}
				balls[j][0] = balls[j][0] + balls[j][2]; //Update balls
				balls[j][1] = balls[j][1] + balls[j][3]; //positions

				StdDraw.setPenColor(StdDraw.GREEN); 
				StdDraw.filledCircle(balls[j][0], balls[j][1], RADIUS);
			}
			StdDraw.show(rate);
		}
	}

}

