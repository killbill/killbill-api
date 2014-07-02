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

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.killbill.billing.account.api.Account;
import org.killbill.billing.catalog.api.Currency;
import org.killbill.billing.security.RequiresPermissions;
import org.killbill.billing.util.callcontext.CallContext;
import org.killbill.billing.util.callcontext.TenantContext;
import org.killbill.billing.util.entity.Pagination;

import static org.killbill.billing.security.Permission.PAYMENT_CAN_CHARGEBACK;
import static org.killbill.billing.security.Permission.PAYMENT_CAN_REFUND;
import static org.killbill.billing.security.Permission.PAYMENT_CAN_TRIGGER_PAYMENT;

public interface DirectPaymentApi {

    /**
     * Authorize a direct payment.
     *
     * @param account                             the account
     * @param paymentMethodId                     the payment method id to use
     * @param directPaymentId                     the direct payment id (non-null for multi-steps flows, such as 3D Secure)
     * @param amount                              the amount to pay
     * @param currency                            the amount currency
     * @param directPaymentExternalKey            the direct payment external key
     * @param directPaymentTransactionExternalKey the direct payment transaction external key
     * @param properties                          plugin specific properties
     * @param context                             the call context
     * @return the payment
     * @throws PaymentApiException
     */
    @RequiresPermissions(PAYMENT_CAN_TRIGGER_PAYMENT)
    public DirectPayment createAuthorization(Account account, UUID paymentMethodId, UUID directPaymentId, BigDecimal amount, Currency currency,
                                             String directPaymentExternalKey, String directPaymentTransactionExternalKey,
                                             Iterable<PluginProperty> properties, CallContext context)
            throws PaymentApiException;

    /**
     * Capture a previously authorized direct payment.
     *
     * @param account                             the account
     * @param amount                              the amount to pay
     * @param currency                            the amount currency
     * @param directPaymentTransactionExternalKey the direct payment transaction external key
     * @param properties                          plugin specific properties
     * @param context                             the call context
     * @return the payment
     * @throws PaymentApiException
     */
    @RequiresPermissions(PAYMENT_CAN_TRIGGER_PAYMENT)
    public DirectPayment createCapture(Account account, UUID directPaymentId, BigDecimal amount, Currency currency,
                                       String directPaymentTransactionExternalKey, Iterable<PluginProperty> properties,
                                       CallContext context)
            throws PaymentApiException;

    /**
     * Combine an authorize and capture direct payment.
     *
     * @param account                             the account
     * @param paymentMethodId                     the payment method id to use
     * @param directPaymentId                     the direct payment id (non-null for multi-steps flows, such as 3D Secure)
     * @param amount                              the amount to pay
     * @param currency                            the amount currency
     * @param directPaymentExternalKey            the direct payment external key
     * @param directPaymentTransactionExternalKey the direct payment transaction external key
     * @param properties                          plugin specific properties
     * @param context                             the call context
     * @return the payment
     * @throws PaymentApiException
     */
    @RequiresPermissions(PAYMENT_CAN_TRIGGER_PAYMENT)
    public DirectPayment createPurchase(Account account, UUID paymentMethodId, UUID directPaymentId, BigDecimal amount, Currency currency,
                                        String directPaymentExternalKey, String directPaymentTransactionExternalKey,
                                        Iterable<PluginProperty> properties, CallContext context)
            throws PaymentApiException;

    /**
     * Combine an authorize and capture direct payment.
     *
     * @param account                             the account
     * @param paymentMethodId                     the payment method id to use
     * @param directPaymentId                     the direct payment id (non-null for multi-steps flows, such as 3D Secure)
     * @param amount                              the amount to pay
     * @param currency                            the amount currency
     * @param directPaymentExternalKey            the direct payment external key
     * @param directPaymentTransactionExternalKey the direct payment transaction external key
     * @param properties                          plugin specific properties
     * @param paymentOptions                      options to control payment behavior
     * @param context                             the call context
     * @return the payment
     * @throws PaymentApiException
     */
    @RequiresPermissions(PAYMENT_CAN_TRIGGER_PAYMENT)
    public DirectPayment createPurchaseWithPaymentControl(Account account, UUID paymentMethodId, UUID directPaymentId, BigDecimal amount, Currency currency,
                                                          String directPaymentExternalKey, String directPaymentTransactionExternalKey,
                                                          Iterable<PluginProperty> properties, PaymentOptions paymentOptions, CallContext context)
            throws PaymentApiException;

