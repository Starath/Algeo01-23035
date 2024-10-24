package main;

import functions.*;
import java.util.*;
import matrix.*;

public class Main {
    public static Scanner scan;
    public static void main(String[] args) {
        int isLaunch = 0;
        int pilihan, pilihanMet;
        String tipe;
        clearScreen();
        System.out.println("""



                             /$$$$$$  /$$        /$$$$$$  /$$$$$$$$  /$$$$$$ \r
                            /$$__  $$| $$       /$$__  $$| $$_____/ /$$__  $$\r
                           | $$  \\ $$| $$      | $$  \\__/| $$      | $$  \\ $$\r
                           | $$$$$$$$| $$      | $$ /$$$$| $$$$$   | $$  | $$\r
                           | $$__  $$| $$      | $$|_  $$| $$__/   | $$  | $$\r
                           | $$  | $$| $$      | $$  \\ $$| $$      | $$  | $$\r
                           | $$  | $$| $$$$$$$$|  $$$$$$/| $$$$$$$$|  $$$$$$/\r
                           |__/  |__/|________/ \\______/ |________/ \\______/ """
        ); 
        System.out.print("\n\n\n");
        
        while (true) { 
            // double[][] M;
            if (isLaunch == 1) clearScreen();
            isLaunch = 1;
            System.out.println("MENU");
            border();
            System.out.println("1. Sistem Persamaan Linier");
            System.out.println("2. Determinan");
            System.out.println("3. Matriks balikan");
            System.out.println("4. Interpolasi Polinom");
            System.out.println("5. Interpolasi Bicubic Spline");
            System.out.println("6. Regresi Linier dan Kuadratik Berganda");
            System.out.println("7. Keluar\n");

            scan = new Scanner(System.in);
            border();
            System.out.print("Pilih menu: ");
            pilihan = scan.nextInt();

            while (true) {

                /*************************************************/
                /*                    SPL                        */
                if (pilihan == 1) {
                    SPL resultSPL;
                    Matrix M = null;
                    tipe = "spl";
                    clearScreen();
                    border();
                    System.out.println("SISTEM PERSAMAAN LINIER");
                    System.out.println("1. Metode Eliminasi Gauss");
                    System.out.println("2. Metode Eliminasi Gauss-Jordan");
                    System.out.println("3. Metode Matriks balikan");
                    System.out.println("4. Kaidah Cramer");
                    System.out.println("5. Kembali\n");
                    System.out.print("Pilih metode: ");
                    pilihanMet = scan.nextInt();

                    if(pilihanMet == 1 || pilihanMet == 2 || pilihanMet == 3 || pilihanMet == 4 ){
                        M = inputMatrix(tipe);
                    }
                    
                    if (pilihanMet == 1) resultSPL = SPL.gaussElim(M);
                    else if(pilihanMet == 2) resultSPL = SPL.gaussJordanElim(M);
                    else if(pilihanMet== 3) resultSPL = SPL.inverseSPL(M);
                    else if(pilihanMet == 4) resultSPL = SPL.cramerMethodSPL(M);
                    else if(pilihanMet == 5) break;
                    else {
                        System.out.println("Input tidak valid!");
                        confirmExit();
                        continue;}
                    int pilihOutput = outputMain();
                    border();
                    System.out.println("");
                    if(pilihOutput == 1){
                        if(resultSPL.isNoSolution()){
                            System.out.println("SPL tidak memiliki solusi / solusi tidak ditemukan");
                        } else {
                            resultSPL.displaySolutions();
                        }
                        System.out.println("");
                        border();
                    } else {
                        IO.fileOutputSPL(resultSPL);
                    }
                    confirmExit();
                    }



                /*************************************************/
                /*              DETERMINAN                       */
                else if (pilihan == 2) {
                    tipe = "det";
                    double det;
                    Matrix M = null;
                    clearScreen();
                    border();
                    System.out.println("DETERMINAN MATRIKS");
                    System.out.println("1. Metode Reduksi Baris");
                    System.out.println("2. Metode Ekspansi Kofaktor");
                    System.out.println("3. Kembali\n");
                    System.out.print("Pilih metode: ");
                    pilihanMet = scan.nextInt();

                    if(pilihanMet == 1 || pilihanMet == 2 ){
                        M = inputMatrix(tipe);
                    }
                    if (pilihanMet == 1) {
                        det = MatrixAdv.detByGauss(M);}

                    else if(pilihanMet == 2) {
                        det = MatrixAdv.detByCofactor(M);}

                    else if (pilihanMet == 3) {break;}
                    else {
                        System.out.println("Input tidak valid!");
                        confirmExit();
                        continue;
                    }
                    int pilihOutput = outputMain();
                    if(pilihOutput == 1){
                        border();
                        System.out.println("");
                        System.out.println("Determinan Matriks: " + det);
                        System.out.println("");
                        border();
                    } else {
                        IO.fileOutputDetorBicubic(det, "det");
                    }
                    confirmExit();
                }

                /*************************************************/
                /*                     INVERS                    */
                else if (pilihan == 3) {
                    tipe = "invers";
                    Matrix M = null, invers;
                    clearScreen();
                    border();
                    System.out.println("MATRIKS BALIKAN");
                    System.out.println("1. Metode Reduksi Baris");
                    System.out.println("2. Metode Matriks Adjoin");
                    System.out.println("3. Kembali\n");
                    System.out.print("Pilih metode: ");
                    pilihanMet = scan.nextInt();

                    if(pilihanMet == 1 || pilihanMet == 2){
                        M = inputMatrix(tipe);
                    }
                    if(pilihanMet == 1){
                        invers = MatrixAdv.inverseByOBE(M);
                    }
                    else if(pilihanMet == 2){
                        invers = MatrixAdv.inverseByAdjoin(M);
                    }
                    else if(pilihanMet == 3)break;
                    else {
                        System.out.println("Input tidak valid!");
                        confirmExit();
                        continue;
                    }

                    int pilihOutput = outputMain();
                    if(pilihOutput == 1){
                        border();
                        System.out.println("");
                        IO.terminalOutputMatrix(invers);
                        System.out.println("");
                        border();
                    } else {
                        IO.fileOutputInvers(invers);
                    }
                    confirmExit();
                }

                /*************************************************/
                /*                   POLINOM                     */
                else if (pilihan == 4) {
                    tipe = "polinom";
                    Matrix mPoints, mHasil;
                    double Absis;
                    clearScreen();
                    border();
                    System.out.println("INTERPOLASI POLINOMIAL");
                    System.out.println("1. Interpolasi");
                    System.out.println("0. Kembali");
                    pilihanMet = scan.nextInt();
                    if(pilihanMet == 1){
                        int pilihInput = inputMain();
                        if(pilihInput == 1)
                        {
                            mPoints = PolyInterpolation.KeyboardInputPoints();
                            border();
                            mHasil = PolyInterpolation.PointstoMatrix(mPoints);
                            clearScreen();
                            System.out.println("Matrix untuk interpolasi polinomial  : ");
                            IO.terminalOutputMatrix(mHasil);
                            SPL solutions = PolyInterpolation.InterpolationFunction(mHasil);
                            border();
                            System.out.println("Persamaan polinomial yang diperoleh: ");
                            PolyInterpolation.OutputInterpolation(solutions);
                            System.out.println("");
                            border();
                            System.out.printf("Masukkan absis titik : ");
                            Absis = PolyInterpolation.setAbsis();
                            // Validasi input untuk koordinat x
                            double Ordinat = PolyInterpolation.InterpolationFX(solutions, Absis);
                            border();
                        } else if(pilihInput == 2) {
                            
                        }
                    }
                    else if(pilihanMet == 0){
                        break;
                    } else {
                        System.out.println("Input tidak valid!");
                        confirmExit();
                        continue;
                    }
                    confirmExit();
                }

                /*************************************************/
                /*                    BICUBIC                    */
                else if (pilihan == 5) {
                    Matrix M;
                    double a,b;
                    System.out.println("BICUBIC INTERPOLATION");
                    System.out.println("1. Interpolasi");
                    System.out.println("0. Kembali");
                    pilihanMet = scan.nextInt();
                    if(pilihanMet == 1){
                        int pilihInput = inputMain();
                        if(pilihInput == 1){
                            System.out.println("Masukkan elemen-elemen matriks:");
                            M = IO.keyboardInputMatrix(4, 4);
                            System.out.println("Masukkan titik-titik yang akan diinterpolasi [f(a,b)]");
                            System.out.print("a: ");
                            a = scan.nextDouble();
                            System.out.print("b: ");
                            b = scan.nextDouble();
                            
                        } else{
                            String path = IO.inputFileName();
                            M = IO.fileInputMatrix(path, 4, 4);
                            double[] points = IO.fileInputPoints(path, M.rowCount());
                            a = points[0];
                            b = points[1];
                        }
                    } else if (pilihanMet == 0){
                        break;
                    } else {
                        System.out.println("Input tidak valid!");
                        confirmExit();
                        continue;
                    }
                             
                    double interpolated = Bicubic.bicubicInterpolation(M, a, b);
                    int pilihOutput = outputMain();
                    if(pilihOutput == 1){
                        border();
                        System.out.println("");
                        System.out.println("f(" + a + "," + b + ") = " + interpolated);
                        System.out.println("");
                        border();
                    }
                    else{
                        IO.fileOutputDetorBicubic(interpolated, "bicubic");
                    }
                    confirmExit();
                }

                /*************************************************/
                /*                   REGRESI                     */
                else if (pilihan == 6) {
                    tipe = "regresi";
                            clearScreen();
                            border();
                            System.out.println("REGRESI BERGANDA");
                            System.out.println("1. Metode Regresi Linear Berganda");
                            System.out.println("2. Metode Regresi Kuadratik Berganda");
                            System.out.println("3. Kembali\n");
                            System.out.print("Pilih metode: ");
                            pilihanMet = scan.nextInt();
                            
                            if (pilihanMet == 3) {break;}
                            else if(pilihanMet == 1 || pilihanMet == 2){
                                /*1. Input Nilai X  */
                                Matrix M = inputMatrix("regresi");
                                int totalSampel = M.rowCount();
                                int nPeubah = M.colCount();
                                
                                /*2. Input Nilai Y */
                                Matrix Y = Regression.inputValue(totalSampel, "Y");
                                
                                /*3. Menambah Kolom (matriks) Y ke samping kanan matriks M */
                                Matrix regressionMatrix = Regression.MatrixExtender(M, Y, nPeubah);
                                border();
                                
                                /*4. Menampilkan hasil dari regresi dalam bentuk SPL */
                                System.out.println("Berikut hasil SPL: ");
                                switch (pilihanMet) {
                                    case 1 -> Regression.displaySPL(Regression.MultipleLinearReg(regressionMatrix, nPeubah, totalSampel)); // kedua ini nanti ganti
                                    case 2 -> Regression.displaySPL(Regression.MultipleCuadraticReg(regressionMatrix, nPeubah, totalSampel)); // kedua ini nanti ganti
                                }
                                System.out.println("\n");
                                border();
                                
                                /*5. Menerima input nilai Xi sejumlah nPeubah untuk memprediksi hasil akhirnya */
                                Matrix Xk = Regression.inputValue(nPeubah, "Xk");                        

                                /*6. Memprediksi nilai  */
                                if (pilihanMet == 2) {
                                    // int row = Regression.combination(nPeubah, 2);
                                    Matrix XCuadratic = Regression.valueOfXCuadratic(Xk, nPeubah);                        
                                    Regression.predictByValueX(Regression.MultipleCuadraticReg(regressionMatrix, nPeubah, totalSampel), XCuadratic);
                                }
                                else{
                                    Regression.predictByValueX(Regression.MultipleLinearReg(regressionMatrix, nPeubah, totalSampel), Xk);
                                }
                            }
                            else {
                                System.out.println("Input tidak valid!");
                                confirmExit();
                                continue;
                            }
                            confirmExit();


                }
                else if (pilihan == 7) {
                    clearScreen();
                    System.out.println("""                                                                
                      _____            _____            _____         _____         _____    _____      _____      ______   
                  ___|\\    \\      ____|\\    \\      ____|\\    \\    ___|\\    \\   ___|\\     \\  |\\    \\    /    /| ___|\\     \\  
                 /    /\\    \\    /     /\\    \\    /     /\\    \\  |    |\\    \\ |    |\\     \\ | \\    \\  /    / ||     \\     \\ 
                |    |  |____|  /     /  \\    \\  /     /  \\    \\ |    | |    ||    | |     ||  \\____\\/    /  /|     ,_____/|
                |    |    ____ |     |    |    ||     |    |    ||    | |    ||    | /_ _ /  \\ |    /    /  / |     \\--'\\_|/
                |    |   |    ||     |    |    ||     |    |    ||    | |    ||    |\\    \\    \\|___/    /  /  |     /___/|  
                |    |   |_,  ||\\     \\  /    /||\\     \\  /    /||    | |    ||    | |    |       /    /  /   |     \\____|\\ 
                |\\ ___\\___/  /|| \\_____\\/____/ || \\_____\\/____/ ||____|/____/||____|/____/|      /____/  /    |____ '     /|
                | |   /____ / | \\ |    ||    | / \\ |    ||    | /|    /    | ||    /     ||     |`    | /     |    /_____/ |
                 \\|___|    | /   \\|____||____|/   \\|____||____|/ |____|____|/ |____|_____|/     |_____|/      |____|     | /
                   \\( |____|/       \\(    )/         \\(    )/      \\(    )/     \\(    )/           )/           \\( |_____|/ 
                     '   )/           '    '           '    '        '    '       '    '            '             '    )/    
                         '                                                                                             '     
                            """);
                    System.exit(0);
                }

                else {
                    System.out.println("\nPilihan tidak valid!");
                    confirmExit();
                    break;

                }
            }
        }
    }

