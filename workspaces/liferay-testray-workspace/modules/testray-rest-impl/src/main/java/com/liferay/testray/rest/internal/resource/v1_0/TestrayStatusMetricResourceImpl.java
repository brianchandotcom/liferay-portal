package com.liferay.testray.rest.internal.resource.v1_0;

import com.liferay.testray.rest.resource.v1_0.TestrayStatusMetricResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Nilton Vieira
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/testray-status-metric.properties",
	scope = ServiceScope.PROTOTYPE, service = TestrayStatusMetricResource.class
)
public class TestrayStatusMetricResourceImpl
	extends BaseTestrayStatusMetricResourceImpl {
}