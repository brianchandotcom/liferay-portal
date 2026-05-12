/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sample;

import org.junit.jupiter.api.Test;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author Ryan Schuhler
 */
public class ReadyRestControllerTest {

	@Test
	public void testGet() throws Exception {
		_mockMvc.perform(
			MockMvcRequestBuilders.get("/ready")
		).andExpect(
			MockMvcResultMatchers.status(
			).isOk()
		).andExpect(
			MockMvcResultMatchers.content(
			).string(
				"READY"
			)
		);
	}

	private final MockMvc _mockMvc = MockMvcBuilders.standaloneSetup(
		new ReadyRestController()
	).build();

}