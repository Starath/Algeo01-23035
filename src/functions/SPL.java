package functions;

import matrix.*;

public class SPL {
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