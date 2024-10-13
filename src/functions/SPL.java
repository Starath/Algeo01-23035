package functions;

import matrix.*;

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
    public void setSolutions(int Idx, int val){solutions[Idx] = val;}

    public static Matrix GaussJordanElim (Matrix mProblem) {
        for (int i = 0; i < mProblem.rowCount(); i++) {
            if (mProblem.isPivotZero(i)) {
                mProblem.searchPivot(i);
            }
            if (mProblem.isPivotZero(i)) { // Kalau masih 0
                continue;
            }
            mProblem.OBEReduksi(i);
        }
        return mProblem;
    }


    public static void main(String[] args) {
        Matrix M = new Matrix(2, 3);
        Matrix MHasil = new Matrix(2, 3);
        MatrixIO.keyboardInputMatrix(M);
        System.out.println(M.isPivotZero(0));
        MHasil = GaussJordanElim(M);
        MatrixIO.terminalOutputMatrix(MHasil);
    }
} 