package com.liferay.testray.rest.internal.resource.v1_0;

import com.liferay.testray.rest.resource.v1_0.TestrayRoutineDurationReportResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Nilton Vieira
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/testray-routine-duration-report.properties",
	scope = ServiceScope.PROTOTYPE,
	service = TestrayRoutineDurationReportResource.class
)
public class TestrayRoutineDurationReportResourceImpl
	extends BaseTestrayRoutineDurationReportResourceImpl {
}