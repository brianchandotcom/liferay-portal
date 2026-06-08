/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {ConnectedProps, connect} from 'react-redux';
import {addAlert} from '~/shared/actions/alerts';
import HubspotForm from '~/shared/components/HubspotForm';
import Modal from '~/shared/components/modal';
import {Alert} from '~/shared/types';

const FORM_ID = '32cf039a-7a47-4461-82c5-e694d9f29057';
const PORTAL_ID = '252686';

const connector = connect(null, {addAlert});

type PropsFromRedux = ConnectedProps<typeof connector>;

interface IContactSalesModalProps
	extends React.HTMLAttributes<HTMLElement>,
		PropsFromRedux {
	onClose: () => void;
}

const ContactSalesModal: React.FC<IContactSalesModalProps> = ({
	addAlert,
	onClose,
}) => (
	<Modal>
		<Modal.Header
			onClose={onClose}
			title={Liferay.Language.get('contact-sales')}
		/>

		<Modal.Body>
			<HubspotForm
				css=""
				cssClass="hs-form-container"
				cssRequired=""
				formId={FORM_ID}
				onFormSubmitted={() => {
					addAlert({
						alertType: Alert.Types.Success,
						message: Liferay.Language.get('success'),
					});

					onClose();
				}}
				portalId={PORTAL_ID}
				submitButtonClass="btn btn-block btn-primary"
			/>
		</Modal.Body>
	</Modal>
);

export default connector(ContactSalesModal);
