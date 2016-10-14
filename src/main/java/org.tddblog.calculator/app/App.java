package org.tddblog.calculator.app;

import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.json.JSONObject;
import org.tddblog.calculator.calc.CalculatorService;
import org.tddblog.dbmigr.DbMigrConfig;
import org.tddblog.dbmigr.DbMigrationModule;
import org.tddblog.dbmigr.DbMigrationService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

public class App extends Application<AppConfig> {
    private Injector injector;

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Please provide exactly one argument for configuration. See config/config-istvan.yml");
        }

        App app = new App();

//        app.migrateDb();
        app.run("server", args[0]);
    }

    public App() {
    }

    @Override
    public String getName() {
        return "Calculator example app for tddblog.hu";
    }

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new CfEnvironmentVariableSubstitutor(false, false)
                )
        );
        bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html"));
    }

    public void migrateDb() {
        JSONObject vcapServices = new JSONObject(System.getenv("VCAP_SERVICES"));
        JSONObject mongodb = (JSONObject) vcapServices.getJSONArray("mongodb").get(0);
        String mongodbUri = mongodb.getString("uri");

        DbMigrConfig dbMigrConfig = new DbMigrConfig()
                .setMongoDbUri(mongodbUri)
                .setMigrationScriptClassPrefix("org.tddblog.dbmigr.script.Migr%03d")
                .setPrimary(System.getenv("CF_INSTANCE_INDEX").equals("0"))
                .setAppVersion(getAppVersion());

        Injector injector = Guice.createInjector(new DbMigrationModule(dbMigrConfig));
        DbMigrationService dbMigrationService = injector.getInstance(DbMigrationService.class);
        dbMigrationService.doMigration();
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

    private boolean isCloudFoundryEnvironment() {
        return !Strings.isNullOrEmpty(System.getenv("CF_INSTANCE_INDEX"));
    }

    private int getAppVersion() {
        try {
            // the file is created during commit stage by CI tool
            URL resourceUrl = getClass().getClassLoader().getResource("version.txt");
            File file = new File(resourceUrl.getFile());
            String firstLine = Files.readFirstLine(file, Charset.forName("UTF-8"));
            return Integer.parseInt(firstLine);
        } catch (IOException ex) {
            throw new RuntimeException("Error reading version.txt", ex);
        }
    }
}