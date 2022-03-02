package group.msg.at.cloud.payara.opentracing.jaeger;

import io.jaegertracing.Configuration;
import io.opentracing.*;
import io.opentracing.propagation.Format;
import io.opentracing.util.GlobalTracer;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Adapter that maps an OpenTracing {@link Tracer} to a Jaeger Tracer.
 */
@Named
@Singleton
public class CdiAwareJaegerTracerAdapter implements Tracer {

    private static final Logger LOGGER = Logger.getLogger(CdiAwareJaegerTracerAdapter.class.getName());

    private static final String CONFIG_JAEGER_SERVICE_NAME = "jaeger.service.name";

    private static final String CONFIG_JAEGER_AGENT_HOST = "jaeger.agent.host";

    private static final String CONFIG_JAEGER_AGENT_PORT = "jaeger.agent.port";

    private static final String CONFIG_JAEGER_REPORTER_LOG_SPANS = "jaeger.reporter.log-spans";

    private static final String CONFIG_JAEGER_SAMPLER_TYPE = "jaeger.sampler.type";

    private static final String CONFIG_JAEGER_SAMPLER_PARAM = "jaeger.sampler.param";

    @Inject
    @ConfigProperty(name = CONFIG_JAEGER_SERVICE_NAME, defaultValue = "jaeger-service")
    String jaegerServiceName;

    @Inject
    @ConfigProperty(name = CONFIG_JAEGER_AGENT_HOST, defaultValue = "localhost")
    String jaegerAgentHost;

    @Inject
    @ConfigProperty(name = CONFIG_JAEGER_AGENT_PORT, defaultValue = "6831")
    int jaegerAgentPort;

    @Inject
    @ConfigProperty(name = CONFIG_JAEGER_REPORTER_LOG_SPANS, defaultValue = "false")
    boolean jaegerLogSpans;

    @Inject
    @ConfigProperty(name = CONFIG_JAEGER_SAMPLER_TYPE, defaultValue = "const")
    String jaegerSamplerType;

    @Inject
    @ConfigProperty(name = CONFIG_JAEGER_SAMPLER_PARAM)
    Optional<Double> jaegerSamplerParam;

    private Tracer adaptee;

    private synchronized void ensureJaegerTracer() {
        if (adaptee == null) {
            LOGGER.info("registering Jaeger OpenTracing Tracer");
            Configuration.SenderConfiguration senderConfig = Configuration.SenderConfiguration.fromEnv()
                    .withAgentHost(jaegerAgentHost)
                    .withAgentPort(jaegerAgentPort);
            Configuration.ReporterConfiguration reporterConfig = Configuration.ReporterConfiguration.fromEnv()
                    .withLogSpans(jaegerLogSpans)
                    .withSender(senderConfig);
            Configuration.SamplerConfiguration samplerConfig = Configuration.SamplerConfiguration.fromEnv()
                    .withType(jaegerSamplerType);
            if (jaegerSamplerParam.isPresent()) {
                samplerConfig = samplerConfig.withParam(jaegerSamplerParam.get());
            }
            Configuration tracerConfig = Configuration.fromEnv(jaegerServiceName)
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
            adaptee = tracerConfig.getTracer();
            GlobalTracer.register(adaptee);
        }
    }

    @Override
    public ScopeManager scopeManager() {
        ensureJaegerTracer();
        return adaptee.scopeManager();
    }

    @Override
    public Span activeSpan() {
        ensureJaegerTracer();
        return adaptee.activeSpan();
    }

    @Override
    public Scope activateSpan(Span span) {
        ensureJaegerTracer();
        return adaptee.activateSpan(span);
    }

    @Override
    public SpanBuilder buildSpan(String string) {
        ensureJaegerTracer();
        return adaptee.buildSpan(string);
    }

    @Override
    public <C> void inject(SpanContext sc, Format<C> format, C c) {
        ensureJaegerTracer();
        adaptee.inject(sc, format, c);
    }

    @Override
    public <C> SpanContext extract(Format<C> format, C c) {
        ensureJaegerTracer();
        return adaptee.extract(format, c);
    }

    @Override
    public void close() {
        ensureJaegerTracer();
        adaptee.close();
    }
}
