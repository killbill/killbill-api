package org.killbill.billing.catalog.api;

import java.util.List;

public interface TierPriceOverride {

    /**
     * @return the {@code Block Override} for that {@code Tier Override} section.
     */
    public List<TieredBlockPriceOverride> getTieredBlockPriceOverrides();
}
