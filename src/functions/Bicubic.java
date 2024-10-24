package functions;

import java.util.Scanner;
import main.IO;
import matrix.Matrix;
import matrix.MatrixAdv;

public class Bicubic {
    // PERSAMAAN y = Xa
    /* Dimana y adalah matrix nilai fungsi, X adalah matrix basis, dan a adalah matrix koefisien */
    public static double bicubicInterpolation(Matrix M, double a, double b){
        if (a == 0 && b == 0) return M.getElmt(1, 1);
        else if (a == 0 && b == 1) return M.getElmt(1, 2);
        else if (a == 1 && b == 1) return M.getElmt(2, 2);
        else if (a == 1 && b == 0) return M.getElmt(2, 1);


        Matrix Mnew = reshapeTo16x1(M);
        Matrix X = getBasisMatrix();
        Matrix A = getAlphaMatrix(X, Mnew);

        double result = 0.0;
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result += A.getElmt(index, 0) * Math.pow(a, i) * Math.pow(b, j);
                index++;
            }
        }

        return result;

    }
    public static Matrix getBasisMatrix(){
        int i,j, xyRow, row, col, x, y;
        Matrix X = new Matrix(16, 16);
        int[][] XY = {
            {0 , 0},
            {1 , 0},
            {0 , 1},
            {1 , 1}
        };
        xyRow = 0;
        col = 0;
        for(row = 0; row < 16; row++){
            col = 0;
            if(xyRow == 4) xyRow = 0;
            x = XY[xyRow][0];
            y = XY[xyRow][1]; 
                for(j = 0; j < 4; j++){
                    for (i = 0; i < 4; i++){
                        if(row < 4) X.setElmt(row, col, Math.pow(x, i) * Math.pow(y, j));
                        else if(row<8) X.setElmt(row, col, i * Math.pow(x, i-1) * Math.pow(y, j));
                        else if(row<12 ) X.setElmt(row, col, j * Math.pow(x, i) * Math.pow(y, j-1));
                        else X.setElmt(row, col, i * j * Math.pow(x, i-1) * Math.pow(y, j-1));
                        if (Double.isNaN(X.getElmt(row, col))) X.setElmt(row, col, 0);
                        col++;
                    }
                }
            xyRow++;
        }
        return X;
    }

    public static Matrix getAlphaMatrix(Matrix X, Matrix y){
        Matrix inverseX = MatrixAdv.inverseByAdjoin(X);
        Matrix alpha = MatrixAdv.multiplyMatrix(inverseX, y);
        return alpha;
    }

    public static Matrix reshapeTo16x1(Matrix M){
        Matrix y = new Matrix(16, 1);        
        int i,j, idxY;
        idxY = 0;
        for(i = 0; i < 4; i++){
            for(j = 0; j < 4; j++){
                y.setElmt(idxY, 0, M.getElmt(i, j));
                idxY++;
            }
        }
        return y;
    }

    public static void main(String[] args) {
        Matrix M = IO.keyboardInputMatrix(4, 4);
        Scanner scan = new Scanner(System.in);
        double a = scan.nextDouble();
        double b = scan.nextDouble();
        System.out.println(bicubicInterpolation(M, a, b));
        Matrix X = getBasisMatrix();
        IO.terminalOutputMatrix(X);
        System.out.println(MatrixAdv.detByGauss(X));
    }
}

