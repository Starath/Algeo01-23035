// package functions;

// import matrix.*;
package functions;
import java.util.*;
import main.*;
import matrix.*;

public class Regression {
    /* ============== 1. MULTIPLE LINEAR REGRESSION =================*/
    public static SPL MultipleLinearReg(Matrix matrixData, int nPeubah, int totalSampel){
    /*asumsi matrix data terdiri dari data2 Xi dan Y. colCount = (nPeubah + 1) */
    /* KAMUS LOKAL */
    int i, j;
    Matrix matrixOne = new Matrix(totalSampel, 1);

    Matrix matrixExtended = MatrixExtender(matrixData, matrixOne, 0);
    
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
            j = index;
            for (k = 0; k < nPeubah; k++) {
                for (l = k + 1; l < nPeubah; l++) {
                    value = matrixData.getElmt(i, k) * matrixData.getElmt(i, l);
                    matrixCuadratic.setElmt(i, j, value);
                    j++;
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

    public static Matrix valueOfXCuadratic(Matrix matrixX, int nPeubah){
        /* KAMUS LOKAL */
        int rowCuadratic = 2*nPeubah + combination(nPeubah, 2) + 1;
        Matrix matrixXCuadratic = new Matrix(rowCuadratic, 1); 
        int j, k, l, index;
        double value;
        
        /*Membuat matriks kuadratik dulu dari raw data */
        /*variabel linear */
        for (j = 0; j < nPeubah; j++) {
            matrixXCuadratic.setElmt(j, 0, matrixX.getElmt(j, 0));
        }
        index = nPeubah;
        
        /*variabel kuadratik */
        k = 0;
        for (j = index; j < index + nPeubah; j++) {
            matrixXCuadratic.setElmt(j, 0,  Math.pow((matrixX.getElmt(k, 0)), nPeubah));
            k++;
        }
        index += nPeubah;

        /*variabel interaksi */
        j = index;
        for (k = 0; k < nPeubah; k++) {
            for (l = k + 1; l < nPeubah; l++) {
                value = matrixX.getElmt(k, 0) * matrixX.getElmt(l, 0);
                matrixXCuadratic.setElmt(j, 0, value);
                j++;
            }
        }                    
        return matrixXCuadratic;
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
    
    public static Matrix MatrixExtender(Matrix matrixData, Matrix matrixAdd, int idxCol){
        /*menambah kolom berisikan angka satu di sebelah kiri matrix */
        Matrix matrixExtended = new Matrix(matrixData.rowCount(), matrixData.colCount() + 1);
        int i, j;
        int row = matrixExtended.rowCount();
        int col = matrixExtended.colCount();
        for (i = 0; i < row; i++){
            for (j = 0; j < col ; j++){
                if (idxCol == 0){ /*ini ngisi nilai di kiri (kolom isinya satu semua) */
                    if(j == idxCol){
                        matrixExtended.setElmt(i, j, 1);
                    }                
                    else{
                        matrixExtended.setElmt(i, j, matrixData.getElmt(i, j-1));
                    }    
                }
                else if (idxCol == matrixData.colCount()){ /*ini ngisi nilai di kanan (kolom isinya nilai Y) */
                    if(j == idxCol){
                        matrixExtended.setElmt(i, j, matrixAdd.getElmt(i, 0));
                    }                
                    else{
                        matrixExtended.setElmt(i, j, matrixData.getElmt(i, j));
                    }    
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

    public static Matrix inputValue(int row, String value){
        Matrix M;
        int pilihan;
        Scanner scan = new Scanner(System.in);
        System.out.println("");
        while(true) {
            if (value == "Y"){
                System.out.println("Menu Input Nilai Y");
                System.out.println("1. Input dari keyboard");
                System.out.println("2. Input dari file");
                System.out.print("Pilih metode: ");
                pilihan = scan.nextInt();
    
                if (pilihan == 1){
                    System.out.println("Masukkan nilai-nilai Y: ");
                    M = IO.keyboardInputMatrix(row,1);
                    break;
                } else if(pilihan == 2){
                    M = IO.fileInputMatrix();
                    break;
                } else {
                    System.out.println("Input tidak valid!\n");
                    Main.confirmExit();
                    M = new Matrix(1,1);
                    M.setElmt(0, 0, 0);
                    return M;
                }
            }
            else if (value == "Xk"){
                System.out.println("Menu Input Nilai X");
                System.out.println("1. Input dari keyboard");
                System.out.println("2. Input dari file");
                System.out.print("Pilih metode: ");
                pilihan = scan.nextInt();
                
                if (pilihan == 1){
                    System.out.println("Masukkan nilai-nilai Xk: ");
                    M = IO.keyboardInputMatrix(row,1);
                    break;
                } else if(pilihan == 2){
                    M = IO.fileInputMatrix();
                    break;
                } else {
                    System.out.println("Input tidak valid!\n");
                    Main.confirmExit();
                    M = new Matrix(1,1);
                    M.setElmt(0, 0, 0);
                    return M;
                }
    
            }
        }
        return M;
        
    }

    public static void displaySPL(SPL spl){
        System.out.print("f(x)" + " = ");
        for(int i = 0; i < spl.varCount(); i++){
            if (i == 0){
                System.out.print(spl.getSolutions(i));
            }
            else{
                System.out.print(" + " + spl.getSolutions(i) + " X" + i);
            }
        }
    }

    public static void predictByValueX(SPL spl, Matrix X){
        double returnValue = spl.getSolutions(0);
    
        for (int i = 1; i < X.rowCount(); i++) {
            returnValue += X.getElmt(i, 0) * spl.getSolutions(i); 
        }
        System.out.println("Hasil dari f(X) : ");
        System.out.println(returnValue);    
}


    public static void main() {
        Matrix regressionMatrix = new Matrix(20, 4); 
        Matrix matrixX = new Matrix(20, 3); 
        Matrix matrixY = new Matrix(20, 1); 
        Matrix valueX = new Matrix(3, 1); 
        SPL splByLinearReg = new SPL(3);
        SPL splByCuadraticReg = new SPL(9);
        int i, j;
        double inputX = 0;
        double[][] M =   {
            {72.4, 76.3, 29.18}, {41.6, 70.3, 29.35}, {34.3, 77.1, 29.24}, {35.1, 68.0, 29.27},
            {10.7, 79.0, 29.78}, {12.9, 79.7, 29.80}, {8.3, 66.8, 29.69}, {20.1, 76.9, 29.82},
            {72.2, 77.7, 29.09}, {24.0, 67.7, 29.60}, {23.2, 76.8, 29.38}, {47.4, 86.6, 29.35},
            {31.5, 79.6, 29.63}, {10.6, 68.7, 29.43}, {11.2, 80.6, 29.48}, {73.3, 73.6, 29.38},
            {75.4, 74.9, 29.28}, {75.0, 78.7, 29.09}, {107.4, 86.8, 29.03}, {54.9, 70.9, 29.37}
        };
        
        double[] Y =   {0.90, 0.91, 0.96, 0.89, 1.00, 1.10, 1.15, 1.03, 0.77, 1.07, 
            1.07, 0.94, 1.10, 1.10, 1.10, 0.91, 0.87, 0.88, 0.82, 0.95};

        double[] X =   {50.0, 76.0, 29.3};
    
        for (i = 0; i < 20; i++) {
            for (j = 0; j < 3; j++) {
                matrixX.setElmt(i, j, M[i][j]);
            }
        }
        IO.terminalOutputMatrix(matrixX);

        for (i = 0; i < 20; i++) {
            matrixY.setElmt(i, 0, Y[i]);
        }
        IO.terminalOutputMatrix(matrixY);

        for (i = 0; i < 3; i++) {
            valueX.setElmt(i, 0, X[i]);
        }
        IO.terminalOutputMatrix(valueX);

        regressionMatrix = MatrixExtender(matrixX, matrixY, 3);

        IO.terminalOutputMatrix(regressionMatrix);

        displaySPL(MultipleLinearReg(regressionMatrix, 3, 20));
        predictByValueX(MultipleLinearReg(regressionMatrix, 3, 20), valueX);
        displaySPL(MultipleCuadraticReg(regressionMatrix, 3, 20));
        predictByValueX(MultipleCuadraticReg(regressionMatrix, 3, 20), valueX);
        // for(i = 0; i < 4; i++){
        //     for (j = 0; j < 5; j++) {
        //         if (j == 4){
        //             System.out.print(" = " + hasilRegresi.getElmt(i, j));
        //         }
        //         else if(j == 0){
        //             System.out.print(hasilRegresi.getElmt(i, j));
        //         }    
        //         else{
        //             System.out.print(" + " + hasilRegresi.getElmt(i, j) + " X" + j);
        //         }
        //     }
        //     System.out.println("\n");

        // }

    }

}