package com.latex.latexjavafx;

import java.math.BigDecimal;

public class Utils {

    public static String getStringFromDouble(double value) {
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(value));
        int intValue = bigDecimal.intValue();
        var decimal = bigDecimal.subtract(
                new BigDecimal(intValue));
        if (decimal.compareTo(BigDecimal.ZERO) == 0) {
            return String.valueOf(intValue);
        }
        return String.format("%.2f", value);
    }

    public static Fraction[][] toFractionMatrix(double[][] v) {
        Fraction[][] fraction = new Fraction[v.length][v[0].length];
        for (int i = 0; i < v.length; i++)
            for (int j = 0; j < v[0].length; j++)
                fraction[i][j] = Fraction.of(v[i][j]);
        return fraction;
    }

    public static String parseMatrixToLatex(double[][] doubles) {
        return parseMatrixToLatex(toFractionMatrix(doubles));
    }
    public static String parseMatrixToLatex(Fraction[][] v) {
        StringBuilder answer = new StringBuilder("\\begin{bmatrix}\n");
        for (Fraction[] doubles : v) {
            for (int j = 0; j < doubles.length; j++) {
                answer.append(doubles[j].toFracLatex());
                if (j + 1 != doubles.length)
                    answer.append(" & ");
            }
            answer.append("\\\\");
        }
        answer.append("\\end{bmatrix}");
        return answer.toString();
    }
}
