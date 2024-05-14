/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.rest.internal.resource.v1_0;

import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.testray.rest.dto.v1_0.TestrayCache;
import com.liferay.testray.rest.dto.v1_0.TestrayTestSuite;
import com.liferay.testray.rest.manager.TestrayManager;
import com.liferay.testray.rest.resource.v1_0.TestrayTestSuiteResource;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

import org.w3c.dom.Document;

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

		String fileName = multipartBody.getBinaryFile(
			"file"
		).getFileName();

		if (fileName.endsWith(".tar")) {
			_testrayManager.processArchive(
				contextCompany.getCompanyId(),
				multipartBody.getBinaryFileAsBytes("file"), fileName,
				_serviceContextHelper.getServiceContext(),
				contextUser.getUserId());
		}
		else if (fileName.endsWith(".xml")) {
			DocumentBuilderFactory documentBuilderFactory =
				SecureXMLFactoryProviderUtil.newDocumentBuilderFactory();

			DocumentBuilder documentBuilder =
				documentBuilderFactory.newDocumentBuilder();

			File file = null;

			try {
				file = FileUtil.createTempFile(
					multipartBody.getBinaryFileAsBytes("file"));

				Document document = documentBuilder.parse(file);

				TestrayCache testrayCache = new TestrayCache();

				_testrayManager.loadTestrayCache(
					contextCompany.getCompanyId(), testrayCache,
					contextUser.getUserId());

				_testrayManager.processDocument(
					contextCompany.getCompanyId(), document, fileName,
					file.length(), _serviceContextHelper.getServiceContext(),
					testrayCache, contextUser.getUserId());
			}
			finally {
				FileUtil.delete(file);
			}
		}
		else {
			throw new FileExtensionException("File not supported");
		}

		TestrayTestSuite testrayTestSuite = new TestrayTestSuite();

		testrayTestSuite.setFileName(fileName);
		testrayTestSuite.setRuntime(System.currentTimeMillis() - startTime);

		return testrayTestSuite;
	}

	@Reference
	private ServiceContextHelper _serviceContextHelper;

	@Reference
	private TestrayManager _testrayManager;

}