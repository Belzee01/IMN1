package com.test;

import java.util.Arrays;

public class Poisson {

    private final Double xStart = -72.0;
    private final Double xEnd = 72.0;

    private final Double yStart = -72.0;
    private final Double yEnd = 72.0;

    private final static Double delta = 0.75;

    private Integer xPoints;
    private Integer yPoints;

    private double[][] potential;
    private double[][] density;

    private final static double[] OMEGA = {0.6, 0.8, 1.0, 1.5, 1.9, 1.95, 1.99};

    public Poisson() {
        this.xPoints = Math.toIntExact(Math.round(((xEnd - xStart) / delta)) + 1);
        this.yPoints = Math.toIntExact(Math.round(((yEnd - yStart) / delta)) + 1);

        this.potential = new double[this.xPoints][this.yPoints];
        this.density = new double[this.xPoints][this.yPoints];

    }

    private double getDensity(double x, double y) {
        int i = (int) ((this.xPoints - 1) - (this.xEnd - x) / delta);
        int j = (int) ((this.yPoints - 1) - (this.yEnd - y) / delta);

        this.density[i][j] = (1.0 / (Math.pow(25.0, 2.0) * Math.PI)) * Math.exp(-(Math.pow(x / 25.0, 2.0)) - (Math.pow(y / 25.0, 2.0)));

        return this.density[i][j];
    }

    private double getPotential(int x, int y, int omg) {
        double i = x * delta + this.xStart;
        double j = y * delta + this.yStart;

        /*


         */

        double tempOMEGa = (1.0 - OMEGA[omg]);

        double pot1 = this.potential[x][y];
        double pot2 = this.potential[x + 1][y];
        double pot3 = this.potential[x - 1][y];
        double pot4 = this.potential[x][y + 1];
        double pot5 = this.potential[x][y - 1];

        double den1 = getDensity(i, j);

        this.potential[x][y] =  tempOMEGa * pot1 + OMEGA[omg] * (pot2 + pot3 + pot4 + pot5 + den1 * Math.pow(delta, 2.0)) / 4.0;
        return this.potential[x][y];
    }

    private double diffX(int x, int y, int omg) {
        return (getPotential(x + 1, y, omg) - getPotential(x, y, omg)) / delta;
    }

    private double diffY(int x, int y, int omg) {
        return (getPotential(x, y + 1, omg) - getPotential(x, y, omg)) / delta;
    }

    /*
    Dopisać wypełnianie potencjału oraz wypełnianie gestości przed przystąpieniem do liczenia następnych wartości całki
    Konkretnie stworzyć mapy potencjału i gęstości

     */

    public void calculateIntegral() {
        double a = 0.0;
        int k = 0;

        final int omg = 0;

        for (double i = this.xStart; i < this.xEnd-2*delta; i += delta) {
            for (double j = this.yStart; j < this.yEnd-2*delta; j += delta) {
                int xIndex = (int) ((this.xPoints ) - (this.xEnd - i) / delta);
                int yIndex = (int) ((this.yPoints ) - (this.yEnd - j) / delta);

                double temp = (0.5 * ((Math.pow(diffX(xIndex, yIndex, omg), 2.0) + Math.pow(diffY(xIndex, yIndex, omg), 2.0))) - getDensity(i, j) * getPotential(xIndex, yIndex, omg)) * delta * delta;
//                if ((Math.abs(a - temp) < 0.00000001) || k > 1000) {
//                    break;
//                }

                System.out.println("Wartosc iteracji = " + temp + " Iteracja : " + i + " " + j);
                a += temp;
                k++;
            }
        }
        System.out.println("Zbieznosc : a = " + a + " k = " + k);
    }
}
