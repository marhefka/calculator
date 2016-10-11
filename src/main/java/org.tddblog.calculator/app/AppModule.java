package org.tddblog.calculator.app;

import com.google.inject.AbstractModule;
import org.tddblog.calculator.calc.CalculatorModule;
import org.tddblog.calculator.mongo.MongoModule;

public class AppModule extends AbstractModule {
    private final AppConfig appConfig;

    public AppModule(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    protected void configure() {
        install(new MongoModule(appConfig.getMongo()));
        install(new CalculatorModule());
    }
}
