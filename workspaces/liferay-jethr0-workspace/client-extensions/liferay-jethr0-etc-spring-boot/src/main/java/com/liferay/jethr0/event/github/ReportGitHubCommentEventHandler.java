/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.event.github.user.GitHubUser;
import com.liferay.jethr0.job.GenerateTestrayCSVJobEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class ReportGitHubCommentEventHandler
	extends BaseGitHubCommentEventHandler {

	@Override
	public String process() throws InvalidJSONException {
		GenerateTestrayCSVJobEntity generateTestrayCSVJobEntity =
			_createGenerateTestrayCSVJobEntity();

		if (generateTestrayCSVJobEntity == null) {
			return null;
		}

		invokeJobEntity(generateTestrayCSVJobEntity);

		return generateTestrayCSVJobEntity.toString();
	}

	protected ReportGitHubCommentEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	private GenerateTestrayCSVJobEntity _createGenerateTestrayCSVJobEntity()
		throws InvalidJSONException {

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		if (gitHubPullRequest == null) {
			return null;
		}

		Matcher testrayBuildIDMatcher = _testrayBuildIDPattern.matcher(
			gitHubPullRequest.getBody());

		if (!testrayBuildIDMatcher.find()) {
			return null;
		}

		String testrayBuildID = testrayBuildIDMatcher.group("testrayBuildID");

		JobEntityRepository jobEntityRepository =
			Jethr0ContextUtil.getJobEntityRepository();

		GitHubUser receiverGitHubUser =
			gitHubPullRequest.getReceiverGitHubUser();

		String jobName = StringUtil.combine(
			"ci:report - " + receiverGitHubUser.getName(), "#",
			gitHubPullRequest.getNumber(), " ", testrayBuildID);

		JobEntity jobEntity = jobEntityRepository.create(
			null, jobName, null, 3, null, JobEntity.State.OPENED,
			JobEntity.Type.GENERATE_TESTRAY_CSV);

		if (!(jobEntity instanceof GenerateTestrayCSVJobEntity)) {
			throw new RuntimeException("Invalid job type");
		}

		GenerateTestrayCSVJobEntity generateTestrayCSVJobEntity =
			(GenerateTestrayCSVJobEntity)jobEntity;

		generateTestrayCSVJobEntity.setJenkinsSlaveLabel("!master");
		generateTestrayCSVJobEntity.setPortalPullRequestURL(
			gitHubPullRequest.getHTMLURL());
		generateTestrayCSVJobEntity.setTestrayBuildID(
			Long.valueOf(testrayBuildID));

		jobEntityRepository.update(generateTestrayCSVJobEntity);

		return generateTestrayCSVJobEntity;
	}

	private static final Pattern _testrayBuildIDPattern = Pattern.compile(
		"ci:report:(?<testrayBuildID>[\\d]+)");

}