/*
 * Copyright 2010-2013 Ning, Inc.
 * Copyright 2014 Groupon, Inc
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

package org.killbill.billing.payment.api;

import java.util.List;

/**
 * The retryOnFailure parameter will be passed to the plugin to allow or not for retry logic after the call has failed.
 */
public interface PaymentOptions {

    /**
     * Whether this payment occurred outside of the system and should only be recorded as being successful
     */
    public boolean isExternalPayment();

    /**
     * If the pluginName is null, payment code will bypass invocation of any payment retry logic.
     *
     * @return the name of the RetryPluginApi that payment should invoke or null
     */
    public List<String> getPaymentControlPluginNames();
}
