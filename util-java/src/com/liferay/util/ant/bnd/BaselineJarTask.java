/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.util.ant.bnd;


import aQute.bnd.build.ProjectBuilder;
import aQute.bnd.differ.Baseline;
import aQute.bnd.differ.Baseline.BundleInfo;
import aQute.bnd.differ.Baseline.Info;
import aQute.bnd.differ.DiffPluginImpl;
import aQute.bnd.osgi.Builder;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.Jar;
import aQute.bnd.service.diff.Delta;
import aQute.bnd.service.diff.Diff;
import aQute.bnd.version.Version;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Path;

/**
 * @author Raymond Augé
 */
public class BaselineJarTask extends BaseBndTask {

	@Override
	public void addClasspath(Path classpath) {
		_classpath = classpath;
	}

	public void setFile(File file) {
		_file = file;
	}

	public void setOutputPath(File outputPath) {
		_outputPath = outputPath;
	}

	public void setSourcePath(File sourcePath) {
		_sourcePath = sourcePath;
	}

	@Override
	public void trace(String format, Object... args) {
		// This is to silence the default trace output of the super class
	}

	@Override
	protected void doBeforeExecute() throws Exception {
		super.doBeforeExecute();

		File bndRootFile = getBndRootFile();

		File rootDir = bndRootFile.getParentFile();

		if (_classpath == null) {
			throw new BuildException("classpath is null");
		}

		if ((_file == null) || !_file.exists() || _file.isDirectory()) {
			if (_file != null) {
				_project.log(
					"file is either missing or is a directory " +
						_file.getAbsolutePath(),
					Project.MSG_ERR);
			}

			throw new BuildException("file is not set correctly");
		}

		if ((_outputPath == null) || !_outputPath.exists() ||
			!_outputPath.isDirectory()) {

			if (_outputPath != null) {
				_project.log(
					"outputpath is either missing or is not a directory " +
						_outputPath.getAbsolutePath(),
					Project.MSG_ERR);
			}

			throw new BuildException("outputPath is not set correctly");
		}

		_reportLevel = _project.getProperty("baseline.jar.report.level");

		_reportLevelIsDiff = "diff".equals(_reportLevel);
		_reportLevelIsOff = "off".equals(_reportLevel);
		_reportLevelIsPersist = "persist".equals(_reportLevel);
		_reportLevelIsStandard = "standard".equals(_reportLevel);

		if (_reportLevelIsPersist) {
			_reportLevelIsDiff = true;

			File baselineReportsDir = new File(
				rootDir, getBaselineResportsDirName());

			if (!baselineReportsDir.exists() && !baselineReportsDir.mkdir()) {
				throw new BuildException(
					"could not create " + baselineReportsDir.getName());
			}

			_logFile = new File(
				baselineReportsDir, _outputPath.getName() + ".log");

			if (_logFile.exists()) {
				_logFile.delete();
			}
		}

		if ((_sourcePath == null) || !_sourcePath.exists() ||
			!_sourcePath.isDirectory()) {

			if (_sourcePath != null) {
				_project.log(
					"sourcepath is either missing or is not a directory " +
						_sourcePath.getAbsolutePath(),
					Project.MSG_ERR);
			}

			throw new BuildException("sourcePath is not set correctly");
		}

		for (String fileName : _classpath.list()) {
			_classpathFiles.add(new File(fileName.replace('\\', '/')));
		}

		_bndDir = new File(rootDir, getBndDirName());

		if (!rootDir.canWrite()) {
			return;
		}

		File buildFile = new File(_bndDir, "build.bnd");

		if (!_bndDir.exists() && !_bndDir.mkdir()) {
			return;
		}

		if (buildFile.exists() || !_bndDir.canWrite()) {
			return;
		}

		BufferedWriter out = new BufferedWriter(new FileWriter(buildFile));

		for (String line : _BUILD_DEFAULTS) {
			out.write(line);
			out.newLine();
		}

		out.close();

		File baselineRepoDir = new File(_bndDir, "baselinerepo");

		if (!baselineRepoDir.exists()) {
			baselineRepoDir.mkdir();
		}
	}

