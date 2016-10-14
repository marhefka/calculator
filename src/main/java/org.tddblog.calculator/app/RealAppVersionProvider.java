package org.tddblog.calculator.app;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

public class RealAppVersionProvider {
    public int getAppVersion() {
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
