/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.util;

import com.liferay.portal.kernel.util.HashCode;
import com.liferay.portal.kernel.util.HashCodeFactoryUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class State {

	public State(String id, String name) {
		_id = id;
		_name = name;
	}

	public int compareTo(Object obj) {
		State state = (State)obj;

		if ((getId() != null) && (state.getId() != null)) {
			return getId().toLowerCase().compareTo(state.getId().toLowerCase());
		}
		else if ((getName() != null) && (state.getName() != null)) {
			return getName().toLowerCase().compareTo(
				state.getName().toLowerCase());
		}
		else {
			return -1;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof State)) {
			return false;
		}

		State state = (State)obj;

		if ((getId() != null) && (state.getId() != null)) {
			return getId().equalsIgnoreCase(state.getId());
		}
		else if ((getName() != null) && (state.getName() != null)) {
			return getName().equalsIgnoreCase(state.getName());
		}
		else {
			return false;
		}
	}

	public String getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	@Override
	public int hashCode() {
		HashCode hashCode = HashCodeFactoryUtil.getHashCode();

		hashCode.append(_id);
		hashCode.append(_name);

		return hashCode.toHashCode();
	}

	private String _id;
	private String _name;

}