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
                    } else {
                        IO.fileOutputSPL(resultSPL);
                    }
                    System.out.println("");
                    border();
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
                        System.out.println("Determinan Matriks: " + det);
                        border();
                        confirmExit();
                    }
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
                        IO.terminalOutputMatrix(invers);
                    }
                }

                /*************************************************/
                /*                   POLINOM                     */
                else if (pilihan == 4) {
                    tipe = "polinom";
                    Matrix mPoints, mHasil;
                    double Absis;
                    int lagi;
                    clearScreen();
                    border();
                    System.out.println("INTERPOLASI POLINOMIAL");
                    System.out.println("(ketik 0 untuk kembali)");
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
                        PolyInterpolation.InterpolationFX(solutions);
                        border();
                        while(true) {
                            System.out.println("Lanjut memasukkan nilai absis? (y/n): ");
                            lagi = scan.nextInt();
                            if(lagi == 0) break;
                            else if(lagi == 1) {
                                System.out.println("");
                                border();
                                PolyInterpolation.InterpolationFX(solutions);
                                border();
                            }
                        }
                    } else if(pilihInput == 2) {
                        
                    }
                }

                /*************************************************/
                /*                    BICUBIC                    */
                else if (pilihan == 5) {
                    tipe = "bicubic";
                    Matrix M;
                    double a,b;
                    int pilihInput = inputMain();
                    if(pilihInput == 1){
                        M = IO.keyboardInputMatrix(4, 4);
                        a = scan.nextDouble();
                        b = scan.nextDouble();
                    } else{}

                }

                /*************************************************/
                /*                   REGRESI                     */
                else if (pilihan == 6) {
                    tipe = "regresi";
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
        int row,col;
        int pilihInput = inputMain();
        System.out.print("Jumlah baris: ");
        row = scan.nextInt();
        System.out.print("Jumlah kolom: ");
        col = scan.nextInt();
    
        if(pilihInput == 1){
            System.out.println("Masukkan Elemen-elemen Matriks: ");
            M = IO.keyboardInputMatrix(row, col);
        } else {
            M = IO.fileInputMatrix();
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

        //     if (pilihan == 1 || pilihan == 3){
        //         System.out.print("Determinan Matriks: ");
        //         if (tipe.equals("det1") || tipe.equals("det2")){
        //             switch (tipe) {
        //                 case "det1": 
        //                     System.out.println(MatrixAdv.detByGauss(M)); // kedua ini nanti ganti
        //                     break;
        //                 case "det2":
        //                     System.out.println(MatrixAdv.detByCofactor(M));
        //                     break;
        //             }
        //             break;}
        //         // OUTPUT MATRIX 
        //         else {
        //         IO.terminalOutputMatrix(M);
        //         break;}

        //     } else if(pilihan == 2){
        //         IO.fileOutputMatrix();
        //         break;
        //     } else {
        //         System.out.println("Input tidak valid!\n");
        //         confirmExit();
        //         break;
        //     }

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
