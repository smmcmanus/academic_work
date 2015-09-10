package lab1;
import java.util.Arrays;

public class Sort {
    
    //================================= sort =================================
    //
    // Input: array A of XYPoints refs 
    //        lessThan is the function used to compare points
    //
    // Output: Upon completion, array A is sorted in nondecreasing order
    //         by lessThan.
    //
    // DO NOT USE ARRAYS.SORT.  WE WILL CHECK FOR THIS.  YOU WILL GET A 0.
    // I HAVE GREP AND I AM NOT AFRAID TO USE IT.
    //=========================================================================
     
    public static void msort(XYPoint[] A, Comparator lessThan) {
		int len = A.length;
		mergeSort(A, len, lessThan);
		}
        
    public static XYPoint[] mergeSort(XYPoint[] A, int len, Comparator lessThan) {
		if(len > 1){
			int splitter = (len / 2) - 1;
			
			//Recursively divides A until it is split into 1 item pieces.
			XYPoint[] L = Arrays.copyOfRange(A, 0, splitter + 1);
			XYPoint[] R = Arrays.copyOfRange(A, splitter + 1 , len);

			L = mergeSort(L, L.length, lessThan);  
        	R = mergeSort(R, R.length, lessThan);
        	
        	//Combine. Iterates through left and right halves and combines back into A, comparing piece by piece.
			int iR = 0;
			int iL = 0;
			int iA = 0;
			while(iR < R.length && iL < L.length){
				if(lessThan.comp(L[iL],R[iR])){
					A[iA] = L[iL];
					iA++;
					iL++;
				}
				else{
					A[iA] = R[iR];
					iA++;
					iR++;
				}
			}
			//If left or right are not empty, copy the rest into A.
			while(iR < R.length){
				A[iA] = R[iR];
				iA++;
				iR++;
			}
			while(iL < L.length){
				A[iA] = L[iL];
				iA++;
				iL++; 
			}
		}
	return A;
	}
}
