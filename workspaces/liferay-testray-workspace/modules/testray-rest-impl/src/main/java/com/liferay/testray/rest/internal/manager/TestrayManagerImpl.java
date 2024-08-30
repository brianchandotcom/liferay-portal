/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.rest.internal.manager;

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
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.testray.rest.dto.v1_0.TestrayCache;
import com.liferay.testray.rest.internal.util.TestrayUtil;
import com.liferay.testray.rest.manager.TestrayManager;

import java.io.File;
import java.io.Serializable;

import java.nio.file.Files;
import java.nio.file.Path;

import java.sql.Timestamp;

import java.time.OffsetDateTime;

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
@Component(service = TestrayManager.class)
public class TestrayManagerImpl implements TestrayManager {

	@Override
	public Map<String, Object> fetchTestrayCaseFlakyParameters(
			long companyId, OffsetDateTime offsetDateTime, long testrayCaseId)
		throws Exception {

		StringBundler sb = new StringBundler(11);

		sb.append("select cr.r_caseToCaseResult_c_caseId, sum(case when ");
		sb.append("cr.previousStatus != cr.dueStatus_ then 1 else 0 end) as ");
		sb.append("totalChanges, count(c_caseResultId_) as totalCases from ");
		sb.append("(select c_caseResultId_, r_caseToCaseResult_c_caseId, ");
		sb.append("@previousStatus previousStatus, @previousStatus := ");
		sb.append("dueStatus_ as dueStatus_ from ");
		sb.append("lportal.O_[%COMPANY_ID%]_CaseResult where ");
		sb.append("r_caseToCaseResult_c_caseId = ? and (dueStatus_ = ");
		sb.append("'PASSED' or dueStatus_ ='FAILED') and startDate_ is not ");
		sb.append("null and startDate_ >= ? order by c_caseResultId_) as cr ");
		sb.append("group by r_caseToCaseResult_c_caseId");

		List<Map<String, Object>> values = TestrayUtil.executeQuery(
			StringUtil.replace(
				sb.toString(), "[%COMPANY_ID%]", String.valueOf(companyId)),
			ListUtil.fromArray(
				GetterUtil.getLong(testrayCaseId), offsetDateTime));

		if (values.isEmpty()) {
			return null;
		}

		return values.get(0);
	}

	@Override
	public void loadTestrayCache(
			long companyId, TestrayCache testrayCache, long userId)
		throws Exception {

		_loadObjectDefinitions(companyId, testrayCache);

		_loadTestrayCaseTypes(companyId, testrayCache, userId);
		_loadTestrayComponents(companyId, testrayCache, userId);
		_loadTestrayFactorCategories(companyId, testrayCache, userId);
		_loadTestrayFactorOptions(companyId, testrayCache, userId);
		_loadTestrayProjects(companyId, testrayCache, userId);
		_loadTestrayTeams(companyId, testrayCache, userId);
	}

