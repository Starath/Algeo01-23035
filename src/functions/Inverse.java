package functions;
import matrix.*;

public class Inverse {
    // 1. MENGGUNAKAN ADJOIN
    public static Matrix inverseByAdjoin(Matrix matrix){
        Matrix adjoinMatrix = matrix.copyMatrix();
        double det;
        for (int row = 0; row < adjoinMatrix.rowCount(); row++) {
            for (int col = 0; col < adjoinMatrix.colCount(); col++) {
                if((row+col)%2 == 0){
                    adjoinMatrix.setElmt(row, col, Determinan.detByGauss(matrix.getMinor(row, col)));
                }
                else{
                    adjoinMatrix.setElmt(row, col, ((-1)*Determinan.detByGauss(matrix.getMinor(row, col))));
                }
            }            
        }
        adjoinMatrix = adjoinMatrix.transposeMatrix(); /*adjoinnya */
        det =  Determinan.detByGauss(matrix);
        adjoinMatrix.constantMultiply(1/det); /*adjoinnya */
        return adjoinMatrix;
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
    
    public static void main(String[] args){
        
    }    
}
