package group.msg.at.cloud.payara.opentracing.jaeger;

import io.jaegertracing.Configuration;
import io.opentracing.*;
import io.opentracing.propagation.Format;
import io.opentracing.util.GlobalTracer;
import org.eclipse.microprofile.config.Config;

import javax.enterprise.inject.spi.CDI;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Adapter that maps an OpenTracing {@link Tracer} to a Jaeger Tracer.
 */
public class JaegerTracerAdapter implements io.opentracing.Tracer {

    private static final Logger LOGGER = Logger.getLogger(JaegerTracerAdapter.class.getName());
    private static final String CONFIG_JAEGER_SERVICE_NAME = "jaeger.service.name";
    private static final String CONFIG_JAEGER_AGENT_HOST = "jaeger.agent.host";
    private static final String CONFIG_JAEGER_AGENT_PORT = "jaeger.agent.port";
    private static final String CONFIG_JAEGER_REPORTER_LOG_SPANS = "jaeger.reporter.log-spans";
    private static final String CONFIG_JAEGER_SAMPLER_TYPE = "jaeger.sampler.type";
    private static final String CONFIG_JAEGER_SAMPLER_PARAM = "jaeger.sampler.param";
    private static Tracer adaptee;

    /**
     * Public constructor that can be called to initialise instance by serviceloader.
     */
    public JaegerTracerAdapter() {
        setUpTracer();
    }

    private synchronized void setUpTracer() {
        if (adaptee == null) {
            Config config = CDI.current().select(Config.class).get();
            String jaegerServiceName = config.getValue(CONFIG_JAEGER_SERVICE_NAME, String.class);
            String jaegerAgentHost = config.getValue(CONFIG_JAEGER_AGENT_HOST, String.class);
            int jaegerAgentPort = config.getValue(CONFIG_JAEGER_AGENT_PORT, Integer.class);
            Optional<Boolean> logSpansValue = config.getOptionalValue(CONFIG_JAEGER_REPORTER_LOG_SPANS, Boolean.class);
            boolean logSpans = false;
            if (logSpansValue.isPresent()) {
                logSpans = logSpansValue.get();
            }
            String jaegerSamplerType = config.getValue(CONFIG_JAEGER_SAMPLER_TYPE, String.class);
            Optional<Double> jaegerSamplerParam = config.getOptionalValue(CONFIG_JAEGER_SAMPLER_PARAM, Double.class);
            Configuration.SenderConfiguration senderConfig = Configuration.SenderConfiguration.fromEnv()
                    .withAgentHost(jaegerAgentHost)
                    .withAgentPort(jaegerAgentPort);
            Configuration.ReporterConfiguration reporterConfig = Configuration.ReporterConfiguration.fromEnv()
                    .withLogSpans(logSpans)
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
        return adaptee.scopeManager();
    }

    @Override
    public Span activeSpan() {
        return adaptee.activeSpan();
    }
/*
    @Override
    public Scope activateSpan(Span span) {
        return wrappedTracer.activateSpan(span);
    }
*/
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
/*
    @Override
    public void close() {
        wrappedTracer.close();
    }
*/
}
