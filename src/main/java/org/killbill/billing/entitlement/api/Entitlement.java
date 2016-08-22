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
 * It contains all the catalog information and current state that answers the entitlement question.
 * <p/>
 * The users of that API will control all the entitlement behavior when making changes such as the effectiveDate, catalog info,...
 * By default the system will use system wide policies to control the billing aspect, but specific APIs also allow to override those.
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
        CANCELLED
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
     * The date is interpreted by the system to be in the timezone specified at the <code>Account</code>
     *
     * @param effectiveDate                the date at which the entitlement should be cancelled
     * @param overrideBillingEffectiveDate use effectiveDate for billing cancellation date as well
     * @param properties                   plugin specific properties
     * @param context                      the context
     * @return the new <code>Entitlement</code> after the cancellation was performed
     * @throws EntitlementApiException if cancellation failed
     */
    @RequiresPermissions(ENTITLEMENT_CAN_CANCEL)
    public Entitlement cancelEntitlementWithDate(final LocalDate effectiveDate, final boolean overrideBillingEffectiveDate, final Iterable<PluginProperty> properties, final CallContext context)
            throws EntitlementApiException;

    /**
     * Cancel the <code>Entitlement</code> with a policy.
     * After this operation, the existing object becomes stale.
     *
     * @param policy     the policy that is used by the system to calculate the cancellation date
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
     * The date is interpreted by the system to be in the timezone specified at the <code>Account</code>
     *
     * @param effectiveDate the date at which the entitlement should be cancelled
     * @param billingPolicy the billingPolicy
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
     * The date is interpreted by the system to be in the timezone specified at the <code>Account</code>
     *
     * @param spec       the product specification for the change
     * @param overrides  the price override for each phase and for a specific currency
     * @param properties plugin specific properties
     * @param context    the context
     * @return the new <code>Entitlement</code> after the change was performed
     * @throws EntitlementApiException if change failed
     */
    @RequiresPermissions(ENTITLEMENT_CAN_CHANGE_PLAN)
    public Entitlement changePlan(final PlanSpecifier spec, final List<PlanPhasePriceOverride> overrides, final Iterable<PluginProperty> properties, final CallContext context)
            throws EntitlementApiException;

    /**
     * Change <code>Entitlement</code> plan at the specified date.
     * After this operation, the existing object becomes stale.
     * <p/>
     * The date is interpreted by the system to be in the timezone specified at the <code>Account</code>
     *
     * @param spec          the product specification for the change
     * @param overrides     the price override for each phase and for a specific currency
     * @param effectiveDate the date at which the entitlement should be changed
     * @param properties    plugin specific properties
     * @param context       the context
     * @return the new <code>Entitlement</code> after the change was performed
     * @throws EntitlementApiException if change failed
     */
    @RequiresPermissions(ENTITLEMENT_CAN_CHANGE_PLAN)
    public Entitlement changePlanWithDate(final PlanSpecifier spec, final List<PlanPhasePriceOverride> overrides, final LocalDate effectiveDate, final Iterable<PluginProperty> properties, final CallContext context)
            throws EntitlementApiException;

    /**
     * Change <code>Entitlement</code> plan at the specified date and overrides the billing policy.
     * After this operation, the existing object becomes stale.
     * <p/>
     * The date is interpreted by the system to be in the timezone specified at the <code>Account</code>
     *
     * @param spec          the product specification for the change
     * @param overrides     the price override for each phase and for a specific currency
     * @param effectiveDate the date at which the entitlement should be changed
     * @param billingPolicy the override billing policy
     * @param properties    plugin specific properties
     * @param context       the context
     * @return the new <code>Entitlement</code> after the change was performed
     * @throws EntitlementApiException if change failed
     */
    @RequiresPermissions(ENTITLEMENT_CAN_CHANGE_PLAN)
    public Entitlement changePlanOverrideBillingPolicy(final PlanSpecifier spec, final List<PlanPhasePriceOverride> overrides, final LocalDate effectiveDate,
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
