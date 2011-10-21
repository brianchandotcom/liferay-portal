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

package com.liferay.maven.plugins;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.tools.LangBuilder;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.HttpImpl;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * @author Mika Koivisto
 * @goal   build-lang
 */
public class LangBuilderMojo extends AbstractMojo {

	public void execute() throws MojoExecutionException {
		try {
			initPortal();

			doExecute();
		}
		catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	protected void doExecute() throws Exception {
		new LangBuilder(langDir, langFile, langPlugin, langTranslate);
	}

	protected void initPortal() {
		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		HttpUtil httpUtil = new HttpUtil();

		httpUtil.setHttp(new HttpImpl());
	}

	/**
	 * @parameter default-value="${basedir}/src/main/resources/content" expression="${langDir}"
	 * @required
	 */
	private String langDir;

	/**
	 * @parameter default-value="Language" expression="${langFile}"
	 */
	private String langFile;

	/**
	 * @parameter default-value="true" expression="${langPlugin}"
	 */
	private boolean langPlugin;

	/**
	 * @parameter default-value="true" expression="${langTranslate}"
	 */
	private boolean langTranslate;

}