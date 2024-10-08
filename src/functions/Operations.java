package functions;
import matrix.*;

public class Operations {
    // Prekondisi M1 dan M2 valid untuk di multiply
    public static Matrix multiplyMatrix(Matrix M1, Matrix M2){
        /*  Penjelasan sedikit (Standard Approach)
            Algoritma mengambil baris(for i) dan kolom(for j) matrix kemudian
            mengalikan elemennya satu per satu (for k), kompleksitas algoritma O(n^3)
        */
        double newElm;
        Matrix Mresult = new Matrix(M1.rowCount(),M2.colCount());
        int i, j, k;
        double[] currRow,currCol;
        for (i = 0; i < M1.rowCount(); i++){
            currRow = M1.getRow(i);
            for(j = 0; j < M2.colCount(); j++){
                newElm = 0;
                currCol = M2.getCol(j);
                for(k = 0; k < M1.colCount(); k++){
                    newElm += currRow[k] * currCol[k];
                }
                Mresult.setElmt(i, j, newElm);
            }
        }
        return Mresult;
    }

    public static void main(String[] args) {
        Matrix M1 = new Matrix(3,3);
        Matrix M2 = new Matrix(3,3);
        Matrix M3;
        M1.keyboardInputMatrix();
        M2.keyboardInputMatrix();
        M3 = multiplyMatrix(M1, M2);
        M3.terminalOutputMatrix();
    }
}
