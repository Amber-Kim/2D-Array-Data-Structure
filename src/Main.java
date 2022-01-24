/*
* Name: Jeong eun Kim
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    /**
     * row : ow from two-dimensional array
     * col : column from two-dimensional array
     * minHeight : minimum height between two peaks
     * minDistance : minimum distance between two peaks
     * radius : exclusion radius used for peak detection
     */
    private static int rows;
    private static int cols;
    private static int minHeight;
    private static double minDistance;
    private static int radius;

    // Read the file and call all methods to print answers
    public static void main(String[] args) {
        File file = new File("ELEVATIONS.TXT");

        long startTime = System.nanoTime();

        int[][] array = readFile(file);
        int[] lowestElevation = findLowest(array);
        Peak[] peaks = findLocalPeak(array,minHeight,radius);
        Peak[] closestPeaks = findClosest(peaks);
        int[] commonElevation = findCommon(array);

        long stopTime = System.nanoTime();

        System.out.printf("\n[Time = %d us]\n", (stopTime - startTime) / 1000);
        System.out.println("Question 1 : The lowest peak is: "+ lowestElevation[0] +" " + "it occurs "+ lowestElevation[1]+" times in the map.");
        System.out.println("Question 2 : The total number of peaks is : "+ peaks.length);
        System.out.println(Arrays.toString(peaks));
        System.out.println("Question 3 : The two closest local peaks are between (rows: "
                +closestPeaks[0].getRow()+", cols: "+ closestPeaks[0].getCol()+"'s elevation is "+ closestPeaks[0].getPeakNum()+" )"+ " and (rows: "+
                closestPeaks[1].getRow()+", cols: " + closestPeaks[1].getCol()+"'s elevation is "+ closestPeaks[1].getPeakNum()+" )" +"\n\t\t\t"+
                " The minimum distance between two peaks = " + String.format("%.2f", minDistance));
        System.out.println("Question 4 : The most common elevation occurs "+ commonElevation[0]+" times and is " + commonElevation[1]);

    }

    /** Read the file and define it as a 2D array
     * Since the first line of the file is given information, it is put into the matching variable and executed from the next line.
     * @param file ELEVATIONS.TXT file
     * @return Two-dimensional array
     */
    private static int[][] readFile(File file) {
        int[][] twoArray = new int[0][0];
        try {
            Scanner sc = new Scanner(file);
            rows = sc.nextInt();
            cols = sc.nextInt();
            minHeight = sc.nextInt();
            radius = sc.nextInt();

            twoArray = new int[rows][cols];
            int[] readArray = new int[rows * cols];
            int rownum = 0;
            int colnum = 0;

            while (sc.hasNextInt()) {
                int temp = sc.nextInt();
                readArray[rownum++] = temp;
            }
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    twoArray[i][j] = readArray[colnum++];
                }
            }
            sc.close();
        } catch (FileNotFoundException ex)
        {
            System.out.println("Could not find " + ex);
            System.exit(0);  // Terminates code (could use return)
        }
        return twoArray;
    }

    /** Find the lowest peak value and count how many times occur.
     * @param twoArray Two-dimensional array
     */
    private static int[] findLowest(int[][] twoArray) {

        int min = 112000;
        int[] lowest = new int[min];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if(twoArray[row][col] <= min){
                    min = twoArray[row][col];
                    lowest[min] ++;
                }
            }
        }
        return new int[]{min, lowest[min]};
    }

    /**
     * Find all the local peaks where the peak elevation is greater or equal to 111300 using an exclusion radius of 13.
     * @param twoArray Two-dimensional array
     * @param minHeight The minimum height
     * @param radius Exclusion radius
     * @return An array with peaks
     */
        private static Peak[] findLocalPeak(int[][] twoArray, int minHeight, int radius) {
        int count = 0;
        boolean localPeak;
        Peak[] peaks = new Peak[rows*cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if(twoArray[row][col] >= minHeight){
                    if(row >= radius && row < rows-radius && col>= radius && col< cols-radius){
                        localPeak = true;
                        for(int i= row-radius; ( i<= row + radius) && localPeak; i++){
                            for(int j= col-radius; ( j<= col+radius) && localPeak; j++){
                                if(i != row || j != col){
                                    if(twoArray[row][col] <= twoArray[i][j]){
                                        localPeak = false;
                                    }
                                }

                            }
                        }
                        if(localPeak){
                            Peak peak = new Peak(twoArray[row][col], row, col);
                            peaks[count] = peak;
                            count++;
                        }
                    }
                }
            }
        }
        peaks = Arrays.copyOf(peaks,count);
        return peaks;
    }

    /** Find the row and column of the two local peakArray with the lowest distance between them.
     *  @param peakArray an array of peaks
     */
    private static Peak[] findClosest(Peak[] peakArray) {
        minDistance = Double.MAX_VALUE;
        Peak peak1 = null, peak2 = null;
        for (int i = 0; i < peakArray.length-1; i++) {
            for (int j = i+1 ; j < peakArray.length; j++) {
                double distance = Math.sqrt((peakArray[i].getRow()-peakArray[j].getRow()) * (peakArray[i].getRow()-peakArray[j].getRow())+
                        (peakArray[i].getCol()-peakArray[j].getCol()) * (peakArray[i].getCol()-peakArray[j].getCol()));
                if(distance <= minDistance){
                    minDistance = distance;
                    peak1 = peakArray[i];
                    peak2 = peakArray[j];
                }
            }
        }
        return new Peak[]{peak1, peak2};
    }

    /** Find the most common elevation value and count in the data set
     * @param array 2D int array
     */
    private static int[] findCommon(int[][] array) {
        int[] comArray = new int[112000];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                comArray[array[row][col]]++;
            }
        }
        int count = 0;
        int comValue = 0;
        for(int i= 20000; i< comArray.length; i++){
            if(comArray[i] > count){
                count = comArray[i];
                comValue = i;
            }
        }
        return new int[]{count, comValue};
    }
}

