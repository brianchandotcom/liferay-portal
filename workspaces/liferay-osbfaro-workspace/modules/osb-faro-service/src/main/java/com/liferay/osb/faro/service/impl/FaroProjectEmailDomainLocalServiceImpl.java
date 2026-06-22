/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.service.impl;

import com.liferay.osb.faro.exception.EmailAddressDomainException;
import com.liferay.osb.faro.model.FaroProjectEmailDomain;
import com.liferay.osb.faro.service.base.FaroProjectEmailDomainLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Matthew Kong
 */
@Component(
	property = "model.class.name=com.liferay.osb.faro.model.FaroProjectEmailDomain",
	service = AopService.class
)
public class FaroProjectEmailDomainLocalServiceImpl
	extends FaroProjectEmailDomainLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public FaroProjectEmailDomain addFaroProjectEmailDomain(
		long groupId, long faroProjectId, String emailDomain) {

		long faroProjectEmailDomainId = counterLocalService.increment();

		FaroProjectEmailDomain faroProjectEmailDomain =
			faroProjectEmailDomainPersistence.create(faroProjectEmailDomainId);

		faroProjectEmailDomain.setGroupId(groupId);
		faroProjectEmailDomain.setFaroProjectId(faroProjectId);
		faroProjectEmailDomain.setEmailDomain(emailDomain);

		return faroProjectEmailDomainPersistence.update(faroProjectEmailDomain);
	}

	@Override
	public void addFaroProjectEmailDomains(
		long groupId, long faroProjectId, List<String> emailAddressDomains) {

		_validate(emailAddressDomains);

		faroProjectEmailDomainPersistence.removeByFaroProjectId(faroProjectId);

		for (String emailDomain : emailAddressDomains) {
			addFaroProjectEmailDomain(groupId, faroProjectId, emailDomain);
		}
	}

	public void afterPropertiesSet() {
		ClassLoader classLoader = getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				"com/liferay/osb/faro/dependencies" +
					"/email-domains-blacklist.csv")) {

			StringUtil.readLines(inputStream, _emailDomainsBlacklist);
		}
		catch (IOException ioException) {
			throw new SystemException(
				"Unable to read email domains blacklist", ioException);
		}
	}

	@Override
	public List<FaroProjectEmailDomain>
		getFaroProjectEmailDomainsByFaroProjectId(long faroProjectId) {

		return faroProjectEmailDomainPersistence.findByFaroProjectId(
			faroProjectId);
	}

	@Override
	public List<FaroProjectEmailDomain> getFaroProjectEmailDomainsByGroupId(
		long groupId) {

		return faroProjectEmailDomainPersistence.findByGroupId(groupId);
	}

	private void _validate(List<String> emailAddressDomains) {
		Set<String> invalidEmailDomains = new HashSet<>(emailAddressDomains);

		invalidEmailDomains.retainAll(_emailDomainsBlacklist);

		if (!invalidEmailDomains.isEmpty()) {
			throw new EmailAddressDomainException(
				"There are invalid email domains", invalidEmailDomains);
		}
	}

	private final Set<String> _emailDomainsBlacklist = new HashSet<>();

}