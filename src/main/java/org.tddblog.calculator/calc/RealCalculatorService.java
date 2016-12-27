package org.tddblog.calculator.calc;

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
}
