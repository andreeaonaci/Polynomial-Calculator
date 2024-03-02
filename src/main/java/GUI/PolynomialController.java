package GUI;

import org.Operations.Polynomial;
import org.Operations.ReturnDivision;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolynomialController {
    HashMap<Integer, List<Double>> quotient = new HashMap<>();
    HashMap<Integer, List<Double>> mapResult = new HashMap<>();
    HashMap<Integer, List<Double>> remainder = new HashMap<>();
    HashMap<Integer, List<Double>> polynom1 = new HashMap<>();
    HashMap<Integer, List<Double>> polynom2 = new HashMap<>();
    private boolean okDuplicated = true;

    public boolean isOkDuplicated() {
        return okDuplicated;
    }

    public void setOkDuplicated(boolean okDuplicated) {
        this.okDuplicated = okDuplicated;
    }

    public PolynomialController(View finalResult) {

        this.finalResult = finalResult;
        finalResult.AddSumListener(new sum());
        finalResult.AddDiffListener(new diff());
        finalResult.AddMultiplyListener(new multiply());
        finalResult.AddDerivativeListener(new derivative());
        finalResult.AddIntegrateListener(new integration());
        finalResult.AddDivisionListener(new division());
    }

    public boolean hasMultipleX(String poly) {

        if (poly.contains("xx"))
            return true;
        else
            return false;
    }

    public boolean checkError(String poly) {
        boolean check = true;

        if (poly.isEmpty()) {
            finalResult.showMessage("The polynomial is null!");
            check = false;
        }

        String pattern = "([+-])?(\\d*\\.?\\d*)?x(\\^(\\d*))?|([+-])?(\\d*\\.?\\d*)";
        Matcher matcher = Pattern.compile(pattern).matcher(poly);

        Pattern p = Pattern.compile("[a-wyzA-WYZ]");
        Matcher m = p.matcher(poly);

        if (m.find()) {
            finalResult.showMessage("The input should be either a digit or a polynomial with the variable x!");
            check = false;
        }

        if (hasMultipleX(poly)) {
            finalResult.showMessage("Invalid polynomial!");
            check = false;
        }

        if (!matcher.find()) {
            finalResult.showMessage("The polynomial hasn`t a good form, it should be of form a1x^n+a2x^(n-1)+...+a(n-1)x^1+an!");
            check = false;
        }

        if (!isOkDuplicated()) {
            finalResult.showMessage("Invalid input, duplicated keys!");
            check = false;
        }

        return check;
    }

    public HashMap<Integer, List<Double>> convertPoltoHashMap(String poly) {

        HashMap<Integer, List<Double>> myMap = new HashMap<>();
        String pattern = "([+-])?(\\d*\\.?\\d+)?x(\\^(\\d*))?|([+-])?(\\d*\\.?\\d+)";
        Matcher matcher = Pattern.compile(pattern).matcher(poly);
        ArrayList chain = new ArrayList<>();
            while (matcher.find()) {

                Double coeff = 1.0d;
                int power = 0;
                String sign = "+";

                if (matcher.group(1) != null && !matcher.group(1).isEmpty())
                    sign = matcher.group(1);

                if (matcher.group(2) != null && !matcher.group(2).isEmpty())
                    coeff = Double.parseDouble(matcher.group(2));

                if (matcher.group(4) == null)
                    power = 1;

                if (matcher.group(4) != null && !matcher.group(4).isEmpty())
                        power = Integer.parseInt(matcher.group(4));

                if (matcher.group(5) != null && !matcher.group(5).isEmpty())
                    sign = matcher.group(5);

                if (matcher.group(6) != null && !matcher.group(6).isEmpty()) {
                    coeff = Double.parseDouble(matcher.group(6));
                    power = 0;
                }

                if (sign.equals("-")) {
                    coeff = -coeff;
                }

                if (myMap.get(power) == null) {
                    chain = new ArrayList<>();
                    chain.add(coeff);
                    myMap.put(power, chain);
                }
                else {
                    setOkDuplicated(false);
                }
            }
            return myMap;
    }

    private View finalResult;

    public String convertHashMaptoPol(HashMap<Integer,List<Double>> map, int maxPower) {
        StringBuilder result = new StringBuilder();
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setMaximumFractionDigits(2);
        boolean ok = false;
        if (map.isEmpty())
            return null;
        for(HashMap.Entry<Integer, List<Double>> iterator : new HashMap<>(map).entrySet()) {
            if (iterator.getValue().get(0) != 0)
                ok = true;
        }
        if (!ok) {
            result.append(0);
            return result.toString();
        }
        else {
            while (map.get(maxPower).get(0) == 0)
                maxPower--;
            if (maxPower != 0) {
                if (maxPower != 1) {
                    if ((map.get(maxPower).get(0)) != 1.0d) {
                        if (map.get(maxPower).get(0) < 0)
                            result.append("-").append(formatter.format((map.get(maxPower).get(0))));
                        if (map.get(maxPower).get(0) > 0)
                            result.append(formatter.format((map.get(maxPower).get(0))));
                    } else {
                        if (map.get(maxPower).get(0) < 0)
                            result.append("-");
                    }
                    result.append("x^").append(maxPower);
                    maxPower--;
                    while (maxPower >= 2) {
                        String sign;
                        if (map.get(maxPower) != null) {
                            if (map.get(maxPower).get(0) != 0) {
                                if (map.get(maxPower).get(0) > 0)
                                    sign = " + ";
                                else
                                    sign = " - ";
                                if (Math.abs(map.get(maxPower).get(0)) != 1.0d) {
                                    result.append(sign).append(formatter.format(Math.abs(map.get(maxPower).get(0))));
                                } else {
                                    result.append(sign);
                                }
                                result.append("x^").append(maxPower);
                            }
                        }
                        maxPower--;
                    }
                    if (maxPower == 1 && map.get(1) != null) {
                        String sign;
                        if (map.get(1).get(0) >= 0)
                            sign = " + ";
                        else 
                            sign = " - ";
                        if (map.get(1).get(0) != 0) {
                            if (Math.abs(map.get(1).get(0)) == 1)
                                result.append(sign).append("x");
                            else
                                result.append(sign + formatter.format(Math.abs(map.get(maxPower).get(0))) + "x ");
                        }
                    }
                    maxPower--;
                    if (maxPower == 0 && map.get(0) != null) {
                        if (map.get(0).get(0) < 0)
                            result.append(" - " + formatter.format(Math.abs(map.get(maxPower).get(0))));
                        if (map.get(0).get(0) > 0)
                            result.append(" + " + formatter.format(Math.abs(map.get(maxPower).get(0))));
                    }
                } else {
                    if (maxPower == 1 && map.get(1) != null) {
                        if (map.get(1).get(0) < 0)
                            if (map.get(1).get(0) != -1)
                                result.append("-" + formatter.format(Math.abs(map.get(1).get(0))));
                            else
                                result.append("-");
                        else
                            if (map.get(1).get(0) != 1)
                                result.append(formatter.format((map.get(1).get(0))));
                        result.append("x");
                        if (map.get(0) != null) {
                            if (map.get(0).get(0) < 0)
                                result.append(formatter.format((map.get(0).get(0))));
                            if (map.get(0).get(0) > 0)
                                if (result.capacity() == 0)
                                    result.append(formatter.format((map.get(0).get(0))));
                                else
                                    result.append("+" + formatter.format((map.get(0).get(0))));
                        }
                    }
                }
            } else {
                if (map.get(0).get(0) != null) {
                    if (map.get(0).get(0) > 0)
                        result.append(formatter.format((map.get(0).get(0))));
                    if (map.get(0).get(0) < 0)
                        result.append("-" + formatter.format((map.get(0).get(0))));
                }
            }
            return result.toString();
        }
    }

    class sum implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            finalResult.setQuotientField(null);
            finalResult.setRemainderField(null);
            finalResult.setFinalResult2(null);
            Polynomial polynomial = new Polynomial(quotient, remainder);
            polynom1 = convertPoltoHashMap(finalResult.getPol1());
            polynom2 = convertPoltoHashMap(finalResult.getPol2());
            boolean error1 = checkError(finalResult.getPol2());
            boolean error2 = checkError(finalResult.getPol1());

            if (error1 && error2) {
                polynomial.concatenate(polynom1, polynom2);
                finalResult.setResult(convertHashMaptoPol(polynomial.sum(polynom1), polynomial.degree(polynom1)));
            } else {
                finalResult.setResult(null);
                setOkDuplicated(true);
            }
        }
    }

    class diff implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            finalResult.setQuotientField(null);
            finalResult.setRemainderField(null);
            finalResult.setFinalResult2(null);
            Polynomial polynomial = new Polynomial(quotient, remainder);
            polynom1 = convertPoltoHashMap(finalResult.getPol1());
            polynom2 = convertPoltoHashMap(finalResult.getPol2());
            boolean error1 = checkError(finalResult.getPol2());
            boolean error2 = checkError(finalResult.getPol1());

            if (error1 && error2) {
                HashMap <Integer, List<Double>> aux = new HashMap<>(polynomial.diff(polynom1, polynom2));
                finalResult.setResult(convertHashMaptoPol(aux, polynomial.degree(aux)));
            }
            else {
                finalResult.setResult(null);
                setOkDuplicated(true);
            }
        }
    }

    class derivative implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            finalResult.setQuotientField(null);
            finalResult.setRemainderField(null);
            finalResult.setFinalResult2(null);
            Polynomial polynomial = new Polynomial(quotient, remainder);
            polynom1 = convertPoltoHashMap(finalResult.getPol1());
            polynom2 = convertPoltoHashMap(finalResult.getPol2());
            boolean error1 = checkError(finalResult.getPol1());

            if (error1) {
                finalResult.setResult(convertHashMaptoPol(polynomial.derivation(polynom1), polynomial.degree(polynom1)));
                finalResult.setFinalResult2(finalResult.setPol2(null));
            }
            else {
                finalResult.setResult(null);
                setOkDuplicated(true);
            }
        }
    }

    class integration implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            finalResult.setQuotientField(null);
            finalResult.setRemainderField(null);
            finalResult.setFinalResult2(null);
            mapResult.clear();
            Polynomial polynomial = new Polynomial(quotient, remainder);
            polynom1 = convertPoltoHashMap(finalResult.getPol1());
            boolean error = checkError(finalResult.getPol1());

            if (error) {
                mapResult = polynomial.integration(polynom1, mapResult);
                finalResult.setResult(convertHashMaptoPol(mapResult, polynomial.degree(mapResult)));
                finalResult.setFinalResult2(finalResult.setPol2(null));
            }
            else {
                finalResult.setResult(null);
                setOkDuplicated(true);
            }
        }
    }

    class multiply implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            finalResult.setQuotientField(null);
            finalResult.setRemainderField(null);
            finalResult.setFinalResult2(null);
            mapResult.clear();
            Polynomial polynomial = new Polynomial(quotient, remainder);
            polynom1 = convertPoltoHashMap(finalResult.getPol1());
            polynom2 = convertPoltoHashMap(finalResult.getPol2());
            boolean error1 = checkError(finalResult.getPol2());
            boolean error2 = checkError(finalResult.getPol1());

            if (error1 && error2) {
                mapResult = polynomial.multiplication(polynom1, polynom2);
                finalResult.setResult(convertHashMaptoPol(mapResult, polynomial.degree(mapResult)));
            }
            else {
                finalResult.setResult(null);
                setOkDuplicated(true);
            }
        }
    }

    class division implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            finalResult.setQuotientField(null);
            finalResult.setRemainderField(null);
            finalResult.setFinalResult2(null);
            Polynomial polynomial = new Polynomial(quotient, remainder);
            polynom1 = convertPoltoHashMap(finalResult.getPol1());
            polynom2 = convertPoltoHashMap(finalResult.getPol2());
            if (finalResult.getPol2().equals("0")) {
                finalResult.showMessage("The 2nd polynomial is 0!");
                finalResult.setResult(null);
                finalResult.setFinalResult2(null);
                finalResult.setRemainderField(null);
                finalResult.setQuotientField(null);
                setOkDuplicated(true);
            }
            else {
                ReturnDivision returnDivision = polynomial.division(polynom1, polynom2);
                boolean var1 = checkError(finalResult.getPol1());
                boolean var2 = checkError(finalResult.getPol2());
                if (var1 && var2) {
                    finalResult.setResult(convertHashMaptoPol(returnDivision.getQuotient(), polynomial.degree(returnDivision.getQuotient())));
                    finalResult.setFinalResult2(convertHashMaptoPol(returnDivision.getRemainder(), polynomial.degree(returnDivision.getRemainder())));
                    finalResult.setQuotientField("~ quotient");
                    finalResult.setRemainderField("~ remainder");
                }
                else {
                    finalResult.setResult(null);
                    finalResult.setFinalResult2(null);
                    finalResult.setRemainderField(null);
                    finalResult.setQuotientField(null);
                    setOkDuplicated(true);
                }
            }
        }
    }
}
