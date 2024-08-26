package com.liferay.testray.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import javax.annotation.Generated;

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Nilton Vieira
 * @generated
 */
@Generated("")
@GraphQLName("TestrayRunComparison")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "TestrayRunComparison")
public class TestrayRunComparison implements Serializable {

	public static TestrayRunComparison toDTO(String json) {
		return ObjectMapperUtil.readValue(TestrayRunComparison.class, json);
	}

	public static TestrayRunComparison unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			TestrayRunComparison.class, json);
	}

	@Schema
	@Valid
	public Object[] getResults() {
		if (_resultsSupplier != null) {
			results = _resultsSupplier.get();

			_resultsSupplier = null;
		}

		return results;
	}

	public void setResults(Object[] results) {
		this.results = results;

		_resultsSupplier = null;
	}

	@JsonIgnore
	public void setResults(
		UnsafeSupplier<Object[], Exception> resultsUnsafeSupplier) {

		_resultsSupplier = () -> {
			try {
				return resultsUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Object[] results;

	@JsonIgnore
	private Supplier<Object[]> _resultsSupplier;

	@Schema
	@Valid
	public TestrayCaseResultComparison[] getTestrayCaseResultComparisons() {
		if (_testrayCaseResultComparisonsSupplier != null) {
			testrayCaseResultComparisons =
				_testrayCaseResultComparisonsSupplier.get();

			_testrayCaseResultComparisonsSupplier = null;
		}

		return testrayCaseResultComparisons;
	}

	public void setTestrayCaseResultComparisons(
		TestrayCaseResultComparison[] testrayCaseResultComparisons) {

		this.testrayCaseResultComparisons = testrayCaseResultComparisons;

		_testrayCaseResultComparisonsSupplier = null;
	}

	@JsonIgnore
	public void setTestrayCaseResultComparisons(
		UnsafeSupplier<TestrayCaseResultComparison[], Exception>
			testrayCaseResultComparisonsUnsafeSupplier) {

		_testrayCaseResultComparisonsSupplier = () -> {
			try {
				return testrayCaseResultComparisonsUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected TestrayCaseResultComparison[] testrayCaseResultComparisons;

	@JsonIgnore
	private Supplier<TestrayCaseResultComparison[]>
		_testrayCaseResultComparisonsSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TestrayRunComparison)) {
			return false;
		}

		TestrayRunComparison testrayRunComparison =
			(TestrayRunComparison)object;

		return Objects.equals(toString(), testrayRunComparison.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Object[] results = getResults();

		if (results != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"results\": ");

			sb.append("[");

			for (int i = 0; i < results.length; i++) {
				sb.append("\"");

				sb.append(_escape(results[i]));

				sb.append("\"");

				if ((i + 1) < results.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		TestrayCaseResultComparison[] testrayCaseResultComparisons =
			getTestrayCaseResultComparisons();

		if (testrayCaseResultComparisons != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayCaseResultComparisons\": ");

			sb.append("[");

			for (int i = 0; i < testrayCaseResultComparisons.length; i++) {
				sb.append(String.valueOf(testrayCaseResultComparisons[i]));

				if ((i + 1) < testrayCaseResultComparisons.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.testray.rest.dto.v1_0.TestrayRunComparison",
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

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

	private Map<String, Serializable> _extendedProperties;

}