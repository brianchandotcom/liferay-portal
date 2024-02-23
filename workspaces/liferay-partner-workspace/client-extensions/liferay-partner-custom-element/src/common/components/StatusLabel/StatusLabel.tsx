/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Status} from '../../utils/constants/status';

type StatusClassname = {
	[key in string]: string;
};

interface Props {
	status: string;
}

const statusClassName: StatusClassname = {
	[Status.APPROVED.name]: 'label label-inverse-success',
	[Status.CANCELED.name]: 'label label-inverse-dark',
	[Status.CLAIM_PAID.name]: 'label label-inverse-info',
	[Status.DRAFT.name]: 'label label-inverse-dark',
	[Status.EXPIRED.name]: 'label label-inverse-dark',
	[Status.IN_DIRECTOR_REVIEW.name]: 'label label-inverse-info',
	[Status.IN_FINANCE_REVIEW.name]: 'label label-inverse-info',
	[Status.MARKETING_DIRECTOR_REVIEW.name]: 'label label-inverse-info',
	[Status.PENDING.name]: 'label label-inverse-warning',
	[Status.REJECT.name]: 'label label-inverse-dark',
	[Status.REQUEST_MORE_INFO.name]: 'label label-inverse-danger',
};

const StatusLabel = ({status}: Props) => {
	return <span className={statusClassName[status]}>{status}</span>;
};

export default StatusLabel;
