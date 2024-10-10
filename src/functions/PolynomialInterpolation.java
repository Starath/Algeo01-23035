package functions;

import matrix.Matrix;

public class PolynomialInterpolation {

    public static void InputPoints() {
        
    }

    public static Matrix PointstoMatrix(Matrix mPoints) {
        // Mengubah matrix dari input point-point menjadi matrix interpolasi
        Matrix mHasil;
        mHasil = new Matrix(mPoints.rowCount(), mPoints.rowCount() + 1);
        for (int i = 0; i < mHasil.rowCount(); i++) {
            for (int j = 0; j < mHasil.colCount() - 1; j++) {
                mHasil.setElmt(i, j, Math.pow(mPoints.getElmt(i, 0), j));
            }
        }
        for (int i = 0; i < mHasil.rowCount(); i++) {
            mHasil.setElmt(i, mHasil.colCount() - 1, mPoints.getElmt(i, 1));
        }
        return mHasil;
    }
}