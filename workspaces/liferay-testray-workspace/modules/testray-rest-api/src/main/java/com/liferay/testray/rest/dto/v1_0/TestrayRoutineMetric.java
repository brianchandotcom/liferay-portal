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
@GraphQLName("TestrayRoutineMetric")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "TestrayRoutineMetric")
public class TestrayRoutineMetric implements Serializable {

	public static TestrayRoutineMetric toDTO(String json) {
		return ObjectMapperUtil.readValue(TestrayRoutineMetric.class, json);
	}

	public static TestrayRoutineMetric unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			TestrayRoutineMetric.class, json);
	}

	@Schema
	public String getTestrayBuildDueDate() {
		if (_testrayBuildDueDateSupplier != null) {
			testrayBuildDueDate = _testrayBuildDueDateSupplier.get();

			_testrayBuildDueDateSupplier = null;
		}

		return testrayBuildDueDate;
	}

	public void setTestrayBuildDueDate(String testrayBuildDueDate) {
		this.testrayBuildDueDate = testrayBuildDueDate;

		_testrayBuildDueDateSupplier = null;
	}

	@JsonIgnore
	public void setTestrayBuildDueDate(
		UnsafeSupplier<String, Exception> testrayBuildDueDateUnsafeSupplier) {

		_testrayBuildDueDateSupplier = () -> {
			try {
				return testrayBuildDueDateUnsafeSupplier.get();
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
	protected String testrayBuildDueDate;

	@JsonIgnore
	private Supplier<String> _testrayBuildDueDateSupplier;

	@Schema
	public Long getTestrayRoutineId() {
		if (_testrayRoutineIdSupplier != null) {
			testrayRoutineId = _testrayRoutineIdSupplier.get();

			_testrayRoutineIdSupplier = null;
		}

		return testrayRoutineId;
	}

	public void setTestrayRoutineId(Long testrayRoutineId) {
		this.testrayRoutineId = testrayRoutineId;

		_testrayRoutineIdSupplier = null;
	}

	@JsonIgnore
	public void setTestrayRoutineId(
		UnsafeSupplier<Long, Exception> testrayRoutineIdUnsafeSupplier) {

		_testrayRoutineIdSupplier = () -> {
			try {
				return testrayRoutineIdUnsafeSupplier.get();
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
	protected Long testrayRoutineId;

	@JsonIgnore
	private Supplier<Long> _testrayRoutineIdSupplier;

	@Schema
	public String getTestrayRoutineName() {
		if (_testrayRoutineNameSupplier != null) {
			testrayRoutineName = _testrayRoutineNameSupplier.get();

			_testrayRoutineNameSupplier = null;
		}

		return testrayRoutineName;
	}

	public void setTestrayRoutineName(String testrayRoutineName) {
		this.testrayRoutineName = testrayRoutineName;

		_testrayRoutineNameSupplier = null;
	}

	@JsonIgnore
	public void setTestrayRoutineName(
		UnsafeSupplier<String, Exception> testrayRoutineNameUnsafeSupplier) {

		_testrayRoutineNameSupplier = () -> {
			try {
				return testrayRoutineNameUnsafeSupplier.get();
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
	protected String testrayRoutineName;

	@JsonIgnore
	private Supplier<String> _testrayRoutineNameSupplier;

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

		if (!(object instanceof TestrayRoutineMetric)) {
			return false;
		}

		TestrayRoutineMetric testrayRoutineMetric =
			(TestrayRoutineMetric)object;

		return Objects.equals(toString(), testrayRoutineMetric.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		String testrayBuildDueDate = getTestrayBuildDueDate();

		if (testrayBuildDueDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayBuildDueDate\": ");

			sb.append("\"");

			sb.append(_escape(testrayBuildDueDate));

			sb.append("\"");
		}

		Long testrayRoutineId = getTestrayRoutineId();

		if (testrayRoutineId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayRoutineId\": ");

			sb.append(testrayRoutineId);
		}

		String testrayRoutineName = getTestrayRoutineName();

		if (testrayRoutineName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayRoutineName\": ");

			sb.append("\"");

			sb.append(_escape(testrayRoutineName));

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
		defaultValue = "com.liferay.testray.rest.dto.v1_0.TestrayRoutineMetric",
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