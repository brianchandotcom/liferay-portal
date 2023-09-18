/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default interface MDFRequestActivityDescription {
	activityPromotion?: string;
	ad?: string;
	assetsLiferayDescription?: string;
	assetsLiferayRequired?: string;
	audienceTarget?: string;
	broadcastChannel?: string;
	creator?: Date;
	cta?: string;
	description?: string;
	detailsLeadFollowUp?: string;
	expectedImpressions?: string;
	externalReferenceCode?: string;
	gatedLandingPage?: string;
	goalOfContent?: string;
	guaranteedImpressions?: string;
	hiringOutsideWriterOrAgency?: string;
	howLiferayBrandUsed?: string;
	keywordsForPPCCampaigns?: string;
	landingPageCopy?: string;
	leadFollowUpStrategies?: string[];
	leadGenerated?: string;
	liferayBranding?: string;
	liferayParticipationRequirements?: string;
	location?: string;
	manySeries?: string;
	marketingActivity?: string;
	nurtureDripCampaign?: string;
	overallMessageContentCTA?: string;
	primaryThemeOrMessage?: string;
	publication?: string;
	quantity?: string;
	resourcesNecessaryFromLiferay?: string;
	sourceAndSizeOfCallList?: string;
	sourceAndSizeOfInviteeList?: string;
	specificSites?: string;
	status?: string;
	targetOfLeads?: string;
	targetOfSends?: string;
	typeMerchandise?: string;
	usingCIABAssets?: string;
	venueName?: string;
	webinarHostPlatform?: string;
	webinarTopic?: string;
	weeksAiring?: string;
}
