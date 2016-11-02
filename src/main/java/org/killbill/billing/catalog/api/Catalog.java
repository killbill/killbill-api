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

package org.killbill.billing.catalog.api;

import java.util.Collection;
import java.util.Date;

import org.joda.time.DateTime;

/**
 * The {@code Catalog} information for a specific tenant.
 */
public interface Catalog {

    /**
     * @return the catalogName
     */
    public String getCatalogName();

    /**
     * @param requestedDate the requestedDate
     * @return the effective for the {@code StandaloneCatalog} matching that requestedDate
     * @throws CatalogApiException
     */
    public Date getStandaloneCatalogEffectiveDate(final DateTime requestedDate) throws CatalogApiException;

    /**
     * @param requestedDate specifies the state of the catalog for that date
     * @return an array of available {@code Currency}s
     * @throws CatalogApiException
     */
    public Currency[] getSupportedCurrencies(DateTime requestedDate) throws CatalogApiException;

    /**
     * @param requestedDate specifies the state of the catalog for that date
     * @return an array of available {@code Product}s
     * @throws CatalogApiException
     */
    public Collection<Product> getProducts(DateTime requestedDate) throws CatalogApiException;

    /**
     * @param requestedDate specifies the state of the catalog for that date
     * @return an array of available {@code Plan}s
     * @throws CatalogApiException
     */
    public Collection<Plan> getPlans(DateTime requestedDate) throws CatalogApiException;

    /**
     * @param requestedDate specifies the state of the catalog for that date
     * @return the {@code PriceListSet} for that requestedDate
     * @throws CatalogApiException
     */
    PriceListSet getPriceLists(final DateTime requestedDate) throws CatalogApiException;

    /**
     * @param name          the unique name of the plan
     * @param requestedDate specifies the state of the catalog for that date
     * @return the {@code Plan}
     * @throws CatalogApiException if {@code Plan} does not exist
     */
    public Plan findPlan(String name, DateTime requestedDate) throws CatalogApiException;

    /**
     * @param spec          the specification for the {@code Plan} to be used
     * @param overrides     the price override for each phase and for a specific currency
     * @param requestedDate specifies the state of the catalog for that date
     * @return the {@code Plan}
     * @throws CatalogApiException if {@code Plan} does not exist
     */
    public Plan createOrFindPlan(PlanSpecifier spec, PlanPhasePriceOverridesWithCallContext overrides,
                                 DateTime requestedDate) throws CatalogApiException;

    /**
     * @param name                  the unique name of the plan
     * @param requestedDate         specifies the state of the catalog for that date
     * @param subscriptionStartDate the startDate of the subscription
     * @return the {@code Plan}
     * @throws CatalogApiException if {@code Plan} does not exist
     */
    public Plan findPlan(String name, DateTime requestedDate, DateTime subscriptionStartDate) throws CatalogApiException;

    /**
     * @param spec          the specification for the {@code Plan} to be used
     * @param overrides     the price override for each phase and for a specific currency
     * @param requestedDate specifies the state of the catalog for that date
     * @return the {@code Plan}
     * @throws CatalogApiException if {@code Plan} does not exist
     */
    public Plan createOrFindPlan(PlanSpecifier spec, PlanPhasePriceOverridesWithCallContext overrides,
                                 DateTime requestedDate, DateTime subscriptionStartDate) throws CatalogApiException;

    /**
     * @param name          the unique name for the {@code Product}
     * @param requestedDate specifies the state of the catalog for that date
     * @return the {@code Product}
     * @throws CatalogApiException if {@code Product} does not exist
     */
    public Product findProduct(String name, DateTime requestedDate) throws CatalogApiException;

    /**
     * @param name          the unique name for the {@code PlanPhase}
     * @param requestedDate specifies the state of the catalog for that date
     * @return the {@code PlanPhase}
     * @throws CatalogApiException if the {@code PlanPhase} does not exist
     */
    public PriceList findPriceList(String name, DateTime requestedDate) throws CatalogApiException;

    /**
     *
     * @param planName                  the unique name of the plan
     * @param requestedDate             specifies the state of the catalog for that date
     * @param subscriptionStartDate     the startDate of the subscription
     * @return
     */
    public PriceList findPriceListForPlan(final String planName,
                                          final DateTime requestedDate,
                                          final DateTime subscriptionStartDate) throws CatalogApiException;

    /**
     * @param name                  the unique name for the {@code PlanPhase}
     * @param requestedDate         specifies the state of the catalog for that date
     * @param subscriptionStartDate the startDate of the subscription
     * @return the {@code PlanPhase}
     * @throws CatalogApiException if the {@code PlanPhase} does not exist
     */
    public PlanPhase findPhase(String name, DateTime requestedDate, DateTime subscriptionStartDate) throws CatalogApiException;

    // TODO : should they be private APIs

    public BillingActionPolicy planChangePolicy(PlanPhaseSpecifier from,
                                                PlanSpecifier to, DateTime requestedDate) throws CatalogApiException;

    public PlanChangeResult planChange(PlanPhaseSpecifier from,
                                       PlanSpecifier to, DateTime requestedDate) throws CatalogApiException;

    public BillingActionPolicy planCancelPolicy(PlanPhaseSpecifier planPhase, DateTime requestedDate) throws CatalogApiException;

    public PlanAlignmentCreate planCreateAlignment(PlanSpecifier specifier, DateTime requestedDate) throws CatalogApiException;

    public BillingAlignment billingAlignment(PlanPhaseSpecifier planPhase, DateTime requestedDate) throws CatalogApiException;

    public PlanAlignmentChange planChangeAlignment(PlanPhaseSpecifier from,
                                                   PlanSpecifier to, DateTime requestedDate) throws CatalogApiException;

    public boolean canCreatePlan(PlanSpecifier specifier, DateTime requestedDate) throws CatalogApiException;

}
