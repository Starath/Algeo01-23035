package matrix;

public class Matrix {
    // Attributes
    double[][] elements;
    int Row; // Jumlah baris
    int Col; // Jumlah kolom

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
    public void swapCols(int col1, int col2){
        double tempCol;
        for (int i =0; i < Row; i++) {
            tempCol = elements[i][col2];
            elements[i][col2] = elements[i][col1];
            elements[i][col1] = tempCol;
        }     
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
    public void constantMultiply(double scalar){
        int i,j;
        for(i = 0; i < Row; i++){
            for(j = 0; j < Col; j++){
                elements[i][j] *= scalar;
            }
        }
    }
    public Matrix rowCutter(int idxRow) {
        while(idxRow != (Row)-1){
            swapRows(idxRow+1, idxRow);
            idxRow++;
        }
        Matrix cutMatrix = new Matrix(Row-1,Col);
        int i, j;
        for (i= 0; i < Row-1; i++) {
            for (j= 0; j < Col; j++) {
                cutMatrix.elements[i][j] = elements[i][j];
            }
        }
        return cutMatrix;
    }
    public Matrix colCutter(int idxCol) {
        while(idxCol != (Col)-1){
            swapCols(idxCol+1, idxCol);
            idxCol++;
        }
        Matrix cutMatrix = new Matrix(Row,Col-1);
        int i, j;
        for (i= 0; i < Row; i++) {
            for (j= 0; j < Col-1; j++) {
                cutMatrix.elements[i][j] = elements[i][j];
            }
        }
        return cutMatrix;
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
    
    public void OBEReduksi(int rowCentre) {
        //OBE untuk seluruh baris
        for (int i = 0; i < Row; i++) {
            if (i == rowCentre) {
                continue;
            }
            OBE(i, rowCentre);
        }
    }
    public boolean isPivotZero(int checkRow) {
        return elements[checkRow][checkRow] == 0;
    }

    public void searchPivot(int colPivotIdx) {
        for (int i = 0; i < Row; i++) {
            if (!isPivotZero(i)) {
                swapRows(i, colPivotIdx);
                break;
            }
        }
    }

    public void OBE(int rowOBEIdx, int rowPivotIdx) {
    //Parameter rowOBEIdx yang di-OBE, rowPivotIdx "acuan"-nya
        double tumbal = elements[rowOBEIdx][rowPivotIdx];
        for (int i = 0; i < Col; i++) {
            elements[rowOBEIdx][i] = elements[rowOBEIdx][i] - tumbal * (elements[rowPivotIdx][i] / elements[rowPivotIdx][rowPivotIdx]);
        }
    }
}
