package svm.alg;

import svm.SVM;

public class LogisticRegression extends Algorithm {

    float[] weights = new float[dim + 1];
    float bias = 0;

    public LogisticRegression(SVM svm) {
        super(svm);
        if (svm.ind.V != null) {
            name = "Logistic Regression";
            svm.outd.algorithm = name;
            svm.outd.showInputData();
        }
    }

    void showStage(long p) {
        svm.control.ta.append("Stage " + p + "\n");
        String s = "";
        for (int j = 0; j < weights.length; j++)
            s += "w[" + j + "] = " + weights[j] + "; ";
        svm.control.ta.append(s + "\n");

        if (dim == 2)
            svm.design.setPointsOfLine(weights);
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
        }
    }

    void showOutput() {
        svm.outd.w = weights;
        svm.outd.accuracy = getAccuracy(weights);
        svm.outd.showInputData();
        svm.outd.showOutputData();
        svm.design.repaint();
    }

    public boolean checkSol() {
        boolean e = false;

        float[] gradientLossW = new float[dim];
        float gradientLossB = 0;
        float z = bias;

        for (int i = 0; i < N; i++) {
            z += scalarProduct(weights, svm.ind.V[i].X);
            float probability = sigmoid(z);
            
            float error = svm.ind.V[i].cl.Y - probability;
            
            if (Math.abs(error) >= 0.999) {
                e = true;
            }

            gradientLossB += error;

            for (int j = 0; j < dim; j++) {
                gradientLossW[j] += svm.ind.V[i].X[j] * error / N;
            }     
        }

        for (int j = 0; j < dim; j++) {
            weights[j] -= gradientLossW[j] * eta;
        }

        
        bias -= gradientLossB * eta / N;
        
        if(e){
            weights[dim] += bias;
        }

        return e;
    }

    private float scalarProduct(float[] w, float[] x) {
        float sum = 0;

        for (int i = 0; i < x.length; i++) {
            sum += w[i] * x[i];
        }

        return sum;
    }

    private float sigmoid(float z) {
        //float s = (float)(1.0 / (1.0 + Math.exp(-z)));

        float s =(float) (1.0 / (1.0 + Math.abs(z))); //alternative

        return s;
    }

    public void run() {
        t = System.currentTimeMillis();
        boolean flag = false;

        float[] weightsCopy = new float[dim + 1];
        float biasCopy = bias;

        for (int j = 0; j < dim; j++) {
            weightsCopy[j] = weights[j] = -0.5f + (float) Math.random();
        }
        weightsCopy[dim] = weights[dim] = bias;

        for (long p = 1; p <= P; p++) {
            boolean erori = checkSol();

            // to show the last version at least
            if(p == P-1){
                erori = false;
            }
            if (!erori) {
                svm.outd.stages_count = p;
                svm.outd.computing_time = System.currentTimeMillis() - t;

                svm.design.calculates = false;
                showOutput();

                flag = true;
                break;
            }
        }

        if (!flag)
            System.out.println(P + " stages have passed. Increase the number of stages and reloaded.");
        else {
            bias = biasCopy;
            for (int i = 0; i <= dim; i++) {
                weights[i] = weightsCopy[i];
            }

            for (long p = 1; p <= P; p++) {
                boolean erori = checkSol();

                showStage(p);

                // to show the last version at least
                if(p == P-1){
                    erori = false;
                }

                if (!erori) {
                    svm.control.start.setLabel("Start Simulation");

                    showOutput();

                    break;
                }
            }
        }
        svm.control.start.enable(false);
    }

}