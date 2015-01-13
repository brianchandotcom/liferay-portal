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
		name = "jsp.taglib", ns = "osgi.extender", version = "${@version}",
		value = "uri=http://alloy.liferay.com/tld/aui"
	)
	public class AUI {
	}

	@ProvideCapability(
		name = "jsp.taglib", ns = "osgi.extender", version = "${@version}",
		value = "uri=http://liferay.com/tld/ddm"
	)
	public class DDM {
	}

	@ProvideCapability(
		name = "jsp.taglib", ns = "osgi.extender", version = "${@version}",
		value = "uri=http://liferay.com/tld/aui"
	)
	public class LiferayAUI {
	}

	@ProvideCapability(
		name = "jsp.taglib", ns = "osgi.extender", version = "${@version}",
		value = "uri=http://liferay.com/tld/ddm"
	)
	public class LiferayDDM {
	}

	@ProvideCapability(
		name = "jsp.taglib", ns = "osgi.extender", version = "${@version}",
		value = "uri=http://java.sun.com/portlet"
	)
	public class LiferayPortlet {
	}

	@ProvideCapability(
		name = "jsp.taglib", ns = "osgi.extender", version = "${@version}",
		value = "uri=http://liferay.com/tld/portlet"
	)
	public class LiferayPortletExt {
	}

	@ProvideCapability(
		name = "jsp.taglib", ns = "osgi.extender", version = "${@version}",
		value = "uri=http://liferay.com/tld/security"
	)
	public class LiferaySecurity {
	}

	@ProvideCapability(
		name = "jsp.taglib", ns = "osgi.extender", version = "${@version}",
		value = "uri=http://liferay.com/tld/staging"
	)
	public class LiferayStaging {
	}

	@ProvideCapability(
		name = "jsp.taglib", ns = "osgi.extender", version = "${@version}",
		value = "uri=http://liferay.com/tld/theme"
	)
	public class LiferayTheme {
	}

	@ProvideCapability(
		name = "jsp.taglib", ns = "osgi.extender", version = "${@version}",
		value = "uri=http://liferay.com/tld/ui"
	)
	public class LiferayUI {
	}

	@ProvideCapability(
		name = "jsp.taglib", ns = "osgi.extender", version = "${@version}",
		value = "uri=http://liferay.com/tld/util"
	)
	public class LiferayUtil {
	}

	@ProvideCapability(
		name = "jsp.taglib", ns = "osgi.extender", version = "${@version}",
		value = "uri=http://java.sun.com/portlet_2_0"
	)
	public class Portlet {
	}

}