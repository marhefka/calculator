package org.tddblog.calculator.mongo;

public class MongoConfig {
    private String uri;
    private int clientPoolSize;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getClientPoolSize() {
        return clientPoolSize;
    }

    public void setClientPoolSize(int clientPoolSize) {
        this.clientPoolSize = clientPoolSize;
    }
}
