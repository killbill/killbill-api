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

package org.killbill.billing.util.audit;

import java.util.List;
import java.util.UUID;

import org.killbill.billing.ObjectType;

public interface AccountAuditLogs {

    public List<AuditLog> getAuditLogsForAccount();

    public List<AuditLog> getAuditLogsForBundle(UUID bundleId);

    public List<AuditLog> getAuditLogsForSubscription(UUID subscriptionId);

    public List<AuditLog> getAuditLogsForSubscriptionEvent(UUID subscriptionEventId);

    public List<AuditLog> getAuditLogsForBlockingState(UUID blockingStateId);

    public List<AuditLog> getAuditLogsForInvoice(UUID invoiceId);

    public List<AuditLog> getAuditLogsForInvoiceItem(UUID invoiceItemId);

    public List<AuditLog> getAuditLogsForInvoicePayment(UUID invoicePaymentId);

    public List<AuditLog> getAuditLogsForPayment(UUID paymentId);

    public List<AuditLog> getAuditLogsForPaymentAttempt(UUID paymentAttemptId);

    public List<AuditLog> getAuditLogsForPaymentTransaction(UUID paymentTransactionId);

    public List<AuditLog> getAuditLogsForPaymentMethod(UUID paymentMethodId);

    public List<AuditLog> getAuditLogsForTag(UUID tagId);

    public List<AuditLog> getAuditLogsForCustomField(UUID customFieldId);

    public AccountAuditLogsForObjectType getAuditLogs(ObjectType objectType);
}
