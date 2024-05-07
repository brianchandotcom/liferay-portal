/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.rest.internal.dispatch.executor;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import com.liferay.dispatch.executor.BaseDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.nio.file.Files;
import java.nio.file.Path;

import java.sql.Timestamp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author José Abelenda
 */
@Component(
	property = {
		"dispatch.task.executor.name=testray-import-results",
		"dispatch.task.executor.overlapping=false",
		"dispatch.task.executor.type=testray-import-results"
	},
	service = DispatchTaskExecutor.class
)
public class TestrayImportResultsDispatchTaskExecutor
	extends BaseDispatchTaskExecutor {

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws Exception {

		UnicodeProperties unicodeProperties =
			dispatchTrigger.getDispatchTaskSettingsUnicodeProperties();

		if (Validator.isNull(unicodeProperties.getProperty("s3APIKey")) ||
			Validator.isNull(unicodeProperties.getProperty("s3BucketName")) ||
			Validator.isNull(
				unicodeProperties.getProperty("s3ErroredFolderName")) ||
			Validator.isNull(
				unicodeProperties.getProperty("s3InboxFolderName")) ||
			Validator.isNull(
				unicodeProperties.getProperty("s3ProcessedFolderName"))) {

			_log.error("The required properties are not set");

			return;
		}

		User user = _userLocalService.getUser(dispatchTrigger.getUserId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setTimeZone(user.getTimeZone());
		serviceContext.setUserId(user.getUserId());

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		String originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(user.getUserId());

		try {
			_objectDefinitionsMap = new HashMap<>();
			_objectEntryIds = new HashMap<>();

			_invoke(
				() -> _load(dispatchTrigger.getCompanyId(), user.getUserId()));

			_invoke(
				() -> _uploadToTestray(
					dispatchTrigger.getCompanyId(), serviceContext,
					unicodeProperties, user.getUserId()));
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
			PrincipalThreadLocal.setName(originalName);
		}
	}

	@Override
	public String getName() {
		return "testray-import-results";
	}

	@Override
	public boolean isClusterModeSingle() {
		return true;
	}

	private ObjectEntry _addObjectEntry(
			String shortName, ServiceContext serviceContext, long userId,
			Map<String, Serializable> values)
		throws Exception {

		return _objectEntryLocalService.addObjectEntry(
			userId, 0,
			_getObjectDefinition(
				shortName
			).getObjectDefinitionId(),
			values, serviceContext);
	}

	private JSONArray _addTestrayAttachments(Node testcaseNode)
		throws Exception {

		JSONArray jsonArray = null;

		Element testcaseElement = (Element)testcaseNode;

		NodeList attachmentsNodeList = testcaseElement.getElementsByTagName(
			"attachments");

		for (int i = 0; i < attachmentsNodeList.getLength(); i++) {
			Node attachmentsNode = attachmentsNodeList.item(i);

			if (attachmentsNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			Element attachmentsElement = (Element)attachmentsNode;

			NodeList fileNodeList = attachmentsElement.getElementsByTagName(
				"file");

			for (int j = 0; j < fileNodeList.getLength(); j++) {
				Node fileNode = fileNodeList.item(j);

				if (fileNode.getNodeType() != Node.ELEMENT_NODE) {
					continue;
				}

				Element fileElement = (Element)fileNode;

				jsonArray = JSONUtil.put(
					JSONUtil.put(
						"name", fileElement.getAttribute("name")
					).put(
						"url", fileElement.getAttribute("url")
					).put(
						"value", fileElement.getAttribute("value")
					));
			}
		}

		return jsonArray;
	}

	private void _addTestrayCase(
			long companyId, ServiceContext serviceContext, Node testcaseNode,
			long testrayBuildId, String testrayBuildTime,
			Map<String, Serializable> testrayCasePropertiesMap,
			long testrayProjectId, long testrayRunId, long userId)
		throws Exception {

		String testrayCaseName = (String)testrayCasePropertiesMap.get(
			"testray.testcase.name");

		String objectEntryIdsKey = StringBundler.concat(
			"Case#", testrayCaseName, "#ProjectId#", testrayProjectId);

		long testrayCaseId = _getObjectEntryId(
			companyId,
			StringBundler.concat(
				"projectId eq '", testrayProjectId, "' and name eq '",
				testrayCaseName, "'"),
			"Case", objectEntryIdsKey, userId);

		long testrayTeamId = _getTestrayTeamId(
			companyId, serviceContext, testrayProjectId,
			(String)testrayCasePropertiesMap.get("testray.team.name"), userId);

		long testrayComponentId = _getTestrayComponentId(
			companyId, serviceContext,
			(String)testrayCasePropertiesMap.get("testray.main.component.name"),
			testrayProjectId, testrayTeamId, userId);

		if (testrayCaseId == 0) {
			ObjectEntry objectEntry = _addObjectEntry(
				"Case", serviceContext, userId,
				HashMapBuilder.<String, Serializable>put(
					"description",
					testrayCasePropertiesMap.get("testray.testcase.description")
				).put(
					"name",
					(String)testrayCasePropertiesMap.get(
						"testray.testcase.name")
				).put(
					"number", 0
				).put(
					"priority",
					testrayCasePropertiesMap.get("testray.testcase.priority")
				).put(
					"r_caseTypeToCases_c_caseTypeId",
					_getTestrayCaseTypeId(
						companyId, serviceContext,
						(String)testrayCasePropertiesMap.get(
							"testray.case.type.name"),
						userId)
				).put(
					"r_componentToCases_c_componentId", testrayComponentId
				).put(
					"r_projectToCases_c_projectId", testrayProjectId
				).build());

			testrayCaseId = objectEntry.getObjectEntryId();

			_objectEntryIds.put(objectEntryIdsKey, testrayCaseId);
		}

		if (ListUtil.isEmpty(
				_getValuesList(
					companyId,
					StringBundler.concat(
						"buildId eq '", testrayBuildId, "' and caseId eq '",
						testrayCaseId, "'"),
					"BuildsCases", userId))) {

			_addObjectEntry(
				"BuildsCases", serviceContext, userId,
				HashMapBuilder.<String, Serializable>put(
					"r_buildToBuildsCases_c_buildId", testrayBuildId
				).put(
					"r_caseToBuildsCases_c_caseId", testrayCaseId
				).build());
		}

		_addTestrayCaseResult(
			serviceContext, testcaseNode, testrayBuildId, testrayBuildTime,
			testrayCaseId, testrayCasePropertiesMap, testrayComponentId,
			testrayRunId, userId);
	}

	private void _addTestrayCaseResult(
			ServiceContext serviceContext, Node testcaseNode,
			long testrayBuildId, String testrayBuildTime, long testrayCaseId,
			Map<String, Serializable> testrayCasePropertiesMap,
			long testrayComponentId, long testrayRunId, long userId)
		throws Exception {

		Map<String, Serializable> properties =
			HashMapBuilder.<String, Serializable>put(
				"attachments", _addTestrayAttachments(testcaseNode)
			).put(
				"closedDate", Timestamp.valueOf(testrayBuildTime)
			).put(
				"dueStatus",
				() -> {
					String testrayTestcaseStatus =
						(String)testrayCasePropertiesMap.get(
							"testray.testcase.status");

					if (testrayTestcaseStatus.equals("blocked")) {
						return "BLOCKED";
					}
					else if (testrayTestcaseStatus.equals("dnr")) {
						return "DIDNOTRUN";
					}
					else if (testrayTestcaseStatus.equals("failed")) {
						return "FAILED";
					}
					else if (testrayTestcaseStatus.equals("in-progress")) {
						return "INPROGRESS";
					}
					else if (testrayTestcaseStatus.equals("passed")) {
						return "PASSED";
					}
					else if (testrayTestcaseStatus.equals("test-fix")) {
						return "TESTFIX";
					}

					return "UNTESTED";
				}
			).put(
				"r_buildToCaseResult_c_buildId", testrayBuildId
			).put(
				"r_caseToCaseResult_c_caseId", testrayCaseId
			).put(
				"r_componentToCaseResult_c_componentId", testrayComponentId
			).put(
				"r_runToCaseResult_c_runId", testrayRunId
			).put(
				"startDate", Timestamp.valueOf(testrayBuildTime)
			).put(
				"warnings",
				GetterUtil.getInteger(
					testrayCasePropertiesMap.get("testray.testcase.warnings"))
			).build();

		Element element = (Element)testcaseNode;

		NodeList nodeList = element.getElementsByTagName("failure");

		Node failureNode = nodeList.item(0);

		if (failureNode != null) {
			String message = _getAttributeValue("message", failureNode);

			if (!message.isEmpty()) {
				properties.put("errors", message);
			}
		}

		_addObjectEntry("CaseResult", serviceContext, userId, properties);
	}

	private void _addTestrayCases(
			long companyId, Element element, ServiceContext serviceContext,
			long testrayBuildId, String testrayBuildTime, long testrayProjectId,
			long testrayRunId, long userId)
		throws Exception {

		NodeList testCaseNodeList = element.getElementsByTagName("testcase");

		for (int i = 0; i < testCaseNodeList.getLength(); i++) {
			Node testcaseNode = testCaseNodeList.item(i);

			Map<String, Serializable> testrayCasePropertiesMap =
				_getTestrayCaseProperties((Element)testcaseNode);

			_addTestrayCase(
				companyId, serviceContext, testcaseNode, testrayBuildId,
				testrayBuildTime, testrayCasePropertiesMap, testrayProjectId,
				testrayRunId, userId);
		}
	}

	private void _addTestrayFactor(
			ServiceContext serviceContext, long testrayFactorCategoryId,
			String testrayFactorCategoryName, long testrayFactorOptionId,
			String testrayFactorOptionName, long testrayRunId, long userId)
		throws Exception {

		_addObjectEntry(
			"Factor", serviceContext, userId,
			HashMapBuilder.<String, Serializable>put(
				"r_factorCategoryToFactors_c_factorCategoryId",
				testrayFactorCategoryId
			).put(
				"r_factorOptionToFactors_c_factorOptionId",
				testrayFactorOptionId
			).put(
				"r_runToFactors_c_runId", testrayRunId
			).put(
				"testrayFactorCategoryName", testrayFactorCategoryName
			).put(
				"testrayFactorOptionName", testrayFactorOptionName
			).build());
	}

	private String _getAttributeValue(String attributeName, Node node) {
		NamedNodeMap namedNodeMap = node.getAttributes();

		if (namedNodeMap == null) {
			return null;
		}

		Node attributeNode = namedNodeMap.getNamedItem(attributeName);

		if (attributeNode == null) {
			return null;
		}

		return attributeNode.getTextContent();
	}

	private ObjectDefinition _getObjectDefinition(
			String objectDefinitionShortName)
		throws Exception {

		ObjectDefinition objectDefinition = _objectDefinitionsMap.get(
			objectDefinitionShortName);

		if (objectDefinition == null) {
			throw new PortalException(
				"No object definition found with short name " +
					objectDefinitionShortName);
		}

		return objectDefinition;
	}

	private long _getObjectEntryId(
			long companyId, String filterString, String shortName,
			String objectEntryIdsKey, long userId)
		throws Exception {

		Long objectEntryId = _objectEntryIds.get(objectEntryIdsKey);

		if (objectEntryId != null) {
			return objectEntryId;
		}

		List<Map<String, Serializable>> valuesList = _getValuesList(
			companyId, filterString, shortName, userId);

		if (ListUtil.isNotEmpty(valuesList)) {
			Map<String, Serializable> values = valuesList.get(0);

			return GetterUtil.getLong(values.get("objectEntryId"));
		}

		return 0;
	}

	private Map<String, String> _getPropertiesMap(Element element) {
		Map<String, String> map = new HashMap<>();

		NodeList propertiesNodeList = element.getElementsByTagName(
			"properties");

		Node propertiesNode = propertiesNodeList.item(0);

		Element propertiesElement = (Element)propertiesNode;

		NodeList propertyNodeList = propertiesElement.getElementsByTagName(
			"property");

		for (int i = 0; i < propertyNodeList.getLength(); i++) {
			Node propertyNode = propertyNodeList.item(i);

			if (!propertyNode.hasAttributes()) {
				continue;
			}

			map.put(
				_getAttributeValue("name", propertyNode),
				_getAttributeValue("value", propertyNode));
		}

		return map;
	}

	private String _getTestrayBuildDescription(
		Map<String, String> propertiesMap) {

		StringBundler sb = new StringBundler(15);

		if (propertiesMap.get("liferay.portal.bundle") != null) {
			sb.append("Bundle: ");
			sb.append(propertiesMap.get("liferay.portal.bundle"));
			sb.append(StringPool.SEMICOLON);
			sb.append(StringPool.NEW_LINE);
		}

		if (propertiesMap.get("liferay.plugins.git.id") != null) {
			sb.append("Plugins hash: ");
			sb.append(propertiesMap.get("liferay.plugins.git.id"));
			sb.append(StringPool.SEMICOLON);
			sb.append(StringPool.NEW_LINE);
		}

		if (propertiesMap.get("liferay.portal.branch") != null) {
			sb.append("Portal branch: ");
			sb.append(propertiesMap.get("liferay.portal.branch"));
			sb.append(StringPool.SEMICOLON);
			sb.append(StringPool.NEW_LINE);
		}

		if (propertiesMap.get("liferay.portal.git.id") != null) {
			sb.append("Portal hash: ");
			sb.append(propertiesMap.get("liferay.portal.git.id"));
			sb.append(StringPool.SEMICOLON);
		}

		return sb.toString();
	}

	private long _getTestrayBuildId(
			long companyId, Map<String, String> propertiesMap,
			ServiceContext serviceContext, String testrayBuildName,
			long testrayProjectId, long testrayRoutineId, long userId)
		throws Exception {

		String objectEntryIdsKey = StringBundler.concat(
			"Build#", testrayBuildName, "#ProjectId#", testrayProjectId);

		long testrayBuildId = _getObjectEntryId(
			companyId,
			StringBundler.concat(
				"projectId eq '", testrayProjectId, "' and name eq '",
				testrayBuildName, "'"),
			"Build", objectEntryIdsKey, userId);

		if (testrayBuildId != 0) {
			return testrayBuildId;
		}

		long testrayProductVersionId = _getTestrayProductVersionId(
			companyId, serviceContext,
			propertiesMap.get("testray.product.version"), testrayProjectId,
			userId);

		ObjectEntry objectEntry = _addObjectEntry(
			"Build", serviceContext, userId,
			HashMapBuilder.<String, Serializable>put(
				"description", _getTestrayBuildDescription(propertiesMap)
			).put(
				"dueDate",
				Timestamp.valueOf(propertiesMap.get("testray.build.date"))
			).put(
				"dueStatus", "ACTIVATED"
			).put(
				"gitHash", propertiesMap.get("git.id")
			).put(
				"githubCompareURLs", propertiesMap.get("liferay.compare.urls")
			).put(
				"name", testrayBuildName
			).put(
				"r_productVersionToBuilds_c_productVersionId",
				testrayProductVersionId
			).put(
				"r_projectToBuilds_c_projectId", testrayProjectId
			).put(
				"r_routineToBuilds_c_routineId", testrayRoutineId
			).build());

		testrayBuildId = objectEntry.getObjectEntryId();

		_objectEntryIds.put(objectEntryIdsKey, testrayBuildId);

		return testrayBuildId;
	}

	private Map<String, Serializable> _getTestrayCaseProperties(
		Element element) {

		Map<String, Serializable> map = new HashMap<>();

		NodeList propertiesNodeList = element.getElementsByTagName(
			"properties");

		Node propertiesNode = propertiesNodeList.item(0);

		Element propertiesElement = (Element)propertiesNode;

		NodeList propertyNodeList = propertiesElement.getElementsByTagName(
			"property");

		for (int i = 0; i < propertyNodeList.getLength(); i++) {
			Node propertyNode = propertyNodeList.item(i);

			if (!propertyNode.hasAttributes()) {
				continue;
			}

			map.put(
				_getAttributeValue("name", propertyNode),
				_getAttributeValue("value", propertyNode));
		}

		return map;
	}

	private long _getTestrayCaseTypeId(
			long companyId, ServiceContext serviceContext,
			String testrayCaseTypeName, long userId)
		throws Exception {

		String objectEntryIdsKey = "CaseType#" + testrayCaseTypeName;

		long testrayCaseTypeId = _getObjectEntryId(
			companyId, "name eq '" + testrayCaseTypeName + "'", "CaseType",
			objectEntryIdsKey, userId);

		if (testrayCaseTypeId != 0) {
			return testrayCaseTypeId;
		}

		ObjectEntry objectEntry = _addObjectEntry(
			"CaseType", serviceContext, userId,
			HashMapBuilder.<String, Serializable>put(
				"name", testrayCaseTypeName
			).build());

		testrayCaseTypeId = objectEntry.getObjectEntryId();

		_objectEntryIds.put(objectEntryIdsKey, testrayCaseTypeId);

		return testrayCaseTypeId;
	}

	private long _getTestrayComponentId(
			long companyId, ServiceContext serviceContext,
			String testrayComponentName, long testrayProjectId,
			long testrayTeamId, long userId)
		throws Exception {

		String objectEntryIdsKey = StringBundler.concat(
			"Component#", testrayComponentName, "#ProjectId#",
			testrayProjectId);

		long testrayComponentId = _getObjectEntryId(
			companyId,
			StringBundler.concat(
				"projectId eq '", testrayProjectId, "' and name eq '",
				testrayComponentName, "'"),
			"Component", objectEntryIdsKey, userId);

		if (testrayComponentId != 0) {
			return testrayComponentId;
		}

		ObjectEntry objectEntry = _addObjectEntry(
			"Component", serviceContext, userId,
			HashMapBuilder.<String, Serializable>put(
				"name", testrayComponentName
			).put(
				"r_projectToComponents_c_projectId", testrayProjectId
			).put(
				"r_teamToComponents_c_teamId", testrayTeamId
			).build());

		testrayComponentId = objectEntry.getObjectEntryId();

		_objectEntryIds.put(objectEntryIdsKey, testrayComponentId);

		return testrayComponentId;
	}

	private long _getTestrayFactorCategoryId(
			long companyId, ServiceContext serviceContext,
			String testrayFactorCategoryName, long userId)
		throws Exception {

		String objectEntryIdsKey =
			"FactorCategory#" + testrayFactorCategoryName;

		long testrayFactorCategoryId = _getObjectEntryId(
			companyId, "name eq '" + testrayFactorCategoryName + "'",
			"FactorCategory", objectEntryIdsKey, userId);

		if (testrayFactorCategoryId != 0) {
			return testrayFactorCategoryId;
		}

		ObjectEntry objectEntry = _addObjectEntry(
			"FactorCategory", serviceContext, userId,
			HashMapBuilder.<String, Serializable>put(
				"name", testrayFactorCategoryName
			).build());

		testrayFactorCategoryId = objectEntry.getObjectEntryId();

		_objectEntryIds.put(objectEntryIdsKey, testrayFactorCategoryId);

		return testrayFactorCategoryId;
	}

	private long _getTestrayFactorOptionId(
			long companyId, ServiceContext serviceContext,
			long testrayFactorCategoryId, String testrayFactorOptionName,
			long userId)
		throws Exception {

		String objectEntryIdsKey = StringBundler.concat(
			"FactorOption#", testrayFactorOptionName, "#FactorCategoryId#",
			testrayFactorCategoryId);

		long testrayFactorOptionId = _getObjectEntryId(
			companyId,
			StringBundler.concat(
				"factorCategoryId eq '", testrayFactorCategoryId,
				"' and name eq '", testrayFactorOptionName, "'"),
			"FactorOption", objectEntryIdsKey, userId);

		if (testrayFactorOptionId != 0) {
			return testrayFactorOptionId;
		}

		ObjectEntry objectEntry = _addObjectEntry(
			"FactorOption", serviceContext, userId,
			HashMapBuilder.<String, Serializable>put(
				"name", testrayFactorOptionName
			).put(
				"r_factorCategoryToOptions_c_factorCategoryId",
				testrayFactorCategoryId
			).build());

		testrayFactorOptionId = objectEntry.getObjectEntryId();

		_objectEntryIds.put(objectEntryIdsKey, testrayFactorOptionId);

		return testrayFactorOptionId;
	}

	private long _getTestrayProductVersionId(
			long companyId, ServiceContext serviceContext,
			String testrayProductVersionName, long testrayProjectId,
			long userId)
		throws Exception {

		String objectEntryIdsKey =
			"ProductVersion#" + testrayProductVersionName;

		long testrayProductVersionId = _getObjectEntryId(
			companyId, "name eq '" + testrayProductVersionName + "'",
			"ProductVersion", objectEntryIdsKey, userId);

		if (testrayProductVersionId != 0) {
			return testrayProductVersionId;
		}

		ObjectEntry objectEntry = _addObjectEntry(
			"ProductVersion", serviceContext, userId,
			HashMapBuilder.<String, Serializable>put(
				"name", testrayProductVersionName
			).put(
				"r_projectToProductVersions_c_projectId", testrayProjectId
			).build());

		testrayProductVersionId = objectEntry.getObjectEntryId();

		_objectEntryIds.put(objectEntryIdsKey, testrayProductVersionId);

		return testrayProductVersionId;
	}

	private long _getTestrayProjectId(
			long companyId, ServiceContext serviceContext,
			String testrayProjectName, long userId)
		throws Exception {

		String objectEntryIdsKey = "Project#" + testrayProjectName;

		long testrayProjectId = _getObjectEntryId(
			companyId, "name eq '" + testrayProjectName + "'", "Project",
			objectEntryIdsKey, userId);

		if (testrayProjectId != 0) {
			return testrayProjectId;
		}

		ObjectEntry objectEntry = _addObjectEntry(
			"Project", serviceContext, userId,
			HashMapBuilder.<String, Serializable>put(
				"name", testrayProjectName
			).build());

		testrayProjectId = objectEntry.getObjectEntryId();

		_objectEntryIds.put(objectEntryIdsKey, testrayProjectId);

		return testrayProjectId;
	}

	private long _getTestrayRoutineId(
			long companyId, ServiceContext serviceContext,
			long testrayProjectId, String testrayRoutineName, long userId)
		throws Exception {

		String objectEntryIdsKey = StringBundler.concat(
			"Routine#", testrayRoutineName, "#ProjectId#", testrayProjectId);

		long testrayRoutineId = _getObjectEntryId(
			companyId,
			StringBundler.concat(
				"projectId eq '", testrayProjectId, "' and name eq '",
				testrayRoutineName, "'"),
			"Routine", objectEntryIdsKey, userId);

		if (testrayRoutineId != 0) {
			return testrayRoutineId;
		}

		ObjectEntry objectEntry = _addObjectEntry(
			"Routine", serviceContext, userId,
			HashMapBuilder.<String, Serializable>put(
				"name", testrayRoutineName
			).put(
				"r_routineToProjects_c_projectId", testrayProjectId
			).build());

		testrayRoutineId = objectEntry.getObjectEntryId();

		_objectEntryIds.put(objectEntryIdsKey, testrayRoutineId);

		return testrayRoutineId;
	}

	private String _getTestrayRunEnvironmentHash(
			long companyId, Element element, ServiceContext serviceContext,
			long testrayRunId, long userId)
		throws Exception {

		StringBundler sb = new StringBundler();

		NodeList environmentNodeList = element.getElementsByTagName(
			"environment");

		for (int i = 0; i < environmentNodeList.getLength(); i++) {
			Node node = environmentNodeList.item(i);

			if (!node.hasAttributes()) {
				continue;
			}

			String testrayFactorCategoryName = _getAttributeValue("type", node);

			long testrayFactorCategoryId = _getTestrayFactorCategoryId(
				companyId, serviceContext, testrayFactorCategoryName, userId);

			String testrayFactorOptionName = _getAttributeValue("option", node);

			long testrayFactorOptionId = _getTestrayFactorOptionId(
				companyId, serviceContext, testrayFactorCategoryId,
				testrayFactorOptionName, userId);

			_addTestrayFactor(
				serviceContext, testrayFactorCategoryId,
				testrayFactorCategoryName, testrayFactorOptionId,
				testrayFactorOptionName, testrayRunId, userId);

			sb.append(testrayFactorCategoryId);
			sb.append(testrayFactorOptionId);
		}

		String testrayFactorsString = sb.toString();

		return String.valueOf(testrayFactorsString.hashCode());
	}

	private long _getTestrayRunId(
			long companyId, Element element, ServiceContext serviceContext,
			Map<String, String> propertiesMap, long testrayBuildId,
			String testrayRunName, long userId)
		throws Exception {

		String objectEntryIdsKey = StringBundler.concat(
			"Run#", testrayRunName, "#BuildId#", testrayBuildId);

		long testrayRunId = _getObjectEntryId(
			companyId,
			StringBundler.concat(
				"buildId eq '", testrayBuildId, "' and name eq '",
				testrayRunName, "'"),
			"Run", objectEntryIdsKey, userId);

		if (testrayRunId != 0) {
			return testrayRunId;
		}

		ObjectEntry objectEntry = _addObjectEntry(
			"Run", serviceContext, userId,
			HashMapBuilder.<String, Serializable>put(
				"externalReferencePK", propertiesMap.get("testray.run.id")
			).put(
				"externalReferenceType", 1
			).put(
				"jenkinsJobKey",
				GetterUtil.getLong(propertiesMap.get("jenkins.job.id"))
			).put(
				"name", testrayRunName
			).put(
				"number", _runNumber++
			).put(
				"r_buildToRuns_c_buildId", testrayBuildId
			).build());

		testrayRunId = objectEntry.getObjectEntryId();

		objectEntry.getValues(
		).put(
			"environmentHash",
			_getTestrayRunEnvironmentHash(
				companyId, element, serviceContext, testrayRunId, userId)
		);

		_objectEntryLocalService.updateObjectEntry(
			userId, objectEntry.getObjectEntryId(), objectEntry.getValues(),
			serviceContext);

		_objectEntryIds.put(objectEntryIdsKey, testrayRunId);

		return testrayRunId;
	}

	private long _getTestrayTeamId(
			long companyId, ServiceContext serviceContext,
			long testrayProjectId, String testrayTeamName, long userId)
		throws Exception {

		String objectEntryIdsKey = StringBundler.concat(
			"Team#", testrayTeamName, "#ProjectId#", testrayProjectId);

		long testrayTeamId = _getObjectEntryId(
			companyId,
			StringBundler.concat(
				"projectId eq '", testrayProjectId, "' and name eq '",
				testrayTeamName, "'"),
			"Team", objectEntryIdsKey, userId);

		if (testrayTeamId != 0) {
			return testrayTeamId;
		}

		ObjectEntry objectEntry = _addObjectEntry(
			"Team", serviceContext, userId,
			HashMapBuilder.<String, Serializable>put(
				"name", testrayTeamName
			).put(
				"r_projectToTeams_c_projectId", testrayProjectId
			).build());

		_objectEntryIds.put(objectEntryIdsKey, objectEntry.getObjectEntryId());

		return objectEntry.getObjectEntryId();
	}

	private List<Map<String, Serializable>> _getValuesList(
			long companyId, String filterString, String shortName, long userId)
		throws Exception {

		ObjectDefinition objectDefinition = _getObjectDefinition(shortName);

		return _objectEntryLocalService.getValuesList(
			0, companyId, userId, objectDefinition.getObjectDefinitionId(),
			_filterFactory.create(filterString, objectDefinition), null,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	private void _invoke(UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		long startTime = System.currentTimeMillis();

		unsafeRunnable.run();

		if (_log.isDebugEnabled()) {
			Thread thread = Thread.currentThread();

			StackTraceElement stackTraceElement = thread.getStackTrace()[2];

			_log.debug(
				StringBundler.concat(
					"Invoking line ", stackTraceElement.getLineNumber(),
					" took ", System.currentTimeMillis() - startTime, " ms"));
		}
	}

	private void _load(long companyId, long userId) throws Exception {
		_loadObjectDefinitions(companyId);

		_loadTestrayCaseTypes(companyId, userId);
		_loadTestrayComponents(companyId, userId);
		_loadTestrayFactorCategories(companyId, userId);
		_loadTestrayFactorOptions(companyId, userId);
		_loadTestrayProjects(companyId, userId);
		_loadTestrayTeams(companyId, userId);
	}

	private void _loadObjectDefinitions(long companyId) {
		List<ObjectDefinition> objectDefinitions =
			_objectDefinitionLocalService.getObjectDefinitions(
				companyId, true, WorkflowConstants.STATUS_APPROVED);

		if (ListUtil.isEmpty(objectDefinitions)) {
			return;
		}

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			if (_objectDefinitionsMap.get(objectDefinition.getShortName()) !=
					null) {

				continue;
			}

			_objectDefinitionsMap.put(
				objectDefinition.getShortName(), objectDefinition);
		}
	}

	private void _loadTestrayCaseTypes(long companyId, long userId)
		throws Exception {

		List<Map<String, Serializable>> valuesList = _getValuesList(
			companyId, null, "CaseType", userId);

		if (ListUtil.isEmpty(valuesList)) {
			return;
		}

		for (Map<String, Serializable> values : valuesList) {
			_objectEntryIds.put(
				"CaseType#name" + values.get("name"),
				GetterUtil.getLong(values.get("caseTypeId")));
		}
	}

	private void _loadTestrayComponents(long companyId, long userId)
		throws Exception {

		List<Map<String, Serializable>> valuesList = _getValuesList(
			companyId, null, "Component", userId);

		if (ListUtil.isEmpty(valuesList)) {
			return;
		}

		for (Map<String, Serializable> values : valuesList) {
			_objectEntryIds.put(
				StringBundler.concat(
					"Component#", GetterUtil.getString(values.get("name")),
					"#TeamId#",
					GetterUtil.getLong(
						values.get("r_teamToComponents_c_teamId"))),
				GetterUtil.getLong(values.get("c_componentId")));
		}
	}

	private void _loadTestrayFactorCategories(long companyId, long userId)
		throws Exception {

		List<Map<String, Serializable>> valuesList = _getValuesList(
			companyId, null, "FactorCategory", userId);

		if (ListUtil.isEmpty(valuesList)) {
			return;
		}

		for (Map<String, Serializable> values : valuesList) {
			_objectEntryIds.put(
				"FactorCategory#" + GetterUtil.getString(values.get("name")),
				GetterUtil.getLong(values.get("c_factorCategoryId")));
		}
	}

	private void _loadTestrayFactorOptions(long companyId, long userId)
		throws Exception {

		List<Map<String, Serializable>> valuesList = _getValuesList(
			companyId, null, "FactorOption", userId);

		if (ListUtil.isEmpty(valuesList)) {
			return;
		}

		for (Map<String, Serializable> values : valuesList) {
			_objectEntryIds.put(
				StringBundler.concat(
					"FactorOption#", GetterUtil.getString(values.get("name")),
					"#FactorCategoryId#",
					GetterUtil.getLong(
						values.get(
							"r_factorCategoryToOptions_c_factorCategoryId"))),
				GetterUtil.getLong(values.get("c_factorCategoryId")));
		}
	}

	private void _loadTestrayProjects(long companyId, long userId)
		throws Exception {

		List<Map<String, Serializable>> valuesList = _getValuesList(
			companyId, null, "Project", userId);

		if (ListUtil.isEmpty(valuesList)) {
			return;
		}

		for (Map<String, Serializable> values : valuesList) {
			_objectEntryIds.put(
				"Project#" + GetterUtil.getString(values.get("name")),
				GetterUtil.getLong(values.get("c_projectId")));
		}
	}

	private void _loadTestrayTeams(long companyId, long userId)
		throws Exception {

		List<Map<String, Serializable>> valuesList = _getValuesList(
			companyId, null, "Team", userId);

		if (ListUtil.isEmpty(valuesList)) {
			return;
		}

		for (Map<String, Serializable> values : valuesList) {
			_objectEntryIds.put(
				StringBundler.concat(
					"Team#", GetterUtil.getString(values.get("name")),
					"#ProjectId#",
					GetterUtil.getLong(
						values.get("r_projectToTeams_c_projectIds"))),
				GetterUtil.getLong(values.get("c_teamId")));
		}
	}

	private void _processArchive(
			long companyId, byte[] bytes, ServiceContext serviceContext,
			long userId)
		throws Exception {

		Path tempDirectoryPath = null;
		Path tempFilePath = null;

		try {
			tempDirectoryPath = Files.createTempDirectory(null);

			tempFilePath = Files.createTempFile(null, null);

			Files.write(tempFilePath, bytes);

			Archiver archiver = ArchiverFactory.createArchiver("tar");

			File tempDirectoryFile = tempDirectoryPath.toFile();

			archiver.extract(tempFilePath.toFile(), tempDirectoryFile);

			DocumentBuilderFactory documentBuilderFactory =
				SecureXMLFactoryProviderUtil.newDocumentBuilderFactory();

			DocumentBuilder documentBuilder =
				documentBuilderFactory.newDocumentBuilder();

			for (File file : tempDirectoryFile.listFiles()) {
				if (_log.isInfoEnabled()) {
					_log.info("Processing " + file.getName());
				}

				try {
					Document document = documentBuilder.parse(file);

					_invoke(
						() -> _processDocument(
							companyId, document, serviceContext, userId));
				}
				catch (Exception exception) {
					_log.error(exception);
				}
				finally {
					file.delete();
				}
			}
		}
		finally {
			if (tempDirectoryPath != null) {
				Files.deleteIfExists(tempDirectoryPath);
			}

			if (tempFilePath != null) {
				Files.deleteIfExists(tempFilePath);
			}
		}
	}

	private void _processDocument(
			long companyId, Document document, ServiceContext serviceContext,
			long userId)
		throws Exception {

		Element element = document.getDocumentElement();

		Map<String, String> propertiesMap = _getPropertiesMap(element);

		long testrayProjectId = _getTestrayProjectId(
			companyId, serviceContext,
			propertiesMap.get("testray.project.name"), userId);

		long testrayRoutineId = _getTestrayRoutineId(
			companyId, serviceContext, testrayProjectId,
			propertiesMap.get("testray.build.type"), userId);

		long testrayBuildId = _getTestrayBuildId(
			companyId, propertiesMap, serviceContext,
			propertiesMap.get("testray.build.name"), testrayProjectId,
			testrayRoutineId, userId);

		long testrayRunId = _getTestrayRunId(
			companyId, element, serviceContext, propertiesMap, testrayBuildId,
			propertiesMap.get("testray.run.id"), userId);

		_addTestrayCases(
			companyId, element, serviceContext, testrayBuildId,
			propertiesMap.get("testray.build.time"), testrayProjectId,
			testrayRunId, userId);
	}

	private void _uploadToTestray(
			long companyId, ServiceContext serviceContext,
			UnicodeProperties unicodeProperties, long userId)
		throws Exception {

		String s3APIKey = unicodeProperties.getProperty("s3APIKey");

		try (InputStream inputStream = new ByteArrayInputStream(
				s3APIKey.getBytes())) {

			long filesCountThreshold = GetterUtil.getLong(
				unicodeProperties.getProperty("filesCountThreshold"), -1);

			Storage storage = StorageOptions.newBuilder(
			).setCredentials(
				GoogleCredentials.fromStream(inputStream)
			).build(
			).getService();

			String s3InboxFolderName = unicodeProperties.getProperty(
				"s3InboxFolderName");

			Page<Blob> page = storage.list(
				unicodeProperties.getProperty("s3BucketName"),
				Storage.BlobListOption.prefix(s3InboxFolderName + "/"));

			for (Blob blob : page.iterateAll()) {
				if (filesCountThreshold == 0) {
					break;
				}

				String name = blob.getName();

				if (name.equals(s3InboxFolderName + "/")) {
					continue;
				}

				if (_log.isInfoEnabled()) {
					_log.info("Processing " + name);
				}

				try {
					_runNumber = 1;

					_processArchive(
						companyId, blob.getContent(), serviceContext, userId);

					blob.copyTo(
						unicodeProperties.getProperty("s3BucketName"),
						name.replaceFirst(
							s3InboxFolderName,
							unicodeProperties.getProperty(
								"s3ProcessedFolderName")));
				}
				catch (Exception exception) {
					_log.error(exception);
					blob.copyTo(
						unicodeProperties.getProperty("s3BucketName"),
						name.replaceFirst(
							s3InboxFolderName,
							unicodeProperties.getProperty(
								"s3ErroredFolderName")));
				}

				blob.delete();

				filesCountThreshold--;
			}
		}
		catch (IOException ioException) {
			_log.error("Unable to authenticate with GCP");

			throw new PortalException(
				"Unable to authenticate with GCP", ioException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TestrayImportResultsDispatchTaskExecutor.class);

	@Reference(
		target = "(filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT + ")"
	)
	private FilterFactory<Predicate> _filterFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private Map<String, ObjectDefinition> _objectDefinitionsMap =
		new HashMap<>();
	private Map<String, Long> _objectEntryIds = new HashMap<>();

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference(target = "(object.entry.manager.storage.type=default)")
	private ObjectEntryManager _objectEntryManager;

	private int _runNumber;

	@Reference
	private UserLocalService _userLocalService;

}