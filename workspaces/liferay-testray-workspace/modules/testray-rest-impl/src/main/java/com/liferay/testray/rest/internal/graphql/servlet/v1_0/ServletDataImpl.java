package com.liferay.testray.rest.internal.graphql.servlet.v1_0;

import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;
import com.liferay.testray.rest.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.testray.rest.internal.graphql.query.v1_0.Query;
import com.liferay.testray.rest.internal.resource.v1_0.TestrayBuildAutofillResourceImpl;
import com.liferay.testray.rest.internal.resource.v1_0.TestrayRunComparisonResourceImpl;
import com.liferay.testray.rest.internal.resource.v1_0.TestrayStatusMetricResourceImpl;
import com.liferay.testray.rest.internal.resource.v1_0.TestrayTestSuiteResourceImpl;
import com.liferay.testray.rest.resource.v1_0.TestrayBuildAutofillResource;
import com.liferay.testray.rest.resource.v1_0.TestrayRunComparisonResource;
import com.liferay.testray.rest.resource.v1_0.TestrayStatusMetricResource;
import com.liferay.testray.rest.resource.v1_0.TestrayTestSuiteResource;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Nilton Vieira
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setTestrayBuildAutofillResourceComponentServiceObjects(
			_testrayBuildAutofillResourceComponentServiceObjects);
		Mutation.setTestrayTestSuiteResourceComponentServiceObjects(
			_testrayTestSuiteResourceComponentServiceObjects);

		Query.setTestrayRunComparisonResourceComponentServiceObjects(
			_testrayRunComparisonResourceComponentServiceObjects);
		Query.setTestrayStatusMetricResourceComponentServiceObjects(
			_testrayStatusMetricResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "TestrayRest";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/testray-rest-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodObjectValuePair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodObjectValuePairs.get(
				"mutation#" + methodName);
		}

		return _resourceMethodObjectValuePairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodObjectValuePairs =
			new HashMap<String, ObjectValuePair<Class<?>, String>>() {
				{
					put(
						"mutation#createTestrayBuildAutofill",
						new ObjectValuePair<>(
							TestrayBuildAutofillResourceImpl.class,
							"postTestrayBuildAutofill"));
					put(
						"mutation#createTestrayBuildAutofillBatch",
						new ObjectValuePair<>(
							TestrayBuildAutofillResourceImpl.class,
							"postTestrayBuildAutofillBatch"));
					put(
						"mutation#createTestrayTestSuite",
						new ObjectValuePair<>(
							TestrayTestSuiteResourceImpl.class,
							"postTestrayTestSuite"));
					put(
						"mutation#createTestrayTestSuiteBatch",
						new ObjectValuePair<>(
							TestrayTestSuiteResourceImpl.class,
							"postTestrayTestSuiteBatch"));

					put(
						"query#testrayRunComparison",
						new ObjectValuePair<>(
							TestrayRunComparisonResourceImpl.class,
							"getTestrayRunComparison"));
					put(
						"query#testrayRunComparisonDetail",
						new ObjectValuePair<>(
							TestrayRunComparisonResourceImpl.class,
							"getTestrayRunComparisonDetail"));
					put(
						"query#testrayStatusMetricByTestrayBuildIdTestrayBuildTestrayCaseTypesMetrics",
						new ObjectValuePair<>(
							TestrayStatusMetricResourceImpl.class,
							"getTestrayStatusMetricByTestrayBuildIdTestrayBuildTestrayCaseTypesMetricsPage"));
					put(
						"query#testrayStatusMetricByTestrayBuildIdTestrayBuildTestrayComponentsMetrics",
						new ObjectValuePair<>(
							TestrayStatusMetricResourceImpl.class,
							"getTestrayStatusMetricByTestrayBuildIdTestrayBuildTestrayComponentsMetricsPage"));
					put(
						"query#testrayStatusMetricByTestrayBuildIdTestrayBuildTestrayRunsMetrics",
						new ObjectValuePair<>(
							TestrayStatusMetricResourceImpl.class,
							"getTestrayStatusMetricByTestrayBuildIdTestrayBuildTestrayRunsMetricsPage"));
					put(
						"query#testrayStatusMetricByTestrayBuildIdTestrayBuildTestrayTeamsMetrics",
						new ObjectValuePair<>(
							TestrayStatusMetricResourceImpl.class,
							"getTestrayStatusMetricByTestrayBuildIdTestrayBuildTestrayTeamsMetricsPage"));
				}
			};

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TestrayBuildAutofillResource>
		_testrayBuildAutofillResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TestrayTestSuiteResource>
		_testrayTestSuiteResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TestrayRunComparisonResource>
		_testrayRunComparisonResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TestrayStatusMetricResource>
		_testrayStatusMetricResourceComponentServiceObjects;

}