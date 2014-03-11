package org.killbill.billing.catalog.api;

public interface Usage {

    /**
     *
     * @return  @return the {@code BillingPeriod}
     */
    public BillingPeriod getBillingPeriod();

    /**
     * @return compliance boolean
     */
    public boolean compliesWithLimits(String unit, double value);

}
