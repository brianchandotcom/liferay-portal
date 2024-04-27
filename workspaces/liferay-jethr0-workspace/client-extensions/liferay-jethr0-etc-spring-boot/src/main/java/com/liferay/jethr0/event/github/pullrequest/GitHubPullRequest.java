/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github.pullrequest;

import com.liferay.jethr0.event.github.GitHubFactory;
import com.liferay.jethr0.event.github.client.GitHubClient;
import com.liferay.jethr0.event.github.comment.GitHubComment;
import com.liferay.jethr0.event.github.commit.GitHubCommit;
import com.liferay.jethr0.event.github.file.GitHubFile;
import com.liferay.jethr0.event.github.ref.GitHubRef;
import com.liferay.jethr0.event.github.repository.GitHubRepository;
import com.liferay.jethr0.event.github.status.GitHubStatus;
import com.liferay.jethr0.event.github.user.GitHubUser;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class GitHubPullRequest {

	public GitHubPullRequest(
		GitHubFactory gitHubFactory, JSONObject jsonObject) {

		_gitHubFactory = gitHubFactory;
		_jsonObject = jsonObject;

		JSONObject baseJSONObject = jsonObject.getJSONObject("base");

		_baseBranchName = baseJSONObject.getString("ref");
		_baseGitHubCommit = _gitHubFactory.newGitHubCommit(baseJSONObject);

		_baseGitHubRepository = _gitHubFactory.newGitHubRepository(
			baseJSONObject.getJSONObject("repo"));

		URL baseGitHubRefURL = StringUtil.toURL(
			StringUtil.combine(
				_baseGitHubRepository.getHTMLURL(), "/tree/",
				baseJSONObject.getString("ref")));

		_baseGitHubRef = _gitHubFactory.newGitHubRef(
			baseGitHubRefURL, _baseGitHubCommit, baseJSONObject);

		JSONObject headJSONObject = jsonObject.getJSONObject("head");

		_headBranchName = headJSONObject.getString("ref");
		_headGitHubCommit = _gitHubFactory.newGitHubCommit(headJSONObject);

		_headGitHubRepository = _gitHubFactory.newGitHubRepository(
			headJSONObject.getJSONObject("repo"));

		URL headGitHubRefURL = StringUtil.toURL(
			StringUtil.combine(
				_headGitHubRepository.getHTMLURL(), "/tree/",
				headJSONObject.getString("ref")));

		_headGitHubRef = _gitHubFactory.newGitHubRef(
			headGitHubRefURL, _headGitHubCommit, baseJSONObject);

		_originGitHubUser = _gitHubFactory.newGitHubUser(
			headJSONObject.getJSONObject("user"));

		_receiverGitHubUser = _gitHubFactory.newGitHubUser(
			baseJSONObject.getJSONObject("user"));

		_senderGitHubUser = _gitHubFactory.newGitHubUser(
			jsonObject.getJSONObject("user"));
	}

	public void close() {
		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("state", "closed");

		GitHubClient gitHubClient = getGitHubClient();

		gitHubClient.requestPatch(getAPIURL(), requestJSONObject);
	}

	public GitHubComment comment(String body) {
		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("body", body);

		GitHubClient gitHubClient = getGitHubClient();

		JSONObject responseJSONObject = new JSONObject(
			gitHubClient.requestPost(getCommentsURL(), requestJSONObject));

		return _gitHubFactory.newGitHubComment(responseJSONObject);
	}

	public URL getAPIURL() {
		return StringUtil.toURL(_jsonObject.getString("url"));
	}

	public String getBaseBranchName() {
		return _baseBranchName;
	}

	public String getBaseBranchSHA() {
		return _baseGitHubCommit.getSHA();
	}

	public GitHubRef getBaseGitHubRef() {
		return _baseGitHubRef;
	}

	public String getBaseRepositoryName() {
		return _baseGitHubRepository.getName();
	}

	public String getBody() {
		return _jsonObject.getString("body");
	}

	public GitHubFile getCIMergeGitHubFile() {
		for (GitHubFile gitHubFile : getGitHubFiles()) {
			String gitHubFileName = gitHubFile.getName();

			if (gitHubFileName.endsWith("/ci-merge")) {
				return gitHubFile;
			}
		}

		return null;
	}

	public URL getCommentsURL() {
		return StringUtil.toURL(_jsonObject.getString("comments_url"));
	}

	public URL getCommitsURL() {
		return StringUtil.toURL(_jsonObject.getString("commits_url"));
	}

	public GitHubCommit getCommonParentGitHubCommit() {
		List<GitHubCommit> gitHubCommits = getGitHubCommits();

		GitHubCommit firstGitHubCommit = gitHubCommits.get(0);

		List<GitHubCommit> parentGitHubCommits =
			firstGitHubCommit.getParentGitHubCommits();

		return parentGitHubCommits.get(0);
	}

	public Set<String> getCompletedTestSuites() {
		Set<String> completedTestSuites = new HashSet<>();

		for (GitHubStatus gitHubStatus : getGitHubStatuses()) {
			Matcher matcher = _completedTestSuiteStatusPattern.matcher(
				gitHubStatus.getDescription());

			if (matcher.find()) {
				completedTestSuites.add(matcher.group("testSuite"));
			}
		}

		return completedTestSuites;
	}

	public URL getFilesURL() {
		return StringUtil.toURL(_jsonObject.getString("url") + "/files");
	}

	public GitHubClient getGitHubClient() {
		return _gitHubFactory.getGitHubClient();
	}

	public List<GitHubComment> getGitHubComments() {
		List<GitHubComment> gitHubComments = new ArrayList<>();

		GitHubClient gitHubClient = getGitHubClient();

		JSONArray commentsJSONArray = new JSONArray(
			gitHubClient.requestGet(getCommentsURL()));

		for (int i = 0; i < commentsJSONArray.length(); i++) {
			gitHubComments.add(
				_gitHubFactory.newGitHubComment(
					commentsJSONArray.getJSONObject(i)));
		}

		return gitHubComments;
	}

	public List<GitHubCommit> getGitHubCommits() {
		if (_gitHubCommits != null) {
			return _gitHubCommits;
		}

		_gitHubCommits = new ArrayList<>();

		GitHubClient gitHubClient = getGitHubClient();

		JSONArray commitsJSONArray = new JSONArray(
			gitHubClient.requestGet(getCommitsURL()));

		for (int i = 0; i < commitsJSONArray.length(); i++) {
			_gitHubCommits.add(
				_gitHubFactory.newGitHubCommit(
					commitsJSONArray.getJSONObject(i)));
		}

		return _gitHubCommits;
	}

	public List<GitHubFile> getGitHubFiles() {
		if (_gitHubFiles != null) {
			return _gitHubFiles;
		}

		_gitHubFiles = new ArrayList<>();

		GitHubClient gitHubClient = getGitHubClient();

		JSONArray filesJSONArray = new JSONArray(
			gitHubClient.requestGet(getFilesURL()));

		for (int i = 0; i < filesJSONArray.length(); i++) {
			_gitHubFiles.add(
				_gitHubFactory.newGitHubFile(filesJSONArray.getJSONObject(i)));
		}

		return _gitHubFiles;
	}

	public Set<GitHubStatus> getGitHubStatuses() {
		if (_gitHubStatuses != null) {
			return _gitHubStatuses;
		}

		_gitHubStatuses = new HashSet<>();

		GitHubClient gitHubClient = getGitHubClient();

		JSONArray statusesJSONArray = new JSONArray(
			gitHubClient.requestGet(getStatusesURL()));

		for (int i = 0; i < statusesJSONArray.length(); i++) {
			_gitHubStatuses.add(
				_gitHubFactory.newGitHubStatus(
					statusesJSONArray.getJSONObject(i)));
		}

		return _gitHubStatuses;
	}

	public String getGitRepoFilePath() {
		GitHubFile ciMergeGitHubFile = getCIMergeGitHubFile();

		if (ciMergeGitHubFile == null) {
			return null;
		}

		String ciMergeGitHubFileName = ciMergeGitHubFile.getName();

		return ciMergeGitHubFileName.replaceAll("/ci-merge", ".gitrepo");
	}

	public String getHeadBranchName() {
		return _headBranchName;
	}

	public String getHeadBranchSHA() {
		return _headGitHubCommit.getSHA();
	}

	public URL getHeadBranchURL() {
		return StringUtil.toURL(
			StringUtil.combine(
				_headGitHubRepository.getHTMLURL(), "/tree/",
				getHeadBranchName()));
	}

	public GitHubRef getHeadGitHubRef() {
		return _headGitHubRef;
	}

	public URL getHTMLURL() {
		return StringUtil.toURL(_jsonObject.getString("html_url"));
	}

	public URL getIssueLockURL() {
		return StringUtil.toURL(getIssueURL() + "/lock");
	}

	public URL getIssueURL() {
		return StringUtil.toURL(_jsonObject.getString("issue_url"));
	}

	public Long getNumber() {
		return _jsonObject.getLong("number");
	}

	public GitHubUser getOriginGitHubUser() {
		return _originGitHubUser;
	}

	public Set<String> getPassingTestSuites() {
		Set<String> passingTestSuites = new HashSet<>();

		for (GitHubStatus gitHubStatus : getGitHubStatuses()) {
			Matcher matcher = _passingTestSuiteStatusPattern.matcher(
				gitHubStatus.getDescription());

			if (matcher.find()) {
				passingTestSuites.add(matcher.group("testSuite"));
			}
		}

		return passingTestSuites;
	}

	public GitHubUser getReceiverGitHubUser() {
		return _receiverGitHubUser;
	}

	public GitHubUser getSenderGitHubUser() {
		return _senderGitHubUser;
	}

	public URL getStatusesURL() {
		return StringUtil.toURL(_jsonObject.getString("statuses_url"));
	}

	public URL getUpstreamBranchURL() {
		return StringUtil.toURL(
			StringUtil.combine(
				"https://github.com/liferay/", getBaseRepositoryName(),
				"/tree/", getBaseBranchName()));
	}

	public boolean isMergeSubrepositoryPullRequest() {
		GitHubFile ciMergeGitHubFile = getCIMergeGitHubFile();

		if (ciMergeGitHubFile != null) {
			return true;
		}

		return false;
	}

	public void lock() {
		GitHubClient gitHubClient = getGitHubClient();

		gitHubClient.requestPut(getIssueLockURL(), null);
	}

	public void open() {
		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("state", "open");

		GitHubClient gitHubClient = getGitHubClient();

		JSONObject responseJSONObject = new JSONObject(
			gitHubClient.requestPatch(getAPIURL(), requestJSONObject));

		JSONArray errorsJSONArray = responseJSONObject.optJSONArray("errors");

		if ((errorsJSONArray == null) || errorsJSONArray.isEmpty()) {
			return;
		}

		for (int i = 0; i < errorsJSONArray.length(); i++) {
			JSONObject errorJSONObject = errorsJSONArray.getJSONObject(i);

			String message = errorJSONObject.optString("message");

			if (StringUtil.isNullOrEmpty(message)) {
				continue;
			}

			comment("GitHub error message: " + message);
		}
	}

	private static final Pattern _completedTestSuiteStatusPattern =
		Pattern.compile(
			"\"ci:test:(?<testSuite>[^\"]+)\"\\s*has (FAILED|PASSED).");
	private static final Pattern _passingTestSuiteStatusPattern =
		Pattern.compile("\"ci:test:(?<testSuite>[^\"]+)\"\\s*has PASSED.");

	private final String _baseBranchName;
	private final GitHubCommit _baseGitHubCommit;
	private final GitHubRef _baseGitHubRef;
	private final GitHubRepository _baseGitHubRepository;
	private List<GitHubCommit> _gitHubCommits;
	private final GitHubFactory _gitHubFactory;
	private List<GitHubFile> _gitHubFiles;
	private Set<GitHubStatus> _gitHubStatuses;
	private final String _headBranchName;
	private final GitHubCommit _headGitHubCommit;
	private final GitHubRef _headGitHubRef;
	private final GitHubRepository _headGitHubRepository;
	private final JSONObject _jsonObject;
	private final GitHubUser _originGitHubUser;
	private final GitHubUser _receiverGitHubUser;
	private final GitHubUser _senderGitHubUser;

}