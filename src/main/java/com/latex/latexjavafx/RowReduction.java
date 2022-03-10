package com.latex.latexjavafx;

import java.util.function.BiConsumer;

public class RowReduction {

    public static void main(String[] args) {
        double[][] matrix = {
                {0, 2, 4, 24, 231, 12},
                {1, 2, 3, 0, 0, 0},
                {2, 1, 0, 0, 0, 0},
                {0, 1, 3, 0, 0, 0},
        };
    }

    private static void printMartix(double[][] matrix) {
        for (double[] doubles : matrix) {
            for (double value : doubles) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
        System.out.println("==============");
    }

    public static void rowReducedEchelon(double[][] v, BiConsumer<double[][], String> callback) {
        final int rows = v.length;
        final int columns = v[0].length;
        int currentRow = 0;
        int[] pivotPosition = new int[v.length];
        for (int column = 0; column < columns; column++) {
            int nonZeroIndex = -1;
            double minValue = Double.MAX_VALUE;
            for (int row = currentRow; row < rows; row++) {
                if (v[row][column] != 0 && Math.abs(v[row][column]) < minValue) {
                    minValue = v[row][column];
                    nonZeroIndex = row;
                } else if (Math.abs(v[row][column]) == 1) {
                    nonZeroIndex = row;
                    break;
                }
            }
            System.out.println("For row " + currentRow + " we have column : " + nonZeroIndex);
            printMartix(v);
            if (nonZeroIndex != -1) {
                // swap
                if (nonZeroIndex != currentRow) {
                    swapRows(v, nonZeroIndex, currentRow);
                    printMartix(v);
                    callback.accept(v, "Row swap " + (nonZeroIndex + 1) + " with " + (currentRow + 1));
                }
                double pivot = v[currentRow][column];
                pivotPosition[currentRow] = column;
                // multiply to create zeros
                for (int row = currentRow + 1; row < rows; row++) {
                    if (v[row][column] != 0) {
                        double multiply = -v[row][column] / pivot;
                        System.out.println("Row " + currentRow + " to row " + row + " * " + multiply);
                        for (int col = 0; col < columns; col++) {
                            v[row][col] += v[currentRow][col] * multiply;
                        }
                        printMartix(v);
                        callback.accept(v,"Row " + (row + 1) + " = " + multiply + " * Row " + (currentRow + 1));
                    }
                }
                printMartix(v);
                currentRow++;// pivot is at [0][column]
            }
        }
        System.out.println("ECHELON FORM");
        printMartix(v);
        currentRow--;
        System.out.println(currentRow);
        for (; currentRow >= 0; currentRow--) {
            int column = pivotPosition[currentRow];
            System.out.println("The column is " + column);
            System.out.println("Divide row " + currentRow + " with " + v[currentRow][column]);
            double pivot = v[currentRow][column];
            for (int col = column; col < columns; col++)
                v[currentRow][col] /= pivot;
            printMartix(v);
            callback.accept(v,"Divide row " + (currentRow + 1) + " with " + pivot);

            for (int row = currentRow - 1; row >= 0; row--) {
                if (v[row][column] != 0) {
                    double multiply = -v[row][column];
                    System.out.println("Adding row " + currentRow + " to row " + row + " * " + multiply);
                    for (int col = 0; col < columns; col++) {
                        v[row][col] += v[currentRow][col] * multiply;
                    }
                    printMartix(v);
                    callback.accept(v,"Row " + (row + 1) + " = " + multiply + " * Row " + (currentRow + 1));
                }
            }
        }
    }

    private static void swapRows(double[][] v, int i, int j) {
        System.out.println("Swaping row " + (i + 1) + " with " + (j + 1));
        var temp = v[i];
        v[i] = v[j];
        v[j] = temp;
    }

}
