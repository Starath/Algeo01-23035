package functions;

import matrix.*;
import main.IO;

/* Setiap method membuat instansi SPL sebagai nilai yang akan di return. 
   Oleh karena itu, penggunaan di luar Class ini tidak perlua membuat instance baru 
 */

public class SPL {
    boolean oneSolution;
    boolean noSolution;
    boolean infSolution;
    double[] solutions;
    
    // Contructor
    public SPL(int varCount){
        this.oneSolution = false;
        this.noSolution = false;
        this.infSolution = false;
        this.solutions = new double[varCount];
    }

    public boolean oneSolution(){return oneSolution;}
    public boolean noSolution(){return noSolution;}
    public boolean infSolution(){return infSolution;}
    public void setOneSolution() {oneSolution = true;}
    public void setNoSolution() {noSolution = true;}
    public void setInfSolution() {infSolution = true;}
    public void setSolutions(int Idx, double val){solutions[Idx] = val;}

    public static Matrix GaussJordanElim (Matrix M) {

    }

     public static SPL gaussianElimination(Matrix M) {
     Matrix T = MatrixAdv.getUpperTriangular(M);
     int row = M.rowCount();
     int col = M.colCount();
     SPL result = new SPL(col -1);

     if(row < col -1) result.setInfSolution(); // Kasus SPL lebih dikit dari variabel
     if(T.colCutter(col-1).hasZeroRow()){
         if(T.hasZeroRow()) result.setInfSolution();
         else result.setNoSolution();
     }
     // Jika tidak ada row zero
     // Back Substitution
     result.setOneSolution();
     for (int i = row - 1; i >= 0; i--) {
         double sum = T.getElmt(i,col-1); // Use Col - 1 to access the augmented column
         
         // Subtract the known terms (elements to the right of the pivot)
         for (int j = i + 1; j < col-1; j++) {
             sum -= T.getElmt(i, j) * result.solutions[j];
         }
         
         // Solve for the pivot variable
         result.solutions[i] = sum / T.getElmt(i, i);
     }
     return result;
    
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
    public static Matrix inverseElim (Matrix mProblem){
        Matrix B = mProblem.copyMatrix();
        for (int i = 0; i < B.colCount()-2; i++) {
            B.colCutter(i);
        } 
        Matrix inverse = MatrixAdv.inverseByOBE(mProblem.colCutter(mProblem.colCount()-1));
        return MatrixAdv.multiplyMatrix(inverse, B);
    }

} 