	@Override
	public void processArchive(
			long companyId, byte[] bytes, String fileName,
			ServiceContext serviceContext, long userId)
		throws Exception {

		long startTime = System.currentTimeMillis();

		String dueStatus = "successful";
		Path tempDirectoryPath = null;
		Path tempFilePath = null;

		TestrayCache testrayCache = new TestrayCache();

		loadTestrayCache(companyId, testrayCache, userId);

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

					processDocument(
						companyId, document, null, 0, serviceContext,
						testrayCache, userId);
				}
				catch (Exception exception) {
					dueStatus = "failed";

					_log.error(exception);
				}
				finally {
					file.delete();
				}
			}
		}
		catch (Exception exception) {
			dueStatus = "failed";

			_log.error(exception);
		}
		finally {
			if (tempDirectoryPath != null) {
				Files.deleteIfExists(tempDirectoryPath);
			}

			if (tempFilePath != null) {
				Files.deleteIfExists(tempFilePath);
			}

			_addTestrayTestSuite(
				testrayCache.getTestrayBuildId(),
				testrayCache.getTestrayCaseResultAmount(), dueStatus,
				System.currentTimeMillis() - startTime, fileName, bytes.length,
				serviceContext, testrayCache, userId);
		}
	}

	@Override
	public void processDocument(
			long companyId, Document document, String fileName, long fileSize,
			ServiceContext serviceContext, TestrayCache testrayCache,
			long userId)
		throws Exception {

		long startTime = System.currentTimeMillis();

		String dueStatus = "successful";

		try {
			Element element = document.getDocumentElement();

			Map<String, String> propertiesMap = _getPropertiesMap(element);

			long testrayProjectId = _getTestrayProjectId(
				companyId, serviceContext, testrayCache,
				propertiesMap.get("testray.project.name"), userId);

			long testrayRoutineId = _getTestrayRoutineId(
				companyId, serviceContext, testrayCache, testrayProjectId,
				propertiesMap.get("testray.build.type"), userId);

			long testrayBuildId = _getTestrayBuildId(
				companyId, propertiesMap, serviceContext,
				propertiesMap.get("testray.build.name"), testrayCache,
				testrayProjectId, testrayRoutineId, userId);

			testrayCache.setTestrayBuildId(testrayBuildId);

			long testrayRunId = _getTestrayRunId(
				companyId, element, serviceContext, propertiesMap,
				testrayBuildId, testrayCache,
				propertiesMap.get("testray.run.id"), userId);

			_addTestrayCases(
				companyId, element, serviceContext,
				propertiesMap.get("testray.build.date"), testrayBuildId,
				testrayCache, testrayProjectId, testrayRunId, userId);
		}
		catch (Exception exception) {
			_log.error(exception);

			dueStatus = "failed";

			throw exception;
		}
		finally {
			if (fileName != null) {
				_addTestrayTestSuite(
					testrayCache.getTestrayBuildId(),
					testrayCache.getTestrayCaseResultAmount(), dueStatus,
					System.currentTimeMillis() - startTime, fileName, fileSize,
					serviceContext, testrayCache, userId);
			}
		}
	}

	private void _addDefaultFactors(
			long companyId, ServiceContext serviceContext,
			TestrayCache testrayCache, long testrayRoutineId, long userId)
		throws Exception {

		List<Map<String, Serializable>> valuesList = _getValuesList(
			companyId,
			"externalReferenceCode in ('TRFCAT-001', 'TRFCAT-002', " +
				"'TRFCAT-003', 'TRFCAT-004', 'TRFCAT-009')",
			null, "FactorCategory", testrayCache, userId);

		if (ListUtil.isNotEmpty(valuesList)) {
			for (Map<String, Serializable> values : valuesList) {
				_addObjectEntry(
					"Factor", serviceContext, testrayCache, userId,
					HashMapBuilder.<String, Serializable>put(
						"r_factorCategoryToFactors_c_factorCategoryId",
						GetterUtil.getLong(values.get("c_factorCategoryId"))
					).put(
						"r_routineToFactors_c_routineId", testrayRoutineId
					).build());
			}
		}
	}

	private ObjectEntry _addObjectEntry(
			String shortName, ServiceContext serviceContext,
			TestrayCache testrayCache, long userId,
			Map<String, Serializable> values)
		throws Exception {

		return _objectEntryLocalService.addObjectEntry(
			userId, 0,
			testrayCache.getObjectDefinition(
				shortName
			).getObjectDefinitionId(),
			values, serviceContext);
	}

	private void _addOrUpdateTestrayCaseResult(
			ServiceContext serviceContext, Node testcaseNode,
			String testrayBuildDate, long testrayBuildId,
			TestrayCache testrayCache, long testrayCaseId,
			Map<String, Serializable> testrayCasePropertiesMap,
			long testrayComponentId, long testrayRunId, long testrayTeamId,
			long userId)
		throws Exception {

		String objectEntryIdsKey = StringBundler.concat(
			"RunId#", testrayRunId, "#CaseId#", testrayCaseId);

		long testrayCaseResultId = _getObjectEntryId(
			serviceContext.getCompanyId(),
			StringBundler.concat(
				"r_runToCaseResult_c_runId eq '", testrayRunId,
				"' and r_caseToCaseResult_c_caseId eq '", testrayCaseId, "'"),
			objectEntryIdsKey, new String[] {"c_caseResultId"}, "CaseResult",
			testrayCache, userId);

		Map<String, Serializable> properties =
			HashMapBuilder.<String, Serializable>put(
				"attachments", _addTestrayAttachments(testcaseNode)
			).put(
				"closedDate", Timestamp.valueOf(testrayBuildDate)
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
				"r_teamToCaseResult_c_teamId", testrayTeamId
			).put(
				"startDate", Timestamp.valueOf(testrayBuildDate)
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

		if (testrayCaseResultId == 0) {
			ObjectEntry objectEntry = _addObjectEntry(
				"CaseResult", serviceContext, testrayCache, userId, properties);

			testrayCache.addObjectEntryId(
				objectEntryIdsKey, objectEntry.getObjectEntryId());

			testrayCache.incrementTestrayCaseResultAmount();

			return;
		}

		_updateObjectEntry(
			testrayCaseResultId, serviceContext, userId, properties);

		testrayCache.incrementTestrayCaseResultAmount();
	}

	private JSONArray _addTestrayAttachments(Node testcaseNode)
		throws Exception {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

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

				jsonArray.put(
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
			String testrayBuildDate, long testrayBuildId,
			TestrayCache testrayCache,
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
				StringUtil.removeChar(
					StringUtil.replace(testrayCaseName, '\'', "''"), '\\'),
				"'"),
			objectEntryIdsKey, new String[] {"c_caseId"}, "Case", testrayCache,
			userId);

		long testrayTeamId = _getTestrayTeamId(
			companyId, serviceContext, testrayCache, testrayProjectId,
			(String)testrayCasePropertiesMap.get("testray.team.name"), userId);

		long testrayComponentId = _getTestrayComponentId(
			companyId, serviceContext, testrayCache,
			(String)testrayCasePropertiesMap.get("testray.main.component.name"),
			testrayProjectId, testrayTeamId, userId);

		if (testrayCaseId == 0) {
			ObjectEntry objectEntry = _addObjectEntry(
				"Case", serviceContext, testrayCache, userId,
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
						companyId, serviceContext, testrayCache,
						(String)testrayCasePropertiesMap.get(
							"testray.case.type.name"),
						userId)
				).put(
					"r_componentToCases_c_componentId", testrayComponentId
				).put(
					"r_projectToCases_c_projectId", testrayProjectId
				).build());

			testrayCaseId = objectEntry.getObjectEntryId();

			testrayCache.addObjectEntryId(objectEntryIdsKey, testrayCaseId);
		}

		if (ListUtil.isEmpty(
				_getValuesList(
					companyId,
					StringBundler.concat(
						"buildId eq '", testrayBuildId, "' and caseId eq '",
						testrayCaseId, "'"),
					null, "BuildsCases", testrayCache, userId))) {

			_addObjectEntry(
				"BuildsCases", serviceContext, testrayCache, userId,
				HashMapBuilder.<String, Serializable>put(
					"r_buildToBuildsCases_c_buildId", testrayBuildId
				).put(
					"r_caseToBuildsCases_c_caseId", testrayCaseId
				).build());
		}

		_addOrUpdateTestrayCaseResult(
			serviceContext, testcaseNode, testrayBuildDate, testrayBuildId,
			testrayCache, testrayCaseId, testrayCasePropertiesMap,
			testrayComponentId, testrayRunId, testrayTeamId, userId);
	}

	private void _addTestrayCases(
			long companyId, Element element, ServiceContext serviceContext,
			String testrayBuildDate, long testrayBuildId,
			TestrayCache testrayCache, long testrayProjectId, long testrayRunId,
			long userId)
		throws Exception {

		NodeList testCaseNodeList = element.getElementsByTagName("testcase");

		for (int i = 0; i < testCaseNodeList.getLength(); i++) {
			Node testcaseNode = testCaseNodeList.item(i);

			Map<String, Serializable> testrayCasePropertiesMap =
				_getTestrayCaseProperties((Element)testcaseNode);

			_addTestrayCase(
				companyId, serviceContext, testcaseNode, testrayBuildDate,
				testrayBuildId, testrayCache, testrayCasePropertiesMap,
				testrayProjectId, testrayRunId, userId);
		}
	}

	private void _addTestrayFactor(
			ServiceContext serviceContext, TestrayCache testrayCache,
			long testrayFactorCategoryId, long testrayFactorOptionId,
			long testrayRunId, long userId)
		throws Exception {

		_addObjectEntry(
			"Factor", serviceContext, testrayCache, userId,
			HashMapBuilder.<String, Serializable>put(
				"r_factorCategoryToFactors_c_factorCategoryId",
				testrayFactorCategoryId
			).put(
				"r_factorOptionToFactors_c_factorOptionId",
				testrayFactorOptionId
			).put(
				"r_runToFactors_c_runId", testrayRunId
			).build());
	}

	private void _addTestrayTestSuite(
			long buildId, long caseResultAmount, String dueStatus,
			long executionTime, String fileName, long fileSize,
			ServiceContext serviceContext, TestrayCache testrayCache,
			long userId)
		throws Exception {

		_addObjectEntry(
			"TestSuite", serviceContext, testrayCache, userId,
			HashMapBuilder.<String, Serializable>put(
				"caseResultAmount", caseResultAmount
			).put(
				"dueStatus", dueStatus
			).put(
				"executionTime", executionTime
			).put(
				"fileName", fileName
			).put(
				"fileSize", fileSize
			).put(
				"r_buildToTestSuite_c_buildId", buildId
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

	private long _getObjectEntryId(
			long companyId, String filterString, String objectEntryIdsKey,
			String[] selectedObjectFieldNames, String shortName,
			TestrayCache testrayCache, long userId)
		throws Exception {

		Long objectEntryId = testrayCache.getObjectEntryId(objectEntryIdsKey);

		if (objectEntryId != null) {
			return objectEntryId;
		}

		List<Map<String, Serializable>> valuesList = _getValuesList(
			companyId, filterString, selectedObjectFieldNames, shortName,
			testrayCache, userId);

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
			TestrayCache testrayCache, long testrayProjectId,
			long testrayRoutineId, long userId)
		throws Exception {

		String objectEntryIdsKey = StringBundler.concat(
			"Build#", testrayBuildName, "#ProjectId#", testrayProjectId);

		long testrayBuildId = _getObjectEntryId(
			companyId,
			StringBundler.concat(
				"projectId eq '", testrayProjectId, "' and name eq '",
				testrayBuildName, "'"),
			objectEntryIdsKey, new String[] {"c_buildId"}, "Build",
			testrayCache, userId);

		if (testrayBuildId != 0) {
			return testrayBuildId;
		}

		long testrayProductVersionId = _getTestrayProductVersionId(
			companyId, serviceContext, testrayCache,
			propertiesMap.get("testray.product.version"), testrayProjectId,
			userId);

		ObjectEntry objectEntry = _addObjectEntry(
			"Build", serviceContext, testrayCache, userId,
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

		testrayCache.addObjectEntryId(objectEntryIdsKey, testrayBuildId);

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
			TestrayCache testrayCache, String testrayCaseTypeName, long userId)
		throws Exception {

		String objectEntryIdsKey = "CaseType#" + testrayCaseTypeName;

		long testrayCaseTypeId = _getObjectEntryId(
			companyId, "name eq '" + testrayCaseTypeName + "'",
			objectEntryIdsKey, new String[] {"c_caseTypeId"}, "CaseType",
			testrayCache, userId);

		if (testrayCaseTypeId != 0) {
			return testrayCaseTypeId;
		}

		ObjectEntry objectEntry = _addObjectEntry(
			"CaseType", serviceContext, testrayCache, userId,
			HashMapBuilder.<String, Serializable>put(
				"name", testrayCaseTypeName
			).build());

		testrayCaseTypeId = objectEntry.getObjectEntryId();

		testrayCache.addObjectEntryId(objectEntryIdsKey, testrayCaseTypeId);

		return testrayCaseTypeId;
	}

	private long _getTestrayComponentId(
			long companyId, ServiceContext serviceContext,
			TestrayCache testrayCache, String testrayComponentName,
			long testrayProjectId, long testrayTeamId, long userId)
		throws Exception {

		String objectEntryIdsKey = StringBundler.concat(
			"Component#", testrayComponentName, "#ProjectId#",
			testrayProjectId);

		Long objectEntryId = testrayCache.getObjectEntryId(objectEntryIdsKey);

		Map<String, Serializable> values = null;

		if (objectEntryId == null) {
			List<Map<String, Serializable>> valuesList = _getValuesList(
				companyId,
				StringBundler.concat(
					"projectId eq '", testrayProjectId, "' and name eq '",
					testrayComponentName, "'"),
				null, "Component", testrayCache, userId);

			if (ListUtil.isNotEmpty(valuesList)) {
				values = valuesList.get(0);

				objectEntryId = GetterUtil.getLong(values.get("objectEntryId"));
			}
			else {
				ObjectEntry objectEntry = _addObjectEntry(
					"Component", serviceContext, testrayCache, userId,
					HashMapBuilder.<String, Serializable>put(
						"name", testrayComponentName
					).put(
						"r_projectToComponents_c_projectId", testrayProjectId
					).put(
						"r_teamToComponents_c_teamId", testrayTeamId
					).build());

				long testrayComponentId = objectEntry.getObjectEntryId();

				testrayCache.addObjectEntryId(
					objectEntryIdsKey, testrayComponentId);

				return testrayComponentId;
			}
		}

		if (values == null) {
			values = _objectEntryLocalService.getValues(objectEntryId);
		}

		long currentTestrayTeamId = GetterUtil.getLong(
			values.get("r_teamToComponents_c_teamId"));

		if (testrayTeamId == currentTestrayTeamId) {
			return objectEntryId;
		}

		_updateObjectEntry(
			GetterUtil.getLong(values.get("objectEntryId")), serviceContext,
			userId,
			HashMapBuilder.<String, Serializable>put(
				"name", testrayComponentName
			).put(
				"r_projectToComponents_c_projectId", testrayProjectId
			).put(
				"r_teamToComponents_c_teamId", testrayTeamId
			).build());

		return objectEntryId;
	}

	private long _getTestrayFactorCategoryId(
			long companyId, ServiceContext serviceContext,
			TestrayCache testrayCache, String testrayFactorCategoryName,
			long userId)
		throws Exception {

		String objectEntryIdsKey =
			"FactorCategory#" + testrayFactorCategoryName;

		long testrayFactorCategoryId = _getObjectEntryId(
			companyId, "name eq '" + testrayFactorCategoryName + "'",
			objectEntryIdsKey, new String[] {"c_factorCategoryId"},
			"FactorCategory", testrayCache, userId);

		if (testrayFactorCategoryId != 0) {
			return testrayFactorCategoryId;
		}

		ObjectEntry objectEntry = _addObjectEntry(
			"FactorCategory", serviceContext, testrayCache, userId,
			HashMapBuilder.<String, Serializable>put(
				"name", testrayFactorCategoryName
			).build());

		testrayFactorCategoryId = objectEntry.getObjectEntryId();

		testrayCache.addObjectEntryId(
			objectEntryIdsKey, testrayFactorCategoryId);

		return testrayFactorCategoryId;
	}

	private long _getTestrayFactorOptionId(
			long companyId, ServiceContext serviceContext,
			TestrayCache testrayCache, long testrayFactorCategoryId,
			String testrayFactorOptionName, long userId)
		throws Exception {

		String objectEntryIdsKey = StringBundler.concat(
			"FactorOption#", testrayFactorOptionName, "#FactorCategoryId#",
			testrayFactorCategoryId);

		long testrayFactorOptionId = _getObjectEntryId(
			companyId,
			StringBundler.concat(
				"factorCategoryId eq '", testrayFactorCategoryId,
				"' and name eq '", testrayFactorOptionName, "'"),
			objectEntryIdsKey, new String[] {"c_factorOptionId"},
			"FactorOption", testrayCache, userId);

		if (testrayFactorOptionId != 0) {
			return testrayFactorOptionId;
		}

		ObjectEntry objectEntry = _addObjectEntry(
			"FactorOption", serviceContext, testrayCache, userId,
			HashMapBuilder.<String, Serializable>put(
				"name", testrayFactorOptionName
			).put(
				"r_factorCategoryToOptions_c_factorCategoryId",
				testrayFactorCategoryId
			).build());

		testrayFactorOptionId = objectEntry.getObjectEntryId();

		testrayCache.addObjectEntryId(objectEntryIdsKey, testrayFactorOptionId);

		return testrayFactorOptionId;
	}

	private long _getTestrayProductVersionId(
			long companyId, ServiceContext serviceContext,
			TestrayCache testrayCache, String testrayProductVersionName,
			long testrayProjectId, long userId)
		throws Exception {

		String objectEntryIdsKey =
			"ProductVersion#" + testrayProductVersionName;

		long testrayProductVersionId = _getObjectEntryId(
			companyId, "name eq '" + testrayProductVersionName + "'",
			objectEntryIdsKey, new String[] {"c_productVersionId"},
			"ProductVersion", testrayCache, userId);

		if (testrayProductVersionId != 0) {
			return testrayProductVersionId;
		}

		ObjectEntry objectEntry = _addObjectEntry(
			"ProductVersion", serviceContext, testrayCache, userId,
			HashMapBuilder.<String, Serializable>put(
				"name", testrayProductVersionName
			).put(
				"r_projectToProductVersions_c_projectId", testrayProjectId
			).build());

		testrayProductVersionId = objectEntry.getObjectEntryId();

		testrayCache.addObjectEntryId(
			objectEntryIdsKey, testrayProductVersionId);

		return testrayProductVersionId;
	}

	private long _getTestrayProjectId(
			long companyId, ServiceContext serviceContext,
			TestrayCache testrayCache, String testrayProjectName, long userId)
		throws Exception {

		String objectEntryIdsKey = "Project#" + testrayProjectName;

		long testrayProjectId = _getObjectEntryId(
			companyId, "name eq '" + testrayProjectName + "'",
			objectEntryIdsKey, new String[] {"c_projectId"}, "Project",
			testrayCache, userId);

		if (testrayProjectId != 0) {
			return testrayProjectId;
		}

		ObjectEntry objectEntry = _addObjectEntry(
			"Project", serviceContext, testrayCache, userId,
			HashMapBuilder.<String, Serializable>put(
				"name", testrayProjectName
			).build());

		testrayProjectId = objectEntry.getObjectEntryId();

		testrayCache.addObjectEntryId(objectEntryIdsKey, testrayProjectId);

		return testrayProjectId;
	}

	private long _getTestrayRoutineId(
			long companyId, ServiceContext serviceContext,
			TestrayCache testrayCache, long testrayProjectId,
			String testrayRoutineName, long userId)
		throws Exception {

		String objectEntryIdsKey = StringBundler.concat(
			"Routine#", testrayRoutineName, "#ProjectId#", testrayProjectId);

		long testrayRoutineId = _getObjectEntryId(
			companyId,
			StringBundler.concat(
				"projectId eq '", testrayProjectId, "' and name eq '",
				testrayRoutineName, "'"),
			objectEntryIdsKey, new String[] {"c_RoutineId"}, "Routine",
			testrayCache, userId);

		if (testrayRoutineId != 0) {
			return testrayRoutineId;
		}

		ObjectEntry objectEntry = _addObjectEntry(
			"Routine", serviceContext, testrayCache, userId,
			HashMapBuilder.<String, Serializable>put(
				"name", testrayRoutineName
			).put(
				"r_routineToProjects_c_projectId", testrayProjectId
			).build());

		testrayRoutineId = objectEntry.getObjectEntryId();

		testrayCache.addObjectEntryId(objectEntryIdsKey, testrayRoutineId);

		_addDefaultFactors(
			companyId, serviceContext, testrayCache, testrayRoutineId, userId);

		return testrayRoutineId;
	}

	private String _getTestrayRunEnvironmentHash(
			long companyId, Element element, ServiceContext serviceContext,
			TestrayCache testrayCache, long testrayRunId, long userId)
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
				companyId, serviceContext, testrayCache,
				testrayFactorCategoryName, userId);

			String testrayFactorOptionName = _getAttributeValue("option", node);

			long testrayFactorOptionId = _getTestrayFactorOptionId(
				companyId, serviceContext, testrayCache,
				testrayFactorCategoryId, testrayFactorOptionName, userId);

			_addTestrayFactor(
				serviceContext, testrayCache, testrayFactorCategoryId,
				testrayFactorOptionId, testrayRunId, userId);

			sb.append(testrayFactorCategoryId);
			sb.append(testrayFactorOptionId);
		}

		String testrayFactorsString = sb.toString();

		return String.valueOf(testrayFactorsString.hashCode());
	}

	private long _getTestrayRunId(
			long companyId, Element element, ServiceContext serviceContext,
			Map<String, String> propertiesMap, long testrayBuildId,
			TestrayCache testrayCache, String testrayRunName, long userId)
		throws Exception {

		String objectEntryIdsKey = StringBundler.concat(
			"Run#", testrayRunName, "#BuildId#", testrayBuildId);

		long testrayRunId = _getObjectEntryId(
			companyId,
			StringBundler.concat(
				"buildId eq '", testrayBuildId, "' and name eq '",
				testrayRunName, "'"),
			objectEntryIdsKey, new String[] {"c_runId"}, "Run", testrayCache,
			userId);

		if (testrayRunId != 0) {
			return testrayRunId;
		}

		ObjectEntry objectEntry = _addObjectEntry(
			"Run", serviceContext, testrayCache, userId,
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
				"number", testrayCache.getNextTestrayRunNumber()
			).put(
				"r_buildToRuns_c_buildId", testrayBuildId
			).build());

		testrayRunId = objectEntry.getObjectEntryId();

		objectEntry.getValues(
		).put(
			"environmentHash",
			_getTestrayRunEnvironmentHash(
				companyId, element, serviceContext, testrayCache, testrayRunId,
				userId)
		);

		_objectEntryLocalService.updateObjectEntry(
			userId, objectEntry.getObjectEntryId(), objectEntry.getValues(),
			serviceContext);

		testrayCache.addObjectEntryId(objectEntryIdsKey, testrayRunId);

		return testrayRunId;
	}

	private long _getTestrayTeamId(
			long companyId, ServiceContext serviceContext,
			TestrayCache testrayCache, long testrayProjectId,
			String testrayTeamName, long userId)
		throws Exception {

		String objectEntryIdsKey = StringBundler.concat(
			"Team#", testrayTeamName, "#ProjectId#", testrayProjectId);

		long testrayTeamId = _getObjectEntryId(
			companyId,
			StringBundler.concat(
				"projectId eq '", testrayProjectId, "' and name eq '",
				testrayTeamName, "'"),
			objectEntryIdsKey, new String[] {"c_teamId"}, "Team", testrayCache,
			userId);

		if (testrayTeamId != 0) {
			return testrayTeamId;
		}

		ObjectEntry objectEntry = _addObjectEntry(
			"Team", serviceContext, testrayCache, userId,
			HashMapBuilder.<String, Serializable>put(
				"name", testrayTeamName
			).put(
				"r_projectToTeams_c_projectId", testrayProjectId
			).build());

		testrayCache.addObjectEntryId(
			objectEntryIdsKey, objectEntry.getObjectEntryId());

		return objectEntry.getObjectEntryId();
	}

	private List<Map<String, Serializable>> _getValuesList(
			long companyId, String filterString,
			String[] selectedObjectFieldNames, String shortName,
			TestrayCache testrayCache, long userId)
		throws Exception {

		ObjectDefinition objectDefinition = testrayCache.getObjectDefinition(
			shortName);

		return _objectEntryLocalService.getValuesList(
			0, companyId, userId, objectDefinition.getObjectDefinitionId(),
			selectedObjectFieldNames,
			_filterFactory.create(filterString, objectDefinition), null,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	private void _loadObjectDefinitions(
		long companyId, TestrayCache testrayCache) {

		List<ObjectDefinition> objectDefinitions =
			_objectDefinitionLocalService.getObjectDefinitions(
				companyId, true, WorkflowConstants.STATUS_APPROVED);

		if (ListUtil.isEmpty(objectDefinitions)) {
			return;
		}

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			testrayCache.addObjectDefinition(objectDefinition);
		}
	}

	private void _loadTestrayCaseTypes(
			long companyId, TestrayCache testrayCache, long userId)
		throws Exception {

		List<Map<String, Serializable>> valuesList = _getValuesList(
			companyId, null, null, "CaseType", testrayCache, userId);

		if (ListUtil.isEmpty(valuesList)) {
			return;
		}

		for (Map<String, Serializable> values : valuesList) {
			testrayCache.addObjectEntryId(
				"CaseType#name" + values.get("name"),
				GetterUtil.getLong(values.get("caseTypeId")));
		}
	}

	private void _loadTestrayComponents(
			long companyId, TestrayCache testrayCache, long userId)
		throws Exception {

		List<Map<String, Serializable>> valuesList = _getValuesList(
			companyId, null, null, "Component", testrayCache, userId);

		if (ListUtil.isEmpty(valuesList)) {
			return;
		}

		for (Map<String, Serializable> values : valuesList) {
			testrayCache.addObjectEntryId(
				StringBundler.concat(
					"Component#", GetterUtil.getString(values.get("name")),
					"#TeamId#",
					GetterUtil.getLong(
						values.get("r_teamToComponents_c_teamId"))),
				GetterUtil.getLong(values.get("c_componentId")));
		}
	}

	private void _loadTestrayFactorCategories(
			long companyId, TestrayCache testrayCache, long userId)
		throws Exception {

		List<Map<String, Serializable>> valuesList = _getValuesList(
			companyId, null, new String[] {"c_factorCategoryId", "name"},
			"FactorCategory", testrayCache, userId);

		if (ListUtil.isEmpty(valuesList)) {
			return;
		}

		for (Map<String, Serializable> values : valuesList) {
			testrayCache.addObjectEntryId(
				"FactorCategory#" + GetterUtil.getString(values.get("name")),
				GetterUtil.getLong(values.get("c_factorCategoryId")));
		}
	}

	private void _loadTestrayFactorOptions(
			long companyId, TestrayCache testrayCache, long userId)
		throws Exception {

		List<Map<String, Serializable>> valuesList = _getValuesList(
			companyId, null, null, "FactorOption", testrayCache, userId);

		if (ListUtil.isEmpty(valuesList)) {
			return;
		}

		for (Map<String, Serializable> values : valuesList) {
			testrayCache.addObjectEntryId(
				StringBundler.concat(
					"FactorOption#", GetterUtil.getString(values.get("name")),
					"#FactorCategoryId#",
					GetterUtil.getLong(
						values.get(
							"r_factorCategoryToOptions_c_factorCategoryId"))),
				GetterUtil.getLong(values.get("c_factorCategoryId")));
		}
	}

	private void _loadTestrayProjects(
			long companyId, TestrayCache testrayCache, long userId)
		throws Exception {

		List<Map<String, Serializable>> valuesList = _getValuesList(
			companyId, null, new String[] {"c_projectId", "name"}, "Project",
			testrayCache, userId);

		if (ListUtil.isEmpty(valuesList)) {
			return;
		}

		for (Map<String, Serializable> values : valuesList) {
			testrayCache.addObjectEntryId(
				"Project#" + GetterUtil.getString(values.get("name")),
				GetterUtil.getLong(values.get("c_projectId")));
		}
	}

	private void _loadTestrayTeams(
			long companyId, TestrayCache testrayCache, long userId)
		throws Exception {

		List<Map<String, Serializable>> valuesList = _getValuesList(
			companyId, null, null, "Team", testrayCache, userId);

		if (ListUtil.isEmpty(valuesList)) {
			return;
		}

		for (Map<String, Serializable> values : valuesList) {
			testrayCache.addObjectEntryId(
				StringBundler.concat(
					"Team#", GetterUtil.getString(values.get("name")),
					"#ProjectId#",
					GetterUtil.getLong(
						values.get("r_projectToTeams_c_projectIds"))),
				GetterUtil.getLong(values.get("c_teamId")));
		}
	}

	private ObjectEntry _updateObjectEntry(
			long objectEntryId, ServiceContext serviceContext, long userId,
			Map<String, Serializable> values)
		throws Exception {

		return _objectEntryLocalService.updateObjectEntry(
			userId, objectEntryId, values, serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TestrayManagerImpl.class);

	@Reference(
		target = "(filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT + ")"
	)
	private FilterFactory<Predicate> _filterFactory;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}