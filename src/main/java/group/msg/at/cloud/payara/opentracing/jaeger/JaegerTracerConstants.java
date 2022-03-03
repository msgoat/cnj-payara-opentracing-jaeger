package group.msg.at.cloud.payara.opentracing.jaeger;

/**
 * Common constants used by all Jaeger Tracing components.
 */
interface JaegerTracerConstants {

    String PROPERTY_JAEGER_ENABLED = "jaeger.enabled";

    String ENVVAR_JAEGER_ENABLED = "JAEGER_ENABLED";

    String DEFAULT_JAEGER_ENABLED = "true";

    String PROPERTY_JAEGER_SERVICE_NAME = "jaeger.service.name";

    String ENVVAR_JAEGER_SERVICE_NAME = "JAEGER_SERVICE_NAME";

    String DEFAULT_JAEGER_SERVICE_NAME = "jaeger-service";

    String PROPERTY_JAEGER_AGENT_HOST = "jaeger.agent.host";

    String ENVVAR_JAEGER_AGENT_HOST = "JAEGER_AGENT_HOST";

    String DEFAULT_JAEGER_AGENT_HOST = "localhost";

    String PROPERTY_JAEGER_AGENT_PORT = "jaeger.agent.port";

    String ENVVAR_JAEGER_AGENT_PORT = "JAEGER_AGENT_PORT";

    String DEFAULT_JAEGER_AGENT_PORT = "6831";

    String PROPERTY_JAEGER_ENDPOINT = "jaeger.endpoint";

    String ENVVAR_JAEGER_ENDPOINT = "JAEGER_ENDPOINT";

    String PROPERTY_JAEGER_AUTH_TOKEN = "jaeger.authtoken";

    String ENVVAR_JAEGER_AUTH_TOKEN = "JAEGER_AUTH_TOKEN";

    String PROPERTY_JAEGER_USER = "jaeger.user";

    String ENVVAR_JAEGER_USER = "JAEGER_USER";

    String PROPERTY_JAEGER_PASSWORD = "jaeger.password";

    String ENVVAR_JAEGER_PASSWORD = "JAEGER_PASSWORD";

    String PROPERTY_JAEGER_REPORTER_LOG_SPANS = "jaeger.reporter.log-spans";

    String ENVVAR_JAEGER_REPORTER_LOG_SPANS = "JAEGER_REPORTER_LOG_SPANS";

    String DEFAULT_JAEGER_REPORTER_LOG_SPANS = "false";

    String PROPERTY_JAEGER_SAMPLER_TYPE = "jaeger.sampler.type";

    String ENVVAR_JAEGER_SAMPLER_TYPE = "JAEGER_SAMPLER_TYPE";

    String DEFAULT_JAEGER_SAMPLER_TYPE = "const";

    String PROPERTY_JAEGER_SAMPLER_PARAM = "jaeger.sampler.param";

    String ENVVAR_JAEGER_SAMPLER_PARAM = "JAEGER_SAMPLER_PARAM";

    String DEFAULT_JAEGER_SAMPLER_PARAM = "1.0";
}
