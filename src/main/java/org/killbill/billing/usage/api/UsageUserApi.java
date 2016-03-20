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

package org.killbill.billing.usage.api;

import java.util.List;
import java.util.UUID;

import org.joda.time.LocalDate;
import org.killbill.billing.KillbillApi;
import org.killbill.billing.security.RequiresPermissions;
import org.killbill.billing.util.callcontext.CallContext;
import org.killbill.billing.util.callcontext.TenantContext;

import static org.killbill.billing.security.Permission.USAGE_CAN_RECORD;

public interface UsageUserApi extends KillbillApi {

    /**
     * Bulk usage API when the external system (or the meter module) rolls-up usage data.
     * <p/>
     *
     * @param usage   the usage for a given period of time associated with a subscription
     * @param context tenant context
     */
    @RequiresPermissions(USAGE_CAN_RECORD)
    public void recordRolledUpUsage(SubscriptionUsageRecord usage, CallContext context) throws UsageApiException;

    /**
     * Get usage information for a given subscription.
     *
     * @param subscriptionId subscription id
     * @param unitType       unit type for this usage
     * @param startDate      start date of the usage period (with respect to the account timezone)
     * @param endDate        end date of the usage period (with respect to the account timezone)
     * @param context        tenant context
     * @return usage data (rolled-up)
     */
    public RolledUpUsage getUsageForSubscription(UUID subscriptionId, String unitType, LocalDate startDate, LocalDate endDate, TenantContext context);

    /**
     * Get usage information for a given subscription.
     *
     * @param subscriptionId subscription id
     * @param context        tenant context
     * @return usage data (rolled-up)
     */
    public List<RolledUpUsage> getAllUsageForSubscription(UUID subscriptionId, List<LocalDate> transitionDates, TenantContext context);
}
