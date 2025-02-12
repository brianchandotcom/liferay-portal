/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.learn;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.client.extension.util.spring.boot3.LiferayOAuth2AccessTokenManager;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nilton Vieira
 */
@RequestMapping("/learn")
@RestController
public class LearnRestController extends BaseRestController {

	@GetMapping("/menu/items")
	@ResponseBody
	public ResponseEntity<Object> getMenuItems(
		@AuthenticationPrincipal Jwt jwt) {

		JSONObject jsonObject = new JSONObject(
			get(
				_getAuthorization(),
				"/o/object-admin/v1.0/object-folders" +
					"/by-external-reference-code" +
						"/P2S3_LEARNING_MANAGEMENT_SYSTEM"));

		return new ResponseEntity<>(
			TransformUtil.transform(
				jsonObject.getJSONArray(
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

		JSONObject jsonObject = new JSONObject(
			get(
				_getAuthorization(),
				StringBundler.concat(
					"/o/c/quizquestions/scopes/", _siteGroupId,
					"?filter=quizId eq '", quizId, "'&fields=id,position,",
					"question,questionType,quizAnswers,quizAnswers.answer,",
					"quizAnswers.id,quizAnswers.position&nestedFields=",
					"quizAnswers&pageSize=500&sort=position")));

		return new ResponseEntity<>(
			jsonObject.getJSONArray(
				"items"
			).toList(),
			HttpStatus.OK);
	}

	@PostMapping("/{quizId}/result")
	@ResponseBody
	public ResponseEntity<Object> postQuizResult(
			@AuthenticationPrincipal Jwt jwt, @PathVariable long quizId,
			@RequestBody Map<String, Object> map)
		throws Exception {

		return ResponseEntity.ok(
			_getQuizResult(
				map,
				new JSONObject(
					get(
						_getAuthorization(),
						StringBundler.concat(
							"/o/c/quizes/", quizId,
							"?&fields=id,r_quiz_c_moduleId,durationMinutes,",
							"passingScore,isKnowledgeCheck,quizQuestions.id,",
							"quizQuestions.position,quizQuestions.question,",
							"quizQuestions.questionType,quizQuestions.",
							"questionTotalScore,quizQuestions.quizAnswers,",
							"quizQuestions.quizAnswers.id,quizQuestions.",
							"quizAnswers.position,quizQuestions.quizAnswers.",
							"answer,quizQuestions.quizAnswers.score&",
							"nestedFields=quizQuestions,quizAnswers&",
							"nestedFieldsDepth=2&pageSize=500")))));
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-learn-etc-spring-boot-oauth-application-headless-server");
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
		Map<String, Object> answersMap, JSONObject quizJSONObject) {

		JSONArray quizQuestionsJSONArray = quizJSONObject.getJSONArray(
			"quizQuestions");

		Map<String, Object> map = HashMapBuilder.<String, Object>put(
			"passingScore", quizJSONObject.getInt("passingScore")
		).put(
			"selectedAnswers", answersMap
		).put(
			"totalQuestions", quizQuestionsJSONArray.length()
		).build();

		float achievedScore = 0;
		float totalScore = 0;
		int totalPassedQuestions = 0;

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

			int questionScore = 0;

			if (Objects.equals(
					scoreSheetJSONObject.getString("type"),
					"selectMultipleChoice")) {

				questionScore = _getQuizQuestionScore(
					(Map<String, Object>)answersMap.get(
						String.valueOf(quizQuestionJSONObject.getLong("id"))),
					quizQuestionJSONObject, scoreSheetJSONObject);
			}
			else {
				questionScore = _getQuizQuestionScore(
					Collections.singletonMap(
						String.valueOf(
							answersMap.get(
								String.valueOf(
									quizQuestionJSONObject.getLong("id")))),
						true),
					quizQuestionJSONObject, scoreSheetJSONObject);
			}

			if (questionScore > 0) {
				totalPassedQuestions++;
			}

			achievedScore += questionScore;
			scoreSheetJSONObject.put("achievedScore", questionScore);
			totalScore += quizQuestionJSONObject.getInt("questionTotalScore");

			scoreSheetJSONArray.put(scoreSheetJSONObject);
		}

		if (quizJSONObject.getBoolean("isKnowledgeCheck")) {
			map.put("scoreSheet", scoreSheetJSONArray.toList());
		}

		map.put(
			"passed",
			Math.round((achievedScore / totalScore) * 100) >=
				quizJSONObject.getInt("passingScore"));
		map.put("totalPassedQuestions", totalPassedQuestions);
		map.put("totalScore", Math.round((achievedScore / totalScore) * 100));

		return map;
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

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${liferay.learn.dxp.site.group.id}")
	private long _siteGroupId;

}