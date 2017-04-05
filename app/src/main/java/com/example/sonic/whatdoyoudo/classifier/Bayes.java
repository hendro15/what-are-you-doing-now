package com.example.sonic.whatdoyoudo.classifier;

import android.provider.CalendarContract;
import android.util.Log;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.core.expressionlanguage.common.Primitives;

/**
 * Created by sonic on 03/04/2017.
 */

public class Bayes {

    private boolean fileExist;
    private Instances train;
    private double label;
    private Instance instance;

    public Instances fileToInstances(Instances source) {
        Instances dataset = source;
        try {
//            dataset = source.getDataSet();

            if (dataset.classIndex() == -1) {
                dataset.setClassIndex(dataset.numAttributes() - 1);
            }
            Attribute att = dataset.classAttribute();
            for(int i  = 0; i<dataset.numClasses(); i++){
                Log.i("g", att.value(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Instances(dataset);
    }

    public String bayes(double[] val) {
        try {
            instance = new DenseInstance(12, val);
            NaiveBayes naiveBayes = new NaiveBayes();
            naiveBayes.buildClassifier(train);

            Evaluation eval = new Evaluation(train);
            eval.predictions();

            label = naiveBayes.classifyInstance(instance);

            return instance.classAttribute().value((int) label);
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }
}
