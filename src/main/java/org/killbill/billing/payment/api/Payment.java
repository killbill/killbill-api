/*
 * Copyright 2014 Groupon, Inc
 *
 * Groupon licenses this file to you under the Apache License, version 2.0
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
import java.util.List;
import java.util.UUID;

import org.killbill.billing.catalog.api.Currency;
import org.killbill.billing.util.entity.Entity;

public interface Payment extends Entity {

    /**
     * @return the account id
     */
    UUID getAccountId();

    /**
     * @return the payment method id
     */
    UUID getPaymentMethodId();

    /**
     * @return the payment number
     */
    Integer getPaymentNumber();

    /**
     * @return the external key
     */
    String getExternalKey();

    /**
     * @return the authorized amount
     */
    BigDecimal getAuthAmount();

    /**
     * @return the captured amount
     */
    BigDecimal getCapturedAmount();

    /**
     * @return the purchased amount
     */
    BigDecimal getPurchasedAmount();

    /**
     * @return the credited amount
     */
    BigDecimal getCreditedAmount();

    /**
     * @return the refunded amount
     */
    BigDecimal getRefundedAmount();

    /**
     * @return true if there was a void operation following an authorization
     */
    Boolean isAuthVoided();

    /**
     * @return the currency associated with that payment
     */
    Currency getCurrency();

    /**
     * @return the list of attempts on that payment
     */
    List<PaymentTransaction> getTransactions();

    /**
     * @return the list of Payment attempts (past and future)
     */
    List<PaymentAttempt> getPaymentAttempts();
}
