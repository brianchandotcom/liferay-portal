/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PRMForm from '../../../../../../../../common/components/PRMForm';
import PRMFormik from '../../../../../../../../common/components/PRMFormik';
import {TacticKeys} from '../../../../../../../../common/enums/mdfRequestTactics';

interface IProps {
	currentActivityIndex: number;
	tactic: TacticKeys;
}

const MiscellaneousMarketingFields = ({
	currentActivityIndex,
	tactic,
}: IProps) => {
	const CTATactics = [
		TacticKeys.BROADCAST_ADVERTISING,
		TacticKeys.CAMPAIGN_WITH_INDUSTRY_PUBLICATION,
		TacticKeys.DIRECT_MAIL,
		TacticKeys.PRINT_ADVERTISING,
	];

	const PublicationExpectedImpressionsTactics = [
		TacticKeys.CAMPAIGN_WITH_INDUSTRY_PUBLICATION,
		TacticKeys.PRINT_ADVERTISING,
	];

	return (
		<>
			<PRMFormik.Field
				component={PRMForm.InputText}
				label="Describe the marketing Activity"
				name={`activities[${currentActivityIndex}].activityDescription.marketingActivity`}
				required
			/>

			{tactic === TacticKeys.BROADCAST_ADVERTISING && (
				<PRMFormik.Field
					component={PRMForm.InputText}
					label="Broadcast channel"
					name={`activities[${currentActivityIndex}].activityDescription.broadcastChannel`}
					required
				/>
			)}

			{PublicationExpectedImpressionsTactics.includes(tactic) && (
				<PRMFormik.Field
					component={PRMForm.InputText}
					label="Publication"
					name={`activities[${currentActivityIndex}].activityDescription.publication`}
					required
				/>
			)}

			{tactic === TacticKeys.DIRECT_MAIL && (
				<PRMFormik.Field
					component={PRMForm.InputText}
					label="Target # of sends"
					name={`activities[${currentActivityIndex}].activityDescription.targetOfSends`}
					required
				/>
			)}

			{CTATactics.includes(tactic) && (
				<PRMFormik.Field
					component={PRMForm.InputText}
					label="CTA"
					name={`activities[${currentActivityIndex}].activityDescription.cta`}
					required
				/>
			)}

			{tactic === TacticKeys.BROADCAST_ADVERTISING && (
				<>
					<PRMFormik.Field
						component={PRMForm.InputText}
						label="# of weeks/airing"
						name={`activities[${currentActivityIndex}].activityDescription.weeksAiring`}
					/>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Guaranteed Impressions"
						name={`activities[${currentActivityIndex}].activityDescription.guaranteedImpressions`}
						required
					/>
				</>
			)}

			{PublicationExpectedImpressionsTactics.includes(tactic) && (
				<PRMFormik.Field
					component={PRMForm.InputText}
					label="Expected Impressions"
					name={`activities[${currentActivityIndex}].activityDescription.expectedImpressions`}
					required
				/>
			)}

			{tactic === TacticKeys.CO_BRANDED_MERCHANDISE && (
				<>
					<PRMFormik.Field
						component={PRMForm.InputText}
						label="What type of merchandise?"
						name={`activities[${currentActivityIndex}].activityDescription.typeMerchandise`}
						required
					/>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Quantity?"
						name={`activities[${currentActivityIndex}].activityDescription.quantity`}
						required
					/>
				</>
			)}

			{tactic === TacticKeys.OUTBOUND_TELEMARKETING_SALES && (
				<>
					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Audience Target"
						name={`activities[${currentActivityIndex}].activityDescription.audienceTarget`}
						required
					/>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Source and Size of call List"
						name={`activities[${currentActivityIndex}].activityDescription.sourceAndSizeOfCallList`}
						required
					/>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Resources necessary from Liferay"
						name={`activities[${currentActivityIndex}].activityDescription.resourcesNecessaryFromLiferay`}
					/>
				</>
			)}
		</>
	);
};

export default MiscellaneousMarketingFields;
