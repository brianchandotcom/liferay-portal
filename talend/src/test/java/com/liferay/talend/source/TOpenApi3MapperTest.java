package com.liferay.talend.source;

import com.liferay.talend.dataset.OpenApi3DataSet;
import com.liferay.talend.datastore.OpenApi3Connection;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.talend.sdk.component.junit.SimpleComponentRule;
import org.talend.sdk.component.junit.SimpleFactory;
import org.talend.sdk.component.runtime.manager.chain.Job;

import javax.json.JsonObject;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class TOpenApi3MapperTest {

	@ClassRule
	public static final SimpleComponentRule SIMPLE_COMPONENT_RULE =
		new SimpleComponentRule(
			TOpenApi3Mapper.class.getPackage().getName());

	@Test
	public void testOpenApiMapper() throws Exception {
		OpenApi3DataSet openApi3DataSet = _getOpenApi3DataSet();

		String configQueryString =
			SimpleFactory.configurationByExample(
				).forInstance(
				openApi3DataSet
				).configured(
				).toQueryString();

		Job.components(
		).component(
			"search",
			"LiferayFamily://tOpenApi3Mapper?" + configQueryString
		).component(
			"collector", "test://collector"
		).connections(
		).from(
			"search"
		).to(
			"collector"
		).build(
		).run();

		List<JsonObject> results = SIMPLE_COMPONENT_RULE.getCollectedData(
			JsonObject.class);

		Assert.assertEquals(16, results.size());
	}

	@Test
	public void testOpenApiProcessor() throws Exception {
		OpenApi3DataSet openApi3DataSet = _getOpenApi3DataSet();

		String configQueryString =
			SimpleFactory.configurationByExample(
			).forInstance(
				openApi3DataSet
			).configured(
			).toQueryString();

		Job.components(
		).component(
			"search",
			"LiferayFamily://tOpenApi3Mapper?" + configQueryString
		).component(
			"collector", "LiferayFamily://tOpenApi3Processor"
		).connections(
		).from(
			"search"
		).to(
			"collector"
		).build(
		).run();

		List<JsonObject> results = SIMPLE_COMPONENT_RULE.getCollectedData(
			JsonObject.class);

		Assert.assertEquals(0, results.size());
	}

	private OpenApi3DataSet _getOpenApi3DataSet()
		throws MalformedURLException {

		OpenApi3Connection openApi3Connection = new OpenApi3Connection();

		openApi3Connection.setApiKey("e4e33e24-eebf-45a4-bbab-4473a3a767ab");
		openApi3Connection.setEndpointInstanceUrl(
			new URL("https://api.swaggerhub.com"));

		OpenApi3DataSet openApi3DataSet = new OpenApi3DataSet();

		openApi3DataSet.setDataStore(openApi3Connection);

		return openApi3DataSet;
	}

}
