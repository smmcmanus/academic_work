package lab4;

import sedgewick.*;

public class Lecture {

	 public static void main(String[] args) {

         double[][] points = {
                         {0.2, 1.0}, 
                         {0.3, 0.7},
                         {0.7, 0.8},
                         {-0.3, -0.9},
                         {-1.0, -1.0}};
         
         for(int i=0; i<points.length; i++) {
                 double sum =0.0;
                 for(int j = 0; j<2; j++) {
                         sum = sum + points[i][j] *points[i][j]; 
                 }
                 if(sum > 1.0) {
                         System.out.println(points[i][0]+" , "+points[i][1]);
                 }
         }
         
         
 }

}