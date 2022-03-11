package com.latex.latexjavafx;

import java.util.Objects;

/**
 * represents a fraction as a/b
 */
public class Fraction implements Comparable<Fraction> {
    public static final Fraction ZERO = new Fraction(0);
    public static final Fraction MAX_VALUE = new Fraction(Double.MAX_VALUE);
    public static final Fraction MIN_VALUE = new Fraction(Double.MIN_VALUE);
    public static final Fraction ONE = new Fraction(1);

    private double a, b;

    private Fraction(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public Fraction(int number) {
        this.a = number;
        this.b = 1;
    }

    public Fraction(double number) {
        this.a = number;
        this.b = 1;
    }

    public static Fraction of(double number) {
        return new Fraction(number);
    }

    public double getNominator() {
        return a;
    }

    public double getDenominator() {
        return b;
    }

    public Fraction add(Fraction other) {
        Fraction f = new Fraction(a * other.b + b * other.a, b * other.b);
        f.simplify();
        return f;
    }

    public Fraction subtract(Fraction other) {
        Fraction f = new Fraction(a * other.b - b * other.a, b * other.b);
        simplify();
        return f;
    }

    public Fraction multiply(double constant) {
        Fraction f = new Fraction(a * constant, b);
        f.simplify();
        return f;
    }

    public Fraction multiply(Fraction other) {
        Fraction f = new Fraction(a * other.a, b * other.b);
        f.simplify();
        return f;
    }

    public void inverse() {
        var temp = b;
        b = a;
        a = temp;
        simplify();
    }

    public Fraction divide(Fraction other) {
        Fraction f = new Fraction(a * other.b, b * other.a);
        f.simplify();
        return f;
    }


    public Fraction abs() {
        Fraction f = new Fraction(Math.abs(a), Math.abs(b));
        f.simplify();
        return f;
    }

    public String toFracLatex() {
        if (a == 0)
            return "0";
        if (Utils.getStringFromDouble(this.b).equals("1"))
            return Utils.getStringFromDouble(this.a);
        else if (Utils.getStringFromDouble(this.b).equals("-1"))
            return Utils.getStringFromDouble(-this.a);
        double value = (a / b);
        if (value < 0)
            return String.format("- \\frac {%s} {%s}", Utils.getStringFromDouble(Math.abs(this.a)), Utils.getStringFromDouble(Math.abs(this.b)));
        return String.format("\\frac {%s} {%s}", Utils.getStringFromDouble(Math.abs(this.a)), Utils.getStringFromDouble(Math.abs(this.b)));
    }

    private int gcd(int a, int b) {
        int r = a % b;
        while (b != 0) {
            a %= b;
            int temp = b;
            b = a;
            a = temp;
        }
        return a;
    }

    public void simplify() {
        double ans = this.a / this.b;
        if (ans % 1 == 0) {
            this.a = ans;
            this.b = 1;
        }else if(this.a % 1 == 0 && this.b % 1 == 0) {
            int gcd = gcd((int) a, (int) b);
            this.a /= gcd;
            this.b /= gcd;
        }
    }

    public String toString() {
        if (a == 0)
            return "0";
        if (Utils.getStringFromDouble(this.b).equals("1"))
            return Utils.getStringFromDouble(this.a);
        else if (Utils.getStringFromDouble(this.b).equals("-1"))
            return Utils.getStringFromDouble(-this.a);
        double value = a / b;
        if (value < 0)
            return String.format("-%s\\%s", Utils.getStringFromDouble(Math.abs(this.a)), Utils.getStringFromDouble(Math.abs(this.b)));
        return String.format("%s\\%s", Utils.getStringFromDouble(Math.abs(this.a)), Utils.getStringFromDouble(Math.abs(this.b)));
    }

    @Override
    public int compareTo(Fraction other) {
        double value = a * other.b - b * other.a;
        if (value == 0)
            return 0;
        else if (value < 0)
            return -1;
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fraction)) return false;
        Fraction fraction = (Fraction) o;
        return compareTo(fraction) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}
