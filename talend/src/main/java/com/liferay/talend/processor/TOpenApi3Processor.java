package com.liferay.talend.processor;

import com.liferay.talend.dataset.OpenApi3DataSet;
import org.talend.sdk.component.api.component.Icon;
import org.talend.sdk.component.api.component.Version;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.meta.Documentation;
import org.talend.sdk.component.api.processor.ElementListener;
import org.talend.sdk.component.api.processor.Processor;

import javax.json.JsonObject;
import java.io.Serializable;


@Documentation("This component prints received open api specification")
@Icon(value = Icon.IconType.CHARTS)
@Processor(name = "tOpenApi3Processor")
@Version(1)
public class TOpenApi3Processor implements Serializable {

	public TOpenApi3Processor(
		@Option("configuration")OpenApi3DataSet openApi3DataSet) {

		_openApi3DataSet = openApi3DataSet;
	}

	@ElementListener
	public void filterPaths(JsonObject openApiJsonObject) {
		System.out.println("[" + this + "] Filtering started");

		OpenApiPrinter.print(openApiJsonObject);

		System.out.println("[" + this + "] Filtering finished");
	}

	private OpenApi3DataSet _openApi3DataSet;

}
