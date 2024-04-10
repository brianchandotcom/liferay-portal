package com.liferay.testray.rest.internal.resource.v1_0;

import com.liferay.testray.rest.resource.v1_0.TestrayBuildAutofillResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Nilton Vieira
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/testray-build-autofill.properties",
	scope = ServiceScope.PROTOTYPE, service = TestrayBuildAutofillResource.class
)
public class TestrayBuildAutofillResourceImpl
	extends BaseTestrayBuildAutofillResourceImpl {
}