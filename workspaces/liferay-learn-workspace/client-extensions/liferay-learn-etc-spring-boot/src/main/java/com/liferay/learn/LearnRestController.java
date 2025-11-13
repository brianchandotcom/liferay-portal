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
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
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
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
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
		@PathVariable long lessonId, @RequestParam String languageCode,
		@RequestParam String voiceName) {

		try {
			JSONObject lessonJSONObject = new JSONObject(
				get(
					_getAuthorization(),
					UriComponentsBuilder.fromPath(
						"/o/c/p2s3lessons/" + lessonId
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

			String fileName = StringBundler.concat(
				"lesson-", lessonId, "-", voiceName, ".mp3");

			JSONObject documentJSONObject = null;

			try {
				documentJSONObject = new JSONObject(
					get(
						"",
						UriComponentsBuilder.fromPath(
							StringBundler.concat(
								"/o/headless-delivery/v1.0/sites/",
								_siteGroupId,
								"/documents/by-external-reference-code/",
								StringUtil.toUpperCase(fileName))
						).build(
						).toUri()));
			}
			catch (WebClientResponseException webClientResponseException) {
				if (webClientResponseException.getStatusCode() !=
						HttpStatus.NOT_FOUND) {

					throw webClientResponseException;
				}

				return ResponseEntity.ok(
					_generateAudioResource(
						content, 0, fileName, languageCode, voiceName));
			}

			OffsetDateTime offsetDateTime = OffsetDateTime.parse(
				lessonJSONObject.getString("dateModified")
			).truncatedTo(
				ChronoUnit.MINUTES
			);

			if (offsetDateTime.isAfter(
					OffsetDateTime.parse(
						documentJSONObject.getString("dateModified")
					).truncatedTo(
						ChronoUnit.MINUTES
					))) {

				return ResponseEntity.ok(
					_generateAudioResource(
						content, documentJSONObject.getLong("id"), fileName,
						languageCode, voiceName));
			}

			return ResponseEntity.ok(
				Collections.singletonMap(
					"contentUrl", documentJSONObject.getString("contentUrl")));
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
						"/o/c/p2s3quizquestions"
					).queryParam(
						"filter", "quizId eq '" + quizId + "'"
					).queryParam(
						"fields",
						StringBundler.concat(
							"id,p2s3QuizQuestionToP2S3QuizAnswers,",
							"p2s3QuizQuestionToP2S3QuizAnswers.answer,",
							"p2s3QuizQuestionToP2S3QuizAnswers.id,",
							"p2s3QuizQuestionToP2S3QuizAnswers.position,",
							"position,question,questionType")
					).queryParam(
						"nestedFields", "p2s3QuizQuestionToP2S3QuizAnswers"
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
						"/o/c/p2s3quizes/" + quizId
					).queryParam(
						"fields",
						StringBundler.concat(
							"durationMinutes,id,isKnowledgeCheck,",
							"p2s3QuizToP2S3QuizQuestions.id,",
							"p2s3QuizToP2S3QuizQuestions.",
							"p2s3QuizQuestionToP2S3QuizAnswers,",
							"p2s3QuizToP2S3QuizQuestions.",
							"p2s3QuizQuestionToP2S3QuizAnswers.answer,",
							"p2s3QuizToP2S3QuizQuestions.",
							"p2s3QuizQuestionToP2S3QuizAnswers.id,",
							"p2s3QuizToP2S3QuizQuestions.",
							"p2s3QuizQuestionToP2S3QuizAnswers.position,",
							"p2s3QuizToP2S3QuizQuestions.",
							"p2s3QuizQuestionToP2S3QuizAnswers.score,",
							"p2s3QuizToP2S3QuizQuestions.position,",
							"p2s3QuizToP2S3QuizQuestions.question,",
							"p2s3QuizToP2S3QuizQuestions.questionTotalScore,",
							"p2s3QuizToP2S3QuizQuestions.questionType,",
							"passingScore,",
							"r_p2s3ModuleToP2S3Quizzes_c_p2s3ModuleId")
					).queryParam(
						"nestedFields",
						"p2s3QuizToP2S3QuizQuestions," +
							"p2s3QuizQuestionToP2S3QuizAnswers"
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

	private Map<String, Object> _generateAudioResource(
			String content, long documentId, String fileName,
			String languageCode, String voiceName)
		throws Exception {

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		List<String> ssmls = _splitSsml(_htmlToReadableText(content));

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

		ByteArrayResource byteArrayResource = new ByteArrayResource(
			byteArrayOutputStream.toByteArray()) {

			@Override
			public String getFilename() {
				return fileName;
			}

		};

		MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();

		multipartBodyBuilder.part(
			"document",
			new JSONObject(
			).put(
				"documentFolderId", _audioLessonsDocumentFolderId
			).put(
				"externalReferenceCode", StringUtil.toUpperCase(fileName)
			).put(
				"fileName", fileName
			).put(
				"title", fileName
			).put(
				"viewableBy", "Anyone"
			).toString(),
			MediaType.APPLICATION_JSON);
		multipartBodyBuilder.part(
			"file", byteArrayResource, MediaType.APPLICATION_OCTET_STREAM);

		if (documentId != 0) {
			return Collections.singletonMap(
				"contentUrl",
				new JSONObject(
					_webClientBuilder.baseUrl(
						_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
					).build(
					).put(
					).uri(
						UriComponentsBuilder.fromPath(
							"/o/headless-delivery/v1.0/sites/{siteGroupId}" +
								"/documents/by-external-reference-code" +
									"/{fileName}"
						).build(
							_siteGroupId, StringUtil.toUpperCase(fileName)
						).toString()
					).contentType(
						MediaType.MULTIPART_FORM_DATA
					).header(
						HttpHeaders.AUTHORIZATION, _getAuthorization()
					).body(
						BodyInserters.fromMultipartData(
							multipartBodyBuilder.build())
					).retrieve(
					).bodyToMono(
						String.class
					).block()
				).optString(
					"contentUrl", null
				));
		}

		return Collections.singletonMap(
			"contentUrl",
			new JSONObject(
				_webClientBuilder.baseUrl(
					_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
				).build(
				).post(
				).uri(
					UriComponentsBuilder.fromPath(
						"/o/headless-delivery/v1.0/document-folders" +
							"/{documentFolderId}/documents"
					).build(
						_audioLessonsDocumentFolderId
					).toString()
				).contentType(
					MediaType.MULTIPART_FORM_DATA
				).header(
					HttpHeaders.AUTHORIZATION, _getAuthorization()
				).body(
					BodyInserters.fromMultipartData(
						multipartBodyBuilder.build())
				).retrieve(
				).bodyToMono(
					String.class
				).block()
			).optString(
				"contentUrl", null
			));
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

	private String _htmlToReadableText(String html) {
		Document document = Jsoup.parse(html);

		for (Element table : document.select("table")) {
			StringBundler sb = new StringBundler();
			List<String> headers = new ArrayList<>();

			Elements tableHeaders = table.select("thead th");

			if (tableHeaders.isEmpty()) {
				Element element = table.selectFirst("tr");

				if (element != null) {
					tableHeaders = element.select("td, th");
				}
			}

			for (Element element : tableHeaders) {
				headers.add(StringUtil.trim(element.text()));
			}

			for (Element element : table.select("tbody tr")) {
				Elements tableColumns = element.select("td, th");

				if (tableColumns.isEmpty()) {
					continue;
				}

				for (int i = 0; i < tableColumns.size(); i++) {
					String label =
						(i < headers.size()) ? headers.get(i) :
							"Column " + (i + 1);

					sb.append(label);

					sb.append(": ");
					sb.append(_normalizeCell(tableColumns.get(i)));
					sb.append(". ");
				}

				sb.append("\n");
			}

			_replaceElementWithText(table, "p", sb.toString());
		}

		for (Element list : document.select("ul, ol")) {
			StringBundler sb = new StringBundler();

			for (Element li : list.select("li")) {
				sb.append("- ");
				sb.append(li.text());
				sb.append("\n ,");
			}

			_replaceElementWithText(list, "p", sb.toString());
		}

		for (Element heading : document.select("h1, h2, h3, h4, h5, h6")) {
			Element paragraph = document.createElement("p");

			heading.after(paragraph.text(". "));
		}

		String content = document.text();

		for (String[] replacements : _LEARN_PATTERN_REPLACEMENTS) {
			content = _replace(content, replacements[1], replacements[0]);
		}

		return content;
	}

	private String _normalizeCell(Element element) {
		String text = element.text();

		if (_supportedValuesContentTable.contains(text)) {
			return "Supported";
		}

		if (_notSupportedValuesContentTable.contains(text)) {
			return "Not supported";
		}

		return StringUtil.trim(text);
	}

	private void _postUserBadge(long quizId, long userId) {
		JSONArray jsonArray = new JSONObject(
			get(
				_getAuthorization(),
				UriComponentsBuilder.fromPath(
					"/o/c/p2s3quizes/" + quizId + "/quizBadge"
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
					"/o/c/p2s3userbadges"
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
				"r_lUserToP2S3UserBadges_userId", userId
			).toString(),
			UriComponentsBuilder.fromPath(
				"/o/c/p2s3userbadges"
			).build(
			).toUri());
	}

	private String _replace(String string, String replacement, String regex) {
		Pattern pattern = Pattern.compile(regex);

		return pattern.matcher(
			string
		).replaceAll(
			replacement
		);
	}

	private void _replaceElementWithText(
		Element element, String tagName, String string) {

		Element replacementElement = new Element(tagName);

		replacementElement.text(string);

		element.replaceWith(replacementElement);
	}

	private List<String> _splitSsml(String ssml) {
		List<String> parts = new ArrayList<>();
		StringBundler sb = new StringBundler();

		String[] sentences = ssml.split("(?<=[.!?])\\s+");

		for (String sentence : sentences) {
			if ((sb.length() + sentence.length()) > 5000) {
				parts.add(StringUtil.trim(sb.toString()));
				sb = new StringBundler();
			}

			sb.append(sentence);
			sb.append(" ");
		}

		if (sb.length() > 0) {
			parts.add(StringUtil.trim(sb.toString()));
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

	private static final String[][] _LEARN_PATTERN_REPLACEMENTS = {
		{"[\\u00A0\\u200B]+", " "}, {"(?i)\\bLiferay\\b", "Life-ray"},
		{"(?i)\\bP\\s*a\\s*a\\s*S\\b", "pass"},
		{"(?i)\\bS\\s*a\\s*a\\s*S\\b", "saas"}, {"(?i)^<speak>|</speak>$", ""}
	};

	private static final Set<String> _notSupportedValuesContentTable =
		new HashSet<>(Arrays.asList("✖", "✕", "✗", ""));
	private static final Set<String> _supportedValuesContentTable =
		new HashSet<>(Arrays.asList("✔", "✓"));

	@Value("${liferay.learn.audio.lessons.document.folder.id}")
	private long _audioLessonsDocumentFolderId;

	@Value("${liferay.learn.google.credentials}")
	private String _googleCredentials;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

	@Value("${liferay.learn.dxp.site.group.id}")
	private String _siteGroupId;

	@Autowired
	private WebClient.Builder _webClientBuilder;

}