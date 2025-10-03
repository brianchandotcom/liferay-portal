/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.learn;

import com.google.auth.oauth2.GoogleCredentials;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.net.HttpURLConnection;
import java.net.URI;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Nilton Vieira
 */
@RequestMapping("/learn")
@RestController
public class LearnRestController extends BaseRestController {

	@GetMapping("/lesson/{lessonId}/audio/base64")
	@ResponseBody
	public ResponseEntity<Object> getLessonAudioBase64(
		@AuthenticationPrincipal Jwt jwt, @PathVariable long lessonId,
		@RequestParam String folderId, @RequestParam String languageCode,
		@RequestParam String voiceName, @RequestParam String voiceType) {

		try {
			JSONObject lessonJSONObject = new JSONObject(
				get(
					_getAuthorization(),
					UriComponentsBuilder.fromPath(
						"/o/c/lessons/" + lessonId
					).queryParam(
						"fields", "content,dateModified"
					).build(
					).toUri()));

			String content = lessonJSONObject.getString("content");

			if (Validator.isNull(content)) {
				return ResponseEntity.status(
					HttpStatus.NOT_FOUND
				).body(
					"Lesson " + lessonId + " is missing readable text."
				);
			}

			OffsetDateTime offsetDateTime1 = OffsetDateTime.parse(
				lessonJSONObject.getString("dateModified")
			).truncatedTo(
				ChronoUnit.MINUTES
			);

			URI uri = UriComponentsBuilder.fromHttpUrl(
				_protocol + "://" + _mainDomain
			).path(
				"/o/headless-delivery/v1.0/document-folders/" + folderId +
					"/documents"
			).queryParam(
				"filter",
				StringBundler.concat(
					"title eq 'lesson-", lessonId, "-", voiceType, ".mp3'")
			).build(
			).toUri();

			HttpURLConnection connection = (HttpURLConnection)uri.toURL(
			).openConnection();

			connection.setRequestMethod("GET");

			connection.setRequestProperty("Accept", "application/json");

			JSONObject documentJSONObject = new JSONObject(
				new String(
					connection.getInputStream(
					).readAllBytes()));

			JSONArray itemsJSONArray = documentJSONObject.optJSONArray("items");

			JSONObject documentItemJSONObject =
				((itemsJSONArray != null) && (itemsJSONArray.length() > 0)) ?
					itemsJSONArray.optJSONObject(0) : null;

			OffsetDateTime offsetDateTime2 = null;

			if (!JSONUtil.isEmpty(itemsJSONArray)) {
				JSONObject audioFileItemJSONObject =
					itemsJSONArray.getJSONObject(0);

				offsetDateTime2 = OffsetDateTime.parse(
					audioFileItemJSONObject.getString("dateModified")
				).truncatedTo(
					ChronoUnit.MINUTES
				);
			}

			if ((itemsJSONArray == null) || itemsJSONArray.isEmpty() ||
				offsetDateTime1.isAfter(offsetDateTime2)) {

				ByteArrayOutputStream byteArrayOutputStream =
					new ByteArrayOutputStream();

				List<String> ssmls = _splitSsml(
					content.replaceAll("\\bLiferay\\b", "Life-ray"), 5000);

				for (String ssml : ssmls) {
					String response = post(
						_getGoogleAccessToken(),
						new JSONObject(
							HashMapBuilder.<String, Object>put(
								"audioConfig",
								HashMapBuilder.<String, Object>put(
									"audioEncoding", "MP3"
								).build()
							).put(
								"input",
								HashMapBuilder.<String, Object>put(
									"text", ssml
								).build()
							).put(
								"voice",
								HashMapBuilder.<String, Object>put(
									"languageCode", languageCode
								).put(
									"name", voiceName
								).build()
							).build()
						).toString(),
						UriComponentsBuilder.fromUriString(
							"https://texttospeech.googleapis.com/v1beta1" +
								"/text:synthesize"
						).build(
						).toUri());

					byteArrayOutputStream.write(
						Base64.getDecoder(
						).decode(
							new JSONObject(
								response
							).getString(
								"audioContent"
							)
						));
				}

				String audioContentBase64 = Base64.getEncoder(
				).encodeToString(
					byteArrayOutputStream.toByteArray()
				);

				JSONObject responseBodyJSONObject = new JSONObject();

				if ((offsetDateTime2 != null) &&
					offsetDateTime1.isAfter(offsetDateTime2)) {

					responseBodyJSONObject.put(
						"id", documentItemJSONObject.optString("id", null));
				}

				responseBodyJSONObject.put(
					"audioContentBase64", audioContentBase64);

				return ResponseEntity.ok(responseBodyJSONObject.toString());
			}

			String documentContentUrl = documentItemJSONObject.optString(
				"contentUrl", null);

			JSONObject responseBodyJSONObject = new JSONObject();

			responseBodyJSONObject.put("contentUrl", documentContentUrl);

			return ResponseEntity.ok(
			).contentType(
				MediaType.APPLICATION_JSON
			).body(
				responseBodyJSONObject.toString(2)
			);
		}
		catch (Exception exception) {
			return ResponseEntity.status(
				500
			).body(
				"Error: " + exception.getMessage()
			);
		}
	}

