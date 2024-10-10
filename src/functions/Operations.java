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
        for (i = 0; i < M1.rowCount(); i++){
            for(j = 0; j < M2.colCount(); j++){
                newElm = 0;
                for(k = 0; k < M1.colCount(); k++){
                    newElm += M1.getElmt(i, k) * M2.getElmt(k,j);
                }
                Mresult.setElmt(i, j, newElm);
            }
        }
        return Mresult;
    }

}
