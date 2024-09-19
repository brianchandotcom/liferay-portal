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
@GraphQLName("TestrayBuildMetric")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "TestrayBuildMetric")
public class TestrayBuildMetric implements Serializable {

	public static TestrayBuildMetric toDTO(String json) {
		return ObjectMapperUtil.readValue(TestrayBuildMetric.class, json);
	}

	public static TestrayBuildMetric unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(TestrayBuildMetric.class, json);
	}

	@Schema
	public Boolean getTestrayBuildArchived() {
		if (_testrayBuildArchivedSupplier != null) {
			testrayBuildArchived = _testrayBuildArchivedSupplier.get();

			_testrayBuildArchivedSupplier = null;
		}

		return testrayBuildArchived;
	}

	public void setTestrayBuildArchived(Boolean testrayBuildArchived) {
		this.testrayBuildArchived = testrayBuildArchived;

		_testrayBuildArchivedSupplier = null;
	}

	@JsonIgnore
	public void setTestrayBuildArchived(
		UnsafeSupplier<Boolean, Exception> testrayBuildArchivedUnsafeSupplier) {

		_testrayBuildArchivedSupplier = () -> {
			try {
				return testrayBuildArchivedUnsafeSupplier.get();
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
	protected Boolean testrayBuildArchived;

	@JsonIgnore
	private Supplier<Boolean> _testrayBuildArchivedSupplier;

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
	public String getTestrayBuildGitHash() {
		if (_testrayBuildGitHashSupplier != null) {
			testrayBuildGitHash = _testrayBuildGitHashSupplier.get();

			_testrayBuildGitHashSupplier = null;
		}

		return testrayBuildGitHash;
	}

	public void setTestrayBuildGitHash(String testrayBuildGitHash) {
		this.testrayBuildGitHash = testrayBuildGitHash;

		_testrayBuildGitHashSupplier = null;
	}

	@JsonIgnore
	public void setTestrayBuildGitHash(
		UnsafeSupplier<String, Exception> testrayBuildGitHashUnsafeSupplier) {

		_testrayBuildGitHashSupplier = () -> {
			try {
				return testrayBuildGitHashUnsafeSupplier.get();
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
	protected String testrayBuildGitHash;

	@JsonIgnore
	private Supplier<String> _testrayBuildGitHashSupplier;

	@Schema
	public Long getTestrayBuildId() {
		if (_testrayBuildIdSupplier != null) {
			testrayBuildId = _testrayBuildIdSupplier.get();

			_testrayBuildIdSupplier = null;
		}

		return testrayBuildId;
	}

	public void setTestrayBuildId(Long testrayBuildId) {
		this.testrayBuildId = testrayBuildId;

		_testrayBuildIdSupplier = null;
	}

	@JsonIgnore
	public void setTestrayBuildId(
		UnsafeSupplier<Long, Exception> testrayBuildIdUnsafeSupplier) {

		_testrayBuildIdSupplier = () -> {
			try {
				return testrayBuildIdUnsafeSupplier.get();
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
	protected Long testrayBuildId;

	@JsonIgnore
	private Supplier<Long> _testrayBuildIdSupplier;

	@Schema
	public String getTestrayBuildImportStatus() {
		if (_testrayBuildImportStatusSupplier != null) {
			testrayBuildImportStatus = _testrayBuildImportStatusSupplier.get();

			_testrayBuildImportStatusSupplier = null;
		}

		return testrayBuildImportStatus;
	}

	public void setTestrayBuildImportStatus(String testrayBuildImportStatus) {
		this.testrayBuildImportStatus = testrayBuildImportStatus;

		_testrayBuildImportStatusSupplier = null;
	}

	@JsonIgnore
	public void setTestrayBuildImportStatus(
		UnsafeSupplier<String, Exception>
			testrayBuildImportStatusUnsafeSupplier) {

		_testrayBuildImportStatusSupplier = () -> {
			try {
				return testrayBuildImportStatusUnsafeSupplier.get();
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
	protected String testrayBuildImportStatus;

	@JsonIgnore
	private Supplier<String> _testrayBuildImportStatusSupplier;

	@Schema
	public String getTestrayBuildName() {
		if (_testrayBuildNameSupplier != null) {
			testrayBuildName = _testrayBuildNameSupplier.get();

			_testrayBuildNameSupplier = null;
		}

		return testrayBuildName;
	}

	public void setTestrayBuildName(String testrayBuildName) {
		this.testrayBuildName = testrayBuildName;

		_testrayBuildNameSupplier = null;
	}

	@JsonIgnore
	public void setTestrayBuildName(
		UnsafeSupplier<String, Exception> testrayBuildNameUnsafeSupplier) {

		_testrayBuildNameSupplier = () -> {
			try {
				return testrayBuildNameUnsafeSupplier.get();
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
	protected String testrayBuildName;

	@JsonIgnore
	private Supplier<String> _testrayBuildNameSupplier;

	@Schema
	public String getTestrayBuildProductVersion() {
		if (_testrayBuildProductVersionSupplier != null) {
			testrayBuildProductVersion =
				_testrayBuildProductVersionSupplier.get();

			_testrayBuildProductVersionSupplier = null;
		}

		return testrayBuildProductVersion;
	}

	public void setTestrayBuildProductVersion(
		String testrayBuildProductVersion) {

		this.testrayBuildProductVersion = testrayBuildProductVersion;

		_testrayBuildProductVersionSupplier = null;
	}

	@JsonIgnore
	public void setTestrayBuildProductVersion(
		UnsafeSupplier<String, Exception>
			testrayBuildProductVersionUnsafeSupplier) {

		_testrayBuildProductVersionSupplier = () -> {
			try {
				return testrayBuildProductVersionUnsafeSupplier.get();
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
	protected String testrayBuildProductVersion;

	@JsonIgnore
	private Supplier<String> _testrayBuildProductVersionSupplier;

	@Schema
	public Boolean getTestrayBuildPromoted() {
		if (_testrayBuildPromotedSupplier != null) {
			testrayBuildPromoted = _testrayBuildPromotedSupplier.get();

			_testrayBuildPromotedSupplier = null;
		}

		return testrayBuildPromoted;
	}

	public void setTestrayBuildPromoted(Boolean testrayBuildPromoted) {
		this.testrayBuildPromoted = testrayBuildPromoted;

		_testrayBuildPromotedSupplier = null;
	}

	@JsonIgnore
	public void setTestrayBuildPromoted(
		UnsafeSupplier<Boolean, Exception> testrayBuildPromotedUnsafeSupplier) {

		_testrayBuildPromotedSupplier = () -> {
			try {
				return testrayBuildPromotedUnsafeSupplier.get();
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
	protected Boolean testrayBuildPromoted;

	@JsonIgnore
	private Supplier<Boolean> _testrayBuildPromotedSupplier;

	@Schema
	public String getTestrayBuildTaskStatus() {
		if (_testrayBuildTaskStatusSupplier != null) {
			testrayBuildTaskStatus = _testrayBuildTaskStatusSupplier.get();

			_testrayBuildTaskStatusSupplier = null;
		}

		return testrayBuildTaskStatus;
	}

	public void setTestrayBuildTaskStatus(String testrayBuildTaskStatus) {
		this.testrayBuildTaskStatus = testrayBuildTaskStatus;

		_testrayBuildTaskStatusSupplier = null;
	}

	@JsonIgnore
	public void setTestrayBuildTaskStatus(
		UnsafeSupplier<String, Exception>
			testrayBuildTaskStatusUnsafeSupplier) {

		_testrayBuildTaskStatusSupplier = () -> {
			try {
				return testrayBuildTaskStatusUnsafeSupplier.get();
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
	protected String testrayBuildTaskStatus;

	@JsonIgnore
	private Supplier<String> _testrayBuildTaskStatusSupplier;

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

		if (!(object instanceof TestrayBuildMetric)) {
			return false;
		}

		TestrayBuildMetric testrayBuildMetric = (TestrayBuildMetric)object;

		return Objects.equals(toString(), testrayBuildMetric.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Boolean testrayBuildArchived = getTestrayBuildArchived();

		if (testrayBuildArchived != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayBuildArchived\": ");

			sb.append(testrayBuildArchived);
		}

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

		String testrayBuildGitHash = getTestrayBuildGitHash();

		if (testrayBuildGitHash != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayBuildGitHash\": ");

			sb.append("\"");

			sb.append(_escape(testrayBuildGitHash));

			sb.append("\"");
		}

		Long testrayBuildId = getTestrayBuildId();

		if (testrayBuildId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayBuildId\": ");

			sb.append(testrayBuildId);
		}

		String testrayBuildImportStatus = getTestrayBuildImportStatus();

		if (testrayBuildImportStatus != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayBuildImportStatus\": ");

			sb.append("\"");

			sb.append(_escape(testrayBuildImportStatus));

			sb.append("\"");
		}

		String testrayBuildName = getTestrayBuildName();

		if (testrayBuildName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayBuildName\": ");

			sb.append("\"");

			sb.append(_escape(testrayBuildName));

			sb.append("\"");
		}

		String testrayBuildProductVersion = getTestrayBuildProductVersion();

		if (testrayBuildProductVersion != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayBuildProductVersion\": ");

			sb.append("\"");

			sb.append(_escape(testrayBuildProductVersion));

			sb.append("\"");
		}

		Boolean testrayBuildPromoted = getTestrayBuildPromoted();

		if (testrayBuildPromoted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayBuildPromoted\": ");

			sb.append(testrayBuildPromoted);
		}

		String testrayBuildTaskStatus = getTestrayBuildTaskStatus();

		if (testrayBuildTaskStatus != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayBuildTaskStatus\": ");

			sb.append("\"");

			sb.append(_escape(testrayBuildTaskStatus));

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
		defaultValue = "com.liferay.testray.rest.dto.v1_0.TestrayBuildMetric",
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