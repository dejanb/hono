/**
 * Copyright (c) 2020 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.hono.adapter.mqtt.quarkus;

import java.time.Duration;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.eclipse.hono.service.resourcelimits.NoopResourceLimitChecks;
import org.eclipse.hono.service.resourcelimits.PrometheusBasedResourceLimitChecks;
import org.eclipse.hono.service.resourcelimits.ResourceLimitChecks;

import com.github.benmanes.caffeine.cache.Caffeine;

import io.opentracing.Tracer;
import io.quarkus.arc.DefaultBean;
import io.quarkus.arc.properties.IfBuildProperty;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;

/**
 * A factory class that creates resource limit checks based on the profile.
 */
public class ResourceLimitChecksFactory {

    @Inject
    Vertx vertx;

    @Inject
    HonoConfig config;

    @Inject
    Tracer tracer;

    @Produces
    @IfBuildProperty(name = "hono.metrics", stringValue = "prometheus")
    ResourceLimitChecks prometheusResourceLimitChecks() {

        final WebClientOptions webClientOptions = new WebClientOptions();
        webClientOptions.setDefaultHost(config.resourceLimitChecks.getHost());
        webClientOptions.setDefaultPort(config.resourceLimitChecks.getPort());
        webClientOptions.setTrustOptions(config.resourceLimitChecks.getTrustOptions());
        webClientOptions.setKeyCertOptions(config.resourceLimitChecks.getKeyCertOptions());
        webClientOptions.setSsl(config.resourceLimitChecks.isTlsEnabled());

        final Caffeine<Object, Object> builder = Caffeine.newBuilder()
                .initialCapacity(config.resourceLimitChecks.getCacheMinSize())
                .maximumSize(config.resourceLimitChecks.getCacheMaxSize())
                .expireAfterWrite(Duration.ofSeconds(config.resourceLimitChecks.getCacheTimeout()));

        return new PrometheusBasedResourceLimitChecks(
                WebClient.create(vertx, webClientOptions),
                config.resourceLimitChecks,
                builder.buildAsync(),
                builder.buildAsync(),
                builder.buildAsync(),
                tracer);
    }

    @Produces
    @DefaultBean
    ResourceLimitChecks noopResourceLimitChecks() {
       return new NoopResourceLimitChecks();
    }

}
