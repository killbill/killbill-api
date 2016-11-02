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
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.killbill.billing.catalog.api.Currency;
import org.killbill.billing.util.entity.Entity;

public interface PaymentAttempt extends Entity {

    /**
     * @return the id of the account.
     */
    UUID getAccountId();

    /**
     * @return the id of the payment method.
     */
    UUID getPaymentMethodId();

    /**
     * @return the external key of the payment.
     */
    String getPaymentExternalKey();

    /**
     * @return the id of the transaction.
     */
    UUID getTransactionId();

    /**
     * @return the external key of the transaction.
     */
    String getTransactionExternalKey();

    /**
     * @return the type of the transaction.
     */
    TransactionType getTransactionType();

    /**
     * @return the effective date.
     */
    DateTime getEffectiveDate();

    /**
     * @return the name of the state.
     */
    String getStateName();

    /**
     * @return the amount.
     */
    BigDecimal getAmount();

    /**
     * @return the currency.
     */
    Currency getCurrency();

    /**
     * @return the name of the plugin.
     */
    String getPluginName();

    /**
     * @return the properties of the plugin.
     */
    List<PluginProperty> getPluginProperties();


}
