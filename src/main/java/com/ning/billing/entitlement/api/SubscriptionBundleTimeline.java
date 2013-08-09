/*
 * Copyright 2010-2013 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.ning.billing.entitlement.api;

import com.ning.billing.ObjectType;
import com.ning.billing.catalog.api.BillingPeriod;
import com.ning.billing.catalog.api.Plan;
import com.ning.billing.catalog.api.PlanPhase;
import com.ning.billing.catalog.api.PriceList;
import com.ning.billing.catalog.api.Product;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.UUID;

public interface SubscriptionBundleTimeline {

    public interface SubscriptionEvent {

        /**
         *
         * @return the unique id for the event
         */
        public UUID getId();

        /**
         * @return the id of the entitlement
         */
        public UUID getEntitlementId();

        /**
         * @return the date at which the transition took place
         */
        public LocalDate getEffectiveDate();

        /**
         * @return the type of transition
         */
        public SubscriptionEventType getEntitlementEventType();


        /**
         * @return whether the billing is blocked
         */
        public boolean isBlockedBilling();

        /**
         * @return whether the entitlement is blocked
         */
        public boolean isBlockedEntitlement();

        /**
         * @return the service that generated the event
         */
        public String getServiceName();

        /**
         * @return the state that was set by a given service for a particular transition
         */
        public String getServiceStateName();

        /**
         * @return the previous product after that transition took place
         */
        public Product getPrevProduct();

        /**
         * @return the previous plan after that transition took place
         */
        public Plan getPrevPlan();

        /**
         * @return the previous phase after that transition took place
         */
        public PlanPhase getPrevPhase();

        /**
         * @return the previous pricelist after that transition took place
         */
        public PriceList getPrevPriceList();

        /**
         * @return the previous billing period name after that transition took place
         */
        public BillingPeriod getPrevBillingPeriod();

        /**
         * @return the next product after that transition took place
         */
        public Product getNextProduct();

        /**
         * @return the next plan after that transition took place
         */
        public Plan getNextPlan();

        /**
         * @return the next phase after that transition took place
         */
        public PlanPhase getNextPhase();

        /**
         * @return the next pricelist after that transition took place
         */
        public PriceList getNextPriceList();

        /**
         * @return the next billing period name after that transition took place
         */
        public BillingPeriod getNextBillingPeriod();

    }

    public enum SubscriptionEventType {
        /* Initial events */
        CREATE(ObjectType.SUBSCRIPTION_EVENT),
        TRANSFER(ObjectType.SUBSCRIPTION_EVENT),
        MIGRATE(ObjectType.SUBSCRIPTION_EVENT),
        /* Phase transition */
        PHASE(ObjectType.SUBSCRIPTION_EVENT),
        /* User generated change plan */
        CHANGE(ObjectType.SUBSCRIPTION_EVENT),
        /* Transition state change for a given service */
        SERVICE_STATE_CHANGE(ObjectType.BLOCKING_STATES),
        /* User generated cancel */
        CANCEL(ObjectType.SUBSCRIPTION_EVENT);

        private ObjectType objectType;

        SubscriptionEventType(ObjectType type) {
            this.objectType = type;
        }

        public ObjectType getObjectType() {
            return objectType;
        }
    }

    /**
     * @return the account id
     */
    public UUID getAccountId();

    /**
     * @return the id of the base entitlement
     */
    public UUID getBaseEntitlementId();

    /**
     * @return the external key
     */
    public String getExternalKey();

    /**
     * @return the ordered list of transitions that occurred across all the <code>Subscription</code>
     */
    public List<SubscriptionEvent> getSubscriptionEvents();
}
