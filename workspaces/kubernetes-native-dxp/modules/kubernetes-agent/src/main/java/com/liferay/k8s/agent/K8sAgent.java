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

package com.liferay.k8s.agent;

import java.util.Map;
import java.util.function.Predicate;

/**
 * @author Raymond Augé
 */
public interface K8sAgent {

	public void createOrUpdateConfigMap(
		Map<String, String> data, Map<String, String> labels, String name);

	public void deleteConfigMap(String name);

	public void deleteConfigMapByLabels(
		String name, Predicate<Map<String, String>> predicate);

}