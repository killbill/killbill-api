/*
 * Copyright 2010-2014 Ning, Inc.
 * Copyright 2014-2016 Groupon, Inc
 * Copyright 2014-2016 The Billing Project, LLC
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

package org.killbill.billing.osgi.api;

import org.killbill.billing.account.api.AccountUserApi;
import org.killbill.billing.catalog.api.CatalogUserApi;
import org.killbill.billing.currency.api.CurrencyConversionApi;
import org.killbill.billing.entitlement.api.EntitlementApi;
import org.killbill.billing.entitlement.api.SubscriptionApi;
import org.killbill.billing.invoice.api.InvoicePaymentApi;
import org.killbill.billing.invoice.api.InvoiceUserApi;
import org.killbill.billing.osgi.api.config.PluginConfigServiceApi;
import org.killbill.billing.payment.api.AdminPaymentApi;
import org.killbill.billing.payment.api.PaymentApi;
import org.killbill.billing.security.api.SecurityApi;
import org.killbill.billing.tenant.api.TenantUserApi;
import org.killbill.billing.usage.api.UsageUserApi;
import org.killbill.billing.util.api.AuditUserApi;
import org.killbill.billing.util.api.CustomFieldUserApi;
import org.killbill.billing.util.api.ExportUserApi;
import org.killbill.billing.util.api.RecordIdApi;
import org.killbill.billing.util.api.TagUserApi;
import org.killbill.billing.util.nodes.KillbillNodesApi;

/**
 * This interface encapsulates all the OSGI interfaces seen by the Killbill OSGI plugins
 */
public interface OSGIKillbill {

    /**
     * Used  by the OSGI bundles to interact with Killbill through APIs
     *
     * @return the matching API
     */
    public AccountUserApi getAccountUserApi();

    public CatalogUserApi getCatalogUserApi();

    public SubscriptionApi getSubscriptionApi();

    public InvoicePaymentApi getInvoicePaymentApi();

    public InvoiceUserApi getInvoiceUserApi();

    public PaymentApi getPaymentApi();

    public TenantUserApi getTenantUserApi();

    public UsageUserApi getUsageUserApi();

    public AuditUserApi getAuditUserApi();

    public CustomFieldUserApi getCustomFieldUserApi();

    public ExportUserApi getExportUserApi();

    public TagUserApi getTagUserApi();

    public EntitlementApi getEntitlementApi();

    public RecordIdApi getRecordIdApi();

    public CurrencyConversionApi getCurrencyConversionApi();

    /**
     * Used by the OSGI bundles to discover their configuration
     *
     * @return the PluginConfigServiceApi
     */
    public PluginConfigServiceApi getPluginConfigServiceApi();

    public SecurityApi getSecurityApi();

    public PluginsInfoApi getPluginsInfoApi();

    public KillbillNodesApi getKillbillNodesApi();

    public AdminPaymentApi getAdminPaymentApi();
}
