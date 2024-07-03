/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getCamelCase} from '../getCamelCase';

const todayDate = new Date();
const currentYear = todayDate.getFullYear();

const currentFiscalYearEnd = `${currentYear}-12-31`;
export const currentFiscalYearStart = `${currentYear}-01-01`;
export const previousFiscalYearStart = `${currentYear - 1}-01-01`;

const fiscalYearFilterCloseDate = `closeDate ge ${currentFiscalYearStart} and closeDate le ${currentFiscalYearEnd}`;
const fiscalYearFilterCreatedDate = `createdDate ge ${currentFiscalYearStart}T00:00:00Z and createdDate le ${currentFiscalYearEnd}T23:59:59Z`;
const fiscalYearFilterSubmitDate = `submitDate ge ${currentFiscalYearStart}T00:00:00Z and submitDate le ${currentFiscalYearEnd}T23:59:59Z`;
const previousToCurrentYearFilterSubmitDate = `submitDate ge ${previousFiscalYearStart}T00:00:00Z and submitDate le ${currentFiscalYearEnd}T23:59:59Z`;
export const REFERENCE_DEAL_SUBMITTED_DATE = '2023-01-01T00:00:00Z';

const mdfRequestOpenListStatus = [
	'Approved',
	'Draft',
	'Marketing Director Review',
	'More Info Requested',
	'Pending Marketing Review',
];

const mdfRequestCompletedListStatus = [
	'Canceled',
	'Completed',
	'Expired',
	'Rejected',
];

const mdfClaimOpenListStatus = [
	'Pending Marketing Review',
	'Approved',
	'In Finance Review',
	'In Director Review',
	'More Info Requested',
	'Draft',
];

const mdfClaimCompletedListStatus = ['Canceled', 'Rejected', 'Claim Paid'];

const renewalStages = [
	'Closed Lost',
	'Closed Won',
	'Committed',
	'Confirmation',
	'Justification / Solution Review',
	'Legal Review / Purchasing',
	'Pending',
	'Solution Validation',
];

const opportunityStages = [
	'Closed Lost',
	'Closed Won',
	'Committed',
	'Confirmation',
	'Disqualified',
	'Justification / Solution Review',
	'Legal Review / Purchasing',
	'Pending',
	'Qualified Meeting',
	'Rejected',
	'Solution Validation',
];

const mdfRequestOpenFilter = mdfRequestCompletedListStatus
	.map((status) => {
		return `(mdfRequestStatus ne '${getCamelCase(status)}')`;
	})
	.join(' and ');

const mdfRequestCompletedFilter = mdfRequestOpenListStatus
	.map((status) => {
		return `(mdfRequestStatus ne '${getCamelCase(status)}')`;
	})
	.join(' and ');

const mdfClaimOpenFilter = mdfClaimCompletedListStatus
	.map((status) => {
		return `(mdfClaimStatus ne '${getCamelCase(status)}')`;
	})
	.join(' and ');

const mdfClaimCompletedFilter = mdfClaimOpenListStatus
	.map((status) => {
		return `(mdfClaimStatus ne '${getCamelCase(status)}')`;
	})
	.join(' and ');

todayDate.setDate(todayDate.getDate() + 30);
const thirtyDaysFromToday = todayDate.toISOString().split('T')[0];

export const Filters = {
	DEAL_DASHBOARD: {
		deals: `leadType eq 'Partner Qualified Lead (PQL)'`,
	},
	DEAL_LISTING: {
		rejected: `${fiscalYearFilterCreatedDate} and leadType eq 'Partner Qualified Lead (PQL)' and leadStatus eq 'CAM rejected'`,
		submitted: `createdDate ge ${REFERENCE_DEAL_SUBMITTED_DATE} and leadType eq 'Partner Qualified Lead (PQL)' and isConverted eq false and leadStatus ne 'CAM rejected'`,
	},
	LEVEL_DASHBOARD: {
		opportunities: `${fiscalYearFilterCloseDate} and stage eq 'Closed Won'`,
	},
	MDF_CLAIM_LISTING: {
		channelsCompleted: `${mdfClaimCompletedFilter} and ${previousToCurrentYearFilterSubmitDate}`,
		channelsOpen: `${mdfClaimOpenFilter} and (mdfClaimStatus ne 'draft')`,
		completedList: mdfClaimCompletedListStatus,
		openList: mdfClaimOpenListStatus,
		partnersCompleted: `${mdfClaimCompletedFilter} and ${fiscalYearFilterSubmitDate} `,
		partnersOpen: `${mdfClaimOpenFilter}`,
	},
	MDF_DASHBOARD: {
		fields: `accountEntry,mdfReqToActs,actToBgts,mdfReqToMDFClms&nestedFieldsDepth=2`,
		requests: `mdfRequestStatus ne 'draft' and ${fiscalYearFilterSubmitDate}`,
	},
	MDF_REQUEST_LISTING: {
		channelsCompleted: `${mdfRequestCompletedFilter} and ${previousToCurrentYearFilterSubmitDate}`,
		channelsOpen: `${mdfRequestOpenFilter} and (mdfRequestStatus ne 'draft')`,
		completedList: mdfRequestCompletedListStatus,
		openList: mdfRequestOpenListStatus,
		partnersCompleted: `${mdfRequestCompletedFilter} and ${fiscalYearFilterSubmitDate}`,
		partnersOpen: `${mdfRequestOpenFilter}`,
	},
	OPPORTUNITY_LISTING: {
		opportunities: `closeDate ge ${currentFiscalYearStart} and ((type eq 'New Business' or type eq 'New Project Existing Business') or (type eq 'Existing Business' and hasRenewal eq false))`,
		stages: opportunityStages,
	},
	RENEWAL_DASHBOARD: {
		renewals: `stage ne 'Closed Won' and stage ne 'Closed Lost' and stage ne 'Disqualified' and stage ne 'Rejected' and stage ne 'Rolled into Opportunity' and type eq 'Existing Business' and closeDate le ${thirtyDaysFromToday}`,
	},
	RENEWAL_LISTING: {
		opportunities: `closeDate ge ${currentFiscalYearStart} and type eq 'Existing Business' and hasRenewal eq true`,
		stages: renewalStages,
	},
	REVENUE_DASHBOARD: {
		opportunities: `${fiscalYearFilterCloseDate} and stage eq 'Closed Won'`,
	},
};
