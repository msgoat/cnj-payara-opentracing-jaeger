package group.msg.at.cloud.payara.opentracing.jaeger;

/**
 * Common constants used by all Jaeger Tracing components.
 */
interface JaegerTracerConstants {

    String PROPERTY_JAEGER_TRACING_ENABLED = "jaeger.tracing.enabled";

    String ENVVAR_JAEGER_TRACING_ENABLED = "JAEGER_TRACING_ENABLED";

    String DEFAULT_JAEGER_TRACING_ENABLED = "true";

    String PROPERTY_JAEGER_SERVICE_NAME = "jaeger.service.name";

    String ENVVAR_JAEGER_SERVICE_NAME = "JAEGER_SERVICE_NAME";

    String DEFAULT_JAEGER_SERVICE_NAME = "jaeger-service";

    String PROPERTY_JAEGER_AGENT_HOST = "jaeger.agent.host";

    String ENVVAR_JAEGER_AGENT_HOST = "JAEGER_AGENT_HOST";

    String DEFAULT_JAEGER_AGENT_HOST = "localhost";

    String PROPERTY_JAEGER_AGENT_PORT = "jaeger.agent.port";

    String ENVVAR_JAEGER_AGENT_PORT = "JAEGER_AGENT_PORT";

    String DEFAULT_JAEGER_AGENT_PORT = "6831";

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
