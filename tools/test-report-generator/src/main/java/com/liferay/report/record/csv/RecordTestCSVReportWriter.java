/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.report.record.csv;

import com.liferay.report.record.csv.playwright.PlaywrightTest;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 * @author Davi Santos
 */
public class RecordTestCSVReportWriter {

	public static void write(
		List<PlaywrightTest> playwrightTests, OutputStream outputStream) {

		try (CSVPrinter csvPrinter = new CSVPrinter(
				new BufferedWriter(new OutputStreamWriter(outputStream)),
				CSVFormat.DEFAULT.builder(
				).setHeader(
					"Class Name", "Test Name", "Ignored", "File Path"
				).build())) {

			for (PlaywrightTest playwrightTest : playwrightTests) {
				csvPrinter.printRecord(
					playwrightTest.getClassName(), playwrightTest.getTestName(),
					playwrightTest.isIgnored(),
					playwrightTest.getTestFilePath());
			}

			csvPrinter.flush();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

}