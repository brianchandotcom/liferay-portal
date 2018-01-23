/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * @author Brian Wing Shun Chan
 */
public class InetAddressTask extends Task {

	@Override
	public void execute() throws BuildException {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();

			if (Validator.isNotNull(_hostAddressProperty)) {
				getProject().setUserProperty(
					_hostAddressProperty, inetAddress.getHostAddress());
			}

			if (Validator.isNotNull(_hostNameProperty)) {
				getProject().setUserProperty(
					_hostNameProperty, inetAddress.getHostName());
			}

			if (Validator.isNotNull(_vmId1Property)) {
				int id = GetterUtil.getInteger(
					StringUtil.extractDigits(inetAddress.getHostName()));

				getProject().setUserProperty(
					_vmId1Property, String.valueOf((id * 2) - 1));
			}

			if (Validator.isNotNull(_vmId2Property)) {
				int id = GetterUtil.getInteger(
					StringUtil.extractDigits(inetAddress.getHostName()));

				getProject().setUserProperty(
					_vmId2Property, String.valueOf(id * 2));
			}
		}
		catch (UnknownHostException uhe) {
			throw new BuildException(uhe);
		}
	}

	public void setHostAddressProperty(String hostAddressProperty) {
		_hostAddressProperty = hostAddressProperty;
	}

	public void setHostNameProperty(String hostNameProperty) {
		_hostNameProperty = hostNameProperty;
	}

	public void setVmId1Property(String vmId1Property) {
		_vmId1Property = vmId1Property;
	}

	public void setVmId2Property(String vmId2Property) {
		_vmId2Property = vmId2Property;
	}

	private String _hostAddressProperty;
	private String _hostNameProperty;
	private String _vmId1Property;
	private String _vmId2Property;

}