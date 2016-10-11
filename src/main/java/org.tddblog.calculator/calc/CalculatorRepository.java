package org.tddblog.calculator.calc;

public interface CalculatorRepository {
    void save(Calculator calculator);

    Calculator findById(String id);
}
