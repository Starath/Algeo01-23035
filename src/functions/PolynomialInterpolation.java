package functions;

import matrix.*;
import java.util.*;

public class PolynomialInterpolation {
    public static Scanner scan;
    public static Matrix KeyboardInputPoints() {
        Matrix mPoints;
        int nPoints;
        scan = new Scanner(System.in);
        System.out.println("Masukkan banyak seluruh titik : ");
        //MASIH PERLU CEK INPUT
        nPoints = scan.nextInt();
        //MASIH PERLU CEK INPUT
        mPoints = new Matrix(nPoints, 2);
        for (int i = 0; i < nPoints; i++) {
            //MASIH PERLU CEK INPUT
            System.out.printf("Masukkan titik x%d dan y%d : ", i + 1, i + 1);
            mPoints.setElmt(i, 0, scan.nextDouble());
            mPoints.setElmt(i, 1, scan.nextDouble());
            //MASIH PERLU CEK INPUT
        }
        return mPoints;
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
    
    public static void main(String[] args) {
        Matrix M;
        M = KeyboardInputPoints();
        MatrixIO.terminalOutputMatrix(M);
        Matrix mProcessed = PointstoMatrix(M);
        MatrixIO.terminalOutputMatrix(mProcessed);
    }
}