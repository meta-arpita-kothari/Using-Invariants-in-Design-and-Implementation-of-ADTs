import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Operations {
	int rowOfOriginalMatrix;
	int colOfOriginalMatrix;
	int i, j;

	/**
	 * Method to transpose the matrix Also sort the tranposed matrix to give
	 * correct output in ascending order
	 * 
	 * @param Sparse
	 *            matrix
	 * @return
	 */
	public SparseMatrix transposeMatrix(SparseMatrix mat) {
		rowOfOriginalMatrix = mat.getMatrixRow();
		colOfOriginalMatrix = mat.getMatrixCol();
		int[][] data = mat.getMatrix();

		int noOfNonZeroElement = mat.getNumOfElements(); // same number of
															// elements
		int[][] result = new int[noOfNonZeroElement][3];

		for (i = 0; i < noOfNonZeroElement; i++) {
			result[i][0] = data[i][1];
			result[i][1] = data[i][0];
			result[i][2] = data[i][2];
		}

		result = sortMatrix(result, noOfNonZeroElement, 3);

		SparseMatrix transposeMatrix = new SparseMatrix(rowOfOriginalMatrix,
				colOfOriginalMatrix, result.length, result); // new matrix with
																// inverse row X
																// col

		return transposeMatrix;

	}

	/**
	 * To check if Sparse matrix is symmetrical
	 * 
	 * @param mat
	 * @return
	 */
	public boolean isSymmetrical(SparseMatrix mat) {
		rowOfOriginalMatrix = mat.getMatrixRow();
		colOfOriginalMatrix = mat.getMatrixCol();
		int[][] matrix = mat.getMatrix();

		int noOfNonZeroElement = mat.getNumOfElements(); // same number of
															// elements

		int[][] transposeMatrix = transposeMatrix(mat).getMatrix();
		if (transposeMatrix != null && matrix != null) {
			for (int i = 0; i < noOfNonZeroElement; i++) {
				for (int j = 0; j < 3; j++) {
					if (transposeMatrix[i][j] != matrix[i][j]) {
						return false;
					}
				}
			}
		}

		return true;

	}

	public void multiplyMatrix(SparseMatrix m1, SparseMatrix m2) {

		Integer[][] result = null;
		if (m1 != null && m2 != null) {
			int r1 = m1.getMatrixRow();
			int c1 = m1.getMatrixCol();
			int r2 = m2.getMatrixRow();
			int c2 = m2.getMatrixCol();
			int nonZeroElements_m1 = m1.getNumOfElements();
			int nonZeroElements_m2 = m2.getNumOfElements();

			if (c1 != r2) {
				// Invalid multiplication
				System.out.println("Can't multiply, " + "Invalid dimensions");

				return;
			}
			// second matrix is transposed
			m2 = transposeMatrix(m2);
			int[][] matrix1 = m1.getMatrix();
			int[][] matrix2 = m2.getMatrix();
			int k = 0;
			int[][] resultMatrix = new int[nonZeroElements_m1
					+ nonZeroElements_m2][3];

			/* Multiplication process */
			for (int i = 0; i < nonZeroElements_m1; i++) {
				for (int j = 0; j < nonZeroElements_m2; j++) {
					if (matrix1[i][1] == matrix2[j][1]) {
						resultMatrix[k][0] = matrix1[i][0];
						resultMatrix[k][1] = matrix2[j][0];
						resultMatrix[k][2] = matrix1[i][2] * matrix2[j][2];
					}
				}
				k++;
			}
			m1 = new SparseMatrix(r1, c2, k, resultMatrix);
			System.out.println("\nResult of Matrix Mulitplication");
			displayMatrix(m1);
		}

	}

	public void addMatrix(SparseMatrix m1, SparseMatrix m2) {

		Integer[][] result = null;
		if (m1 != null && m2 != null) {
			int r1 = m1.getMatrixRow();
			int c1 = m1.getMatrixCol();
			int r2 = m2.getMatrixRow();
			int c2 = m2.getMatrixCol();
			int nonZeroElements_m1 = m1.getNumOfElements();
			int nonZeroElements_m2 = m2.getNumOfElements();

			if (r1 != r2 && c1 != c2) {
				// Invalid addition
				System.out.println("Can't add, " + "Invalid dimensions");

				return;
			}

			int[][] matrix1 = m1.getMatrix();
			int[][] matrix2 = m2.getMatrix();

			int k = 0;
			int[][] resultMatrix = new int[nonZeroElements_m1
					+ nonZeroElements_m2][3];
			ArrayList<Integer> temp = new ArrayList<Integer>();
			HashSet<ArrayList<Integer>> setToMaintain = new HashSet<ArrayList<Integer>>();

			boolean flag = false; // to check if element from matrix one is not
									// added twice
			/* addition process */
			for (int i = 0; i < nonZeroElements_m1; i++) {
				for (int j = 0; j < nonZeroElements_m2; j++) {
					flag = false;
					temp.clear();
					if (matrix1[i][0] == matrix2[j][0]) {
						if (matrix1[i][1] == matrix2[j][1]) {
							resultMatrix[k][0] = matrix1[i][0];
							resultMatrix[k][1] = matrix2[i][1];
							resultMatrix[k][2] = (matrix1[i][2] + matrix2[j][2]);
							System.out.println("\nhey in j equal"
									+ resultMatrix[k][2] + "   k:" + k);
						} else {
							if (!flag) {
								resultMatrix[k][0] = matrix1[i][0];
								resultMatrix[k][1] = matrix1[i][1];
								resultMatrix[k][2] = matrix1[i][2];
								System.out.println("\nhey in j -if i "
										+ resultMatrix[k][2] + "   k:" + k);
								k++;
								flag = true;

							}
							temp.add(matrix2[j][0]);
							temp.add(matrix2[j][1]);
							temp.add(matrix2[j][2]);
							setToMaintain.add(temp);
						}
						System.out.println("\nhey in j " + resultMatrix[k][2]
								+ "   k:" + k);
					}
				}
				System.out.println("\nhey out j " + resultMatrix[k][2]
						+ "   k:" + k);
				k++;
			}

			for (ArrayList<Integer> ele : setToMaintain) {
				resultMatrix[k][0] = ele.get(0);
				resultMatrix[k][1] = ele.get(1);
				resultMatrix[k][2] = ele.get(2);
			}

			resultMatrix = sortMatrix(resultMatrix, k, 3);
			m1 = new SparseMatrix(r1, c2, k, resultMatrix);
			System.out.println("\nResult of Matrix Addition");
			displayMatrix(m1);
		}

	}

	/*
	 * private helper method to sort sparse matrix
	 */
	private int[][] sortMatrix(int[][] mat, int r, int c) {

		for (i = 0; i < r; i++) {
			for (j = 0; j < r - i - 1; j++) {
				if (mat[j][0] > mat[j + 1][0]) {
					// swap mat[j+1] and mat[j]
					int[] temp = mat[j];
					mat[j] = mat[j + 1];
					mat[j + 1] = temp;
				} else if (mat[j][0] == mat[j + 1][0]) {
					if (mat[j][1] > mat[j + 1][1]) {
						// swap mat[j+1] and mat[j]
						int temp[] = mat[j];
						mat[j] = mat[j + 1];
						mat[j + 1] = temp;
					}
				}
			}

		}
		return mat;
	}

	/**
	 * display sparse matrix
	 * 
	 * @param mat
	 */
	public void displayMatrix(SparseMatrix mat) {
		System.out.println("\n");

		System.out.println("Row\tCol\tValue");
		for (int i = 0; i < mat.getNumOfElements(); i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(mat.getValue(i, j) + "    ");
			}
			System.out.println("\n");
		}
		System.out.println("\n");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Operations obj = new Operations();
		int[][] m1 = { { 1, 2, 10 }, { 1, 4, 12 }, { 3, 3, 5 }, { 4, 1, 15 },
				{ 4, 2, 12 } };

		int[][] m2 = { { 1, 3, 8 }, { 2, 4, 23 }, { 3, 3, 9 }, { 4, 1, 20 },
				{ 4, 2, 25 } };

		SparseMatrix mat1 = new SparseMatrix(4, 4, m1.length, m1);
		SparseMatrix mat2 = new SparseMatrix(4, 4, m2.length, m2);
		obj.displayMatrix(mat1);

		// transpose operation
		SparseMatrix result = obj.transposeMatrix(mat1);
		System.out.println("\nTranspose of first matrix");
		obj.displayMatrix(result);
		SparseMatrix result2 = obj.transposeMatrix(mat2);
		System.out.println("\nTranspose of second matrix");
		obj.displayMatrix(result2);

		// multiplication operation- display is inside the function only
		obj.multiplyMatrix(mat1, mat2);
		System.out.println("\n");

		// addition operation- display is inside the function only
		obj.addMatrix(mat1, mat2);

	}

}