	@GetMapping("/menu/items")
	@ResponseBody
	public ResponseEntity<Object> getMenuItems(
		@AuthenticationPrincipal Jwt jwt) {

		return new ResponseEntity<>(
			TransformUtil.transform(
				new JSONObject(
					get(
						_getAuthorization(),
						UriComponentsBuilder.fromPath(
							"/o/object-admin/v1.0/object-folders" +
								"/by-external-reference-code" +
									"/P2S3_LEARNING_MANAGEMENT_SYSTEM"
						).build(
						).toUri())
				).getJSONArray(
					"objectFolderItems"
				).toList(),
				this::_toMap),
			HttpStatus.OK);
	}

	@GetMapping("/{quizId}/questions")
	@ResponseBody
	public ResponseEntity<Object> getQuizQuestions(
			@AuthenticationPrincipal Jwt jwt, @PathVariable long quizId)
		throws Exception {

		return new ResponseEntity<>(
			new JSONObject(
				get(
					_getAuthorization(),
					UriComponentsBuilder.fromPath(
						"/o/c/quizquestions"
					).queryParam(
						"filter", "quizId eq '" + quizId + "'"
					).queryParam(
						"fields",
						"id,position,question,questionType,quizAnswers," +
							"quizAnswers.answer,quizAnswers.id," +
								"quizAnswers.position"
					).queryParam(
						"nestedFields", "quizAnswers"
					).queryParam(
						"pageSize", "500"
					).queryParam(
						"sort", "position"
					).build(
					).toUri())
			).getJSONArray(
				"items"
			).toList(),
			HttpStatus.OK);
	}

	@PostMapping("/{quizId}/result")
	@ResponseBody
	public ResponseEntity<Object> postQuizResult(
			@AuthenticationPrincipal Jwt jwt, @PathVariable long quizId,
			@RequestBody String json)
		throws Exception {

		Map<String, Object> quizResultMap = _getQuizResult(
			new JSONObject(json),
			new JSONObject(
				get(
					_getAuthorization(),
					UriComponentsBuilder.fromPath(
						"/o/c/quizes/" + quizId
					).queryParam(
						"fields",
						StringBundler.concat(
							"id,r_quiz_c_moduleId,durationMinutes,passingScore",
							",isKnowledgeCheck,quizQuestions.id,quizQuestions.",
							"position,quizQuestions.question,quizQuestions.",
							"questionType,quizQuestions.questionTotalScore,",
							"quizQuestions.quizAnswers,quizQuestions.",
							"quizAnswers.id,quizQuestions.quizAnswers.position",
							",quizQuestions.quizAnswers.answer,quizQuestions.",
							"quizAnswers.score")
					).queryParam(
						"nestedFields", "quizQuestions,quizAnswers"
					).queryParam(
						"nestedFieldsDepth", "2"
					).queryParam(
						"pageSize", "500"
					).build(
					).toUri())));

		if (!GetterUtil.getBoolean(quizResultMap.get("isKnowledgeCheck")) &&
			GetterUtil.getBoolean(quizResultMap.get("passed")) &&
			(jwt != null)) {

			_postUserBadge(
				quizId,
				GetterUtil.getLong(
					jwt.getClaims(
					).get(
						"sub"
					)));
		}

		return ResponseEntity.ok(quizResultMap);
	}

