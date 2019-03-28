package com.liferay.talend.service;

import org.talend.sdk.component.api.service.Service;
import org.talend.sdk.component.api.service.completion.SuggestionValues;
import org.talend.sdk.component.api.service.completion.Suggestions;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Igor Beslic
 */
@Service
public class OpenApi3Service {

	@Suggestions("OpenApi3ConnectionApiKey")
	public SuggestionValues suggestApiKeyValues() {
		SuggestionValues suggestionValues = new SuggestionValues();

		Set<SuggestionValues.Item> items = new HashSet<>();

		items.add(
			new SuggestionValues.Item(
				"e4e33e24-eebf-45a4-bbab-4473a3a767ab",
				"liferay.swagger.api.key"));
		items.add(
			new SuggestionValues.Item(
				"e4e33e24-eebf-45a4-bbab-4473a3a767ab",
				"liferay.swagger.api.key.backup"));

		suggestionValues.setItems(items);

		return suggestionValues;
	}

	@Suggestions("OpenApi3ConnectionEndpointInstanceUrl")
	public SuggestionValues suggestEndpointInstanceURL() {
		SuggestionValues suggestionValues = new SuggestionValues();

		Set<SuggestionValues.Item> items = new HashSet<>();

		items.add(
			new SuggestionValues.Item(
				"https://api.swaggerhub.com",
				"liferay.swagger.api.endpoint.instance.url"));
		items.add(
			new SuggestionValues.Item(
				"https://api.swaggerhub.com",
				"liferay.swagger.api.endpoint.instance.url.backup"));

		suggestionValues.setItems(items);

		return suggestionValues;
	}

}
