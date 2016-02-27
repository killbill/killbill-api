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
import org.killbill.billing.catalog.api.BillingActionPolicy;
import org.killbill.billing.catalog.api.PlanPhasePriceOverride;
import org.killbill.billing.catalog.api.PlanPhaseSpecifier;
import org.killbill.billing.payment.api.PluginProperty;
import org.killbill.billing.security.RequiresPermissions;
import org.killbill.billing.util.callcontext.CallContext;
import org.killbill.billing.util.callcontext.TenantContext;

import static org.killbill.billing.security.Permission.ENTITLEMENT_CAN_CREATE;
import static org.killbill.billing.security.Permission.ENTITLEMENT_CAN_TRANSFER;
import static org.killbill.billing.security.Permission.ENTITLEMENT_CAN_PAUSE_RESUME;

/**
 * Primary API to manage the creation and retrieval of <code>Entitlement</code>.
 */
public interface EntitlementApi extends KillbillApi {

    /**
     * Create a new entitlement for that account.
     * <p/>
     * The <code>PlanPhaseSpecifier<code/> should refer to a <code>ProductCategory.BASE</code> of
     * <code>ProductCategory.STANDALONE</code>.
     *
     * @param accountId     the account id
     * @param spec          the product specification for that new entitlement
     * @param externalKey   the external key for that entitlement-- which must be unique in the system
     * @param overrides     the price override for each phase and for a specific currency
     * @param entitlementEffectiveDate the date at which the entitlement should start. if this is null this is assumed now
     * @param billingEffectiveDate  the date at which the billing for the subscription should start. if this is null this is assumed now
     * @param isMigrated     whether this subscription comes from a different system (migrated into Kill Bill)
     * @param properties     plugin specific properties
     * @param context       the context
     * @return a new entitlement
     * @throws EntitlementApiException if the system fail to create the <code>Entitlement</code>.
     */
    @RequiresPermissions(ENTITLEMENT_CAN_CREATE)
    public Entitlement createBaseEntitlement(UUID accountId, PlanPhaseSpecifier spec, String externalKey, List<PlanPhasePriceOverride> overrides, LocalDate entitlementEffectiveDate,  LocalDate billingEffectiveDate, boolean isMigrated, Iterable<PluginProperty> properties, CallContext context)
            throws EntitlementApiException;

    /**
     * Create a new base entitlement and addOn entitlements for that account.
     * <p/>
     *
     * @param accountId     the account id
     * @param externalKey   the bundle external key
     * @param entitlementEffectiveDate the date at which the entitlement should start. if this is null this is assumed now
     * @param billingEffectiveDate  the date at which the billing for the subscription should start. if this is null this is assumed now
     * @param entitlementSpecifier     a list of entitlement specifier
     * @param isMigrated     whether this subscription comes from a different system (migrated into Kill Bill)
     * @param properties     plugin specific properties
     * @param context       the context
     * @return the common bundle created
     * @throws EntitlementApiException if the system fail to create the <code>Entitlement</code>.
     */
    @RequiresPermissions(ENTITLEMENT_CAN_CREATE)
    Entitlement createBaseEntitlementWithAddOns(UUID accountId, String externalKey, Iterable<EntitlementSpecifier> entitlementSpecifier, LocalDate entitlementEffectiveDate,  LocalDate billingEffectiveDate, boolean isMigrated, Iterable<PluginProperty> properties, CallContext context)
            throws EntitlementApiException;

