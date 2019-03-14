package com.liferay.talend.datastore;

import java.io.Serializable;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.type.DataStore;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.configuration.ui.widget.Credential;
import org.talend.sdk.component.api.meta.Documentation;

@DataStore("LiferayDXPConnection")
@GridLayout({
    // the generated layout put one configuration entry per line,
    // customize it as much as needed
    @GridLayout.Row({ "authorization" }),
    @GridLayout.Row({ "serverURL" }),
    @GridLayout.Row({ "anonymous" }),
    @GridLayout.Row({ "user" }),
    @GridLayout.Row({ "password" })
})
@Documentation("TODO fill the documentation for this configuration")
public class LiferayDXPConnection implements Serializable {
    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private String authorization;

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

    public String getAuthorization() {
        return authorization;
    }

    public LiferayDXPConnection setAuthorization(String authorization) {
        this.authorization = authorization;
        return this;
    }

    public java.net.URL getServerURL() {
        return serverURL;
    }

    public LiferayDXPConnection setServerURL(java.net.URL serverURL) {
        this.serverURL = serverURL;
        return this;
    }

    public boolean getAnonymous() {
        return anonymous;
    }

    public LiferayDXPConnection setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
        return this;
    }

    public String getUser() {
        return user;
    }

    public LiferayDXPConnection setUser(String user) {
        this.user = user;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LiferayDXPConnection setPassword(String password) {
        this.password = password;
        return this;
    }
}