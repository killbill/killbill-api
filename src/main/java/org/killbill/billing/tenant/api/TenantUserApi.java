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

package org.killbill.billing.tenant.api;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.killbill.billing.KillbillApi;
import org.killbill.billing.security.RequiresPermissions;
import org.killbill.billing.util.callcontext.CallContext;
import org.killbill.billing.util.callcontext.TenantContext;

import static org.killbill.billing.security.Permission.TENANT_CAN_CREATE;
import static org.killbill.billing.security.Permission.TENANT_KEYS_CAN_ADD;
import static org.killbill.billing.security.Permission.TENANT_KEYS_CAN_DELETE;

public interface TenantUserApi extends KillbillApi {

    @RequiresPermissions(TENANT_CAN_CREATE)
    public Tenant createTenant(final TenantData data, final CallContext context) throws TenantApiException;

    public Tenant getTenantByApiKey(final String key) throws TenantApiException;

    public Tenant getTenantById(final UUID tenantId) throws TenantApiException;

    public List<String> getTenantValuesForKey(final String key, final TenantContext context) throws TenantApiException;

    public Map<String, List<String>> searchTenantKeyValues(String searchKey, TenantContext context) throws TenantApiException;

    @RequiresPermissions(TENANT_KEYS_CAN_ADD)
    public void addTenantKeyValue(final String key, final String value, final CallContext context) throws TenantApiException;

    @RequiresPermissions(TENANT_KEYS_CAN_ADD)
    public void updateTenantKeyValue(final String key, final String value, final CallContext context) throws TenantApiException;

    @RequiresPermissions(TENANT_KEYS_CAN_DELETE)
    public void deleteTenantKey(final String key, final CallContext context) throws TenantApiException;
}
