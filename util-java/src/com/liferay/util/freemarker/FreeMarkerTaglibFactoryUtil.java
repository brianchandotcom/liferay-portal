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
import com.liferay.portal.kernel.util.StringPool;

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
		for (FreeMarkerTaglibFactoryUtil instance : _instances.values()) {
			instance._templateModels.clear();
		}
	}

	public static void registerCache() {
		FreeMarkerTaglibFactoryUtil portalFreeMarkerTaglibFactoryUtil =
			new FreeMarkerTaglibFactoryUtil();

		_instances.put(StringPool.BLANK, portalFreeMarkerTaglibFactoryUtil);

		CacheRegistryUtil.register(portalFreeMarkerTaglibFactoryUtil);
	}

	private FreeMarkerTaglibFactoryUtil() {
	}

	private static class TaglibFactoryCacheWrapper
		implements TemplateHashModel {

		public TaglibFactoryCacheWrapper(ServletContext servletContext) {
			String namespace = servletContext.getContextPath();

			FreeMarkerTaglibFactoryUtil freeMarkerTaglibFactoryUtil =
				_instances.get(namespace);

			if (freeMarkerTaglibFactoryUtil == null) {
				freeMarkerTaglibFactoryUtil = new FreeMarkerTaglibFactoryUtil();

				_instances.put(namespace, freeMarkerTaglibFactoryUtil);
			}

			_templateModels = freeMarkerTaglibFactoryUtil._templateModels;
			_taglibFactory = new TaglibFactory(servletContext);
		}

		public TemplateModel get(String uri) throws TemplateModelException {
			String key = uri;

			TemplateModel templateModel = _templateModels.get(key);

			if (templateModel == null) {
				templateModel = _taglibFactory.get(uri);

				_templateModels.put(key, templateModel);
			}

			return templateModel;
		}

		public boolean isEmpty() {
			return false;
		}

		private TaglibFactory _taglibFactory;
		private Map<String, TemplateModel> _templateModels;

	}

	private static Map<String, FreeMarkerTaglibFactoryUtil> _instances =
		new ConcurrentHashMap<String, FreeMarkerTaglibFactoryUtil>();

	private Map<String, TemplateModel> _templateModels =
		new ConcurrentHashMap<String, TemplateModel>();

}