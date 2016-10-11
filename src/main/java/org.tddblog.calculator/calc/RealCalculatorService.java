package org.tddblog.calculator.calc;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

public class RealCalculatorService extends CalculatorResource {
    private final IdGenerator idGenerator;
    private final CalculatorRepository calculatorRepository;

    @Inject
    public RealCalculatorService(IdGenerator idGenerator,
                                 CalculatorRepository calculatorRepository) {

        this.idGenerator = idGenerator;
        this.calculatorRepository = calculatorRepository;
    }

    @Override
    public CreateCalculatorResponseDTO createCalculator() {
        String value = idGenerator.generateId();

        Calculator calculator = new Calculator(value);
        calculatorRepository.save(calculator);

        CreateCalculatorResponseDTO ret = new CreateCalculatorResponseDTO();
        ret.setId(calculator.getId());
        return ret;
    }

    @Override
    public PressResponseDTO press(String id, String button) {
        validateButton(button);

        Calculator calculator = calculatorRepository.findById(id);
        calculator.press(button.charAt(0));
        calculatorRepository.save(calculator);

        PressResponseDTO ret = new PressResponseDTO();
        ret.setDisplay(calculator.getDisplay());
        return ret;
    }

    private void validateButton(String button) {
        Preconditions.checkArgument(button.length() == 1, "Button must have length of 1.");
        Preconditions.checkArgument(isValidButton(button), "Invalid button: %s", button);
    }

    private boolean isValidButton(String button) {
        char ch = button.charAt(0);
        if (ch >= '0' && ch <= '9') return true;
        if (ch == '+' || ch == '=') return true;
        return false;
    }
}
