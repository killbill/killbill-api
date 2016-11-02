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
import org.killbill.billing.security.RequiresPermissions;
import org.killbill.billing.util.api.TagApiException;
import org.killbill.billing.util.callcontext.CallContext;
import org.killbill.billing.util.callcontext.TenantContext;
import org.killbill.billing.util.entity.Pagination;

import static org.killbill.billing.security.Permission.ACCOUNT_CAN_CHARGE;
import static org.killbill.billing.security.Permission.ACCOUNT_CAN_CREDIT;
import static org.killbill.billing.security.Permission.INVOICE_CAN_CREDIT;
import static org.killbill.billing.security.Permission.INVOICE_CAN_DELETE_CBA;
import static org.killbill.billing.security.Permission.INVOICE_CAN_ITEM_ADJUST;
import static org.killbill.billing.security.Permission.INVOICE_CAN_TRIGGER_INVOICE;

public interface InvoiceUserApi extends KillbillApi {

    /**
     * Get all invoices for a given account.
     *
     * @param accountId account id
     * @param context   the tenant context
     * @return all invoices
     */
    public List<Invoice> getInvoicesByAccount(UUID accountId, boolean includesMigrated, TenantContext context);

    /**
     * Find invoices from a given day, for a given account.
     *
     * @param accountId account id
     * @param fromDate  the earliest target day to consider, in the account timezone
     * @param context   the tenant context
     * @return a list of invoices
     */
    public List<Invoice> getInvoicesByAccount(UUID accountId, LocalDate fromDate, TenantContext context);

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
     * Find unpaid invoices for a given account, up to a given day.
     *
     * @param accountId account id
     * @param upToDate  the latest target day to consider, in the account timezone
     * @param context   the tenant context
     * @return a collection of invoices
     */
    public Collection<Invoice> getUnpaidInvoicesByAccountId(UUID accountId, LocalDate upToDate, TenantContext context);

    /**
     * Trigger an invoice for a given account and a given day.
     *
     * @param accountId       account id
     * @param targetDate      the target day, in the account timezone
     * @param dryRunArguments dry run arguments
     * @param context         the call context
     * @return the invoice generated
     * @throws InvoiceApiException
     */
    @RequiresPermissions(INVOICE_CAN_TRIGGER_INVOICE)
    public Invoice triggerInvoiceGeneration(UUID accountId, LocalDate targetDate, DryRunArguments dryRunArguments, CallContext context) throws InvoiceApiException;

    /**
     * Mark an invoice as written off.
     *
     * @param invoiceId invoice id
     * @param context   call context
     * @throws TagApiException
     */
    public void tagInvoiceAsWrittenOff(UUID invoiceId, CallContext context) throws TagApiException, InvoiceApiException;

    /**
     * Unmark an invoice as written off.
     *
     * @param invoiceId invoice id
     * @param context   call context
     * @throws TagApiException
     */
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
    public List<InvoiceItem> insertExternalCharges(UUID accountId, LocalDate effectiveDate, Iterable<InvoiceItem> charges, boolean autoCommit, CallContext context) throws InvoiceApiException;

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
     * @param amount        the credit amount
     * @param effectiveDate the day to grant the credit, in the account timezone
     * @param currency      the credit currency
     * @param autoCommit    the flag to indicate if the invoice is set to COMMITTED or DRAFT and events are sent
     * @param context       the call context
     * @param description   the item description
     * @return the credit invoice item
     * @throws InvoiceApiException
     */
    @RequiresPermissions(ACCOUNT_CAN_CREDIT)
    public InvoiceItem insertCredit(UUID accountId, BigDecimal amount, LocalDate effectiveDate, Currency currency,
                                    boolean autoCommit, String description, CallContext context) throws InvoiceApiException;

    /**
     * Add a credit to an invoice. This can be used to adjust invoices.
     *
     * @param accountId     account id
     * @param invoiceId     invoice id
     * @param amount        the credit amount
     * @param effectiveDate the day to grant the credit, in the account timezone
     * @param currency      the credit currency
     * @param description   the item description
     * @param context       the call context
     * @return the credit invoice item
     * @throws InvoiceApiException
     */
    @RequiresPermissions(INVOICE_CAN_CREDIT)
    public InvoiceItem insertCreditForInvoice(UUID accountId, UUID invoiceId, BigDecimal amount, LocalDate effectiveDate,
                                              Currency currency, String description, CallContext context) throws InvoiceApiException;

    /**
     * Adjust fully a given invoice item.
     *
     * @param accountId     account id
     * @param invoiceId     invoice id
     * @param invoiceItemId invoice item id
     * @param effectiveDate the effective date for this adjustment invoice item (in the account timezone)
     * @param description   the item description
     * @param context       the call context
     * @return the adjustment invoice item
     * @throws InvoiceApiException
     */
    @RequiresPermissions(INVOICE_CAN_ITEM_ADJUST)
    public InvoiceItem insertInvoiceItemAdjustment(UUID accountId, UUID invoiceId, UUID invoiceItemId, LocalDate effectiveDate,
                                                   String description, CallContext context) throws InvoiceApiException;

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
     * @param context       the call context
     * @return the adjustment invoice item
     * @throws InvoiceApiException
     */
    @RequiresPermissions(INVOICE_CAN_ITEM_ADJUST)
    public InvoiceItem insertInvoiceItemAdjustment(UUID accountId, UUID invoiceId, UUID invoiceItemId, LocalDate effectiveDate,
                                                   BigDecimal amount, Currency currency, String description, CallContext context) throws InvoiceApiException;

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
    public void consumeExstingCBAOnAccountWithUnpaidInvoices(final UUID accountId, final CallContext context);

    /**
     * Move the invoice status from DRAFT to COMMITTED
     *
     * @param invoiceId invoice id
     * @param context the tenant context
     * @throws InvoiceApiException
     */
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
}
