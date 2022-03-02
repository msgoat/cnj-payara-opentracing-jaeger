package group.msg.at.cloud.payara.opentracing.jaeger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static group.msg.at.cloud.payara.opentracing.jaeger.JaegerTracerConstants.*;

public class DefaultJaegerTracingConfigurationTest {

     @AfterEach
     public void onAfterEach() {
          System.getProperties().remove(PROPERTY_JAEGER_AGENT_HOST);
     }

     @Test
     public void configurationProvidesDefaultValuesWhenNoConfigurationIsProvided() {
          JaegerTracerConfiguration underTest = new DefaultJaegerTracerConfiguration();
          assertEquals(Boolean.parseBoolean(DEFAULT_JAEGER_TRACING_ENABLED), underTest.isEnabled());
          assertEquals(DEFAULT_JAEGER_SERVICE_NAME, underTest.getServiceName());
          assertEquals(DEFAULT_JAEGER_AGENT_HOST, underTest.getAgentHost());
          assertEquals(Integer.parseInt(DEFAULT_JAEGER_AGENT_PORT), underTest.getAgentPort());
          assertEquals(Boolean.parseBoolean(DEFAULT_JAEGER_REPORTER_LOG_SPANS), underTest.isLogSpansEnabled());
          assertEquals(DEFAULT_JAEGER_SAMPLER_TYPE, underTest.getSamplerType());
          assertEquals(Double.parseDouble(DEFAULT_JAEGER_SAMPLER_PARAM), underTest.getSamplerParam());
     }

     @Test
     public void configurationProvidesSystemPropertyValueWhenSet() {
          System.setProperty(PROPERTY_JAEGER_AGENT_HOST, "jaeger");
          JaegerTracerConfiguration underTest = new DefaultJaegerTracerConfiguration();
          assertEquals("jaeger", underTest.getAgentHost());
     }

}
