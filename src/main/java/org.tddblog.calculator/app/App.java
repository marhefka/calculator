package org.tddblog.calculator.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.tddblog.calculator.calc.CalculatorService;

public class App extends Application<AppConfig> {
    private Injector injector;

    public static void main(String[] args) throws Exception {
        new App().run("server", "config.yml");
    }

    public App() {
    }

    @Override
    public String getName() {
        return "Calculator sample app for tddblog.hu";
    }

    @Override
    public void run(AppConfig config,
                    Environment environment) {

        injector = createInjector(config);

        environment.jersey().register(injector.getInstance(CalculatorService.class));
        environment.healthChecks().register("dummy", injector.getInstance(AppHealthCheck.class));
    }

    public static Injector createInjector(AppConfig config) {
        return Guice.createInjector(new AppModule(config));
    }
}