package group.msg.at.cloud.payara.opentracing.jaeger;

import static group.msg.at.cloud.payara.opentracing.jaeger.JaegerTracerConstants.*;

/**
 * Default implementation of {@code JaegerTracerConfiguration} which pulls configuration values from
 * environment variables and system properties.
 */
final class DefaultJaegerTracerConfiguration implements JaegerTracerConfiguration {
    private static <T> T getConfigurationValue(String envName, String propertyName, String defaultValue, Class<T> expectedType) {
        T result = null;
        String rawValue = System.getenv(envName);
        if (rawValue == null) {
            rawValue = System.getProperty(propertyName, defaultValue);
        }
        if (rawValue != null) {
            if (expectedType.isAssignableFrom(String.class)) {
                result = expectedType.cast(rawValue);
            } else if (expectedType.isAssignableFrom(Boolean.class)) {
                result = expectedType.cast(Boolean.parseBoolean(rawValue));
            } else if (expectedType.isAssignableFrom(Integer.class)) {
                result = expectedType.cast(Integer.parseInt(rawValue));
            } else if (expectedType.isAssignableFrom(Double.class)) {
                result = expectedType.cast(Double.parseDouble(rawValue));
            } else {
                throw new IllegalArgumentException(String.format("Unsupported configuration value type [%s]", expectedType.getName()));
            }
        }
        return result;
    }

    @Override
    public boolean isEnabled() {
        return getConfigurationValue(ENVVAR_JAEGER_ENABLED, PROPERTY_JAEGER_ENABLED, DEFAULT_JAEGER_ENABLED, Boolean.class);
    }

    @Override
    public String getServiceName() {
        return getConfigurationValue(ENVVAR_JAEGER_SERVICE_NAME, PROPERTY_JAEGER_SERVICE_NAME, DEFAULT_JAEGER_SERVICE_NAME, String.class);
    }

    @Override
    public String getAgentHost() {
        return getConfigurationValue(ENVVAR_JAEGER_AGENT_HOST, PROPERTY_JAEGER_AGENT_HOST, DEFAULT_JAEGER_AGENT_HOST, String.class);
    }

    @Override
    public int getAgentPort() {
        return getConfigurationValue(ENVVAR_JAEGER_AGENT_PORT, PROPERTY_JAEGER_AGENT_PORT, DEFAULT_JAEGER_AGENT_PORT, Integer.class);
    }

    @Override
    public boolean isLogSpansEnabled() {
        return getConfigurationValue(ENVVAR_JAEGER_REPORTER_LOG_SPANS, PROPERTY_JAEGER_REPORTER_LOG_SPANS, DEFAULT_JAEGER_REPORTER_LOG_SPANS, Boolean.class);
    }

    @Override
    public String getSamplerType() {
        return getConfigurationValue(ENVVAR_JAEGER_SAMPLER_TYPE, PROPERTY_JAEGER_SAMPLER_TYPE, DEFAULT_JAEGER_SAMPLER_TYPE, String.class);
    }

    @Override
    public double getSamplerParam() {
        return getConfigurationValue(ENVVAR_JAEGER_SAMPLER_PARAM, PROPERTY_JAEGER_SAMPLER_PARAM, DEFAULT_JAEGER_SAMPLER_PARAM, Double.class);
    }
}
