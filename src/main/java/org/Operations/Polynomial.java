package org.Operations;

import javax.swing.*;
import java.util.*;

public class Polynomial extends ReturnDivision{
    public Polynomial(HashMap<Integer,List<Double>> quotient, HashMap<Integer,List<Double>> remainder) {
        super(quotient, remainder);
    }

    public static int degree(HashMap<Integer, List<Double>> map) {
        int key = 0;
        for (HashMap.Entry<Integer, List<Double>> iterator : new HashMap<>(map).entrySet()) {
            if (!iterator.getValue().isEmpty())
                if (iterator.getValue().get(0) != 0 && iterator.getKey() > key)
                    key = iterator.getKey();
        }
        return key;
    }

    public static HashMap sum(HashMap<Integer, List<Double>> map) {
        Double sumCoeff;
        for (HashMap.Entry<Integer, List<Double>> iterator: new HashMap<>(map).entrySet()) {
            int key = iterator.getKey();
            List<Double> LL = iterator.getValue();
            if (LL.size() > 1) {
                List<Double> values = new ArrayList<>();
                sumCoeff = LL.get(0) + LL.get(1);
                values.add(sumCoeff);
                map.remove(key);
                map.put(key, values);
            }
        }
        return map;
    }

    public static HashMap diff(HashMap<Integer, List<Double>> map, HashMap<Integer, List<Double>> map2) {
        HashMap<Integer, List<Double>> auxMap = new HashMap<>();
        for (HashMap.Entry<Integer, List<Double>> iterator: new HashMap<>(map2).entrySet()) {
            List<Double> values = new ArrayList<>(iterator.getValue());
            Double value = values.get(0) * (-1);
            values.clear();
            values.add(value);
            auxMap.put(iterator.getKey(), values);
        }
        map = concatenate(map, auxMap);
        sum(map);
        return map;
    }

    public static HashMap multiplication(HashMap<Integer, List<Double>> map1, HashMap<Integer, List<Double>> map2) {
        Double multiCoeff;
        HashMap<Integer, List<Double>> mapRes = new HashMap<>();
        for (HashMap.Entry<Integer, List<Double>> iterator1: new HashMap<>(map1).entrySet()) {
            for (HashMap.Entry<Integer, List<Double>> iterator2: new HashMap<>(map2).entrySet()) {
                List<Double> result = new ArrayList<>();
                multiCoeff = iterator1.getValue().get(0) * iterator2.getValue().get(0);
                result.add(multiCoeff);
                if (mapRes.containsKey(iterator1.getKey()+iterator2.getKey())) {
                    mapRes.get(iterator1.getKey()+iterator2.getKey()).addAll(result);
                }
                else {
                    mapRes.put(iterator1.getKey()+iterator2.getKey(),result);
                }
            }
        }
        sum(mapRes);
        map1.clear();

        return mapRes;
    }

    public static HashMap concatenate(HashMap<Integer, List<Double>> map1, HashMap<Integer, List<Double>> map2) {

        for (HashMap.Entry<Integer, List<Double>> iterator : new HashMap<>(map2).entrySet()) {
            List<Double> values = iterator.getValue();
            if (map1.containsKey(iterator.getKey())) {
                List<Double> valuesChaining = new ArrayList<>(map1.get(iterator.getKey()));
                valuesChaining.addAll(values);
                map1.put(iterator.getKey(), valuesChaining);
            }
            else {
                map1.put(iterator.getKey(), values);
            }
        }
        return map1;
    }

    public static ReturnDivision division(HashMap<Integer, List<Double>> pol1, HashMap<Integer, List<Double>> pol2) {
        HashMap<Integer,List<Double>> mapQ = new HashMap<>();
        HashMap<Integer, List<Double>> mapR = new HashMap<>(pol1);
        HashMap<Integer, List<Double>> mapQuotientAux = new HashMap<>();
        if (!pol2.isEmpty()) { //if the polynomial is not null
            int key1 = degree(pol1);
            while (!mapR.isEmpty() && key1 >= degree(pol2) && degree(mapR) >= 0 && key1 >= 0) { //compute the remainder
                key1 = degree(mapR);
                List<Double> result = new ArrayList<>();
                if (mapQ.containsKey(key1-degree(pol2))) { //check for the degree of the element to be added in the quotient
                    if ((mapR.get(key1).get(0)) / (pol2.get(degree(pol2)).get(0)) == 0) { //check for division coefficient equals 0
                        result.add((mapR.get(key1).get(0)) / (pol2.get(degree(pol2)).get(0)));
                        mapR.remove(degree(mapR), result);
                        result.clear();
                    }
                    else { //if not 0
                        result.clear();
                        result.add((mapR.get(key1).get(0)) / (pol2.get(degree(pol2)).get(0)));
                        mapQ.get(key1 - degree(pol2)).addAll(result);
                        mapQuotientAux.put(key1 - degree(pol2), result);
                    }
                }
                else { //if this key doesn`t exist, we put it (diff of 2 keys)
                    result.add((mapR.get(key1).get(0)) / (pol2.get(degree(pol2)).get(0)));
                    mapQ.put(key1-degree(pol2),result);
                    mapQuotientAux.put(key1-degree(pol2),result);
                }
                HashMap map = new HashMap<>(mapQuotientAux);
                map = multiplication(map, pol2);
                mapR = diff(mapR, map); //diff between divider and (added coeff of quotient)*divisor
                key1--;
                mapQuotientAux.clear();
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Cannot divide by 0");
        }
        return new ReturnDivision(mapQ, mapR);
    }

    public static HashMap derivation(HashMap<Integer, List<Double>> map) {
        Double derCoeff;
        int deg = degree(map);
        for (HashMap.Entry<Integer, List<Double>> iterator: new HashMap<>(map).entrySet()) {
            List<Double> LL = iterator.getValue();
            derCoeff = iterator.getKey() * LL.get(0);
            map.remove(iterator.getKey());
            map.put(iterator.getKey() - 1, Arrays.asList(derCoeff));
        }
        if (map.get(-1) != null)
            if (map.get(-1).get(0) != null && deg == 0)
                map.put(0, Arrays.asList(0.0d));
        return map;
    }

    public static HashMap integration(HashMap<Integer, List<Double>> map, HashMap<Integer, List<Double>> mapResult) {
        Double intCoeff;
        for (HashMap.Entry<Integer, List<Double>> iterator: new HashMap<>(map).entrySet()) {
            List<Double> LL = iterator.getValue();
            if (iterator.getValue() != null) {
                intCoeff = LL.get(0) / (iterator.getKey() + 1);
                map.remove(iterator.getKey() + 1);
                mapResult.put(iterator.getKey() + 1, Arrays.asList(intCoeff));
            }
        }
        return mapResult;
    }
}