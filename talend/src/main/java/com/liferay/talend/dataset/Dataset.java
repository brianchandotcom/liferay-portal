package com.liferay.talend.dataset;

import java.io.Serializable;

import com.liferay.talend.datastore.LiferayDXPConnection;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.type.DataSet;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

@DataSet("Dataset")
@GridLayout({
    // the generated layout put one configuration entry per line,
    // customize it as much as needed
    @GridLayout.Row({ "datastore" })
})
@Documentation("TODO fill the documentation for this configuration")
public class Dataset implements Serializable {
    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private LiferayDXPConnection datastore;

    public LiferayDXPConnection getDatastore() {
        return datastore;
    }

    public Dataset setDatastore(LiferayDXPConnection datastore) {
        this.datastore = datastore;
        return this;
    }
}