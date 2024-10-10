package functions;
import matrix.*;
// import Determinan.*;
/*      on progress
        -hanif         */

public class Inverse {
    // 1. MENGGUNAKAN ADJOIN
    public static Matrix inverseByAdjoin(Matrix matrix){
        Matrix adjoinMatrix = matrix.copyMatrix();
        Matrix inverseMatrix = new Matrix(matrix.rowCount(),matrix.colCount());
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
        adjoinMatrix.transposeMatrix(); /*adjoinnya */
        adjoinMatrix.constantMultiply(1 / Determinan.detByGauss(matrix)); /*adjoinnya */
        inverseMatrix = adjoinMatrix;
        return inverseMatrix;
    }    

    // 2. MENGGUNAKAN OBE
    // kita rehat sejenak
    public Matrix inverseByOBE(Matrix matrix){
        Matrix identityMatrix = matrix.copyMatrix();
        identityMatrix.setIdentityMatrix();
        for (int row = 0; row < matrix.rowCount(); row++) {
            for (int col = 0; col < matrix.colCount(); col++) {
            }
        }            
        return identityMatrix;
    } 
    
    public static void main(String[] args){

    }    
}
