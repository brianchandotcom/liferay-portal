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
@GraphQLName("TestraySubtask")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "TestraySubtask")
public class TestraySubtask implements Serializable {

	public static TestraySubtask toDTO(String json) {
		return ObjectMapperUtil.readValue(TestraySubtask.class, json);
	}

	public static TestraySubtask unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(TestraySubtask.class, json);
	}

	@Schema
	public Long getCaseResultAmount() {
		if (_caseResultAmountSupplier != null) {
			caseResultAmount = _caseResultAmountSupplier.get();

			_caseResultAmountSupplier = null;
		}

		return caseResultAmount;
	}

	public void setCaseResultAmount(Long caseResultAmount) {
		this.caseResultAmount = caseResultAmount;

		_caseResultAmountSupplier = null;
	}

	@JsonIgnore
	public void setCaseResultAmount(
		UnsafeSupplier<Long, Exception> caseResultAmountUnsafeSupplier) {

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
	protected Long caseResultAmount;

	@JsonIgnore
	private Supplier<Long> _caseResultAmountSupplier;

	@Schema
	public String getError() {
		if (_errorSupplier != null) {
			error = _errorSupplier.get();

			_errorSupplier = null;
		}

		return error;
	}

	public void setError(String error) {
		this.error = error;

		_errorSupplier = null;
	}

	@JsonIgnore
	public void setError(
		UnsafeSupplier<String, Exception> errorUnsafeSupplier) {

		_errorSupplier = () -> {
			try {
				return errorUnsafeSupplier.get();
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
	protected String error;

	@JsonIgnore
	private Supplier<String> _errorSupplier;

	@Schema
	public Long getId() {
		if (_idSupplier != null) {
			id = _idSupplier.get();

			_idSupplier = null;
		}

		return id;
	}

	public void setId(Long id) {
		this.id = id;

		_idSupplier = null;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		_idSupplier = () -> {
			try {
				return idUnsafeSupplier.get();
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
	protected Long id;

	@JsonIgnore
	private Supplier<Long> _idSupplier;

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
	public String getName() {
		if (_nameSupplier != null) {
			name = _nameSupplier.get();

			_nameSupplier = null;
		}

		return name;
	}

	public void setName(String name) {
		this.name = name;

		_nameSupplier = null;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		_nameSupplier = () -> {
			try {
				return nameUnsafeSupplier.get();
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
	protected String name;

	@JsonIgnore
	private Supplier<String> _nameSupplier;

	@Schema
	public Long getScore() {
		if (_scoreSupplier != null) {
			score = _scoreSupplier.get();

			_scoreSupplier = null;
		}

		return score;
	}

	public void setScore(Long score) {
		this.score = score;

		_scoreSupplier = null;
	}

	@JsonIgnore
	public void setScore(UnsafeSupplier<Long, Exception> scoreUnsafeSupplier) {
		_scoreSupplier = () -> {
			try {
				return scoreUnsafeSupplier.get();
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
	protected Long score;

	@JsonIgnore
	private Supplier<Long> _scoreSupplier;

	@Schema
	public String getStatus() {
		if (_statusSupplier != null) {
			status = _statusSupplier.get();

			_statusSupplier = null;
		}

		return status;
	}

	public void setStatus(String status) {
		this.status = status;

		_statusSupplier = null;
	}

	@JsonIgnore
	public void setStatus(
		UnsafeSupplier<String, Exception> statusUnsafeSupplier) {

		_statusSupplier = () -> {
			try {
				return statusUnsafeSupplier.get();
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
	protected String status;

	@JsonIgnore
	private Supplier<String> _statusSupplier;

	@Schema
	public Long getTestrayTaskId() {
		if (_testrayTaskIdSupplier != null) {
			testrayTaskId = _testrayTaskIdSupplier.get();

			_testrayTaskIdSupplier = null;
		}

		return testrayTaskId;
	}

	public void setTestrayTaskId(Long testrayTaskId) {
		this.testrayTaskId = testrayTaskId;

		_testrayTaskIdSupplier = null;
	}

	@JsonIgnore
	public void setTestrayTaskId(
		UnsafeSupplier<Long, Exception> testrayTaskIdUnsafeSupplier) {

		_testrayTaskIdSupplier = () -> {
			try {
				return testrayTaskIdUnsafeSupplier.get();
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
	protected Long testrayTaskId;

	@JsonIgnore
	private Supplier<Long> _testrayTaskIdSupplier;

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

	@Schema
	public String getUserName() {
		if (_userNameSupplier != null) {
			userName = _userNameSupplier.get();

			_userNameSupplier = null;
		}

		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;

		_userNameSupplier = null;
	}

	@JsonIgnore
	public void setUserName(
		UnsafeSupplier<String, Exception> userNameUnsafeSupplier) {

		_userNameSupplier = () -> {
			try {
				return userNameUnsafeSupplier.get();
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
	protected String userName;

	@JsonIgnore
	private Supplier<String> _userNameSupplier;

	@Schema
	public String getUserPortraitUrl() {
		if (_userPortraitUrlSupplier != null) {
			userPortraitUrl = _userPortraitUrlSupplier.get();

			_userPortraitUrlSupplier = null;
		}

		return userPortraitUrl;
	}

	public void setUserPortraitUrl(String userPortraitUrl) {
		this.userPortraitUrl = userPortraitUrl;

		_userPortraitUrlSupplier = null;
	}

	@JsonIgnore
	public void setUserPortraitUrl(
		UnsafeSupplier<String, Exception> userPortraitUrlUnsafeSupplier) {

		_userPortraitUrlSupplier = () -> {
			try {
				return userPortraitUrlUnsafeSupplier.get();
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
	protected String userPortraitUrl;

	@JsonIgnore
	private Supplier<String> _userPortraitUrlSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TestraySubtask)) {
			return false;
		}

		TestraySubtask testraySubtask = (TestraySubtask)object;

		return Objects.equals(toString(), testraySubtask.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Long caseResultAmount = getCaseResultAmount();

		if (caseResultAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"caseResultAmount\": ");

			sb.append(caseResultAmount);
		}

		String error = getError();

		if (error != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"error\": ");

			sb.append("\"");

			sb.append(_escape(error));

			sb.append("\"");
		}

		Long id = getId();

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(id);
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

		String name = getName();

		if (name != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(name));

			sb.append("\"");
		}

		Long score = getScore();

		if (score != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"score\": ");

			sb.append(score);
		}

		String status = getStatus();

		if (status != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append("\"");

			sb.append(_escape(status));

			sb.append("\"");
		}

		Long testrayTaskId = getTestrayTaskId();

		if (testrayTaskId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayTaskId\": ");

			sb.append(testrayTaskId);
		}

		Long userId = getUserId();

		if (userId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userId\": ");

			sb.append(userId);
		}

		String userName = getUserName();

		if (userName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userName\": ");

			sb.append("\"");

			sb.append(_escape(userName));

			sb.append("\"");
		}

		String userPortraitUrl = getUserPortraitUrl();

		if (userPortraitUrl != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userPortraitUrl\": ");

			sb.append("\"");

			sb.append(_escape(userPortraitUrl));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.testray.rest.dto.v1_0.TestraySubtask",
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