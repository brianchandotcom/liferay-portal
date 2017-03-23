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

package com.liferay.support.tomcat.webresources;

import java.io.File;
import java.io.InputStream;

import java.util.Arrays;
import java.util.Set;

import org.apache.catalina.WebResource;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.util.ResourceSet;
import org.apache.catalina.webresources.AbstractFileResourceSet;
import org.apache.catalina.webresources.EmptyResource;
import org.apache.catalina.webresources.FileResource;

/**
 * @author Shuyang Zhou
 */
public class ExtResourceSet extends AbstractFileResourceSet {

	public ExtResourceSet() {
		super("/");
	}

	@Override
	public WebResource getResource(String path) {
		checkPath(path);

		String webAppMount = getWebAppMount();

		WebResourceRoot webResourceRoot = getRoot();

		if (!path.startsWith(webAppMount)) {
			return new EmptyResource(webResourceRoot, path);
		}

		File file = file(path.substring(webAppMount.length()), false);

		if ((file == null) || !file.exists() || file.isDirectory()) {
			return new EmptyResource(webResourceRoot, path);
		}

		return new FileResource(
			webResourceRoot, path, file, true, getManifest());
	}

	@Override
	public String[] list(String path) {
		checkPath(path);

		String webAppMount = getWebAppMount();

		if (!path.equals(webAppMount)) {
			return EMPTY_STRING_ARRAY;
		}

		File extBaseFile = getFileBase();

		if (!extBaseFile.exists()) {
			return EMPTY_STRING_ARRAY;
		}

		String[] extFiles = extBaseFile.list();

		Arrays.sort(extFiles);

		return extFiles;
	}

	@Override
	public Set<String> listWebAppPaths(String path) {
		checkPath(path);

		String webAppMount = getWebAppMount();

		ResourceSet<String> resourceSet = new ResourceSet<>();

		if (path.startsWith(webAppMount)) {
			File extBaseFile = getFileBase();

			File[] files = extBaseFile.listFiles();

			if (files != null) {
				if (path.charAt(path.length() - 1) != '/') {
					path = path.concat("/");
				}

				for (File file : files) {
					if (file.isFile()) {
						resourceSet.add(path.concat(file.getName()));
					}
				}
			}
		}

		resourceSet.setLocked(true);

		return resourceSet;
	}

	@Override
	public boolean mkdir(String path) {
		return false;
	}

	@Override
	public boolean write(String path, InputStream is, boolean overwrite) {
		return false;
	}

	@Override
	protected void checkType(File file) {
		if (file.exists() && !file.isDirectory()) {
			throw new IllegalArgumentException(file + " is not a dirtectory");
		}
	}

}