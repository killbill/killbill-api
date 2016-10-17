package org.killbill.billing.catalog.api;

import java.util.List;

/**
 * Created by sruthipendyala on 10/17/16.
 */
public interface UsagePriceOverride {

    /**
     * @return the name of the usage section
     */
    public String getName();

    /**
     * @return the {@code UsageType}
     */
    public UsageType getUsageType();

    /**
     * @return the {@code Tier Override} associated with that usage section
     */
    public List<TierPriceOverride> getTierPriceOverrides();

}
