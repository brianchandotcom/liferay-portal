/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.util.ant;

import aQute.lib.osgi.Analyzer;
import aQute.lib.osgi.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import jodd.util.ReflectUtil;

import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;

/**
 * @author Raymond Augé
 */
public class ManifestHelperTask extends Task {

	public void setAnalyze(boolean analyze) {
		_analyze = analyze;
	}

	@Override
	public void execute() throws BuildException {
		try {
			doExecute();
		}
		catch (Exception e) {
			throw new BuildException(e);
		}
	}

	public void setClasspathRef(Reference r) {
		createClasspath().setRefid(r);
	}

	protected void doExecute() throws Exception {
		Project project = getProject();
		File baseDir = project.getBaseDir();

		String os = System.getProperty("os.name").toLowerCase();

		String buildRevision = "";

		File projectDir = new File(
			baseDir, project.getProperty("project.dir"));

		File svnDir = new File(projectDir, ".svn");

		String command = null;

		if (svnDir.exists()) {
			if (os.contains("win")) {
				command = "cmd /c svnversion .";
			}
			else {
				command = "svnversion .";
			}
		}

		File gitDir = new File(projectDir, ".git");

		if (gitDir.exists()) {
			if (os.contains("win")) {
				command = "cmd /c git rev-parse HEAD";
			}
			else {
				command = "git rev-parse HEAD";
			}
		}

		if (command != null) {
			Runtime runtime = Runtime.getRuntime();

			Process process = runtime.exec(command);

			buildRevision = read(process.getInputStream());
		}

		project.setProperty("build.revision", buildRevision);

		AntClassLoader createClassLoader = project.createClassLoader(
			_compileClasspath);

		Class<?> clazz = createClassLoader.loadClass(
			"com.liferay.portal.kernel.util.ReleaseInfo");

		project.setProperty(
			"release.info.code.name", callStatic(clazz, "getCodeName"));
		project.setProperty(
			"release.info.build.date", callStatic(clazz, "getBuildDate"));
		project.setProperty(
			"release.info.build.number",
			callStatic(clazz, "getBuildNumber"));
		project.setProperty(
			"release.info.parent.build.number",
			callStatic(clazz, "getParentBuildNumber"));
		project.setProperty(
			"release.info.release.info",
			callStatic(clazz, "getReleaseInfo"));
		project.setProperty(
			"release.info.server.info", callStatic(clazz, "getServerInfo"));
		project.setProperty(
			"release.info.vendor", callStatic(clazz, "getVendor"));
		project.setProperty(
			"release.info.version", callStatic(clazz, "getVersion"));

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			_defaultFormat);

		project.setProperty(
			"build.time", simpleDateFormat.format(new Date()));

		if (!_analyze) {
			return;
		}

		Analyzer analyzer = new Analyzer();

		analyzer.setBase(baseDir);

		File classesDir = new File(
			baseDir, project.getProperty("module.classes.dir"));

		analyzer.setJar(classesDir);

		File file = new File(baseDir, "bnd.bnd");

		if (file.exists()) {
			analyzer.setProperties(file);
		}
		else {
			analyzer.setProperty(
				Constants.IMPORT_PACKAGE, "*;resolution:=optional");
		}

		Manifest manifest = analyzer.calcManifest();

		Attributes mainAttributes = manifest.getMainAttributes();

		project.setProperty(
			"export.packages",
			mainAttributes.getValue(Constants.EXPORT_PACKAGE));
		project.setProperty(
			"import.packages",
			mainAttributes.getValue(Constants.IMPORT_PACKAGE));
	}

	protected String callStatic(Class<?> clazz, String method)
		throws Exception {

		return String.valueOf(ReflectUtil.invoke(clazz, null, method, null));
	}

	protected Path createClasspath() {
		if (_compileClasspath == null) {
			_compileClasspath = new Path(getProject());
		}

		return _compileClasspath.createPath();
	}

	protected String read(InputStream is) throws IOException {
		StringBuffer sb = new StringBuffer();

		BufferedReader bufferedReader = new BufferedReader(
			new InputStreamReader(is));

		String line = null;

		while ((line = bufferedReader.readLine()) != null) {
			sb.append(line);
			sb.append(_NEW_LINE);
		}

		bufferedReader.close();

		return sb.toString().trim();
	}

	private static final String _defaultFormat = "EEE MMM d HH:mm:ss z yyyy";
	private static final char _NEW_LINE = '\n';

	private boolean _analyze = false;
	private Path _compileClasspath;

}