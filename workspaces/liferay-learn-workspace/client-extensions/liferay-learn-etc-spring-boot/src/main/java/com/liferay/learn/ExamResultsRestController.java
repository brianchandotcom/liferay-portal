/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.learn;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import java.util.HexFormat;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Nilton Vieira
 */
@RequestMapping("/exam-results")
@RestController
public class ExamResultsRestController extends BaseRestController {

	@GetMapping("/csv")
	@ResponseBody
	public ResponseEntity<StreamingResponseBody> getExamResultsCSV(
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

					_write(endDate, jwt, outputStream, startDate);
				}

			}
		);
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/csv")
	public ResponseEntity<String> postExamResultsCSV(
		@AuthenticationPrincipal Jwt jwt,
		@RequestParam("file") MultipartFile multipartFile) {

		try {
			return ResponseEntity.ok(_process(jwt, multipartFile));
		}
		catch (Exception exception) {
			_log.error(exception);

			return ResponseEntity.status(
				HttpStatus.INTERNAL_SERVER_ERROR
			).body(
				"Unable to import CSV"
			);
		}
	}

	private String _decode(byte[] bytes) {
		CharBuffer charBuffer = CharBuffer.allocate(bytes.length);
		CharsetDecoder charsetDecoder = StandardCharsets.UTF_8.newDecoder();

		CoderResult coderResult = charsetDecoder.decode(
			ByteBuffer.wrap(bytes), charBuffer, true);

		if (coderResult.isError()) {
			return new String(bytes, Charset.forName("windows-1252"));
		}

		charsetDecoder.flush(charBuffer);

		charBuffer.flip();

		return charBuffer.toString();
	}

	private String _getExternalReferenceCode(
			String emailAddress, String examName, LocalDate localDate)
		throws Exception {

		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

		HexFormat hexFormat = HexFormat.of();

		String keyString = StringBundler.concat(
			StringUtil.toLowerCase(emailAddress.trim()), "|", examName.trim(),
			"|", localDate);

		return hexFormat.formatHex(
			messageDigest.digest(keyString.getBytes(StandardCharsets.UTF_8)));
	}

	private LocalDateTime _parseLocalDateTime(String value) {
		TemporalAccessor temporalAccessor = _dateTimeFormatter.parseBest(
			value, LocalDateTime::from, LocalDate::from);

		if (temporalAccessor instanceof LocalDate) {
			LocalDate localDate = (LocalDate)temporalAccessor;

			return localDate.atStartOfDay();
		}

		return (LocalDateTime)temporalAccessor;
	}

	private String _process(
			@AuthenticationPrincipal Jwt jwt, MultipartFile multipartFile)
		throws Exception {

		try (CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader(
			).parse(
				new StringReader(_decode(multipartFile.getBytes()))
			)) {

			JSONArray jsonArray = new JSONArray();

			for (CSVRecord csvRecord : csvParser) {
				String examName = csvRecord.get(
					6
				).trim();

				examName = _examNames.getOrDefault(examName, examName);

				String emailAddress = csvRecord.get(
					2
				).trim();

				OffsetDateTime offsetDateTime = OffsetDateTime.of(
					_parseLocalDateTime(
						csvRecord.get(
							10
						).trim()),
					ZoneOffset.UTC);

				jsonArray.put(
					new JSONObject(
					).put(
						"date",
						offsetDateTime.format(DateTimeFormatter.ISO_INSTANT)
					).put(
						"emailAddress", emailAddress
					).put(
						"examName", examName
					).put(
						"externalReferenceCode",
						_getExternalReferenceCode(
							emailAddress, examName,
							offsetDateTime.toLocalDate())
					).put(
						"firstName", csvRecord.get(3)
					).put(
						"lastName", csvRecord.get(4)
					).put(
						"result",
						new JSONObject(
						).put(
							"key", StringUtil.toLowerCase(csvRecord.get(8))
						).put(
							"name", csvRecord.get(8)
						)
					).put(
						"score",
						GetterUtil.getDouble(
							StringUtil.removeChar(
								csvRecord.get(
									7
								).trim(),
								'%'))
					).put(
						"testName", examName
					));
			}

			return post(
				"Bearer " + jwt.getTokenValue(), jsonArray.toString(),
				UriComponentsBuilder.fromPath(
					"/o/c/p2s3examresults/batch?createStrategy=UPSERT"
				).build(
				).toUri());
		}
	}

	private void _write(
			String endDate, Jwt jwt, OutputStream outputStream,
			String startDate)
		throws IOException {

		try (CSVPrinter csvPrinter = new CSVPrinter(
				new BufferedWriter(new OutputStreamWriter(outputStream)),
				CSVFormat.DEFAULT.builder(
				).setHeader(
					"First Name", "Last Name", "Email", "Test Taken",
					"Date of Test Taken", "Test Score", "Test Result"
				).build())) {

			String filterString = "";

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
				JSONObject jsonObject1 = new JSONObject(
					get(
						"Bearer " + jwt.getTokenValue(),
						UriComponentsBuilder.fromPath(
							"/o/c/p2s3examresults"
						).queryParam(
							"filter", filterString
						).queryParam(
							"page", i
						).queryParam(
							"pageSize", 500
						).build(
						).toUri()));

				JSONArray jsonArray = jsonObject1.getJSONArray("items");

				for (int j = 0; j < jsonArray.length(); j++) {
					JSONObject jsonObject2 = jsonArray.getJSONObject(j);

					csvPrinter.printRecord(
						jsonObject2.getString("firstName"),
						jsonObject2.getString("lastName"),
						jsonObject2.getString("emailAddress"),
						jsonObject2.getString("examName"),
						jsonObject2.getString("date"),
						jsonObject2.getDouble("score"),
						jsonObject2.getJSONObject(
							"result"
						).getString(
							"name"
						));
				}

				lastPage = jsonObject1.getInt("lastPage");
			}

			csvPrinter.flush();
		}
		catch (Exception exception) {
			throw new IOException(exception);
		}
	}

	private static final Log _log = LogFactory.getLog(
		ExamResultsRestController.class);

	private static final DateTimeFormatter _dateTimeFormatter =
		DateTimeFormatter.ofPattern(
			"[yyyy-MM-dd'T'H:mm:ss[.SSS]][yyyy-MM-dd H:mm:ss][d MMMM yyyy]",
			LocaleUtil.ENGLISH);
	private static final Map<String, String> _examNames = Map.of(
		"Building Enterprise Websites with Liferay",
		"Building Enterprise Websites with Liferay Certification Exam");

}