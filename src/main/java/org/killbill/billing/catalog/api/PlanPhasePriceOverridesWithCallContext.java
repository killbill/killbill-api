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

package org.killbill.billing.catalog.api;

import java.util.List;

import org.killbill.billing.util.callcontext.CallContext;

public interface PlanPhasePriceOverridesWithCallContext {

    /**
     *
     * @return the price override definition for the plan
     */
    public List<PlanPhasePriceOverride> getOverrides();

    /**
     *
     * @return the context associated with the call
     */
    public CallContext getCallContext();
}
