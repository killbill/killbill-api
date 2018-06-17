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
import org.killbill.billing.catalog.api.BillingActionPolicy;
import org.killbill.billing.catalog.api.Plan;
import org.killbill.billing.catalog.api.PlanPhase;
import org.killbill.billing.catalog.api.PlanPhasePriceOverride;
import org.killbill.billing.catalog.api.PlanPhaseSpecifier;
import org.killbill.billing.catalog.api.PlanSpecifier;
import org.killbill.billing.catalog.api.PriceList;
import org.killbill.billing.catalog.api.Product;
import org.killbill.billing.catalog.api.ProductCategory;
import org.killbill.billing.payment.api.PluginProperty;
import org.killbill.billing.security.RequiresPermissions;
import org.killbill.billing.util.callcontext.CallContext;
import org.killbill.billing.util.entity.Entity;

import static org.killbill.billing.security.Permission.ENTITLEMENT_CAN_CANCEL;
import static org.killbill.billing.security.Permission.ENTITLEMENT_CAN_CHANGE_PLAN;
import static org.killbill.billing.security.Permission.ENTITLEMENT_CAN_CREATE;

/**
 * An Entitlement is created using the <code>EntitlementApi</code>
 * <p/>
 * It contains apis to return entitlement state (i.e information about whether the user can access the service purchased through the subscription), and also
 * apis to modify the state of the subscription (upgrade, downgrade, cancellation).
 * <p/>
 *
 * The apis <code>cancelEntitlement</code> and <code>changePlan</code> are relying on either dates and/or policies to achieve correct result.
 *
 * The dates provided are <code>LocalDate</code> and they are interpreted by the system using the the account timezone:
 *
 * Example: Let's assume for instance a point in time set to 2017-04-24:00.00.01.000Z, and a user that makes a <code>changePlan</code> api call by
 * specifying an effective date of 2017-04-24 for an account with a UTC-8 TZ. In the account timezone, the clock shows 2017-04-23:16.00.01.000(-8), and
 * so the change will be effective in the future, any time during the 24 hours period that starts at 2017-04-24:00.00.00.000(-8), with no specific guarantee
 * about the time.
 *
 * In order to ensure that the api call happens immediately (as the call returns), one can pass a null effective date.
 *
 * The policies provide an easy way to achieve desired results without having to do any date computation. However, when using the <code>IMMEDIATE</code> policy,
 * it is interesting to notice that the policy will be converted into an effective date and so the same principle discussed earlier with interpreting the date into
 * the account timezone will occur, resulting in the operation to not necessarily happen immediately (as the call returns) but instead within the 24 hours window.
 * The result will be correct in the sense that the service will move to the new Plan on the right day, and if necessary billing pro-ration will show the correct amount.
 *
 * In the case of <code>PENDING</code> subscriptions, that is for which the (billing and/or entitlement) effective start date are in the future, providing an <code>IMMEDIATE</code>
 * policy or a null effective date will default to the effective start date of the subscription.
 *
 * <p/>
 *
 * @see org.killbill.billing.entitlement.api.EntitlementApi
 */
public interface Entitlement extends Entity {

    /**
     * Used to control the effective date that should be used on Plan change or on cancellation.
     */
    public enum EntitlementActionPolicy {
        /* Immediate */
        IMMEDIATE,
        /* End of Term */
        END_OF_TERM
    }

    /**
     * Possible states of an <code>Entitlement</code>
     */
    public enum EntitlementState {
        /* The entitlement was created in the future */
        PENDING,
        /* The entitlement was created in that initial state */
        ACTIVE,
        /* The system blocked the entitlement */
        BLOCKED,
        /* The user cancelled the entitlement */
        CANCELLED,
        /* The entitlement expired (FIXEDTERM use case) */
        EXPIRED
    }

    /**
     * Possible <code>Entitlement</code> source types.
     */
    public enum EntitlementSourceType {
        NATIVE,
        MIGRATED,
        TRANSFERRED
    }

    /**
     * @return the unique id of the base entitlement
     */
    public UUID getBaseEntitlementId();

    /**
     * @return the unique Id of the SubscriptionBundle
     */
    public UUID getBundleId();

    /**
     * @return the account id
     */
    public UUID getAccountId();

