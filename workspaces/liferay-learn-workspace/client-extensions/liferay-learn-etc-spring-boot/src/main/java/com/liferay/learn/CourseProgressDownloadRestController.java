/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.learn;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Lucas Emanuel
 */
@RequestMapping("/course-progress/download")
@RestController
public class CourseProgressDownloadRestController extends BaseRestController {

	@GetMapping
	@ResponseBody
	public ResponseEntity<StreamingResponseBody> get(
			@AuthenticationPrincipal Jwt jwt,
			@RequestParam(required = false, value = "endDate") String endDate,
			@RequestParam(required = false, value = "startDate") String
				startDate)
		throws IOException {

		return ResponseEntity.ok(
		).header(
			"Content-Disposition",
			"attachment; filename=\"course_progress.csv\""
		).body(
			new StreamingResponseBody() {

				@Override
				public void writeTo(OutputStream outputStream)
					throws IOException {

					_write(endDate, outputStream, startDate);
				}

			}
		);
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-learn-etc-spring-boot-oauth-application-headless-server");
	}

	private boolean _isWithinDateRange(String dateStr, String start, String end)
		throws IOException {

		if ((dateStr == null) || ((start == null) && (end == null))) {
			return true;
		}

		try {
			LocalDate date = LocalDate.parse(
				dateStr, DateTimeFormatter.ISO_DATE_TIME);

			if (start != null) {
				LocalDate startDate = LocalDate.parse(start);

				if (date.isBefore(startDate)) {
					return false;
				}
			}

			if (end != null) {
				LocalDate endDate = LocalDate.parse(end);

				if (date.isAfter(endDate)) {
					return false;
				}
			}

			return true;
		}
		catch (Exception exception) {
			throw new IOException(exception);
		}
	}

	private void _write(
			String endDate, OutputStream outputStream, String startDate)
		throws IOException {

		try (CSVPrinter csvPrinter = new CSVPrinter(
				new BufferedWriter(new OutputStreamWriter(outputStream)),
				CSVFormat.DEFAULT.builder(
				).setHeader(
					"First Name", "Last Name", "Work Email", "Course Name",
					"Completion Status", "% Complete", "User Group"
				).build())) {

			int lastPage = 1;

			for (int i = 1; i <= lastPage; i++) {
				JSONObject jsonObject = new JSONObject(
					get(
						_getAuthorization(),
						UriComponentsBuilder.fromUriString(
							"/o/c/enrollments/scopes/" + _siteGroupId
						).queryParam(
							"pageSize", 500
						).queryParam(
							"page", i
						).queryParam(
							"nestedFields", "course,user"
						).build(
						).toUri()));

				JSONArray jsonArray = jsonObject.getJSONArray("items");

				for (int j = 0; j < jsonArray.length(); j++) {
					JSONObject enrollment = jsonArray.getJSONObject(j);

					JSONObject user = enrollment.optJSONObject(
						"r_userenrollments_user");
					JSONObject course = enrollment.optJSONObject(
						"r_courseEnrollment_c_course");

					if ((user == null) || (course == null)) {
						continue;
					}

					String modifiedDate = enrollment.optString(
						"dateModified", null);

					if (!_isWithinDateRange(modifiedDate, startDate, endDate)) {
						continue;
					}

					String userId = user.optString(
						"id",
						UUID.randomUUID(
						).toString());
					String[] fullName = user.optString(
						"name", ""
					).split(
						" ", 2
					);
					String firstName = (fullName.length > 0) ? fullName[0] : "";
					String lastName = (fullName.length > 1) ? fullName[1] : "";
					String email = user.optString("emailAddress", "");

					JSONArray groupsJSONArray = user.optJSONArray(
						"userGroupBriefs");
					List<String> groupNames = new ArrayList<>();

					if (groupsJSONArray != null) {
						for (int g = 0; g < groupsJSONArray.length(); g++) {
							groupNames.add(
								groupsJSONArray.getJSONObject(
									g
								).optString(
									"name", ""
								));
						}
					}

					String userGroup = String.join(" | ", groupNames);

					String courseTitle = course.optString("title", "");
					float totalAssets = course.optInt("totalAssets", 0);

					String completedAssetsStr = enrollment.optString(
						"completedAssetIds", ""
					).replaceFirst(
						"^,", ""
					);

					List<String> completedAssets =
						completedAssetsStr.isBlank() ? Collections.emptyList() :
							Arrays.asList(completedAssetsStr.split(","));

					if (totalAssets == 0)

						continue;

					float percent =
						((float)completedAssets.size() / totalAssets) * 100;
					String status =
						(percent >= 100) ? "completed" : "in progress";

					csvPrinter.printRecord(
						firstName, lastName, email, courseTitle, status,
						String.format("%.2f", percent), userGroup);
				}

				lastPage = jsonObject.optInt("lastPage", 1);
			}

			csvPrinter.flush();
		}
		catch (Exception e) {
			throw new IOException(e);
		}
	}

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${liferay.learn.dxp.site.group.id}")
	private long _siteGroupId;

}