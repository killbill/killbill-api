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

public interface PluginConfig {

    public enum PluginType {
        PAYMENT,
        NOTIFICATION,
        INVOICE,
        CURRENCY,
        CATALOG,
        __UNKNOWN__
    }

    public enum PluginLanguage {
        JAVA,
        RUBY
    }

    public String getPluginName();

    public PluginType getPluginType();

    public String getVersion();

    public String getPluginVersionnedName();

    /**
     * @return root directory of the deployed plugin
     */
    public File getPluginVersionRoot();

    public PluginLanguage getPluginLanguage();
}
