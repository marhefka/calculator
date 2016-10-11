package org.tddblog.calculator.app;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationStartTest {
    @Test
    public void applicationStartsSuccessfully() throws Exception {
        new App().run("server", "src/test/resources/config-dropwizard-test.yml");

        HttpResponse<String> response = Unirest.post("http://localhost:9000/calculator/createCalculator").asString();
        assertThat(response.getStatus()).isEqualTo(200);
    }
}
