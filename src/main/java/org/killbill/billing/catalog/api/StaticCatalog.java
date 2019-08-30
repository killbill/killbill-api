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

import org.killbill.billing.catalog.api.rules.PlanRules;

/**
 * The interface {@code StaticCatalog} gives the view of that {@code Catalog} at a given time.
 * This represents a specific version of the {@code Catalog}
 */
public interface StaticCatalog {

    /**
     * @return the {@code Catalog} name
     */
    public String getCatalogName();

    /**
     * @return the date at which this version of {@code Catalog} becomes effective
     */
    public Date getEffectiveDate();

    /**
     * @return an array of supported {@code Currency}
     * @throws CatalogApiException
     */
    public Currency[] getSupportedCurrencies() throws CatalogApiException;

    /**
     * @return an array of supported {@code Product}
     * @throws CatalogApiException
     */
    public Collection<Product> getProducts() throws CatalogApiException;

    /**
     * @return an array of supported {@code Unit}
     * @throws CatalogApiException
     */
    public Unit[] getUnits() throws CatalogApiException;

    /**
     * @return an array of supported {@code Plan}
     * @throws CatalogApiException
     */
    public Collection<Plan> getPlans() throws CatalogApiException;

    /**
     * @param spec      the specification for the {@code Plan} to be used
     * @param overrides the price override for each phase and for a specific currency
     * @return the {@code Plan}
     * @throws CatalogApiException if not such {@code Plan} can be found
     */
    public Plan createOrFindPlan(PlanSpecifier spec, PlanPhasePriceOverridesWithCallContext overrides) throws CatalogApiException;

    // TODO_CATALOG All these exceptions are a result of having our DefaultVersionedCatalog implementation implement both StaticCatalog and Catalog (and are needed for the Catalog case)
    /**
     * @param name the name of the {@Plan}
     * @return the {@code Plan}
     * @throws CatalogApiException if not such {@code Plan} can be found
     */
    public Plan findPlan(String name) throws CatalogApiException;

    /**
     * @param name the name of the {@code Product}
     * @return the {@code Product}
     * @throws CatalogApiException if no such {@code Product} exists
     */
    public Product findProduct(String name) throws CatalogApiException;

    /**
     * @param name the name of the {@code PlanPhase}
     * @return the {@code PlanPhase}
     * @throws CatalogApiException if no such {@code PlanPhase} exists
     */
    public PlanPhase findPhase(String name) throws CatalogApiException;


    public PriceListSet getPriceLists() throws CatalogApiException;

    /**
     * @param name the name of the {@code PriceList}
     * @return the {@code PriceList}
     * @throws CatalogApiException if no such {@code PriceList} exists
     */
    public PriceList findPriceList(String name) throws CatalogApiException;

    /**
     *
     * @return All existing BASE {@code Plan}
     * @throws CatalogApiException
     */
    public List<Listing> getAvailableBasePlanListings() throws CatalogApiException;

    /**
     *
     * @param baseProductName the base {@code Plan}
     * @param priceListName the base {@code PriceList}
     * @return All existing ADD_ON {@code Plan}
     * @throws CatalogApiException
     */
    public List<Listing> getAvailableAddOnListings(String baseProductName, String priceListName) throws CatalogApiException;

    /**
     *
     * @return All the catalog rules
     * @throws CatalogApiException
     */
    public PlanRules getPlanRules() throws CatalogApiException;

}
