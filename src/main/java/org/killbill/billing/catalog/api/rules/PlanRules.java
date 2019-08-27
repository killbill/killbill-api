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

package org.killbill.billing.catalog.api.rules;

import org.killbill.billing.catalog.api.BillingActionPolicy;
import org.killbill.billing.catalog.api.BillingAlignment;
import org.killbill.billing.catalog.api.CatalogApiException;
import org.killbill.billing.catalog.api.PlanAlignmentCreate;
import org.killbill.billing.catalog.api.PlanChangeResult;
import org.killbill.billing.catalog.api.PlanPhaseSpecifier;
import org.killbill.billing.catalog.api.PlanSpecifier;
import org.killbill.billing.catalog.api.StaticCatalog;

public interface PlanRules {

    public StaticCatalog getCatalog();

    public Iterable<CaseChangePlanPolicy> getCaseChangePlanPolicy();

    public Iterable<CaseChangePlanAlignment> getCaseChangePlanAlignment();

    public Iterable<CaseCancelPolicy> getCaseCancelPolicy();

    public Iterable<CaseCreateAlignment> getCaseCreateAlignment();

    public Iterable<CaseBillingAlignment> getCaseBillingAlignment();

    public Iterable<CasePriceList> getCasePriceList();

    // TODO_CATALOG Debatable whether this is really public -- at the same time we already export the models PlanChangeResult, ..
    public PlanAlignmentCreate getPlanCreateAlignment(final PlanSpecifier specifier) throws CatalogApiException;

    public BillingActionPolicy getPlanCancelPolicy(final PlanPhaseSpecifier planPhase) throws CatalogApiException;

    public BillingAlignment getBillingAlignment(final PlanPhaseSpecifier planPhase) throws CatalogApiException;

    public PlanChangeResult getPlanChangeResult(final PlanPhaseSpecifier from, final PlanSpecifier to) throws CatalogApiException;

}
