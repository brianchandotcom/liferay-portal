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

package com.liferay.jethr0.testsuite;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TestSuiteFactory {

	public static TestSuite newTestSuite(JSONObject jsonObject) {
		long id = jsonObject.getLong("id");

		if (_testSuites.containsKey(id)) {
			return _testSuites.get(id);
		}

		TestSuite testSuite = new DefaultTestSuite(jsonObject);

		_testSuites.put(testSuite.getId(), testSuite);

		return testSuite;
	}

	public static void removeTestSuite(TestSuite testSuite) {
		if (testSuite == null) {
			return;
		}

		_testSuites.remove(testSuite.getId());
	}

	private static final Map<Long, TestSuite> _testSuites = new HashMap<>();

}