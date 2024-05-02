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
@GraphQLName("TestrayCaseResultTestFlowUpdate")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "TestrayCaseResultTestFlowUpdate")
public class TestrayCaseResultTestFlowUpdate implements Serializable {

	public static TestrayCaseResultTestFlowUpdate toDTO(String json) {
		return ObjectMapperUtil.readValue(
			TestrayCaseResultTestFlowUpdate.class, json);
	}

	public static TestrayCaseResultTestFlowUpdate unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			TestrayCaseResultTestFlowUpdate.class, json);
	}

	@Schema
	public Integer getCaseResultAmount() {
		if (_caseResultAmountSupplier != null) {
			caseResultAmount = _caseResultAmountSupplier.get();

			_caseResultAmountSupplier = null;
		}

		return caseResultAmount;
	}

	public void setCaseResultAmount(Integer caseResultAmount) {
		this.caseResultAmount = caseResultAmount;

		_caseResultAmountSupplier = null;
	}

	@JsonIgnore
	public void setCaseResultAmount(
		UnsafeSupplier<Integer, Exception> caseResultAmountUnsafeSupplier) {

		_caseResultAmountSupplier = () -> {
			try {
				return caseResultAmountUnsafeSupplier.get();
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
	protected Integer caseResultAmount;

	@JsonIgnore
	private Supplier<Integer> _caseResultAmountSupplier;

	@Schema
	public String getComment() {
		if (_commentSupplier != null) {
			comment = _commentSupplier.get();

			_commentSupplier = null;
		}

		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;

		_commentSupplier = null;
	}

	@JsonIgnore
	public void setComment(
		UnsafeSupplier<String, Exception> commentUnsafeSupplier) {

		_commentSupplier = () -> {
			try {
				return commentUnsafeSupplier.get();
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
	protected String comment;

	@JsonIgnore
	private Supplier<String> _commentSupplier;

	@Schema
	public String getDueStatus() {
		if (_dueStatusSupplier != null) {
			dueStatus = _dueStatusSupplier.get();

			_dueStatusSupplier = null;
		}

		return dueStatus;
	}

	public void setDueStatus(String dueStatus) {
		this.dueStatus = dueStatus;

		_dueStatusSupplier = null;
	}

	@JsonIgnore
	public void setDueStatus(
		UnsafeSupplier<String, Exception> dueStatusUnsafeSupplier) {

		_dueStatusSupplier = () -> {
			try {
				return dueStatusUnsafeSupplier.get();
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
	protected String dueStatus;

	@JsonIgnore
	private Supplier<String> _dueStatusSupplier;

	@Schema
	public String getIssues() {
		if (_issuesSupplier != null) {
			issues = _issuesSupplier.get();

			_issuesSupplier = null;
		}

		return issues;
	}

	public void setIssues(String issues) {
		this.issues = issues;

		_issuesSupplier = null;
	}

	@JsonIgnore
	public void setIssues(
		UnsafeSupplier<String, Exception> issuesUnsafeSupplier) {

		_issuesSupplier = () -> {
			try {
				return issuesUnsafeSupplier.get();
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
	protected String issues;

	@JsonIgnore
	private Supplier<String> _issuesSupplier;

	@Schema
	public Long getMbMessageId() {
		if (_mbMessageIdSupplier != null) {
			mbMessageId = _mbMessageIdSupplier.get();

			_mbMessageIdSupplier = null;
		}

		return mbMessageId;
	}

	public void setMbMessageId(Long mbMessageId) {
		this.mbMessageId = mbMessageId;

		_mbMessageIdSupplier = null;
	}

	@JsonIgnore
	public void setMbMessageId(
		UnsafeSupplier<Long, Exception> mbMessageIdUnsafeSupplier) {

		_mbMessageIdSupplier = () -> {
			try {
				return mbMessageIdUnsafeSupplier.get();
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
	protected Long mbMessageId;

	@JsonIgnore
	private Supplier<Long> _mbMessageIdSupplier;

	@Schema
	public Long getMbThreadId() {
		if (_mbThreadIdSupplier != null) {
			mbThreadId = _mbThreadIdSupplier.get();

			_mbThreadIdSupplier = null;
		}

		return mbThreadId;
	}

	public void setMbThreadId(Long mbThreadId) {
		this.mbThreadId = mbThreadId;

		_mbThreadIdSupplier = null;
	}

	@JsonIgnore
	public void setMbThreadId(
		UnsafeSupplier<Long, Exception> mbThreadIdUnsafeSupplier) {

		_mbThreadIdSupplier = () -> {
			try {
				return mbThreadIdUnsafeSupplier.get();
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
	protected Long mbThreadId;

	@JsonIgnore
	private Supplier<Long> _mbThreadIdSupplier;

	@Schema
	public Long getUserId() {
		if (_userIdSupplier != null) {
			userId = _userIdSupplier.get();

			_userIdSupplier = null;
		}

		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;

		_userIdSupplier = null;
	}

	@JsonIgnore
	public void setUserId(
		UnsafeSupplier<Long, Exception> userIdUnsafeSupplier) {

		_userIdSupplier = () -> {
			try {
				return userIdUnsafeSupplier.get();
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
	protected Long userId;

	@JsonIgnore
	private Supplier<Long> _userIdSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TestrayCaseResultTestFlowUpdate)) {
			return false;
		}

		TestrayCaseResultTestFlowUpdate testrayCaseResultTestFlowUpdate =
			(TestrayCaseResultTestFlowUpdate)object;

		return Objects.equals(
			toString(), testrayCaseResultTestFlowUpdate.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Integer caseResultAmount = getCaseResultAmount();

		if (caseResultAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"caseResultAmount\": ");

			sb.append(caseResultAmount);
		}

		String comment = getComment();

		if (comment != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"comment\": ");

			sb.append("\"");

			sb.append(_escape(comment));

			sb.append("\"");
		}

		String dueStatus = getDueStatus();

		if (dueStatus != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dueStatus\": ");

			sb.append("\"");

			sb.append(_escape(dueStatus));

			sb.append("\"");
		}

		String issues = getIssues();

		if (issues != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"issues\": ");

			sb.append("\"");

			sb.append(_escape(issues));

			sb.append("\"");
		}

		Long mbMessageId = getMbMessageId();

		if (mbMessageId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"mbMessageId\": ");

			sb.append(mbMessageId);
		}

		Long mbThreadId = getMbThreadId();

		if (mbThreadId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"mbThreadId\": ");

			sb.append(mbThreadId);
		}

		Long userId = getUserId();

		if (userId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userId\": ");

			sb.append(userId);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.testray.rest.dto.v1_0.TestrayCaseResultTestFlowUpdate",
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