    /**
     * Void a previously authorized payment.
     *
     * @param account                             the account
     * @param directPaymentId                     the direct payment id
     * @param directPaymentTransactionExternalKey the direct payment transaction external key
     * @param properties                          plugin specific properties
     * @param context                             the call context
     * @return the payment
     * @throws PaymentApiException
     */
    @RequiresPermissions(PAYMENT_CAN_TRIGGER_PAYMENT)
    public DirectPayment createVoid(Account account, UUID directPaymentId, String directPaymentTransactionExternalKey, Iterable<PluginProperty> properties,
                                    CallContext context)
            throws PaymentApiException;

    /**
     * Refund a previously captured payment.
     *
     * @param account                             the account
     * @param directPaymentId                     the direct payment id
     * @param amount                              the amount to refund
     * @param currency                            the amount currency
     * @param directPaymentTransactionExternalKey the direct payment transaction external key
     * @param properties                          plugin specific properties
     * @param context                             the call context
     * @return the payment
     * @throws PaymentApiException
     */
    @RequiresPermissions(PAYMENT_CAN_REFUND)
    public DirectPayment createRefund(Account account, UUID directPaymentId, BigDecimal amount, Currency currency,
                                      String directPaymentTransactionExternalKey, Iterable<PluginProperty> properties,
                                      CallContext context)
            throws PaymentApiException;

    /**
     * Refund a previously captured payment.
     *
     * @param account                             the account
     * @param directPaymentId                     the direct payment id
     * @param amount                              the amount to refund
     * @param currency                            the amount currency
     * @param directPaymentTransactionExternalKey the direct payment transaction external key
     * @param properties                          plugin specific properties
     * @param paymentOptions                      options to control payment behavior
     * @param context                             the call context
     * @return the payment
     * @throws PaymentApiException
     */
    @RequiresPermissions(PAYMENT_CAN_REFUND)
    public DirectPayment createRefundWithPaymentControl(Account account, UUID directPaymentId, BigDecimal amount, Currency currency,
                                                        String directPaymentTransactionExternalKey, Iterable<PluginProperty> properties,
                                                        PaymentOptions paymentOptions, CallContext context)
            throws PaymentApiException;

    /**
     * Credit a payment method.
     * <p/>
     * This is also known as payment in reverse.
     *
     * @param account                             the account
     * @param paymentMethodId                     the payment method id to use
     * @param directPaymentId                     the direct payment id (non-null for multi-steps flows)
     * @param amount                              the amount to credit
     * @param currency                            the amount currency
     * @param directPaymentExternalKey            the direct payment external key
     * @param directPaymentTransactionExternalKey the direct payment transaction external key
     * @param properties                          plugin specific properties
     * @param context                             the call context
     * @return the payment
     * @throws PaymentApiException
     */
    @RequiresPermissions(PAYMENT_CAN_TRIGGER_PAYMENT)
    public DirectPayment createCredit(Account account, UUID paymentMethodId, UUID directPaymentId, BigDecimal amount, Currency currency,
                                      String directPaymentExternalKey, String directPaymentTransactionExternalKey,
                                      Iterable<PluginProperty> properties, CallContext context)
            throws PaymentApiException;

    /**
     * Transition a currently PENDING transaction into either a SUCCESS or a FAILURE
     *
     * @param account                    the account
     * @param directPaymentTransactionId the direct transaction id
     * @param isSuccess                  whether the transaction is successful or not
     * @param context                    the call context
     * @throws PaymentApiException
     */
    public void notifyPendingTransactionOfStateChanged(Account account, UUID directPaymentTransactionId, boolean isSuccess, CallContext context) throws PaymentApiException;


