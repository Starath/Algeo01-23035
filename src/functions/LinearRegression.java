// package functions;

// import matrix.*;
package functions;
import matrix.*;

public class LinearRegression {
    /* ============== 1. MULTIPLE LINEAR REGRESSION =================*/
    public static SPL MultipleLinearReg(Matrix matrixData, int nPeubah, int totalSampel){
    /*asumsi matrix data terdiri dari data2 Xi dan Y. colCount = (nPeubah + 1) */
    /* KAMUS LOKAL */
    int i, j;
    Matrix matrixExtended = MatrixExtender(matrixData);
    
    /*Metode Normal Estimation Equation (NEE) for Multiple Linear Regression */
    Matrix matrixNEE = new Matrix(nPeubah + 1, nPeubah + 2); 
    int rowNEE = matrixNEE.rowCount();
    int colNEE = matrixNEE.colCount();
    
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
        
        return (SPL.gaussElim(matrixNEE));
    }
    
    /* ============== 2. MULTIPLE CUADRATIC REGRESSION =================*/
    public static SPL MultipleCuadraticReg(Matrix matrixData, int nPeubah, int totalSampel){
        /* KAMUS LOKAL */
        int rowCuadratic = totalSampel;
        int colCuadratic = 2*nPeubah + combination(nPeubah, 2) + 1;
        Matrix matrixCuadratic = new Matrix(rowCuadratic, colCuadratic); 
        int i, j, k, l, index;
        double value;
        
        /*Membuat matriks kuadratik dulu dari raw data */
        for (i = 0; i < totalSampel; i++) {

            for (j = 0; j < colCuadratic; j++) {
                /*variabel linear */
                index = nPeubah;
                if (j < nPeubah) {
                    matrixCuadratic.setElmt(i, j, matrixData.getElmt(i, j));
                }
                
                /*variabel kuadratik */
                index += nPeubah;
                if (j < index) {
                    for (k = 0; k < nPeubah; k++) {
                        matrixCuadratic.setElmt(i, j, Math.pow((matrixData.getElmt(i, k)), nPeubah));
                    }
                }
                
                /*variabel interaksi */
                index += combination(nPeubah, 2);
                if (j < index) {
                    for (k = 0; k < nPeubah; k++) {
                        for (l = k + 1; l < nPeubah; l++) {
                            value = matrixData.getElmt(i, k) * matrixData.getElmt(i, l);
                            matrixCuadratic.setElmt(i, j, value);
                        }
                    }                    
                }
                
                /*variabel Y */
                int colOfY = matrixData.colCount() - 1;
                matrixCuadratic.setElmt(i, j, matrixData.getElmt(i, colOfY));
                index += 1;
                if (j < index) {
                    for (k = 0; k < nPeubah; k++) {
                    }
                }
            }
        }

        SPL hasilSPL = MultipleLinearReg(matrixCuadratic, nPeubah, totalSampel);
        return hasilSPL;
    }


    /* ============== 3. HELPER METHOD =================*/

    public static double sumValue(Matrix matrixData, int x1, int x2, int totalSampel){
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

    public static int factorial(int angka) {
        int result = 1;
        for (int i = 1; i <= angka; i++) {
            result *= i;
        }
        return result;
    }

    // Fungsi untuk menghitung kombinasi C(n, r)
    public static int combination(int n, int r) {
        // Menggunakan rumus C(n, r) = n! / (r! * (n - r)!)
        return factorial(n) / (factorial(r) * factorial(n - r));
    }
    
}