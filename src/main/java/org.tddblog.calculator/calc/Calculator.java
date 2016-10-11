package org.tddblog.calculator.calc;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.math.BigDecimal;

@Entity(noClassnameStored = true)
public class Calculator {
    @Id
    private String id;
    private BigDecimal display;
    private boolean plusSignPressed;
    private boolean secondNumberStarted;
    private BigDecimal firstNumber;

    public Calculator(String id) {
        this.id = id;
        this.display = BigDecimal.ZERO;
        this.plusSignPressed = false;
    }

    public void press(char button) {
        if (button >= '0' && button <= '9') {
            pressNumber(new BigDecimal("" + button));
            return;
        }

        if (button == '+') {
            // + sign
            if (plusSignPressed) {
                throw new UnsupportedOperationException();
            }

            plusSignPressed = true;
            secondNumberStarted = false;
            firstNumber = display;
            return;
        }

        if (button == '=') {
            if (plusSignPressed) {
                if (secondNumberStarted) {
                    display = firstNumber.add(display);
                } else {
                    display = firstNumber;
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }
    }

    private void pressNumber(BigDecimal digit) {
        if (!plusSignPressed || secondNumberStarted) {
            display = display
                    .multiply(BigDecimal.TEN)
                    .add(digit);
            return;
        }

        if (!secondNumberStarted) {
            secondNumberStarted = true;
            display = digit;
        }
    }

    public String getId() {
        return id;
    }

    public BigDecimal getDisplay() {
        return display;
    }
}
