public class Matrix {
    // Attributes
    double[][] M;
    int Row;
    int Col;

    //Constructor
    public Matrix(int nRow, int nCol) {
        this.Row = nRow;
        this.Col = nCol;
        this.M = new double[nRow][nCol]
    }
}
