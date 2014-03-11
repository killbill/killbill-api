package org.killbill.billing.catalog.api;

public interface Fixed {

    /**
     *
     * @return the {@code FixedType}
     */
    public FixedType getType();

    /**
     *
     * @return the fixed {@code InternationalPrice} for that {@code Fixed} section.
     */
    public InternationalPrice getPrice();
}
