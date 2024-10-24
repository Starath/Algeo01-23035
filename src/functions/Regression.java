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
    for (i = 0; i < totalSampel; i++) {
        matrixOne.setElmt(i, 0, 1);
    }

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
                else if (idxCol == matrixExtended.colCount()){ /*ini ngisi nilai di kanan (kolom isinya nilai Y) */
                    if(j == idxCol){
                        matrixExtended.setElmt(i, j, matrixAdd.getElmt(i, 0));
                    }                
                    else{
                        matrixExtended.setElmt(i, j, matrixData.getElmt(i, j-1));
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
            else if (value == "X"){
                System.out.println("Menu Input Nilai X");
                System.out.println("1. Input dari keyboard");
                System.out.println("2. Input dari file");
                System.out.print("Pilih metode: ");
                pilihan = scan.nextInt();
    
                if (pilihan == 1){
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
        System.out.print("Y" + " = ");
        for(int i = 0; i < spl.varCount(); i++){
            if (i == 0){
                System.out.print(spl.getSolutions(i));
                
            }
            else{
                System.out.print(" + " + spl.getSolutions(i) + "X" + (i+1));
            }
        }
    }

    public static double predictByValueX(SPL spl, Matrix X){
        double returnValue = spl.getSolutions(0);
    
        for (int i = 1; i < X.rowCount(); i++) {
            returnValue += X.getElmt(i, 0) * spl.getSolutions(i); 
        }
        return returnValue;
    }

    // clearScreen();
    // border();
    // System.out.println("REGRESI BERGANDA");
    // System.out.println("1. Metode Regresi Linear Berganda");
    // System.out.println("2. Metode Regresi Kuadratik Berganda");
    // System.out.println("3. Kembali\n");
    // System.out.print("Pilih metode: ");
    // pilihanMet = scan.nextInt();


    // System.out.println("Masukkan N jumlah peubah : ");
    // int nPeubah = scan.nextInt();
    // System.out.println("Masukkan M jumlah sampel : ");
    // int totalSampel = scan.nextInt();
    // Matrix M = inputMatrix(totalSampel, nPeubah); // Ntar ganti sama inputMatrix
    // System.out.println("Masukkan M buah nilai Y : ");
    // Matrix Y = Regression.inputValue(totalSampel, "Y");
    // Matrix regressionMatrix = Regression.MatrixExtender(M, Y, nPeubah + 1);
    
    // if (pilihanMet == 3) {break;}
    // else if(pilihanMet == 1 || pilihanMet == 2){
    //     border();
    //     System.out.println("Berikut hasil SPL: ");
    //     switch (pilihanMet) {
    //         case 1 -> Regression.displaySPL(Regression.MultipleLinearReg(regressionMatrix, nPeubah, totalSampel)); // kedua ini nanti ganti
    //         case 2 -> Regression.displaySPL(Regression.MultipleCuadraticReg(regressionMatrix, nPeubah, totalSampel)); // kedua ini nanti ganti
    //     }
    //     border();
    // }
    // else {
    //     System.out.println("Input tidak valid!");
    // }
    // if (pilihanMet == 1) {
    //     System.out.println("Masukkan N buah nilai X : ");
    //     Matrix X = Regression.inputValue(nPeubah, "X");                        
    //     double predictValue = Regression.predictByValueX(Regression.MultipleLinearReg(regressionMatrix, nPeubah, totalSampel), X);
    //     System.out.println("Hasil dari f(X) : ");
    //     System.out.println(predictValue);
    // }
    // else if (pilihanMet == 2){
    //     System.out.println("Masukkan N buah nilai X : ");
    //     Matrix X = Regression.inputValue(nPeubah, "X");                        
    //     Matrix XCuadratic = Regression.valueOfXCuadratic(X, nPeubah);                        

    //     double predictValue = Regression.predictByValueX(Regression.MultipleLinearReg(regressionMatrix, nPeubah, totalSampel), XCuadratic);
    //     System.out.println("Hasil dari f(X) : ");
    //     System.out.println(predictValue);                   
    // }
    
    // confirmExit();

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