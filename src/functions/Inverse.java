package functions;
import matrix.*;
// import Determinan.*;
/*      on progress
        -hanif         */

public class Inverse {
    // 1. MENGGUNAKAN ADJOIN
    public static Matrix inverseByAdjoin(Matrix matrix){
        Matrix adjoinMatrix = matrix.copyMatrix();
        // Matrix inverseMatrix = new Matrix(matrix.rowCount(),matrix.colCount());
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
        Matrix M = new Matrix(3, 3);
        M.keyboardInputMatrix();
        Matrix M2 = M.copyMatrix();
        (inverseByAdjoin(M2)).terminalOutputMatrix();
        System.out.print("\n");
        System.out.print("\n");

    }    
}
