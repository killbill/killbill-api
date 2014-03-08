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

package org.killbill.billing.payment.plugin.api;

import java.math.BigDecimal;
import java.util.UUID;

import org.joda.time.DateTime;

import org.killbill.billing.catalog.api.Currency;

public interface PaymentInfoPlugin {

    /**
     * @return the id in Kill Bill
     */
    public UUID getKbPaymentId();

    /**
     * @return payment amount
     */
    public BigDecimal getAmount();

    /**
     * @return payment currency
     */
    public Currency getCurrency();

    /**
     * @return date when the payment was created
     */
    public DateTime getCreatedDate();

    /**
     * @return date when the payment is effective
     */
    public DateTime getEffectiveDate();

    /**
     * @return payment status in the gateway
     */
    public PaymentPluginStatus getStatus();

    /**
     * @return gateway error, if any
     */
    public String getGatewayError();

    /**
     * @return gateway error code, if any
     */
    public String getGatewayErrorCode();

    /**
     * @return the first payment reference id
     */
    public String getFirstPaymentReferenceId();

    /**
     * @return the second payment reference id
     */
    public String getSecondPaymentReferenceId();
}
