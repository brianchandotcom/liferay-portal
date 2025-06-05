/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useNavigate} from 'react-router-dom';

import {Section} from '../../../../../components/Section/Section';
import {useNewAppContext} from '../../../../../context/NewAppContext';
import {ProductWorkflowStatusCode} from '../../../../../enums/Product';
import i18n from '../../../../../i18n';
import AppReview from '../../../components/AppReview/AppReview';

const Submit = ({
	hideAppProfile = false,
	readonly = false,
}: {
	hideAppProfile?: boolean;
	readonly?: boolean;
}) => {
	const [context] = useNewAppContext();
	const navigate = useNavigate();
	const isEditingApp =
		context?._product &&
		context._product.productStatus === ProductWorkflowStatusCode.APPROVED;

	return (
		<div className="app-review-container">
			<Section
				disabled
				label={i18n.translate('app-submission')}
				required
				tooltip={i18n.translate('more-info')}
				tooltipText={i18n.translate('more-info')}
			>
				<hr />
			</Section>

			<div className="border p-5 rounded-lg">
				{!hideAppProfile && (
					<>
						<AppReview.Profile context={context} readonly />
						<hr />
					</>
				)}
				<AppReview.Description
					context={context}
					editNavigate={() => navigate('../profile')}
					readonly={readonly}
				/>
				<AppReview.Categories
					context={context}
					editNavigate={() => navigate('../profile')}
					readonly={readonly}
				/>
				{!isEditingApp && (
					<AppReview.Build
						context={context}
						editNavigate={() => navigate('../build')}
						readonly={readonly}
					/>
				)}
				<AppReview.Pricing
					context={context}
					editNavigate={() => navigate('../pricing')}
					readonly={readonly}
				/>
				<AppReview.Licensing
					context={context}
					editNavigate={() => navigate('../licensing')}
					readonly={readonly}
				/>
				<AppReview.Storefront
					context={context}
					editNavigate={() => navigate('../storefront')}
					readonly={readonly}
				/>
				<AppReview.Support
					context={context}
					editNavigate={() => navigate('../support')}
					isLastSection
					readonly={readonly}
				/>
			</div>
		</div>
	);
};

export default Submit;
