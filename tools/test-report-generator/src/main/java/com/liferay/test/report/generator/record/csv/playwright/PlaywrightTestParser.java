/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.test.report.generator.record.csv.playwright;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Davi Santos
 */
public class PlaywrightTestParser {

	public static List<PlaywrightTest> parse(Path playwrightTestFilePath) {
		String playwrightTestFileContent = "";

		try {
			playwrightTestFileContent = Files.readString(
				playwrightTestFilePath);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		List<PlaywrightTest> playwrightTests = new ArrayList<>();

		Matcher matcher = _testPattern.matcher(playwrightTestFileContent);

		String className = playwrightTestFilePath.getFileName(
		).toString(
		).replaceFirst(
			"\\.spec\\.ts$", ""
		);

		String testFilePath = playwrightTestFilePath.toString(
		).replaceAll(
			"^.*?(modules/)", "$1"
		);

		while (matcher.find()) {
			String testName = matcher.group(3);

			boolean ignored = false;

			if (matcher.group(1) != null) {
				ignored = true;
			}

			playwrightTests.add(
				new PlaywrightTest(className, testFilePath, testName, ignored));
		}

		return playwrightTests;
	}

	private static final Pattern _testPattern = Pattern.compile(
		"test(\\.skip)?\\(\\s*(['\"`])(.*?)\\2");

}