/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.WorkflowableComment;
import com.liferay.portal.kernel.model.AuditedModel;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.taglib.aui.base.BaseWorkflowStatusTag;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Objects;

/**
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
 *          com.liferay.portal.workflow.taglib.servlet.taglib.WorkflowStatusTag}
 */
@Deprecated
public class WorkflowStatusTag extends BaseWorkflowStatusTag {

	@Override
	protected String getPage() {
		return "/html/taglib/aui/workflow_status/page.jsp";
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		super.setAttributes(httpServletRequest);

		Object bean = getBean();

		if (bean == null) {
			bean = pageContext.getAttribute("aui:model-context:bean");
		}

		String helpMessage = getHelpMessage();

		if (Validator.isNull(helpMessage) &&
			(getStatus() == WorkflowConstants.STATUS_APPROVED) &&
			Validator.isNotNull(getVersion())) {

			helpMessage = _HELP_MESSAGE_DEFAULT;
		}

		Class<?> model = getModel();

		if (model == null) {
			model = (Class<?>)pageContext.getAttribute(
				"aui:model-context:model");
		}

		setNamespacedAttribute(httpServletRequest, "bean", bean);

		if ((bean != null) && (model != null) &&
			Objects.equals(getStatus(), WorkflowConstants.STATUS_PENDING)) {

			setNamespacedAttribute(
				httpServletRequest, "classPK", _getClassPK(bean));
			setNamespacedAttribute(
				httpServletRequest, "companyId", _getCompanyId(bean));
			setNamespacedAttribute(
				httpServletRequest, "groupId", _getGroupId(bean));
		}

		setNamespacedAttribute(httpServletRequest, "helpMessage", helpMessage);
		setNamespacedAttribute(httpServletRequest, "model", model);
	}

	private long _getClassPK(Object bean) {
		if (bean instanceof WorkflowableComment workflowableComment) {
			return workflowableComment.getPrimaryKey();
		}

		if (bean instanceof ClassedModel classedModel) {
			return GetterUtil.getLong(classedModel.getPrimaryKeyObj());
		}

		return 0;
	}

	private long _getCompanyId(Object bean) {
		if (bean instanceof AuditedModel auditedModel) {
			return auditedModel.getCompanyId();
		}

		if (bean instanceof ShardedModel shardedModel) {
			return shardedModel.getCompanyId();
		}

		if (bean instanceof WorkflowableComment workflowableComment) {
			return workflowableComment.getCompanyId();
		}

		return 0;
	}

	private long _getGroupId(Object bean) {
		if (bean instanceof Comment comment) {
			return comment.getGroupId();
		}

		return AUIUtil.getGroupId(bean, 0);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _HELP_MESSAGE_DEFAULT =
		"a-new-version-is-created-automatically-if-this-content-is-modified";

}