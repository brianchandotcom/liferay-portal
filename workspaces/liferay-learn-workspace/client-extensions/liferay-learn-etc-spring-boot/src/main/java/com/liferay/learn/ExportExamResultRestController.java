/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.learn;

import com.liferay.client.extension.util.spring.boot.BaseRestController;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

/**
 * @author Nilton Vieira
 */
@RequestMapping("/export/exam/result")
@RestController
public class ExportExamResultRestController extends BaseRestController {

	@GetMapping
	@ResponseBody
	public ResponseEntity<StreamingResponseBody> get(
			@AuthenticationPrincipal Jwt jwt,
			@RequestParam(required = false, value = "endDate") String endDate,
			@RequestParam(required = false, value = "startDate") String
				startDate)
		throws Exception {

		return ResponseEntity.ok(
		).header(
			"Content-Disposition", "attachment; filename=\"exam_results.csv\""
		).body(
			new StreamingResponseBody() {

				@Override
				public void writeTo(OutputStream outputStream)
					throws IOException {

					try {
						_write(endDate, jwt, outputStream, startDate);
					}
					catch (Exception exception) {
						throw new RuntimeException(exception);
					}
				}

			}
		);
	}

	private void _write(
			String endDate, Jwt jwt, OutputStream outputStream,
			String startDate)
		throws Exception {

		try (CSVPrinter csvPrinter = new CSVPrinter(
				new BufferedWriter(new OutputStreamWriter(outputStream)),
				CSVFormat.DEFAULT.builder(
				).setHeader(
					"First Name", "Last Name", "Email", "Test Taken",
					"Date of Test Taken", "Test Score", "Test Result"
				).build())) {

			String filterString = "&filter=";

			if (Validator.isNotNull(endDate)) {
				filterString += "date le " + endDate;

				if (Validator.isNotNull(startDate)) {
					filterString += " and ";
				}
			}

			if (Validator.isNotNull(startDate)) {
				filterString += "date ge " + startDate;
			}

			int lastPage = 1;

			for (int i = 1; i <= lastPage; i++) {
				JSONObject jsonObject = new JSONObject(
					get(
						"Bearer " + jwt.getTokenValue(),
						StringBundler.concat(
							"/o/c/p2s3examresults/scopes/", _siteGroupId,
							"?pageSize=500&page=", i, filterString)));

				JSONArray jsonArray = jsonObject.getJSONArray("items");

				for (int j = 0; j < jsonArray.length(); j++) {
					JSONObject itemJSONObject = jsonArray.getJSONObject(j);

					csvPrinter.printRecord(
						itemJSONObject.getString("firstName"),
						itemJSONObject.getString("lastName"),
						itemJSONObject.getString("email"),
						itemJSONObject.getString("examName"),
						itemJSONObject.getString("date"),
						itemJSONObject.getDouble("score"),
						itemJSONObject.getJSONObject(
							"result"
						).getString(
							"name"
						));
				}

				lastPage = jsonObject.getInt("lastPage");
			}

			csvPrinter.flush();
		}
		catch (Exception exception) {
			throw new IOException(exception);
		}
	}

	@Value("${liferay.learn.dxp.site.group.id}")
	private long _siteGroupId;

}