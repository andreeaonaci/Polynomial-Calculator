package org.example;

import GUI.PolynomialController;
import GUI.View;

public class App {
    public static void main( String[] args )
    {
        View view = new View();
        view.setVisible(true);
        new PolynomialController(view);
    }
}
