/*
 * Copyright 2010-2013 Ning, Inc.
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

package org.killbill.billing.invoice.api;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.joda.time.LocalDate;
import org.killbill.billing.KillbillApi;
import org.killbill.billing.account.api.AccountApiException;
import org.killbill.billing.catalog.api.Currency;
import org.killbill.billing.payment.api.PluginProperty;
import org.killbill.billing.security.RequiresPermissions;
import org.killbill.billing.util.api.AuditLevel;
import org.killbill.billing.util.api.TagApiException;
import org.killbill.billing.util.audit.AuditLogWithHistory;
import org.killbill.billing.util.callcontext.CallContext;
import org.killbill.billing.util.callcontext.TenantContext;
import org.killbill.billing.util.entity.Pagination;

import static org.killbill.billing.security.Permission.ACCOUNT_CAN_CHARGE;
import static org.killbill.billing.security.Permission.ACCOUNT_CAN_CREDIT;
import static org.killbill.billing.security.Permission.INVOICE_CAN_COMMIT;
import static org.killbill.billing.security.Permission.INVOICE_CAN_CREDIT;
import static org.killbill.billing.security.Permission.INVOICE_CAN_DELETE_CBA;
import static org.killbill.billing.security.Permission.INVOICE_CAN_DRY_RUN_INVOICE;
import static org.killbill.billing.security.Permission.INVOICE_CAN_ITEM_ADJUST;
import static org.killbill.billing.security.Permission.INVOICE_CAN_TRIGGER_INVOICE;
import static org.killbill.billing.security.Permission.INVOICE_CAN_VOID;
import static org.killbill.billing.security.Permission.INVOICE_CAN_WRITE_OFF;

public interface InvoiceUserApi extends KillbillApi {

    /**
     * Get all invoices for a given account.
     *
     * @param accountId account id
     * @param includesMigrated flag that indicates if migrated invoices should be returned
     * @param includeVoidedInvoices flag that indicates if voided invoices should be returned
     * @param includeInvoiceComponents flag that indicates if invoice components (items, payments, etc.) should be returned
     * @param context   the tenant context
     * @return all invoices
     */
    public List<Invoice> getInvoicesByAccount(UUID accountId, boolean includesMigrated, boolean includeVoidedInvoices, boolean includeInvoiceComponents, TenantContext context);
    
    /**
     * @param accountId account id
     * @param offset the offset of the first result
     * @param limit the maximum number of results to retrieve
     * @param context the tenant context
     * @return paginated invoices
     */
    public Pagination<Invoice> getInvoicesByAccount(UUID accountId, Long offset, Long limit, TenantContext context);

    /**
     * Find invoices from a given day, for a given account.
     *
     * @param accountId account id
     * @param fromDate  the earliest included target day to consider, in the account timezone
     * @param upToDate  the latest included target day to consider, in the account timezone
     * @param includeInvoiceComponents flag that indicates if invoice components (items, payments, etc.) should be returned
     * @param context   the tenant context
     * @return a list of invoices
     */
    public List<Invoice> getInvoicesByAccount(UUID accountId, LocalDate fromDate, LocalDate upToDate, boolean includeVoidedInvoices, boolean includeInvoiceComponents, TenantContext context);

    /**
     *
     * @param accountId account id
     * @param groupId the group id
     * @param context the tenant context
     * @return
     */
    public List<Invoice> getInvoicesByGroup(UUID accountId, UUID groupId, TenantContext context);


    /**
     * @param context the user context
     * @param offset  the offset of the first result
     * @param limit   the maximum number of results to retrieve
     * @return the list of invoices for that tenant
     */
    public Pagination<Invoice> getInvoices(Long offset, Long limit, TenantContext context);

    /**
     * Find all invoices having their id, number, account id or currency matching the search key
     *
     * @param searchKey the search key
     * @param offset    the offset of the first result
     * @param limit     the maximum number of results to retrieve
     * @param context   the user context
     * @return the list of invoices matching this search key for that tenant
     */
    public Pagination<Invoice> searchInvoices(String searchKey, Long offset, Long limit, TenantContext context);

    /**
     * Retrieve the account balance.
     *
     * @param accountId account id
     * @param context   the tenant context
     * @return the account balance
     */
    public BigDecimal getAccountBalance(UUID accountId, TenantContext context);

    /**
     * Retrieve the account CBA.
     *
     * @param accountId account id
     * @param context   the tenant context
     * @return the account CBA
     */
    public BigDecimal getAccountCBA(UUID accountId, TenantContext context);

    /**
     * Retrieve an invoice by id.
     *
     * @param invoiceId invoice id
     * @param context   the tenant context
     * @return the invoice
     */
    public Invoice getInvoice(UUID invoiceId, TenantContext context) throws InvoiceApiException;

    /**
     * Get all invoices for a given payment.
     *
     * @param paymentId payment id
     * @param context   the tenant context
     * @return the invoice
     */
    public Invoice getInvoiceByPayment(UUID paymentId, TenantContext context) throws InvoiceApiException;

    /**
     * Retrieve an invoice by invoice number.
     *
     * @param number  invoice number
     * @param context the tenant context
     * @return the invoice
     */
    public Invoice getInvoiceByNumber(Integer number, TenantContext context) throws InvoiceApiException;

    /**
     *
     * @param invoiceItemId invoice item id
     * @param context  the tenant context
     * @return
     * @throws InvoiceApiException
     */
    public Invoice getInvoiceByInvoiceItem(UUID invoiceItemId, TenantContext context) throws InvoiceApiException;

    /**
     * Find unpaid invoices for a given account, up to a given day.
     *
     * @param accountId account id
     * @param fromDate  the earliest included target day to consider, in the account timezone
     * @param upToDate  the latest included target day to consider, in the account timezone
     * @param context   the tenant context
     * @return a collection of invoices
     */
    public Collection<Invoice> getUnpaidInvoicesByAccountId(UUID accountId, LocalDate fromDate, LocalDate upToDate, TenantContext context);

    /**
     * Trigger an invoice for a given account and a given day.
     *
     * @param accountId       account id
     * @param targetDate      the target day, in the account timezone
     * @param properties      plugin specific properties
     * @param context         the call context
     * @return the invoice generated
     * @throws InvoiceApiException
     */
    @RequiresPermissions(INVOICE_CAN_TRIGGER_INVOICE)
    public Invoice triggerInvoiceGeneration(UUID accountId, LocalDate targetDate, Iterable<PluginProperty> properties, CallContext context) throws InvoiceApiException;

    /**
     * Trigger an invoice for a given account and a given day.
     *
     * If there is an active plugin implementing the InvoicePluginApi#getInvoiceGrouping it will be invoked,
     * and as a result we may end up with N invoices.
     *
     * @param accountId       account id
     * @param targetDate      the target day, in the account timezone
     * @param properties      plugin specific properties
     * @param context         the call context
     * @return the invoice generated
     * @throws InvoiceApiException
     */
    @RequiresPermissions(INVOICE_CAN_TRIGGER_INVOICE)
    public Iterable<Invoice> triggerInvoiceGroupGeneration(UUID accountId, LocalDate targetDate, Iterable<PluginProperty> properties, CallContext context) throws InvoiceApiException;


    /**
     * Trigger an invoice for a given account and a given day.
     *
     * @param accountId       account id
     * @param targetDate      the target day, in the account timezone
     * @param dryRunArguments dry run arguments
     * @param properties      plugin specific properties
     * @param context         the call context
     * @return the invoice generated
     * @throws InvoiceApiException
     */
    @RequiresPermissions(INVOICE_CAN_DRY_RUN_INVOICE)
    public Invoice triggerDryRunInvoiceGeneration(UUID accountId, LocalDate targetDate, DryRunArguments dryRunArguments, Iterable<PluginProperty> properties, CallContext context) throws InvoiceApiException;


    /**
     * Mark an invoice as written off.
     *
     * @param invoiceId invoice id
     * @param context   call context
     * @throws TagApiException
     */
    @RequiresPermissions(INVOICE_CAN_WRITE_OFF)
    public void tagInvoiceAsWrittenOff(UUID invoiceId, CallContext context) throws TagApiException, InvoiceApiException;

    /**
     * Unmark an invoice as written off.
     *
     * @param invoiceId invoice id
     * @param context   call context
     * @throws TagApiException
     */
    @RequiresPermissions(INVOICE_CAN_WRITE_OFF)
    public void tagInvoiceAsNotWrittenOff(UUID invoiceId, CallContext context) throws TagApiException, InvoiceApiException;

    /**
     * Retrieve an external charge by id.
     *
     * @param externalChargeId external charge id
     * @param context          the tenant context
     * @return the external charge
     * @throws InvoiceApiException
     */
    public InvoiceItem getExternalChargeById(UUID externalChargeId, TenantContext context) throws InvoiceApiException;

    /**
     * Add one or multiple external charges to an account.
     *
     * @param accountId     account id
     * @param effectiveDate the effective date for newly created invoices (in the account timezone)
     * @param charges       the charges
     * @param autoCommit    the flag to indicate if the invoice is set to COMMITTED or DRAFT and events are sent
     * @param context       the call context
     * @return the external charges invoice items
     * @throws InvoiceApiException
     */
    @RequiresPermissions(ACCOUNT_CAN_CHARGE)
    public List<InvoiceItem> insertExternalCharges(UUID accountId, LocalDate effectiveDate, Iterable<InvoiceItem> charges, boolean autoCommit, Iterable<PluginProperty> properties, CallContext context) throws InvoiceApiException;


    /**
     * Add one or multiple tax items to one invoice.
     *
     * @param accountId     account id
     * @param effectiveDate the effective date for newly created invoice (in the account timezone)
     * @param taxes         the tax items
     * @param autoCommit    the flag to indicate if the invoice is set to COMMITTED or DRAFT and events are sent
     * @param context       the call context
     * @return the tax invoice items
     * @throws InvoiceApiException
     */
    @RequiresPermissions(ACCOUNT_CAN_CHARGE)
    public List<InvoiceItem> insertTaxItems(UUID accountId, LocalDate effectiveDate, Iterable<InvoiceItem> taxes, boolean autoCommit, Iterable<PluginProperty> properties, CallContext context) throws InvoiceApiException;


    /**
     * Retrieve a credit by id.
     *
     * @param creditId credit id
     * @param context  the tenant context
     * @return the credit
     * @throws InvoiceApiException
     */
    public InvoiceItem getCreditById(UUID creditId, TenantContext context) throws InvoiceApiException;

    /**
     * Add a credit to an account.
     *
     * @param accountId     account id
     * @param effectiveDate the day to grant the credit, in the account timezone
     * @param creditItems   the list of credits to add
     * @param autoCommit    the flag to indicate if the invoice is set to COMMITTED or DRAFT and events are sent
     * @param context       the call context
     * @param properties    the plugin specific properties
     * @return the credit invoice items
     * @throws InvoiceApiException
     */
    @RequiresPermissions(ACCOUNT_CAN_CREDIT)
    public List<InvoiceItem> insertCredits(UUID accountId, LocalDate effectiveDate, Iterable<InvoiceItem> creditItems, boolean autoCommit, Iterable<PluginProperty> properties, CallContext context) throws InvoiceApiException;


    /**
     * Adjust fully a given invoice item.
     *
     * @param accountId     account id
     * @param invoiceId     invoice id
     * @param invoiceItemId invoice item id
     * @param effectiveDate the effective date for this adjustment invoice item (in the account timezone)
     * @param description   the item description
     * @param itemDetails   the item details
     * @param properties    the plugin specific properties
     * @param context       the call context
     * @return the adjustment invoice item
     * @throws InvoiceApiException
     */
    @RequiresPermissions(INVOICE_CAN_ITEM_ADJUST)
    public InvoiceItem insertInvoiceItemAdjustment(UUID accountId, UUID invoiceId, UUID invoiceItemId, LocalDate effectiveDate,
                                                   String description, String itemDetails, Iterable<PluginProperty> properties, CallContext context) throws InvoiceApiException;

    /**
     * Adjust partially a given invoice item.
     *
     * @param accountId     account id
     * @param invoiceId     invoice id
     * @param invoiceItemId invoice item id
     * @param effectiveDate the effective date for this adjustment invoice item (in the account timezone)
     * @param amount        the adjustment amount
     * @param currency      adjustment currency
     * @param description   the item description
     * @param itemDetails   the item details
     * @param properties    the plugin specific properties
     * @param context       the call context
     * @return the adjustment invoice item
     * @throws InvoiceApiException
     */
    @RequiresPermissions(INVOICE_CAN_ITEM_ADJUST)
    public InvoiceItem insertInvoiceItemAdjustment(UUID accountId, UUID invoiceId, UUID invoiceItemId, LocalDate effectiveDate,
                                                   BigDecimal amount, Currency currency, String description, String itemDetails, Iterable<PluginProperty> properties, CallContext context) throws InvoiceApiException;

    /**
     * Delete a CBA item.
     *
     * @param accountId     account id
     * @param invoiceId     invoice id
     * @param invoiceItemId invoice item id (must be of type CBA_ADJ)
     * @param context       the call context
     * @throws InvoiceApiException
     */
    @RequiresPermissions(INVOICE_CAN_DELETE_CBA)
    public void deleteCBA(UUID accountId, UUID invoiceId, UUID invoiceItemId, CallContext context) throws InvoiceApiException;

    /**
     * Retrieve the invoice formatted in HTML.
     *
     * @param invoiceId invoice id
     * @param context   the tenant context
     * @return the invoice in HTML format
     * @throws AccountApiException
     * @throws IOException
     * @throws InvoiceApiException
     */
    public String getInvoiceAsHTML(UUID invoiceId, TenantContext context) throws AccountApiException, IOException, InvoiceApiException;

    /**
     * Rebalance CBA for account which have credit and unpaid invoices-- only needed if system is configured to not rebalance automatically.
     *
     * @param accountId account id
     * @param context   the call context
     */
    @RequiresPermissions(INVOICE_CAN_DELETE_CBA)
    public void consumeExistingCBAOnAccountWithUnpaidInvoices(final UUID accountId, final CallContext context);

    /**
     * Move the invoice status from DRAFT to COMMITTED
     *
     * @param invoiceId invoice id
     * @param context the tenant context
     * @throws InvoiceApiException
     */
    @RequiresPermissions(INVOICE_CAN_COMMIT)
    public void commitInvoice(UUID invoiceId, CallContext context) throws InvoiceApiException;

    /** @param accountId   account id
     * @param invoiceDate maximum billing event day to consider (in the account timezone)
     * @param items       items to be placed on the migration invoice
     * @param context     call call context
     * @return The UUID of the created invoice
     */
    @RequiresPermissions(INVOICE_CAN_TRIGGER_INVOICE)
    public UUID createMigrationInvoice(UUID accountId, LocalDate invoiceDate, Iterable<InvoiceItem> items, CallContext context);

    /**
     * Move a given child credit to the parent level
     *
     * @param childAccountId child account id
     * @param context the tenant context
     * @throws InvoiceApiException
     */
    @RequiresPermissions(INVOICE_CAN_DELETE_CBA)
    public void transferChildCreditToParent(UUID childAccountId, CallContext context) throws InvoiceApiException;

    /**
     * Retrieve invoice items details associated to Parent SUMMARY invoice item
     *
     * @param parentInvoiceId the parent invoice id
     * @param context the tenant context
     * @return a list of invoice items associated with a parent invoice
     * @throws InvoiceApiException if any unexpected error occurs
     */
    List<InvoiceItem> getInvoiceItemsByParentInvoice(UUID parentInvoiceId, final TenantContext context) throws InvoiceApiException;

    /**
     * Move the invoice status from DRAFT or COMMITTED to VOID
     *
     * @param invoiceId invoice id
     * @param context the tenant context
     * @throws InvoiceApiException
     */
    @RequiresPermissions(INVOICE_CAN_VOID)
    public void voidInvoice(UUID invoiceId, CallContext context) throws InvoiceApiException;

    /**
     * Get all the audit entries with history for a given invoice.
     *
     * @param invoiceId         the invoice id
     * @param auditLevel        audit level (verbosity)
     * @param context           the tenant context
     * @return all audit entries with history for an invoice
     */
    List<AuditLogWithHistory> getInvoiceAuditLogsWithHistoryForId(UUID invoiceId, AuditLevel auditLevel, TenantContext context);

    /**
     * Get all the audit entries with history for a given invoice item.
     *
     * @param invoiceItemId      the invoice item id
     * @param auditLevel        audit level (verbosity)
     * @param context           the tenant context
     * @return all audit entries with history for an invoice item
     */
    List<AuditLogWithHistory> getInvoiceItemAuditLogsWithHistoryForId(UUID invoiceItemId, AuditLevel auditLevel, TenantContext context);

    /**
     * Get all the audit entries with history for a given invoice payment.
     *
     * @param invoicePaymentId   the invoice payment id
     * @param auditLevel        audit level (verbosity)
     * @param context           the tenant context
     * @return all audit entries with history for an invoice payment
     */
    List<AuditLogWithHistory> getInvoicePaymentAuditLogsWithHistoryForId(UUID invoicePaymentId, AuditLevel auditLevel, TenantContext context);

}
