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

package com.liferay.util.bridges.php;

import com.caucho.vfs.FilesystemPath;
import com.caucho.vfs.Path;
import com.caucho.vfs.StreamImpl;
import com.caucho.vfs.VfsStream;

import com.liferay.portal.kernel.servlet.ServletContextUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import java.util.Map;

import javax.servlet.ServletContext;

/**
 * @author Raymond Augé
 */
public class ServletContextPath extends FilesystemPath {

	public ServletContextPath(ServletContext servletContext) {
		super(null, StringPool.SLASH, StringPool.SLASH);

		_servletContext = servletContext;

		_root = this;

		try {
			_rootURI = ServletContextUtil.getRootURI(_servletContext);
		}
		catch (MalformedURLException murle) {
			throw new IllegalStateException();
		}

		_useRootURI = true;
	}

	@Override
	public boolean canRead() {
		return true;
	}

	@Override
	public Path fsWalk(
		String userPath, Map<String, Object> newAttributes, String path) {

		String authority = _rootURI.getAuthority();

		if (Validator.isNotNull(authority)) {
			int index = path.indexOf(authority);

			if (index != -1) {
				path = path.substring(index + authority.length());
			}
		}

		return new ServletContextPath(_root, userPath, path, _servletContext);
	}

	@Override
	public String getScheme() {
		if (_useRootURI) {
			return _rootURI.getScheme();
		}

		return "file";
	}

	@Override
	public StreamImpl openReadImpl() throws IOException {
		String path = getPath();

		URL url = _servletContext.getResource(path);

		if (url == null) {
			throw new FileNotFoundException(getFullPath());
		}

		return new VfsStream(url.openStream(), null);
	}

	protected ServletContextPath(
		FilesystemPath root, String userPath, String path,
		ServletContext servletContext) {

		super(root, userPath, path);

		_servletContext = servletContext;

		try {
			_rootURI = ServletContextUtil.getRootURI(_servletContext);
		}
		catch (MalformedURLException murle) {
			throw new IllegalStateException();
		}

		_useRootURI = userPath.startsWith(_rootURI.toString());
	}

	private final URI _rootURI;
	private final ServletContext _servletContext;
	private final boolean _useRootURI;

}