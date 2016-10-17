package org.killbill.billing.catalog.api;

import java.math.BigDecimal;

/**
 * Created by sruthipendyala on 10/17/16.
 */
public interface BlockPriceOverride {

    /**
     * @return the unit for that {@code Block} section.
     */
    public String getUnitName();

    /**
     * @return the size of the block
     */
    public Double getSize();

    /**
     * @return the recurring {@code InternationalPrice} for that {@code Block} section.
     */
    public BigDecimal getPrice();

}
