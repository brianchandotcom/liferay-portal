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
@GraphQLName("TestrayTestSuite")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "TestrayTestSuite")
public class TestrayTestSuite implements Serializable {

	public static TestrayTestSuite toDTO(String json) {
		return ObjectMapperUtil.readValue(TestrayTestSuite.class, json);
	}

	public static TestrayTestSuite unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(TestrayTestSuite.class, json);
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
	public Long getRuntime() {
		if (_runtimeSupplier != null) {
			runtime = _runtimeSupplier.get();

			_runtimeSupplier = null;
		}

		return runtime;
	}

	public void setRuntime(Long runtime) {
		this.runtime = runtime;

		_runtimeSupplier = null;
	}

	@JsonIgnore
	public void setRuntime(
		UnsafeSupplier<Long, Exception> runtimeUnsafeSupplier) {

		_runtimeSupplier = () -> {
			try {
				return runtimeUnsafeSupplier.get();
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
	protected Long runtime;

	@JsonIgnore
	private Supplier<Long> _runtimeSupplier;

	@Schema
	public String getTarFileName() {
		if (_tarFileNameSupplier != null) {
			tarFileName = _tarFileNameSupplier.get();

			_tarFileNameSupplier = null;
		}

		return tarFileName;
	}

	public void setTarFileName(String tarFileName) {
		this.tarFileName = tarFileName;

		_tarFileNameSupplier = null;
	}

	@JsonIgnore
	public void setTarFileName(
		UnsafeSupplier<String, Exception> tarFileNameUnsafeSupplier) {

		_tarFileNameSupplier = () -> {
			try {
				return tarFileNameUnsafeSupplier.get();
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
	protected String tarFileName;

	@JsonIgnore
	private Supplier<String> _tarFileNameSupplier;

	@Schema
	public String getXmlFileName() {
		if (_xmlFileNameSupplier != null) {
			xmlFileName = _xmlFileNameSupplier.get();

			_xmlFileNameSupplier = null;
		}

		return xmlFileName;
	}

	public void setXmlFileName(String xmlFileName) {
		this.xmlFileName = xmlFileName;

		_xmlFileNameSupplier = null;
	}

	@JsonIgnore
	public void setXmlFileName(
		UnsafeSupplier<String, Exception> xmlFileNameUnsafeSupplier) {

		_xmlFileNameSupplier = () -> {
			try {
				return xmlFileNameUnsafeSupplier.get();
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
	protected String xmlFileName;

	@JsonIgnore
	private Supplier<String> _xmlFileNameSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TestrayTestSuite)) {
			return false;
		}

		TestrayTestSuite testrayTestSuite = (TestrayTestSuite)object;

		return Objects.equals(toString(), testrayTestSuite.toString());
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

		Long runtime = getRuntime();

		if (runtime != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"runtime\": ");

			sb.append(runtime);
		}

		String tarFileName = getTarFileName();

		if (tarFileName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"tarFileName\": ");

			sb.append("\"");

			sb.append(_escape(tarFileName));

			sb.append("\"");
		}

		String xmlFileName = getXmlFileName();

		if (xmlFileName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"xmlFileName\": ");

			sb.append("\"");

			sb.append(_escape(xmlFileName));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.testray.rest.dto.v1_0.TestrayTestSuite",
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