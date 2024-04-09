package com.liferay.testray.rest.internal.resource.v1_0;

import com.liferay.testray.rest.resource.v1_0.TestrayTestSuiteResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Nilton Vieira
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/testray-test-suite.properties",
	scope = ServiceScope.PROTOTYPE, service = TestrayTestSuiteResource.class
)
public class TestrayTestSuiteResourceImpl
	extends BaseTestrayTestSuiteResourceImpl {
}