package org.tddblog.calculator.calc;

import com.google.inject.Inject;
import org.mongodb.morphia.Datastore;

public class MongoCalculatorRepository implements CalculatorRepository {
    private final Datastore datastore;

    @Inject
    public MongoCalculatorRepository(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public void save(Calculator calculator) {
        datastore.save(calculator);
    }

    @Override
    public Calculator findById(String id) {
        return datastore.find(Calculator.class, "_id", id).get();
    }
}
