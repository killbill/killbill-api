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

package org.killbill.billing.osgi.api.config;

import java.io.File;

public interface PluginConfig extends Comparable<PluginConfig> {

    /**
     * The key that was used at installation time (when installed through KPM)
     * This is also the key from the official KB plugin repo (https://github.com/killbill/killbill-cloud/blob/master/kpm/lib/kpm/plugins_directory.yml)
     */
    public String getPluginKey();

    /**
     *
     * @return the killbill plugin name (as seen on the filesystem)
     */
    public String getPluginName();

    public PluginType getPluginType();

    public String getVersion();

    public String getPluginVersionnedName();

    /**
     * @return root directory of the deployed plugin
     */
    public File getPluginVersionRoot();

    public PluginLanguage getPluginLanguage();

    public boolean isSelectedForStart();

    public boolean isDisabled();
}
