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
import org.yaml.snakeyaml.Yaml;

import java.io.*;

public class IntegrationTestBase {
    protected Injector injector;
    protected CalculatorService calculatorService;
    protected DummyIdGenerator idGenerator;

    @Before
    public void setUp() throws Exception {
        AppConfig appConfig = readYamlConfig("config-test.yml");
        injector = Guice.createInjector(
                Modules.override(new AppModule(appConfig)).with(new AppTestModule())
        );

        Datastore datastore = injector.getInstance(Datastore.class);
        datastore.getDB().dropDatabase();

        calculatorService = injector.getInstance(CalculatorService.class);
        idGenerator = (DummyIdGenerator) injector.getInstance(IdGenerator.class);
    }

    public AppConfig readYamlConfig(String fileName) {
        try (InputStream input = new FileInputStream(getFile(fileName))) {
            Yaml yaml = new Yaml();
            return yaml.loadAs(input, AppConfig.class);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("Please create your config file under src/test/resources/" + fileName);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private File getFile(String fileName) {
        return new File("src/test/resources/" + fileName);
    }
}
