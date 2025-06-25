/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.test.report.generator.record.csv.playwright;

import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Davi Santos
 */
public class PlaywrightTestSelector {

	public List<PlaywrightTest> getPlaywrightTests() {
		List<PlaywrightTest> playwrightTests = new ArrayList<>();

		try {
			String projectRoot = System.getProperty("user.dir");

			Path playwrightTestsDirPath = Paths.get(
				projectRoot, "/../../modules/test/playwright/tests"
			).toRealPath();

			Files.walkFileTree(
				playwrightTestsDirPath,
				new SimpleFileVisitor<>() {

					@Override
					public FileVisitResult visitFile(
							Path file, BasicFileAttributes basicFileAttributes)
						throws IOException {

						Path playwrighTestFilePath = file.toRealPath();

						if (!playwrighTestFilePath.startsWith(
								playwrightTestsDirPath)) {

							return FileVisitResult.CONTINUE;
						}

						if (playwrighTestFilePath.toString(
							).endsWith(
								".spec.ts"
							)) {

							playwrightTests.addAll(
								PlaywrightTestParser.parse(
									playwrighTestFilePath));
						}

						return FileVisitResult.CONTINUE;
					}

				});
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		playwrightTests.sort(
			Comparator.comparing(PlaywrightTest::getClassName));

		return playwrightTests;
	}

}