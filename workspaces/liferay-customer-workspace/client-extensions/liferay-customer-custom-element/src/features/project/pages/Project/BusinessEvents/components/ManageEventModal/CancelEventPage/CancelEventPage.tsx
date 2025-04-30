/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloClient} from '@apollo/client/core/ApolloClient';
import {ClayInput} from '@clayui/form';
import {Observer} from '@clayui/modal/lib/types';
import {useState} from 'react';
import {Badge} from '~/components';
import {Liferay} from '~/services/liferay';
import {updateBusinessEvent} from '~/services/liferay/graphql/queries';
import i18n from '~/utils/I18n';
import {IBusinessEvent} from '~/utils/types';

import useAccountsSyncBusinessEvents from '../../../hooks/useAccountsSyncBusinessEvents';
import BusinessEventsModal from '../../BusinessEventsModal/BusinessEventsModal';

interface IProps {
	accountExternalReferenceCode: string;
	businessEvent: IBusinessEvent;
	client: ApolloClient<any>;
	closeFunction?: (value: boolean) => void;
	modalType: string;
	observer: Observer;
	onCancel: () => void;
}

const CancelEventPage: React.FC<IProps> = ({
	accountExternalReferenceCode,
	businessEvent,
	client,
	closeFunction = () => {},
	modalType,
	observer,
	onCancel,
}) => {
	const [reason, setReason] = useState('');
	const [isLoadingSubmitButton, setIsLoadingSubmitButton] =
		useState<boolean>(false);

	const {updateAccountBusinessEvents} = useAccountsSyncBusinessEvents(
		accountExternalReferenceCode,
		businessEvent,
		false,
		true
	);

	const handleSubmit = async () => {
		const updatedBusinessEvent = {...businessEvent};

		const businessEventId = updatedBusinessEvent.id;

		const formattedBusinessEvent = {
			eventStatus: 'canceled',
			lastComment: reason,
			r_accountEntryToBusinessEvents_accountEntryId:
				updatedBusinessEvent.r_accountEntryToBusinessEvents_accountEntryId,
			targetGoLiveDateTime: updatedBusinessEvent.targetGoLiveDateTime,
		};

		try {
			setIsLoadingSubmitButton(true);

			await updateAccountBusinessEvents();

			await client.mutate<{
				updateBusinessEvent: IBusinessEvent;
			}>({
				context: {
					displaySuccess: false,
					type: 'liferay-rest',
				},
				mutation: updateBusinessEvent,
				variables: {
					businessEvent: formattedBusinessEvent,
					businessEventId,
				},
			});

			closeFunction(false);
			onCancel();
		}
		catch (error) {
			setIsLoadingSubmitButton(false);

			Liferay.Util.openToast({
				message: i18n.translate('an-unexpected-error-occurred'),
				type: 'danger',
			});
			console.error('Error canceling business event', error);
		}
	};

	const handleInputChange = (event: {target: {value: string}}) => {
		setReason(event.target.value);
	};

	return (
		<BusinessEventsModal
			handleSubmit={handleSubmit}
			headerTitle={i18n.translate('cancel-business-event')}
			isLoadingSubmitButton={isLoadingSubmitButton}
			modalType={modalType}
			observer={observer}
			onClose={() => closeFunction(false)}
			reason={reason}
			submitButton={i18n.translate('cancel-business-event')}
			title={`${i18n.translate('cancel')} ${businessEvent.name}`}
		>
			<div>
				<div className="font-weight-bold mb-3">
					{i18n.translate(
						'please-let-us-know-the-reason-you-are-canceling-this-event'
					)}

					<span className="edit-modal-asterisk"> *</span>
				</div>

				<ClayInput
					component="textarea"
					onChange={handleInputChange}
					required
					type="text"
					value={reason}
				/>

				<Badge alertType="info" badgeClassName="mt-3">
					<span className="pl-1 text-paragraph">
						{i18n.translate(
							'once-canceled-no-further-edits-can-be-made-to-this-event'
						)}
					</span>
				</Badge>
			</div>
		</BusinessEventsModal>
	);
};

export default CancelEventPage;
