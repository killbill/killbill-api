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

package org.killbill.billing.catalog.api;

import java.util.Collection;
import java.util.Date;
import java.util.List;

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
     * @throws CatalogApiException if no catalog can be found for that date
     */
    public Date getStandaloneCatalogEffectiveDate(final DateTime requestedDate) throws CatalogApiException;

    /**
     * @param requestedDate specifies the state of the catalog for that date
     * @return an array of available {@code Currency}s
     * @throws CatalogApiException if no catalog can be found for that date
     */
    public Currency[] getSupportedCurrencies(DateTime requestedDate) throws CatalogApiException;

    /**
     * @param requestedDate specifies the state of the catalog for that date
     * @return an array of available {@code Unit}s
     * @throws CatalogApiException if no catalog can be found for that date
     */
    public Unit[] getUnits(DateTime requestedDate) throws CatalogApiException;

    /**
     * @param requestedDate specifies the state of the catalog for that date
     * @return an array of available {@code Product}s
     * @throws CatalogApiException if no catalog can be found for that date
     */
    public Collection<Product> getProducts(DateTime requestedDate) throws CatalogApiException;

    /**
     * @param requestedDate specifies the state of the catalog for that date
     * @return an array of available {@code Plan}s
     * @throws CatalogApiException if no catalog can be found for that date
     */
    public Collection<Plan> getPlans(DateTime requestedDate) throws CatalogApiException;

    /**
     * @param requestedDate specifies the state of the catalog for that date
     * @return the {@code PriceListSet} for that requestedDate
     * @throws CatalogApiException if no catalog can be found for that date
     */
    PriceListSet getPriceLists(final DateTime requestedDate) throws CatalogApiException;

    /**
     * @param name          the unique name of the plan
     * @param requestedDate specifies the state of the catalog for that date
     * @return the {@code Plan}
     * @throws CatalogApiException if no catalog can be found for that date or if the {@code Plan} does not exist
     */
    public Plan findPlan(String name, DateTime requestedDate) throws CatalogApiException;

    /**
     * @param spec          the specification for the {@code Plan} to be used
     * @param overrides     the price override for each phase and for a specific currency
     * @param requestedDate specifies the state of the catalog for that date
     * @return the {@code Plan}
     * @throws CatalogApiException if no catalog can be found for that date or if the {@code Plan} cannot be found nor created
     */
    public Plan createOrFindPlan(PlanSpecifier spec,
                                 PlanPhasePriceOverridesWithCallContext overrides,
                                 DateTime requestedDate) throws CatalogApiException;


    /**
     * @param name          the unique name for the {@code Product}
     * @param requestedDate specifies the state of the catalog for that date
     * @return the {@code Product}
     * @throws CatalogApiException if no catalog can be found for that date or if the {@code Product} does not exist
     */
    public Product findProduct(String name,
                               DateTime requestedDate) throws CatalogApiException;


    public List<StaticCatalog> getVersions();
}
