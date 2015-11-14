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

package org.killbill.billing.util.nodes;

import org.joda.time.DateTime;
import org.killbill.billing.osgi.api.PluginInfo;

public interface NodeInfo {

    public String getNodeName();

    public DateTime getBootTime();

    public DateTime getLastUpdatedDate();

    public String getKillbillVersion();

    public String getApiVersion();

    public String getPlatformVersion();

    public String getCommonVersion();

    public String getPluginApiVersion();

    public Iterable<PluginInfo> getPluginInfo();
}
