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

package org.killbill.billing.currency.api;

import java.util.Set;

import org.joda.time.DateTime;
import org.killbill.billing.KillbillApi;
import org.killbill.billing.catalog.api.Currency;

public interface CurrencyConversionApi extends KillbillApi {

    /**
     * @return the list of currency that can be used as a base currency for conversion
     * @throws CurrencyConversionException if there is no currency provider
     */
    public Set<Currency> getBaseRates()
            throws CurrencyConversionException;

    /**
     * This will return the latest -- as known by the plugin -- information for the conversion rates.
     *
     * @param baseCurrency the base currency to be used
     * @return the currency list of currency conversion rates
     * @throws CurrencyConversionException if baseCurrency is not supported or if there is no currency provider
     */
    public CurrencyConversion getCurrentCurrencyConversion(Currency baseCurrency)
            throws CurrencyConversionException;

    /**
     * @param baseCurrency   the base currency to be used
     * @param dateConversion the date for the conversion
     * @return the list of currency conversion rates for the date provided
     * @throws CurrencyConversionException if baseCurrency is not supported or if there is no currency provider
     */
    public CurrencyConversion getCurrencyConversion(Currency baseCurrency, DateTime dateConversion)
            throws CurrencyConversionException;
}
