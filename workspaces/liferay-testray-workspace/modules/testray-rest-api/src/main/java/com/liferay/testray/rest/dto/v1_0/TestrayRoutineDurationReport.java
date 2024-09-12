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

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Nilton Vieira
 * @generated
 */
@Generated("")
@GraphQLName("TestrayRoutineDurationReport")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "TestrayRoutineDurationReport")
public class TestrayRoutineDurationReport implements Serializable {

	public static TestrayRoutineDurationReport toDTO(String json) {
		return ObjectMapperUtil.readValue(
			TestrayRoutineDurationReport.class, json);
	}

	public static TestrayRoutineDurationReport unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			TestrayRoutineDurationReport.class, json);
	}

	@Schema
	public Boolean getTestrayCaseFlaky() {
		if (_testrayCaseFlakySupplier != null) {
			testrayCaseFlaky = _testrayCaseFlakySupplier.get();

			_testrayCaseFlakySupplier = null;
		}

		return testrayCaseFlaky;
	}

	public void setTestrayCaseFlaky(Boolean testrayCaseFlaky) {
		this.testrayCaseFlaky = testrayCaseFlaky;

		_testrayCaseFlakySupplier = null;
	}

	@JsonIgnore
	public void setTestrayCaseFlaky(
		UnsafeSupplier<Boolean, Exception> testrayCaseFlakyUnsafeSupplier) {

		_testrayCaseFlakySupplier = () -> {
			try {
				return testrayCaseFlakyUnsafeSupplier.get();
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
	protected Boolean testrayCaseFlaky;

	@JsonIgnore
	private Supplier<Boolean> _testrayCaseFlakySupplier;

	@Schema
	public Long getTestrayCaseId() {
		if (_testrayCaseIdSupplier != null) {
			testrayCaseId = _testrayCaseIdSupplier.get();

			_testrayCaseIdSupplier = null;
		}

		return testrayCaseId;
	}

	public void setTestrayCaseId(Long testrayCaseId) {
		this.testrayCaseId = testrayCaseId;

		_testrayCaseIdSupplier = null;
	}

	@JsonIgnore
	public void setTestrayCaseId(
		UnsafeSupplier<Long, Exception> testrayCaseIdUnsafeSupplier) {

		_testrayCaseIdSupplier = () -> {
			try {
				return testrayCaseIdUnsafeSupplier.get();
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
	protected Long testrayCaseId;

	@JsonIgnore
	private Supplier<Long> _testrayCaseIdSupplier;

	@Schema
	public String getTestrayCaseName() {
		if (_testrayCaseNameSupplier != null) {
			testrayCaseName = _testrayCaseNameSupplier.get();

			_testrayCaseNameSupplier = null;
		}

		return testrayCaseName;
	}

	public void setTestrayCaseName(String testrayCaseName) {
		this.testrayCaseName = testrayCaseName;

		_testrayCaseNameSupplier = null;
	}

	@JsonIgnore
	public void setTestrayCaseName(
		UnsafeSupplier<String, Exception> testrayCaseNameUnsafeSupplier) {

		_testrayCaseNameSupplier = () -> {
			try {
				return testrayCaseNameUnsafeSupplier.get();
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
	protected String testrayCaseName;

	@JsonIgnore
	private Supplier<String> _testrayCaseNameSupplier;

	@Schema
	public Integer getTestrayCasePriority() {
		if (_testrayCasePrioritySupplier != null) {
			testrayCasePriority = _testrayCasePrioritySupplier.get();

			_testrayCasePrioritySupplier = null;
		}

		return testrayCasePriority;
	}

	public void setTestrayCasePriority(Integer testrayCasePriority) {
		this.testrayCasePriority = testrayCasePriority;

		_testrayCasePrioritySupplier = null;
	}

	@JsonIgnore
	public void setTestrayCasePriority(
		UnsafeSupplier<Integer, Exception> testrayCasePriorityUnsafeSupplier) {

		_testrayCasePrioritySupplier = () -> {
			try {
				return testrayCasePriorityUnsafeSupplier.get();
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
	protected Integer testrayCasePriority;

	@JsonIgnore
	private Supplier<Integer> _testrayCasePrioritySupplier;

	@Schema
	public Long getTestrayCaseResultAvgDuration() {
		if (_testrayCaseResultAvgDurationSupplier != null) {
			testrayCaseResultAvgDuration =
				_testrayCaseResultAvgDurationSupplier.get();

			_testrayCaseResultAvgDurationSupplier = null;
		}

		return testrayCaseResultAvgDuration;
	}

	public void setTestrayCaseResultAvgDuration(
		Long testrayCaseResultAvgDuration) {

		this.testrayCaseResultAvgDuration = testrayCaseResultAvgDuration;

		_testrayCaseResultAvgDurationSupplier = null;
	}

	@JsonIgnore
	public void setTestrayCaseResultAvgDuration(
		UnsafeSupplier<Long, Exception>
			testrayCaseResultAvgDurationUnsafeSupplier) {

		_testrayCaseResultAvgDurationSupplier = () -> {
			try {
				return testrayCaseResultAvgDurationUnsafeSupplier.get();
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
	protected Long testrayCaseResultAvgDuration;

	@JsonIgnore
	private Supplier<Long> _testrayCaseResultAvgDurationSupplier;

	@Schema
	public Long[] getTestrayCaseResultDurations() {
		if (_testrayCaseResultDurationsSupplier != null) {
			testrayCaseResultDurations =
				_testrayCaseResultDurationsSupplier.get();

			_testrayCaseResultDurationsSupplier = null;
		}

		return testrayCaseResultDurations;
	}

	public void setTestrayCaseResultDurations(
		Long[] testrayCaseResultDurations) {

		this.testrayCaseResultDurations = testrayCaseResultDurations;

		_testrayCaseResultDurationsSupplier = null;
	}

	@JsonIgnore
	public void setTestrayCaseResultDurations(
		UnsafeSupplier<Long[], Exception>
			testrayCaseResultDurationsUnsafeSupplier) {

		_testrayCaseResultDurationsSupplier = () -> {
			try {
				return testrayCaseResultDurationsUnsafeSupplier.get();
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
	protected Long[] testrayCaseResultDurations;

	@JsonIgnore
	private Supplier<Long[]> _testrayCaseResultDurationsSupplier;

	@Schema
	public String[] getTestrayCaseResultStatus() {
		if (_testrayCaseResultStatusSupplier != null) {
			testrayCaseResultStatus = _testrayCaseResultStatusSupplier.get();

			_testrayCaseResultStatusSupplier = null;
		}

		return testrayCaseResultStatus;
	}

	public void setTestrayCaseResultStatus(String[] testrayCaseResultStatus) {
		this.testrayCaseResultStatus = testrayCaseResultStatus;

		_testrayCaseResultStatusSupplier = null;
	}

	@JsonIgnore
	public void setTestrayCaseResultStatus(
		UnsafeSupplier<String[], Exception>
			testrayCaseResultStatusUnsafeSupplier) {

		_testrayCaseResultStatusSupplier = () -> {
			try {
				return testrayCaseResultStatusUnsafeSupplier.get();
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
	protected String[] testrayCaseResultStatus;

	@JsonIgnore
	private Supplier<String[]> _testrayCaseResultStatusSupplier;

	@Schema
	public String getTestrayCaseTypeName() {
		if (_testrayCaseTypeNameSupplier != null) {
			testrayCaseTypeName = _testrayCaseTypeNameSupplier.get();

			_testrayCaseTypeNameSupplier = null;
		}

		return testrayCaseTypeName;
	}

	public void setTestrayCaseTypeName(String testrayCaseTypeName) {
		this.testrayCaseTypeName = testrayCaseTypeName;

		_testrayCaseTypeNameSupplier = null;
	}

	@JsonIgnore
	public void setTestrayCaseTypeName(
		UnsafeSupplier<String, Exception> testrayCaseTypeNameUnsafeSupplier) {

		_testrayCaseTypeNameSupplier = () -> {
			try {
				return testrayCaseTypeNameUnsafeSupplier.get();
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
	protected String testrayCaseTypeName;

	@JsonIgnore
	private Supplier<String> _testrayCaseTypeNameSupplier;

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
	public String getTestrayTeamName() {
		if (_testrayTeamNameSupplier != null) {
			testrayTeamName = _testrayTeamNameSupplier.get();

			_testrayTeamNameSupplier = null;
		}

		return testrayTeamName;
	}

	public void setTestrayTeamName(String testrayTeamName) {
		this.testrayTeamName = testrayTeamName;

		_testrayTeamNameSupplier = null;
	}

	@JsonIgnore
	public void setTestrayTeamName(
		UnsafeSupplier<String, Exception> testrayTeamNameUnsafeSupplier) {

		_testrayTeamNameSupplier = () -> {
			try {
				return testrayTeamNameUnsafeSupplier.get();
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
	protected String testrayTeamName;

	@JsonIgnore
	private Supplier<String> _testrayTeamNameSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TestrayRoutineDurationReport)) {
			return false;
		}

		TestrayRoutineDurationReport testrayRoutineDurationReport =
			(TestrayRoutineDurationReport)object;

		return Objects.equals(
			toString(), testrayRoutineDurationReport.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Boolean testrayCaseFlaky = getTestrayCaseFlaky();

		if (testrayCaseFlaky != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayCaseFlaky\": ");

			sb.append(testrayCaseFlaky);
		}

		Long testrayCaseId = getTestrayCaseId();

		if (testrayCaseId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayCaseId\": ");

			sb.append(testrayCaseId);
		}

		String testrayCaseName = getTestrayCaseName();

		if (testrayCaseName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayCaseName\": ");

			sb.append("\"");

			sb.append(_escape(testrayCaseName));

			sb.append("\"");
		}

		Integer testrayCasePriority = getTestrayCasePriority();

		if (testrayCasePriority != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayCasePriority\": ");

			sb.append(testrayCasePriority);
		}

		Long testrayCaseResultAvgDuration = getTestrayCaseResultAvgDuration();

		if (testrayCaseResultAvgDuration != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayCaseResultAvgDuration\": ");

			sb.append(testrayCaseResultAvgDuration);
		}

		Long[] testrayCaseResultDurations = getTestrayCaseResultDurations();

		if (testrayCaseResultDurations != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayCaseResultDurations\": ");

			sb.append("[");

			for (int i = 0; i < testrayCaseResultDurations.length; i++) {
				sb.append(testrayCaseResultDurations[i]);

				if ((i + 1) < testrayCaseResultDurations.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		String[] testrayCaseResultStatus = getTestrayCaseResultStatus();

		if (testrayCaseResultStatus != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayCaseResultStatus\": ");

			sb.append("[");

			for (int i = 0; i < testrayCaseResultStatus.length; i++) {
				sb.append("\"");

				sb.append(_escape(testrayCaseResultStatus[i]));

				sb.append("\"");

				if ((i + 1) < testrayCaseResultStatus.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		String testrayCaseTypeName = getTestrayCaseTypeName();

		if (testrayCaseTypeName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayCaseTypeName\": ");

			sb.append("\"");

			sb.append(_escape(testrayCaseTypeName));

			sb.append("\"");
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

		String testrayTeamName = getTestrayTeamName();

		if (testrayTeamName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayTeamName\": ");

			sb.append("\"");

			sb.append(_escape(testrayTeamName));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.testray.rest.dto.v1_0.TestrayRoutineDurationReport",
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