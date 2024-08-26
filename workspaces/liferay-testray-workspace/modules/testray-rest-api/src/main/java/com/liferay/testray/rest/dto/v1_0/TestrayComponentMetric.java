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
@GraphQLName("TestrayComponentMetric")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "TestrayComponentMetric")
public class TestrayComponentMetric implements Serializable {

	public static TestrayComponentMetric toDTO(String json) {
		return ObjectMapperUtil.readValue(TestrayComponentMetric.class, json);
	}

	public static TestrayComponentMetric unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			TestrayComponentMetric.class, json);
	}

	@Schema
	public Long getTestrayComponentId() {
		if (_testrayComponentIdSupplier != null) {
			testrayComponentId = _testrayComponentIdSupplier.get();

			_testrayComponentIdSupplier = null;
		}

		return testrayComponentId;
	}

	public void setTestrayComponentId(Long testrayComponentId) {
		this.testrayComponentId = testrayComponentId;

		_testrayComponentIdSupplier = null;
	}

	@JsonIgnore
	public void setTestrayComponentId(
		UnsafeSupplier<Long, Exception> testrayComponentIdUnsafeSupplier) {

		_testrayComponentIdSupplier = () -> {
			try {
				return testrayComponentIdUnsafeSupplier.get();
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
	protected Long testrayComponentId;

	@JsonIgnore
	private Supplier<Long> _testrayComponentIdSupplier;

	@Schema
	public String getTestrayComponentName() {
		if (_testrayComponentNameSupplier != null) {
			testrayComponentName = _testrayComponentNameSupplier.get();

			_testrayComponentNameSupplier = null;
		}

		return testrayComponentName;
	}

	public void setTestrayComponentName(String testrayComponentName) {
		this.testrayComponentName = testrayComponentName;

		_testrayComponentNameSupplier = null;
	}

	@JsonIgnore
	public void setTestrayComponentName(
		UnsafeSupplier<String, Exception> testrayComponentNameUnsafeSupplier) {

		_testrayComponentNameSupplier = () -> {
			try {
				return testrayComponentNameUnsafeSupplier.get();
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
	protected String testrayComponentName;

	@JsonIgnore
	private Supplier<String> _testrayComponentNameSupplier;

	@Schema
	@Valid
	public TestrayStatusMetric getTestrayStatusMetric() {
		if (_testrayStatusMetricSupplier != null) {
			testrayStatusMetric = _testrayStatusMetricSupplier.get();

			_testrayStatusMetricSupplier = null;
		}

		return testrayStatusMetric;
	}

	public void setTestrayStatusMetric(
		TestrayStatusMetric testrayStatusMetric) {

		this.testrayStatusMetric = testrayStatusMetric;

		_testrayStatusMetricSupplier = null;
	}

	@JsonIgnore
	public void setTestrayStatusMetric(
		UnsafeSupplier<TestrayStatusMetric, Exception>
			testrayStatusMetricUnsafeSupplier) {

		_testrayStatusMetricSupplier = () -> {
			try {
				return testrayStatusMetricUnsafeSupplier.get();
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
	protected TestrayStatusMetric testrayStatusMetric;

	@JsonIgnore
	private Supplier<TestrayStatusMetric> _testrayStatusMetricSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TestrayComponentMetric)) {
			return false;
		}

		TestrayComponentMetric testrayComponentMetric =
			(TestrayComponentMetric)object;

		return Objects.equals(toString(), testrayComponentMetric.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Long testrayComponentId = getTestrayComponentId();

		if (testrayComponentId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayComponentId\": ");

			sb.append(testrayComponentId);
		}

		String testrayComponentName = getTestrayComponentName();

		if (testrayComponentName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayComponentName\": ");

			sb.append("\"");

			sb.append(_escape(testrayComponentName));

			sb.append("\"");
		}

		TestrayStatusMetric testrayStatusMetric = getTestrayStatusMetric();

		if (testrayStatusMetric != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayStatusMetric\": ");

			sb.append(String.valueOf(testrayStatusMetric));
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.testray.rest.dto.v1_0.TestrayComponentMetric",
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