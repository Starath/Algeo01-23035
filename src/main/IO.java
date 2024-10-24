package main;

import functions.*;
import java.io.*;
import java.util.*;
import matrix.*;

public class IO {
    public static Scanner scan;

    /* ===========================================*/
    /*                 MATRIX I/O                 */
    /* ===========================================*/

    /*============================================ */
    /*=================== INPUT  ==================*/
    /*============================================ */
    public static Matrix keyboardInputMatrix(int row, int col){
        scan = new Scanner(System.in);
        if (row == -1 || col == -1){
            System.out.print("Masukkan jumlah baris: ");
            row = scan.nextInt();
    
            System.out.print("Masukkan jumlah kolom: ");
            col = scan.nextInt();
        }
        
        Matrix M = new Matrix(row, col);
        for (int i = 0; i < M.rowCount(); i++){
            for (int j = 0; j < M.colCount(); j++){
                double elm = scan.nextDouble();
                M.setElmt(i, j, elm);
            }
        }
        System.out.println(""); // Biar ada newline (styling)
        return M;
    }

    public static String inputFileName(){
        scan = new Scanner(System.in);
        System.out.println("Masukkan nama file (contoh: a.txt): ");
        String filename = scan.nextLine();
        String path = "..\\test\\" + filename;
        return path;
    }
    public static Matrix fileInputMatrix(String path, int row, int col){
        System.out.println("Opening " + path + "...");

        try {
            int nRow, nCol;
            nRow = nCol = 0;
            File file = new File(path);
            Scanner scanFile = new Scanner(file);

            // Create matrix
            Matrix M = new Matrix(row, col);
            scanFile = new Scanner(file);

            // Read file and assign each element
            for (int i = 0; i < row; i++){
                for (int j = 0; j < col; j++){
                    M.setElmt(i, j, scanFile.nextDouble());
                }
            }

            scanFile.close();
            return M;

        } catch (FileNotFoundException e) {
            System.out.println("File not found...");
            Matrix zero = new Matrix(1, 1);
            zero.setElmt(0, 0, 0);
            // return 0 nanti di cek di main sebagai boolean value
            return zero;
        }
    }

    //Bicubic
    public static double[] fileInputPoints(String path,int line) {
        double[] points = new double[10];
        try {
            File file = new File(path);
            Scanner scanFile = new Scanner(file);
            for(int i = 0; i < line; i++){
                scanFile.nextLine();
            }
            int index = 0;
            while(scanFile.hasNextDouble()){
                points[index] = scanFile.nextDouble();
                index++;
            }
            scanFile.close();
            return points;
        } catch (FileNotFoundException e) {
            System.out.println("File not found...");
            return points;
        }
    }    
    
    public static int FileRowCounter(String path) {
        Scanner fileScanner;
        while (true) { 
            try {
                fileScanner = new Scanner(new FileReader(path));
                break;
            } catch (FileNotFoundException e) {
                System.out.println("File not found...");
                path = IO.inputFileName();
            }
        }

        int rowCounter = 0;
        while (fileScanner.hasNextLine()) {
            fileScanner.nextLine();
            rowCounter++;
        }
        return rowCounter;
    }

    public static double AbsisFileInput(String path, int rowCounter) {
        Scanner fileScanner;
        int i = 0; 
        double Absis;
        while (true) { 
            try {
                fileScanner = new Scanner(new FileReader(path));
                break;
            } catch (FileNotFoundException e) {
                System.out.println("File not found...");
                path = IO.inputFileName();
            }
        }

        while (i < rowCounter - 1) {
            fileScanner.next();
            i++;
        }
        Absis = fileScanner.nextDouble();
        return Absis;
    }

    /*============================================ */
    /*=================== OUTPUT ==================*/
    /*============================================ */

    public static void terminalOutputMatrix(Matrix M) {
        for (int i = 0; i < M.rowCount(); i++){
            for (int j = 0; j < M.colCount(); j++){
                System.out.print(M.getElmt(i, j) + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public static String fileOutputMaster(){
        String path = inputFileName();
        File file = new File(path);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
            // You can also return a default value or throw a custom exception
        }
        return path;
    }

    public static void fileOutputSPL(SPL R){
        boolean success;
        String path = fileOutputMaster();
        PrintStream consoleOut = System.out;
        try {
            PrintStream fileOutput = new PrintStream(new File(path));
            System.setOut(fileOutput);
            if(R.isNoSolution()){
                System.out.println("SPL tidak memiliki solusi / solusi tidak ditemukan");
            } else {
                R.displaySolutions();
            }
            fileOutput.close();
            success = true;
        } catch (FileNotFoundException e) {
            System.err.println("File not found or could not be created.");
            success = false;
        }
        System.setOut(consoleOut);
        if(success){System.out.println("\nSaved to file successfully! \n");}
    }

    public static void fileOutputDetorBicubic(double val, String tipe){
        boolean success;
        String path = fileOutputMaster();
        PrintStream consoleOut = System.out;
        try {
            PrintStream fileOutput = new PrintStream(new File(path));
            System.setOut(fileOutput);
            if(tipe.equals("det")){
                System.out.println("Determinan Matriks: " + val);
            } 
            else if(tipe.equals("bicubic")){
                System.out.println("f(" + "a" + "," + "b" + ") = " + val);
            }
            fileOutput.close();
            success = true;
        } catch (FileNotFoundException e) {
            System.err.println("File not found or could not be created.");
            success = false;
        }
        System.setOut(consoleOut);
        if(success){System.out.println("\nSaved to file successfully! \n");}
    }

    public static void fileOutputInvers(Matrix invers){
        boolean success;
        String path = fileOutputMaster();
        PrintStream consoleOut = System.out;
        try {
            PrintStream fileOutput = new PrintStream(new File(path));
            System.setOut(fileOutput);
            terminalOutputMatrix(invers);
            fileOutput.close();
            success = true;
        } catch (FileNotFoundException e) {
            System.err.println("File not found or could not be created.");
            success = false;
        }
        System.setOut(consoleOut);
        if(success){System.out.println("\nSaved to file successfully! \n");}
    }
    public static void FileOutputInterpolations(SPL solutions, double Absis, double Ordinat)  {
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



    /* ===========================================*/
    /*                 I/O MAIN                   */
    /* ===========================================*/


}

