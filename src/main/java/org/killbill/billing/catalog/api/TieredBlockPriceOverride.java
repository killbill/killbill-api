package org.killbill.billing.catalog.api;

public interface TieredBlockPriceOverride extends BlockPriceOverride {
    /**
     * @return the max used usage to consider this tiered block.
     */
    public Double getMax();
}
