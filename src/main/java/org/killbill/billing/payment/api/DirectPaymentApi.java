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

import org.killbill.billing.account.api.Account;
import org.killbill.billing.security.RequiresPermissions;
import org.killbill.billing.util.callcontext.CallContext;
import org.killbill.billing.util.callcontext.TenantContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.killbill.billing.security.Permission.PAYMENT_CAN_TRIGGER_PAYMENT;

public interface DirectPaymentApi {

    /**
     *
     * Authorize a direct payment.
     *
     * @param account the account
     * @param amount  the amount to pay
     * @param context the call context
     * @return the payment
     * @throws PaymentApiException
     */
    @RequiresPermissions(PAYMENT_CAN_TRIGGER_PAYMENT)
    public DirectPayment createAuthorization(Account account, BigDecimal amount, String externalKey, CallContext context)
            throws PaymentApiException;

    /**
     *
     * Capture a previously authorized direct payment.
     *
     * @param account the account
     * @param amount  the amount to pay
     * @param context the call context
     * @return the payment
     * @throws PaymentApiException
     */
    @RequiresPermissions(PAYMENT_CAN_TRIGGER_PAYMENT)
    public DirectPayment createCapture(Account account, UUID directPaymentId, BigDecimal amount, CallContext context)
            throws PaymentApiException;

    /**
     *
     * Combine an authorize and capture direct payment.
     *
     * @param account the account
     * @param amount  the amount to pay
     * @param context the call context
     * @return the payment
     * @throws PaymentApiException
     */
    @RequiresPermissions(PAYMENT_CAN_TRIGGER_PAYMENT)
    public DirectPayment createPurchase(Account account, BigDecimal amount, String externalKey, CallContext context)
            throws PaymentApiException;

    /**
     *
     * Void a previously authorized payment.
     *
     * @param account the account
     * @param context the call context
     * @return the payment
     * @throws PaymentApiException
     */
    @RequiresPermissions(PAYMENT_CAN_TRIGGER_PAYMENT)
    public DirectPayment createVoid(Account account, UUID directPaymentId,  CallContext context)
            throws PaymentApiException;

    /**
     *
     * Credit a previously captured payment.
     *
     * @param account the account
     * @param context the call context
     * @return the payment
     * @throws PaymentApiException
     */
    @RequiresPermissions(PAYMENT_CAN_TRIGGER_PAYMENT)
    public DirectPayment createCredit(Account account, UUID directPaymentId,  CallContext context)
            throws PaymentApiException;


    public List<DirectPayment> getAccountPayments(UUID accountId, final boolean withPluginInfo, TenantContext context)
            throws PaymentApiException;


    public DirectPayment getPayment(UUID directPaymentId, final boolean withPluginInfo, TenantContext context)
            throws PaymentApiException;

}
