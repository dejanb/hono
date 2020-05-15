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
package org.eclipse.hono.adapter.http.quarkus;

import io.quarkus.arc.config.ConfigProperties;
import static io.vertx.core.Vertx.vertx;

import org.eclipse.hono.adapter.http.HttpProtocolAdapterProperties;
import org.eclipse.hono.client.RequestResponseClientConfigProperties;
import org.eclipse.hono.config.ApplicationConfigProperties;
import org.eclipse.hono.config.ServerConfig;

@ConfigProperties(prefix = "hono", namingStrategy = ConfigProperties.NamingStrategy.VERBATIM, failOnMismatchingMember = false)
public class HonoConfig {

     public CommandConfig command;

     public HealthCheckConfig healthCheck;

     public ApplicationConfig app;

     public HttpConfig http;

     public CredentialsClientConfig credentials;

     public DeviceConnectionConfig deviceConnection;

     public DownstreamSenderConfig messaging;

     public RegistrationClientConfig registration;

     public TenantClientConfig tenant;

     @ConfigProperties(prefix = "hono.command", failOnMismatchingMember = false)
     public static class CommandConfig extends RequestResponseClientConfigProperties {}

     @ConfigProperties(prefix = "hono.health-check", failOnMismatchingMember = false)
     public static class HealthCheckConfig extends ServerConfig {}

     @ConfigProperties(prefix = "hono.app", failOnMismatchingMember = false)
     public static class ApplicationConfig extends ApplicationConfigProperties {}

     @ConfigProperties(prefix = "hono.adapter", failOnMismatchingMember = false)
     public static class HttpConfig extends HttpProtocolAdapterProperties {}

     @ConfigProperties(prefix = "hono.credentials", failOnMismatchingMember = false)
     public static class CredentialsClientConfig extends RequestResponseClientConfigProperties {}

     @ConfigProperties(prefix = "hono.device-connection", failOnMismatchingMember = false)
     public static class DeviceConnectionConfig extends RequestResponseClientConfigProperties {}

     @ConfigProperties(prefix = "hono.messaging", failOnMismatchingMember = false)
     public static class DownstreamSenderConfig extends RequestResponseClientConfigProperties {}

     @ConfigProperties(prefix = "hono.registration", failOnMismatchingMember = false)
     public static class RegistrationClientConfig extends RequestResponseClientConfigProperties {}

     @ConfigProperties(prefix = "hono.tenant", failOnMismatchingMember = false)
     public static class TenantClientConfig extends RequestResponseClientConfigProperties {}
}
