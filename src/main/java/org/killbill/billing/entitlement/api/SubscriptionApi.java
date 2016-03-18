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

package org.killbill.billing.entitlement.api;

import java.util.List;
import java.util.UUID;

import org.joda.time.LocalDate;
import org.killbill.billing.KillbillApi;
import org.killbill.billing.OrderingType;
import org.killbill.billing.payment.api.PluginProperty;
import org.killbill.billing.security.RequiresPermissions;
import org.killbill.billing.util.callcontext.CallContext;
import org.killbill.billing.util.callcontext.TenantContext;
import org.killbill.billing.util.entity.Pagination;

import static org.killbill.billing.security.Permission.ENTITLEMENT_CAN_CREATE;
import static org.killbill.billing.security.Permission.ENTITLEMENT_CAN_PAUSE_RESUME;

/**
 * API to manage the retrieval of <code>Subscription</code> information.
 */
public interface SubscriptionApi extends KillbillApi {

    int PAST_EVENTS = 0x1;
    int PRESENT_EVENTS = 0x2;
    int FUTURE_EVENTS = 0x4;
    int PAST_OR_PRESENT_EVENTS = (PAST_EVENTS | PRESENT_EVENTS);
    int FUTURE_OR_PRESENT_EVENTS = (PRESENT_EVENTS | FUTURE_EVENTS);
    int ALL_EVENTS = (PAST_OR_PRESENT_EVENTS | FUTURE_OR_PRESENT_EVENTS);

    /**
     * Retrieves a <code>Subscription</code> For the entitlementId
     *
     * @param entitlementId the id of the entitlement associated with the subscription
     * @param context       the context
     * @return the subscription
     * @throws SubscriptionApiException if it odes not exist
     */
    Subscription getSubscriptionForEntitlementId(UUID entitlementId, TenantContext context) throws SubscriptionApiException;

    /**
     * Retrieves all the <code>Subscription</code> attached to the base entitlement.
     *
     * @param bundleId the id of the bundle
     * @param context  the context
     * @return a list of subscriptions
     * @throws SubscriptionApiException if the baseEntitlementId does not exist.
     */
    public SubscriptionBundle getSubscriptionBundle(UUID bundleId, TenantContext context) throws SubscriptionApiException;

    /**
     * Update the externalKey for a given bundle
     *
     * @param bundleId       ; bundle id
     * @param newExternalKey : the new value for the externalKey
     * @param context        : the call context
     */
    @RequiresPermissions(ENTITLEMENT_CAN_CREATE)
    public void updateExternalKey(UUID bundleId, String newExternalKey, CallContext context) throws EntitlementApiException;

    /**
     * Retrieves all the <code>SubscriptionBundle</code> for a given account and matching an external key.
     *
     * @param accountId   the account id
     * @param externalKey the external key
     * @param context     the context
     * @return a <code>SubscriptionBundle</code>
     * @throws SubscriptionApiException if there is n o such object matching the account and external key
     */
    public List<SubscriptionBundle> getSubscriptionBundlesForAccountIdAndExternalKey(UUID accountId, String externalKey, TenantContext context) throws SubscriptionApiException;

    /**
     * Retrieves all the <code>SubscriptionBundle</code> for the given external key.
     * </p>
     * It is possible to have multiple <code>SubscriptionBundle</code> for a given external key in the system but only one
     * will be active -- i.e. will contain <code>Subscription</code> in the active state.
     *
     * @param externalKey the external key
     * @param context     the context
     * @return a <code>SubscriptionBundle</code>
     * @throws SubscriptionApiException if there is no such object
     */
    public SubscriptionBundle getActiveSubscriptionBundleForExternalKey(String externalKey, TenantContext context) throws SubscriptionApiException;

    /**
     * Returns an ordered list of all <code>SubscriptionBundle</code> for a given external key.
     *
     * @return
     */
    public List<SubscriptionBundle> getSubscriptionBundlesForExternalKey(String externalKey, TenantContext context) throws SubscriptionApiException;

    /**
     * Retrieves all the <code>SubscriptionBundle</code> for a given account.
     *
     * @param accountId the account id
     * @param context   the context
     * @return list of <code>SubscriptionBundle</code>
     * @throws SubscriptionApiException if the account does not exist
     */
    public List<SubscriptionBundle> getSubscriptionBundlesForAccountId(UUID accountId, TenantContext context) throws SubscriptionApiException;

    /**
     * @param context the user context
     * @param offset  the offset of the first result
     * @param limit   the maximum number of results to retrieve
     * @return the list of <code>SubscriptionBundle</code> for that tenant
     */
    public Pagination<SubscriptionBundle> getSubscriptionBundles(Long offset, Long limit, TenantContext context);

    /**
     * Find all <code>SubscriptionBundle</code> having their id, account id or external key matching the search key
     *
     * @param searchKey the search key
     * @param offset    the offset of the first result
     * @param limit     the maximum number of results to retrieve
     * @param context   the user context
     * @return the list of <code>SubscriptionBundle</code> matching this search key for that tenant
     */
    public Pagination<SubscriptionBundle> searchSubscriptionBundles(String searchKey, Long offset, Long limit, TenantContext context);

    /**
     * Add a <code>BlockingState</code>
     * <p/>
     * The date is interpreted by the system to be in the timezone specified at the <code>Account</code>
     *
     * @param blockingState the blockingState to be added
     * @param effectiveDate the date in the account time zone at which the operation should be effective, if null this is interpreted to be immediate
     * @param properties    plugin specific properties
     * @param context       the context
     * @throws EntitlementApiException if the entitlement was not in <tt>ACTIVE</tt> state
     */
    @RequiresPermissions(ENTITLEMENT_CAN_PAUSE_RESUME)
    public void addBlockingState(BlockingState blockingState, LocalDate effectiveDate, Iterable<PluginProperty> properties, CallContext context)
            throws EntitlementApiException;

    /**
     *
     * @param accountId     the account id
     * @param typeFilter    the list of <code>BlockingStateType</code> filters. All types are returned if null or empty.
     * @param svcsFilter    the list of service filters. All services are returned if null or empty.
     * @param orderingType  the ordering direction, that is <code>ASCENDING</code> or <code>DESCENDING</code>
     * @param timeFilter    the filtering types constructed as a bitwise operation
     * @param context       the user context
     *
     * @throws EntitlementApiException if the account is invalid.
     * @return the ordered list of  <code>BlockingState</code>
     */
    public Iterable<BlockingState> getBlockingStates(UUID accountId, List<BlockingStateType> typeFilter, List<String> svcsFilter, OrderingType orderingType, int timeFilter, TenantContext context)
            throws EntitlementApiException;

}
