/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.github.comment.GitHubComment;
import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.PortalEvaluatePullRequestJobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.util.StringUtil;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class ReevaluateGitHubCommentEventHandler
	extends BaseGitHubCommentEventHandler {

	@Override
	public String process() throws InvalidJSONException {
		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		if (gitHubPullRequest == null) {
			return null;
		}

		Matcher reevaluateMatcher = _reevaluatePattern.matcher(
			gitHubPullRequest.getBody());

		if (!reevaluateMatcher.find()) {
			GitHubComment gitHubComment = gitHubPullRequest.comment(
				StringUtil.combine(
					"There is a missing or invalid build ID. ",
					"No reevaluation was triggered."));

			return gitHubComment.toString();
		}

		String jenkinsBuildID = reevaluateMatcher.group("jenkinsBuildID");

		invokeJobEntity(_createJobEntity(jenkinsBuildID));

		GitHubComment gitHubComment = gitHubPullRequest.comment(
			StringUtil.combine(
				"CI is reevaluating the build with build ID: `", jenkinsBuildID,
				"` against the latest valid upstream results."));

		if (_log.isInfoEnabled()) {
			_log.info(
				StringUtil.combine(
					"Reevaluation completed for ",
					gitHubPullRequest.getHTMLURL(), " at ",
					StringUtil.toString(new Date())));
		}

		return gitHubComment.toString();
	}

	protected ReevaluateGitHubCommentEventHandler(
		JSONObject messageJSONObject) {

		super(messageJSONObject);
	}

	private JobEntity _createJobEntity(String jenkinsBuildID)
		throws InvalidJSONException {

		JobEntityRepository jobEntityRepository =
			Jethr0ContextUtil.getJobEntityRepository();

		JobEntity jobEntity = jobEntityRepository.create(
			null, "ci:reevaluate:" + jenkinsBuildID, null, 3, null,
			JobEntity.State.OPENED,
			JobEntity.Type.PORTAL_EVALUATE_PULL_REQUEST);

		if (!(jobEntity instanceof PortalEvaluatePullRequestJobEntity)) {
			return null;
		}

		PortalEvaluatePullRequestJobEntity portalEvaluatePullRequestJobEntity =
			(PortalEvaluatePullRequestJobEntity)jobEntity;

		portalEvaluatePullRequestJobEntity.setJenkinsBuildID(jenkinsBuildID);
		portalEvaluatePullRequestJobEntity.setJenkinsSlaveLabel("!master");

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		if (gitHubPullRequest != null) {
			portalEvaluatePullRequestJobEntity.setPortalPullRequestURL(
				gitHubPullRequest.getHTMLURL());
		}

		jobEntityRepository.update(portalEvaluatePullRequestJobEntity);

		return portalEvaluatePullRequestJobEntity;
	}

	private static final Log _log = LogFactory.getLog(
		StopGitHubCommentEventHandler.class);

	private static final Pattern _reevaluatePattern = Pattern.compile(
		"ci:reevaluate:(?<jenkinsBuildID>[\\d]+_[\\d]+)");

}