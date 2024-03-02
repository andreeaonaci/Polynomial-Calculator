package org.Operations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.*;


public class AppTest {
    @Test
    public void testSum1() {
        HashMap<Integer, List<Double>> map = new HashMap<>();
        map.put(1, Arrays.asList(1.0d, 2.0d));
        map.put(2, Arrays.asList(3.0d));
        map.put(3, Arrays.asList(4.0d, 5.0d));

        HashMap<Integer, List<Double>> result = Polynomial.sum(map);

        Assertions.assertEquals(result.get(1), Arrays.asList(3.0d));
        Assertions.assertEquals(result.get(2), Arrays.asList(3.0d));
        Assertions.assertEquals(result.get(3), Arrays.asList(9.0d));
    }

    @Test
    public void testSum2() {
        HashMap<Integer, List<Double>> map = new HashMap<>();
        map.put(1, Arrays.asList(1.0d, -4.0d));
        map.put(2, Arrays.asList(9.0d));
        map.put(3, Arrays.asList(4.0d));

        HashMap<Integer, List<Double>> result = Polynomial.sum(map);

        Assertions.assertEquals(result.get(1), Arrays.asList(-3.0d));
        Assertions.assertEquals(result.get(2), Arrays.asList(9.0d));
        Assertions.assertEquals(result.get(3), Arrays.asList(4.0d));
    }

    @Test
    public void testDiff1() {
        HashMap<Integer, List<Double>> map1 = new HashMap<>();
        HashMap<Integer, List<Double>> map2 = new HashMap<>();
        map1.put(1, Arrays.asList(1.0d));
        map1.put(2, Arrays.asList(4.0d));
        map2.put(1, Arrays.asList(3.0d));
        map2.put(0, Arrays.asList(-9.0d));

        HashMap<Integer, List<Double>> result = Polynomial.diff(map1, map2);

        Assertions.assertEquals(result.get(0), Arrays.asList(9.0d));
        Assertions.assertEquals(result.get(1), Arrays.asList(-2.0d));
        Assertions.assertEquals(result.get(2), Arrays.asList(4.0d));
    }

    @Test
    public void testDiff2() {
        HashMap<Integer, List<Double>> map1 = new HashMap<>();
        HashMap<Integer, List<Double>> map2 = new HashMap<>();
        map1.put(1, Arrays.asList(3.0d));
        map1.put(2, Arrays.asList(4.0d));
        map2.put(2, Arrays.asList(3.0d));
        map2.put(1, Arrays.asList(-5.0d));

        HashMap<Integer, List<Double>> result = Polynomial.diff(map1, map2);

        Assertions.assertEquals(result.get(1), Arrays.asList(8.0d));
    }

    @Test
    public void testDeriv1() {
        HashMap<Integer, List<Double>> map1 = new HashMap<>();
        map1.put(1, Arrays.asList(1.0d));
        map1.put(2, Arrays.asList(4.0d));

        HashMap<Integer, List<Double>> result = Polynomial.derivation(map1);

        Assertions.assertEquals(result.size(), 2);
        Assertions.assertEquals(result.get(0), Arrays.asList(1.0d));
        Assertions.assertEquals(result.get(1), Arrays.asList(8.0d));
    }

    @Test
    public void testDeriv2() {
        HashMap<Integer, List<Double>> map1 = new HashMap<>();
        map1.put(0, Arrays.asList(1.0d));

        HashMap<Integer, List<Double>> result = Polynomial.derivation(map1);

        Assertions.assertEquals(result.get(0), Arrays.asList(0.0d));
    }

    @Test
    public void testIntegr1() {
        HashMap<Integer, List<Double>> map1 = new HashMap<>();
        map1.put(1, Arrays.asList(1.0d));
        map1.put(2, Arrays.asList(3.0d));

        HashMap<Integer, List<Double>> result = new HashMap<>();
        result = Polynomial.integration(map1, result);

        Assertions.assertEquals(result.size(), 2);
        Assertions.assertEquals(result.get(2), Arrays.asList(0.5d));
        Assertions.assertEquals(result.get(3), Arrays.asList(1.0d));
    }

    @Test
    public void testIntegr2() {
        HashMap<Integer, List<Double>> map1 = new HashMap<>();
        map1.put(0, Arrays.asList(1.0d));

        HashMap<Integer, List<Double>> result = new HashMap<>();
        result = Polynomial.integration(map1, result);

        Assertions.assertEquals(result.get(1), Arrays.asList(1.0d));
    }

