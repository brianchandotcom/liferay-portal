/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getCamelCase} from '../getCamelCase';

const todayDate = new Date();
const currentYear = todayDate.getFullYear();

const previousFiscalYearStart = `${currentYear - 1}-01-01`;
const currentFiscalYearStart = `${currentYear}-01-01`;
const currentFiscalYearEnd = `${currentYear}-12-31`;

const fiscalYearFilterCloseDate = `closeDate ge ${currentFiscalYearStart} and closeDate le ${currentFiscalYearEnd}`;
const fiscalYearFilterSubmitDate = `submitDate ge ${currentFiscalYearStart}T00:00:00Z and submitDate le ${currentFiscalYearEnd}T23:59:59Z`;
const fiscalYearFilterDateCreated = `dateCreated ge ${currentFiscalYearStart}T00:00:00Z and dateCreated le ${currentFiscalYearEnd}T23:59:59Z`;
const previousToCurrentYearFilterDateCreated = `dateCreated ge ${previousFiscalYearStart}T00:00:00Z and dateCreated le ${currentFiscalYearEnd}T23:59:59Z`;

// const fiscalYearFilterCreatedDate = `createdDate ge ${currentFiscalYearStart}T00:00:00Z and createdDate le ${currentFiscalYearEnd}T23:59:59Z`;

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

const opportunityStageClosed = `stage ne 'Marketing Qualified' and stage ne 'Sales Accepted Opportunity' and stage ne 'Confirmation' and stage ne 'Justification / Solution Review' and stage ne 'Justification / Due Diligence' and stage ne 'Solution Validation' and stage ne 'GTM / Partnership Plan Validation' and stage ne 'Legal Review / Purchasing' and stage ne 'Legal Review' and stage ne 'Committed' and stage ne 'Pending' and stage ne 'Rolled into Opportunity'`;
const opportunityStageOpen = `stage ne 'Closed Won' and stage ne 'Closed Lost' and stage ne 'Disqualified' and stage ne 'Rejected' and stage ne 'Rolled into Opportunity'`;

todayDate.setDate(todayDate.getDate() + 30);
const thirtyDaysFromToday = todayDate.toISOString().split('T')[0];

export const Filters = {
	DEAL_DASHBOARD: {
		deals: `leadType eq 'Partner Qualified Lead (PQL)'`,
	},
	DEAL_LISTING: {
		rejectedWIP: `leadType eq 'Partner Qualified Lead (PQL)' and leadStatus eq 'CAM rejected'`,

		submittedWIP: `leadType eq 'Partner Qualified Lead (PQL)' and leadStatus ne 'Qualified' and leadStatus ne 'CAM rejected'`,
	},
	LEVEL_DASHBOARD: {
		opportunities: `stage eq 'Closed Won' and ${fiscalYearFilterCloseDate}`,
	},
	MDF_CLAIM_LISTING: {
		channelsCompleted: `${mdfClaimCompletedFilter} and ${previousToCurrentYearFilterDateCreated}`,
		channelsOpen: `${mdfClaimOpenFilter} and (mdfClaimStatus ne 'draft')`,
		completedList: mdfClaimCompletedListStatus,
		openList: mdfClaimOpenListStatus,
		partnersCompleted: `${mdfClaimCompletedFilter} and ${fiscalYearFilterDateCreated} `,
		partnersOpen: `${mdfClaimOpenFilter}`,
	},
	MDF_DASHBOARD: {
		fields: `accountEntry,mdfReqToActs,actToBgts,mdfReqToMDFClms&nestedFieldsDepth=2`,
		requests: `mdfRequestStatus ne 'draft' and ${fiscalYearFilterSubmitDate}`,
	},
	MDF_REQUEST_LISTING: {
		channelsCompleted: `${mdfRequestCompletedFilter} and ${previousToCurrentYearFilterDateCreated}`,
		channelsOpen: `${mdfRequestOpenFilter} and (mdfRequestStatus ne 'draft')`,
		completedList: mdfRequestCompletedListStatus,
		openList: mdfRequestOpenListStatus,
		partnersCompleted: `${mdfRequestCompletedFilter} and ${fiscalYearFilterDateCreated}`,
		partnersOpen: `${mdfRequestOpenFilter}`,
	},
	OPPORTUNITY_LISTING: {
		closedWIP: `${opportunityStageClosed} and ${fiscalYearFilterCloseDate}`,
		openWIP: `${opportunityStageOpen}`,
	},
	RENEWAL_DASHBOARD: {
		renewals: `${opportunityStageOpen} and type eq 'Existing Business' and closeDate le ${thirtyDaysFromToday}`,
	},
	RENEWAL_LISTING: {
		closedWIP: `${opportunityStageClosed} and type eq 'Existing Business' and ${fiscalYearFilterCloseDate}`,
		openWIP: `${opportunityStageOpen} and type eq 'Existing Business'`,
	},
	REVENUE_DASHBOARD: {
		opportunities: `stage eq 'Closed Won' and ${fiscalYearFilterCloseDate}`,
	},
};
