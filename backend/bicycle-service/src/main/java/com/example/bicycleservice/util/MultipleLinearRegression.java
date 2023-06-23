package com.example.bicycleservice.util;

public class MultipleLinearRegression {
    private final double[][] x;
    private final double[] y;
    private final double[] coefficients;
    private double loss;

    public MultipleLinearRegression(double[][] x, double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("The number of rows in x must be the same as the length of y.");
        }
        this.x = x;
        this.y = y;
        coefficients = calculateCoefficients();
        loss = calculateLoss();
    }

    private double[] calculateCoefficients() {
        int n = x[0].length; // number of independent variables
        int m = x.length; // number of data points

        // Calculate the transpose of X
        double[][] xT = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                xT[i][j] = x[j][i];
            }
        }

        // Calculate the product of X transpose and X
        double[][] xTx = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < m; k++) {
                    xTx[i][j] += xT[i][k] * x[k][j];
                }
            }
        }

        // Calculate the inverse of X transpose multiplied by X
        double[][] xTxInverse = invert(xTx);

        // Calculate the product of X transpose and Y
        double[] xTy = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                xTy[i] += xT[i][j] * y[j];
            }
        }

        // Calculate the coefficients vector
        double[] coefficients = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                coefficients[i] += xTxInverse[i][j] * xTy[j];
            }
        }

        return coefficients;
    }

    private double calculateLoss() {
        int n = x.length;
        int m = x[0].length;
        double[] predictions = new double[n];
        for (int i = 0; i < n; i++) {
            double prediction = 0.0;
            for (int j = 0; j < m; j++) {
                prediction += coefficients[j] * x[i][j];
            }
            predictions[i] = prediction;
        }

        double sumSquaredErrors = 0.0;
        for (int i = 0; i < n; i++) {
            double error = y[i] - predictions[i];
            sumSquaredErrors += error * error;
        }

        return sumSquaredErrors / n;
    }

    private double[][] invert(double[][] A) {
        int n = A.length;
        double[][] B = new double[n][n];

        // Initialize B to the identity matrix
        for (int i = 0; i < n; i++) {
            B[i][i] = 1.0;
        }

        // Perform Gaussian elimination with partial pivoting
        for (int i = 0; i < n; i++) {
            int maxRow = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(A[j][i]) > Math.abs(A[maxRow][i])) {
                    maxRow = j;
                }
            }

            // Swap the rows
            double[] temp = A[i];
            A[i] = A[maxRow];
            A[maxRow] = temp;
            temp = B[i];
            B[i] = B[maxRow];
            B[maxRow] = temp;

            // Divide the pivot row by the pivot element
            double pivot = A[i][i];
            for (int j = i; j < n; j++) {
                A[i][j] /= pivot;
            }
            for (int j = 0; j < n; j++) {
                B[i][j] /= pivot;
            }

            // Subtract the pivot row from all other rows
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    double factor = A[j][i];
                    for (int k = i; k < n; k++) {
                        A[j][k] -= factor * A[i][k];
                    }
                    for (int k = 0; k < n; k++) {
                        B[j][k] -= factor * B[i][k];
                    }
                }
            }
        }
        return B;
    }

    public double predict(double[] input) {
        if (input.length != x[0].length) {
            throw new IllegalArgumentException("The length of input must be the same as the number of columns in x.");
        }
        double prediction = 0.0;
        for (int i = 0; i < coefficients.length; i++) {
            prediction += coefficients[i] * input[i];
        }
        return prediction;
    }

    public double getLoss() {
        return loss;
    }

    public static void main(String[] args) {
//        double[][] x = {
//                {1.0, 2.0, 3.0, 4.0, 5.0, 6.0},
//                {2.0, 3.0, 1.0, 5.0, 4.0, 6.0},
//                {3.0, 1.0, 2.0, 6.0, 5.0, 4.0},
//                {4.0, 5.0, 6.0, 1.0, 2.0, 3.0},
//                {5.0, 6.0, 4.0, 2.0, 1.0, 3.0},
//                {6.0, 4.0, 5.0, 3.0, 1.0, 2.0}
//        };
//        double[] y = {10.0, 20.0, 15.0, 24.0, 18.0, 22.0};
        double[][] x = Statistics.getX();
        double[] y = Statistics.getY();
        MultipleLinearRegression mlr = new MultipleLinearRegression(x, y);
        double loss = mlr.getLoss();
        double[] input = {1.0, 3.0, 2.0, 4.0, 5.0, 6.0}; // input for prediction
        double prediction = mlr.predict(input);
        System.out.println("Prediction result: " + prediction);
        System.out.println("Loss: " + loss);
    }
}