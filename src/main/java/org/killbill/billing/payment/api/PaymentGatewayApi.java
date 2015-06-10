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

package org.killbill.billing.payment.api;

import java.util.UUID;

import org.killbill.billing.KillbillApi;
import org.killbill.billing.account.api.Account;
import org.killbill.billing.payment.plugin.api.GatewayNotification;
import org.killbill.billing.payment.plugin.api.HostedPaymentPageFormDescriptor;
import org.killbill.billing.util.callcontext.CallContext;

public interface PaymentGatewayApi extends KillbillApi {

    /**
     * Build metadata for the client to create a redirect form
     *
     * @param account      account
     * @param customFields form fields
     * @param properties   custom properties for the gateway
     * @param context      call context
     * @return redirect form metadata
     * @throws PaymentApiException
     */
    public HostedPaymentPageFormDescriptor buildFormDescriptor(Account account, UUID paymentMethodId, Iterable<PluginProperty> customFields, Iterable<PluginProperty> properties, CallContext context)
            throws PaymentApiException;


    /**
     * Build metadata for the client to create a redirect form
     *
     * @param account      account
     * @param customFields form fields
     * @param properties   custom properties for the gateway
     * @param paymentOptions options to control payment behavior
     * @param context      call context
     * @return redirect form metadata
     * @throws PaymentApiException
     */
    public HostedPaymentPageFormDescriptor buildFormDescriptorWithPaymentControl(Account account, UUID paymentMethodId, Iterable<PluginProperty> customFields, Iterable<PluginProperty> properties, PaymentOptions paymentOptions, CallContext context)
            throws PaymentApiException;

    /**
     * Process a notification from the gateway
     * <p/>
     * This potentially does more than just deserialize the payload. The plugin may have to acknowledge it
     * with the gateway.
     *
     * @param notification serialized notification object
     * @param pluginName   the payment plugin name
     * @param properties   custom properties for the gateway
     * @param context      call context
     * @return gateway notification object used to build the response to the gateway
     * @throws PaymentApiException
     */
    public GatewayNotification processNotification(String notification, String pluginName, Iterable<PluginProperty> properties, CallContext context)
            throws PaymentApiException;


    /**
     * Process a notification from the gateway
     * <p/>
     * This potentially does more than just deserialize the payload. The plugin may have to acknowledge it
     * with the gateway.
     *
     * @param notification serialized notification object
     * @param pluginName   the payment plugin name
     * @param properties   custom properties for the gateway
     * @param paymentOptions options to control payment behavior
     * @param context      call context
     * @return gateway notification object used to build the response to the gateway
     * @throws PaymentApiException
     */
    public GatewayNotification processNotificationWithPaymentControl(String notification, String pluginName, Iterable<PluginProperty> properties, PaymentOptions paymentOptions, CallContext context)
            throws PaymentApiException;
}
