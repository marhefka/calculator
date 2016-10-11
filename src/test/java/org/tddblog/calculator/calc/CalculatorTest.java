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

    @Test
    public void adding35And4Results39() {
        final String id = "ABC-123";
        idGenerator.mockId(id);

        calculatorService.createCalculator();

        PressResponseDTO state1 = calculatorService.press(id, "3");
        assertThat(state1.getDisplay()).isEqualTo("3");

        PressResponseDTO state2 = calculatorService.press(id, "5");
        assertThat(state2.getDisplay()).isEqualTo("35");

        PressResponseDTO state3 = calculatorService.press(id, "+");
        assertThat(state3.getDisplay()).isEqualTo("35");

        PressResponseDTO state4 = calculatorService.press(id, "4");
        assertThat(state4.getDisplay()).isEqualTo("4");

        PressResponseDTO state5 = calculatorService.press(id, "=");
        assertThat(state5.getDisplay()).isEqualTo("39");
    }
}
