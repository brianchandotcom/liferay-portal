/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.test.report.generator.csv;

import com.liferay.test.report.generator.csv.playwright.PlaywrightTest;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.nio.charset.StandardCharsets;

import java.util.List;

/**
 * @author Davi Santos
 */
public class TestReportCSVWriter {

	public static void write(
		OutputStream outputStream, List<PlaywrightTest> playwrightTests) {

		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
				outputStream, StandardCharsets.UTF_8);

			BufferedWriter bufferedWriter = new BufferedWriter(
				outputStreamWriter);

			bufferedWriter.write(_CSV_HEADER);
			bufferedWriter.newLine();

			for (PlaywrightTest playwrightTest : playwrightTests) {
				bufferedWriter.write(
					String.format(
						_CSV_FORMAT, playwrightTest.getClassName(),
						playwrightTest.getTestName(),
						playwrightTest.isIgnored(),
						playwrightTest.getTestFilePath()));

				bufferedWriter.newLine();
			}

			bufferedWriter.flush();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static final String _CSV_FORMAT = "%s,%s,%s,%s";

	private static final String _CSV_HEADER = String.join(
		",", "Class Name", "Test Name", "Ignored", "File Path");

}