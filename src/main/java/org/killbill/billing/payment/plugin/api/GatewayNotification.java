/*
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

package org.killbill.billing.payment.plugin.api;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.killbill.billing.payment.api.PluginProperty;

public interface GatewayNotification {

    /**
     * @return the id in Kill Bill
     */
    public UUID getKbPaymentId();

    /**
     * Get the status code associated with the response to the gateway
     *
     * @return the response status code or -1 if the status was not set
     */
    public int getStatus();

    /**
     * Get the serialized response entity returned to the gateway
     *
     * @return an object instance or null if there is no entity
     */
    public String getEntity();

    /**
     * Get the headers to return to the gateway
     *
     * @return map of header key / values
     */
    public Map<String, List<String>> getHeaders();

    /**
     * @return the list of custom properties set by the plugin
     */
    public List<PluginProperty> getProperties();
}
