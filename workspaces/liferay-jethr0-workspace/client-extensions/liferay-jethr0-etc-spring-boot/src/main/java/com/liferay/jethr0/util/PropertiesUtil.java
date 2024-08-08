/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.util;

import com.liferay.portal.kernel.util.ArrayUtil;

import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ObjectUtils;

/**
 * @author Michael Hashimoto
 */
public class PropertiesUtil {

	public static Properties combine(Properties... propertiesArray)
		throws IOException {

		Properties properties = new Properties();

		if (propertiesArray != null) {
			for (Properties propertiesEntry : propertiesArray) {
				if (propertiesEntry == null) {
					continue;
				}

				for (String propertyName :
						propertiesEntry.stringPropertyNames()) {

					properties.setProperty(
						propertyName,
						propertiesEntry.getProperty(propertyName));
				}
			}
		}

		return properties;
	}

	public static Properties getProperties(String propertiesContent)
		throws IOException {

		Properties properties = new Properties();

		properties.load(new StringReader(propertiesContent));

		return properties;
	}

	public static String getPropertyValue(
		Properties properties, String basePropertyName, String... opts) {

		String propertyName = _getPropertyName(
			properties, basePropertyName, opts);

		return _getPropertyValue(
			properties, new ArrayList<String>(), propertyName);
	}

	private static String _getFilteredPropertyValue(String propertyValue) {
		if (propertyValue == null) {
			return null;
		}

		List<String> propertyValues = new ArrayList<>();

		for (String value : propertyValue.split("\\s*,\\s*")) {
			if (!value.startsWith("#")) {
				propertyValues.add(value);
			}
		}

		return StringUtil.join(",", propertyValues);
	}

	private static Set<Set<String>> _getOrderedOptSets(String... opts) {
		Set<Set<String>> optSets = new LinkedHashSet<>();

		optSets.add(new LinkedHashSet<>(Arrays.asList(opts)));

		int optCount = opts.length;

		for (int i = optCount - 1; i >= 0; i--) {
			String[] childOpts = new String[optCount - 1];

			if (childOpts.length == 0) {
				continue;
			}

			for (int j = 0; j < optCount; j++) {
				if (j < i) {
					childOpts[j] = opts[j];
				}
				else if (j > i) {
					childOpts[j - 1] = opts[j];
				}
			}

			optSets.addAll(_getOrderedOptSets(childOpts));
		}

		Set<Set<String>> orderedOptSet = new LinkedHashSet<>();

		for (int i = optCount; i > 0; i--) {
			for (Set<String> optSet : optSets) {
				if (optSet.size() == i) {
					orderedOptSet.add(optSet);
				}
			}
		}

		return orderedOptSet;
	}

	private static String _getPropertyName(
		Properties properties, String basePropertyName, String... opts) {

		if (ArrayUtil.isEmpty(opts)) {
			return basePropertyName;
		}

		Set<String> optSet = new LinkedHashSet<>(Arrays.asList(opts));

		optSet.remove(null);

		opts = optSet.toArray(new String[0]);

		Properties matchingProperties = new Properties();

		for (Object key : properties.keySet()) {
			String keyString = key.toString();

			if (keyString.matches(
					Pattern.quote(basePropertyName) + "(\\[.*|$)")) {

				matchingProperties.setProperty(
					keyString, properties.getProperty(keyString));
			}
		}

		Set<Set<String>> targetOptSets = _getOrderedOptSets(opts);

		String propertyName = null;

		Map<String, Set<String>> propertyOptRegexSets =
			_getPropertyOptRegexSets(
				basePropertyName, matchingProperties.stringPropertyNames());

		for (Set<String> targetOptSet : targetOptSets) {
			for (Map.Entry<String, Set<String>> propertyOptRegexEntry :
					propertyOptRegexSets.entrySet()) {

				Set<String> propertyOptRegexes =
					propertyOptRegexEntry.getValue();

				if (targetOptSet.size() < propertyOptRegexes.size()) {
					continue;
				}

				boolean matchesAllPropertyOptRegexes = true;

				for (String targetOpt : targetOptSet) {
					boolean matchesPropertyOptRegex = false;

					for (String propertyOptRegex : propertyOptRegexes) {
						if (targetOpt.matches(propertyOptRegex)) {
							matchesPropertyOptRegex = true;
						}
					}

					if (!matchesPropertyOptRegex) {
						matchesAllPropertyOptRegexes = false;

						break;
					}
				}

				if (matchesAllPropertyOptRegexes) {
					propertyName = propertyOptRegexEntry.getKey();

					break;
				}
			}

			if (propertyName != null) {
				break;
			}
		}

		if (propertyName != null) {
			return propertyName;
		}

		return basePropertyName;
	}

