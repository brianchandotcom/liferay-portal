/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import jakarta.annotation.Generated;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author Leslie Wong
 * @generated
 */
@Generated("")
@GraphQLName(
	description = "A topic an individual is interested in, inferred from the keywords of the pages they viewed. Use `getWorkspaceGroupChannelInterestsPage` to list interests.",
	value = "Interest"
)
@io.swagger.v3.oas.annotations.media.Schema(
	description = "A topic an individual is interested in, inferred from the keywords of the pages they viewed. Use `getWorkspaceGroupChannelInterestsPage` to list interests."
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Interest")
public class Interest implements Serializable {

	public static Interest toDTO(String json) {
		return ObjectMapperUtil.readValue(Interest.class, json);
	}

	public static Interest unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Interest.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "Day the interest scores were last calculated. Every interest in a response shares it, since only the latest calculation is returned."
	)
	public Date getDateRecorded() {
		if (_dateRecordedSupplier != null) {
			dateRecorded = _dateRecordedSupplier.get();

			_dateRecordedSupplier = null;
		}

		return dateRecorded;
	}

	public void setDateRecorded(Date dateRecorded) {
		this.dateRecorded = dateRecorded;

		_dateRecordedSupplier = null;
	}

	@JsonIgnore
	public void setDateRecorded(
		UnsafeSupplier<Date, Exception> dateRecordedUnsafeSupplier) {

		_dateRecordedSupplier = () -> {
			try {
				return dateRecordedUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(
		description = "Day the interest scores were last calculated. Every interest in a response shares it, since only the latest calculation is returned."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date dateRecorded;

	@JsonIgnore
	private Supplier<Date> _dateRecordedSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "ID of the individual who has the interest. Use it as `individualId` in the individual tools."
	)
	public String getIndividualId() {
		if (_individualIdSupplier != null) {
			individualId = _individualIdSupplier.get();

			_individualIdSupplier = null;
		}

		return individualId;
	}

	public void setIndividualId(String individualId) {
		this.individualId = individualId;

		_individualIdSupplier = null;
	}

	@JsonIgnore
	public void setIndividualId(
		UnsafeSupplier<String, Exception> individualIdUnsafeSupplier) {

		_individualIdSupplier = () -> {
			try {
				return individualIdUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(
		description = "ID of the individual who has the interest. Use it as `individualId` in the individual tools."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String individualId;

	@JsonIgnore
	private Supplier<String> _individualIdSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "Name of the interest: a keyword of the pages the individual viewed."
	)
	public String getName() {
		if (_nameSupplier != null) {
			name = _nameSupplier.get();

			_nameSupplier = null;
		}

		return name;
	}

	public void setName(String name) {
		this.name = name;

		_nameSupplier = null;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		_nameSupplier = () -> {
			try {
				return nameUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(
		description = "Name of the interest: a keyword of the pages the individual viewed."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String name;

	@JsonIgnore
	private Supplier<String> _nameSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "Number of distinct pages with this keyword that the individual viewed."
	)
	public Long getRelatedPagesCount() {
		if (_relatedPagesCountSupplier != null) {
			relatedPagesCount = _relatedPagesCountSupplier.get();

			_relatedPagesCountSupplier = null;
		}

		return relatedPagesCount;
	}

	public void setRelatedPagesCount(Long relatedPagesCount) {
		this.relatedPagesCount = relatedPagesCount;

		_relatedPagesCountSupplier = null;
	}

	@JsonIgnore
	public void setRelatedPagesCount(
		UnsafeSupplier<Long, Exception> relatedPagesCountUnsafeSupplier) {

		_relatedPagesCountSupplier = () -> {
			try {
				return relatedPagesCountUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(
		description = "Number of distinct pages with this keyword that the individual viewed."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long relatedPagesCount;

	@JsonIgnore
	private Supplier<Long> _relatedPagesCountSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "Interest score of the individual in this topic. Higher means a stronger interest."
	)
	public Double getScore() {
		if (_scoreSupplier != null) {
			score = _scoreSupplier.get();

			_scoreSupplier = null;
		}

		return score;
	}

	public void setScore(Double score) {
		this.score = score;

		_scoreSupplier = null;
	}

	@JsonIgnore
	public void setScore(
		UnsafeSupplier<Double, Exception> scoreUnsafeSupplier) {

		_scoreSupplier = () -> {
			try {
				return scoreUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(
		description = "Interest score of the individual in this topic. Higher means a stronger interest."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Double score;

	@JsonIgnore
	private Supplier<Double> _scoreSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Interest)) {
			return false;
		}

		Interest interest = (Interest)object;

		return Objects.equals(toString(), interest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		Date dateRecorded = getDateRecorded();

		if (dateRecorded != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateRecorded\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(dateRecorded));

			sb.append("\"");
		}

		String individualId = getIndividualId();

		if (individualId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"individualId\": ");

			sb.append("\"");

			sb.append(_escape(individualId));

			sb.append("\"");
		}

		String name = getName();

		if (name != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(name));

			sb.append("\"");
		}

		Long relatedPagesCount = getRelatedPagesCount();

		if (relatedPagesCount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"relatedPagesCount\": ");

			sb.append(relatedPagesCount);
		}

		Double score = getScore();

		if (score != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"score\": ");

			sb.append(score);
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.osb.faro.rest.dto.v1_0.Interest",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof Map) {
						sb.append(_toJSON((Map<String, ?>)valueArray[i]));
					}
					else if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value instanceof Collection) {
			return String.valueOf(
				JSONFactoryUtil.createJSONArray((Collection<?>)value));
		}
		else if (value instanceof Map) {
			return String.valueOf(
				JSONFactoryUtil.createJSONObject((Map<?, ?>)value));
		}
		else if (value instanceof Object[]) {
			return String.valueOf(
				JSONFactoryUtil.createJSONArray(
					Arrays.asList((Object[])value)));
		}
		else if (value instanceof String) {
			return StringBundler.concat("\"", _escape(value), "\"");
		}

		return String.valueOf(value);
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

	private Map<String, Serializable> _extendedProperties;

}
// LIFERAY-REST-BUILDER-HASH:-721554903