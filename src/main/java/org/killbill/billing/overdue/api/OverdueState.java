/*
 * Copyright 2010-2013 Ning, Inc.
 * Copyright 2014 Groupon, Inc
 * Copyright 2014 The Billing Project, LLC
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

package org.killbill.billing.overdue.api;

import org.joda.time.Period;

public interface OverdueState {

    /**
     *
     * @return the condition to evaluate on to decide on the state
     */
    public OverdueCondition getOverdueCondition();

    /**
     *
     * @return the name of the overdue state
     */
    public String getName();

    /**
     *
     * @return the external message associated to the state (user facing)
     */
    public String getExternalMessage();

    /**
     *
     * @return whether the system allows to make plan change on existing subscriptions
     */
    public boolean isBlockChanges();

    /**
     * (Bad name, keep it at API level to be consistent with xml config)
     * @return whether the subscriptions are being paused, which means service (entitlement) will be disabled and billing will also be disabled.
     */
    public boolean isDisableEntitlementAndChangesBlocked();

    /**
     *
     * @return the cancellation policy
     *
     * If set to NONE, no cancellation will be performed when reaching that state
     */
    public OverdueCancellationPolicy getOverdueCancellationPolicy();

    /**
     *
     * @return whether that state is defined as being the 'clear' state.
     */
    public boolean isClearState();

    /**
     *
     * @return the period of time for the system to reevaluate the state after it entered in the given state
     * @throws OverdueApiException
     */
    public Period getAutoReevaluationInterval() throws OverdueApiException;

    /**
     *
     * @return the email notification details
     */
    public EmailNotification getEmailNotification();


}
