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

package com.liferay.dynamic.include.factory;

import com.liferay.dynamic.include.factory.configuration.v1.DynamicIncludeConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.dynamic.include.factory.configuration.v1.DynamicIncludeConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	service = DynamicInclude.class
)
public class DynamicIncludeFactory implements DynamicInclude {

	@Activate
	public DynamicIncludeFactory(Map<String, Object> properties) {
		_dynamicIncludeConfiguration = ConfigurableUtil.createConfigurable(
			DynamicIncludeConfiguration.class, properties);

		Instant now = Instant.now();

		List<String> urlList = new ArrayList<>();

		for (String url : _dynamicIncludeConfiguration.urls()) {
			if (url.indexOf('?') > -1) {
				url += "&t=".concat(String.valueOf(now.toEpochMilli()));
			}
			else {
				url += "?t=".concat(String.valueOf(now.toEpochMilli()));
			}

			urlList.add(url);
		}

		_urls = urlList;
	}

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		if (!_urls.isEmpty()) {
			PrintWriter printWriter = httpServletResponse.getWriter();

			for (String url : _urls) {
				if (url.indexOf(".js") > -1) {
					printWriter.println(
						StringBundler.concat(
							"<script charset=\"",
							_dynamicIncludeConfiguration.charset(),
							"\" data-senna-track=\"temporary\" src=\"", url,
							"\" type=\"text/javascript\"></script>"
						));
				}
				else if (url.indexOf(".css") > -1) {
					printWriter.println(
						StringBundler.concat(
							"<link charset=\"",
							_dynamicIncludeConfiguration.charset(),
							" data-senna-track=\"temporary\" href=\"", url,
							"\" rel=\"stylesheet\" type=\"text/css\"/>"
						));
				}
			}

			printWriter.flush();
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		// css -> /html/common/themes/top_head.jsp#post
		// js ->  /html/common/themes/top_js.jspf#resources
		dynamicIncludeRegistry.register(_dynamicIncludeConfiguration.key());
	}

	private final DynamicIncludeConfiguration _dynamicIncludeConfiguration;
	private final List<String> _urls;

}