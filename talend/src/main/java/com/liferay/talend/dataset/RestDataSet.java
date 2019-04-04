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

package com.liferay.talend.dataset;

import java.io.Serializable;

import com.liferay.talend.datastore.BasicDataStore;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.action.Suggestable;
import org.talend.sdk.component.api.configuration.constraint.Required;
import org.talend.sdk.component.api.configuration.type.DataSet;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

/**
 * @author Zoltán Takács
 */
@DataSet("RestDataSet")
@GridLayout({
    @GridLayout.Row({ "dataStore" }),
    @GridLayout.Row({ "moduleName" })
})
@Documentation("TODO fill the documentation for this configuration")
public class RestDataSet implements Serializable {
    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private BasicDataStore dataStore;

    @Option
    @Required
    @Suggestable(value = "loadSalesforceModules", parameters = { "dataStore" })
    @Documentation("Endpoints")
    private String moduleName;

    public BasicDataStore getDataStore() {
        return dataStore;
    }

    public RestDataSet setDataStore(BasicDataStore dataStore) {
        this.dataStore = dataStore;
        return this;
    }
}