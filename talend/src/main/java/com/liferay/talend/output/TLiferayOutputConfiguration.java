package com.liferay.talend.output;

import java.io.Serializable;

import com.liferay.talend.dataset.Dataset;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

@GridLayout({
    // the generated layout put one configuration entry per line,
    // customize it as much as needed
    @GridLayout.Row({ "dataset" }),
    @GridLayout.Row({ "timeout" }),
    @GridLayout.Row({ "maxItemsPerRequest" })
})
@Documentation("TODO fill the documentation for this configuration")
public class TLiferayOutputConfiguration implements Serializable {
    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private Dataset dataset;

    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private int timeout;

    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private int maxItemsPerRequest;

    public Dataset getDataset() {
        return dataset;
    }

    public TLiferayOutputConfiguration setDataset(Dataset dataset) {
        this.dataset = dataset;
        return this;
    }

    public int getTimeout() {
        return timeout;
    }

    public TLiferayOutputConfiguration setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public int getMaxItemsPerRequest() {
        return maxItemsPerRequest;
    }

    public TLiferayOutputConfiguration setMaxItemsPerRequest(int maxItemsPerRequest) {
        this.maxItemsPerRequest = maxItemsPerRequest;
        return this;
    }
}