	private void doBaselineJar(
			Jar jar, File output, aQute.bnd.build.Project bndProject)
		throws Exception {

		if (_reportLevelIsOff) {
			return;
		}

		ProjectBuilder projectbuilder = new ProjectBuilder(bndProject);

		Jar baselineJar = projectbuilder.getBaselineJar();

		try {
			if (baselineJar == null) {
				String name = bndProject.getProperty(Constants.BASELINEREPO);

				bndProject.deploy(name, output);

				return;
			}

			Baseline baseline = new Baseline(this, _differ);

			Set<Info> infos = baseline.baseline(jar, baselineJar, null);

			if (infos.isEmpty()) {
				return;
			}

			BundleInfo bundleInfo = baseline.getBundleInfo();

			Info[] array = infos.toArray(new Info[infos.size()]);

			Arrays.sort(
				array, new Comparator<Info>() {

					public int compare(Info info1, Info info2) {
						return info1.packageName.compareTo(info2.packageName);
					}

				}
			);

			for (Info info : array) {
				Diff packageDiff = info.packageDiff;
				Delta delta = packageDiff.getDelta();
				String warnings = "-";
				Version newerVersion = info.newerVersion;
				Version suggestedVersion = info.suggestedVersion;

				if (suggestedVersion != null) {
					if (newerVersion.compareTo(suggestedVersion) > 0) {
						warnings = "EXCESSIVE VERSION INCREASE";
					}
					else if (newerVersion.compareTo(suggestedVersion) < 0) {
						warnings = "VERSION INCREASE REQUIRED";
					}
				}

				if (delta == Delta.REMOVED) {
					warnings = "PACKAGE REMOVED";
				}
				else if (delta == Delta.UNCHANGED) {
					boolean newVersionSuggested = false;

					if ((suggestedVersion.getMajor() !=
							newerVersion.getMajor()) ||
						(suggestedVersion.getMicro() !=
							newerVersion.getMicro()) ||
						(suggestedVersion.getMinor() !=
							newerVersion.getMinor())) {

						newVersionSuggested = true;
						warnings = "VERSION INCREASE SUGGESTED";
					}

					if (!newVersionSuggested && !info.mismatch) {
						continue;
					}
				}

				if (_reportLevelIsStandard && warnings.equals("-")) {
					continue;
				}

				doInfo(bundleInfo, info, warnings);

				if (_reportLevelIsDiff && (delta != Delta.REMOVED)) {
					doPackageDiff(packageDiff);
				}
			}
		}
		finally {
			if (baselineJar != null) {
				baselineJar.close();
			}

			if (_printWriter != null) {
				_printWriter.close();
			}

			projectbuilder.close();
		}
	}

	private void doDiff(Diff diff, StringBuffer sb) {
		String output = String.format(
			"%s%-3s %-10s %s", sb, getShortDelta(diff.getDelta()),
			diff.getType().toString().toLowerCase(), diff.getName());

		_project.log(output, Project.MSG_WARN);

		if (_printWriter != null) {
			_printWriter.println(output);
		}

		sb.append("\t");

		for (Diff curDiff : diff.getChildren()) {
			if (curDiff.getDelta() == Delta.UNCHANGED) {
				continue;
			}

			doDiff(curDiff, sb);
		}

		sb.deleteCharAt(sb.length() - 1);
	}

	@Override
	protected void doExecute() throws Exception {
		aQute.bnd.build.Project bndProject = getBndProject();

		Builder builder = new Builder(bndProject);

		builder.setClasspath(
			_classpathFiles.toArray(new File[_classpathFiles.size()]));
		builder.setPedantic(isPedantic());
		builder.setProperties(_file);
		builder.setSourcepath(new File[] {_sourcePath});

		Jar jars[] = builder.builds();

		// Report both task failures and bnd build failures.

		boolean taskFailed = report();
		boolean bndFailed = report(builder);

		// Fail this build if failure is not ok and either the task
		// failed or the bnd build failed.

		if (taskFailed || bndFailed) {
			throw new BuildException(
				"bnd failed",
				new org.apache.tools.ant.Location(
					_file.getAbsolutePath()));
		}

		for (Jar jar : jars) {
			String bsn = jar.getName();

			File output = _outputPath;

			String path = builder.getProperty("-output");

			if (path == null) {
				output = getFile(_outputPath, bsn + ".jar");
			}
			else {
				output = getFile(_outputPath, path);
			}

			if (!output.exists() ||
				(output.lastModified() <= jar.lastModified())) {

				jar.write(output);

				log(
					jar.getName() + " (" + output.getName() + ") " +
						jar.getResources().size());

				doBaselineJar(jar, output, bndProject);
			}
			else {
				log(
					jar.getName() + " (" + output.getName() + ") " +
						jar.getResources().size() + " (not modified)");
			}

			report();

			jar.close();
		}

		builder.close();
	}

