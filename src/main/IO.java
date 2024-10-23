package main;

import java.awt.RenderingHints;
import java.io.*;
import java.util.*;
import matrix.*;

public class IO {
    public static Scanner scan;

    /* ===========================================*/
    /*                 MATRIX I/O                 */
    /* ===========================================*/

    /*=================== INPUT ==================*/
    public static Matrix keyboardInputMatrix(){
        int row = -1, col = -1;
        scan = new Scanner(System.in);
        if (row == -1 || col == -1){
            System.out.print("Masukkan jumlah baris: ");
            row = scan.nextInt();
    
            System.out.print("Masukkan jumlah kolom: ");
            col = scan.nextInt();
        }
        
        Matrix M = new Matrix(row, col);
        System.out.println("Masukkan elemen-elemen matriks");
        for (int i = 0; i < M.rowCount(); i++){
            for (int j = 0; j < M.colCount(); j++){
                double elm = scan.nextDouble();
                M.setElmt(i, j, elm);
            }
        }
        System.out.println(""); // Biar ada newline (styling)
        return M;
    }

    public static Matrix fileInputMatrix(){
        scan = new Scanner(System.in);
        System.out.println("Masukkan nama file (contoh: a.txt)");
        String filename = scan.nextLine();
        String path = "..\\test\\" + filename;
        System.out.println("Opening " + path + "...");

        try {
            int nRow, nCol;
            nRow = nCol = 0;
            File file = new File(path);
            Scanner scanFile = new Scanner(file);

            // Taro di luar? biar ngeliat m sekali aja
            while(scanFile.hasNextLine()){
                nCol = (scanFile.nextLine().split(" ").length);
                nRow++;
            }
            scanFile.close();

            // Create matrix
            Matrix M = new Matrix(nRow, nCol);
            scanFile = new Scanner(file);

            // Read file and assign each element
            for (int i = 0; i < nRow; i++){
                for (int j = 0; j < nCol; j++){
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

    /*=================== OUTPUT ==================*/

    public static void terminalOutputMatrix(Matrix M) {
        for (int i = 0; i < M.rowCount(); i++){
            for (int j = 0; j < M.colCount(); j++){
                System.out.print(M.getElmt(i, j) + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public static void fileOutputMatrix(){

    }
    public static void printRow(Matrix M, int idx) {
        System.out.println(Arrays.toString(M.getRow(idx)));
     }
    public void printCol(Matrix M, int idx){
        double col[] = M.getCol(idx);
        int i;
        for (i = 0; i < M.rowCount();i++) {
            System.out.print("[");
            System.out.print(col[i]);
            System.out.print("]\n");
        }
    }

    // public static void fileOutMatrix(Matrix M){

    // }
    public static void main(String[] args) {
        Matrix M;
        M = keyboardInputMatrix();
        terminalOutputMatrix(M);
    }

    /* ===========================================*/
    /*                 OTHER I/O                  */
    /* ===========================================*/
}

