/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloClient} from '@apollo/client/core/ApolloClient';
import ClayIcon from '@clayui/icon';
import {useEffect, useState} from 'react';
import {Badge, Button, Input} from '~/components';
import {Liferay} from '~/services/liferay';
import {updateBusinessEvent} from '~/services/liferay/graphql/queries';
import i18n from '~/utils/I18n';
import {IBusinessEvent} from '~/utils/types';

import Layout from '../../../../../../../../components/FormLayout';

interface IProps {
	businessEvent: IBusinessEvent;
	client: ApolloClient<any>;
	closeFunction?: (value: boolean) => void;
	errors?: Record<string, any>;
	onCancel: () => void;
	touched?: any;
	values?: any;
}

const CancelEventPage: React.FC<IProps> = ({
	businessEvent,
	client,
	closeFunction = () => {},
	errors,
	onCancel,
	touched,
	values,
}) => {
	const [baseButtonDisabled, setBaseButtonDisabled] = useState<boolean>(true);

	const [isLoadingSubmitButton, setIsLoadingSubmitButton] =
		useState<boolean>(false);

	const handleSubmit = async () => {
		const businessEventId = businessEvent.id;

		const updatedBusinessEvent = {eventStatus: 'canceled'};

		try {
			setIsLoadingSubmitButton(true);

			await client.mutate<{
				updateBusinessEvent: IBusinessEvent;
			}>({
				context: {
					displaySuccess: false,
					type: 'liferay-rest',
				},
				mutation: updateBusinessEvent,
				variables: {
					businessEvent: updatedBusinessEvent,
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

	useEffect(() => {
		const hasBusinessEvent = businessEvent;
		const hasComment = values.comment;
		const hasError = errors && Object.keys(errors).length;
		const hasTouched = !!Object.keys(touched).length;

		const areAllRequiredFieldsFilled = !!(hasBusinessEvent && hasComment);

		setBaseButtonDisabled(
			!hasTouched || !!hasError || !areAllRequiredFieldsFilled
		);
	}, [businessEvent, errors, touched, values.comment]);

	return (
		<Layout
			footerProps={{
				leftButton: (
					<Button
						displayType="secondary"
						onClick={() => {
							closeFunction(false);
						}}
					>
						{i18n.translate('cancel')}
					</Button>
				),
				middleButton: (
					<Button
						className="bg-danger"
						disabled={baseButtonDisabled || isLoadingSubmitButton}
						isLoading={isLoadingSubmitButton}
						onClick={handleSubmit}
					>
						{i18n.translate('cancel-business-event')}
					</Button>
				),
			}}
			headerProps={{
				button: (
					<Button
						aria-label={i18n.translate('close')}
						borderless
						className="text-neutral-5"
						onClick={() => closeFunction(false)}
						size="xs"
					>
						<span>
							<ClayIcon symbol="times" />
						</span>
					</Button>
				),
				greetings: i18n.translate('cancel-business-event'),
				title: `${i18n.translate('cancel')} ${businessEvent.name}`,
			}}
			layoutType="match-parent"
		>
			<>
				<Input
					badgeClassName="ml-3 mr-3"
					component="textarea"
					groupStyle="pb-1"
					label={i18n.translate(
						'please-let-us-know-the-reason-you-are-canceling-this-event'
					)}
					name="comment"
					required
					type="text"
				/>

				<Badge alertType="info" badgeClassName="ml-3 mr-3">
					<span className="pl-1">
						{i18n.translate(
							'once-canceled-no-further-edits-can-be-made-to-this-event'
						)}
					</span>
				</Badge>
			</>
		</Layout>
	);
};

export default CancelEventPage;
