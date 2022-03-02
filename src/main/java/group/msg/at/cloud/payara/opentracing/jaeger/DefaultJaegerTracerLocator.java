package group.msg.at.cloud.payara.opentracing.jaeger;

import io.opentracing.*;
import io.opentracing.noop.NoopTracerFactory;
import io.opentracing.propagation.Format;

public final class DefaultJaegerTracerLocator implements Tracer {

    private final Tracer adaptee;

    public DefaultJaegerTracerLocator() {
        DefaultJaegerTracerConfiguration configuration = new DefaultJaegerTracerConfiguration();
        if (configuration.isEnabled()) {
            this.adaptee = new DefaultJaegerTracerAdapter(configuration);
        } else {
            this.adaptee = NoopTracerFactory.create();
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

    @Override
    public SpanBuilder buildSpan(String s) {
        return adaptee.buildSpan(s);
    }

    @Override
    public <C> void inject(SpanContext spanContext, Format<C> format, C c) {
        adaptee.inject(spanContext, format, c);
    }

    @Override
    public <C> SpanContext extract(Format<C> format, C c) {
        return adaptee.extract(format, c);
    }

    @Override
    public Scope activateSpan(Span span) {
        return adaptee.activateSpan(span);
    }

    @Override
    public void close() {
        adaptee.close();
    }
}
