package com.ning.billing.entitlement.api;

import com.ning.billing.catalog.api.ActionPolicy;
import com.ning.billing.catalog.api.BillingPeriod;
import com.ning.billing.util.callcontext.CallContext;
import org.joda.time.LocalDate;

import java.util.UUID;

public interface Entitlement {


    public enum EntitlementActionPolicy {
        SOT,
        EOD
    }

    public UUID getId();

    // Cancel entitlement at the specified date and let subscription/catalog/rules do the right thing for billing
    public boolean cancelEntitlementWithDate(final LocalDate effectiveDate, final CallContext context)
            throws EntitlementApiException;

    // Cancel entitlement at a date that matches SOT/EOT (billing)
    public boolean cancelEntitlementWithPolicy(final EntitlementActionPolicy policy, final CallContext context)
            throws EntitlementApiException;

    // Cancel entitlement at the specified date and override subscription/catalog/rules
    public boolean cancel(final LocalDate effectiveDate, final ActionPolicy billingPolicy, final CallContext context)
            throws EntitlementApiException;

    // Cancel entitlement at a date that matches SOT/EOT (billing) and override subscription/catalog/rules
    public boolean cancelEntitlementWithDateOverrideBillingPolicy(final EntitlementActionPolicy policy, final ActionPolicy billingPolicy, final CallContext context)
            throws EntitlementApiException;


    // Uncancel entitlement (only works if entitlement is maked as canceled in the future)
    public boolean uncancel(final CallContext context)
            throws EntitlementApiException;


    public boolean changePlan(final String productName, final BillingPeriod term, final String priceList, final LocalDate requestedDate, final CallContext context)
            throws EntitlementApiException;

    public boolean changePlanOverrideBillingPolicy(final String productName, final BillingPeriod term, final String priceList, final LocalDate requestedDate,
                                        final ActionPolicy policy, final CallContext context)
            throws EntitlementApiException;


    public boolean pause(final LocalDate effectiveDate, final CallContext context)
            throws EntitlementApiException;

    public boolean resume(final LocalDate effectiveDate, final CallContext context)
            throws EntitlementApiException;
}
