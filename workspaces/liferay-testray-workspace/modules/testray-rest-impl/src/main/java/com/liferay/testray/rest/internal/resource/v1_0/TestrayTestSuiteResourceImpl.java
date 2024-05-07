/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.rest.internal.resource.v1_0;

import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.testray.rest.dto.v1_0.TestrayTestSuite;
import com.liferay.testray.rest.resource.v1_0.TestrayTestSuiteResource;

import java.io.File;
import java.io.Serializable;

import java.sql.Timestamp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Nilton Vieira
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/testray-test-suite.properties",
	scope = ServiceScope.PROTOTYPE, service = TestrayTestSuiteResource.class
)
public class TestrayTestSuiteResourceImpl
	extends BaseTestrayTestSuiteResourceImpl {

	@Override
	public TestrayTestSuite postTestrayTestSuite(MultipartBody multipartBody)
		throws Exception {

		long startTime = System.currentTimeMillis();

		DocumentBuilderFactory documentBuilderFactory =
			SecureXMLFactoryProviderUtil.newDocumentBuilderFactory();

		DocumentBuilder documentBuilder =
			documentBuilderFactory.newDocumentBuilder();

		TestrayTestSuite testrayTestSuite = new TestrayTestSuite();
		File file = null;

		try {
			file = FileUtil.createTempFile(
				multipartBody.getBinaryFileAsBytes("file"));

			Document document = documentBuilder.parse(file);

			_processDocument(document);

			testrayTestSuite.setRuntime(System.currentTimeMillis() - startTime);
			testrayTestSuite.setXmlFileName(
				multipartBody.getBinaryFile(
					"file"
				).getFileName());
		}
		finally {
			FileUtil.delete(file);
		}

		return testrayTestSuite;
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
			long companyId, Node testcaseNode, long testrayBuildId,
			String testrayBuildTime,
			Map<String, Serializable> testrayCasePropertiesMap,
			long testrayProjectId, long testrayRunId)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				companyId, "C_Case");

		String testrayCaseName = (String)testrayCasePropertiesMap.get(
			"testray.testcase.name");

		List<Map<String, Serializable>> valuesList =
			_objectEntryLocalService.getValuesList(
				0, companyId, contextUser.getUserId(),
				objectDefinition.getObjectDefinitionId(),
				_filterFactory.create(
					StringBundler.concat(
						"projectId eq '", testrayProjectId, "' and name eq '",
						testrayCaseName, "'"),
					objectDefinition),
				null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		long testrayCaseId = 0;

		if (ListUtil.isNotEmpty(valuesList)) {
			Map<String, Serializable> values = valuesList.get(0);

			testrayCaseId = GetterUtil.getLong(values.get("objectEntryId"));
		}

		long testrayTeamId = _getTestrayTeamId(
			companyId, testrayProjectId,
			(String)testrayCasePropertiesMap.get("testray.team.name"));

		long testrayComponentId = _getTestrayComponentId(
			companyId,
			(String)testrayCasePropertiesMap.get("testray.main.component.name"),
			testrayProjectId, testrayTeamId);

		if (testrayCaseId == 0) {
			ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
				contextUser.getUserId(), 0,
				objectDefinition.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					"description",
					testrayCasePropertiesMap.get("testray.testcase.description")
				).put(
					"name",
					testrayCasePropertiesMap.get("testray.testcase.name")
				).put(
					"number", 0
				).put(
					"priority",
					testrayCasePropertiesMap.get("testray.testcase.priority")
				).put(
					"r_caseTypeToCases_c_caseTypeId",
					_getTestrayCaseTypeId(
						companyId,
						(String)testrayCasePropertiesMap.get(
							"testray.case.type.name"))
				).put(
					"r_componentToCases_c_componentId", testrayComponentId
				).put(
					"r_projectToCases_c_projectId", testrayProjectId
				).build(),
				_serviceContextHelper.getServiceContext());

			testrayCaseId = objectEntry.getObjectEntryId();
		}

		objectDefinition = _objectDefinitionLocalService.getObjectDefinition(
			companyId, "C_BuildsCases");

		valuesList = _objectEntryLocalService.getValuesList(
			0, companyId, contextUser.getUserId(),
			objectDefinition.getObjectDefinitionId(),
			_filterFactory.create(
				StringBundler.concat(
					"buildId eq '", testrayBuildId, "' and caseId eq '",
					testrayCaseId, "'"),
				objectDefinition),
			null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		if (ListUtil.isNotEmpty(valuesList)) {
			_objectEntryLocalService.addObjectEntry(
				contextUser.getUserId(), 0,
				objectDefinition.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					"r_buildToBuildsCases_c_buildId", testrayBuildId
				).put(
					"r_caseToBuildsCases_c_caseId", testrayCaseId
				).build(),
				_serviceContextHelper.getServiceContext());
		}

		_addTestrayCaseResult(
			companyId, testcaseNode, testrayBuildId, testrayBuildTime,
			testrayCaseId, testrayCasePropertiesMap, testrayComponentId,
			testrayRunId);
	}

	private void _addTestrayCaseResult(
			long companyId, Node testcaseNode, long testrayBuildId,
			String testrayBuildTime, long testrayCaseId,
			Map<String, Serializable> testrayCasePropertiesMap,
			long testrayComponentId, long testrayRunId)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				companyId, "C_CaseResult");

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

		_objectEntryLocalService.addObjectEntry(
			contextUser.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(), properties,
			_serviceContextHelper.getServiceContext());
	}

	private void _addTestrayCases(
			long companyId, Element element, long testrayBuildId,
			String testrayBuildTime, long testrayProjectId, long testrayRunId)
		throws Exception {

		NodeList testCaseNodeList = element.getElementsByTagName("testcase");

		for (int i = 0; i < testCaseNodeList.getLength(); i++) {
			Node testcaseNode = testCaseNodeList.item(i);

			Map<String, Serializable> testrayCasePropertiesMap =
				_getTestrayCaseProperties((Element)testcaseNode);

			_addTestrayCase(
				companyId, testcaseNode, testrayBuildId, testrayBuildTime,
				testrayCasePropertiesMap, testrayProjectId, testrayRunId);
		}
	}

	private void _addTestrayFactor(
			long companyId, long testrayFactorCategoryId,
			String testrayFactorCategoryName, long testrayFactorOptionId,
			String testrayFactorOptionName, long testrayRunId)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				companyId, "C_Factor");

		_objectEntryLocalService.addObjectEntry(
			contextUser.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(),
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
			).build(),
			_serviceContextHelper.getServiceContext());
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
			String testrayBuildName, long testrayProjectId,
			long testrayRoutineId)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				companyId, "C_Build");

		List<Map<String, Serializable>> valuesList =
			_objectEntryLocalService.getValuesList(
				0, companyId, contextUser.getUserId(),
				objectDefinition.getObjectDefinitionId(),
				_filterFactory.create(
					StringBundler.concat(
						"projectId eq '", testrayProjectId, "' and name eq '",
						testrayBuildName, "'"),
					objectDefinition),
				null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		if (ListUtil.isNotEmpty(valuesList)) {
			Map<String, Serializable> values = valuesList.get(0);

			return GetterUtil.getLong(values.get("objectEntryId"));
		}

		long testrayProductVersionId = _getTestrayProductVersionId(
			companyId, propertiesMap.get("testray.product.version"),
			testrayProjectId);

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			contextUser.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(),
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
			).build(),
			_serviceContextHelper.getServiceContext());

		return objectEntry.getObjectEntryId();
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
			long companyId, String testrayCaseTypeName)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				companyId, "C_CaseType");

		List<Map<String, Serializable>> valuesList =
			_objectEntryLocalService.getValuesList(
				0, companyId, contextUser.getUserId(),
				objectDefinition.getObjectDefinitionId(),
				_filterFactory.create(
					"name eq '" + testrayCaseTypeName + "'", objectDefinition),
				null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		if (ListUtil.isNotEmpty(valuesList)) {
			Map<String, Serializable> values = valuesList.get(0);

			return GetterUtil.getLong(values.get("objectEntryId"));
		}

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			contextUser.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"name", testrayCaseTypeName
			).build(),
			_serviceContextHelper.getServiceContext());

		return objectEntry.getObjectEntryId();
	}

	private long _getTestrayComponentId(
			long companyId, String testrayComponentName, long testrayProjectId,
			long testrayTeamId)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				companyId, "C_Component");

		List<Map<String, Serializable>> valuesList =
			_objectEntryLocalService.getValuesList(
				0, companyId, contextUser.getUserId(),
				objectDefinition.getObjectDefinitionId(),
				_filterFactory.create(
					StringBundler.concat(
						"projectId eq '", testrayProjectId, "' and name eq '",
						testrayComponentName, "'"),
					objectDefinition),
				null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		if (ListUtil.isNotEmpty(valuesList)) {
			Map<String, Serializable> values = valuesList.get(0);

			return GetterUtil.getLong(values.get("objectEntryId"));
		}

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			contextUser.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"name", testrayComponentName
			).put(
				"r_projectToComponents_c_projectId", testrayProjectId
			).put(
				"r_teamToComponents_c_teamId", testrayTeamId
			).build(),
			_serviceContextHelper.getServiceContext());

		return objectEntry.getObjectEntryId();
	}

	private long _getTestrayFactorCategoryId(
			long companyId, String testrayFactorCategoryName)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				companyId, "C_FactorCategory");

		List<Map<String, Serializable>> valuesList =
			_objectEntryLocalService.getValuesList(
				0, companyId, contextUser.getUserId(),
				objectDefinition.getObjectDefinitionId(),
				_filterFactory.create(
					"name eq '" + testrayFactorCategoryName + "'",
					objectDefinition),
				null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		if (ListUtil.isNotEmpty(valuesList)) {
			Map<String, Serializable> values = valuesList.get(0);

			return GetterUtil.getLong(values.get("objectEntryId"));
		}

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			contextUser.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"name", testrayFactorCategoryName
			).build(),
			_serviceContextHelper.getServiceContext());

		return objectEntry.getObjectEntryId();
	}

	private long _getTestrayFactorOptionId(
			long companyId, long testrayFactorCategoryId,
			String testrayFactorOptionName)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				companyId, "C_FactorOption");

		List<Map<String, Serializable>> valuesList =
			_objectEntryLocalService.getValuesList(
				0, companyId, contextUser.getUserId(),
				objectDefinition.getObjectDefinitionId(),
				_filterFactory.create(
					StringBundler.concat(
						"factorCategoryId eq '", testrayFactorCategoryId,
						"' and name eq '", testrayFactorOptionName, "'"),
					objectDefinition),
				null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		if (ListUtil.isNotEmpty(valuesList)) {
			Map<String, Serializable> values = valuesList.get(0);

			return GetterUtil.getLong(values.get("objectEntryId"));
		}

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			contextUser.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"name", testrayFactorOptionName
			).put(
				"r_factorCategoryToOptions_c_factorCategoryId",
				testrayFactorCategoryId
			).build(),
			_serviceContextHelper.getServiceContext());

		return objectEntry.getObjectEntryId();
	}

	private long _getTestrayProductVersionId(
			long companyId, String testrayProductVersionName,
			long testrayProjectId)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				companyId, "C_ProductVersion");

		List<Map<String, Serializable>> valuesList =
			_objectEntryLocalService.getValuesList(
				0, companyId, contextUser.getUserId(),
				objectDefinition.getObjectDefinitionId(),
				_filterFactory.create(
					StringBundler.concat(
						"projectId eq '", testrayProjectId, "' and name eq '",
						testrayProductVersionName, "'"),
					objectDefinition),
				null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		if (ListUtil.isNotEmpty(valuesList)) {
			Map<String, Serializable> values = valuesList.get(0);

			return GetterUtil.getLong(values.get("objectEntryId"));
		}

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			contextUser.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"name", testrayProductVersionName
			).put(
				"r_projectToProductVersions_c_projectId", testrayProjectId
			).build(),
			_serviceContextHelper.getServiceContext());

		return objectEntry.getObjectEntryId();
	}

	private long _getTestrayProjectId(long companyId, String testrayProjectName)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				companyId, "C_Project");

		List<Map<String, Serializable>> valuesList =
			_objectEntryLocalService.getValuesList(
				0, companyId, contextUser.getUserId(),
				objectDefinition.getObjectDefinitionId(),
				_filterFactory.create(
					"name eq '" + testrayProjectName + "'", objectDefinition),
				null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		if (ListUtil.isNotEmpty(valuesList)) {
			Map<String, Serializable> values = valuesList.get(0);

			return GetterUtil.getLong(values.get("objectEntryId"));
		}

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			contextUser.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"name", testrayProjectName
			).build(),
			_serviceContextHelper.getServiceContext());

		return objectEntry.getObjectEntryId();
	}

	private long _getTestrayRoutineId(
			long companyId, long testrayProjectId, String testrayRoutineName)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				companyId, "C_Routine");

		List<Map<String, Serializable>> valuesList =
			_objectEntryLocalService.getValuesList(
				0, companyId, contextUser.getUserId(),
				objectDefinition.getObjectDefinitionId(),
				_filterFactory.create(
					StringBundler.concat(
						"projectId eq '", testrayProjectId, "' and name eq '",
						testrayRoutineName, "'"),
					objectDefinition),
				null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		if (ListUtil.isNotEmpty(valuesList)) {
			Map<String, Serializable> values = valuesList.get(0);

			return GetterUtil.getLong(values.get("objectEntryId"));
		}

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			contextUser.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"name", testrayRoutineName
			).put(
				"r_routineToProjects_c_projectId", testrayProjectId
			).build(),
			_serviceContextHelper.getServiceContext());

		return objectEntry.getObjectEntryId();
	}

	private String _getTestrayRunEnvironmentHash(
			long companyId, Element element, long testrayRunId)
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
				companyId, testrayFactorCategoryName);

			String testrayFactorOptionName = _getAttributeValue("option", node);

			long testrayFactorOptionId = _getTestrayFactorOptionId(
				companyId, testrayFactorCategoryId, testrayFactorOptionName);

			_addTestrayFactor(
				companyId, testrayFactorCategoryId, testrayFactorCategoryName,
				testrayFactorOptionId, testrayFactorOptionName, testrayRunId);

			sb.append(testrayFactorCategoryId);
			sb.append(testrayFactorOptionId);
		}

		String testrayFactorsString = sb.toString();

		return String.valueOf(testrayFactorsString.hashCode());
	}

	private long _getTestrayRunId(
			long companyId, Element element, Map<String, String> propertiesMap,
			long testrayBuildId, String testrayRunName)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				companyId, "C_Run");

		List<Map<String, Serializable>> valuesList =
			_objectEntryLocalService.getValuesList(
				0, companyId, contextUser.getUserId(),
				objectDefinition.getObjectDefinitionId(),
				_filterFactory.create(
					StringBundler.concat(
						"buildId eq '", testrayBuildId, "' and name eq '",
						testrayRunName, "'"),
					objectDefinition),
				null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		if (ListUtil.isNotEmpty(valuesList)) {
			Map<String, Serializable> values = valuesList.get(0);

			return GetterUtil.getLong(values.get("objectEntryId"));
		}

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			contextUser.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(),
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
				"number", 0
			).put(
				"r_buildToRuns_c_buildId", testrayBuildId
			).build(),
			_serviceContextHelper.getServiceContext());

		objectEntry.getValues(
		).put(
			"environmentHash",
			_getTestrayRunEnvironmentHash(
				companyId, element, objectEntry.getObjectEntryId())
		);

		_objectEntryLocalService.updateObjectEntry(
			contextUser.getUserId(), objectEntry.getObjectEntryId(),
			objectEntry.getValues(), _serviceContextHelper.getServiceContext());

		return objectEntry.getObjectEntryId();
	}

	private long _getTestrayTeamId(
			long companyId, long testrayProjectId, String testrayTeamName)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				companyId, "C_Team");

		List<Map<String, Serializable>> valuesList =
			_objectEntryLocalService.getValuesList(
				0, companyId, contextUser.getUserId(),
				objectDefinition.getObjectDefinitionId(),
				_filterFactory.create(
					StringBundler.concat(
						"projectId eq '", testrayProjectId, "' and name eq '",
						testrayTeamName, "'"),
					objectDefinition),
				null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		if (ListUtil.isNotEmpty(valuesList)) {
			Map<String, Serializable> values = valuesList.get(0);

			return GetterUtil.getLong(values.get("objectEntryId"));
		}

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			contextUser.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"name", testrayTeamName
			).put(
				"r_projectToTeams_c_projectId", testrayProjectId
			).build(),
			_serviceContextHelper.getServiceContext());

		return objectEntry.getObjectEntryId();
	}

	private void _processDocument(Document document) throws Exception {
		Element element = document.getDocumentElement();

		Map<String, String> propertiesMap = _getPropertiesMap(element);

		long testrayProjectId = _getTestrayProjectId(
			contextCompany.getCompanyId(),
			propertiesMap.get("testray.project.name"));

		long testrayRoutineId = _getTestrayRoutineId(
			contextCompany.getCompanyId(), testrayProjectId,
			propertiesMap.get("testray.build.type"));

		long testrayBuildId = _getTestrayBuildId(
			contextCompany.getCompanyId(), propertiesMap,
			propertiesMap.get("testray.build.name"), testrayProjectId,
			testrayRoutineId);

		long testrayRunId = _getTestrayRunId(
			contextCompany.getCompanyId(), element, propertiesMap,
			testrayBuildId, propertiesMap.get("testray.run.id"));

		_addTestrayCases(
			contextCompany.getCompanyId(), element, testrayBuildId,
			propertiesMap.get("testray.build.date"), testrayProjectId,
			testrayRunId);
	}

	@Reference(
		target = "(filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT + ")"
	)
	private FilterFactory<Predicate> _filterFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}