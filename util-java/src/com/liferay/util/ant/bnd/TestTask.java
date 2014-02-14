/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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

import aQute.bnd.build.Project;
import aQute.bnd.build.ProjectTester;

import java.io.File;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.tools.ant.BuildException;

/**
 * @author Raymond Augé
 */
public class TestTask extends BaseBndTask {

	public void setContinuous(boolean continuous) {
		_continuous = continuous;
	}

	public void setDir(File dir) {
		_dir = dir;
	}

	public void setRunfiles(String runFiles) {
		_runFiles = runFiles;
	}

	@Override
	protected void doExecute() throws Exception {
		File baseDir = project.getBaseDir();

		Project bndProject = getBndProject();

		try {
			List<Project> projects;

			if (_runFiles == null) {
				projects = Collections.singletonList(bndProject);
			}
			else {
				StringTokenizer tokenizer = new StringTokenizer(_runFiles, ",");

				projects = new LinkedList<Project>();

				while (tokenizer.hasMoreTokens()) {
					String runFilePath = tokenizer.nextToken().trim();

					Project runProject;

					if (".".equals(runFilePath)) {
						runProject = bndProject;
					}
					else {
						File runFile = new File(baseDir, runFilePath);

						if (!runFile.isFile()) {
							throw new BuildException(
								String.format(
									"Run file %s does not exist (or is not a " +
										"file).",
								runFile.getAbsolutePath()));
						}

						runProject = new Project(
							bndProject.getWorkspace(), baseDir, runFile);

						runProject.setParent(bndProject);
					}

					projects.add(runProject);
				}
			}

			for (Project project : projects) {
				project.clear();
			}

			for (Project project : projects) {
				executeProject(project);
			}
		}
		catch (Exception e) {
			throw new BuildException(e);
		}
	}

	private void executeProject(Project project) throws Exception {
		this.project.log("Testing " + project.getPropertiesFile());

		ProjectTester tester = project.getProjectTester();

		tester.setContinuous(_continuous);

		if (_dir != null) {
			tester.setCwd(_dir);
		}

		String testerDir = project.getProperty("tester.dir", "test-reports");

		File reportDir = new File(project.getBase(), testerDir);

		tester.setReportDir(reportDir);

		tester.prepare();

		if (report(project)) {
			throw new BuildException("Failed to initialise for testing.");
		}

		int errors = tester.test();

		if (errors == 0) {
			this.project.log("All tests passed");
		}
		else {
			if (errors > 0) {
				this.project.log(
					errors + " Error(s)", org.apache.tools.ant.Project.MSG_ERR);
			}
			else {
				this.project.log(
					"Error " + errors, org.apache.tools.ant.Project.MSG_ERR);
			}

			throw new BuildException("Tests failed");
		}

		if (report(project)) {
			throw new BuildException("Tests failed");
		}
	}

	private boolean	_continuous	= false;
	private String	_runFiles	= null;
	private File _dir = null;

}