    /**
     * Adds an ADD_ON entitlement to previously created entitlement.
     * <p/>
     * The <code>PlanPhaseSpecifier<code/> should refer to a <code>ProductCategory.ADD_ON</code>.
     * The new entitlement will be bundled using the externalKey that was specified when creating the
     * base entitlement.
     *
     * @param bundleId      the id of the bundle
     * @param spec          the product specification for that new entitlement
     * @param overrides     the price override for each phase and for a specific currency
     * @param entitlementEffectiveDate the date at which the entitlement should start. if this is null this is assumed now
     * @param billingEffectiveDate  the date at which the billing for the subscription should start. if this is null this is assumed now
     * @param isMigrated     whether this subscription comes from a different system (migrated into Kill Bill)
     * @param properties     plugin specific properties
     * @param context       the context
     * @return a new entitlement
     * @throws EntitlementApiException if the system fail to create the <code>Entitlement</code>
     */
    @RequiresPermissions(ENTITLEMENT_CAN_CREATE)
    public Entitlement addEntitlement(UUID bundleId, PlanPhaseSpecifier spec, List<PlanPhasePriceOverride> overrides, LocalDate entitlementEffectiveDate,  LocalDate billingEffectiveDate, boolean isMigrated, Iterable<PluginProperty> properties, CallContext context)
            throws EntitlementApiException;

    /**
     * Simulate a change of product for the BP on that bundle and return the effect it would have on the existing ADD_ON-- if any.
     *
     * @param bundleId          the id of the bundle
     * @param targetProductName the target product name for the BP
     * @param effectiveDate     the date at which the change would occur
     * @param context           the context
     * @return the status for the existing ADD_ON <code>Entitlement</code>
     * @throws EntitlementApiException if this operation is not carried on a base plan.
     */
    public List<EntitlementAOStatusDryRun> getDryRunStatusForChange(UUID bundleId, final String targetProductName, final LocalDate effectiveDate, final TenantContext context)
            throws EntitlementApiException;

    /**
     * Will pause all entitlements associated with the base entitlement. If there are no ADD_ONN this is only the base entitlement.
     *
     * @param bundleId
     * @param effectiveDate
     * @param properties     plugin specific properties
     * @param context
     * @throws EntitlementApiException if the system fail to find the base <code>Entitlement</code>
     */
    @RequiresPermissions(ENTITLEMENT_CAN_PAUSE_RESUME)
    public void pause(UUID bundleId, LocalDate effectiveDate, Iterable<PluginProperty> properties, CallContext context)
            throws EntitlementApiException;

    /**
     * Will resume all entitlements associated with the base entitlement. If there are no ADD_ONN this is only the base entitlement.
     *
     * @param bundleId
     * @param effectiveDate
     * @param properties     plugin specific properties
     * @param context
     * @throws EntitlementApiException if the system fail to find the base <code>Entitlement</code>
     */
    @RequiresPermissions(ENTITLEMENT_CAN_PAUSE_RESUME)
    public void resume(UUID bundleId, LocalDate effectiveDate, Iterable<PluginProperty> properties, CallContext context)
            throws EntitlementApiException;

    /**
     * Will add a blocking event for the specified service associated with the base entitlement.
     *
     * @param bundleId          the id of the bundle
     * @param stateName         the name of the state
     * @param serviceName       the name of the service
     * @param effectiveDate     the date at which the operation should occur
     * @param blockBilling      whether this event should block billing
     * @param blockEntitlement  whether this event should block entitlement
     * @param blockChange       whether this event should block any change
     * @param properties        plugin specific properties
     * @param context           the context
     *
     * @throws EntitlementApiException if the system fail to find the base <code>Entitlement</code>
     */
     @RequiresPermissions(ENTITLEMENT_CAN_PAUSE_RESUME)
     public void setBlockingState(UUID bundleId, String stateName, String serviceName, LocalDate effectiveDate, boolean blockBilling, boolean blockEntitlement, boolean blockChange, Iterable<PluginProperty> properties, CallContext context)
             throws EntitlementApiException;

    /**
     *
     * @param blockableId     the id of the blockable (account/bundle/subscription)
     * @param type            the type
     * @param serviceName     the name of the service
     * @param context         the context
     * @return
     */
    public Iterable<BlockingState> getBlockingStatesForServiceAndType(UUID blockableId, BlockingStateType type, String serviceName, TenantContext context);

