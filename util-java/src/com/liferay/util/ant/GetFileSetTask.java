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

package com.liferay.util.ant;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.DirSet;
import org.apache.tools.ant.types.FileSet;

/**
 * @author Lily Chi
 */
public class GetFileSetTask extends Task {

	@Override
	public void execute() throws BuildException {
		File baseDir = new File(_rootDir);

		Project project = getProject();

		List<String> classNames = new ArrayList<>();

		Collections.addAll(classNames, _classNames.split(","));

		List<Path> classResultList = new ArrayList();
		List<Path> srcResultList = new ArrayList();

		Set<String> notFoundClassNames = findFiles(
			baseDir, classNames, classResultList, srcResultList);

		if (!notFoundClassNames.isEmpty()) {
			_LOGGER.log(
				Level.WARNING, "Class files for {0} were not found!",
				notFoundClassNames.toString());

			if (classResultList.isEmpty()) {
				return;
			}
		}

		DirSet srcDirSet = new DirSet();

		srcDirSet.setProject(getProject());
		srcDirSet.setDir(baseDir);

		for (Path srcFilePath : srcResultList) {
			String srcResult = String.valueOf(srcFilePath);

			int srcDirIndex = srcResult.indexOf("src") + 3;

			if (srcResult.contains(_MODULE_SRC_PARAMETER)) {
				srcDirIndex = srcResult.indexOf(_MODULE_SRC_PARAMETER) + 13;
			}

			srcResult = srcResult.substring(_rootDir.length() + 1, srcDirIndex);

			srcDirSet.setIncludes(srcResult);
		}

		project.addReference("srcSet", srcDirSet);

		FileSet classFileSet = new FileSet();

		classFileSet.setProject(getProject());
		classFileSet.setDir(baseDir);

		for (Path classFilePath : classResultList) {
			String filePath = String.valueOf(classFilePath);

			filePath = filePath.substring(_rootDir.length() + 1);

			classFileSet.setIncludes(filePath);
		}

		project.addReference("classSet", classFileSet);
	}

	public Set<String> findFiles(
		File baseDir, List<String> classNames, List<Path> classFileList,
		List<Path> srcFileList) {

		if (!baseDir.exists() || !baseDir.isDirectory()) {
			throw new BuildException();
		}

		Set<String> notFoundClassNames = new HashSet<>();

		notFoundClassNames.addAll(classNames);

		try {
			Files.walkFileTree(
				baseDir.toPath(),
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(
							Path file, BasicFileAttributes attrs)
						throws IOException {

						Path fileNamePath = file.getFileName();

						String fileName = fileNamePath.toString();

						if (_skipDirectory(fileName)) {
							return FileVisitResult.SKIP_SUBTREE;
						}
						else {
							return FileVisitResult.CONTINUE;
						}
					}

					@Override
					public FileVisitResult visitFile(
							Path file, BasicFileAttributes attrs)
						throws IOException {

						Path fileNamePath = file.getFileName();

						String fileName = fileNamePath.toString();

						for (String className : classNames) {
							String targetSrcName = className.concat(".java");

							if (_matchClassName(className, fileName)) {
								classFileList.add(file);

								notFoundClassNames.remove(className);
							}
							else if (fileName.equals(targetSrcName)) {
								srcFileList.add(file);
							}
						}

						return FileVisitResult.CONTINUE;
					}

				});
		}
		catch (IOException ioe) {
			throw new BuildException(ioe);
		}

		return notFoundClassNames;
	}

	public void setClassNames(String classNames) {
		_classNames = classNames;
	}

	public void setRootDir(String rootDir) {
		_rootDir = rootDir;
	}

	private boolean _matchClassName(String className, String fileName) {
		if (fileName.equals(className.concat(".class"))) {
			return true;
		}

		if (fileName.startsWith(className.concat("$")) &&
			fileName.endsWith(".class")) {

			return true;
		}

		return false;
	}

	private boolean _skipDirectory(String fileName) {
		if (fileName.startsWith(".") || _skipList.contains(fileName)) {
			return true;
		}
		else {
			return false;
		}
	}

	private static final Logger _LOGGER = Logger.getLogger(
		GetFileSetTask.class.getName());

	private static final String _MODULE_SRC_PARAMETER = String.valueOf(
		Paths.get("src", "main", "java"));

	private static final List<String> _skipList = Arrays.asList(
		"node_modules", "benchmarks", "definitions", "gradle", "lib",
		"nbproject", "oss-licenses", "portal-client", "readme", "sql", "tools");

	private String _classNames;
	private String _rootDir;

}