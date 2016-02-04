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

package org.killbill.billing.entitlement.api;

import java.util.UUID;

import org.joda.time.LocalDate;
import org.killbill.billing.catalog.api.BillingPeriod;
import org.killbill.billing.catalog.api.Plan;
import org.killbill.billing.catalog.api.PlanPhase;
import org.killbill.billing.catalog.api.PriceList;
import org.killbill.billing.catalog.api.Product;

public interface SubscriptionEvent {

    /**
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
    public SubscriptionEventType getSubscriptionEventType();

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
