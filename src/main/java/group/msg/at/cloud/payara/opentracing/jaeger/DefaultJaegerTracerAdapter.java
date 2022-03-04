package group.msg.at.cloud.payara.opentracing.jaeger;

import io.jaegertracing.Configuration;
import io.opentracing.*;
import io.opentracing.propagation.Format;
import io.opentracing.util.GlobalTracer;

import java.util.logging.Logger;

/**
 * Adapter that maps an OpenTracing {@link Tracer} to a Jaeger Tracer.
 */
final class DefaultJaegerTracerAdapter implements Tracer {

    private static final Logger LOGGER = Logger.getLogger(DefaultJaegerTracerAdapter.class.getName());

    private final Tracer adaptee;

    /**
     * Public constructor that can be called to initialise instance by serviceloader.
     */
    public DefaultJaegerTracerAdapter(JaegerTracerConfiguration configuration) {
        this.adaptee = registerTracer(configuration);
    }

    private Tracer registerTracer(JaegerTracerConfiguration configuration) {
        Configuration.SenderConfiguration senderConfig = Configuration.SenderConfiguration.fromEnv()
                .withAgentHost(configuration.getAgentHost())
                .withAgentPort(configuration.getAgentPort());
        Configuration.ReporterConfiguration reporterConfig = Configuration.ReporterConfiguration.fromEnv()
                .withLogSpans(configuration.isLogSpansEnabled())
                .withSender(senderConfig);
        Configuration.SamplerConfiguration samplerConfig = Configuration.SamplerConfiguration.fromEnv()
                .withType(configuration.getSamplerType());
        if (!"remote".equals(configuration.getSamplerType())) {
            samplerConfig = samplerConfig.withParam(configuration.getSamplerParam());
        }
        Configuration tracerConfig = Configuration.fromEnv(configuration.getServiceName())
                .withSampler(samplerConfig)
                .withReporter(reporterConfig);
        StringBuilder msg = new StringBuilder();
        msg.append("registering Jaeger Tracer with configuration: ");
        msg.append("serviceName=").append(tracerConfig.getServiceName());
        msg.append(", agentHost=").append(tracerConfig.getReporter().getSenderConfiguration().getAgentHost());
        msg.append(", agentPort=").append(tracerConfig.getReporter().getSenderConfiguration().getAgentPort());
        msg.append(", samplerType=").append(tracerConfig.getSampler().getType());
        msg.append(", samplerParam=").append(tracerConfig.getSampler().getParam());
        msg.append(", logSpans=").append(tracerConfig.getReporter().getLogSpans());
        LOGGER.info(msg.toString());
        Tracer result = tracerConfig.getTracer();
        GlobalTracer.registerIfAbsent(result);
        return result;
    }

    @Override
    public ScopeManager scopeManager() {
        return adaptee.scopeManager();
    }

    @Override
    public Span activeSpan() {
        return adaptee.activeSpan();
    }

    @Override
    public Scope activateSpan(Span span) {
        return adaptee.activateSpan(span);
    }

    @Override
    public SpanBuilder buildSpan(String string) {
        return adaptee.buildSpan(string);
    }

    @Override
    public <C> void inject(SpanContext sc, Format<C> format, C c) {
        adaptee.inject(sc, format, c);
    }

    @Override
    public <C> SpanContext extract(Format<C> format, C c) {
        return adaptee.extract(format, c);
    }

    @Override
    public void close() {
        adaptee.close();
    }
}
