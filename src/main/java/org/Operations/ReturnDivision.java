package org.Operations;

import java.util.HashMap;
import java.util.List;

public class ReturnDivision {
    HashMap<Integer, List<Double>> quotient;
    HashMap<Integer, List<Double>> remainder;

    public HashMap<Integer, List<Double>> getQuotient() {
        return quotient;
    }

    public HashMap<Integer, List<Double>> getRemainder() {
        return remainder;
    }

    public ReturnDivision(HashMap<Integer, List<Double>> quotient, HashMap<Integer, List<Double>> remainder) {
        this.quotient = quotient;
        this.remainder = remainder;
    }
}
