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

package com.liferay.cobertura.instrument;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;

import java.lang.reflect.Field;

import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

import net.sourceforge.cobertura.coveragedata.CoverageDataFileHandler;
import net.sourceforge.cobertura.coveragedata.ProjectData;
import net.sourceforge.cobertura.coveragedata.TouchCollector;

/**
 * @author Shuyang Zhou
 */
public class ProjectDataUtil {

	public static void addMergeHook() {
		Set<Runnable> mergeHooks = _getMergeHooks();

		mergeHooks.add(_mergeHookRunnable);
	}

	public static ProjectData captureProjectData() {
		runMergeHooks();

		String className = ProjectDataUtil.class.getName();

		synchronized (className.intern()) {
			FileLock fileLock = _lockFile();

			ProjectData masterProjectData = new ProjectData();

			for (ProjectData projectData : _projectDatas.values()) {
				masterProjectData.merge(projectData);
			}

			try {
				File dataFile = CoverageDataFileHandler.getDefaultDataFile();

				if (dataFile.exists()) {
					masterProjectData.merge(_readProjectData(dataFile));

					dataFile.delete();
				}

				return masterProjectData;
			}
			finally {
				_unlockFile(fileLock);
			}
		}
	}

	public static ProjectData getOrCreateProjectData(ClassLoader classLoader) {
		ProjectData projectData = _projectDatas.get(classLoader);

		if (projectData == null) {
			projectData = new ProjectData();

			ProjectData previousProjectData = _projectDatas.putIfAbsent(
				classLoader, projectData);

			if (previousProjectData != null) {
				projectData = previousProjectData;
			}
		}

		return projectData;
	}

	public static void runMergeHooks() {
		Set<Runnable> runnables = _getMergeHooks();

		for (Runnable runnable : runnables) {
			runnable.run();
		}
	}

	private static Set<Runnable> _getMergeHooks() {
		ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

		if (ProjectDataUtil.class.getClassLoader() == systemClassLoader) {
			return _mergeHooks;
		}

		try {
			Class<?> clazz = systemClassLoader.loadClass(
				ProjectDataUtil.class.getName());

			Field field = clazz.getDeclaredField("_mergeHooks");

			field.setAccessible(true);

			return (Set<Runnable>)field.get(null);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static FileLock _lockFile() {
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(
				InstrumentationAgent.getLockFile(), "rw");

			FileChannel fileChannel = randomAccessFile.getChannel();

			return fileChannel.lock();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private static ProjectData _readProjectData(File dataFile) {
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;

		for (int i = 0; i < _RETRY_TIMES; i++) {
			try {
				fileInputStream = new FileInputStream(dataFile);

				objectInputStream = new ObjectInputStream(fileInputStream);

				return (ProjectData)objectInputStream.readObject();
			}
			catch (IOException ioe) {
				continue;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
			finally {
				if (objectInputStream != null) {
					try {
						objectInputStream.close();
					}
					catch (IOException ioe) {
					}
				}

				if (fileInputStream != null) {
					try {
						fileInputStream.close();
					}
					catch (IOException ioe) {
					}
				}
			}
		}

		throw new IllegalStateException(
			"Unable to load project data after retry for " + _RETRY_TIMES +
				" times");
	}

	private static void _unlockFile(FileLock fileLock) {
		try {
			fileLock.release();

			FileChannel fileChannel = fileLock.channel();

			fileChannel.close();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private static void _writeProjectData(
		ProjectData projectData, File dataFile) {

		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;

		try {
			fileOutputStream = new FileOutputStream(dataFile);

			objectOutputStream = new ObjectOutputStream(fileOutputStream);

			objectOutputStream.writeObject(projectData);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			if (objectOutputStream != null) {
				try {
					objectOutputStream.close();
				}
				catch (IOException ioe) {
				}
			}

			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				}
				catch (IOException ioe) {
				}
			}
		}
	}

	private static final int _RETRY_TIMES = 10;

	private static final Runnable _mergeHookRunnable = new Runnable() {

			@Override
			public void run() {
				ProjectData projectData = new ProjectData();

				PrintStream printStream = new PrintStream(
					new ByteArrayOutputStream());

				synchronized (FileDescriptor.out) {
					PrintStream stdOut = System.out;

					System.setOut(printStream);

					try {
						TouchCollector.applyTouchesOnProjectData(projectData);
					}
					finally {
						System.setOut(stdOut);
					}
				}

				String className = ProjectDataUtil.class.getName();

				synchronized (className.intern()) {
					FileLock fileLock = _lockFile();

					try {
						File dataFile =
							CoverageDataFileHandler.getDefaultDataFile();

						if (dataFile.exists()) {
							projectData.merge(_readProjectData(dataFile));
						}

						_writeProjectData(projectData, dataFile);
					}
					finally {
						_unlockFile(fileLock);
					}
				}

				if (ProjectDataUtil.class.getClassLoader() !=
						ClassLoader.getSystemClassLoader()) {

					Set<Runnable> mergeHooks = _getMergeHooks();

					mergeHooks.remove(this);
				}
			}

		};

	private static final Set<Runnable> _mergeHooks =
		new CopyOnWriteArraySet<>();
	private static final ConcurrentMap<ClassLoader, ProjectData> _projectDatas =
		new ConcurrentHashMap<>();

}