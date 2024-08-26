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
@GraphQLName("TestrayRunMetric")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "TestrayRunMetric")
public class TestrayRunMetric implements Serializable {

	public static TestrayRunMetric toDTO(String json) {
		return ObjectMapperUtil.readValue(TestrayRunMetric.class, json);
	}

	public static TestrayRunMetric unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(TestrayRunMetric.class, json);
	}

	@Schema
	public Long getTestrayRunId() {
		if (_testrayRunIdSupplier != null) {
			testrayRunId = _testrayRunIdSupplier.get();

			_testrayRunIdSupplier = null;
		}

		return testrayRunId;
	}

	public void setTestrayRunId(Long testrayRunId) {
		this.testrayRunId = testrayRunId;

		_testrayRunIdSupplier = null;
	}

	@JsonIgnore
	public void setTestrayRunId(
		UnsafeSupplier<Long, Exception> testrayRunIdUnsafeSupplier) {

		_testrayRunIdSupplier = () -> {
			try {
				return testrayRunIdUnsafeSupplier.get();
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
	protected Long testrayRunId;

	@JsonIgnore
	private Supplier<Long> _testrayRunIdSupplier;

	@Schema
	public String getTestrayRunName() {
		if (_testrayRunNameSupplier != null) {
			testrayRunName = _testrayRunNameSupplier.get();

			_testrayRunNameSupplier = null;
		}

		return testrayRunName;
	}

	public void setTestrayRunName(String testrayRunName) {
		this.testrayRunName = testrayRunName;

		_testrayRunNameSupplier = null;
	}

	@JsonIgnore
	public void setTestrayRunName(
		UnsafeSupplier<String, Exception> testrayRunNameUnsafeSupplier) {

		_testrayRunNameSupplier = () -> {
			try {
				return testrayRunNameUnsafeSupplier.get();
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
	protected String testrayRunName;

	@JsonIgnore
	private Supplier<String> _testrayRunNameSupplier;

	@Schema
	public Long getTestrayRunNumber() {
		if (_testrayRunNumberSupplier != null) {
			testrayRunNumber = _testrayRunNumberSupplier.get();

			_testrayRunNumberSupplier = null;
		}

		return testrayRunNumber;
	}

	public void setTestrayRunNumber(Long testrayRunNumber) {
		this.testrayRunNumber = testrayRunNumber;

		_testrayRunNumberSupplier = null;
	}

	@JsonIgnore
	public void setTestrayRunNumber(
		UnsafeSupplier<Long, Exception> testrayRunNumberUnsafeSupplier) {

		_testrayRunNumberSupplier = () -> {
			try {
				return testrayRunNumberUnsafeSupplier.get();
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
	protected Long testrayRunNumber;

	@JsonIgnore
	private Supplier<Long> _testrayRunNumberSupplier;

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

		if (!(object instanceof TestrayRunMetric)) {
			return false;
		}

		TestrayRunMetric testrayRunMetric = (TestrayRunMetric)object;

		return Objects.equals(toString(), testrayRunMetric.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Long testrayRunId = getTestrayRunId();

		if (testrayRunId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayRunId\": ");

			sb.append(testrayRunId);
		}

		String testrayRunName = getTestrayRunName();

		if (testrayRunName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayRunName\": ");

			sb.append("\"");

			sb.append(_escape(testrayRunName));

			sb.append("\"");
		}

		Long testrayRunNumber = getTestrayRunNumber();

		if (testrayRunNumber != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayRunNumber\": ");

			sb.append(testrayRunNumber);
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
		defaultValue = "com.liferay.testray.rest.dto.v1_0.TestrayRunMetric",
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