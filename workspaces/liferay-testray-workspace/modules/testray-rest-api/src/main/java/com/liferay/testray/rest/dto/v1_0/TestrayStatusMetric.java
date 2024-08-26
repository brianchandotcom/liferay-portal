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
@GraphQLName("TestrayStatusMetric")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "TestrayStatusMetric")
public class TestrayStatusMetric implements Serializable {

	public static TestrayStatusMetric toDTO(String json) {
		return ObjectMapperUtil.readValue(TestrayStatusMetric.class, json);
	}

	public static TestrayStatusMetric unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			TestrayStatusMetric.class, json);
	}

	@Schema
	public Long getBlocked() {
		if (_blockedSupplier != null) {
			blocked = _blockedSupplier.get();

			_blockedSupplier = null;
		}

		return blocked;
	}

	public void setBlocked(Long blocked) {
		this.blocked = blocked;

		_blockedSupplier = null;
	}

	@JsonIgnore
	public void setBlocked(
		UnsafeSupplier<Long, Exception> blockedUnsafeSupplier) {

		_blockedSupplier = () -> {
			try {
				return blockedUnsafeSupplier.get();
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
	protected Long blocked;

	@JsonIgnore
	private Supplier<Long> _blockedSupplier;

	@Schema
	public Long getFailed() {
		if (_failedSupplier != null) {
			failed = _failedSupplier.get();

			_failedSupplier = null;
		}

		return failed;
	}

	public void setFailed(Long failed) {
		this.failed = failed;

		_failedSupplier = null;
	}

	@JsonIgnore
	public void setFailed(
		UnsafeSupplier<Long, Exception> failedUnsafeSupplier) {

		_failedSupplier = () -> {
			try {
				return failedUnsafeSupplier.get();
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
	protected Long failed;

	@JsonIgnore
	private Supplier<Long> _failedSupplier;

	@Schema
	public Long getInProgress() {
		if (_inProgressSupplier != null) {
			inProgress = _inProgressSupplier.get();

			_inProgressSupplier = null;
		}

		return inProgress;
	}

	public void setInProgress(Long inProgress) {
		this.inProgress = inProgress;

		_inProgressSupplier = null;
	}

	@JsonIgnore
	public void setInProgress(
		UnsafeSupplier<Long, Exception> inProgressUnsafeSupplier) {

		_inProgressSupplier = () -> {
			try {
				return inProgressUnsafeSupplier.get();
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
	protected Long inProgress;

	@JsonIgnore
	private Supplier<Long> _inProgressSupplier;

	@Schema
	public Long getPassed() {
		if (_passedSupplier != null) {
			passed = _passedSupplier.get();

			_passedSupplier = null;
		}

		return passed;
	}

	public void setPassed(Long passed) {
		this.passed = passed;

		_passedSupplier = null;
	}

	@JsonIgnore
	public void setPassed(
		UnsafeSupplier<Long, Exception> passedUnsafeSupplier) {

		_passedSupplier = () -> {
			try {
				return passedUnsafeSupplier.get();
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
	protected Long passed;

	@JsonIgnore
	private Supplier<Long> _passedSupplier;

	@Schema
	public Long getTestfix() {
		if (_testfixSupplier != null) {
			testfix = _testfixSupplier.get();

			_testfixSupplier = null;
		}

		return testfix;
	}

	public void setTestfix(Long testfix) {
		this.testfix = testfix;

		_testfixSupplier = null;
	}

	@JsonIgnore
	public void setTestfix(
		UnsafeSupplier<Long, Exception> testfixUnsafeSupplier) {

		_testfixSupplier = () -> {
			try {
				return testfixUnsafeSupplier.get();
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
	protected Long testfix;

	@JsonIgnore
	private Supplier<Long> _testfixSupplier;

	@Schema
	public Long getTotal() {
		if (_totalSupplier != null) {
			total = _totalSupplier.get();

			_totalSupplier = null;
		}

		return total;
	}

	public void setTotal(Long total) {
		this.total = total;

		_totalSupplier = null;
	}

	@JsonIgnore
	public void setTotal(UnsafeSupplier<Long, Exception> totalUnsafeSupplier) {
		_totalSupplier = () -> {
			try {
				return totalUnsafeSupplier.get();
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
	protected Long total;

	@JsonIgnore
	private Supplier<Long> _totalSupplier;

	@Schema
	public Long getUntested() {
		if (_untestedSupplier != null) {
			untested = _untestedSupplier.get();

			_untestedSupplier = null;
		}

		return untested;
	}

	public void setUntested(Long untested) {
		this.untested = untested;

		_untestedSupplier = null;
	}

	@JsonIgnore
	public void setUntested(
		UnsafeSupplier<Long, Exception> untestedUnsafeSupplier) {

		_untestedSupplier = () -> {
			try {
				return untestedUnsafeSupplier.get();
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
	protected Long untested;

	@JsonIgnore
	private Supplier<Long> _untestedSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TestrayStatusMetric)) {
			return false;
		}

		TestrayStatusMetric testrayStatusMetric = (TestrayStatusMetric)object;

		return Objects.equals(toString(), testrayStatusMetric.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Long blocked = getBlocked();

		if (blocked != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"blocked\": ");

			sb.append(blocked);
		}

		Long failed = getFailed();

		if (failed != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"failed\": ");

			sb.append(failed);
		}

		Long inProgress = getInProgress();

		if (inProgress != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"inProgress\": ");

			sb.append(inProgress);
		}

		Long passed = getPassed();

		if (passed != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"passed\": ");

			sb.append(passed);
		}

		Long testfix = getTestfix();

		if (testfix != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testfix\": ");

			sb.append(testfix);
		}

		Long total = getTotal();

		if (total != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"total\": ");

			sb.append(total);
		}

		Long untested = getUntested();

		if (untested != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"untested\": ");

			sb.append(untested);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.testray.rest.dto.v1_0.TestrayStatusMetric",
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