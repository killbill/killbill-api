package org.killbill.billing.catalog.api;

public interface Usage {

    /**
     *
     * @return the {@code BillingMode}
     */
    public BillingMode getBillingMode();

    /**
     *
     * @return the {@code UsageType}
     */
    public UsageType getUsageType();

    /**
     *
     * @return  @return the {@code BillingPeriod}
     */
    public BillingPeriod getBillingPeriod();

    /**
     * @return compliance boolean
     */
    public boolean compliesWithLimits(String unit, double value);

    /**
     *
     * @return the {@code Limit} associated with that usage section
     */
    public Limit[] getLimits();


    /**
     *
     * @return the {@code Tier} associated with that usage section
     */
    public Tier[] getTiers();


    /**
     *
     * @return the {@code Block} associated with that usage section
     */
    public Block[] getBlocks();


    /**
     *
     * @return the fixed {@code InternationalPrice} for that {@code Usage} section.
     */
    public InternationalPrice getFixedPrice();

    /**
     *
     * @return the recurring {@code InternationalPrice} for that {@code Usage} section.
     */
    public InternationalPrice getRecurringPrice();
}
