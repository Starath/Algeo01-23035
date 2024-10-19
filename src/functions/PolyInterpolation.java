package functions;

import matrix.*;
import main.*;
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

    public static SPL InterpolationFunction(Matrix otwSolved) {
        SPL solusi = new SPL(otwSolved.rowCount() - 1);
        solusi = SPL.gaussJordanElim(otwSolved);
        return solusi;
    }

    public static void OutputInterpolation(SPL solusi) {
        int lenSolusi = solusi.variables;
        System.out.printf("P(X) = ");
        for (int index = lenSolusi - 1; index >= 0; index--) {
            if (index == 0) {
                System.out.print("(" + solusi.solutions[index] + ")");
                break;
            }
            if (index == 1) {
                System.out.print("(" + solusi.solutions[index] + ")" + "x + ");
                continue;
            }
            System.out.print("(" + solusi.solutions[index] + ")" + "x^" + index + " + ");
        }
    }

    public static double InterpolationFX(SPL solusi, double Absis) {
        double Ordinat = 0;
        for (int index = 0; index < solusi.variables; index++) {
            Ordinat += solusi.solutions[index] * Math.pow(Absis,index);
        }
        return Ordinat;
    }

    public static void main(String[] args) {
        Matrix mPoints;
        mPoints = KeyboardInputPoints();
        Matrix otwSolved = PointstoMatrix(mPoints);
        IO.terminalOutputMatrix(otwSolved);
        otwSolved = MatrixAdv.getRREMatrix(otwSolved);
        IO.terminalOutputMatrix(otwSolved);
        SPL solusi = InterpolationFunction(otwSolved);
        OutputInterpolation(solusi);
        System.out.println(InterpolationFX(solusi, 3));

    }

}
