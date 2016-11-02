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

package org.killbill.billing.catalog.api;


/**
 * The class {@code PlanSpecifier} specifies the attributes of a {@code Plan}
 */
public class PlanSpecifier {

    // User can either specify the unique planName or a mix of {productName, billingPeriod, priceListName}
    private final String planName;

    private final String productName;
    private final BillingPeriod billingPeriod;
    private final String priceListName;

    public PlanSpecifier(final String productName,
                         final BillingPeriod billingPeriod,
                         final String priceListName) {
        super();
        this.planName = null;
        this.productName = productName;
        this.billingPeriod = billingPeriod;
        this.priceListName = priceListName;
    }

    public PlanSpecifier(final String planName) {
        super();
        this.planName = planName;
        this.productName = null;
        this.billingPeriod = null;
        this.priceListName = null;
    }

    public PlanSpecifier(final PlanPhaseSpecifier planPhase) {
        super();
        this.planName = planPhase.getPlanName();
        this.productName = planPhase.getProductName();
        this.billingPeriod = planPhase.getBillingPeriod();
        this.priceListName = planPhase.getPriceListName();
    }

    /**
     *
     * @return the name of the Plan
     */
    public String getPlanName() {
        return planName;
    }


    /**
     * @return the name of the product
     */
    public String getProductName() {
        return productName;
    }


    /**
     * @return the {@code BillingPeriod}
     */
    public BillingPeriod getBillingPeriod() {
        return billingPeriod;
    }

    /**
     * @return the name of the {@code PriceList}
     */
    public String getPriceListName() {
        return priceListName;
    }
}
