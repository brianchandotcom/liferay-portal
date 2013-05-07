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

package net.sourceforge.cobertura.instrument;

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

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.sourceforge.cobertura.coveragedata.ProjectData;
import net.sourceforge.cobertura.coveragedata.TouchCollector;

/**
 * @author Shuyang Zhou
 */
public class ProjectDataUtil {

	public static void addShutdownHook(Runnable runnable) {
		List<Runnable> shutdownHooks = _getShutdownHooks();

		shutdownHooks.add(runnable);
	}

	public static ProjectData captureProjectData(File dataFile, File lockFile) {
		for (Runnable runnable : _getShutdownHooks()) {
			runnable.run();
		}

		synchronized (ProjectDataUtil.class.getName().intern()) {
			FileLock fileLock = _lockFile(lockFile);

			try {
				if (dataFile.exists()) {
					return _readProjectData(dataFile);
				}
				else {
					return new ProjectData();
				}
			}
			finally {
				_unlockFile(fileLock);
			}
		}
	}

	public static ProjectData collectProjectData() {
		ProjectData projectData = new ProjectData();

		PrintStream printStream = new PrintStream(new ByteArrayOutputStream());

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

		return projectData;
	}

	public static void mergeSave(
		File dataFile, File lockFile, ProjectData... projectDatas) {

		synchronized (ProjectDataUtil.class.getName().intern()) {
			FileLock fileLock = _lockFile(lockFile);

			try {
				ProjectData masterProjectData = null;

				if (dataFile.exists()) {
					masterProjectData = _readProjectData(dataFile);
				}
				else {
					masterProjectData = new ProjectData();
				}

				for (ProjectData projectData : projectDatas) {
					masterProjectData.merge(projectData);
				}

				_writeProjectData(masterProjectData, dataFile);
			}
			finally {
				_unlockFile(fileLock);
			}
		}
	}

	private static List<Runnable> _getShutdownHooks() {
		ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

		if (ProjectDataUtil.class.getClassLoader() == systemClassLoader) {
			return _shutdownHooks;
		}

		try {
			Class<?> clazz = systemClassLoader.loadClass(
				ProjectDataUtil.class.getName());

			Field field = clazz.getDeclaredField("_shutdownHooks");

			field.setAccessible(true);

			return (List<Runnable>)field.get(null);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static FileLock _lockFile(File file) {
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(
				file, "rw");

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

	private static List<Runnable> _shutdownHooks =
		new CopyOnWriteArrayList<Runnable>();

	static {
		ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

		if (ProjectDataUtil.class.getClassLoader() == systemClassLoader) {
			Runtime.getRuntime().addShutdownHook(
				new Thread() {

					@Override
					public void run() {

						for (Runnable runnable : _shutdownHooks) {
							runnable.run();
						}
					}

				});
		}

	}

}