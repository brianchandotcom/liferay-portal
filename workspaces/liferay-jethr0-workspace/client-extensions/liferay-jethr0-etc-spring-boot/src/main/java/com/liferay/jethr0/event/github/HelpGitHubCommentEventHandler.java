/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.github.comment.GitHubComment;
import com.liferay.jethr0.event.github.issue.GitHubIssue;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class HelpGitHubCommentEventHandler
	extends BaseGitHubCommentEventHandler {

	@Override
	public String process() throws InvalidJSONException, IOException {
		if (closeInvalidUpstreamGitHubBranchName()) {
			return null;
		}

		GitHubIssue gitHubIssue = getGitHubIssue();

		GitHubComment gitHubComment = gitHubIssue.createGitHubComment(
			_getMessage());

		return gitHubComment.getBody();
	}

	protected HelpGitHubCommentEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	private String _getAvailableTestSuitesMessage()
		throws InvalidJSONException, IOException {

		StringBuilder sb = new StringBuilder();

		Set<String> availableTestSuites = getAvailableTestSuites();

		if (!availableTestSuites.isEmpty()) {
			sb.append("#### ci:test[:suite][:SHA|nocompile|");
			sb.append("norebase|gist-SHA]\n");
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Test the pull request. ");
			sb.append("Optionally specify the name of the test suite ");
			sb.append("and/or optionally specify the upstream SHA to ");
			sb.append("test against or use \"nocompile\" to test ");
			sb.append("with a prebuilt bundle or use \"norebase\" to ");
			sb.append("test without rebasing.\n");
			sb.append("<details><summary><strong>Available test suites:");
			sb.append("</strong></summary>\n\n");

			List<String> orderedTestSuites = new ArrayList<>(
				availableTestSuites);

			Collections.sort(orderedTestSuites);

			for (String testSuite : orderedTestSuites) {
				sb.append("- &nbsp;&nbsp;&nbsp;&nbsp;ci:test:**");
				sb.append(testSuite);
				sb.append("** - ");

				String ciTestSuiteDescription = getCIProperty(
					"ci.test.suite.description[" + testSuite + "]");

				if (ciTestSuiteDescription != null) {
					sb.append(ciTestSuiteDescription);
				}
				else {
					sb.append("No description is available.");
				}

				sb.append("\n");
			}

			sb.append("</details>\n");
		}
		else {
			sb.append("#### ci:test[:SHA|nocompile|norebase|gist-SHA]\n");
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Test the pull request");
			sb.append(". Optionally specify the upstream SHA to test ");
			sb.append("against or use \"nocompile\" to test with a ");
			sb.append("prebuilt bundle or use \"norebase\" to test ");
			sb.append("without rebasing.\n");
		}

		return sb.toString();
	}

	private String _getMessage() throws InvalidJSONException, IOException {
		StringBuilder sb = new StringBuilder();

		sb.append("## Available CI commands:\n");
		sb.append("#### ci:close\n");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Close the pull request.\n");

		String ciForwardReceiverUsername = getJenkinsBranchBuildPropertyValue(
			"ci.forward.default.receiver.username");

		if ((ciForwardReceiverUsername != null) &&
			!ciForwardReceiverUsername.isEmpty()) {

			sb.append("#### ci:forward[:force]\n");
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Test the pull request ");
			sb.append(" and forward the pull request to `");
			sb.append(ciForwardReceiverUsername);
			sb.append("` if the required test suites pass for ");
			sb.append("`ci:forward` or complete for `ci:forward:force`.\n");
		}

		sb.append("#### ci:merge[:force]\n");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Merge in the changes from ");
		sb.append("the subrepo. All tests must pass before this ");
		sb.append("command will successfully run. Optionally use the ");
		sb.append("force flag to bypass failed tests.\n");
		sb.append("#### ci:reevaluate:[buildID]\n");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Reevaluate the pull request ");
		sb.append("result from a generated build ID.\n");
		sb.append("#### ci:reopen\n");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Reopen the pull request.\n");
		sb.append("#### ci:stop[:suite]\n");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Stop all currrently ");
		sb.append("running tests. Optionally specify the name of the ");
		sb.append("test suite.\n");
		sb.append(_getAvailableTestSuitesMessage());
		sb.append("\n");
		sb.append("For more details, see ");
		sb.append("<a href=\"https://liferay.atlassian.net/wiki/spaces/");
		sb.append("SUPPORT/pages/1957036035/CI+liferay-continuous-");
		sb.append("integration+GitHub+Commands\">");
		sb.append("CI GitHub Commands</a>");

		return sb.toString();
	}

}