/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PRMForm from '../../../../../../../../common/components/PRMForm';
import PRMFormik from '../../../../../../../../common/components/PRMFormik';
import getBooleanEntries from '../../../../../../../../common/utils/getBooleanEntries';

interface IProps {
	currentActivityIndex: number;
}

const ContentMarketingFields = ({currentActivityIndex}: IProps) => {
	return (
		<>
			<PRMFormik.Field
				component={PRMForm.RadioGroup}
				items={getBooleanEntries()}
				label="Will this content be gated and have a landing page?"
				name={`activities[${currentActivityIndex}].activityDescription.gatedLandingPage`}
				required
				small
			/>

			<PRMFormik.Field
				component={PRMForm.InputText}
				label="Describe the primary theme or message of your content"
				name={`activities[${currentActivityIndex}].activityDescription.primaryThemeOrMessage`}
				required
			/>

			<PRMFormik.Field
				component={PRMForm.InputText}
				label="Goal of Content"
				name={`activities[${currentActivityIndex}].activityDescription.goalOfContent`}
				required
			/>

			<PRMFormik.Field
				component={PRMForm.RadioGroup}
				items={getBooleanEntries()}
				label="Are you hiring an outside writer or agency to prepare the content?"
				name={`activities[${currentActivityIndex}].activityDescription.hiringOutsideWriterOrAgency`}
				required
				small
			/>
		</>
	);
};

export default ContentMarketingFields;
