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

/**
 * The class {@code PlanPhaseSpecifier} specifies the attributes of a {@code PlanPhase}
 */
public class PlanPhaseSpecifier extends PlanSpecifier {

    // The phaseType is used for if/when we need to skipe phases
    private final PhaseType phaseType;

    public PlanPhaseSpecifier(final String productName,
                              final BillingPeriod billingPeriod,
                              final String priceListName,
                              final PhaseType phaseType) {
        super(productName, billingPeriod, priceListName);
        this.phaseType = phaseType;
    }

    public PlanPhaseSpecifier(final String planName,
                              final PhaseType phaseType) {
        super(planName);
        this.phaseType = phaseType;
    }

    /**
     * @return the {@code PhaseType}
     */
    public PhaseType getPhaseType() {
        return phaseType;
    }
}
