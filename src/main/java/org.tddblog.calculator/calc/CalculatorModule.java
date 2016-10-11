package org.tddblog.calculator.calc;

import com.google.inject.AbstractModule;

public class CalculatorModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CalculatorService.class).to(RealCalculatorService.class);
        bind(IdGenerator.class).to(RandomIdGenerator.class);
        bind(CalculatorRepository.class).to(MongoCalculatorRepository.class);
    }
}
