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

package org.killbill.billing.payment.api;

import java.math.BigDecimal;
import java.util.UUID;

import org.joda.time.DateTime;
import org.killbill.billing.catalog.api.Currency;
import org.killbill.billing.util.entity.Entity;

public interface PaymentAttempt extends Entity {

    /**
     * @return the id of the payment.
     */
    UUID getPaymentId();

    /**
     * @return the type of transaction
     */
    TransactionType getTransactionType();

    /**
     * @return the amount
     */
    BigDecimal getAmount();

    /**
     * @return the currency associated with that payment
     */
    Currency getCurrency();

    /**
     * @return the status for that transaction
     */
    TransactionStatus getTransactionStatus();

    /**
     * @return the effective date of the payment
     */
    DateTime getEffectiveDate();
}
