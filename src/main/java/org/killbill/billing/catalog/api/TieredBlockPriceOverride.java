package org.killbill.billing.catalog.api;

/**
 * Created by sruthipendyala on 10/17/16.
 */

public interface TieredBlockPriceOverride extends BlockPriceOverride {
    /**
     * @return the max used usage to consider this tiered block.
     */
    public Double getMax();
}