    /**
     * Transition a currently PENDING transaction into either a SUCCESS or a FAILURE
     *
     * @param account                    the account
     * @param directPaymentTransactionId the direct transaction id
     * @param isSuccess                  whether the transaction is successful or not
     * @param paymentOptions             options to control payment behavior
     * @param context                    the call context
     * @throws PaymentApiException
     */
    public void notifyPendingTransactionOfStateChangedWithPaymentControl(Account account, UUID directPaymentTransactionId, boolean isSuccess, PaymentOptions paymentOptions, CallContext context) throws PaymentApiException;


    /**
     * Record a chargeback
     *
     * @param account                          the account
     * @param directPaymentTransactionId       the direct transaction id
     * @param chargebackTransactionExternalKey the chargeback external key
     * @param amount                           the amount to chargeback
     * @param currency                         the amount currency
     * @param context                          the call context
     * @return the chargeback transactionId
     * @throws PaymentApiException
     */
    @RequiresPermissions(PAYMENT_CAN_CHARGEBACK)
    public DirectPayment notifyChargeback(Account account, UUID directPaymentTransactionId, String chargebackTransactionExternalKey, BigDecimal amount, Currency currency, CallContext context) throws PaymentApiException;



    /**
     * Record a chargeback
     *
     * @param account                          the account
     * @param directPaymentTransactionId       the direct transaction id
     * @param chargebackTransactionExternalKey the chargeback external key
     * @param amount                           the amount to chargeback
     * @param currency                         the amount currency
     * @param paymentOptions                   options to control payment behavior
     * @param context                          the call context
     * @return the chargeback transactionId
     * @throws PaymentApiException
     */
    @RequiresPermissions(PAYMENT_CAN_CHARGEBACK)
    public DirectPayment notifyChargebackWithPaymentControl(Account account, UUID directPaymentTransactionId, String chargebackTransactionExternalKey, BigDecimal amount, Currency currency, final PaymentOptions paymentOptions, CallContext context) throws PaymentApiException;

    /**
     * @param accountId      the account id
     * @param withPluginInfo whether to fetch plugin info
     * @param properties     plugin specific properties
     * @param context        the call context
     * @return the list of direct payments on this account
     * @throws PaymentApiException
     */
    public List<DirectPayment> getAccountPayments(UUID accountId, boolean withPluginInfo, Iterable<PluginProperty> properties, TenantContext context)
            throws PaymentApiException;

    /**
     * @param directPaymentId the direct payment id
     * @param withPluginInfo  whether to fetch plugin info
     * @param properties      plugin specific properties
     * @param context         the call context
     * @return the payment
     * @throws PaymentApiException
     */
    public DirectPayment getPayment(UUID directPaymentId, boolean withPluginInfo, Iterable<PluginProperty> properties, TenantContext context)
            throws PaymentApiException;

    /**
     * @param paymentExternalKey the payment external key
     * @param withPluginInfo     whether to fetch plugin info
     * @param properties         plugin specific properties
     * @param context            the call context
     * @return the payment
     * @throws PaymentApiException
     */
    public DirectPayment getPaymentByExternalKey(String paymentExternalKey, boolean withPluginInfo, Iterable<PluginProperty> properties, TenantContext context)
            throws PaymentApiException;

    /**
     * Find all payments across all plugins
     *
     * @param offset         the offset of the first result
     * @param limit          the maximum number of results to retrieve
     * @param withPluginInfo whether to fetch plugin info
     * @param properties     plugin specific properties
     * @param context        the user context
     * @return the list of payments for that tenant
     */
    public Pagination<DirectPayment> getPayments(Long offset, Long limit, boolean withPluginInfo, Iterable<PluginProperty> properties, TenantContext context);

    /**
     * Find all payments in a given plugin
     *
     * @param offset         the offset of the first result
     * @param limit          the maximum number of results to retrieve
     * @param pluginName     the payment plugin name
     * @param withPluginInfo whether to fetch plugin info
     * @param properties     plugin specific properties
     * @param context        the user context
     * @return the list of payments for that tenant
     * @throws PaymentApiException
     */
    public Pagination<DirectPayment> getPayments(Long offset, Long limit, String pluginName, boolean withPluginInfo, Iterable<PluginProperty> properties, TenantContext context) throws PaymentApiException;

