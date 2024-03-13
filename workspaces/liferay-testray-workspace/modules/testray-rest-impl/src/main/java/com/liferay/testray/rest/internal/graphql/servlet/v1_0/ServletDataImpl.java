package com.liferay.testray.rest.internal.graphql.servlet.v1_0;

import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;
import com.liferay.testray.rest.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.testray.rest.internal.graphql.query.v1_0.Query;
import com.liferay.testray.rest.internal.resource.v1_0.TestrayRunComparisonResourceImpl;
import com.liferay.testray.rest.resource.v1_0.TestrayRunComparisonResource;

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
		Query.setTestrayRunComparisonResourceComponentServiceObjects(
			_testrayRunComparisonResourceComponentServiceObjects);
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
						"query#testrayRunComparison",
						new ObjectValuePair<>(
							TestrayRunComparisonResourceImpl.class,
							"getTestrayRunComparison"));
				}
			};

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TestrayRunComparisonResource>
		_testrayRunComparisonResourceComponentServiceObjects;

}