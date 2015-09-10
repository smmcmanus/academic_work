package lab1;
import java.util.Arrays;

public class ClosestPair {

    public final static double INF = java.lang.Double.POSITIVE_INFINITY;

    /** 
     * Given a collection of points, find the closest pair of point and the
     * distance between them in the form "(x1, y1) (x2, y2) distance"
     *
     * @param pointsByX points sorted in nondecreasing order by X coordinate
     * @param pointsByY points sorted in nondecreasing order by Y coordinate
     * @return Result object containing the points and distance
     */
    static Result findClosestPair(XYPoint pointsByX[], XYPoint pointsByY[]) {
    	int len = pointsByX.length;
    	//If array is empty
    	if(len == 0){
    		XYPoint one = new XYPoint(0, 0);
    		XYPoint two = new XYPoint(99999, 99999);
    		Result empty = new Result(one, two, INF);
    		return empty;
    	}
    	//Array of size one
    	if(len == 1){
    		Result rMin = new Result(pointsByX[0], pointsByX[0], INF);
    		return rMin;
    	}
    	//Checks array for two of the same point, meaning distance 0
    	for(int i = 0; i < len - 1; i++) {
            if(pointsByX[i].x == pointsByX[i+1].x && pointsByX[i].y == pointsByX[i+1].y){
                Result rMin = new Result(pointsByX[i], pointsByX[i + 1], 0.0);
                return rMin;
            }
        }
        //Start Recursion
        Result rMin = split(pointsByX, pointsByY, len); 
		return rMin;
    }
    /** 
     *Finds smallest distance between points using a brute force algorithm.
     *
     * @param pointsByX points sorted in nondecreasing order by X coordinate
     * @return Result object containing the points and distance
     */
    public static Result findClosestPairBruteForce(XYPoint pointsByX[]){
    	Result ans = new Result(pointsByX[0], pointsByX[0], INF);
    	for(int i = 0; i < pointsByX.length; i++){
        	for (int j = i + 1; j < pointsByX.length; j++){
            	if(pointsByX[i].dist(pointsByX[j]) < ans.dist){
                	ans = new Result(pointsByX[i], pointsByX[j], pointsByX[i].dist(pointsByX[j])); 
                }
            }
        }
    	return ans;
    }
    /** 
     *Finds smallest distance over the split between left and right halves.
     *
     * @param strip points sorted within min distance of the split
     * @param delta Result holding the benchmark minimum points for the strip
     * @param strip points sorted within min distance of the split
     * @param amount of points occupying strip
     * @return Result object containing the points and distance
     */
    public static Result ystrip(XYPoint strip[], Result delta, int size){
    	double delt = delta.dist;
    	int k = 0;
    	Result ans = new Result(strip[0], strip[0], INF); 
    	if(size == 1){
    		return ans;
    	} 
		for (int j = 0; j < size; j++){
			k = j + 1;
			while(k < size && (strip[k].y - strip[j].y) < delt){
				if (strip[j].dist(strip[k]) < delt){
					delt = strip[j].dist(strip[k]); 
					ans = new Result(strip[j], strip[k], delt);
				} 
			k++;
 			}
 		}
		return ans;
    }
    /** 
     *Divide and conquer portion. Divides array, finds distances at leaves and then compares 
     *and combines the smaller part together.
     *
     * @param pointsByX points sorted in nondecreasing order by X coordinate
     * @param pointsByY points sorted in nondecreasing order by Y coordinate
     * @param lenX int length of pointsByX
     * @return Result object containing the points and distance
     */
    public static Result split(XYPoint pointsByX[], XYPoint pointsByY[], int lenX){
    	//Distance at leaves when 1 point left.
    	if(lenX < 2){
    		Result min = new Result(pointsByX[0], pointsByX[0], INF);
    		return min;
    	}
    	//Returns distance at leaves
    	if(lenX == 2){
			Result min = new Result(pointsByX[0], pointsByX[1], pointsByX[0].dist(pointsByX[1]));
			return min;
		}
		Comparator lessThan = new XComparator();
		//Finds center of array and divides into two subarrays.
		int splitter = lenX / 2;
		XYPoint[] leftX = Arrays.copyOfRange(pointsByX, 0, splitter + 1);
		XYPoint[] rightX = Arrays.copyOfRange(pointsByX, splitter + 1 , lenX);
		XYPoint[] leftY = new XYPoint[leftX.length];
		XYPoint[] rightY = new XYPoint[rightX.length];
		int l = 0; 
		int r = 0;
		//Creates arrays sorted by Y of values in pointsByX subarrays
		for (int i = 0; i < lenX ; i++){
			if (lessThan.comp(pointsByY[i], pointsByX[splitter + 1])){
				leftY[l] = pointsByY[i];
				l++;
			}
			else{
				rightY[r] = pointsByY[i];
				r++;
			}
		}
		//Recursive steps
		Result minL = split(leftX, leftY, leftX.length);
		Result minR = split(rightX, rightY, rightX.length);
		//Finds minimum Result of left and right sides
		Result min = comp(minR, minL);
		double delta = min.dist;
		int sizeStrip = 0;
		//Constructs Strip array
		XYPoint[] strip = new XYPoint[pointsByY.length];
		for(int i = 0; i < pointsByY.length; i++){
			if(pointsByY[i].x - pointsByY[splitter].x <= delta){
				strip[sizeStrip] = pointsByY[i];
				sizeStrip++;
			}
		}
		//Finds minimum result within strip array
		Result stripped = ystrip(strip, min, sizeStrip);
		//Compare min of left and right and min of strip. Returns smaller distance Result.
		Result minimized = comp(stripped, min);		
		return minimized;
	}
	/** 
     *Compares two results, and returns the one with a smaller distance;
     *
     * @param one Result
     * @param two Other Result
     * @return Result object containing the points and smaller distance
     */
	public static Result comp(Result one, Result two){
		//If same distance, return result with lower sum of x values. Y values if x sum is equal.
		if(one.dist == two.dist){
			if((one.p1.x) < (two.p1.x)){
				return one;
			}
			else if((one.p1.x) > (two.p1.x)){
				return two;
			}
			else if((one.p1.y) < (two.p1.y)){
				return one;
			}
			else if((one.p1.y) > (two.p1.y)){
				return two;
			}
			else if((one.p1.x == two.p1.x) && (one.p1.y == two.p1.y)){
				if((one.p2.x) < (two.p2.x)){
					return one;
				}
				else if((one.p2.x) > (two.p2.x)){
					return two;
				}
				else if((one.p2.y) < (two.p2.y)){
					return one;
				}
				else if((one.p2.y) > (two.p2.y)){
					return two;
				}
			}
		}
		else if(one.dist > two.dist){
			return two;
		}
		return one;
	}
	
    static int abs(int x) {
        if (x < 0) {
            return -x;
        } 
        else {
            return x;
        }
    }
}
