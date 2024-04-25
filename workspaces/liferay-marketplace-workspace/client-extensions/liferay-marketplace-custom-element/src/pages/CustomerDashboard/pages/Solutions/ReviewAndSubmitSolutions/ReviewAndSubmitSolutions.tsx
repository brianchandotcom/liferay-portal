/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';

import {Section} from '../../../../../components/Section/Section';
import {CardSectionsBody} from '../../../../PublisherDashboard/pages/Apps/AppCreationFlow/ReviewAndSubmitAppPage/CardSectionsBody';
import {App} from '../../../../PublisherDashboard/pages/Apps/AppCreationFlow/ReviewAndSubmitAppPage/ReviewAndSubmitAppPageUtil';
import {Solution} from './SolutionsDetails';

type ReviewAndSubmitSolutions = {
	loading?: boolean;
	readonly?: boolean;
	solution?: Solution;
};

export function ReviewAndSubmitSolutions({
	loading,
	readonly = true,
	solution,
}: ReviewAndSubmitSolutions) {
	return (
		<div className="review-and-submit-solution-page-container">
			<Section
				disabled={readonly}
				label={readonly ? '' : 'App Submission'}
				tooltip={readonly ? '' : 'More info'}
				tooltipText={readonly ? '' : 'More Info'}
			>
				<div className="review-and-submit-solution-page-card-container">
					<div className="border px-6 review-and-submit-solution-page-card-body rounded">
						{loading ? (
							<ClayLoadingIndicator
								displayType="primary"
								shape="circle"
								size="md"
							/>
						) : (
							<CardSectionsBody
								app={solution as App}
								isApp={false}
								readonly={readonly}
							/>
						)}
					</div>
				</div>
			</Section>
		</div>
	);
}
