/*
 * Copyright 2016 The Billing Project, LLC
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

package org.killbill.billing.entitlement.api;

import java.util.UUID;

import org.joda.time.DateTime;

public interface BaseEntitlementWithAddOnsSpecifier {

    /**
     * @return the bundle Id
     */
    public UUID getBundleId();

    /**
     * @return the bundle external key
     */
    String getBundleExternalKey();

    /**
     * @return the list of entitlement specifiers
     */
    Iterable<EntitlementSpecifier> getEntitlementSpecifier();

    /**
     * @return the effective date of the entitlement
     */
    DateTime getEntitlementEffectiveDate();

    /**
     * @return the billing effective date
     */
    DateTime getBillingEffectiveDate();

    /**
     * @return whether the entitlement is migrated
     */
    boolean isMigrated();
}