    /**
     * @return the external key that was supplied when creating the base entitlement
     */
    public String getExternalKey();

    /**
     * @return the state of the entitlement
     */
    public EntitlementState getState();

    /**
     * @return the entitlement source type
     */
    public EntitlementSourceType getSourceType();

    /**
     * @return the start date of the entitlement
     */
    public LocalDate getEffectiveStartDate();

    /**
     * @return the end date of the entitlement, that is the date at which it got cancelled.
     */
    public LocalDate getEffectiveEndDate();

    /**
     * @return the last <code>Product</code> prior to cancellation
     */
    public Product getLastActiveProduct();

    /**
     * @return the last <code>Plan</code> prior to cancellation
     */
    public Plan getLastActivePlan();

    /**
     * @return the last <code>Phase</code> prior to cancellation
     */
    public PlanPhase getLastActivePhase();

    /**
     * @return the last active PriceList
     */
    public PriceList getLastActivePriceList();

    /**
     * @return the last active ProductCategory
     */
    public ProductCategory getLastActiveProductCategory();

    /**
     * The billCycleDay should be interpreted in the account timezone.
     * The billCycleDay is used to determine when to bill a specific subscription
     * <p/>
     *
     * @return the billCycleDay for that subscription
     */
    public Integer getBillCycleDayLocal();

    /**
     * Cancels the <code>Entitlement</code> at the specified date.
     * After this operation, the existing object becomes stale.
     * <p/>
     * <p/>
     *
     * @param effectiveDate                the date at which the entitlement should be cancelled
     * @param overrideBillingEffectiveDate use effectiveDate for billing cancellation date as well, instead of relying on default catalog policies.
     * @param properties                   plugin specific properties
     * @param context                      the context
     * @return the new <code>Entitlement</code> after the cancellation was performed
     * @throws EntitlementApiException if cancellation failed
     *
     * The date is interpreted by the system to be in the timezone specified at the <code>Account</code>
     *
     *
     */
    @RequiresPermissions(ENTITLEMENT_CAN_CANCEL)
    public Entitlement cancelEntitlementWithDate(final LocalDate effectiveDate, final boolean overrideBillingEffectiveDate, final Iterable<PluginProperty> properties, final CallContext context)
            throws EntitlementApiException;

    /**
     * Cancel the <code>Entitlement</code> with a policy.
     * After this operation, the existing object becomes stale.
     *
     * The billing effective date will be computed from the default catalog policies.
     *
     * @param policy     the policy that is used by the system to calculate the entitlement cancellation date
     * @param properties plugin specific properties
     * @param context    the context
     * @return the new <code>Entitlement</code> after the cancellation was performed
     * @throws EntitlementApiException if cancellation failed
     */
    @RequiresPermissions(ENTITLEMENT_CAN_CANCEL)
    public Entitlement cancelEntitlementWithPolicy(final EntitlementActionPolicy policy, final Iterable<PluginProperty> properties, final CallContext context)
            throws EntitlementApiException;

    /**
     * Cancels the <code>Entitlement</code> at the specified date
     * After this operation, the existing object becomes stale.
     * <p/>
     *
     *
     * @param effectiveDate the date at which the entitlement should be cancelled
     * @param billingPolicy the billingPolicy that should be use to compute the billing cancellation date
     * @param properties    plugin specific properties
     * @param context       the context
     * @return the new <code>Entitlement</code> after the cancellation was performed
     * @throws EntitlementApiException if cancellation failed
     */
    @RequiresPermissions(ENTITLEMENT_CAN_CANCEL)
    public Entitlement cancelEntitlementWithDateOverrideBillingPolicy(final LocalDate effectiveDate, final BillingActionPolicy billingPolicy, final Iterable<PluginProperty> properties, final CallContext context)
            throws EntitlementApiException;

    /**
     * Cancels the <code>Entitlement</code> at the specified date and overrides the default billing policy.
     * After this operation, the existing object becomes stale.
     *
     * @param policy        the policy that is used by the system to calculate the cancellation date
     * @param billingPolicy the override billing policy
     * @param properties    plugin specific properties
     * @param context       the context
     * @return the new <code>Entitlement</code> after the cancellation was performed
     * @throws EntitlementApiException if cancellation failed
     */
    @RequiresPermissions(ENTITLEMENT_CAN_CANCEL)
    public Entitlement cancelEntitlementWithPolicyOverrideBillingPolicy(final EntitlementActionPolicy policy, final BillingActionPolicy billingPolicy, final Iterable<PluginProperty> properties, final CallContext context)
            throws EntitlementApiException;