    /**
     * Retrieves an <code>Entitlement</code> using its id.
     *
     * @param id      the id of the entitlement
     * @param context the context
     * @return the entitlement
     * @throws EntitlementApiException if the entitlement does not exist
     */
    public Entitlement getEntitlementForId(UUID id, TenantContext context) throws EntitlementApiException;

    /**
     * Retrieves all the <code>Entitlement</code> attached to the base entitlement.
     *
     * @param bundleId the id of the bundle
     * @param context  the context
     * @return a list of entitlements
     * @throws EntitlementApiException if the entitlement does not exist
     */
    public List<Entitlement> getAllEntitlementsForBundle(UUID bundleId, TenantContext context)
            throws EntitlementApiException;

    /**
     * Retrieves all the <code>Entitlement</code> for a given account and matching an external key.
     *
     * @param accountId   the account id
     * @param externalKey the external key
     * @param context     the context
     * @return a list of entitlements
     * @throws EntitlementApiException if the account does not exist
     */
    public List<Entitlement> getAllEntitlementsForAccountIdAndExternalKey(UUID accountId, String externalKey, TenantContext context)
            throws EntitlementApiException;

    /**
     * Retrieves all the <code>Entitlement</code> for a given account.
     *
     * @param accountId the account id
     * @param context   the context
     * @return a list of entitlements
     * @throws EntitlementApiException if the account does not exist
     */
    public List<Entitlement> getAllEntitlementsForAccountId(UUID accountId, TenantContext context) throws EntitlementApiException;

    /**
     * Transfer all the <code>Entitlement</code> For the source account and matching the external key to the destination account.
     * <p/>
     * The date is interpreted by the system to be in the timezone specified at the destination <code>Account</code>.
     * <p/>
     * The <code>Entitlement</code> on the source account will be cancelled at effective date and the <code>Entitlement</code>
     * on the destination account will be created at the effectiveDate.
     *
     * @param sourceAccountId the unique id for the account on which the bundle will be transferred For
     * @param destAccountId   the unique id for the account on which the bundle will be transferred to
     * @param externalKey     the externalKey for the bundle
     * @param effectiveDate   the date at which this transfer should occur
     * @param properties     plugin specific properties
     * @param context         the user context
     * @return the id of the newly created bundle for the destination account
     * @throws EntitlementApiException if the system could not transfer the entitlements
     */
    @RequiresPermissions(ENTITLEMENT_CAN_TRANSFER)
    public UUID transferEntitlements(final UUID sourceAccountId, final UUID destAccountId, final String externalKey, final LocalDate effectiveDate,
                                     Iterable<PluginProperty> properties, final CallContext context)
            throws EntitlementApiException;

    /**
     * Transfer all the <code>Entitlement</code> for the source account and matching the external key to the destination account.
     * <p/>
     * The date is interpreted by the system to be in the timezone specified at the destination <code>Account</code>.
     * <p/>
     * The <code>Entitlement</code> on the source account will be cancelled at effective date and the <code>Entitlement</code>
     * on the destination account will be created at the effectiveDate. The <tt>billingPolicy</tt> will be used to override
     * the default billing behavior for the cancellation of the subscriptions on the source account.
     *
     * @param sourceAccountId the unique id for the account on which the bundle will be transferred For
     * @param destAccountId   the unique id for the account on which the bundle will be transferred to
     * @param externalKey     the externalKey for the bundle
     * @param effectiveDate   the date at which this transfer should occur
     * @param billingPolicy   the override billing policy
     * @param properties     plugin specific properties
     * @param context         the user context
     * @return the id of the newly created base entitlement for the destination account
     * @throws EntitlementApiException if the system could not transfer the entitlements
     */
    @RequiresPermissions(ENTITLEMENT_CAN_TRANSFER)
    public UUID transferEntitlementsOverrideBillingPolicy(final UUID sourceAccountId, final UUID destAccountId, final String externalKey, final LocalDate effectiveDate,
                                                          final BillingActionPolicy billingPolicy, Iterable<PluginProperty> properties, final CallContext context)
            throws EntitlementApiException;

}
