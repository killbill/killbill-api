/*
 * Copyright 2010-2013 Ning, Inc.
 * Copyright 2015 Groupon, Inc
 * Copyright 2015 The Billing Project, LLC
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

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * An Subscription is created using the <code>SubscriptionApi</code>
 * <p/>
 * It contains its <code>Entitlement</code> and additional billing related information.
 *
 * @see org.killbill.billing.entitlement.api.SubscriptionApi
 */
public interface Subscription extends Entitlement {

    /**
     * @return the date at which the billing started for that subscription
     */
    public DateTime getBillingStartDate();

    /**
     * @return the date at which the billing stopped for that subscription
     */
    public DateTime getBillingEndDate();

    /**
     * @return the date up to which that <code>Subscription got invoiced</code>
     */
    public LocalDate getChargedThroughDate();


    /**
     * @return the ordered list of transitions that occurred for that subscription
     */
    public List<SubscriptionEvent> getSubscriptionEvents();
}
