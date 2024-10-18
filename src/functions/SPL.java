package functions;

import main.IO;
import matrix.*;

/* Setiap method membuat instansi SPL sebagai nilai yang akan di return. 
   Oleh karena itu, penggunaan di luar Class ini tidak perlu membuat instance baru
   
   Setiap instance memiliki 3 state, oneSolution, infSolution dan noSolution.
   Solusi unik dinyatakan dalam sebuah array solusi.
   Tidak ada solusi cukup dengan state no solution, display "tidak ada solusi" ditulis
   pada Main.
   Solusi dependent (Infinite Solutions) dinyatakan dalam prametrik 
 */

public class SPL {
    boolean oneSolution;
    boolean noSolution;
    boolean infSolution;
    double[] solutions;
    String[] paramSolutions;
    int variables;
    
    // Contructor
    public SPL(int varCount){
        this.oneSolution = false;
        this.noSolution = false;
        this.infSolution = false;
        this.solutions = new double[varCount];
        this.paramSolutions = new String[varCount];
        this.variables = this.solutions.length; 
    }

    public boolean oneSolution(){return oneSolution;}
    public boolean noSolution(){return noSolution;}
    public boolean infSolution(){return infSolution;}
    public void setOneSolution() {oneSolution = true;}
    public void setNoSolution() {noSolution = true;}
    public void setInfSolution() {infSolution = true;}
    public void setSolutions(int Idx, double val){solutions[Idx] = val;}
    public void setParamSolutions(int Idx, String val){paramSolutions[Idx] = val;}
    public double getSolutions(int Idx){return solutions[Idx];}
    public String getParamSolutions(int Idx){return paramSolutions[Idx];}
    public int varCount(){return variables;}
    public void displaySolutions(){
        for(int i = 0; i < variables; i++){
            if(oneSolution){System.out.print("X" + (i+1) + " = " + solutions[i] + " ");}
            else if(infSolution){System.out.print("X" + (i+1) + " = " + paramSolutions[i] + " ");}
            System.out.println("");
        }
    }

    public static SPL gaussJordanElim (Matrix M) {
        Matrix T = MatrixAdv.getRREMatrix(M);
        int row = M.rowCount(), col = M.colCount();
        SPL result = new SPL(col -1);

        if(T.colCutter(col-1).hasZeroRow()){
            if(T.hasZeroRow()) result.setInfSolution();
            else result.setNoSolution();
        }
        // Kalo solusi unik
        if (!result.noSolution && !result.infSolution()){
            result.setOneSolution();
            for(int i = 0; i < row; i++){
                result.solutions[i] = T.getElmt(i, col-1);
            }
        }
        return result;
    }

    public static SPL gaussElim(Matrix M) {
     Matrix T = MatrixAdv.getUpperTriangular(M);
     int row = M.rowCount(), col = M.colCount();
     SPL result = new SPL(col -1);

     if(T.colCutter(col-1).hasZeroRow()){
         if(T.hasZeroRow()) result.setInfSolution();
         else result.setNoSolution();
     }
     // Jika tidak ada row zero

     if (!result.noSolution && !result.infSolution()) {
         result.setOneSolution();

         // Back Substitution
        for (int i = row - 1; i >= 0; i--) {
            double sum = T.getElmt(i,col-1); 

            for (int j = i + 1; j < col-1; j++) {
                sum -= T.getElmt(i, j) * result.solutions[j];
            }
            
            result.solutions[i] = sum / T.getElmt(i, i);
                }
        }
     return result;
    }
    
