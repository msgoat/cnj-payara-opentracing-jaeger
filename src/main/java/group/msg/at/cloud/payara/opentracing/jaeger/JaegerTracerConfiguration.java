package group.msg.at.cloud.payara.opentracing.jaeger;

/**
 * Provides Jaeger Tracer configuration.
 */
interface JaegerTracerConfiguration {

    boolean isEnabled();

    String getServiceName();

    String getAgentHost();

    int getAgentPort();

    boolean isLogSpansEnabled();

    String getSamplerType();

    double getSamplerParam();
}
