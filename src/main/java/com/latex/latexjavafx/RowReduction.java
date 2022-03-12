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

    private static void printMartix(Fraction[][] matrix) {
        for (Fraction[] doubles : matrix) {
            for (Fraction value : doubles) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
        System.out.println("==============");
    }

    public static void rowReducedEchelon(double[][] v, BiConsumer<Fraction[][], String> callback) {
        rowReducedEchelonFraction(Utils.toFractionMatrix(v), callback);
    }

    private static void rowReducedEchelonFraction(Fraction[][] v, BiConsumer<Fraction[][], String> callback) {
        final int rows = v.length;
        final int columns = v[0].length;
        int currentRow = 0;
        int[] pivotPosition = new int[v.length];
        for (int column = 0; column < columns; column++) {
            int nonZeroIndex = -1;
            Fraction minValue = Fraction.MAX_VALUE;
            for (int row = currentRow; row < rows; row++) {
                if (!v[row][column].equals(Fraction.ZERO) && v[row][column].abs().compareTo(minValue) < 0) {
                    minValue = v[row][column];
                    nonZeroIndex = row;
                } else if (v[row][column].abs().equals(Fraction.ONE)) {
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
                    callback.accept(v, "R_" + (nonZeroIndex + 1) + "\\leftrightarrow R_" + (currentRow + 1));
                }
                Fraction pivot = v[currentRow][column];
                pivotPosition[currentRow] = column;
                // multiply to create zeros
                for (int row = currentRow + 1; row < rows; row++) {
                    if (!v[row][column].equals(Fraction.ZERO)) {
                        Fraction multiply = v[row][column].divide(pivot).multiply(-1);
                        System.out.println("Multiply is " + multiply);
                        for (int col = 0; col < columns; col++) {
                            v[row][col] = v[row][col].add(v[currentRow][col].multiply(multiply));
                        }
                        System.out.println("Multiply is " + multiply);

                        printMartix(v);
                        callback.accept(v, "R_" + (row + 1) + " \\mathrel{{+}{=}} " + multiply.toFracLatex() + " * R_" + (currentRow + 1));
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
            Fraction pivot = v[currentRow][column];
            if(pivot.equals(Fraction.ONE)) // skip if the pivot is one
                continue;
            for (int col = column; col < columns; col++) {
                System.out.println("Dividing " + v[currentRow][col] + " with " + pivot);
                v[currentRow][col] = v[currentRow][col].divide(pivot);
                System.out.println("Got " + v[currentRow][col]);
            }
            System.out.println("R_" + (currentRow + 1) + " /= " + pivot.toFracLatex());
            callback.accept(v, "R_" + (currentRow + 1) + " /= " + pivot.toFracLatex());
            printMartix(v);

            for (int row = currentRow - 1; row >= 0; row--) {
                if (!v[row][column].equals(Fraction.ZERO)) {
                    Fraction multiply = v[row][column].multiply(-1);
                    System.out.println("Adding row " + currentRow + " to row " + row + " * " + multiply);
                    for (int col = 0; col < columns; col++) {
                        v[row][col] = v[row][col].add(v[currentRow][col].multiply(multiply));
                    }
                    printMartix(v);
                    callback.accept(v, "R_" + (row + 1) + " \\mathrel{{+}{=}} " + multiply.toFracLatex() + " * R_" + (currentRow + 1));
                }
            }
        }
    }

    private static void swapRows(Fraction[][] v, int i, int j) {
        System.out.println("Swaping row " + (i + 1) + " with " + (j + 1));
        var temp = v[i];
        v[i] = v[j];
        v[j] = temp;
    }

}
