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

	private void _processDocument(Document document) throws Exception {
		Element element = document.getDocumentElement();

		Map<String, String> propertiesMap = _getPropertiesMap(element);

		_getTestrayProjectId(
			contextCompany.getCompanyId(),
			propertiesMap.get("testray.project.name"));
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