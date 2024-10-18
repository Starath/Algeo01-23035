// package functions;

// import matrix.*;
package functions;
import java.util.*;
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
        // return (matrixNEE);
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
            /*variabel linear */
            for (j = 0; j < nPeubah; j++) {
                matrixCuadratic.setElmt(i, j, matrixData.getElmt(i, j));
            }
            index = nPeubah;
            
            /*variabel kuadratik */
            k = 0;
            for (j = index; j < index + nPeubah; j++) {
                matrixCuadratic.setElmt(i, j,  Math.pow((matrixData.getElmt(i, k)), nPeubah));
                k++;
            }
            index += nPeubah;

            /*variabel interaksi */
            for (j = index; j < index + combination(nPeubah, 2); j++) {
                for (k = 0; k < nPeubah; k++) {
                    for (l = k + 1; l < nPeubah; l++) {
                        value = matrixData.getElmt(i, k) * matrixData.getElmt(i, l);
                        matrixCuadratic.setElmt(i, j, value);
                        j++;
                    }
                }                    
            }
            index += combination(nPeubah, 2);
            
            /*variabel Y */
            int indexOfY = matrixData.colCount() - 1;
            matrixCuadratic.setElmt(i, index, matrixData.getElmt(i, indexOfY));
        }

        SPL hasilSPL = MultipleLinearReg(matrixCuadratic, colCuadratic-1, totalSampel);
        return hasilSPL;
    }


    /* ============== 3. HELPER METHOD =================*/

    public static double sumValue(Matrix matrixData, int x1, int x2, int totalSampel){
        int i;
        double sum = 0;
        double[] array1 = matrixData.getCol(x1);
        double[] array2 = matrixData.getCol(x2);
        for (i = 0; i < totalSampel; i++) {
            sum += array1[i] * array2[i];
        }
        return sum;
    }    
    
    public static Matrix MatrixExtender(Matrix matrix){
        /*menambah kolom berisikan angka satu di sebelah kiri matrix */
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

    public static Scanner scan;

    public static double inputValueReg(SPL spl){    
        scan = new Scanner(System.in);
        double inputX, result = 0;

        // Meminta pengguna untuk mengisi nilai-nilai array
        System.out.println("Masukkan nilai-nilai X:");
        for (int i = 1; i < spl.varCount(); i++) {
            System.out.print("Nilai X" + (i) + " : ");
            inputX = scan.nextDouble();  
            result += inputX * spl.getSolutions(i);
        }
        return result;
    }

    public static void displaySPL(SPL spl){
        System.out.print("Y" + " = ");
        for(int i = 0; i < spl.varCount(); i++){
            System.out.print(spl.getSolutions(i) + "X" + (i+1));
        }
    }


    public static void main() {
        Matrix matrix = new Matrix(20, 4); 
        SPL splByLinearReg = new SPL(3);
        SPL splByCuadraticReg = new SPL(9);
        int i, j;
        double inputX = 0;
        double[][] elements =   {
            {72.4, 76.3, 29.18, 0.90}, {41.6, 70.3, 29.35, 0.91}, {34.3, 77.1, 29.24, 0.96}, {35.1, 68.0, 29.27, 0.89},
            {10.7, 79.0, 29.78, 1.00}, {12.9, 79.7, 29.80, 1.10}, {8.3, 66.8, 29.69, 1.15}, {20.1, 76.9, 29.82, 1.03},
            {72.2, 77.7, 29.09, 0.77}, {24.0, 67.7, 29.60, 1.07}, {23.2, 76.8, 29.38, 1.07}, {47.4, 86.6, 29.35, 0.94},
            {31.5, 79.6, 29.63, 1.10}, {10.6, 68.7, 29.43, 1.10}, {11.2, 80.6, 29.48, 1.10}, {73.3, 73.6, 29.38, 0.91},
            {75.4, 74.9, 29.28, 0.87}, {75.0, 78.7, 29.09, 0.88}, {107.4, 86.8, 29.03, 0.82}, {54.9, 70.9, 29.37, 0.95}
        };

        for (i = 0; i < 20; i++) {
            for (j = 0; j < 4; j++) {
                matrix.setElmt(i, j, elements[i][j]);
            }
        }
        splByLinearReg = MultipleLinearReg(matrix, 3, 10);
        splByCuadraticReg = MultipleCuadraticReg(matrix, 3, 10);
        // Matrix mCuadratic = MultipleCuadraticReg(matrix, 3, 20);
        // Matrix mLinear = MultipleLinearReg(matrix, 3, 20);
        // IO.terminalOutputMatrix(mCuadratic);
        // System.out.println("\n");
        // IO.terminalOutputMatrix(mLinear);
        splByLinearReg.displaySolutions();
        System.out.println("\n");
        splByCuadraticReg.displaySolutions();
        inputValueReg(splByLinearReg);
        displaySPL(splByLinearReg);
    }

}