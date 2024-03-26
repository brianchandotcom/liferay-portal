/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default class GitBranch {
	constructor({
		branchSHA,
		branchURL,
		dateCreated,
		dateModified,
		id,
		rebased,
		type,
		upstreamBranchSHA,
		upstreamBranchURL,
	}) {
		this.branchSHA = branchSHA;
		this.branchURL = branchURL;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.id = id;
		this.rebased = rebased;
		this.type = type;
		this.upstreamBranchSHA = upstreamBranchSHA;
		this.upstreamBranchURL = upstreamBranchURL;
	}
}
