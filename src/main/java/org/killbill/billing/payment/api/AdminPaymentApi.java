/*
 * Copyright 2010-2013 Ning, Inc.
 * Copyright 2015 Groupon, Inc
 * Copyright 2015 The Billing Project, LLC
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

import org.killbill.billing.KillbillApi;
import org.killbill.billing.security.RequiresPermissions;
import org.killbill.billing.util.callcontext.CallContext;

import static org.killbill.billing.security.Permission.ADMIN_CAN_FIX_DATA;

public interface AdminPaymentApi extends KillbillApi {

    /**
     * Modify the state for a given PaymentTransaction and its associated Payment. Should ONLY be used for admin purpose when
     * data is corrupted and will require special privileges.
     *
     * @param payment                 the payment whose state will be updated
     * @param paymentTransaction      the paymentTransaction whose state will be updated
     * @param transactionStatus       the new transactionStatus
     * @param lastSuccessPaymentState the new lastSuccessPaymentState
     * @param currentPaymentStateName the new currentPaymentStateName
     * @param properties              plugin properties
     * @param context                 a valid context
     * @throws PaymentApiException
     */
    @RequiresPermissions(ADMIN_CAN_FIX_DATA)
    public void fixPaymentTransactionState(final Payment payment, PaymentTransaction paymentTransaction, TransactionStatus transactionStatus, String lastSuccessPaymentState, String currentPaymentStateName,
                                           Iterable<PluginProperty> properties, CallContext context)
            throws PaymentApiException;

}
