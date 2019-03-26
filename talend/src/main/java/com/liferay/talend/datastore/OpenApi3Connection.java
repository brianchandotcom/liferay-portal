package com.liferay.talend.datastore;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.type.DataStore;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

import java.io.Serializable;
import java.net.URL;

@DataStore("OpenApi3Connection")
@GridLayout({
    @GridLayout.Row({ "endpointInstanceUrl" }),
    @GridLayout.Row({ "apiKey" })
})
@Documentation("Data store with endpoint URL and authentication for open Api3 endpoint")
public class OpenApi3Connection implements Serializable {

    public URL getEndpointInstanceUrl() {
        return _endpointInstanceUrl;
    }

    public OpenApi3Connection setEndpointInstanceUrl(URL endpoint) {
        _endpointInstanceUrl = endpoint;
        return this;
    }

    public String getApiKey() {
        return _apiKey;
    }

    public OpenApi3Connection setApiKey(String apiKey) {
        _apiKey = apiKey;

        return this;
    }

	@Option("apiKey")
	@Documentation("Api key used for authorization header")
	private String _apiKey;

    @Option("endpointInstanceUrl")
	@Documentation("Endpoint instance URL")
	private URL _endpointInstanceUrl;


}