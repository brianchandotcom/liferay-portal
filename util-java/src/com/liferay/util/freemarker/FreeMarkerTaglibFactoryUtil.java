/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.util.freemarker;

import com.liferay.portal.kernel.cache.CacheRegistryItem;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;

import freemarker.ext.jsp.TaglibFactory;

import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

/**
 * @author Shuyang Zhou
 */
public class FreeMarkerTaglibFactoryUtil implements CacheRegistryItem {

	public static TemplateHashModel createTaglibFactory(
		ServletContext servletContext) {

		return new TaglibFactoryCacheWrapper(servletContext);
	}

	public String getRegistryName() {
		return FreeMarkerTaglibFactoryUtil.class.getName();
	}

	public void invalidate() {
		_taglibCache.clear();
	}

	private FreeMarkerTaglibFactoryUtil() {
	}

	private static class TaglibFactoryCacheWrapper
		implements TemplateHashModel {

		public TaglibFactoryCacheWrapper(ServletContext servletContext) {
			_nameSpace = servletContext.getContextPath();
			_taglibFactory = new TaglibFactory(servletContext);
		}

		public TemplateModel get(String uri) throws TemplateModelException {
			String key = uri;

			if (_nameSpace.length() > 0) {
				key = _nameSpace.concat(uri);
			}

			TemplateModel taglib = _taglibCache.get(key);

			if (taglib == null) {
				taglib = _taglibFactory.get(uri);

				_taglibCache.put(key, taglib);
			}

			return taglib;
		}

		public boolean isEmpty() throws TemplateModelException {
			return false;
		}

		private String _nameSpace;
		private TaglibFactory _taglibFactory;

	}

	private static Map<String, TemplateModel> _taglibCache =
		new ConcurrentHashMap<String, TemplateModel>();

	static {
		CacheRegistryUtil.register(new FreeMarkerTaglibFactoryUtil());
	}

}