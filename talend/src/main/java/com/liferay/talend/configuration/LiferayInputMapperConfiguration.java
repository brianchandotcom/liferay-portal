/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.talend.configuration;

import java.io.Serializable;

import com.liferay.talend.dataset.RestDataSet;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

/**
 * @author Zoltán Takács
 */
@GridLayout({
    // the generated layout put one configuration entry per line,
    // customize it as much as needed
    @GridLayout.Row({ "restDataSet" }),
    @GridLayout.Row({ "timeout" }),
    @GridLayout.Row({ "maxItemsPerRequest" })
})
@Documentation("TODO fill the documentation for this configuration")
public class LiferayInputMapperConfiguration implements Serializable {
    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private RestDataSet restDataSet;

    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private int timeout;

    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private int maxItemsPerRequest;

    public RestDataSet getRestDataSet() {
        return restDataSet;
    }

    public LiferayInputMapperConfiguration setRestDataSet(RestDataSet restDataSet) {
        this.restDataSet = restDataSet;
        return this;
    }

    public int getTimeout() {
        return timeout;
    }

    public LiferayInputMapperConfiguration setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public int getMaxItemsPerRequest() {
        return maxItemsPerRequest;
    }

    public LiferayInputMapperConfiguration setMaxItemsPerRequest(int maxItemsPerRequest) {
        this.maxItemsPerRequest = maxItemsPerRequest;
        return this;
    }
}