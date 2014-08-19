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

package com.liferay.util.poi;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.InputStream;

import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * @author Mirco Tamburini
 */
public class XLSTextStripper {

	public XLSTextStripper(InputStream is) {
		String text = null;

		try {
			StringBundler sb = new StringBundler();

			HSSFWorkbook workbook = new HSSFWorkbook(is);

			int numOfSheets = workbook.getNumberOfSheets();

			for (int i = 0; i < numOfSheets; i++) {
				HSSFSheet sheet = workbook.getSheetAt(i);

				Iterator<Row> rowIterator = sheet.rowIterator();

				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();

					Iterator<Cell> cellIterator = row.cellIterator();

					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();

						String cellStringValue = null;

						if (cell.getCellType() == 4) {
							boolean booleanValue = cell.getBooleanCellValue();

							cellStringValue = String.valueOf(booleanValue);
						}
						else if (cell.getCellType() == 0) {
							double doubleValue = cell.getNumericCellValue();

							cellStringValue = String.valueOf(doubleValue);
						}
						else if (cell.getCellType() == 1) {
							cellStringValue =
								cell.getRichStringCellValue().getString();
						}

						if (cellStringValue != null) {
							sb.append(cellStringValue);
							sb.append("\t");
						}
					}

					sb.append("\n");
				}
			}

			text = sb.toString();
		}
		catch (Exception e) {
			_log.error(e.getMessage());
		}

		_text = text;
	}

	public String getText() {
		return _text;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		XLSTextStripper.class);

	private final String _text;

}