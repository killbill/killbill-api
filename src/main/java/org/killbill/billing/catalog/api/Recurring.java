package org.killbill.billing.catalog.api;

public interface Recurring {

    /**
     *
     * @return  @return the {@code BillingPeriod}
     */
    public BillingPeriod getBillingPeriod();

    /**
     *
     * @return the recurring {@code InternationalPrice} for that {@code Recurring} section.
     */
    public InternationalPrice getRecurringPrice();
}
