/*
 * Copyright 2010-2013 Ning, Inc.
 * Copyright 2014 Groupon, Inc
 * Copyright 2014 The Billing Project, LLC
 *
 * The Billing Project licenses this file to you under the Apache License, version 2.0
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

package org.killbill.billing.overdue.api;

import java.util.UUID;

import org.killbill.billing.KillbillApi;
import org.killbill.billing.account.api.ImmutableAccountData;
import org.killbill.billing.security.RequiresPermissions;
import org.killbill.billing.util.callcontext.CallContext;
import org.killbill.billing.util.callcontext.TenantContext;

import static org.killbill.billing.security.Permission.OVERDUE_CAN_UPLOAD;

public interface OverdueApi extends KillbillApi {

    /**
     * @param context the tenant information.
     * @return the overdue configuration associated with the tenant
     * @throws OverdueApiException
     */
    OverdueConfig getOverdueConfig(TenantContext context) throws OverdueApiException;

    /**
     * @param context the context
     * @throws OverdueApiException
     */
    @RequiresPermissions(OVERDUE_CAN_UPLOAD)
    void uploadOverdueConfig(String overdueXML, CallContext context) throws OverdueApiException;

    /**
     *
     * @param accountId the account Id
     * @param context   the context
     * @return
     * @throws OverdueApiException
     */
    public OverdueState getOverdueStateFor(UUID accountId, TenantContext context) throws OverdueApiException;

    /**
     *
     * @param overdueConfig          new overdue config
     * @param callContext            the context
     * @throws OverdueApiException
     */
    public void uploadOverdueConfig(final OverdueConfig overdueConfig, final CallContext callContext) throws OverdueApiException;
}
