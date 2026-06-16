/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.service.persistence.impl;

import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.model.impl.FaroProjectImpl;
import com.liferay.osb.faro.service.persistence.FaroProjectFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Geyson Silva
 */
@Component(service = FaroProjectFinder.class)
public class FaroProjectFinderImpl
	extends FaroProjectFinderBaseImpl implements FaroProjectFinder {

	public static final String FIND_BY_ED =
		FaroProjectFinder.class.getName() + ".findByED";

	@Override
	public List<FaroProject> findByEmailAddressDomain(
		String emailAddressDomain) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_ED);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity("OSBFaro_FaroProject", FaroProjectImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(emailAddressDomain);

			return (List<FaroProject>)QueryUtil.list(
				sqlQuery, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Reference
	private CustomSQL _customSQL;

}