    /**
     * Removes a pending future cancellation on an entitlement.
     *
     * <p/>
     * The call will only succeed if the entitlement has been cancelled previously and if the effectiveDate of the cancellation
     * did not occur yet. In such a case it will remove both the cancellation event at the entitlement and billing level-- regardless
     * of when is the effectiveDate of the billing cancellation event.
     *
     * @param properties plugin specific properties
     * @param context    the context
     */
    @RequiresPermissions(ENTITLEMENT_CAN_CANCEL)
    public void uncancelEntitlement(final Iterable<PluginProperty> properties, final CallContext context)
            throws EntitlementApiException;

    /**
     * Change <code>Entitlement</code> plan using default policy.
     * After this operation, the existing object becomes stale.
     * <p/>
     *
     *
     * @param spec       the product specification for the change
     * @param overrides  the price override for each phase and for a specific currency
     * @param properties plugin specific properties
     * @param context    the context
     * @return the new <code>Entitlement</code> after the change was performed
     * @throws EntitlementApiException if change failed
     */
    @RequiresPermissions(ENTITLEMENT_CAN_CHANGE_PLAN)
    public Entitlement changePlan(final PlanPhaseSpecifier spec, final List<PlanPhasePriceOverride> overrides, final Iterable<PluginProperty> properties, final CallContext context)
            throws EntitlementApiException;

    /**
     *
     * @param properties plugin specific properties
     * @param context    the context
     * @throws EntitlementApiException
     */
    @RequiresPermissions(ENTITLEMENT_CAN_CHANGE_PLAN)
    public void undoChangePlan(final Iterable<PluginProperty> properties, final CallContext context)
            throws EntitlementApiException;

    /**
     * Change <code>Entitlement</code> plan at the specified date.
     * After this operation, the existing object becomes stale.
     * <p/>
     *
     *
     * @param spec          the product specification for the change
     * @param overrides     the price override for each phase and for a specific currency
     * @param effectiveDate the effective date at which the entitlement and billing should be changed
     * @param properties    plugin specific properties
     * @param context       the context
     * @return the new <code>Entitlement</code> after the change was performed
     * @throws EntitlementApiException if change failed
     */
    @RequiresPermissions(ENTITLEMENT_CAN_CHANGE_PLAN)
    public Entitlement changePlanWithDate(final PlanPhaseSpecifier spec, final List<PlanPhasePriceOverride> overrides, final LocalDate effectiveDate, final Iterable<PluginProperty> properties, final CallContext context)
            throws EntitlementApiException;

    /**
     * Change <code>Entitlement</code> plan at the specified date and overrides the billing policy.
     * After this operation, the existing object becomes stale.
     * <p/>
     * The date is interpreted by the system to be in the timezone specified at the <code>Account</code>
     *
     * @param spec          the product specification for the change
     * @param overrides     the price override for each phase and for a specific currency
     * @param effectiveDate unused (reserved for future use)
     * @param billingPolicy the overriden billing policy that will determine effective date for the changePlan operation.
     * @param properties    plugin specific properties
     * @param context       the context
     * @return the new <code>Entitlement</code> after the change was performed
     * @throws EntitlementApiException if change failed
     */
    @RequiresPermissions(ENTITLEMENT_CAN_CHANGE_PLAN)
    public Entitlement changePlanOverrideBillingPolicy(final PlanPhaseSpecifier spec, final List<PlanPhasePriceOverride> overrides, final LocalDate effectiveDate,
                                                       final BillingActionPolicy billingPolicy, final Iterable<PluginProperty> properties, final CallContext context)
            throws EntitlementApiException;

    /**
     * @param bcd               the new BCD for that subscription
     * @param effectiveFromDate date after which that BCD change becomes active
     * @param context           the context
     * @throws EntitlementApiException
     */
    @RequiresPermissions(ENTITLEMENT_CAN_CREATE)
    public void updateBCD(final int bcd, final LocalDate effectiveFromDate, final CallContext context) throws EntitlementApiException;

}
