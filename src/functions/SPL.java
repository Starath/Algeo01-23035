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

    public static Matrix GaussJordanElim (Matrix mProblem) {

    }

    public static Matrix GaussElim (Matrix mProblem) {

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
    public static Matrix inverseElim (Matrix mProblem){
        Matrix B = mProblem.copyMatrix();
        for (int i = 0; i < B.colCount()-2; i++) {
            B.colCutter(i);
        } 
        Matrix inverse = MatrixAdv.inverseByOBE(mProblem.colCutter(mProblem.colCount()-1));
        return MatrixAdv.multiplyMatrix(inverse, B);
    }
    public static void main(String[] args) {
        Matrix M;
        M =IO.keyboardInputMatrix(3, 4);
        IO.terminalOutputMatrix(M);
        Matrix mHasil;
        mHasil = GaussElim(M);
        IO.terminalOutputMatrix(mHasil);
    }
} 