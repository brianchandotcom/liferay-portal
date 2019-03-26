package com.liferay.talend.dataset;

import com.liferay.talend.datastore.OpenApi3Connection;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.type.DataSet;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

import java.io.Serializable;

@DataSet("OpenApi3DataSet")
@GridLayout({
    @GridLayout.Row({ "_openApi3Connection" })
})
@Documentation("Additional Open Api 3 properties and directives needed for parser services")
public class OpenApi3DataSet implements Serializable {

    public OpenApi3Connection getDataStore() {
        return _openApi3Connection;
    }

    public OpenApi3DataSet setDataStore(OpenApi3Connection dataStore) {
        _openApi3Connection = dataStore;

        return this;
    }

	@Option("_openApi3Connection")
	@Documentation("Connection configuration for Swagger Hub")
	private OpenApi3Connection _openApi3Connection;

}