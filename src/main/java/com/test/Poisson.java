package com.test;

import java.util.Arrays;

public class Poisson {

    private final static Double xStart = -72.0;
    private final static Double xEnd = 72.0;

    private final static Double yStart = -72.0;
    private final static Double yEnd = 72.0;

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

    public double getDensity(int x, int y) {
        return this.density[x][y] = (1.0/(Math.pow(25.0, 2.0)*Math.PI))* Math.exp(- (Math.pow(x/25.0, 2.0)) - (Math.pow(y/25.0, 2.0)));
    }

    public double getPotential(int x, int y, int omg) {
        return this.potential[x][y] = (1.0 - OMEGA[omg])*this.potential[x][y] + OMEGA[omg] * (this.potential[x+1][y] + this.potential[x-1][y] + this.potential[x][y+1] + this.potential[x][y-1] + this.density[x][y] * Math.pow(delta, 2.0))/4.0;
    }

    private double diffX(int x, int y) {
        return (this.potential[x+1][y] - this.potential[x][y])/delta;
    }

    private double diffY(int x, int y) {
        return (this.potential[x][y+1] - this.potential[x][y])/delta;
    }

    public void calculateIntegral() {
        double a = 0.0;


    }
}
