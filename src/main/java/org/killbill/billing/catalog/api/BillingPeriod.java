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

package org.killbill.billing.catalog.api;

import org.joda.time.Period;

/**
 * The {@code BillingPeriod} supported in the system
 */
public enum BillingPeriod {

    DAILY(Period.days(1)),
    WEEKLY(Period.weeks(1)),
    BIWEEKLY(Period.weeks(2)),
    THIRTY_DAYS(Period.days(30)),
    THIRTY_ONE_DAYS(Period.days(31)),
    SIXTY_DAYS(Period.days(60)),
    NINETY_DAYS(Period.days(90)),
    MONTHLY(Period.months(1)),
    BIMESTRIAL(Period.months(2)),
    QUARTERLY(Period.months(3)),
    TRIANNUAL(Period.months(4)),
    BIANNUAL(Period.months(6)),
    ANNUAL(Period.years(1)),
    SESQUIENNIAL(Period.months(18)),
    BIENNIAL(Period.years(2)),
    TRIENNIAL(Period.years(3)),
    NO_BILLING_PERIOD(Period.ZERO);

    private final Period period;

    BillingPeriod(final Period period) {
        this.period = period;
    }

    public Period getPeriod() {
        return period;
    }
}