    /**
     * Find all payments matching the search key across all plugins
     * <p/>
     * The match will be plugin specific: for instance some plugins will try to match the key
     * against the transaction ids, etc.
     *
     * @param searchKey      the search key
     * @param offset         the offset of the first result
     * @param limit          the maximum number of results to retrieve
     * @param withPluginInfo whether to fetch plugin info
     * @param properties     plugin specific properties
     * @param context        the user context
     * @return the list of payments matching this search key for that tenant
     */
    public Pagination<DirectPayment> searchPayments(String searchKey, Long offset, Long limit, boolean withPluginInfo, Iterable<PluginProperty> properties, TenantContext context);

    /**
     * Find all payments matching the search key in a given plugin
     * <p/>
     * The match will be plugin specific: for instance some plugins will try to match the key
     * against the transaction ids, etc.
     *
     * @param searchKey      the search key
     * @param offset         the offset of the first result
     * @param limit          the maximum number of results to retrieve
     * @param pluginName     the payment plugin name
     * @param withPluginInfo whether to fetch plugin info
     * @param properties     plugin specific properties
     * @param context        the user context
     * @return the list of payments matching this search key for that tenant
     * @throws PaymentApiException
     */
    public Pagination<DirectPayment> searchPayments(String searchKey, Long offset, Long limit, String pluginName, boolean withPluginInfo, Iterable<PluginProperty> properties, TenantContext context) throws PaymentApiException;

    /**
     * @param paymentMethodExternalKey the externl key
     * @param account                  the account
     * @param pluginName               the plugin name
     * @param setDefault               whether this should be set as a default payment method
     * @param paymentMethodInfo        the details for the payment method
     * @param properties               plugin specific properties
     * @param context                  the call context
     * @return the uuid of the payment method
     * @throws PaymentApiException
     */
    public UUID addPaymentMethod(String paymentMethodExternalKey, Account account, String pluginName, boolean setDefault, PaymentMethodPlugin paymentMethodInfo, Iterable<PluginProperty> properties, CallContext context)
            throws PaymentApiException;

    /**
     * @param accountId      the account id
     * @param withPluginInfo whether we want to retrieve the plugin info for that payment method
     * @param properties     plugin specific properties
     * @param context        the call context
     * @return the list of payment methods
     * @throws PaymentApiException
     */
    public List<PaymentMethod> getAccountPaymentMethods(UUID accountId, boolean withPluginInfo, Iterable<PluginProperty> properties, TenantContext context)
            throws PaymentApiException;

    /**
     * @param paymentMethodId  the payment method id
     * @param includedInactive returns the payment method even if this is not active
     * @param withPluginInfo   whether we want to retrieve the plugin info for that payment method
     * @param properties       plugin specific properties
     * @param context          the call context   @return the payment method
     * @throws PaymentApiException
     */
    public PaymentMethod getPaymentMethodById(UUID paymentMethodId, boolean includedInactive, boolean withPluginInfo, Iterable<PluginProperty> properties, TenantContext context)
            throws PaymentApiException;

    /**
     *
     * @param paymentMethodExternalKey the payment method external key
     * @param includedInactive returns the payment method even if this is not active
     * @param withPluginInfo   whether we want to retrieve the plugin info for that payment method
     * @param properties       plugin specific properties
     * @param context          the call context   @return the payment method
     * @throws PaymentApiException
     */
    public PaymentMethod getPaymentMethodByExternalKey(String paymentMethodExternalKey, boolean includedInactive, boolean withPluginInfo, Iterable<PluginProperty> properties, TenantContext context)
            throws PaymentApiException;

    /**
     * Find all payment methods across all plugins
     *
     * @param offset         the offset of the first result
     * @param limit          the maximum number of results to retrieve
     * @param withPluginInfo whether to fetch plugin info
     * @param properties     plugin specific properties
     * @param context        the user context
     * @return the list of payment methods for that tenant
     */
    public Pagination<PaymentMethod> getPaymentMethods(Long offset, Long limit, boolean withPluginInfo, Iterable<PluginProperty> properties, TenantContext context);

