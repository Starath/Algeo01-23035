package functions;
import matrix.*;

public class Determinan {
    public static double detByGauss (Matrix matrix){
        Matrix GaussMatrix = matrix.copyMatrix();
        int i, row, col;
        double scalar, det;
        det = 1;
        for(col = 0; col < GaussMatrix.colCount(); col++){                
            row = col;
            while((row < GaussMatrix.rowCount()) && (GaussMatrix.getElmt(row, col) == 0)){ /*asumsikan matriks persegi */
                row++;
            }

            if (row < GaussMatrix.rowCount()){ /*Mencari indeks tidak nol pertama */
                GaussMatrix.swapRows(row, col);
                det *= (-1);
            }
            else{
                return 0; /*kolom nol semua -> indeks out of length -> det = nol*/
            }

            for(i = col+1; i < GaussMatrix.rowCount(); i++){ /*membuat angka di bawah pivot menjadi nol */
                scalar = (GaussMatrix.getElmt(i, col) / GaussMatrix.getElmt(col, col)) * (-1);
                GaussMatrix.addRows(i, col, scalar);
            }

            det *= GaussMatrix.getElmt(col, col);
            // System.out.print(" Determinan : [");
            // System.out.print(det);
            // System.out.print("]\n");

            // terminalOutputMatrix();
        }
        return det;
    }

    public double detByCofactor(Matrix matrix){
        // Matrix CofactorM = copyMatrix();
        double det = 0;
        double cofactor;
        int j;
        /* Rekursi anjay */
        if (matrix.colCount() == 1){
            return (matrix.getElmt(0, 0));
        }

        for(j=0; j<matrix.colCount()-1; j++){

            if((0+j)%2 == 0){
                // System.out.print(cofactor);
                cofactor = detByCofactor(matrix.getMinor(0, j));
            }
            else{
                cofactor = (-1)*(detByCofactor(matrix.getMinor(0, j)));
            }
            det += (matrix.getElmt(0, j)* cofactor);
            // System.out.print(" Determinan : [");
            // System.out.print(det);
            // System.out.print("]\n");

            // terminalOutputMatrix();
        }
        return det;
    }

    public static void main(String[] args){

    }

    
}
