package org.tddblog.calculator;

import com.google.inject.AbstractModule;
import org.tddblog.calculator.calc.DummyIdGenerator;
import org.tddblog.calculator.calc.IdGenerator;

public class AppTestModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IdGenerator.class).to(DummyIdGenerator.class);
    }
}
