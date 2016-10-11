package org.tddblog.calculator.mongo;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.tddblog.calculator.calc.Calculator;

import java.util.Arrays;
import java.util.logging.Level;

@Singleton
public class MongoConnector implements Provider<Datastore> {
    public final static Class[] ENTITIES = new Class[]{
            Calculator.class
    };

    private final MongoConfig mongoConfig;
    private Datastore datastore;

    @Inject
    public MongoConnector(MongoConfig mongoConfig) {
        this.mongoConfig = mongoConfig;
    }

    @Inject
    public void init() {        // init() method with @Inject annotation is always executed automatically by Guice when object is instantiated
        java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);

        MongoClientOptions.Builder clientOptionsBuilder = new MongoClientOptions.Builder()
                .connectionsPerHost(mongoConfig.getClientPoolSize());

        MongoClientURI mongoClientURI = new MongoClientURI(mongoConfig.getUri(), clientOptionsBuilder);
        MongoClient mongoClient = new MongoClient(mongoClientURI);

        Morphia morphia = new Morphia();
        morphia.getMapper().getOptions().setStoreNulls(true);
        morphia.getMapper().getOptions().setObjectFactory(new CustomMorphiaObjectFactory());
        morphia.getMapper().getConverters().addConverter(BigDecimalConverter.class);

        Arrays.stream(ENTITIES).forEach(morphia::map);

        datastore = morphia.createDatastore(mongoClient, mongoClientURI.getDatabase());
    }

    @Override
    public Datastore get() {
        return datastore;
    }
}
