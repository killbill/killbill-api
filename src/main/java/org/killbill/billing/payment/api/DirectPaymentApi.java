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

import static org.killbill.billing.security.Permission.PAYMENT_CAN_TRIGGER_PAYMENT;

public interface DirectPaymentApi {

    /**
     * Authorize a direct payment.
     *
     * @param account         the account
     * @param directPaymentId the direct payment id (non-null for multi-steps flows, such as 3D Secure)
     * @param amount          the amount to pay
     * @param currency        the amount currency
     * @param properties      plugin specific properties
     * @param context         the call context
     * @return the payment
     * @throws PaymentApiException
     */
    @RequiresPermissions(PAYMENT_CAN_TRIGGER_PAYMENT)
    public DirectPayment createAuthorization(Account account, UUID directPaymentId, BigDecimal amount, Currency currency, String externalKey, Iterable<PluginProperty> properties, CallContext context)
            throws PaymentApiException;

    /**
     * Capture a previously authorized direct payment.
     *
     * @param account    the account
     * @param amount     the amount to pay
     * @param currency   the amount currency
     * @param properties plugin specific properties
     * @param context    the call context
     * @return the payment
     * @throws PaymentApiException
     */
    @RequiresPermissions(PAYMENT_CAN_TRIGGER_PAYMENT)
    public DirectPayment createCapture(Account account, UUID directPaymentId, BigDecimal amount, Currency currency, Iterable<PluginProperty> properties, CallContext context)
            throws PaymentApiException;

    /**
     * Combine an authorize and capture direct payment.
     *
     * @param account         the account
     * @param directPaymentId the direct payment id (non-null for multi-steps flows, such as 3D Secure)
     * @param amount          the amount to pay
     * @param currency        the amount currency
     * @param properties      plugin specific properties
     * @param context         the call context
     * @return the payment
     * @throws PaymentApiException
     */
    @RequiresPermissions(PAYMENT_CAN_TRIGGER_PAYMENT)
    public DirectPayment createPurchase(Account account, UUID directPaymentId, BigDecimal amount, Currency currency, String externalKey, Iterable<PluginProperty> properties, CallContext context)
            throws PaymentApiException;

    /**
     * Void a previously authorized payment.
     *
     * @param account         the account
     * @param directPaymentId the direct payment id
     * @param properties      plugin specific properties
     * @param context         the call context
     * @return the payment
     * @throws PaymentApiException
     */
    @RequiresPermissions(PAYMENT_CAN_TRIGGER_PAYMENT)
    public DirectPayment createVoid(Account account, UUID directPaymentId, Iterable<PluginProperty> properties, CallContext context)
            throws PaymentApiException;

    /**
     * Credit a previously captured payment.
     *
     * @param account         the account
     * @param directPaymentId the direct payment id
     * @param amount          the amount to credit
     * @param currency        the amount currency
     * @param properties      plugin specific properties
     * @param context         the call context
     * @return the payment
     * @throws PaymentApiException
     */
    @RequiresPermissions(PAYMENT_CAN_TRIGGER_PAYMENT)
    public DirectPayment createCredit(Account account, UUID directPaymentId, BigDecimal amount, Currency currency, Iterable<PluginProperty> properties, CallContext context)
            throws PaymentApiException;

    /**
     * @param accountId the account id
     * @param context   the call context
     * @return the list of direct payments on this account
     * @throws PaymentApiException
     */
    public List<DirectPayment> getAccountPayments(UUID accountId, TenantContext context)
            throws PaymentApiException;

    /**
     * @param directPaymentId the direct payment id
     * @param withPluginInfo  whether to fetch plugin info
     * @param properties      plugin specific properties
     * @param context         the call context
     * @return the payment
     * @throws PaymentApiException
     */
    public DirectPayment getPayment(UUID directPaymentId, final boolean withPluginInfo, Iterable<PluginProperty> properties, TenantContext context)
            throws PaymentApiException;

    /**
     * Find all payments across all plugins
     *
     * @param offset     the offset of the first result
     * @param limit      the maximum number of results to retrieve
     * @param properties plugin specific properties
     * @param context    the user context
     * @return the list of payments for that tenant
     */
    public Pagination<DirectPayment> getPayments(Long offset, Long limit, Iterable<PluginProperty> properties, TenantContext context);

    /**
     * Find all payments in a given plugin
     *
     * @param offset     the offset of the first result
     * @param limit      the maximum number of results to retrieve
     * @param pluginName the payment plugin name
     * @param properties plugin specific properties
     * @param context    the user context
     * @return the list of payments for that tenant
     * @throws PaymentApiException
     */
    public Pagination<DirectPayment> getPayments(Long offset, Long limit, String pluginName, Iterable<PluginProperty> properties, TenantContext context) throws PaymentApiException;

    /**
     * Find all payments matching the search key across all plugins
     * <p/>
     * The match will be plugin specific: for instance some plugins will try to match the key
     * against the transaction ids, etc.
     *
     * @param searchKey  the search key
     * @param offset     the offset of the first result
     * @param limit      the maximum number of results to retrieve
     * @param properties plugin specific properties
     * @param context    the user context
     * @return the list of payments matching this search key for that tenant
     */
    public Pagination<DirectPayment> searchPayments(String searchKey, Long offset, Long limit, Iterable<PluginProperty> properties, TenantContext context);

    /**
     * Find all payments matching the search key in a given plugin
     * <p/>
     * The match will be plugin specific: for instance some plugins will try to match the key
     * against the transaction ids, etc.
     *
     * @param searchKey  the search key
     * @param offset     the offset of the first result
     * @param limit      the maximum number of results to retrieve
     * @param pluginName the payment plugin name
     * @param properties plugin specific properties
     * @param context    the user context
     * @return the list of payments matching this search key for that tenant
     * @throws PaymentApiException
     */
    public Pagination<DirectPayment> searchPayments(String searchKey, Long offset, Long limit, String pluginName, Iterable<PluginProperty> properties, TenantContext context) throws PaymentApiException;
}
