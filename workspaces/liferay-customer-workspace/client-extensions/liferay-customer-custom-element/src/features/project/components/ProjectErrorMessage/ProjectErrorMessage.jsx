/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import i18n from '~/utils/I18n';

import './ProjectErrorMessage.css';

const ProjectErrorMessage = () => (
	<div className="align-items-center cp-project-error-message-page d-flex flex-column justify-content-center">
		<div className="cp-project-error-message-container">
			<div className="align-items-center cp-project-error-message-alert d-flex flex-column mt-7">
				<div className="align-items-center cp-project-error-message-card-icon d-flex justify-content-center">
					<ClayIcon
						className="cp-project-error-message-icon text-primary"
						symbol="lock"
					/>
				</div>

				<p className="cp-project-error-message-title mt-4">
					{i18n.translate('you-do-not-have-access-to-this-project')}
				</p>
			</div>

			<p className="cp-project-error-message-informative mt-2 mx-9 text-center">
				{i18n.translate(
					'make-sure-the-project-link-is-correct-and-that-you-have-access-to-this-project'
				)}
			</p>
		</div>
	</div>
);

export default ProjectErrorMessage;
