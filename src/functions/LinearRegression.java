// package functions;

// import matrix.*;
package functions;
import matrix.*;
public class LinearRegression {
    public static void MultipleRegression(Matrix matrixData, int nPeubah, int totalSampel){
    /*asumsi matrix data terdiri dari data2 Xi dan Y. colCount = (nPeubah + 1) */
    /* KAMUS LOKAL */
        int i, j;
        Matrix matrixExtended = MatrixExtender(matrixData);
        Matrix matrixNEE = new Matrix(nPeubah + 1, nPeubah + 2); 
        int rowNEE = matrixNEE.rowCount();
        int colNEE = matrixNEE.colCount();
        SPL hasil = new SPL(nPeubah); /*Normal Estimation Equation for Multiple Linear Regression */
        
        for (i = 0; i < rowNEE; i++) {
            for (j = 0; j < colNEE; j++) {

                if ((i == 0) && (j == 0)){
                    matrixNEE.setElmt(i, j, nPeubah);
                }
                if ((i == 0) && (j != 0)){
                    matrixNEE.setElmt(i, j, sumValue(matrixExtended, 0, j, totalSampel));
                }
                if ((i != 0) && (j == 0)){
                    matrixNEE.setElmt(i, j, sumValue(matrixExtended, 0, i, totalSampel));
                }
                else{
                    matrixNEE.setElmt(i, j, sumValue(matrixExtended, i, j, totalSampel));

                }
            }
        }

        hasil = SPL.gaussElim(matrixNEE);
    }
    

    public static int sumValue(Matrix matrixData, int x1, int x2, int totalSampel){
        int i;
        double sum = 0;
        double[] array1 = matrixData.getCol(x1);
        double[] array2 = matrixData.getCol(x2);
        for (i = 1; i <= totalSampel; i++) {
            sum += array1[i] * array2[i];
        }
        return sum;
    }    

    public static Matrix MatrixExtender(Matrix matrix){
        Matrix matrixExtended = new Matrix(matrix.rowCount(), matrix.colCount() + 1);
        int i, j;
        int row = matrixExtended.rowCount();
        int col = matrixExtended.colCount();
        for (i = 0; i < row; i++){
            for (j = 0; j < col ; j++){
                if(j == 0){
                    matrixExtended.setElmt(i, j, 1);
                }                
                else{
                    matrixExtended.setElmt(i, j, matrix.getElmt(i, j-1));
                }
            }
        }
        return matrixExtended;
    }

}