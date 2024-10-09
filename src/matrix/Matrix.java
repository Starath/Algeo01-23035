package matrix;

import java.io.*;
import java.util.*;

public class Matrix {
    // Attributes
    double[][] elements;
    int Row; // Jumlah baris
    int Col; // Jumlah kolom

    public static Scanner scan;

    //Constructor
    public Matrix(int nRow, int nCol) {
        this.Row = nRow;
        this.Col = nCol;
        this.elements = new double[nRow][nCol];
    }

    /* ============== BASIC MATRIX =================*/
    // Prekondisi idx < Row atau Col
    public double[] getRow(int idx) {return elements[idx];}
    public double[] getCol(int idx) {
        double[] col = new double[Row];
        for (int i =0; i < Col; i++) {
            col[i] = elements[i][idx];
        }
        return col;
    }

    public int rowCount(){return Row;}
    public int colCount(){return Col;}
    
    public boolean isSquare() { 
        return Col == Row;
    }

    public boolean isIdentity() { 
        if (!isSquare()) {
            return false;
        } 
        for (int i = 0; i < Row; i++) {
            for (int j = 0; j < Col; j++) {
                if (i == j) {
                    if (elements[i][j] != 1) return false;
                } else {
                    if (elements[i][j] != 0) return false;
                }
            }
        }
        return true;
    }

    /* ============== MODIFY MATRIX =================*/
    public void setElmt(int i, int j, double val) {elements[i][j] = val;}
    
    public double getElmt(int i, int j) {return elements[i][j];}

    public Matrix copyMatrix(){
        Matrix newM = new Matrix(Row, Col);
        for (int i =0; i<Row;i++){
            for (int j=0; j<Col; j++) {
                newM.elements[i][j] = elements[i][j];
            }
        }
        return newM;
    }
    public void swapRows(int row1, int row2) {
        double[] tempRow1 = getRow(row1);
        double[] tempRow2 = getRow(row2);
        elements[row1] = tempRow2;
        elements[row2] = tempRow1;
    }
    public void multiplyRow(int row, double scalar){
        for(int j =0; j < Col; j++){
            elements[row][j] *= scalar;
        }
    }
    // Menambahkan row2 ke row1
    public void addRows(int row1, int row2, double scalar) {
        for(int j =0; j < Col; j++){
            elements[row1][j] += (elements[row2][j] * scalar);
        }
    }

    public void OBE(int rowOBEIdx, int rowPivotIdx) {
    //Parameter rowOBEIdx yang di-OBE, rowPivotIdx "acuan"-nya
        double tumbal = elements[rowOBEIdx][rowPivotIdx];
        for (int i = 0; i < Col; i++) {
            elements[rowOBEIdx][i] = elements[rowOBEIdx][i] - tumbal * elements[rowPivotIdx][i] / elements[rowPivotIdx][rowPivotIdx];
        }
    }
    public void constantMultiply(double scalar){
        int i,j;
        for(i = 0; i < Row; i++){
            for(j = 0; j < Col; j++){
                elements[i][j] *= scalar;
            }
        }
    }
    public void OBEReduksi(int rowCentre) {
    //OBE untuk seluruh baris
        for (int i = 0; i < Col; i++) {
            if (i == rowCentre) {
                continue;
            }
            OBE(i, rowCentre);
        }
    }
    /* ============== ADVANCED MATRIX =================*/
    public Matrix transposeMatrix() {
        Matrix trans = new Matrix(Row,Col);
        for (int i =0; i<Row;i++){
            for (int j=0; j<Col; j++) {
                trans.elements[i][j] = elements[j][i];
            }
        }
        return trans;
    }

    public void setIdentityMatrix(){
        int i, j;
        for (i = 0; i < Row; i++){
            for (j = 0; j < Col; j++){
                if(i == j){elements[i][j] = 1;}
                else{ elements[i][j] = 0;}
            }
        }
    }
    
    /* ===========================================*/
    /*                 MATRIX I/O                 */
    /* ===========================================*/

    /*=================== INPUT ==================*/
    public void keyboardInputMatrix(){
        scan = new Scanner(System.in);
        System.out.println("Masukkan elemen-elemen matriks");
        for (int i = 0; i < Row; i++){
            for (int j = 0; j < Col; j++){
                elements[i][j] = scan.nextDouble();
            }
        }
        System.out.println(""); // Biar ada newline (styling)
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

    public void terminalOutputMatrix() {
        for (int i = 0; i < Row; i++){
            for (int j = 0; j < Col; j++){
                System.out.print(elements[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public void fileOutputMatrix(){
        
    }
    public void printRow(int idx) {
        System.out.println(Arrays.toString(getRow(idx)));
     }
    public void printCol(int idx){
        double col[] = getCol(idx);
        int i;
        for (i = 0; i < Row;i++) {
            System.out.print("[");
            System.out.print(col[i]);
            System.out.print("]\n");
        }
    }

    public static void main(String[] args) {
    //     Matrix M = new Matrix(3, 3);
    //     M.keyboardInputMatrix();
    //     Matrix M2 = M.copyMatrix();
    //     M.terminalOutputMatrix();
    //     // M.swapRows(0, 1);
    //     // M.terminalOutputMatrix();
    //     // M.multiplyRow(1, 2);
    //     // M.terminalOutputMatrix();
    //     M2.terminalOutputMatrix();
    //     Matrix M3 = M.transposeMatrix();
    //     M3.terminalOutputMatrix();
    //     M3.OBEReduksi(0);
    //     M3.OBEReduksi(1);
    //     M3.OBEReduksi(2);
    //     M3.terminalOutputMatrix();
    
    Matrix M;
    M = fileInputMatrix();
    M.terminalOutputMatrix();
    }
}
