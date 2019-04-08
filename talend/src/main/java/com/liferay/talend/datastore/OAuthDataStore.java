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

package com.liferay.talend.datastore;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.action.Checkable;
import org.talend.sdk.component.api.configuration.type.DataStore;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

import java.io.Serializable;

@DataStore("OAuthDataStore")
@GridLayout(
    names = GridLayout.FormType.MAIN,
    value =
    {
    @GridLayout.Row({ "serverURL" }),
    @GridLayout.Row({ "consumerKey", "consumerSecret"}),
})
@Documentation("TODO fill the documentation for this configuration")
public class OAuthDataStore implements Serializable {

    public java.net.URL getServerURL() {
        return serverURL;
    }

    public OAuthDataStore setServerURL(java.net.URL serverURL) {
        this.serverURL = serverURL;
        return this;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public OAuthDataStore setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;

        return this;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public OAuthDataStore setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;

        return this;
    }

	@Option
	@Documentation("TODO fill the documentation for this parameter")
	private java.net.URL serverURL;

	@Option
	@Documentation("TODO fill the documentation for this parameter")
	private String consumerKey;

	@Option
	@Documentation("TODO fill the documentation for this parameter")
	private String consumerSecret;

}