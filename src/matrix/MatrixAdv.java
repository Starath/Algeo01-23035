package matrix;
import main.IO;

public class MatrixAdv {

    public static Matrix multiplyMatrix(Matrix M1, Matrix M2){
        double newElm;
        Matrix Mresult = new Matrix(M1.rowCount(),M2.colCount());
        int i, j, k;
        for (i = 0; i < M1.rowCount(); i++){
            for(j = 0; j < M2.colCount(); j++){
                newElm = 0;
                for(k = 0; k < M1.colCount(); k++){
                    newElm += M1.getElmt(i, k) * M2.getElmt(k,j);
                }
                Mresult.setElmt(i, j, newElm);
            }
        }
        return Mresult;
    }
    
    public static Matrix getMinor(Matrix M, int row, int col){
        Matrix minorMatrix = M.copyMatrix();
        minorMatrix = minorMatrix.rowCutter(row);
        minorMatrix = minorMatrix.colCutter(col);
        return minorMatrix;
    }

    public static Matrix getCofactorMatrix(Matrix M) {
        // Create new Matrix (pass by value)
        int i,j;
        Matrix cofactorMat = new Matrix(M.rowCount(), M.colCount());
        for (i = 0; i< M.rowCount(); i++){
            for (j = 0; j< M.colCount(); j++){
                cofactorMat.setElmt(i, j, detByGauss(getMinor(M,i, j)) * ((i+j) % 2 == 0 ? 1 : -1));
            }
        }
        return cofactorMat;
    }

    public static Matrix getAdjoinMatrix(Matrix M){
        // Create new Matrix (pass by value)
        return getCofactorMatrix(M).transposeMatrix();
    }

    /* ======================  DETERMINAN  ===================================== */
    public static double detByGauss (Matrix M){
        Matrix matrix = M.copyMatrix();
        int  row, col;
        double det = 1;
        for(col = 0; col < matrix.colCount(); col++){                
            row = col;
            while((row < matrix.rowCount()) && (matrix.getElmt(row, col) == 0)){ /*asumsikan matriks persegi */
                row++;
            }

            if (row < matrix.rowCount()){ /*Mencari indeks tidak nol pertama */
                if (row != col){
                    matrix.swapRows(row, col);
                    det *= (-1);
                }
                Matrix.OBEReduksi(matrix, row, col);/*membuat angka di bawah pivot menjadi nol */
                det *= matrix.getElmt(col, col);
            }
            else{
                return 0; /*kolom nol semua -> indeks out of length -> det = nol*/
            }
        }
        return det;
    }

    public static double detByCofactor(Matrix M){
        double det = 0;
        int i;
        Matrix cofactor = getCofactorMatrix(M);
        for (i = 0; i < M.rowCount(); i++){
            det += M.getElmt(i, 0) * cofactor.getElmt(i, 0);
        }
        return det;
    }

    /* ======================  INVERSE  ===================================== */
    // 1. MENGGUNAKAN ADJOIN
    public static Matrix inverseByAdjoin(Matrix M){
        double det = detByGauss(M);
        Matrix invers = getAdjoinMatrix(M);
        invers.constantMultiply(1/det);
        return invers;
    }    
    
    // 2. MENGGUNAKAN OBE
    public static Matrix inverseByOBE(Matrix matrix){
        Matrix inverseMatrix = new Matrix(matrix.rowCount(),matrix.colCount());
        Matrix identityMatrix = matrix.copyMatrix();
        identityMatrix.setIdentityMatrix();
        int i, row, col;
        double scalar;
        for (col = 0; col < matrix.colCount(); col++) {
            row = col;
            while((row < matrix.rowCount()) && (matrix.getElmt(row, col) == 0)){ /*asumsikan matriks persegi */
                row++;
            }

            if (row < matrix.rowCount()){ /*Mencari indeks tidak nol pertama */
                matrix.swapRows(row, col);
                identityMatrix.swapCols(row, col);
            }
            else{
                return inverseMatrix;
            }
            for(i = 0; i < matrix.rowCount(); i++){ /*membuat angka di bawah pivot menjadi nol */
                if (i != col){
                    scalar = (matrix.getElmt(i, col) / matrix.getElmt(col, col)) * (-1);
                    matrix.addRows(i, col, scalar);
                    identityMatrix.addRows(i, col, scalar);

                }
            }
        }            
        return matrix;
    }
    public static void main(String[] args) {
        Matrix M = new Matrix(3,3);
        M = IO.keyboardInputMatrix(M.rowCount(), M.colCount());
        IO.terminalOutputMatrix(M);
        System.out.println(detByGauss(M));

    }
}
