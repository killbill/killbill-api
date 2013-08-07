package com.ning.billing.entitlement.api;

import org.joda.time.LocalDate;

import java.util.UUID;

/**
 * An Subscription is created using the <code>SubscriptionApi</code>
 * <p/>
 * It contains its <code>Entitlement</code> and additional billing related information.
 *
 * @see com.ning.billing.entitlement.api.SubscriptionApi
 */
public interface Subscription {

    public enum BillingState {
        /* The subscription is currently invoiced */
        ACTIVE,
        /* The subscription is not currently invoiced because its entitlement has been paused or blocked */
        INACTIVE,
        /* The subscription is not invoiced */
        CANCELLED
    }


    /**
     * @return the unique id of the subscription
     */
    public UUID getId();

    /**
     * @return the <code>Entitlement</code> associated with that <code>Subscription</code>
     */
    public Entitlement getEntitlement();


    /**
     * @return the date at which the billing started for that subscription
     */
    public LocalDate getBillingStartDate();

    /**
     * @return the date at which the billing stopped for that subscription
     */
    public LocalDate getBillingEndDate();

    /**
     * @return the date up to which that <code>Subscription got invoiced</code>
     */
    public LocalDate getChargedThroughDate();

    /**
     * @return the day of the month when that subscription is invoiced.
     */
    public int getBCD();

}
