import java.util.*;

public final class SparseMatrix {
	// Maximum number of elements in matrix 
    int MAX = 100; 
  
    /*Array representation 
    * of sparse matrix 
    * [][0] represents row 
    * [][1] represents col 
    * [][2] represents value 
    */
    private int mat[][] = new int[MAX][3];  
    private final int rowOfOriginalMatrix , colOfOriginalMatrix; // dimensions of matrix
    private  int noOfNonZeroElement;   // total number of elements in matrix 
    Scanner input = new Scanner(System.in);
    
    public SparseMatrix(int r, int c) 
    {   
    	rowOfOriginalMatrix = r; // initialize row 
    	colOfOriginalMatrix = c; // initialize col 
        noOfNonZeroElement = 0; // initialize length to 0 
        setMatrix(); //initialize sparse matrix by taking input from user
        sortMatrix();
    } 
    
    public SparseMatrix(int r, int c,int noOfNonZeroElement, int[][] m){
    	rowOfOriginalMatrix = r; // initialize row 
    	colOfOriginalMatrix = c; // initialize col 
        this.noOfNonZeroElement =noOfNonZeroElement; // initialize length to 0 
        mat = m; //initialize sparse matrix by @param m
    }
    
    // insert elements into sparse matrix 
    private void insertEle(int r, int c, int val) 
    {      // insert row value 
            mat[noOfNonZeroElement][0] = r; 
            // insert col value 
            mat[noOfNonZeroElement][1] = c; 
            // insert element's value 
            mat[noOfNonZeroElement][2] = val; 
  
            // increment number of mat in matrix 
            noOfNonZeroElement++; 
    } 
    
    // method to set elements in matrix
	private void setMatrix(){
		boolean  flag = true;
		int r , c, val;
		while(flag){
			System.out.println("Enter row , col, value (comma separated)(Enter 'end' to stop): ");
			String rowcolval = input.next();
			
			if(rowcolval.equalsIgnoreCase("end")){
				break;
			}
			
	        String[] parts = rowcolval.split(",");
	        //check the string ,string array size >2 ,<1
	        if (parts.length != 3) {
	            System.out.println("Your entering format not valid !! ");
	            //recall the method
	            setMatrix();
	        } else {
	            
	            r = Integer.parseInt(parts[0]);
	            c = Integer.parseInt(parts[1]);
	            val = Integer.parseInt(parts[2]);
	            // invalid entry 
	            if (r > rowOfOriginalMatrix || c > colOfOriginalMatrix) { 
	                System.out.println("Wrong entry, index out of range!!"); 
	            } 
	            else if(val == 0) {
	            	System.out.println("Wrong entry, enter non-zero value only!!"); 
	                }
	            else{
	            	insertEle(r,c,val);
	            	
	            }
	            	
	        }
	        
		}
	}
	
	/*
	 * private helper method to sort sparse matrix
	 */
	private void sortMatrix(){

		for(int i = 0 ; i < noOfNonZeroElement; i++){
			for(int j = 0 ; j < noOfNonZeroElement-i-1 ; j++){
				if (mat[j][0] > mat[j+1][0]) 
				{
                    // swap mat[j+1] and mat[j] 
                    int[] temp = mat[j]; 
                    mat[j] = mat[j+1]; 
                    mat[j+1] = temp; 
                } 
				else if (mat[j][0] == mat[j+1][0]) 
				{
					if(mat[j][1] > mat[j+1][1]){
						// swap mat[j+1] and mat[j] 
	                    int temp[] = mat[j]; 
	                    mat[j] = mat[j+1]; 
	                    mat[j+1] = temp;
					}                     
                } 
			}

		}
	}
	
	public int getMatrixRow(){
		return rowOfOriginalMatrix;
	}
	
	public int getMatrixCol(){
		return rowOfOriginalMatrix;
	}
	
	public int getNumOfElements(){
		return noOfNonZeroElement;
	}
	
	public int getValue(int r, int c){
		return mat[r][c];
	}
	
	public int[][] getMatrix(){
		return mat;
	}
	

}
