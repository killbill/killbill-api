package com.ning.billing.entitlement.api;

import com.ning.billing.catalog.api.ActionPolicy;
import com.ning.billing.catalog.api.BillingPeriod;
import com.ning.billing.catalog.api.Plan;
import com.ning.billing.catalog.api.PlanPhase;
import com.ning.billing.catalog.api.PriceList;
import com.ning.billing.catalog.api.Product;
import com.ning.billing.catalog.api.ProductCategory;
import com.ning.billing.util.callcontext.CallContext;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.UUID;

/**
 * An Entitlement is created using the <code>EntitlementApi</code>
 * <p/>
 * It contains all the catalog information and current state that answers the entitlement question.
 * <p>
 * The users of that API will control all the entitlement behavior when making changes such as the effectiveDate, catalog info,...
 * By default the system will use system wide policies to control the billing aspect, but specific APIs also allow to override those.
 * <p>
 * @see com.ning.billing.entitlement.api.EntitlementApi
 */
public interface Entitlement {

    /**
     * Used to control the effective date that should be used on Plan change or on cancellation.
     */
    public enum EntitlementActionPolicy {
        /* Start of Term. */
        SOT,
        /* End of Term */
        EOT
    }

    /**
     * Possible states of an <code>Entitlement</code>
     */
    public enum EntitlementState {
        /* The entitlement was created in that initial state */
        ACTIVE,
        /* The user explicitely paused the entitlement */
        PAUSED,
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
     * @return the unique id of the entitlement
     */
    public UUID getId();

    /**
     *
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
     * @return the requested end date of the entitlement, that is the date at which the user submitted the cancellation.
     */
    public LocalDate getRequestedEndDate();

    /**
     * @return the current <code>Product</code>
     */
    public Product getProduct();

    /**
     * @return the current <code>Plan</code>
     */
    public Plan getPlan();

    /**
     * @return the current <code>PriceList></code>
     */
    public PriceList getPriceList();

    /**
     * @return the current <Phase></Phase>
     */
    public PlanPhase getCurrentPhase();

    /**
     * @return the current <code>ProductCategory</code>
     */
    public ProductCategory getProductCategory();

    /**
     * @return the last <code>Product</code> prior to cancellation
     */
    public Product getLastActiveProduct();

    /**
     * @return the last <code>Plan</code> prior to cancellation
     */
    public Plan getLastActivePlan();

    /**
     * @return the last active PriceList
     */
    public PriceList getLastActivePriceList();

    /**
     * @return the last active ProductCategory
     */
    public String getLastActiveProductCategory();


    /**
     * Cancels the <code>Entitlement</code> at the specified date
     * <p/>
     * The date is interpreted by the system to be in the timezone specified at the <code>Account</code>
     *
     * @param effectiveDate the date at which the entitlement should be cancelled
     * @param context       the context
     * @return true if the entitlement is in the <tt>CANCELLED</tt> state
     * @throws EntitlementApiException if cancellation failed
     */
    public boolean cancelEntitlementWithDate(final LocalDate effectiveDate, final CallContext context)
            throws EntitlementApiException;


    /**
     * Cancel the <code>Entitlement</code> with a policy.
     *
     * @param policy  the policy that is used by the system to calculate the cancellation date
     * @param context the context
     * @return true if the entitlement is in the <tt>CANCELLED</tt> state
     * @throws EntitlementApiException if cancellation failed
     */
    public boolean cancelEntitlementWithPolicy(final EntitlementActionPolicy policy, final CallContext context)
            throws EntitlementApiException;


    /**
     * Cancels the <code>Entitlement</code> at the specified date
     * <p/>
     * The date is interpreted by the system to be in the timezone specified at the <code>Account</code>
     *
     * @param effectiveDate the date at which the entitlement should be cancelled
     * @param billingPolicy the billingPolicy
     * @param context       the context
     * @return true if the entitlement is in the <tt>CANCELLED</tt> state
     * @throws EntitlementApiException if cancellation failed
     */
    public boolean cancelEntitlementWithDateOverrideBillingPolicy(final LocalDate effectiveDate, final ActionPolicy billingPolicy, final CallContext context)
            throws EntitlementApiException;


    /**
     * Cancels the <code>Entitlement</code> at the specified date and overrides the default billing policy.
     *
     * @param policy        the policy that is used by the system to calculate the cancellation date
     * @param billingPolicy the override billing policy
     * @param context       the context
     * @return true if the entitlement is in the <tt>CANCELLED</tt> state
     * @throws EntitlementApiException if cancellation failed
     */
    public boolean cancelEntitlementWithPolicyOverrideBillingPolicy(final EntitlementActionPolicy policy, final ActionPolicy billingPolicy, final CallContext context)
            throws EntitlementApiException;


    /**
     * Change <code>Entitlement</code> plan at the specified date.
     * <p/>
     * The date is interpreted by the system to be in the timezone specified at the <code>Account</code>
     *
     * @param productName   the new product name
     * @param billingPeriod the new billing period
     * @param priceList     the new priceList
     * @param effectiveDate the date at which the entitlement should be changed
     * @param context       the context
     * @return true if the change has already taken effect
     * @throws EntitlementApiException if change failed
     */
    public boolean changePlan(final String productName, final BillingPeriod billingPeriod, final String priceList, final LocalDate effectiveDate, final CallContext context)
            throws EntitlementApiException;

    /**
     * Change <code>Entitlement</code> plan at the specified date and overrides the billing policy.
     * <p/>
     * The date is interpreted by the system to be in the timezone specified at the <code>Account</code>
     *
     * @param productName   the new product name
     * @param billingPeriod the new billing period
     * @param priceList     the new priceList
     * @param effectiveDate the date at which the entitlement should be changed
     * @param billingPolicy the override billing policy
     * @param context       the context
     * @return true if the change has already taken effect
     * @throws EntitlementApiException if change failed
     */
    public boolean changePlanOverrideBillingPolicy(final String productName, final BillingPeriod billingPeriod, final String priceList, final LocalDate effectiveDate,
                                                   final ActionPolicy billingPolicy, final CallContext context)
            throws EntitlementApiException;


    /**
     * Pauses an <code>Entitlement</code> until it gets resumed.
     * <p/>
     * The date is interpreted by the system to be in the timezone specified at the <code>Account</code>
     *
     * @param effectiveDate the date at which the entitlement should be paused
     * @param context       the context
     * @return true if the entitlement has been paused
     * @throws EntitlementApiException if the entitlement was not in <tt>ACTIVE</tt> state
     */
    public boolean pause(final LocalDate effectiveDate, final CallContext context)
            throws EntitlementApiException;

    /**
     * Resumes an <code>Entitlement</code> that was paused
     * <p/>
     * The date is interpreted by the system to be in the timezone specified at the <code>Account</code>
     *
     * @param effectiveDate the date at which the entitlement should be resumed
     * @param context       the context
     * @return true if the entitlement has been resumed
     * @throws EntitlementApiException
     */
    public boolean resume(final LocalDate effectiveDate, final CallContext context)
            throws EntitlementApiException;
}
