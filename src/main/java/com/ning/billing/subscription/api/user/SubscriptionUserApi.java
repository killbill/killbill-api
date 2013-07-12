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

package com.ning.billing.subscription.api.user;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.ning.billing.catalog.api.PlanPhaseSpecifier;
import com.ning.billing.util.callcontext.CallContext;
import com.ning.billing.util.callcontext.TenantContext;

public interface SubscriptionUserApi {

    public SubscriptionBundle getBundleFromId(UUID id, TenantContext context) throws SubscriptionUserApiException;

    public Subscription getSubscriptionFromId(UUID id, TenantContext context) throws SubscriptionUserApiException;

    public List<SubscriptionBundle> getBundlesForKey(String bundleKey, TenantContext context) throws SubscriptionUserApiException;

    public SubscriptionBundle getBundleForAccountAndKey(UUID accountId, String bundleKey, TenantContext context) throws SubscriptionUserApiException;

    public List<SubscriptionBundle> getBundlesForAccount(UUID accountId, TenantContext context);

    public List<Subscription> getSubscriptionsForBundle(UUID bundleId, TenantContext context);

    public List<Subscription> getSubscriptionsForAccountAndKey(UUID accountId, String bundleKey, TenantContext context);

    public Subscription getBaseSubscription(UUID bundleId, TenantContext context) throws SubscriptionUserApiException;

    public SubscriptionBundle createBundleForAccount(UUID accountId, String bundleKey, CallContext context)
            throws SubscriptionUserApiException;

    public Subscription createSubscription(UUID bundleId, PlanPhaseSpecifier spec, DateTime requestedDate, CallContext context)
            throws SubscriptionUserApiException;

    public List<SubscriptionStatusDryRun> getDryRunChangePlanStatus(UUID subscriptionId, /* @Nullable */ String productName,
                                                                    DateTime requestedDate, TenantContext context)
            throws SubscriptionUserApiException;

    public DateTime getNextBillingDate(UUID account, TenantContext context);
}
