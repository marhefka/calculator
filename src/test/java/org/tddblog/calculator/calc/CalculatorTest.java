package org.tddblog.calculator.calc;

import org.junit.Test;
import org.tddblog.calculator.IntegrationTestBase;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorTest extends IntegrationTestBase {
    @Test
    public void newCalculatorShows0Initially() {
        idGenerator.mockId("ABC-123");

        CreateCalculatorResponseDTO calculatorDTO = calculatorService.createCalculator();
        assertThat(calculatorDTO.getId()).isEqualTo("ABC-123");
    }
}
