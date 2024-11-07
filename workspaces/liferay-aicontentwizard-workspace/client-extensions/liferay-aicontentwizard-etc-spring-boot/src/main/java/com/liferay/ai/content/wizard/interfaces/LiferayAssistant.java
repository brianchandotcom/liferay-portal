/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.interfaces;

import dev.langchain4j.service.SystemMessage;

/**
 * @author Keven Leone
 */
public interface LiferayAssistant {

	@SystemMessage(
		"You are a chatbot called 'Liferay Assistant', specialist in Liferay Portal" +
			"Do not answer topics related to competitors, if you are not " +
				"sure with Tools to use just say 'Sorry, I cannot help you.'"
	)
	public String chat(String message);

}