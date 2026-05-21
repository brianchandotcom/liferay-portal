/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.rest.internal.resource.v1_0;

import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.testray.rest.dto.v1_0.TestrayBuild;
import com.liferay.testray.rest.manager.TestrayManager;
import com.liferay.testray.rest.resource.v1_0.TestrayBuildResource;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Nilton Vieira
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/testray-build.properties",
	scope = ServiceScope.PROTOTYPE, service = TestrayBuildResource.class
)
public class TestrayBuildResourceImpl extends BaseTestrayBuildResourceImpl {

	@Override
	public TestrayBuild patchTestrayBuild(Long testrayBuildId)
		throws Exception {

		ObjectEntry objectEntry = _testrayManager.updateTestrayBuildSummary(
			contextCompany.getCompanyId(), testrayBuildId,
			contextUser.getUserId());

		Map<String, Serializable> values = objectEntry.getValues();

		return new TestrayBuild() {
			{
				setArchived(
					() -> GetterUtil.getBoolean(values.get("archived")));
				setCaseResultBlocked(
					() -> GetterUtil.getInteger(
						values.get("caseResultBlocked")));
				setCaseResultDidNotRun(
					() -> GetterUtil.getInteger(
						values.get("caseResultDidNotRun")));
				setCaseResultFailed(
					() -> GetterUtil.getInteger(
						values.get("caseResultFailed")));
				setCaseResultIncomplete(
					() -> GetterUtil.getInteger(
						values.get("caseResultIncomplete")));
				setCaseResultInProgress(
					() -> GetterUtil.getInteger(
						values.get("caseResultInProgress")));
				setCaseResultPassed(
					() -> GetterUtil.getInteger(
						values.get("caseResultPassed")));
				setCaseResultTestFix(
					() -> GetterUtil.getInteger(
						values.get("caseResultTestFix")));
				setCaseResultUntested(
					() -> GetterUtil.getInteger(
						values.get("caseResultUntested")));
				setCpuUseTime(
					() -> GetterUtil.getString(values.get("cpuUseTime")));
				setDateArchived(
					() -> String.valueOf(
						GetterUtil.get(
							values.get("dateArchived"), null, null)));
				setDescription(
					() -> GetterUtil.getString(values.get("description")));
				setDueDate(
					() -> String.valueOf(
						GetterUtil.get(values.get("dueDate"), null, null)));
				setDueStatus(
					() -> GetterUtil.getString(values.get("dueStatus")));
				setGitHash(() -> GetterUtil.getString(values.get("gitHash")));
				setGithubCompareURLs(
					() -> GetterUtil.getString(
						values.get("githubCompareURLs")));
				setName(() -> GetterUtil.getString(values.get("name")));
				setPromoted(
					() -> GetterUtil.getBoolean(values.get("promoted")));
				setR_productVersionToBuilds_c_productVersionId(
					() -> GetterUtil.getLong(
						values.get(
							"r_productVersionToBuilds_c_productVersionId")));
				setR_projectToBuilds_c_projectId(
					() -> GetterUtil.getLong(
						values.get("r_projectToBuilds_c_projectId")));
				setR_routineToBuilds_c_routineId(
					() -> GetterUtil.getLong(
						values.get("r_routineToBuilds_c_routineId")));
				setTemplate(
					() -> GetterUtil.getBoolean(values.get("template")));
				setTemplateTestrayBuildId(
					() -> GetterUtil.getLong(
						values.get("templateTestrayBuildId")));
			}
		};
	}

	@Reference
	private TestrayManager _testrayManager;

}