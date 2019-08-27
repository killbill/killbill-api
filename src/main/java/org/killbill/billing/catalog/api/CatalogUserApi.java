/*
 * Copyright 2010-2013 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package org.killbill.billing.catalog.api;

import org.joda.time.DateTime;
import org.killbill.billing.KillbillApi;
import org.killbill.billing.security.RequiresPermissions;
import org.killbill.billing.util.callcontext.CallContext;
import org.killbill.billing.util.callcontext.TenantContext;

import static org.killbill.billing.security.Permission.CATALOG_CAN_DELETE;
import static org.killbill.billing.security.Permission.CATALOG_CAN_UPLOAD;

/**
 * The interface {@code CatalogUserApi} to retrieve catalog information
 */
public interface CatalogUserApi extends KillbillApi {

    /**
     * Retrieves the multi versioned catalog.
     *
     * @param catalogName        the name of the catalog
     * @param catalogDateVersion optional date filter to retrieve a specific version
     * @param context            the user context that specifies the tenant information
     * @return the {@code Catalog}
     */
    Catalog getCatalog(String catalogName, DateTime catalogDateVersion, TenantContext context) throws CatalogApiException;

    /**
     * Retrieves the current catalog
     *
     * @param catalogName the name of the catalog
     * @param context     the user context that specifies the tenant information
     * @return the {@code Catalog}
     */
    StaticCatalog getCurrentCatalog(String catalogName, TenantContext context) throws CatalogApiException;

    /**
     * @param catalogXML the catalog XML
     * @param context    the user context
     * @throws CatalogApiException
     */
    @RequiresPermissions(CATALOG_CAN_UPLOAD)
    void uploadCatalog(String catalogXML, CallContext context) throws CatalogApiException;

    /**
     * Creates a per-tenant default template catalog
     *
     * @param effectiveDate the effective date for this catalog
     * @param callContext   the user context
     * @throws CatalogApiException
     */
    @RequiresPermissions(CATALOG_CAN_UPLOAD)
    void createDefaultEmptyCatalog(final DateTime effectiveDate, final CallContext callContext) throws CatalogApiException;

    /**
     * Allows to add plan definition in existing per-tenant catalog. The simplicity of the api
     * limits the types of plans that can be added (recurring evergreen plans defined for one currency
     * with or without a trial)
     *
     * @param planDescriptor
     * @param requestedDate
     * @param context
     * @throws CatalogApiException
     */
    @RequiresPermissions(CATALOG_CAN_UPLOAD)
    void addSimplePlan(SimplePlanDescriptor planDescriptor, DateTime requestedDate, CallContext context) throws CatalogApiException;

    /**
     * Delete a per-tenant catalog  (should be used with caution)
     *
     * @param callContext the user context
     * @throws CatalogApiException
     */
    @RequiresPermissions(CATALOG_CAN_DELETE)
    void deleteCatalog(CallContext callContext) throws CatalogApiException;
}