	private String _convertHtmlListToTextInline(String html) {
		html = _convertHtmlTableToTextInline(html);

		Matcher matcher = _liPattern.matcher(html);

		StringBuffer stringBuffer = new StringBuffer();

		while (matcher.find()) {
			String openTag = matcher.group(1);
			String tagContent = matcher.group(
				2
			).trim();
			String closeTag = matcher.group(3);

			String visibleText = tagContent.replaceAll(
				"(?s)<[^>]+>", " "
			).replaceAll(
				"\\s+", " "
			).trim();

			if (!visibleText.matches(".*[.!?;:]$")) {
				int lastCloseTagIndex = tagContent.lastIndexOf("</");

				if (lastCloseTagIndex != -1) {
					String contentBeforeClosingTag = tagContent.substring(
						0, lastCloseTagIndex
					).replaceAll(
						"\\s+$", ""
					);
					String closingTagAndContentAfter = tagContent.substring(
						lastCloseTagIndex);

					tagContent = StringBundler.concat(
						contentBeforeClosingTag, ".",
						closingTagAndContentAfter);
				}
				else {
					tagContent = tagContent + ".";
				}
			}

			matcher.appendReplacement(
				stringBuffer,
				Matcher.quoteReplacement(
					StringBundler.concat(openTag, tagContent, closeTag)));
		}

		matcher.appendTail(stringBuffer);

		html = stringBuffer.toString();

		String textContent = html.replaceAll("(?s)<[^>]+>", " ");

		return textContent.replaceAll(
			"\\s+", " "
		).trim();
	}

	private String _convertHtmlTableToTextInline(String html) {
		if (html == null) {
			return "";
		}

		Matcher tableMatcher = _tablePattern.matcher(html);
		StringBuffer stringBuffer = new StringBuffer();

		while (tableMatcher.find()) {
			String tableHtml = tableMatcher.group(1);

			List<String> headers = new ArrayList<>();
			Matcher theadMatcher = _theadPattern.matcher(tableHtml);

			if (theadMatcher.find()) {
				Matcher headTrMatcher = _trPattern.matcher(
					theadMatcher.group(1));

				if (headTrMatcher.find()) {
					Matcher headCellsMatcher = _cellPattern.matcher(
						headTrMatcher.group(1));

					while (headCellsMatcher.find()) {
						headers.add(
							_decodeBasicHtmlEntities(
								headCellsMatcher.group(1)
							).trim());
					}
				}
			}

			String bodyHtml = tableHtml;
			Matcher tbodyMatcher = _tbodyPattern.matcher(tableHtml);

			if (tbodyMatcher.find()) {
				bodyHtml = tbodyMatcher.group(1);
			}

			Matcher trMatcher = _trPattern.matcher(bodyHtml);

			StringBuilder tableDescription = new StringBuilder("Table: ");

			if (!headers.isEmpty()) {
				StringBundler sb = new StringBundler();

				sb.append("Column headings: ");

				for (int i = 0; i < headers.size(); i++) {
					sb.append(headers.get(i));

					if (i < (headers.size() - 1)) {
						sb.append("; ");
					}
					else {
						sb.append(". ");
					}
				}

				tableDescription.append(sb.toString());
			}

			int row = 0;

			while (trMatcher.find()) {
				row++;

				String tr = trMatcher.group(1);

				Matcher cellMatcher = _cellPattern.matcher(tr);

				List<String> cells = new ArrayList<>();

				while (cellMatcher.find()) {
					String raw = _decodeBasicHtmlEntities(
						cellMatcher.group(1)
					).trim();

					if (Objects.equals(raw, "✔") || Objects.equals(raw, "✓")) {
						raw = "supported";
					}
					else if (raw.isEmpty() || Objects.equals(raw, "&nbsp;")) {
						raw = "not supported";
					}

					cells.add(raw);
				}

				if (!cells.isEmpty()) {
					tableDescription.append(
						"Row "
					).append(
						row
					).append(
						". "
					);

					for (int c = 0; c < cells.size(); c++) {
						String colName =
							(c < headers.size()) ? headers.get(c) :
								("Column " + (c + 1));

						tableDescription.append(
							colName
						).append(
							": "
						).append(
							cells.get(c)
						).append(
							". "
						);
					}
				}
			}

			String trimmedTableDescriptionText = tableDescription.toString(
			).trim();

			String replacement = trimmedTableDescriptionText + " ";

			tableMatcher.appendReplacement(
				stringBuffer, Matcher.quoteReplacement(replacement));
		}

		tableMatcher.appendTail(stringBuffer);

		return stringBuffer.toString();
	}

	private String _decodeBasicHtmlEntities(String string) {
		if (string == null) {
			return "";
		}

		return StringUtil.replace(
			string,
			new String[] {
				"&nbsp;", "&NBSP;", "\u00A0", "&amp;", "&lt;", "&gt;", "&quot;",
				"&#39;"
			},
			new String[] {" ", " ", " ", "&", "<", ">", "\"", "'"});
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-learn-etc-spring-boot-oahs");
	}

