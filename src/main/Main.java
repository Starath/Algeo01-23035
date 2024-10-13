package main;
import java.util.*;
import matrix.*;

public class Main {
    public static Scanner scan;
    public static void main(String[] args) {
        int isLaunch = 0;
        int pilihan, pilihanMet;
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
            System.out.println("7. Interpolasi Gambar"); // Bonus
            System.out.println("8. Keluar\n");

            scan = new Scanner(System.in);
            border();
            System.out.print("Pilih menu: ");
            pilihan = scan.nextInt();

            while (true) {
                if (pilihan == 1) {
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

                    if (pilihanMet == 1) {break;}
                    // else if(pilihMetode == 2) {}
                    // else if(pilihMetode == 3) {}
                    // else if(pilihMetode == 4) {}
                    // else {}
                }
                else if (pilihan == 2) {
                    clearScreen();
                    border();
                    System.out.println("DETERMINAN MATRIKS");
                    System.out.println("1. Metode Reduksi Baris");
                    System.out.println("2. Metode Ekspansi Kofaktor");
                    System.out.println("3. Kembali\n");
                    System.out.print("Pilih metode: ");
                    pilihanMet = scan.nextInt();

                    if (pilihanMet == 3) {break;}
                    else if(pilihanMet == 1){
                        Matrix M = inputMatrix(); // Ntar ganti sama inputMatrix
                        border();
                        System.out.print("Determinan Matrix: ");
                        System.out.println(MatrixAdv.detByGauss(M));
                        border();
                        
                    }

                    confirmExit();
                    break;
                }

                else if (pilihan == 3) {
                    clearScreen();
                    border();
                    System.out.println("DETERMINAN MATRIKS");
                    System.out.println("1. Metode Reduksi Baris");
                    System.out.println("2. Metode Ekspansi Kofaktor");
                    System.out.println("3. Kembali\n");
                    System.out.print("Pilih metode: ");
                    pilihan = scan.nextInt();

                    if (pilihan == 3) {break;}
                }
                else if (pilihan == 4) {}
                else if (pilihan == 5) {}
                else if (pilihan == 6) {}
                else if (pilihan == 7) {}
                else if (pilihan == 8) {
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
    public static Matrix inputMatrix(){
        Matrix M;
        int pilihan;
        Scanner scan = new Scanner(System.in);
        System.out.println("");
        while(true) {
            clearScreen();
            border();
            System.out.println("Menu Input Matriks");
            border();
            System.out.println("1. Input dari keyboard");
            System.out.println("2. Input dari file");
            System.out.print("Pilih metode: ");
            pilihan = scan.nextInt();

            if (pilihan == 1){
                M = MatrixIO.keyboardInputMatrix();
                break;
            } else if(pilihan == 2){
                M = MatrixIO.fileInputMatrix();
                break;
            } else {
                System.out.println("Input tidak valid!\n");
                confirmExit();
                M = new Matrix(1,1);
                M.setElmt(0, 0, 0);
                return M;
            }

        }
        return M;
        
    }
    // Pilihan Output Matrix
    public static void outputMatrix(Matrix M){
        int pilihan;
        Scanner scan = new Scanner(System.in);
        System.out.println("");
        while(true) {
            clearScreen();
            border();
            System.out.println("Menu Input Matriks");
            border();
            System.out.println("1. Output ke terminal");
            System.out.println("2. Output ke file");
            System.out.println("3. Output ke terminal & simpan ke file");
            System.out.print("Pilih metode: ");
            pilihan = scan.nextInt();

            if (pilihan == 1){
                MatrixIO.terminalOutputMatrix(M);
                break;
            } else if(pilihan == 2){
                MatrixIO.fileOutputMatrix();
                break;
            } else {
                System.out.println("Input tidak valid!\n");
                confirmExit();
                break;
            }

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
