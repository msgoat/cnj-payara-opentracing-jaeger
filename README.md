# cnj-payara-opentracing-jaeger

This is an extension to the Payara Platform 5.194+ as an alternative implementation of Opentracing.

## Status
![Build status](https://drone.cloudtrain.aws.msgoat.eu/api/badges/msgoat/cnj-payara-opentracing-jaeger/status.svg)

## Release information

Check [changelog](changelog.md) for latest version and release information.

## Configuration

Configuration can be done with a subset of the official 
[Jaeger OpenTracing environment variables](https://github.com/jaegertracing/jaeger-client-java/blob/master/jaeger-core/README.md#configuration-via-environment).

Additionally, allows you to switch off the Jaeger OpenTracing extension by setting the boolean environment variable 
`JAEGER_TRACING_ENABLED` or system property `jaeger.tracing.enabled` to __false__.

## Using with Payara Server

This JAR must be added as a library to the server itself, and must not be included with a deployed application.

> __Attention__: This library cannot be added using the `customJars` configuration of the `payara-maven-plugin`. You will have to copy this library next to the Payara Micro Uber JAR and add it to Payara using the `--addlibs` command line argument.