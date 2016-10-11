package org.tddblog.calculator.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.tddblog.calculator.calc.CalculatorService;

public class App extends Application<AppConfig> {
    private Injector injector;

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Please provide exactly one argument for configuration. See config/config-istvan.yml");
        }
        new App().run("server", args[0]);
    }

    public App() {
    }

    @Override
    public String getName() {
        return "Calculator example app for tddblog.hu";
    }

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html"));
    }

    @Override
    public void run(AppConfig config,
                    Environment environment) {

        injector = createInjector(config);

        environment.jersey().setUrlPattern("/api/*");
        environment.jersey().register(injector.getInstance(CalculatorService.class));
        environment.healthChecks().register("dummy", injector.getInstance(AppHealthCheck.class));
    }

    public static Injector createInjector(AppConfig config) {
        return Guice.createInjector(new AppModule(config));
    }
}