    public static SPL cramerMethodSPL(Matrix M){
        int row = M.rowCount();
        int col = M.colCount();
        
        Matrix A = M.colCutter(col - 1);
        double[] B = M.getCol(col - 1);
        double detA, detB;
        detA = MatrixAdv.detByGauss(A); // Determinant of A
        
        SPL result = new SPL(col - 1);
        if (row != col -1 || detA == 0) {
            System.out.println("Tidak dapat diselesaikan dengan metode Cramer");
            return result;
        }
        result.setOneSolution();
        int i,j;
        for (j = 0; j < col-1; j++){
            Matrix TempMatrix = A.copyMatrix();
            for (i = 0; i < row; i++){
                TempMatrix.setElmt(i, j, B[i]);
            }
            detB = MatrixAdv.detByGauss(TempMatrix);
            result.setSolutions(j, detB/detA);
        }
        
        return result;
    }
    public static SPL inverseSPL (Matrix mProblem){
        // Asumsikan matriks invertible (dicek di main)
        Matrix A = mProblem.copyMatrix();
        int row = mProblem.rowCount();
        int col = mProblem.colCount();
        double[] tempB = A.getCol(col-1);
        Matrix B = new Matrix(row,1);
        // Copy double[] ke Matrix
        for(int i = 0; i < row; i++){
            B.setElmt(i, 0, tempB[i]);
        }
        // IO.terminalOutputMatrix(B);
        A = A.colCutter(col-1);
        Matrix inverse = MatrixAdv.inverseByAdjoin(A);
        // IO.terminalOutputMatrix(inverse);
        Matrix multiplied = MatrixAdv.multiplyMatrix(inverse, B);
        
        // Assign value hasil ke result SPL
        SPL result = new SPL(col-1);
        result.setOneSolution();
        for(int i = 0; i < row; i++){
            result.setSolutions(i, multiplied.getElmt(i, 0));
        }
        return result;
    }

    // Dipakai pada RREF Form
    public static SPL parametricWriter(Matrix M) {
        int row = M.rowCount(), col = M.colCount() - 1;
        int i, j, k;
        boolean[] isFreeVariable = new boolean[col];
        double[] constantsHolder = new double[col];
        int paramCount = 1;
        SPL result = new SPL(col);

        // Initialize paramSolutions and isFreeVariable;
        for (i = 0; i < col; i++) {
            result.setParamSolutions(i, "");
            isFreeVariable[i] = true;
        }

         // Find all the free variables
        for(i = 0 ; i < row; i++){
             for( j = 0; j< col; j++){
                if(M.getElmt(i, j) == 1 ){
                    isFreeVariable[j] = false;
                    constantsHolder[j] = M.getElmt(i, col);
                    break;
                }
            }
        }
        for (j = 0; j < col; j++){
            
            if (isFreeVariable[j]) {
                // Initialize the free variable with a parameter like t1, t2, etc.
                result.setParamSolutions(j, "t" + paramCount);
                paramCount++; // Increment paramCount here for free variables
            }
         }
         int nonFreeCount = 0;
         for(j = 0; j < col; j++){
            if(!isFreeVariable[j]) nonFreeCount++;
         }
         int[] indexHolder = new int[nonFreeCount];
         int indexHolderIdx = 0;
         for(j = 0; j < col; j++){
            if(!isFreeVariable[j]) {
                indexHolder[indexHolderIdx] = j;
                indexHolderIdx++;}
         }
         // Construct Parametric Form
         indexHolderIdx = 0;
         for (i = 0; i < row && indexHolderIdx < nonFreeCount; i++){
                int nonFreeIdx = indexHolder[indexHolderIdx]; // Get the current non-free variable index
                if (M.getElmt(i, nonFreeIdx) == 1) { // Ensure the current row corresponds to this non-free variable
                    double constant = constantsHolder[nonFreeIdx]; // The constant term for this non-free variable
                    String currSolution = (constant == 0) ? "" : "" + constant;
                
                // For each free variable, print its coefficient with parameter
                for (k = 0; k < col; k++) {
                    if (isFreeVariable[k]) {
                        double coeff = -M.getElmt(i, k);
                        String sign = "+";
                        if (coeff < 0) {
                            coeff = -coeff;
                            sign = "-";
                        }
                        if (coeff != 0){
                            if(!currSolution.isEmpty()) {currSolution += " " + sign + " ";}
                            if (coeff == 1){currSolution += result.paramSolutions[k];}
                            else currSolution += coeff + result.paramSolutions[k];
                        }
                        
                    }
                }
                result.setParamSolutions(nonFreeIdx, currSolution);
                indexHolderIdx++;
                }
        }
        return result;
    }
    public static void main(String[] args) {
        Matrix M = IO.keyboardInputMatrix(4, 6);
        M = MatrixAdv.getRREMatrix(M);
        IO.terminalOutputMatrix(M);
        SPL R = parametricWriter(M);
        R.setInfSolution();
        R.displaySolutions();
    }
} 
