package com.ning.billing.entitlement.api;

import com.ning.billing.catalog.api.BillingPeriod;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.UUID;

/**
 * The <code>EntitlementBundleTimeline</code> shows all the transitions that occured
 * for a group of <code>Entitlement</code> attached to a given account and identified by
 * its external key.
 */
public interface EntitlementBundleTimeline {

    public interface EntitlementEvent {

        /**
         *
         * @return the id of the entitlement
         */
        public UUID getEntitlementId();

        /**
         *
         * @return the date at which the transition took place
         */
        public LocalDate getEffectiveDate();

        /**
         *
         * @return the type of transition
         */
        public EntitlementEventType getEntitlementEventType();

        /**
         *
         * @return the product name after that transition took place
         */
        public String getProductName();

        /**
         *
         * @return the plan name after that transition took place
         */
        public String getPlanName();

        /**
         *
         * @return the phase name after that transition took place
         */
        public String getPhaseName();

        /**
         *
         * @return the pricelist name after that transition took place
         */
        public String getPriceListName();

        /**
         *
         * @return the billing period name after that transition took place
         */
        public BillingPeriod getBillingPeriod();
    }

    public enum EntitlementEventType {
        /* Initial events */
        CREATE,
        TRANSFER,
        MIGRATE,
        /* Phase transition */
        PHASE,
        /* User generated change plan */
        CHANGE,
        /* User generated pause/resume */
        PAUSE,
        RESUME,
        /* System generated block/unblock */
        BLOCK,
        UNBLOCK,
        /* User generated cancel */
        CANCEL
    }

    /**
     *
     * @return the account id
     */
    public UUID getAccountId();

    /**
     *
     * @return the id of the base entitlement
     */
    public UUID getBaseEntitlementId();

    /**
     *
     * @return the external key
     */
    public String getExternalKey();

    /**
     *
     * @return the ordered list of transitions that occurred across all the <code>Entitlement</code>
     */
    public List<EntitlementEvent> getEntitlementEvents();
}
