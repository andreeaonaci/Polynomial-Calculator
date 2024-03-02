package GUI;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private JPanel basePanel;
    private JTextField pol1;
    private JTextField pol2;
    private JLabel result;
    private JButton sum;
    private JButton diff;
    private JButton multiply;
    private JButton division;
    private JButton derivate;
    private JButton integrate;
    private JLabel Result;
    private JLabel finalResult2;
    private JLabel quotientField;
    private JLabel remainderField;

    public void setQuotientField(String quotientField) {
        this.quotientField.setText(quotientField);
    }

    public void setRemainderField(String remainderField) {
        this.remainderField.setText(remainderField);
    }

    public void setDimension(int w, int h) {
        add(basePanel);
        setBounds(300, 200, w, h);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void showMessage (String message) {
        JOptionPane.showMessageDialog(View.this, message, "Message", JOptionPane.ERROR_MESSAGE);
    }

    public void AddSumListener (ActionListener listener) {
        this.sum.addActionListener(listener);
    }

    public void AddDiffListener (ActionListener listener) {
        this.diff.addActionListener(listener);
    }

    public void AddMultiplyListener (ActionListener listener) {
        this.multiply.addActionListener(listener);
    }

    public void AddDivisionListener (ActionListener listener) {
        this.division.addActionListener(listener);
    }

    public void setFinalResult2(String text) {
        this.finalResult2.setText(text);
    }

    public void AddDerivativeListener (ActionListener listener) {
        this.derivate.addActionListener(listener);
    }

    public String getPol1() {
        return pol1.getText();
    }

    public String setPol2(String pol2) {
        this.pol2.setText(pol2);
        return pol2;
    }

    public String getPol2() {
        return pol2.getText();
    }

    public void setResult(String result) {
        this.result.setText(result);
    }

    public View() {
        setDimension(900,500);
    }

    public void AddIntegrateListener (ActionListener listener) {
        this.integrate.addActionListener(listener);
    }
}
