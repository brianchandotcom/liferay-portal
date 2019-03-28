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

/**
 * @author Igor Beslic
 */
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