	private String _getGoogleAccessToken() throws Exception {
		GoogleCredentials googleCredentials = GoogleCredentials.fromStream(
			new ByteArrayInputStream(_googleCredentials.getBytes())
		).createScoped(
			Collections.singletonList(
				"https://www.googleapis.com/auth/cloud-platform")
		);

		googleCredentials.refresh();

		String accessTokenValue = googleCredentials.getAccessToken(
		).getTokenValue();

		return "Bearer " + accessTokenValue;
	}

	private int _getQuizQuestionScore(
		Map<String, Object> answerMap, JSONObject quizQuestionJSONObject,
		JSONObject scoreSheetJSONObject) {

		JSONArray quizAnswersJSONArray = quizQuestionJSONObject.getJSONArray(
			"quizAnswers");

		scoreSheetJSONObject.put("questionsAnswers", quizAnswersJSONArray);

		boolean incorrectAnswer = false;

		for (int j = 0; j < quizAnswersJSONArray.length(); j++) {
			JSONObject quizAnswerJSONObject =
				quizAnswersJSONArray.getJSONObject(j);

			if (((quizAnswerJSONObject.getInt("score") > 0) &&
				 !GetterUtil.getBoolean(
					 answerMap.get(
						 String.valueOf(
							 quizAnswerJSONObject.getLong("id"))))) ||
				((quizAnswerJSONObject.getInt("score") <= 0) &&
				 GetterUtil.getBoolean(
					 answerMap.get(
						 String.valueOf(
							 quizAnswerJSONObject.getLong("id")))))) {

				incorrectAnswer = true;

				break;
			}
		}

		if (incorrectAnswer) {
			return 0;
		}

		return quizQuestionJSONObject.getInt("questionTotalScore");
	}

	private Map<String, Object> _getQuizResult(
		JSONObject quizAnswersJSONObject, JSONObject quizJSONObject) {

		JSONArray quizQuestionsJSONArray = quizJSONObject.getJSONArray(
			"quizQuestions");

		Map<String, Object> map = HashMapBuilder.<String, Object>put(
			"isKnowledgeCheck", false
		).put(
			"passingScore", quizJSONObject.getInt("passingScore")
		).put(
			"selectedAnswers", quizAnswersJSONObject.toMap()
		).put(
			"totalQuestions", quizQuestionsJSONArray.length()
		).build();

		float achievedQuizScore = 0;
		float totalQuizScore = 0;
		int totalPassedQuizQuestions = 0;

		JSONArray scoreSheetJSONArray = new JSONArray();

		for (int i = 0; i < quizQuestionsJSONArray.length(); i++) {
			JSONObject quizQuestionJSONObject =
				quizQuestionsJSONArray.getJSONObject(i);
			JSONObject scoreSheetJSONObject = new JSONObject();

			scoreSheetJSONObject.put(
				"questionId", quizQuestionJSONObject.getLong("id")
			).put(
				"questionTitle", quizQuestionJSONObject.getString("question")
			).put(
				"totalScore",
				quizQuestionJSONObject.getInt("questionTotalScore")
			).put(
				"type",
				quizQuestionJSONObject.getJSONObject(
					"questionType"
				).getString(
					"key"
				)
			);

			int quizQuestionScore = 0;

			if (Objects.equals(
					scoreSheetJSONObject.getString("type"),
					"selectMultipleChoice")) {

				JSONObject jsonObject = quizAnswersJSONObject.getJSONObject(
					String.valueOf(quizQuestionJSONObject.getLong("id")));

				scoreSheetJSONObject.put("selectedAnswer", jsonObject);

				quizQuestionScore = _getQuizQuestionScore(
					jsonObject.toMap(), quizQuestionJSONObject,
					scoreSheetJSONObject);
			}
			else {
				long id = quizAnswersJSONObject.getLong(
					String.valueOf(quizQuestionJSONObject.getLong("id")));

				scoreSheetJSONObject.put("selectedAnswer", id);

				quizQuestionScore = _getQuizQuestionScore(
					Collections.singletonMap(String.valueOf(id), true),
					quizQuestionJSONObject, scoreSheetJSONObject);
			}

			if (quizQuestionScore > 0) {
				totalPassedQuizQuestions++;
			}

			achievedQuizScore += quizQuestionScore;
			scoreSheetJSONObject.put("achievedScore", quizQuestionScore);
			totalQuizScore += quizQuestionJSONObject.getInt(
				"questionTotalScore");

			scoreSheetJSONArray.put(scoreSheetJSONObject);
		}

		if (quizJSONObject.getBoolean("isKnowledgeCheck")) {
			map.put("isKnowledgeCheck", true);
			map.put("scoreSheet", scoreSheetJSONArray.toList());
		}

		map.put(
			"passed",
			Math.round((achievedQuizScore / totalQuizScore) * 100) >=
				quizJSONObject.getInt("passingScore"));
		map.put("totalPassedQuestions", totalPassedQuizQuestions);
		map.put(
			"totalScore",
			Math.round((achievedQuizScore / totalQuizScore) * 100));

		return map;
	}

