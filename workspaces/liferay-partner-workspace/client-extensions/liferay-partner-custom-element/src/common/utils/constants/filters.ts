/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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

const mdfClaimStageClosed = `mdfClaimStatus eq 'claimPaid' or mdfClaimStatus eq 'canceled' or mdfClaimStatus eq 'rejected'`;
const mdfClaimStageOpen = `mdfClaimStatus ne 'claimPaid' and mdfClaimStatus ne 'canceled' and mdfClaimStatus ne 'rejected'`;
const mdfRequestStageClosed = `(mdfRequestStatus eq 'Closed' or mdfRequestStatus eq 'Canceled' or mdfRequestStatus eq 'Rejected'`;
const mdfRequestStageOpen = `(mdfRequestStatus eq 'Draft' or mdfRequestStatus eq 'Pending Marketing Review' or mdfRequestStatus eq 'Approved' or mdfRequestStatus eq 'Marketing Director Review' or mdfRequestStatus eq 'More Info Requested')`;
const opportunityStageClosed = `stage ne 'Marketing Qualified' and stage ne 'Sales Accepted Opportunity' and stage ne 'Confirmation' and stage ne 'Justification / Solution Review' and stage ne 'Justification / Due Diligence' and stage ne 'Solution Validation' and stage ne 'GTM / Partnership Plan Validation' and stage ne 'Legal Review / Purchasing' and stage ne 'Legal Review' and stage ne 'Committed' and stage ne 'Pending' and stage ne 'Rolled into Opportunity'`;
const opportunityStageOpen = `stage ne 'Closed Won' and stage ne 'Closed Lost' and stage ne 'Disqualified' and stage ne 'Rejected' and stage ne 'Rolled into Opportunity'`;

todayDate.setDate(todayDate.getDate() + 30);
const thirtyDaysFromToday = todayDate.toISOString().split('T')[0];

export const Filters = {
	DEAL_DASHBOARD: {
		deals: `leadType eq 'Partner Qualified Lead (PQL)'`,

		// approvedLeads: `leadType eq 'Partner Qualified Lead (PQL)' and leadStatus eq 'Qualified'`,
		// rejectedLeads: `leadType eq 'Partner Qualified Lead (PQL)' and leadStatus eq 'CAM rejected'`,
		// submittedLeads: `leadType eq 'Partner Qualified Lead (PQL)' and leadStatus ne 'Qualified' and leadStatus ne 'CAM rejected'`,
	},
	DEAL_LISTING: {
		// rejected: `leadType eq 'Partner Qualified Lead (PQL)' and leadStatus eq 'CAM rejected' and ${fiscalYearFilterCreatedDate}`,

		rejectedWIP: `leadType eq 'Partner Qualified Lead (PQL)' and leadStatus eq 'CAM rejected'`,

		// submitted: `leadType eq 'Partner Qualified Lead (PQL)' and (leadStatus ne 'Qualified' and leadStatus ne 'CAM rejected' or isConverted ne true) and createdDate ge 2023-01-01T00:00:00Z`,

		submittedWIP: `leadType eq 'Partner Qualified Lead (PQL)' and leadStatus ne 'Qualified' and leadStatus ne 'CAM rejected'`,
	},
	LEVEL_DASHBOARD: {
		opportunities: `stage eq 'Closed Won' and ${fiscalYearFilterCloseDate}`,

		// expansion: `stage eq 'Closed Won' and type eq 'Existing Business' and Has_Renewal_Indicator__c eq false and GrowthARR gt 0 and ${fiscalYearFilterCloseDate}`,
		// newBusiness: `stage eq 'Closed Won' and type eq 'New Business' and ${fiscalYearFilterCloseDate}`,
		// newProjectExistingBusiness: `stage eq 'Closed Won' and type eq 'New Project Existing Business' and ${fiscalYearFilterCloseDate}`,
	},
	MDF_CLAIM_LISTING: {
		channelsClosed: `${mdfClaimStageClosed} and ${previousToCurrentYearFilterDateCreated}`,
		channelsOpen: `${mdfClaimStageOpen} and mdfClaimStatus ne 'draft' and ${previousToCurrentYearFilterDateCreated}`,
		partnerClosed: `${mdfClaimStageClosed} and ${fiscalYearFilterDateCreated} `,
		partnerOpen: `${mdfClaimStageOpen}`,
	},
	MDF_DASHBOARD: {
		fields: `accountEntry,mdfReqToActs,actToBgts,mdfReqToMDFClms&nestedFieldsDepth=2`,
		requests: `mdfRequestStatus ne 'draft' and ${fiscalYearFilterSubmitDate}`,
	},

	MDF_REQUEST_LISTING: {
		channels: `mdfRequestStatus ne 'draft' and ${previousToCurrentYearFilterDateCreated}`,
		channelsClosed: `mdfRequestStatus ne 'draft' and ${mdfRequestStageClosed} and ${previousToCurrentYearFilterDateCreated}`,
		channelsOpen: `mdfRequestStatus ne 'draft' and ${mdfRequestStageOpen} and ${previousToCurrentYearFilterDateCreated}`,
		partners: `${fiscalYearFilterDateCreated}`,
		partnersClosed: `${mdfRequestStageClosed} and ${fiscalYearFilterDateCreated}`,
		partnersOpen: `${mdfRequestStageOpen} and ${fiscalYearFilterDateCreated}`,
	},

	OPPORTUNITY_LISTING: {
		// closed: `(stage eq 'Closed Won' or stage eq 'Closed Lost' or stage eq 'Disqualified' or stage eq 'Rejected') and (type eq 'New Business' or type eq 'New Project Existing Business' or (type eq 'Existing Business' and hasRenewal eq false)) and ${fiscalYearFilterCloseDate}`,

		closedWIP: `${opportunityStageClosed} and ${fiscalYearFilterCloseDate}`,

		// open: `(stage ne 'Closed Won' and stage ne 'Closed Lost' and stage ne 'Disqualified' and stage ne 'Rejected' and stage ne 'Rolled into opportunity') and ((type eq 'New Business' or type eq 'New Project Existing Business') or (type eq 'Existing Business' and hasRenewal eq false and growthArr gt 0))`,

		openWIP: `${opportunityStageOpen}`,
	},
	RENEWAL_DASHBOARD: {
		renewals: `${opportunityStageOpen} and type eq 'Existing Business' and closeDate le ${thirtyDaysFromToday}`,
	},
	RENEWAL_LISTING: {
		// closed: `stage eq 'Closed Won' or stage eq 'Closed Lost' and stage ne 'Rolled into opportunity'  and type eq 'Existing Business' and hasRenewal eq true and ${fiscalYearFilterCloseDate}`,

		closedWIP: `${opportunityStageClosed} and type eq 'Existing Business' and ${fiscalYearFilterCloseDate}`,

		// open: `stage ne 'Closed Won' and stage ne 'Closed Lost' and stage ne 'Disqualified' and stage ne 'Rejected' and stage ne 'Rolled into opportunity' and type eq 'Existing Business and hasRenewal eq true`,

		openWIP: `${opportunityStageOpen} and type eq 'Existing Business'`,
	},
	REVENUE_DASHBOARD: {
		opportunities: `stage eq 'Closed Won' and ${fiscalYearFilterCloseDate}`,
	},
};
