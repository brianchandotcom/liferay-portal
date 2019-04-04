/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.talend.service;

import com.liferay.talend.datastore.BasicDataStore;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.service.Service;
import org.talend.sdk.component.api.service.completion.SuggestionValues;
import org.talend.sdk.component.api.service.completion.Suggestions;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Igor Beslic
 */
@Service
public class UIActionService {

	@Suggestions("fetchEndpoints")
	public SuggestionValues fetchEndpoints(@Option("restDataSet") final BasicDataStore dataStore) {
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

}
