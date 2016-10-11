package org.tddblog.calculator.calc;

import java.util.UUID;

public class RandomIdGenerator implements IdGenerator {
    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }
}
