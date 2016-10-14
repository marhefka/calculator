package org.tddblog.calculator.app;

import io.dropwizard.configuration.UndefinedEnvironmentVariableException;
import org.apache.commons.lang3.text.StrLookup;
import org.apache.commons.lang3.text.StrSubstitutor;

public class CfEnvironmentVariableSubstitutor extends StrSubstitutor {
    public CfEnvironmentVariableSubstitutor(boolean strict, boolean substitutionInVariables) {
        super(new SmarthomeEnvironmentVariableLookup(strict));
        this.setEnableSubstitutionInVariables(substitutionInVariables);
    }

    public static class SmarthomeEnvironmentVariableLookup extends StrLookup<Object> {
        private final boolean strict;

        public SmarthomeEnvironmentVariableLookup(boolean strict) {
            this.strict = strict;
        }

        @Override
        public String lookup(String key) {
            final String value = System.getenv(key);

            if (value == null && strict) {
                throw new UndefinedEnvironmentVariableException("The environment variable '" + key
                        + "' is not defined; could not substitute the expression '${"
                        + key + "}'.");
            }

            return value;
        }
    }
}
