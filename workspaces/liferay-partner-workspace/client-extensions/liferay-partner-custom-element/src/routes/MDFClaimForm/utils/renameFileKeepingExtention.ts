/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import LiferayFile from '../../../common/interfaces/liferayFile';

const renameFileKeepingExtention = (oldFile: LiferayFile, name: string) => {
	const oldName = oldFile.name?.split('.');
	const newName = `${name}.${oldName?.slice(-1)}`;

	return new File([oldFile as Blob], newName, {
		lastModified: oldFile.lastModified,
		type: oldFile.type,
	});
};

export default renameFileKeepingExtention;
