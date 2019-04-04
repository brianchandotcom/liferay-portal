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

import java.io.Serializable;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.type.DataStore;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.configuration.ui.widget.Credential;
import org.talend.sdk.component.api.meta.Documentation;

@DataStore("BasicDataStore")
@GridLayout(
    names = GridLayout.FormType.MAIN,
    value =
    {
    @GridLayout.Row({ "authorization" }),
    @GridLayout.Row({ "serverURL", "anonymous" }),
    @GridLayout.Row({ "user", "password"}),
})
@Documentation("TODO fill the documentation for this configuration")
public class BasicDataStore implements Serializable {

    @Option
    @Documentation("TODO fill the documentation for this parameter")
    Authorization authorization;

    enum Authorization {
        Basic,
        OAuth2
    }

    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private java.net.URL serverURL;

    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private boolean anonymous;

    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private String user;

    @Credential
    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private String password;

    public java.net.URL getServerURL() {
        return serverURL;
    }

    public BasicDataStore setServerURL(java.net.URL serverURL) {
        this.serverURL = serverURL;
        return this;
    }

    public boolean getAnonymous() {
        return anonymous;
    }

    public BasicDataStore setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
        return this;
    }

    public String getUser() {
        return user;
    }

    public BasicDataStore setUser(String user) {
        this.user = user;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public BasicDataStore setPassword(String password) {
        this.password = password;
        return this;
    }
}