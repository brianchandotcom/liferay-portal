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
@GraphQLName("TestrayBuildAutofill")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "TestrayBuildAutofill")
public class TestrayBuildAutofill implements Serializable {

	public static TestrayBuildAutofill toDTO(String json) {
		return ObjectMapperUtil.readValue(TestrayBuildAutofill.class, json);
	}

	public static TestrayBuildAutofill unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			TestrayBuildAutofill.class, json);
	}

	@Schema
	public Integer getCaseAmount() {
		if (_caseAmountSupplier != null) {
			caseAmount = _caseAmountSupplier.get();

			_caseAmountSupplier = null;
		}

		return caseAmount;
	}

	public void setCaseAmount(Integer caseAmount) {
		this.caseAmount = caseAmount;

		_caseAmountSupplier = null;
	}

	@JsonIgnore
	public void setCaseAmount(
		UnsafeSupplier<Integer, Exception> caseAmountUnsafeSupplier) {

		_caseAmountSupplier = () -> {
			try {
				return caseAmountUnsafeSupplier.get();
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
	protected Integer caseAmount;

	@JsonIgnore
	private Supplier<Integer> _caseAmountSupplier;

	@Schema
	public Long getRunId1() {
		if (_runId1Supplier != null) {
			runId1 = _runId1Supplier.get();

			_runId1Supplier = null;
		}

		return runId1;
	}

	public void setRunId1(Long runId1) {
		this.runId1 = runId1;

		_runId1Supplier = null;
	}

	@JsonIgnore
	public void setRunId1(
		UnsafeSupplier<Long, Exception> runId1UnsafeSupplier) {

		_runId1Supplier = () -> {
			try {
				return runId1UnsafeSupplier.get();
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
	protected Long runId1;

	@JsonIgnore
	private Supplier<Long> _runId1Supplier;

	@Schema
	public Long getRunId2() {
		if (_runId2Supplier != null) {
			runId2 = _runId2Supplier.get();

			_runId2Supplier = null;
		}

		return runId2;
	}

	public void setRunId2(Long runId2) {
		this.runId2 = runId2;

		_runId2Supplier = null;
	}

	@JsonIgnore
	public void setRunId2(
		UnsafeSupplier<Long, Exception> runId2UnsafeSupplier) {

		_runId2Supplier = () -> {
			try {
				return runId2UnsafeSupplier.get();
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
	protected Long runId2;

	@JsonIgnore
	private Supplier<Long> _runId2Supplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TestrayBuildAutofill)) {
			return false;
		}

		TestrayBuildAutofill testrayBuildAutofill =
			(TestrayBuildAutofill)object;

		return Objects.equals(toString(), testrayBuildAutofill.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Integer caseAmount = getCaseAmount();

		if (caseAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"caseAmount\": ");

			sb.append(caseAmount);
		}

		Long runId1 = getRunId1();

		if (runId1 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"runId1\": ");

			sb.append(runId1);
		}

		Long runId2 = getRunId2();

		if (runId2 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"runId2\": ");

			sb.append(runId2);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.testray.rest.dto.v1_0.TestrayBuildAutofill",
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
					if (valueArray[i] instanceof String) {
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