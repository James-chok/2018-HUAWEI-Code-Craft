package com.elasticcloudservice.predict;

import java.util.ArrayList;
import java.util.List;

public class GradientDescent {
    List<Float> flavorSequence = new ArrayList<Float>();
    double Q = 1000;

    public GradientDescent(List<Float> flavorSequence) {
	this.flavorSequence = flavorSequence;
    }
    
    private double derivative(double Q) {
	double sum = 0;
	for (int i = 0; i < flavorSequence.size(); i++) {
	    sum += (Q*i - flavorSequence.get(i)) * i;
	}
	return sum;
    }
    
    private double function(double Q) {
	double sum = 0;
	for (int i = 0; i < flavorSequence.size(); i++) {
	    sum += (Q*i - flavorSequence.get(i)) * (Q*i - flavorSequence.get(i));
	}
	sum *= 0.5;
	return sum;
    }
    
    public double getQ() {
	double y_cur = function(Q);
	double y_div = function(Q);
	while (y_div > 0.00001) {
	    Q = Q - 0.00001 * derivative(Q);
	    y_div = y_cur - function(Q);
	    y_cur = function(Q);
	}
	return Q;
    }
}
