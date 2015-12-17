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

package org.killbill.billing.osgi.api;

import org.killbill.billing.KillbillApi;
import org.killbill.billing.osgi.api.config.PluginLanguage;

public interface PluginsInfoApi extends KillbillApi {

    /**
     *
     * @return the list of plugins as seen by the OSGI registry
     */
    public Iterable<PluginInfo> getPluginsInfo();

    /**
     * Notify OSGI component that a new plugin was installed (downloaded) on the file system.
     *
     * @param newState       the state (currently only {@code NEW_VERSION} is allowed
     * @param pluginKey      the plugin key (used during install/uninstall time)
     * @param pluginName     the name of the plugin (as seen of the filesystem)
     * @param pluginVersion  the version of the plugin
     * @param pluginLanguage the language (JAVA or RUBY)
     */
    public void notifyOfStateChanged(PluginStateChange newState, String pluginKey, String pluginName, String pluginVersion, PluginLanguage pluginLanguage);
}
