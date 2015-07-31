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

package com.liferay.taglib;

import aQute.bnd.annotation.headers.ProvideCapability;

/**
 * @author Raymond Augé
 */
public class Capabilities {

	@ProvideCapability(
		name = "jsp.taglib", ns = "osgi.extender",
		value = "uri=http://alloy.liferay.com/tld/alloy",
		version = "${@version}"
	)
	public class Alloy {
	}

	@ProvideCapability(
		name = "jsp.taglib", ns = "osgi.extender",
		value = "uri=http://alloy.liferay.com/tld/alloy_util",
		version = "${@version}"
	)
	public class AlloyUtil {
	}

	@ProvideCapability(
		name = "jsp.taglib", ns = "osgi.extender",
		value = "uri=http://alloy.liferay.com/tld/aui", version = "${@version}"
	)
	public class AUI {
	}

	@ProvideCapability(
		name = "jsp.taglib", ns = "osgi.extender",
		value = "uri=http://liferay.com/tld/aui", version = "${@version}"
	)
	public class LiferayAUI {
	}

	@ProvideCapability(
		name = "jsp.taglib", ns = "osgi.extender",
		value = "uri=http://java.sun.com/portlet", version = "${@version}"
	)
	public class LiferayPortlet {
	}

	@ProvideCapability(
		name = "jsp.taglib", ns = "osgi.extender",
		value = "uri=http://liferay.com/tld/portlet", version = "${@version}"
	)
	public class LiferayPortletExt {
	}

	@ProvideCapability(
		name = "jsp.taglib", ns = "osgi.extender",
		value = "uri=http://liferay.com/tld/security", version = "${@version}"
	)
	public class LiferaySecurity {
	}

	@ProvideCapability(
		name = "jsp.taglib", ns = "osgi.extender",
		value = "uri=http://liferay.com/tld/theme", version = "${@version}"
	)
	public class LiferayTheme {
	}

	@ProvideCapability(
		name = "jsp.taglib", ns = "osgi.extender",
		value = "uri=http://liferay.com/tld/ui", version = "${@version}"
	)
	public class LiferayUI {
	}

	@ProvideCapability(
		name = "jsp.taglib", ns = "osgi.extender",
		value = "uri=http://liferay.com/tld/util", version = "${@version}"
	)
	public class LiferayUtil {
	}

	@ProvideCapability(
		name = "jsp.taglib", ns = "osgi.extender",
		value = "uri=http://java.sun.com/portlet_2_0", version = "${@version}"
	)
	public class Portlet {
	}

}