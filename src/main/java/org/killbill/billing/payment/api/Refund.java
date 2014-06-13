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

package org.killbill.billing.payment.api;

import java.math.BigDecimal;
import java.util.UUID;

import org.joda.time.DateTime;
import org.killbill.billing.catalog.api.Currency;
import org.killbill.billing.payment.plugin.api.PaymentTransactionInfoPlugin;
import org.killbill.billing.util.entity.Entity;

public interface Refund extends Entity {

    /**
     * @return the payment id
     */
    public UUID getPaymentId();

    /**
     * @return whether the refund did trigger an invoice or invoice item adjustment
     */
    public boolean isAdjusted();

    /**
     * @return the refund amount
     */
    public BigDecimal getRefundAmount();

    /**
     * @return the currency associated with that refund
     */
    public Currency getCurrency();

    /**
     * Date of the refund
     *
     * @return the effective date of the refund
     */
    public DateTime getEffectiveDate();

    /**
     * @return the refund status
     */
    public RefundStatus getRefundStatus();

    /**
     * This will only be filled when the call requires the details from the plugin
     *
     * @return the additional info from the plugin
     */
    public PaymentTransactionInfoPlugin getRefundInfoPlugin();
}
