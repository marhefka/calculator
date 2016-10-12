package org.tddblog.calculator.app;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationStartTest {
    @Test
    public void applicationStartsSuccessfully() throws Exception {
        new App().run("server", "src/test/resources/config-dropwizard-test.yml");

        try (WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            webClient.getOptions().setUseInsecureSSL(true);
            HtmlPage page = webClient.getPage("http://localhost:9000");

            String attrDisabled = page.getElementById("btn7").getAttribute("disabled");
            assertThat(attrDisabled).isEqualTo("disabled");

            DomElement createButton = page.getElementById("create");
            createButton.click();

            Thread.sleep(500);

            String attrDisabled2 = page.getElementById("btn7").getAttribute("disabled");
            assertThat(attrDisabled2).isNullOrEmpty();
        }
    }
}