    // Pilihan Input Matrix
    public static int inputMain(){
        Matrix M;
        int pilihan;
        Scanner scan = new Scanner(System.in);
        System.out.println("");
        while(true) {
            clearScreen();
            border();
            System.out.println("Menu Input");
            border();
            System.out.println("1. Input dari keyboard");
            System.out.println("2. Input dari file");
            System.out.print("Pilih metode: ");
            pilihan = scan.nextInt();
            System.out.println("");
            if(pilihan != 1 && pilihan != 2){
                System.out.println("Input tidak Valid!");
                confirmExit();
                continue;
            }
            return pilihan;
        }
    }

    public static Matrix inputMatrix(String tipe){
        Matrix M;
        int row = 0,col = 0;
        int pilihInput = inputMain();
        if (tipe == "regresi"){
            System.out.print("""
                (Jumlah baris adalah Banyaknya sampel) 
                (Jumlah kolom adalah banyaknya peubah) 
                """);
        }
        
        
        System.out.print("Jumlah baris: ");
        row = scan.nextInt();
        System.out.print("Jumlah kolom: ");
        col = scan.nextInt();
        System.out.println("");
        
    
        if(pilihInput == 1){
            System.out.println("Masukkan Elemen-elemen Matriks: ");
            M = IO.keyboardInputMatrix(row, col);
        } else {
            String path = IO.inputFileName();
            M = IO.fileInputMatrix(path, row, col);
        }
        return M;
    }
    // Pilihan Output Matrix
    public static int outputMain(){
        int pilihan;
        Scanner scan = new Scanner(System.in);
        System.out.println("");
        while(true) {
            clearScreen();
            border();
            System.out.println("Menu Output");
            border();
            System.out.println("1. Output ke terminal");
            System.out.println("2. Output ke file");
            System.out.print("Pilih metode: ");
            pilihan = scan.nextInt();
            System.out.println("");
            if(pilihan != 1 && pilihan != 2){
                System.out.println("Input tidak Valid!");
                confirmExit();
                continue;
            }
            return pilihan;

        }
    }

    // Border untuk styling
    public static void border() {
        System.out.println("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+");
    }

    // Membersihkan terminal
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Print title - tambahkan ke setiap pilihan yang ada
    public static void menuTitle(String[] title) {
        System.out.println(Arrays.toString(title));
    }

    // Konfirmasi kembali ke menu
    public static void confirmExit() {
        System.out.print("Kembali ke menu ");
        scan = new Scanner(System.in);
        scan.nextLine();
    }
}