    /**
     * Find all payment methods in a given plugin
     *
     * @param offset         the offset of the first result
     * @param limit          the maximum number of results to retrieve
     * @param pluginName     the payment plugin name
     * @param withPluginInfo whether to fetch plugin info
     * @param properties     plugin specific properties
     * @param context        the user context
     * @return the list of payment methods for that tenant
     * @throws PaymentApiException
     */
    public Pagination<PaymentMethod> getPaymentMethods(Long offset, Long limit, String pluginName, boolean withPluginInfo, Iterable<PluginProperty> properties, TenantContext context) throws PaymentApiException;

    /**
     * Find all payment methods matching the search key across all plugins
     * <p/>
     * The match will be plugin specific: for instance some plugins will try to match the key
     * against the last 4 credit cards digits, agreement ids, etc.
     *
     * @param searchKey      the search key
     * @param offset         the offset of the first result
     * @param limit          the maximum number of results to retrieve
     * @param withPluginInfo whether to fetch plugin info
     * @param properties     plugin specific properties
     * @param context        the user context
     * @return the list of payment methods matching this search key for that tenant
     */
    public Pagination<PaymentMethod> searchPaymentMethods(String searchKey, Long offset, Long limit, boolean withPluginInfo, Iterable<PluginProperty> properties, TenantContext context);

    /**
     * Find all payment methods matching the search key in a given plugin
     * <p/>
     * The match will be plugin specific: for instance some plugins will try to match the key
     * against the last 4 credit cards digits, agreement ids, etc.
     *
     * @param searchKey      the search key
     * @param offset         the offset of the first result
     * @param limit          the maximum number of results to retrieve
     * @param pluginName     the payment plugin name
     * @param withPluginInfo whether to fetch plugin info
     * @param properties     plugin specific properties
     * @param context        the user context
     * @return the list of payment methods matching this search key for that tenant
     * @throws PaymentApiException
     */
    public Pagination<PaymentMethod> searchPaymentMethods(String searchKey, Long offset, Long limit, String pluginName, boolean withPluginInfo, Iterable<PluginProperty> properties, TenantContext context) throws PaymentApiException;

    /**
     * @param account                                  the account
     * @param paymentMethodId                          the id of the payment  method
     * @param deleteDefaultPaymentMethodWithAutoPayOff whether to allow deletion of default payment method and set account into AUTO_PAY_OFF
     * @param properties                               plugin specific properties
     * @param context                                  the call context
     * @throws PaymentApiException
     */
    public void deletePaymentMethod(Account account, UUID paymentMethodId, boolean deleteDefaultPaymentMethodWithAutoPayOff, Iterable<PluginProperty> properties, CallContext context)
            throws PaymentApiException;

    /**
     * @param account         the account
     * @param paymentMethodId the payment method id
     * @param properties      plugin specific properties
     * @param context         the call context
     * @throws PaymentApiException
     */
    public void setDefaultPaymentMethod(Account account, UUID paymentMethodId, Iterable<PluginProperty> properties, CallContext context)
            throws PaymentApiException;

    /**
     * @param account    the account
     * @param pluginName the name of the plugin
     * @param properties plugin specific properties
     * @param context    the call context
     * @return the list of payment methods for that account
     * @throws PaymentApiException
     */
    public List<PaymentMethod> refreshPaymentMethods(Account account, String pluginName, Iterable<PluginProperty> properties, CallContext context)
            throws PaymentApiException;

    /**
     * Refresh all payment methods across all plugins
     * <p/>
     * This call is not atomic.
     *
     * @param account    the account
     * @param properties plugin specific properties
     * @param context    the call context
     * @return the list of payment methods for that account
     * @throws PaymentApiException
     */
    public List<PaymentMethod> refreshPaymentMethods(Account account, Iterable<PluginProperty> properties, CallContext context)
            throws PaymentApiException;
}
