/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.model.impl;

import com.liferay.osb.faro.model.FaroProjectEmailDomain;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing FaroProjectEmailDomain in entity cache.
 *
 * @author Matthew Kong
 * @generated
 */
public class FaroProjectEmailDomainCacheModel
	implements CacheModel<FaroProjectEmailDomain>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FaroProjectEmailDomainCacheModel)) {
			return false;
		}

		FaroProjectEmailDomainCacheModel faroProjectEmailDomainCacheModel =
			(FaroProjectEmailDomainCacheModel)object;

		if ((faroProjectEmailDomainId ==
				faroProjectEmailDomainCacheModel.faroProjectEmailDomainId) &&
			(mvccVersion == faroProjectEmailDomainCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, faroProjectEmailDomainId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", faroProjectEmailDomainId=");
		sb.append(faroProjectEmailDomainId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", faroProjectId=");
		sb.append(faroProjectId);
		sb.append(", emailDomain=");
		sb.append(emailDomain);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public FaroProjectEmailDomain toEntityModel() {
		FaroProjectEmailDomainImpl faroProjectEmailDomainImpl =
			new FaroProjectEmailDomainImpl();

		faroProjectEmailDomainImpl.setMvccVersion(mvccVersion);
		faroProjectEmailDomainImpl.setFaroProjectEmailDomainId(
			faroProjectEmailDomainId);
		faroProjectEmailDomainImpl.setGroupId(groupId);
		faroProjectEmailDomainImpl.setCompanyId(companyId);
		faroProjectEmailDomainImpl.setFaroProjectId(faroProjectId);

		if (emailDomain == null) {
			faroProjectEmailDomainImpl.setEmailDomain("");
		}
		else {
			faroProjectEmailDomainImpl.setEmailDomain(emailDomain);
		}

		faroProjectEmailDomainImpl.resetOriginalValues();

		return faroProjectEmailDomainImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		faroProjectEmailDomainId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		faroProjectId = objectInput.readLong();
		emailDomain = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(faroProjectEmailDomainId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(faroProjectId);

		if (emailDomain == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(emailDomain);
		}
	}

	public long mvccVersion;
	public long faroProjectEmailDomainId;
	public long groupId;
	public long companyId;
	public long faroProjectId;
	public String emailDomain;

}