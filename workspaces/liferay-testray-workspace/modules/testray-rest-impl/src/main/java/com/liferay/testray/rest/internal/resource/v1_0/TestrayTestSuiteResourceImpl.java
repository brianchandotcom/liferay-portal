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

		File tempFile = FileUtil.createTempFile(
			multipartBody.getBinaryFileAsBytes("file"));

		Document document = documentBuilder.parse(tempFile);

		_processDocument(document);

		TestrayTestSuite testrayTestSuite = new TestrayTestSuite();

		testrayTestSuite.setRuntime(System.currentTimeMillis() - startTime);
		testrayTestSuite.setXmlFileName(
			multipartBody.getBinaryFile(
				"file"
			).getFileName());

		return testrayTestSuite;
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
				"dueDate", propertiesMap.get("testray.build.time")
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

	private void _processDocument(Document document) throws Exception {
		Element element = document.getDocumentElement();

		Map<String, String> propertiesMap = _getPropertiesMap(element);

		long testrayProjectId = _getTestrayProjectId(
			contextCompany.getCompanyId(),
			propertiesMap.get("testray.project.name"));

		long testrayRoutineId = _getTestrayRoutineId(
			contextCompany.getCompanyId(), testrayProjectId,
			propertiesMap.get("testray.build.type"));

		_getTestrayBuildId(
			contextCompany.getCompanyId(), propertiesMap,
			propertiesMap.get("testray.build.name"), testrayProjectId,
			testrayRoutineId);
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