    @Test
    public void testMultiply1() {
        HashMap<Integer, List<Double>> map1 = new HashMap<>();
        HashMap<Integer, List<Double>> map2 = new HashMap<>();
        map1.put(1, Arrays.asList(-1.0d));
        map1.put(2, Arrays.asList(4.0d));
        map2.put(1, Arrays.asList(3.0d));
        map2.put(0, Arrays.asList(-9.0d));

        HashMap<Integer, List<Double>> result = Polynomial.multiplication(map1, map2);

        Assertions.assertEquals(result.get(1), Arrays.asList(9.0d));
        Assertions.assertEquals(result.get(2), Arrays.asList(-39.0d));
        Assertions.assertEquals(result.get(3), Arrays.asList(12.0d));
    }

    @Test
    public void testMultiply2() {
        HashMap<Integer, List<Double>> map1 = new HashMap<>();
        HashMap<Integer, List<Double>> map2 = new HashMap<>();
        map1.put(0, Arrays.asList(4.0d));
        map1.put(1, Arrays.asList(-3.0d));
        map1.put(3, Arrays.asList(1.0d));
        map2.put(1, Arrays.asList(1.0d));
        map2.put(0, Arrays.asList(2.0d));

        HashMap<Integer, List<Double>> result = Polynomial.multiplication(map1, map2);

        Assertions.assertEquals(result.get(0), Arrays.asList(8.0d));
        Assertions.assertEquals(result.get(1), Arrays.asList(-2.0d));
        Assertions.assertEquals(result.get(2), Arrays.asList(-3.0d));
        Assertions.assertEquals(result.get(3), Arrays.asList(2.0d));
        Assertions.assertEquals(result.get(4), Arrays.asList(1.0d));
    }

    @Test
    public void testMultiply3() {
        HashMap<Integer, List<Double>> map1 = new HashMap<>();
        HashMap<Integer, List<Double>> map2 = new HashMap<>();
        map1.put(0, Arrays.asList(2.0d));
        map1.put(1, Arrays.asList(1.0d));
        map2.put(1, Arrays.asList(1.0d));
        map2.put(0, Arrays.asList(2.0d));

        HashMap<Integer, List<Double>> result = Polynomial.multiplication(map1, map2);

        Assertions.assertEquals(result.get(0), Arrays.asList(4.0d));
        Assertions.assertEquals(result.get(1), Arrays.asList(4.0d));
        Assertions.assertEquals(result.get(2), Arrays.asList(1.0d));
    }

    @Test
    public void testDivision1() {
        HashMap<Integer, List<Double>> map1 = new HashMap<>();
        HashMap<Integer, List<Double>> map2 = new HashMap<>();
        map1.put(1, Arrays.asList(-1.0d));
        map1.put(2, Arrays.asList(4.0d));
        map2.put(1, Arrays.asList(4.0d));
        map2.put(0, Arrays.asList(-8.0d));

        ReturnDivision result = Polynomial.division(map1, map2);

        Assertions.assertEquals(result.getRemainder().get(0), Arrays.asList(14.0d));
        Assertions.assertEquals(result.getQuotient().get(1), Arrays.asList(1.0d));
        Assertions.assertEquals(result.getQuotient().get(0), Arrays.asList(1.75d));
    }

    @Test
    public void testDivision2() {
        HashMap<Integer, List<Double>> map1 = new HashMap<>();
        HashMap<Integer, List<Double>> map2 = new HashMap<>();
        map1.put(0, Arrays.asList(-4.0d));
        map1.put(2, Arrays.asList(1.0d));
        map2.put(1, Arrays.asList(1.0d));
        map2.put(0, Arrays.asList(2.0d));

        ReturnDivision result = Polynomial.division(map1, map2);

        Assertions.assertEquals(result.getRemainder().get(0), Arrays.asList(0.0d));
        Assertions.assertEquals(result.getQuotient().get(1), Arrays.asList(1.0d));
        Assertions.assertEquals(result.getQuotient().get(0), Arrays.asList(-2.0d));
    }

    @Test
    public void testDivision3() {
        HashMap<Integer, List<Double>> map1 = new HashMap<>();
        HashMap<Integer, List<Double>> map2 = new HashMap<>();
        map1.put(1, Arrays.asList(1.0d));
        map1.put(0, Arrays.asList(2.0d));
        map2.put(1, Arrays.asList(1.0d));
        map2.put(0, Arrays.asList(-2.0d));

        ReturnDivision result = Polynomial.division(map1, map2);

        Assertions.assertEquals(result.getRemainder().get(0), Arrays.asList(4.0d));
    }
}