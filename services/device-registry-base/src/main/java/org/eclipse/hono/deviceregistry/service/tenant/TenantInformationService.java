/*******************************************************************************
 * Copyright (c) 2019, 2020 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.hono.deviceregistry.service.tenant;

import org.eclipse.hono.service.management.Result;

import io.opentracing.Span;
import io.vertx.core.Future;

/**
 * A service which provides tenant information to internal service implementations.
 * <p>
 * It is used to verify that the tenant exists and validates its data before operations are executed on the devices and credentials.
 * It has particular value when tenants are stored in external systems to Hono.
 * For registries that store tenants internally, it can use embedded tenant service (if available).
 */
public interface TenantInformationService {

    /**
     * Checks whether tenant exists in the registry or external system.
     *
     * @param tenantId The identifier of the tenant.
     * @param span The active OpenTracing span for this operation. It is not to be closed in this method!
     *            An implementation should log (error) events on this span and it may set tags and use this span as the
     *            parent for any spans created in this method.
     * @return A future indicating the outcome of the operation. The <em>status</em> will be
     *            <ul>
     *            <li><em>200 OK</em> if a tenant with the given ID exists. The <em>payload</em> will contain the
     *            tenant's unique key.</li>
     *            <li><em>404 Not Found</em> if no tenant with the given identifier exists.</li>
     *            </ul>
     */
     Future<Result<TenantKey>> tenantExists(String tenantId, Span span);

}
