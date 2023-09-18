/* eslint-disable react-hooks/exhaustive-deps */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useFormikContext} from 'formik';

import PRMForm from '../../../../../../../../common/components/PRMForm';
import PRMFormik from '../../../../../../../../common/components/PRMFormik';
import {TacticKeys} from '../../../../../../../../common/enums/mdfRequestTactics';
import MDFRequest from '../../../../../../../../common/interfaces/mdfRequest';
import getBooleanEntries from '../../../../../../../../common/utils/getBooleanEntries';

interface IProps {
	currentActivityIndex: number;
	tactic?: TacticKeys;
}

const DigitalMarketingFields = ({currentActivityIndex, tactic}: IProps) => {
	const {setFieldValue, values} = useFormikContext<MDFRequest>();

	const onAssetsLiferaySelected = (
		event: React.ChangeEvent<HTMLInputElement>
	) => {
		const assetsLiferayRequiredValue = event.target.value;

		setFieldValue(
			`activities[${currentActivityIndex}].activityDescription.assetsLiferayRequired`,
			assetsLiferayRequiredValue
		);

		if (assetsLiferayRequiredValue) {
			setFieldValue(
				`activities[${currentActivityIndex}].activityDescription.assetsLiferayDescription`,
				''
			);
		}
	};
	const onNurtureDripCampaignSelected = (
		event: React.ChangeEvent<HTMLInputElement>
	) => {
		const nurtureDripCampaignValue = event.target.value;

		setFieldValue(
			`activities[${currentActivityIndex}].activityDescription.nurtureDripCampaign`,
			nurtureDripCampaignValue
		);

		if (nurtureDripCampaignValue) {
			setFieldValue(
				`activities[${currentActivityIndex}].activityDescription.manySeries`,
				''
			);
		}
	};

	return (
		<>
			<PRMFormik.Field
				component={PRMForm.InputText}
				label="Overall message/content/CTA"
				name={`activities[${currentActivityIndex}].activityDescription.overallMessageContentCTA`}
				required
			/>

			{tactic === TacticKeys.EMAIL_CAMPAIGN ? (
				<>
					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Landing page copy"
						name={`activities[${currentActivityIndex}].activityDescription.landingPageCopy`}
						required
					/>

					<PRMFormik.Field
						component={PRMForm.RadioGroup}
						items={getBooleanEntries()}
						label="Nurture or drip campaign?"
						name={`activities[${currentActivityIndex}].activityDescription.nurtureDripCampaign`}
						onChange={onNurtureDripCampaignSelected}
						required
						small
					/>

					<PRMFormik.Field
						component={PRMForm.RadioGroup}
						items={getBooleanEntries()}
						label="Are you using any CIAB assets?"
						name={`activities[${currentActivityIndex}].activityDescription.usingCIABAssets`}
						required
						small
					/>

					{values.activities[currentActivityIndex].activityDescription
						?.nurtureDripCampaign === 'true' && (
						<PRMFormik.Field
							component={PRMForm.InputText}
							label="How many in series?"
							name={`activities[${currentActivityIndex}].activityDescription.manySeries`}
							required
						/>
					)}
				</>
			) : (
				<>
					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Any specific sites to be used"
						name={`activities[${currentActivityIndex}].activityDescription.specificSites`}
						required
					/>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Keywords for PPC campaigns (must be approved by Liferay prior to execution)"
						name={`activities[${currentActivityIndex}].activityDescription.keywordsForPPCCampaigns`}
					/>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Ad (any size/type)"
						name={`activities[${currentActivityIndex}].activityDescription.ad`}
					/>
				</>
			)}

			<PRMFormik.Field
				component={PRMForm.RadioGroup}
				items={getBooleanEntries()}
				label="Do you require any assets from Liferay?"
				name={`activities[${currentActivityIndex}].activityDescription.assetsLiferayRequired`}
				onChange={onAssetsLiferaySelected}
				required
				small
			/>

			{values.activities[currentActivityIndex].activityDescription
				?.assetsLiferayRequired === 'true' && (
				<PRMFormik.Field
					component={PRMForm.InputText}
					label="Please describe including specifications and due dates"
					name={`activities[${currentActivityIndex}].activityDescription.assetsLiferayDescription`}
					required
				/>
			)}

			<PRMFormik.Field
				component={PRMForm.InputText}
				label="How will the Liferay brand be used in the campaign?"
				name={`activities[${currentActivityIndex}].activityDescription.howLiferayBrandUsed`}
				required
			/>
		</>
	);
};

export default DigitalMarketingFields;
