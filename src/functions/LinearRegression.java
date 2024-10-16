// package functions;

// import matrix.*;
package functions;
import matrix.*;
public class LinearRegression {
    public static void multipleRegression(Matrix M, Matrix X){
      /* KAMUS LOKAL */
        String rumus, m;
        int i, j, k, l, idxCol;
        int n = X.Col; // Jumlah peubah x
        double temp;
        double taksiran; // index element
        Matrix mHasil; // Matrix setelah Normal Estimation Equation
        Matrix mHasilSPL; // Matrix hasil SPL
    
        /* ALGORITMA */
        // Normal Estimation Equation
        mHasil = new Matrix(n + 1, n + 2);
        for (i = 0; i < mHasil.Row; i++) {
            idxCol = i - 1;
            for (j = 0; j < M.Col; j++) {
                l = j + 1;
                temp = 0;
                for (k = 0; k < M.Row; k++) {
                    if (i != 0) {
                        temp += M.getElmt(k, j) * M.getElmt(k, idxCol);
                    } else {
                        temp += M.getElmt(k, j);
                    }
                }
                if (j == 0 && i != 0) {
                    mHasil.setElmt(i, j, mHasil.getElmt(j, i));
                }
                if (j == 0 && i == 0) {
                    mHasil.setElmt(i, j, M.Row);
                }
                mHasil.setElmt(i, l, temp);
            }
        }
  
        // SPL pada Matrix hasil Normal Estimation Equation
        mHasilSPL = SPL.GaussElim(mHasil);

        // rumus polinom Y
    }
    

}    