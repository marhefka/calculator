package org.tddblog.calculator.calc;

import com.google.inject.Singleton;

@Singleton      // to preserve id
public class DummyIdGenerator implements IdGenerator {
    private String id;

    @Override
    public String generateId() {
        if (id == null) {
            throw new RuntimeException("Id is not mocked.");
        }

        return id;
    }

    public void mockId(String id) {
        this.id = id;
    }
}
