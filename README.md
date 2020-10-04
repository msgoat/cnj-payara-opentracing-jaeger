# cnj-payara-opentracing-jaeger

This is an extension to the Payara Platform 5.194+ as an alternative implementation of Opentracing.

## Status
![Build status](https://drone.at41tools.k8s.aws.msgoat.eu/api/badges/msgoat/cnj-payara-opentracing-jaeger/status.svg)

## Release information

Latest version is: `0.8.0`.

A changelog can be found in [changelog.md](changelog.md).

## Configuration

Configuration can be done with MP Config properties which match to the official Jaeger environment variables.

See https://github.com/jaegertracing/jaeger-client-java/blob/master/jaeger-core/README.md#configuration-via-environment 

## Using with Payara Server

This must be added as a library to the server itself, not included with a deployed application.
