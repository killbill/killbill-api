/*
 * Copyright 2010-2013 Ning, Inc.
 * Copyright 2015-2019 Groupon, Inc
 * Copyright 2015-2019 The Billing Project, LLC
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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PluginNodeCommandMetadata extends DefaultNodeCommandMetadata implements NodeCommandMetadata {

    public static final String PLUGIN_KEY = "pluginKey";
    public static final String PLUGIN_NAME = "pluginName";
    public static final String PLUGIN_VERSION = "pluginVersion";

    private String pluginKey;
    private String pluginName;
    private String pluginVersion;

    // For Jackson
    public PluginNodeCommandMetadata() {
        super();
    }

    @JsonCreator
    public PluginNodeCommandMetadata(@JsonProperty(PLUGIN_KEY) final String pluginKey,
                                     @JsonProperty(PLUGIN_NAME) final String pluginName,
                                     @JsonProperty(PLUGIN_VERSION) final String pluginVersion,
                                     @JsonProperty("properties") final List<NodeCommandProperty> properties) {
        super(properties);
        this.pluginKey = pluginKey;
        this.pluginName = pluginName;
        this.pluginVersion = pluginVersion;
    }

    public String getPluginKey() {
        return pluginKey;
    }

    public String getPluginName() {
        return pluginName;
    }

    public String getPluginVersion() {
        return pluginVersion;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PluginNodeCommandMetadata{");
        sb.append("pluginKey='").append(pluginKey).append('\'');
        sb.append(", pluginName='").append(pluginName).append('\'');
        sb.append(", pluginVersion='").append(pluginVersion).append('\'');
        sb.append(", properties=").append(getProperties());
        sb.append('}');
        return sb.toString();
    }
}
