/*
 * Copyright 2014-2018 Groupon, Inc
 * Copyright 2014-2018 The Billing Project, LLC
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
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;
import org.killbill.billing.KillbillApi;
import org.killbill.billing.account.api.Account;
import org.killbill.billing.catalog.api.Currency;
import org.killbill.billing.invoice.api.InvoicePayment;
import org.killbill.billing.security.RequiresPermissions;
import org.killbill.billing.util.callcontext.CallContext;
import org.killbill.billing.util.callcontext.TenantContext;

import static org.killbill.billing.security.Permission.PAYMENT_CAN_CHARGEBACK;
import static org.killbill.billing.security.Permission.PAYMENT_CAN_REFUND;
import static org.killbill.billing.security.Permission.PAYMENT_CAN_TRIGGER_PAYMENT;

public interface InvoicePaymentApi extends KillbillApi {

    /**
     * Combine an authorize and capture payment for a given invoice and allow to go through registered routing plugins
     *
     * @param account                       the account
     * @param invoiceId                     the invoice id
     * @param paymentMethodId               the payment method id to use
     * @param paymentId                     the payment id (non-null for multi-steps flows, such as 3D Secure)
     * @param amount                        the amount to pay
     * @param currency                      the amount currency
     * @param effectiveDate                 the effectiveDate of the payment operation
     * @param paymentExternalKey            the payment external key
     * @param paymentTransactionExternalKey the payment transaction external key
     * @param properties                    plugin specific properties
     * @param paymentOptions                options to control payment behavior
     * @param context                       the call context
     * @return the invoice payment
     * @throws PaymentApiException
     */
    @RequiresPermissions(PAYMENT_CAN_TRIGGER_PAYMENT)
    public InvoicePayment createPurchaseForInvoicePayment(Account account,
                                                          UUID invoiceId,
                                                          UUID paymentMethodId,
                                                          UUID paymentId,
                                                          BigDecimal amount,
                                                          Currency currency,
                                                          DateTime effectiveDate,
                                                          String paymentExternalKey,
                                                          String paymentTransactionExternalKey,
                                                          Iterable<PluginProperty> properties,
                                                          PaymentOptions paymentOptions,
                                                          CallContext context) throws PaymentApiException;

    /**
     * Refund a previously captured payment and allow to go through registered routing  plugins
     *
     * @param isAdjusted                    whether to adjust the invoice
     * @param adjustments                   individual adjustments per invoice item id
     * @param account                       the account
     * @param paymentId                     the payment id
     * @param effectiveDate                 the effectiveDate of the payment operation
     * @param amount                        the amount to refund
     * @param currency                      the amount currency
     * @param paymentTransactionExternalKey the payment transaction external key
     * @param properties                    plugin specific properties
     * @param paymentOptions                options to control payment behavior
     * @param context                       the call context
     * @return the payment
     * @throws PaymentApiException
     */
    @RequiresPermissions(PAYMENT_CAN_REFUND)
    public InvoicePayment createRefundForInvoicePayment(boolean isAdjusted,
                                                        Map<UUID, BigDecimal> adjustments,
                                                        Account account,
                                                        UUID paymentId,
                                                        BigDecimal amount,
                                                        Currency currency,
                                                        DateTime effectiveDate,
                                                        String paymentTransactionExternalKey,
                                                        Iterable<PluginProperty> properties,
                                                        PaymentOptions paymentOptions,
                                                        CallContext context) throws PaymentApiException;

    /**
     * Credit a payment method for a given invoice and allow to go through registered routing  plugins
     * <p/>
     * This is also known as payment in reverse.
     *
     * @param isAdjusted                    whether to adjust the invoice
     * @param adjustments                   individual adjustments per invoice item id
     * @param account                       the account
     * @param originalPaymentId             the original payment id
     * @param paymentMethodId               the payment method id to use
     * @param paymentId                     the payment id (non-null for multi-steps flows)
     * @param amount                        the amount to credit
     * @param currency                      the amount currency
     * @param effectiveDate                 the effectiveDate of the payment operation
     * @param paymentExternalKey            the payment external key
     * @param paymentTransactionExternalKey the payment transaction external key
     * @param properties                    plugin specific properties
     * @param paymentOptions                options to control payment behavior
     * @param context                       the call context
     * @return the payment
     * @throws PaymentApiException
     */
    @RequiresPermissions(PAYMENT_CAN_TRIGGER_PAYMENT)
    public InvoicePayment createCreditForInvoicePayment(boolean isAdjusted,
                                                        Map<UUID, BigDecimal> adjustments,
                                                        Account account,
                                                        UUID originalPaymentId,
                                                        UUID paymentMethodId,
                                                        UUID paymentId,
                                                        BigDecimal amount,
                                                        Currency currency,
                                                        DateTime effectiveDate,
                                                        String paymentExternalKey,
                                                        String paymentTransactionExternalKey,
                                                        Iterable<PluginProperty> properties,
                                                        PaymentOptions paymentOptions,
                                                        CallContext context) throws PaymentApiException;

    /**
     * Record a chargeback and allow to go through registered routing plugins
     *
     * @param account                       the account
     * @param paymentId                     the payment id
     * @param effectiveDate                 the effectiveDate of the payment operation
     * @param amount                        the amount to refund
     * @param currency                      the amount currency
     * @param paymentTransactionExternalKey the payment transaction external key
     * @param properties                    plugin specific properties
     * @param paymentOptions                options to control payment behavior
     * @param context                       the call context
     * @return the payment
     * @throws PaymentApiException
     */
    @RequiresPermissions(PAYMENT_CAN_CHARGEBACK)
    public InvoicePayment createChargebackForInvoicePayment(Account account,
                                                            UUID paymentId,
                                                            BigDecimal amount,
                                                            Currency currency,
                                                            DateTime effectiveDate,
                                                            String paymentTransactionExternalKey,
                                                            Iterable<PluginProperty> properties,
                                                            PaymentOptions paymentOptions,
                                                            CallContext context) throws PaymentApiException;

    /**
     * Reverse a chargeback and allow to go through registered routing plugins
     *
     * @param account                       the account
     * @param paymentId                     the payment id
     * @param effectiveDate                 the effectiveDate of the payment operation
     * @param paymentTransactionExternalKey the payment transaction external key
     * @param properties                    plugin specific properties
     * @param paymentOptions                options to control payment behavior
     * @param context                       the call context
     * @return the payment
     * @throws PaymentApiException
     */
    @RequiresPermissions(PAYMENT_CAN_CHARGEBACK)
    public InvoicePayment createChargebackReversalForInvoicePayment(Account account,
                                                                    UUID paymentId,
                                                                    DateTime effectiveDate,
                                                                    String paymentTransactionExternalKey,
                                                                    Iterable<PluginProperty> properties,
                                                                    PaymentOptions paymentOptions,
                                                                    CallContext context) throws PaymentApiException;

    public List<InvoicePayment> getInvoicePayments(UUID paymentId, TenantContext context);

    public List<InvoicePayment> getInvoicePaymentsByAccount(UUID accountId, TenantContext context);
}