	private static Map<String, Set<String>> _getPropertyOptRegexSets(
		String basePropertyName, Set<String> propertyNames) {

		Map<String, Set<String>> propertyOptRegexSets = new LinkedHashMap<>();

		List<String> orderedPropertyNames = new ArrayList<>(propertyNames);

		Collections.sort(orderedPropertyNames, new PropertyNameComparator());

		Set<String> basePropertyOptSet = _getPropertyOptSet(basePropertyName);

		for (String propertyName : orderedPropertyNames) {
			Set<String> propertyOptSet = _getPropertyOptSet(propertyName);

			propertyOptSet.removeAll(basePropertyOptSet);

			propertyOptRegexSets.put(propertyName, propertyOptSet);
		}

		return propertyOptRegexSets;
	}

	private static Set<String> _getPropertyOptSet(String propertyName) {
		Set<String> propertyOptSet = new LinkedHashSet<>();

		List<Integer> indices = new ArrayList<>();
		Stack<Integer> stack = new Stack<>();

		Integer start = null;

		for (int i = 0; i < propertyName.length(); i++) {
			char c = propertyName.charAt(i);

			if (c == '[') {
				stack.push(i);

				if (start == null) {
					start = i;

					indices.add(start);
				}
			}

			if (c == ']') {
				if (start == null) {
					continue;
				}

				stack.pop();

				if (stack.isEmpty()) {
					indices.add(i);

					start = null;
				}
			}
		}

		for (int i = 0; i < indices.size(); i++) {
			int nextIndex = propertyName.length();

			if (indices.size() > (i + 1)) {
				nextIndex = indices.get(i + 1);
			}

			String opt = Pattern.quote(
				propertyName.substring(indices.get(i) + 1, nextIndex));

			propertyOptSet.add(opt.replaceAll("\\*", "\\\\E.*\\\\Q"));

			i++;
		}

		return propertyOptSet;
	}

	private static String _getPropertyValue(
		Properties properties, List<String> previousNames, String name) {

		if (previousNames.contains(name)) {
			if (previousNames.size() > 1) {
				StringBuilder sb = new StringBuilder();

				sb.append("Circular property reference chain found\n");

				for (String previousName : previousNames) {
					sb.append(previousName);
					sb.append(" -> ");
				}

				sb.append(name);

				throw new IllegalStateException(sb.toString());
			}

			return StringUtil.combine("${", name, "}");
		}

		previousNames.add(name);

		if (!properties.containsKey(name)) {
			return null;
		}

		String value = _getFilteredPropertyValue(properties.getProperty(name));

		Matcher matcher = _nestedPropertyPattern.matcher(value);

		String newValue = value;

		while (matcher.find()) {
			String propertyGroup = matcher.group(0);
			String propertyName = matcher.group(1);

			if (properties.containsKey(propertyName)) {
				newValue = StringUtil.replace(
					newValue, propertyGroup,
					_getPropertyValue(
						properties, new ArrayList<>(previousNames),
						propertyName));
			}
		}

		return newValue;
	}

	private static final Pattern _nestedPropertyPattern = Pattern.compile(
		"\\$\\{([^\\}]+)\\}");

	private static class PropertyNameComparator implements Comparator<String> {

		@Override
		public int compare(String propertyName1, String propertyName2) {
			if ((propertyName1 == null) || (propertyName2 == null)) {
				return ObjectUtils.compare(propertyName1, propertyName2);
			}

			Set<String> propertyOptSet1 = _getPropertyOptSet(propertyName1);
			Set<String> propertyOptSet2 = _getPropertyOptSet(propertyName2);

			if (propertyOptSet1.size() != propertyOptSet2.size()) {
				return Integer.compare(
					propertyOptSet2.size(), propertyOptSet1.size());
			}

			int propertyOptRegexCount1 = _countPropertyOptRegexes(
				propertyOptSet1);
			int propertyOptRegexCount2 = _countPropertyOptRegexes(
				propertyOptSet2);

			if (propertyOptRegexCount1 != propertyOptRegexCount2) {
				return Integer.compare(
					propertyOptRegexCount1, propertyOptRegexCount2);
			}

			if (propertyName1.length() != propertyName2.length()) {
				return Integer.compare(
					propertyName2.length(), propertyName1.length());
			}

			return propertyName2.compareTo(propertyName1);
		}

		private int _countPropertyOptRegexes(Set<String> propertyOptSet) {
			int count = 0;

			for (String propertyOpt : propertyOptSet) {
				if (propertyOpt.contains(".*")) {
					count++;
				}
			}

			return count;
		}

	}

}