/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.event.jenkins.client.JenkinsClient;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.PortalPullRequestJobEntity;
import com.liferay.jethr0.job.PullRequestJobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.util.StringUtil;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PortalTestGitHubCommentEventHandler
	extends BaseTestGitHubCommentEventHandler {

	public URL getDistPortalBundlesBuildURL()
		throws InvalidJSONException, IOException {

		URL distPortalJobURL = _getDistPortalJobURL();

		JenkinsClient jenkinsClient = Jethr0ContextUtil.getJenkinsClient();

		String response = jenkinsClient.requestGet(
			StringUtil.toURL(
				distPortalJobURL + "/lastCompletedBuild/api/json"));

		JSONObject jsonObject = new JSONObject(response);

		int lastCompletedBuildNumber = jsonObject.getInt("number");

		Pattern distPortalBundleFileNamesPattern =
			_getDistPortalBundleFileNamesPattern();

		int buildNumber = lastCompletedBuildNumber + 1;

		while (buildNumber > Math.max(0, lastCompletedBuildNumber - 10)) {
			URL distPortalBundlesBuildURL = StringUtil.toURL(
				StringUtil.combine(distPortalJobURL, "/", buildNumber, "/"));

			try {
				String newResponse = jenkinsClient.requestGet(
					distPortalBundlesBuildURL);

				Matcher distPortalBundleFileNamesMatcher =
					distPortalBundleFileNamesPattern.matcher(newResponse);

				if (distPortalBundleFileNamesMatcher.find()) {
					return distPortalBundlesBuildURL;
				}
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}
			}

			buildNumber--;
		}

		return null;
	}

	protected PortalTestGitHubCommentEventHandler(
		JSONObject messageJSONObject) {

		super(messageJSONObject);

		String testSuiteName = "default";

		try {
			testSuiteName = getTestSuite();
		}
		catch (InvalidJSONException | IOException exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		_testSuiteName = testSuiteName;
	}

	@Override
	protected JobEntity.Type getJobEntityType() {
		if (_testSuiteName.equals("sf")) {
			return JobEntity.Type.PORTAL_PULL_REQUEST_SF;
		}

		return JobEntity.Type.PORTAL_PULL_REQUEST;
	}

	@Override
	protected int getJobPriority() {
		if (_testSuiteName.equals("sf")) {
			return 3;
		}

		return 5;
	}

	@Override
	protected PullRequestJobEntity getPullRequestJobEntity()
		throws InvalidJSONException, IOException {

		PullRequestJobEntity pullRequestJobEntity =
			super.getPullRequestJobEntity();

		if (pullRequestJobEntity instanceof PortalPullRequestJobEntity) {
			PortalPullRequestJobEntity portalPullRequestJobEntity =
				(PortalPullRequestJobEntity)pullRequestJobEntity;

			portalPullRequestJobEntity.setGitHubGistID(_getGitHubGistID());
			portalPullRequestJobEntity.setPortalBundlesDistURL(
				getDistPortalBundlesBuildURL());
		}

		return pullRequestJobEntity;
	}

	@Override
	protected String getRebaseBranchSHA()
		throws InvalidJSONException, IOException {

		if (!_testNoCompile()) {
			return super.getRebaseBranchSHA();
		}

		URL distPortalBundlesBuildURL = getDistPortalBundlesBuildURL();

		if (distPortalBundlesBuildURL == null) {
			return super.getRebaseBranchSHA();
		}

		PullRequestJobEntity pullRequestJobEntity = getPullRequestJobEntity();

		if (!(pullRequestJobEntity instanceof PortalPullRequestJobEntity)) {
			return super.getRebaseBranchSHA();
		}

		PortalPullRequestJobEntity portalPullRequestJobEntity =
			(PortalPullRequestJobEntity)pullRequestJobEntity;

		portalPullRequestJobEntity.setPortalBundlesDistURL(
			distPortalBundlesBuildURL);

		JobEntityRepository jobEntityRepository =
			Jethr0ContextUtil.getJobEntityRepository();

		jobEntityRepository.update(portalPullRequestJobEntity);

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		gitHubPullRequest.comment("The test will run with a prebuilt bundle.");

		JenkinsClient jenkinsClient = Jethr0ContextUtil.getJenkinsClient();

		String response = jenkinsClient.requestGet(
			StringUtil.toURL(distPortalBundlesBuildURL + "/git-hash"));

		return response.trim();
	}

	private Pattern _getDistPortalBundleFileNamesPattern()
		throws InvalidJSONException {

		GitBranchEntity upstreamGitBranchEntity = getUpstreamGitBranchEntity();

		try {
			String distPortalBundleFileNames =
				getJenkinsBranchBuildPropertyValue(
					"dist.portal.bundle.file.names",
					upstreamGitBranchEntity.getName());

			if (distPortalBundleFileNames == null) {
				distPortalBundleFileNames =
					_DIST_PORTAL_BUNDLE_FILE_NAMES_DEFAULT;
			}

			StringBuilder sb = new StringBuilder();

			List<String> distPortalBundleFileNamesList = new ArrayList<>();

			Collections.addAll(
				distPortalBundleFileNamesList,
				distPortalBundleFileNames.split("\\s*,\\s*"));

			Collections.sort(distPortalBundleFileNamesList);

			for (String distPortalBundleFileName :
					distPortalBundleFileNamesList) {

				String quotedDistPortalBundleFileName = Pattern.quote(
					distPortalBundleFileName);

				sb.append("\\<a href=\"");
				sb.append(quotedDistPortalBundleFileName);
				sb.append("\"\\>");
				sb.append(quotedDistPortalBundleFileName);
				sb.append("\\</a\\>.*");
			}

			sb.setLength(sb.length() - 2);

			return Pattern.compile(sb.toString(), Pattern.DOTALL);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to load build properties", ioException);
		}
	}

	private URL _getDistPortalJobURL()
		throws InvalidJSONException, IOException {

		GitBranchEntity upstreamGitBranchEntity = getUpstreamGitBranchEntity();

		try {
			return StringUtil.toURL(
				getJenkinsBranchBuildPropertyValue(
					"dist.portal.job.url", upstreamGitBranchEntity.getName()));
		}
		catch (MalformedURLException malformedURLException) {
			if (_log.isWarnEnabled()) {
				_log.warn(malformedURLException);
			}

			return null;
		}
	}

	private String _getGitHubGistID() throws InvalidJSONException {
		for (String testOption : getTestOptions()) {
			if (StringUtil.isNullOrEmpty(testOption)) {
				continue;
			}

			Matcher gitHubGistIDMatcher = _gitHubGistIDPattern.matcher(
				testOption);

			if (!gitHubGistIDMatcher.find()) {
				continue;
			}

			return gitHubGistIDMatcher.group("id");
		}

		return null;
	}

	private boolean _testNoCompile() throws InvalidJSONException {
		for (String testOption : getTestOptions()) {
			if (testOption.equals("nocompile")) {
				return true;
			}
		}

		return false;
	}

	private static final String _DIST_PORTAL_BUNDLE_FILE_NAMES_DEFAULT =
		"git-hash,liferay-portal-bundle-tomcat.tar.gz," +
			"liferay-portal-source.tar.gz";

	private static final Log _log = LogFactory.getLog(
		PortalTestGitHubCommentEventHandler.class);

	private static final Pattern _gitHubGistIDPattern = Pattern.compile(
		"gist-(?<id>[a-f0-9]{7,40})");

	private final String _testSuiteName;

}