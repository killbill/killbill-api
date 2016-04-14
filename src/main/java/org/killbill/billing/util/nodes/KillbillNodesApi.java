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

package org.killbill.billing.util.nodes;

import org.killbill.billing.KillbillApi;
import org.killbill.billing.osgi.api.PluginInfo;

public interface KillbillNodesApi extends KillbillApi {


    /**
     * Each killbill instance registers its current state (versions, plugins)
     * when it starts or when changes occur in the system. The api will
     * return that information across all nodes.
     * <p/>
     * @return the list {@code NodeInfo}
     */
    public Iterable<NodeInfo> getNodesInfo();

    /**
     *
     * @return the current {@code NodeInfo}
     */
    public NodeInfo getCurrentNodeInfo();

    /**
     * The api will trigger a command that will be issued on all active cluster nodes
     *
     * @param nodeCommand the command to be triggered across all killbill nodes
     * @param localNodeOnly the command should only triggered on the node where it applied
     */
    public void triggerNodeCommand(NodeCommand nodeCommand, boolean localNodeOnly);

    /**
     * The api is used to notify core killbill system about changes in the plugins (new installed plugin, started, stopped, ...)
     *
     * @param plugin        the info associated to the changed plugin
     * @param latestPlugins the info associated to all plugins
     */
    public void notifyPluginChanged(PluginInfo plugin, Iterable<PluginInfo> latestPlugins);
}
