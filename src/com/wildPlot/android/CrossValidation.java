package com.wildplot.android;

import com.wildplot.MyMath.Matrix_d;
import com.wildplot.MyMath.Polynomial;
import com.wildplot.MyMath.Vector_d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class CrossValidation {
    private final static int X = 0;
    private final static int Y = 1;
    private final static int SEARCH_DEPTH = 5;
    private String m = "";
    private String lambda = "";
    private double[][] points;
    private int foldCnt;
    private ArrayList<ArrayList<Double>> xFolds = new ArrayList<ArrayList<Double>>();
    private ArrayList<ArrayList<Double>> yFolds = new ArrayList<ArrayList<Double>>();
    private double[][][] testSetsForAllFolds;
    private double[][][] validationSetsForAllFolds;
    
    
    
    public CrossValidation(double[][] points, int foldCnt) {
        super();
        this.points = points;
        this.foldCnt = foldCnt;
    }
    public void doCrossValidation(){
        ArrayList<Thread> threads = new ArrayList<Thread>();
        ArrayList<ValidationWorker> validationWorkers = new ArrayList<ValidationWorker>();
        createFolds();
        
        for(int i = 0; i< SEARCH_DEPTH; i++){
            ValidationWorker valWorker = new ValidationWorker(i, points);
            Thread thread = new Thread(valWorker);
            thread.start();
            threads.add(thread);
            validationWorkers.add(valWorker);
            System.err.println("started testing m=" + i);
        }
        
        HashMap<Integer, Boolean> didVerboseForM = new HashMap<Integer, Boolean>();
        int numberOfErrorsWorseThanBestM = 0;
        int currentBestM = 0;
        double currentError = Double.POSITIVE_INFINITY;
        do{
            for(int m = 0; m<validationWorkers.size(); m++){
                ValidationWorker valWorker =  validationWorkers.get(m);
                if(valWorker.isFinished()){
                    if(m < currentBestM){
                        continue;
                    }
                    
                    if(currentError > valWorker.getLastErrorLambda()){
                        if(didVerboseForM.get(m) == null){
                            System.out.println("Error for M=" + m + " : " + valWorker.getLastErrorLambda());
                            didVerboseForM.put(m, true);
                        }
                        currentError = valWorker.getLastErrorLambda();
                        currentBestM = m;
                        int valWorkerCount = validationWorkers.size();
                        int currentDepth = valWorkerCount - currentBestM;
                        int missingDepth = SEARCH_DEPTH-currentDepth;
                        if(missingDepth > 0){
                            for(int i = valWorkerCount; i< valWorkerCount+missingDepth; i++){
                                ValidationWorker valWorkerNew = new ValidationWorker(i, points);
                                Thread thread = new Thread(valWorkerNew);
                                thread.start();
                                threads.add(thread);
                                validationWorkers.add(valWorkerNew);
                                System.err.println("started testing m=" + i);
                            }
                        }
                    }else{
                        if(didVerboseForM.get(m) == null){
                            System.err.println("Error for M=" + m + " : " + valWorker.getLastErrorLambda());
                            didVerboseForM.put(m, true);
                        }
                        numberOfErrorsWorseThanBestM = m - currentBestM; 
                        //System.err.println("number of worse errors after best: " + numberOfErrorsWorseThanBestM);
                    }
                }else{
                    try {
                        Thread.sleep(100);
                        break;
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            
        }while(numberOfErrorsWorseThanBestM < SEARCH_DEPTH-1);
        
        
        ValidationWorker bestMValWorker = validationWorkers.get(currentBestM);
        
        lambda = bestMValWorker.getBestLambdaForM() +"";
        m = ""+currentBestM;
    }
    
    
    private void createFolds(){
        ArrayList<Integer> indexList = new ArrayList<Integer>();
        Random random = new Random();
        for(int i=0; i<points[0].length; i++){
            indexList.add(i);
        }
        
        int pointsPerFold = (int) Math.round(Math.floor(points[0].length*1.0/this.foldCnt));
        if(foldCnt >= points[0].length){
            foldCnt = points[0].length; //n-fold or leave one out cross validation
            pointsPerFold = 1;
        }
        prepareFolds();
        for(int i = 0; i<foldCnt-1; i++){
            ArrayList<Double> xFold = xFolds.get(i);
            ArrayList<Double> yFold = yFolds.get(i);
            
            for(int j = 0; j<pointsPerFold; j++){
                int index = random.nextInt(indexList.size());
                index = indexList.remove(index);
                xFold.add(points[X][index]);
                yFold.add(points[Y][index]);
            }
        }
        ArrayList<Double> xFold = xFolds.get(xFolds.size()-1);
        ArrayList<Double> yFold = yFolds.get(yFolds.size()-1);
        for(int index:indexList){
            xFold.add(points[X][index]);
            yFold.add(points[Y][index]);
        }
        
        validationSetsForAllFolds = new double[this.foldCnt][2][];
        testSetsForAllFolds = new double[this.foldCnt][2][];
        
        for(int validationFold=0 ; validationFold< this.foldCnt; validationFold++){
            validationSetsForAllFolds[validationFold][X] = new double[xFolds.get(validationFold).size()];
            validationSetsForAllFolds[validationFold][Y] = new double[yFolds.get(validationFold).size()];
            testSetsForAllFolds[validationFold][X] = new double[points[X].length - xFolds.get(validationFold).size()];
            testSetsForAllFolds[validationFold][Y] = new double[points[Y].length - yFolds.get(validationFold).size()];
            
            int validationIndex = 0;
            int testIndex = 0;
            for(int currentFold = 0; currentFold < foldCnt; currentFold++){
                for(int point=0; point < xFolds.get(currentFold).size(); point++){
                    if(currentFold == validationFold){
                        validationSetsForAllFolds[validationFold][X][validationIndex] = xFolds.get(currentFold).get(point);
                        validationSetsForAllFolds[validationFold][Y][validationIndex++] = yFolds.get(currentFold).get(point);
                    } else {
                        testSetsForAllFolds[validationFold][X][testIndex] = xFolds.get(currentFold).get(point);
                        testSetsForAllFolds[validationFold][Y][testIndex++] = yFolds.get(currentFold).get(point);
                    }
                }
            }
        }
        
    }
    private void prepareFolds(){
        for(int i=0; i<foldCnt; i++){
            xFolds.add(new ArrayList<Double>());
            yFolds.add(new ArrayList<Double>());
        }
    }
    
   
    public static double calcError(Polynomial omega, double[][] points){
        double error = 0;
        for(int n = 0; n<points[0].length; n++){
            error+= (omega.get(points[0][n]) - points[1][n])*(omega.get(points[0][n]) - points[1][n]);
        }
        return error/2.0;
    }
    
    public static double calcErrorRms(Polynomial omega, double[][] points){
        double error = calcError(omega, points);
        return Math.sqrt((2.0*error)/points.length);
    }
    
    public static Polynomial calcOmega(double[][] points, int M, double lambda, boolean verbose){
        double[][] equationSystem = new double[M+1][M+1];
        double[] rightSideOfEquation = new double[M+1];
        for(int i = 0; i<=M; i++){
            for(int m=0; m<=M; m++){
                for(int n = 0; n< points[0].length; n++){
                    equationSystem[i][m] += Math.pow(points[X][n], i+m);
                }
                if(i==m){
                    equationSystem[i][m] += lambda;
                }
            }
            
            for(int n = 0; n< points[0].length; n++){
                rightSideOfEquation[i] += points[Y][n]*Math.pow(points[X][n], i); 
            }
            
        }
        Matrix_d eqMatrix = new Matrix_d(equationSystem);

        Vector_d rightSideVector = new Vector_d(rightSideOfEquation);
        Vector_d omegaVec = eqMatrix.solve(rightSideVector);
        if(verbose){
          eqMatrix.print("equation System");
          rightSideVector.print("right side Vector");
          omegaVec.print("Omega vector");

        }
       
        return new Polynomial(omegaVec);
        
    }

    public String getM() {
        return m;
    }
    public String getLambda() {
        return lambda;
    }



    private class ValidationWorker implements Runnable{
        private int currentM;
        private double[][] points;
        private boolean isFinished = false;
        private double lastErrorLambda = Double.POSITIVE_INFINITY;
        private double bestLambdaForM = 1;
        

        public ValidationWorker(int currentM, double[][] points) {
            super();
            this.currentM = currentM;
            this.points = points;
        }



        @Override
        public void run() {
            isFinished = false;
            
            int numberOfTimesErrorDidNotDecreaseWithLambda = 0;
            int currentY = 0;
            
            while(numberOfTimesErrorDidNotDecreaseWithLambda<SEARCH_DEPTH){
                double error = 0;
                for(int currentFoldNr = 0; currentFoldNr<CrossValidation.this.testSetsForAllFolds.length; currentFoldNr++){
                    double[][] testSet = CrossValidation.this.testSetsForAllFolds[currentFoldNr];
                    double[][] validationSet = CrossValidation.this.validationSetsForAllFolds[currentFoldNr];

                    Polynomial omega = calcOmega(testSet, currentM, Math.exp(currentY), false);
                    error+= calcErrorRms(omega, validationSet);
                    
                }
                
                if(lastErrorLambda> error){
                    bestLambdaForM = Math.exp(currentY);
                    lastErrorLambda = error;
                    numberOfTimesErrorDidNotDecreaseWithLambda=0;
                    currentY--;
                } else {
                    currentY--;
                    numberOfTimesErrorDidNotDecreaseWithLambda++;
                }
            }
            isFinished = true;
        }
        public boolean isFinished() {
            return isFinished;
        }



        public double getLastErrorLambda() {
            return lastErrorLambda;
        }



        public double getBestLambdaForM() {
            return bestLambdaForM;
        }
    }

}
