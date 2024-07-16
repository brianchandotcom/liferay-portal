/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '../../../../I18n';
import {Button} from '../../../../components';

const ConfirmationMessageModal = ({handlePage}) => {
	return (
		<div className="d-flex flex-column p-4">
			<div className="mb-4">
				<p className="h2 mb-1">
					{i18n.translate('set-up-analytics-cloud')}
				</p>

				<p className="text-paragraph-sm">
					{i18n.translate(
						'we-ll-need-a-few-details-to-finish-creating-your-analytics-cloud-workspace'
					)}
				</p>
			</div>

			<div className="mb-3">
				<p className="h5">
					{i18n.translate('thank-you-for-submitting-this-request')}
				</p>

				<p>
					{i18n.translate(
						'your-analytics-cloud-workspace-will-be-provisioned-in-1-2-business-days-an-email-will-be-sent-once-your-workspace-is-ready'
					)}
				</p>
			</div>

			<div className="d-flex justify-content-center mb-1">
				<Button onClick={() => handlePage(true)}>
					{i18n.translate('done')}
				</Button>
			</div>
		</div>
	);
};

export default ConfirmationMessageModal;
