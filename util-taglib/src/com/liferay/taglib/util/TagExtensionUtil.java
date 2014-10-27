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

package com.liferay.taglib.util;

import com.liferay.kernel.servlet.taglib.TagExtension;
import com.liferay.kernel.servlet.taglib.TagExtensionRegistry;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.util.TagKeyResolver;
import com.liferay.taglib.servlet.JspWriterHttpServletResponse;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Carlos Sierra Andrés
 */
public class TagExtensionUtil {

	public static void invokeExtensionAt(
		Object tag, PageContext pageContext, String itemKey,
		boolean ascending) {

		String tagClassName = tag.getClass().getName();

		TagKeyResolver tagResolver = TagExtensionRegistry.getTagIdResolver(
			tagClassName);

		if (tagResolver != null) {
			JspWriterHttpServletResponse wrappedResponse =
				new JspWriterHttpServletResponse(pageContext);

			HttpServletRequest request = (HttpServletRequest)
				pageContext.getRequest();

			String extensionId = tagResolver.getKey(
				request, wrappedResponse, tag);

			if (extensionId != null) {
				String registryKey = extensionId.concat("#").concat(itemKey);

				List<TagExtension> viewExtensions =
					TagExtensionRegistry.getTagExtension(
						tagClassName, registryKey);

				if (viewExtensions != null) {
					Iterator<TagExtension> iterator;

					if (ascending) {
						iterator = viewExtensions.iterator();
					}
					else {
						iterator = ListUtil.reverseIterator(viewExtensions);
					}
					while (iterator.hasNext()) {
						TagExtension tagExtension = iterator.next();

						tagExtension.include(
							request, wrappedResponse, tagClassName, extensionId,
							itemKey);
					}
				}
			}
		}
	}

}