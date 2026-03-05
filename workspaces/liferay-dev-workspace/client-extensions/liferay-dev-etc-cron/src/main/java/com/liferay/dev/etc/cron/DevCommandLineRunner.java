/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dev.etc.cron;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.headless.admin.user.client.dto.v1_0.RoleBrief;
import com.liferay.headless.admin.user.client.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.client.resource.v1_0.UserAccountResource;
import com.liferay.headless.admin.workflow.client.dto.v1_0.ChangeTransition;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTask;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTaskAssignToMe;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTasksBulkSelection;
import com.liferay.headless.admin.workflow.client.resource.v1_0.WorkflowTaskResource;
import com.liferay.headless.delivery.client.dto.v1_0.BlogPosting;
import com.liferay.headless.delivery.client.dto.v1_0.Creator;
import com.liferay.headless.delivery.client.resource.v1_0.BlogPostingResource;
import com.liferay.petra.string.StringBundler;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Ryan Schuhler
 */
@Component
public class DevCommandLineRunner
	extends BaseService implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		try {
			if (_log.isInfoEnabled()) {
				_log.info("Running workflow approval cron job");
			}

			_approveStaffBlogs();
		}
		catch (Exception exception) {
			_log.error("Error running workflow approval cron job", exception);
		}
	}

	private void _approveStaffBlogs() throws Exception {
		String authorization =
			_liferayOAuth2AccessTokenManager.getAuthorization(
				_liferayOAuthApplicationExternalReferenceCodes);

		URL endpoint = new URL(lxcDXPServerProtocol + "://" + lxcDXPMainDomain);

		BlogPostingResource blogPostingResource = BlogPostingResource.builder(
		).header(
			"Authorization", authorization
		).endpoint(
			endpoint
		).build();

		UserAccountResource userAccountResource = UserAccountResource.builder(
		).header(
			"Authorization", authorization
		).endpoint(
			endpoint
		).build();

		WorkflowTaskResource workflowTaskResource =
			WorkflowTaskResource.builder(
			).header(
				"Authorization", authorization
			).endpoint(
				endpoint
			).build();

		WorkflowTasksBulkSelection workflowTasksBulkSelection =
			new WorkflowTasksBulkSelection();

		workflowTasksBulkSelection.setAssetTypes(
			() -> new String[] {"com.liferay.blogs.model.BlogsEntry"});
		workflowTasksBulkSelection.setCompleted(() -> false);

		Collection<WorkflowTask> workflowTasks =
			workflowTaskResource.postWorkflowTasksPage(
				null, null, workflowTasksBulkSelection
			).getItems();

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Found ", workflowTasks.size(), " pending blog tasks"));
		}

		List<Long> approvedBlogPostingIds = new ArrayList<>();
		List<Long> staffBlogPostingIds = new ArrayList<>();

		for (WorkflowTask workflowTask : workflowTasks) {
			try {
				boolean approved = _processWorkflowTask(
					workflowTask, blogPostingResource, userAccountResource,
					workflowTaskResource, staffBlogPostingIds);

				if (approved) {
					approvedBlogPostingIds.add(
						workflowTask.getObjectReviewed(
						).getId());
				}
			}
			catch (Exception exception) {
				_log.error(
					StringBundler.concat(
						"Failed to process workflow task ",
						workflowTask.getId(), " for blog entry ",
						workflowTask.getObjectReviewed(
						).getId()),
					exception);
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("Staff blogs: " + staffBlogPostingIds);
			_log.info("Updated blogs: " + approvedBlogPostingIds);
		}
	}

	private boolean _processWorkflowTask(
			WorkflowTask workflowTask, BlogPostingResource blogPostingResource,
			UserAccountResource userAccountResource,
			WorkflowTaskResource workflowTaskResource,
			List<Long> staffBlogPostingIds)
		throws Exception {

		BlogPosting blogPosting = blogPostingResource.getBlogPosting(
			workflowTask.getObjectReviewed(
			).getId());

		Creator creator = blogPosting.getCreator();

		if (creator == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Task ", workflowTask.getId(), " has no creator for ",
						blogPosting.getHeadline()));
			}

			return false;
		}

		UserAccount userAccount = userAccountResource.getUserAccount(
			creator.getId());

		String emailAddress = userAccount.getEmailAddress();

		String returnValue = "Review";

		if ((emailAddress != null) && emailAddress.endsWith("@liferay.com")) {
			returnValue = "AutoApprove";
		}

		RoleBrief[] roleBriefs = userAccount.getRoleBriefs();

		if (roleBriefs != null) {
			for (RoleBrief roleBrief : roleBriefs) {
				if (Objects.equals(roleBrief.getName(), "Administrator")) {
					returnValue = "AutoApprove";

					break;
				}
			}
		}

		if (Objects.equals(returnValue, "AutoApprove")) {
			staffBlogPostingIds.add(
				workflowTask.getObjectReviewed(
				).getId());

			workflowTaskResource.postWorkflowTaskAssignToMe(
				workflowTask.getId(), new WorkflowTaskAssignToMe());

			ChangeTransition changeTransition = new ChangeTransition();

			changeTransition.setTransitionName(() -> "approve");

			workflowTaskResource.postWorkflowTaskChangeTransition(
				workflowTask.getId(), changeTransition);

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactory.getLog(
		DevCommandLineRunner.class);

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${liferay.oauth.application.external.reference.codes}")
	private String _liferayOAuthApplicationExternalReferenceCodes;

}