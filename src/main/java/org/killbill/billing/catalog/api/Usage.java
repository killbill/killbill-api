/*
 * Copyright 2014 The Billing Project, LLC
 *
 * The Billing Project licenses this file to you under the Apache License, version 2.0
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

public interface Usage extends CatalogEntity {


    /**
     * @return the {@code BillingMode}
     */
    public BillingMode getBillingMode();

    /**
     * @return the {@code UsageType}
     */
    public UsageType getUsageType();

    /**
     * @return @return the {@code BillingPeriod}
     */
    public BillingPeriod getBillingPeriod();

    /**
     * @return compliance boolean
     */
    public boolean compliesWithLimits(String unit, double value);

    /**
     * @return the {@code Limit} associated with that usage section
     */
    public Limit[] getLimits();

    /**
     * @return the {@code Tier} associated with that usage section
     */
    public Tier[] getTiers();

    /**
     * @return the {@code Block} associated with that usage section
     */
    public Block[] getBlocks();

    /**
     * @return the fixed {@code InternationalPrice} for that {@code Usage} section.
     */
    public InternationalPrice getFixedPrice();

    /**
     * @return the recurring {@code InternationalPrice} for that {@code Usage} section.
     */
    public InternationalPrice getRecurringPrice();
}
