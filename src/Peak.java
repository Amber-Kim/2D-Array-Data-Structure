/*
 * Name: Jeong eun Kim
 */

public class Peak {
    /**
     * peaknum : number of the peak
     * row : the row from two-dimensional array
     * col : the column from two-dimensional array
     */
    private final int peakNum;
    private final int row;
    private final int col;

    /* Constructor of Peak */
    public Peak(int peakNum, int row, int col) {
        this.peakNum = peakNum;
        this.row = row;
        this.col = col;
    }

    /* Getter of Peak's attributes*/
    public int getPeakNum() {
        return peakNum;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String toString(){
        return "\nPeak number : " + peakNum + "(row: " + row + ", col " + col+ ")";
    }
}
