package functions;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import main.IO;
import matrix.*;


public class PolyInterpolation {

    public static Scanner scan;
    public static Matrix KeyboardInputPoints() {
        Matrix mPoints;
        int nPoints;
        Scanner scan = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Masukkan banyak seluruh titik: ");
                nPoints = scan.nextInt();
                if (nPoints > 0) {  // Pastikan input nPoints adalah bilangan positif
                    break;
                } else {
                    System.out.println("Jumlah titik harus lebih dari 0. Coba lagi.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Harap masukkan bilangan bulat positif.");
                scan.next(); // Membersihkan input yang salah
            }
        }

        mPoints = new Matrix(nPoints, 2);

        // Loop untuk memasukkan setiap titik x dan y
        for (int i = 0; i < nPoints; i++) {
            double x = 0, y = 0;
            System.out.printf("Masukkan titik x%d dan y%d : ", i + 1, i + 1);

            // Validasi input untuk koordinat x
            while (true) {
                try {
                    System.out.println("");
                    System.out.print("x" + (i + 1) + ": ");
                    x = scan.nextDouble();
                    break;  // Keluar dari loop jika input valid
                } catch (InputMismatchException e) {
                    System.out.println("Input tidak valid. Harap masukkan angka untuk koordinat x.");
                    scan.next(); // Membersihkan input yang salah
                }
            }

            // Validasi input untuk koordinat y
            while (true) {
                try {
                    System.out.print("y" + (i + 1) + ": ");
                    y = scan.nextDouble();
                    break;  // Keluar dari loop jika input valid
                } catch (InputMismatchException e) {
                    System.out.println("Input tidak valid. Harap masukkan angka untuk koordinat y.");
                    scan.next(); // Membersihkan input yang salah
                }
            }

            // Set elemen di matriks untuk titik tersebut
            mPoints.setElmt(i, 0, x);  // Set x
            mPoints.setElmt(i, 1, y);  // Set y
        }
        return mPoints;
    }
    

    public static Matrix PointstoMatrix(Matrix mPoints) {
        // Mengubah matrix dari input point-point menjadi matrix interpolasi
        Matrix mHasil;
        mHasil = new Matrix(mPoints.rowCount(), mPoints.rowCount() + 1);
        for (int i = 0; i < mHasil.rowCount(); i++) {
            for (int j = 0; j < mHasil.colCount() - 1; j++) {
                mHasil.setElmt(i, j, Math.pow(mPoints.getElmt(i, 0), j));
            }
        }
        for (int i = 0; i < mHasil.rowCount(); i++) {
            mHasil.setElmt(i, mHasil.colCount() - 1, mPoints.getElmt(i, 1));
        }
        return mHasil;
    }

    public static SPL InterpolationFunction(Matrix otwSolved) {
        SPL solusi;
        solusi = SPL.gaussJordanElim(otwSolved);
        return solusi;
    }

    public static void OutputInterpolation(SPL solusi) {
        int lenSolusi = solusi.variables;
        System.out.printf("P(X) = ");
        for (int index = lenSolusi - 1; index >= 0; index--) {
            if (index == 0) {
                System.out.print("(" + solusi.solutions[index] + ")\n");
                break;
            }
            if (index == 1) {
                System.out.print("(" + solusi.solutions[index] + ")" + "x + ");
                continue;
            }
            System.out.print("(" + solusi.solutions[index] + ")" + "x^" + index + " + ");
        }
    }

    public static double setAbsis() {
        System.out.printf("Masukkan absis titik : ");
        double absis;
        // Validasi input untuk koordinat x
        while (true) {
            try {
                absis = scan.nextDouble();
                break;  // Keluar dari loop jika input valid
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Harap masukkan angka untuk koordinat x.");
                scan.next(); // Membersihkan input yang salah
            }
        }
        return absis;
    }

    public static double InterpolationFX(SPL solusi, double absis) {
        double Ordinat = 0;
        for (int index = 0; index < solusi.variables; index++) {
            Ordinat += solusi.solutions[index] * Math.pow(absis,index);
        }
        System.out.println("P(" + absis + ") = " + Ordinat);
        return Ordinat;
    }

    public static void fileoutputinterpolations(SPL solutions, double Absis, double Ordinat)  {
        String path = IO.fileOutputMaster();
        PrintStream ConsoleOut = System.out;
        boolean success;
        try {
            PrintStream fileOutput = new PrintStream(new File(path));
            System.setOut(fileOutput);
            if(solutions.isNoSolution()){
                System.out.println("Tidak terdapat persamaan polinomial");
            } else {
                PolyInterpolation.OutputInterpolation(solutions);
                System.out.println("P(" + Absis + ") = " + Ordinat + "\n");
            }
            fileOutput.close();
            success = true;
        } catch (FileNotFoundException e) {
            System.err.println("File not found or could not be created.");
            success = false;
        }
        System.setOut(ConsoleOut);
        if(success){System.out.println("Saved to file successfully! ");} 
    }

    public static void main(String[] args) 
    {
        int pilihan;
        Matrix mPoints = PolyInterpolation.KeyboardInputPoints();
        Matrix mHasil = PolyInterpolation.PointstoMatrix(mPoints);
        SPL solusi = PolyInterpolation.InterpolationFunction(mHasil);
        PolyInterpolation.OutputInterpolation(solusi);
        System.out.println("");
        // Scanner scan = new Scanner(System.in);
        // System.out.println("");
        // while(true) 
        // {
        //     Main.clearScreen();
        //     Main.border();
        //     System.out.println("Menu Input Matriks");
        //     Main.border();
        //     System.out.println("1. Input dari keyboard");
        //     System.out.println("2. Input dari file");
        //     System.out.print("Pilih metode: ");
        //     pilihan = -1;  // Inisialisasi dengan nilai di luar rentang yang valid
        //     boolean isCancel = false; // Flag untuk pengecekan apakah user ingin cancel

        //     try {
        //         System.out.printf("Pilih metode (ketik 0 untuk batal): ");
        //         pilihan = scan.nextInt();

        //         // Cek apakah user ingin membatalkan input
        //         if (pilihan == 0) {
        //             System.out.println("Input dibatalkan.");
        //             isCancel = true; // Set flag cancel
        //             break;
        //         }
        //         // Validasi apakah pilihan termasuk dalam rentang 1 hingga 3
        //         if (pilihan >= 1 && pilihan <= 2) {
        //             System.out.println("Anda memilih metode " + pilihan);
        //         } else {
        //             System.out.println("Pilihan tidak valid. Harap masukkan angka 1 hingga 3.");
        //         }
        //     } catch (InputMismatchException e) {
        //         System.out.println("Input tidak valid. Harap masukkan angka.");
        //         scan.next(); // Membersihkan input yang salah
        //     }
    
        //     // Tindakan setelah input selesai
        //     if (!isCancel) {
        //         System.out.println("Proses dilanjutkan dengan pilihan: " + pilihan);
        //     } else {
        //         break;
        //     }
        //     scan.close();    

        //     if (pilihan == 1) {
        //         Matrix mPoints = PolyInterpolation.KeyboardInputPoints();
        //         Matrix mInterpolate = PolyInterpolation.PointstoMatrix(mPoints);  
        //         SPL solusi = PolyInterpolation.InterpolationFunction(mInterpolate);
        //         PolyInterpolation.OutputInterpolation(solusi);
        //         Main.border();
        //         PolyInterpolation.InterpolationFX(solusi);
        //         Main.border();
        //     } else if (pilihan == 2) {
        //         // Matrix mPoints = PolyInterpolation.FileInputPoints();
        //         // Matrix mInterpolate = PolyInterpolation.PointstoMatrix(mPoints);  
        //         // SPL solusi = PolyInterpolation.InterpolationFunction(mInterpolate);
        //         // PolyInterpolation.OutputInterpolation(solusi);
        //         // Main.border();
        //         // PolyInterpolation.InterpolationFX(solusi);
        //         // Main.border();
        //     }
        // }
    }
}
