package org.tddblog.calculator.calc;

public interface CalculatorService {
    CreateCalculatorResponseDTO createCalculator();

    PressResponseDTO press(String id, String button);
}
