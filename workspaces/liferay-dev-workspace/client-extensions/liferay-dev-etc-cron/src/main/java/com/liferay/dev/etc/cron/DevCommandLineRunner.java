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
import com.liferay.headless.admin.workflow.client.dto.v1_0.ObjectReviewed;
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
		List<Long> approvedBlogPostingIds = new ArrayList<>();

		String authorization =
			_liferayOAuth2AccessTokenManager.getAuthorization(
				_liferayOAuthApplicationExternalReferenceCodes);
		URL url = new URL(lxcDXPServerProtocol + "://" + lxcDXPMainDomain);

		BlogPostingResource blogPostingResource = BlogPostingResource.builder(
		).header(
			"Authorization", authorization
		).endpoint(
			url
		).build();

		List<Long> staffBlogPostingIds = new ArrayList<>();

		UserAccountResource userAccountResource = UserAccountResource.builder(
		).header(
			"Authorization", authorization
		).endpoint(
			url
		).build();
		WorkflowTaskResource workflowTaskResource =
			WorkflowTaskResource.builder(
			).header(
				"Authorization", authorization
			).endpoint(
				url
			).build();

		for (WorkflowTask workflowTask :
				_getWorkflowTasks(workflowTaskResource)) {

			ObjectReviewed objectReviewed = workflowTask.getObjectReviewed();

			try {
				boolean approved = _processWorkflowTask(
					blogPostingResource, staffBlogPostingIds,
					userAccountResource, workflowTask, workflowTaskResource);

				if (approved) {
					approvedBlogPostingIds.add(objectReviewed.getId());
				}
			}
			catch (Exception exception) {
				_log.error(
					StringBundler.concat(
						"Unable to process workflow task ",
						workflowTask.getId(), " for blog posting ",
						objectReviewed.getId()),
					exception);
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("Approved blog posting IDs: " + approvedBlogPostingIds);
			_log.info("Staff blog posting IDs: " + staffBlogPostingIds);
		}
	}

	private Collection<WorkflowTask> _getWorkflowTasks(
			WorkflowTaskResource workflowTaskResource)
		throws Exception {

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
					"Found ", workflowTasks.size(), " workflow tasks"));
		}

		return workflowTasks;
	}

	private boolean _processWorkflowTask(
			BlogPostingResource blogPostingResource,
			List<Long> staffBlogPostingIds,
			UserAccountResource userAccountResource, WorkflowTask workflowTask,
			WorkflowTaskResource workflowTaskResource)
		throws Exception {

		ObjectReviewed objectReviewed = workflowTask.getObjectReviewed();

		BlogPosting blogPosting = blogPostingResource.getBlogPosting(
			objectReviewed.getId());

		Creator creator = blogPosting.getCreator();

		if (creator == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Workflow task ", workflowTask.getId(),
						" has no creator for blog posting ",
						blogPosting.getHeadline()));
			}

			return false;
		}

		boolean approve = false;

		UserAccount userAccount = userAccountResource.getUserAccount(
			creator.getId());

		String emailAddress = userAccount.getEmailAddress();

		if ((emailAddress != null) && emailAddress.endsWith("@liferay.com")) {
			approve = true;
		}

		if (!approve) {
			RoleBrief[] roleBriefs = userAccount.getRoleBriefs();

			if (roleBriefs != null) {
				for (RoleBrief roleBrief : roleBriefs) {
					if (Objects.equals(roleBrief.getName(), "Administrator")) {
						approve = true;

						break;
					}
				}
			}
		}

		if (approve) {
			staffBlogPostingIds.add(objectReviewed.getId());

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