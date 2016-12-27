package org.tddblog.calculator;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import org.junit.Before;
import org.mongodb.morphia.Datastore;
import org.tddblog.calculator.app.AppConfig;
import org.tddblog.calculator.app.AppModule;
import org.tddblog.calculator.calc.CalculatorService;
import org.tddblog.calculator.calc.DummyIdGenerator;
import org.tddblog.calculator.calc.IdGenerator;
import org.tddblog.calculator.mongo.MongoConfig;

public class IntegrationTestBase {
    protected Injector injector;
    protected CalculatorService calculatorService;
    protected DummyIdGenerator idGenerator;

    @Before
    public void setUp() throws Exception {
        AppConfig appConfig = createConfig();

        injector = Guice.createInjector(
                Modules.override(new AppModule(appConfig)).with(new AppTestModule())
        );

        Datastore datastore = injector.getInstance(Datastore.class);
        datastore.getDB().dropDatabase();

        calculatorService = injector.getInstance(CalculatorService.class);
        idGenerator = (DummyIdGenerator) injector.getInstance(IdGenerator.class);
    }

    private AppConfig createConfig() {
        MongoConfig mongoConfig = new MongoConfig();
        mongoConfig.setClientPoolSize(1);
        mongoConfig.setUri("mongodb://localhost:27017/test");

        AppConfig appConfig = new AppConfig();
        appConfig.setMongo(mongoConfig);
        return appConfig;
    }
}
