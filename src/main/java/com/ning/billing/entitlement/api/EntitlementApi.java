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

package com.ning.billing.entitlement.api;

import com.ning.billing.catalog.api.PlanPhaseSpecifier;
import com.ning.billing.util.callcontext.CallContext;
import com.ning.billing.util.callcontext.TenantContext;

import java.util.List;
import java.util.UUID;

public interface EntitlementApi {

    public Entitlement createBaseEntitlement(UUID accountId, PlanPhaseSpecifier spec, String externalKey, CallContext context)
            throws EntitlementApiException;

    public Entitlement addEntitlement(UUID baseEntitlementId, PlanPhaseSpecifier spec, CallContext context)
            throws EntitlementApiException;

    public Entitlement getEntitlementFromId(UUID id, TenantContext context) throws EntitlementApiException;

    public List<Entitlement> getAllEntitlementFromBaseId(UUID baseEntitlementId, TenantContext context) throws EntitlementApiException;

    public List<Entitlement> getAllEntitlementForAccountIdAndExternalKey(UUID accountId, String externalKey, TenantContext context) throws EntitlementApiException;

    public List<Entitlement> getAllEntitlementFromAccountId(UUID accountId, TenantContext context) throws EntitlementApiException;


    /**
     * @param overdueableId the uuid of the object potentially in an overduabke state
     * @param context       the context associated to that call
     * @return a list of all the blocking states for that object
     */
    public List<BlockingState> getBlockingHistory(UUID overdueableId, TenantContext context);
}
