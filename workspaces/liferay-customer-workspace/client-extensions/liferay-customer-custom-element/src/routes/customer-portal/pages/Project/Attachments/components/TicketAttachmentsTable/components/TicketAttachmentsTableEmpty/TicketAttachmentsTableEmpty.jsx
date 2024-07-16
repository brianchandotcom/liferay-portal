/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import ClayEmptyState from '@clayui/empty-state';
import ClayIcon from '@clayui/icon';
import i18n from '~/common/I18n';

const TicketAttachmentsTableEmpty = ({description, title}) => {
	return (
		<div className="attachments-icon-container d-flex flex-column justify-content-center text-center">
			<div className="align-self-center attachments-icon d-flex justify-content-center py-4">
				<ClayIcon symbol="folder" />
			</div>

			<ClayEmptyState
				className="d-flex flex-column justify-content-center"
				description={
					description ||
					i18n.translate('sorry-there-are-no-results-found')
				}
				title={title || i18n.translate('no-results-found')}
			/>
		</div>
	);
};

export default TicketAttachmentsTableEmpty;
