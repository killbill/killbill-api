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

package org.killbill.billing.payment.api;

public enum TransactionStatus {
    /* Success! */
    SUCCESS,
    /* Initial status or plugin does not know (transaction may or not have happenend) */
    UNKNOWN,
    /* The payment transaction is asynchronous and final state will be updated later */
    PENDING,
    /* The payment transaction went through, but came back with an error (for e.g : not enough funds) */
    PAYMENT_FAILURE,
    /* The payment transaction did not go through, and plugin knows for sure that operation was not even attenpted (for e.g: connection error to gateway) */
    PLUGIN_FAILURE,
    /* Payment Subsystem is off */
    PAYMENT_SYSTEM_OFF;
}
