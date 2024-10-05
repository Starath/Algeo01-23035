package main;
import java.util.*;

public class Main {
    public static Scanner scan;
    public static void main(String[] args) {
        int isLaunch = 0;
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
            border();
            isLaunch = 1;
            System.out.println("MENU");
            System.out.println("1. Sistem Persamaan Linier");
            System.out.println("2. Determinan");
            System.out.println("3. Matriks balikan");
            System.out.println("4. Interpolasi Polinom");
            System.out.println("5. Interpolasi Bicubic Spline");
            System.out.println("6. Regresi Linier dan Kuadratik Berganda");
            System.out.println("7. Interpolasi Gambar"); // Bonus
            System.out.println("8. Keluar\n");

            scan = new Scanner(System.in);
            System.out.print("Pilih menu: ");
            int pilihan = scan.nextInt();

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
                    System.out.print("Pilih menu: ");
                    pilihan = scan.nextInt();

                    if (pilihan == 5) {break;}
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
                    System.out.print("Pilih menu: ");
                    pilihan = scan.nextInt();

                    if (pilihan == 3) {break;}
                }

                else if (pilihan == 3) {
                    clearScreen();
                    border();
                    System.out.println("DETERMINAN MATRIKS");
                    System.out.println("1. Metode Reduksi Baris");
                    System.out.println("2. Metode Ekspansi Kofaktor");
                    System.out.println("3. Kembali\n");
                    System.out.print("Pilih menu: ");
                    pilihan = scan.nextInt();

                    if (pilihan == 3) {break;}
                }
                else if (pilihan == 4) {}
                else if (pilihan == 5) {}
                else if (pilihan == 6) {}
                else if (pilihan == 7) {}
                else if (pilihan == 8) {
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

    public static void border() {
        System.out.println("\n+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+\n");
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void confirmExit() {
        System.out.print("Kembali ke menu ");
        scan = new Scanner(System.in);
        scan.nextLine();
    }
}
