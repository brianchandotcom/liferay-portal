/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.contacts.model.impl;

import com.liferay.osb.faro.contacts.model.ContactsCardTemplate;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ContactsCardTemplate in entity cache.
 *
 * @author Shinn Lok
 * @generated
 */
public class ContactsCardTemplateCacheModel
	implements CacheModel<ContactsCardTemplate>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ContactsCardTemplateCacheModel)) {
			return false;
		}

		ContactsCardTemplateCacheModel contactsCardTemplateCacheModel =
			(ContactsCardTemplateCacheModel)object;

		if ((contactsCardTemplateId ==
				contactsCardTemplateCacheModel.contactsCardTemplateId) &&
			(mvccVersion == contactsCardTemplateCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, contactsCardTemplateId);

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
		StringBundler sb = new StringBundler(23);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", contactsCardTemplateId=");
		sb.append(contactsCardTemplateId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createTime=");
		sb.append(createTime);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", modifiedTime=");
		sb.append(modifiedTime);
		sb.append(", name=");
		sb.append(name);
		sb.append(", settings=");
		sb.append(settings);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ContactsCardTemplate toEntityModel() {
		ContactsCardTemplateImpl contactsCardTemplateImpl =
			new ContactsCardTemplateImpl();

		contactsCardTemplateImpl.setMvccVersion(mvccVersion);
		contactsCardTemplateImpl.setContactsCardTemplateId(
			contactsCardTemplateId);
		contactsCardTemplateImpl.setGroupId(groupId);
		contactsCardTemplateImpl.setCompanyId(companyId);
		contactsCardTemplateImpl.setCreateTime(createTime);
		contactsCardTemplateImpl.setUserId(userId);

		if (userName == null) {
			contactsCardTemplateImpl.setUserName("");
		}
		else {
			contactsCardTemplateImpl.setUserName(userName);
		}

		contactsCardTemplateImpl.setModifiedTime(modifiedTime);

		if (name == null) {
			contactsCardTemplateImpl.setName("");
		}
		else {
			contactsCardTemplateImpl.setName(name);
		}

		if (settings == null) {
			contactsCardTemplateImpl.setSettings("");
		}
		else {
			contactsCardTemplateImpl.setSettings(settings);
		}

		contactsCardTemplateImpl.setType(type);

		contactsCardTemplateImpl.resetOriginalValues();

		return contactsCardTemplateImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		contactsCardTemplateId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		createTime = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();

		modifiedTime = objectInput.readLong();
		name = objectInput.readUTF();
		settings = objectInput.readUTF();

		type = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(contactsCardTemplateId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(createTime);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(modifiedTime);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (settings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(settings);
		}

		objectOutput.writeInt(type);
	}

	public long mvccVersion;
	public long contactsCardTemplateId;
	public long groupId;
	public long companyId;
	public long createTime;
	public long userId;
	public String userName;
	public long modifiedTime;
	public String name;
	public String settings;
	public int type;

}