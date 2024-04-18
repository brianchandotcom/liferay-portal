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
@GraphQLName("TestrayTestFlow")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "TestrayTestFlow")
public class TestrayTestFlow implements Serializable {

	public static TestrayTestFlow toDTO(String json) {
		return ObjectMapperUtil.readValue(TestrayTestFlow.class, json);
	}

	public static TestrayTestFlow unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(TestrayTestFlow.class, json);
	}

	@Schema
	public Integer getTestraySubtasksAmount() {
		if (_testraySubtasksAmountSupplier != null) {
			testraySubtasksAmount = _testraySubtasksAmountSupplier.get();

			_testraySubtasksAmountSupplier = null;
		}

		return testraySubtasksAmount;
	}

	public void setTestraySubtasksAmount(Integer testraySubtasksAmount) {
		this.testraySubtasksAmount = testraySubtasksAmount;

		_testraySubtasksAmountSupplier = null;
	}

	@JsonIgnore
	public void setTestraySubtasksAmount(
		UnsafeSupplier<Integer, Exception>
			testraySubtasksAmountUnsafeSupplier) {

		_testraySubtasksAmountSupplier = () -> {
			try {
				return testraySubtasksAmountUnsafeSupplier.get();
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
	protected Integer testraySubtasksAmount;

	@JsonIgnore
	private Supplier<Integer> _testraySubtasksAmountSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TestrayTestFlow)) {
			return false;
		}

		TestrayTestFlow testrayTestFlow = (TestrayTestFlow)object;

		return Objects.equals(toString(), testrayTestFlow.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Integer testraySubtasksAmount = getTestraySubtasksAmount();

		if (testraySubtasksAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testraySubtasksAmount\": ");

			sb.append(testraySubtasksAmount);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.testray.rest.dto.v1_0.TestrayTestFlow",
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