	private void doHeader(BundleInfo bundleInfo) {
		if (_headerPrinted) {
			return;
		}

		_headerPrinted = true;

		try {
			_logFile.createNewFile();

			_printWriter = new PrintWriter(_logFile);
		}
		catch (IOException ioe) {
			throw new BuildException(ioe);
		}

		_project.log(
			"[Baseline Report]  Mode: " + _reportLevel, Project.MSG_WARN);

		if (bundleInfo.mismatch) {
			_project.log(
				"[Baseline Warning] Bundle Version Change Recommended: " +
					bundleInfo.suggestedVersion,
				Project.MSG_WARN);
		}

		reportLog(
			" ", "PACKAGE_NAME", "DELTA", "CUR_VER", "BASE_VER",
			"REC_VER", "WARNINGS", "ATTRIBUTES");

		reportLog(
			"=", "==================================================",
			"==========", "==========", "==========", "==========",
			"==========", "==========");
	}

	private void doInfo(BundleInfo bundleInfo, Info info, String warnings) {
		doHeader(bundleInfo);

		reportLog(
				String.valueOf(info.mismatch ? '*' : ' '),
				info.packageName,
				String.valueOf(info.packageDiff.getDelta()),
				String.valueOf(info.newerVersion),
				String.valueOf(info.olderVersion),
				String.valueOf(
					info.suggestedVersion == null ? "-" :
						info.suggestedVersion), warnings,
				String.valueOf(info.attributes));
	}

	private void doPackageDiff(Diff diff) {
		StringBuffer sb = new StringBuffer();

		sb.append("\t");

		for (Diff curDiff : diff.getChildren()) {
			if (curDiff.getDelta() == Delta.UNCHANGED) {
				continue;
			}

			doDiff(curDiff, sb);
		}
	}

	private String getBaselineResportsDirName() {
		if (_baselineResportsDirName != null) {
			return _baselineResportsDirName;
		}

		_baselineResportsDirName = _project.getProperty(
			"baseline.jar.reports.dir.name");

		if (_baselineResportsDirName == null) {
			_baselineResportsDirName = _BASELINE_REPORTS_DIR;
		}

		return _baselineResportsDirName;
	}

	private String getShortDelta(Delta delta) {
		if (delta == Delta.ADDED) {
			return "+";
		}
		else if (delta == Delta.CHANGED) {
			return "~";
		}
		else if (delta == Delta.MAJOR) {
			return ">";
		}
		else if (delta == Delta.MICRO) {
			return "0xB5";
		}
		else if (delta == Delta.MINOR) {
			return "<";
		}
		else if (delta == Delta.REMOVED) {
			return "-";
		}

		return String.valueOf(delta.toString().charAt(0));
	}

	private void reportLog(
		String string1, String string2, String string3, String string4,
		String string5, String string6, String string7, String string8) {

		String output = String.format(
			"%s %-50s %-10s %-10s %-10s %-10s %-10s", string1, string2,
			string3, string4, string5, string6, string7);

		_project.log(output, Project.MSG_WARN);

		if (_printWriter != null) {
			_printWriter.println(output);
		}
	}

	private final String[] _BUILD_DEFAULTS = new String[] {
		"-plugin: aQute.bnd.deployer.obr.LocalOBR;name=baselinerepo;" +
			"mode=build;local=${workspace}/.bnd/baselinerepo",
		"-pluginpath: ${workspace}/osgi/lib/plugin/bnd-repository.jar",
		"-baseline: ${ant.project.name}",
		"-baselinerepo: baselinerepo",
		"-releaserepo: baselinerepo"
	};

	private static final String _BASELINE_REPORTS_DIR = "baseline-reports";

	private final DiffPluginImpl _differ = new DiffPluginImpl();

	private String _baselineResportsDirName;
	private File _bndDir;
	private Path _classpath;
	private List<File> _classpathFiles = new ArrayList<File>();
	private File _file;
	private boolean _headerPrinted = false;
	private File _logFile;
	private File _outputPath;
	private PrintWriter _printWriter;
	private String _reportLevel;
	boolean _reportLevelIsOff = true;
	boolean _reportLevelIsStandard = false;
	boolean _reportLevelIsDiff = false;
	boolean _reportLevelIsPersist = false;
	private File _sourcePath;

}