	private void _postUserBadge(long quizId, long userId) {
		JSONArray jsonArray = new JSONObject(
			get(
				_getAuthorization(),
				UriComponentsBuilder.fromPath(
					"/o/c/quizes/" + quizId + "/quizBadge"
				).queryParam(
					"fields", "id"
				).build(
				).toUri())
		).getJSONArray(
			"items"
		);

		if (jsonArray.isEmpty()) {
			return;
		}

		JSONObject badgeJSONObject = jsonArray.getJSONObject(0);

		JSONObject userBadgeJSONObject = new JSONObject(
			get(
				_getAuthorization(),
				UriComponentsBuilder.fromPath(
					"/o/c/userbadges"
				).queryParam(
					"filter",
					StringBundler.concat(
						"userId eq '", userId, "' and badgeId eq ",
						badgeJSONObject.getLong("id"))
				).build(
				).toUri()));

		if (userBadgeJSONObject.getInt("totalCount") > 0) {
			return;
		}

		post(
			_getAuthorization(),
			new JSONObject(
			).put(
				"badgeId", badgeJSONObject.getLong("id")
			).put(
				"quizId", quizId
			).put(
				"r_userBadges_userId", userId
			).toString(),
			UriComponentsBuilder.fromPath(
				"/o/c/userbadges"
			).build(
			).toUri());
	}

	private List<String> _splitSsml(String ssml, int maxLength) {
		List<String> parts = new ArrayList<>();
		StringBundler sb = new StringBundler();

		String ssmlContent = ssml.replaceFirst(
			"^<speak>", ""
		).replaceFirst(
			"</speak>$", ""
		).trim();

		ssmlContent = _decodeBasicHtmlEntities(ssmlContent);

		ssmlContent = _convertHtmlListToTextInline(ssmlContent);

		String[] sentences = ssmlContent.split("(?<=[.!?])\\s+");

		for (String sentence : sentences) {
			if ((sb.length() + sentence.length()) > maxLength) {
				parts.add(
					sb.toString(
					).trim());
				sb = new StringBundler();
			}

			sb.append(
				sentence
			).append(
				" "
			);
		}

		if (sb.length() > 0) {
			parts.add(
				sb.toString(
				).trim());
		}

		return parts;
	}

	private Map<String, Object> _toMap(Object object) {
		Map<String, Object> map = (Map<String, Object>)object;

		if (!map.containsKey("objectDefinition")) {
			return null;
		}

		Map<String, Object> objectDefinitionMap = (Map<String, Object>)map.get(
			"objectDefinition");

		return HashMapBuilder.<String, Object>put(
			"externalReferenceCode",
			objectDefinitionMap.get("externalReferenceCode")
		).put(
			"id", objectDefinitionMap.get("id")
		).put(
			"title", objectDefinitionMap.get("pluralLabel")
		).build();
	}

	private static final Pattern _cellPattern = Pattern.compile(
		"(?is)<t(?:h|d)[^>]*>(.*?)</t(?:h|d)>");
	private static final Pattern _liPattern = Pattern.compile(
		"(?i)(<li[^>]*>)(.*?)(</li>)", Pattern.DOTALL);
	private static final Pattern _tablePattern = Pattern.compile(
		"(?is)<table[^>]*>(.*?)</table>");
	private static final Pattern _tbodyPattern = Pattern.compile(
		"(?is)<tbody[^>]*>(.*?)</tbody>");
	private static final Pattern _theadPattern = Pattern.compile(
		"(?is)<thead[^>]*>(.*?)</thead>");
	private static final Pattern _trPattern = Pattern.compile(
		"(?is)<tr[^>]*>(.*?)</tr>");

	@Value("${liferay.learn.google.credentials}")
	private String _googleCredentials;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _mainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _protocol;

}