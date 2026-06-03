/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the FaroPreferences service. Represents a row in the &quot;OSBFaro_FaroPreferences&quot; database table, with each column mapped to a property of this class.
 *
 * @author Matthew Kong
 * @see FaroPreferencesModel
 * @generated
 */
@ImplementationClassName("com.liferay.osb.faro.model.impl.FaroPreferencesImpl")
@ProviderType
public interface FaroPreferences extends FaroPreferencesModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.osb.faro.model.impl.FaroPreferencesImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<FaroPreferences, Long>
		FARO_PREFERENCES_ID_ACCESSOR = new Accessor<FaroPreferences, Long>() {

			@Override
			public Long get(FaroPreferences faroPreferences) {
				return faroPreferences.getFaroPreferencesId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<FaroPreferences> getTypeClass() {
				return FaroPreferences.class;
			}

		};

}