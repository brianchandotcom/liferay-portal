/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import {zodResolver} from '@hookform/resolvers/zod';
import classNames from 'classnames';
import {useState} from 'react';
import {useForm} from 'react-hook-form';
import {KeyedMutator} from 'swr';

import BaseWrapper from '../../../components/Form/BaseWrapper';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import zodSchema, {z} from '../../../schema/zod';
import HeadlessTrialExtensionRequest from '../../../services/rest/HeadlessTrialExtensionRequest';
import {EXTEND_OPTIONS, EXTEND_TYPES} from '../constants';
import {ExtendRequestStatus} from '../enums/SSATrials';
import trialOAuth2 from '../../../services/oauth/Trial';

type ExtendSSATrialModalProps = {
	accountId: number;
	firstExtendRequest: boolean;
	onClose: () => void;
	order: PlacedOrder;
	ssaTrialExtendMutate: KeyedMutator<any>;
};

const ExtendSSATrialModal: React.FC<ExtendSSATrialModalProps> = ({
	accountId,
	firstExtendRequest,
	onClose,
	order,
	ssaTrialExtendMutate,
}) => {
	const {formState, handleSubmit, setValue, trigger} = useForm({
		defaultValues: {
			duration: 0,
			reason: '',
		},
		mode: 'all',
		reValidateMode: 'onChange',
		resolver: zodResolver(zodSchema.extendSSATrial),
	});

	const {isLoading, isValid} = formState;

	const extendType = firstExtendRequest
		? EXTEND_TYPES.AUTO_EXTEND
		: EXTEND_TYPES.ADMIN_REQUEST;

	const extendOptions = EXTEND_OPTIONS.find(
		(option) => option.extendType === extendType
	);

	const [duration, setDuration] = useState<number | undefined>(undefined);
	const [reason, setReason] = useState<string>('');

	const onSubmit = async (form: z.infer<typeof zodSchema.extendSSATrial>) => {
		try {
			const extendTrial = {
				duration: form.duration,
				r_accountToTrialExtensionRequest_accountEntryId: accountId,
				r_orderToTrialExtensionRequest_commerceOrderId: order.id,
				reason: form.reason,
				dueStatus: {
					key:
						extendType === EXTEND_TYPES.AUTO_EXTEND
							? ExtendRequestStatus.AUTO_APPROVED
							: ExtendRequestStatus.PENDING,
				},
			};

			const newExtensionRequest: TrialExtend =
				await HeadlessTrialExtensionRequest.createTrialExtensionRequest(
					extendTrial
				);

			if (extendType === EXTEND_TYPES.AUTO_EXTEND) {
				try {
					await trialOAuth2.extendTrial(newExtensionRequest.id);
				}
				catch (e) {
					console.log(e);
				}
			}

			ssaTrialExtendMutate(
				(data: any) => {
					return {
						...data,
						items: [newExtensionRequest, ...data.items],
					};
				},
				{revalidate: false}
			);

			onClose();
		}
		catch (error) {
			console.error(error);

			Liferay.Util.openToast({
				message: i18n.translate('an-unexpected-error-occurred'),
				type: 'danger',
			});
		}
	};

	return (
		<div>
			<ClayAlert displayType={extendOptions?.alertType}>
				{extendOptions?.alertText}
			</ClayAlert>
			<BaseWrapper label="Duration (days)" required>
				<ClayInput
					className={classNames('my-4', {
						'has-error': formState.errors.duration,
					})}
					max={60}
					min={1}
					onChange={(event) => {
						setDuration(
							Number(event.target.value) < 1
								? undefined
								: Number(event.target.value)
						);
						setValue('duration', Number(event.target.value));
						trigger();
					}}
					placeholder="Value between 1 and 60"
					type="number"
					value={duration}
				></ClayInput>
			</BaseWrapper>
			<BaseWrapper label="Reason" required>
				<ClayInput
					className={classNames('my-4', {
						'has-error': formState.errors.reason,
					})}
					onChange={(event) => {
						setReason(event.target.value);
						setValue('reason', event.target.value);
						trigger();
					}}
					type="text"
					value={reason}
				></ClayInput>
			</BaseWrapper>
			<div className="d-flex justify-content-end">
				<ClayButton
					className="mr-4"
					displayType="secondary"
					onClick={onClose}
				>
					{i18n.translate('cancel')}
				</ClayButton>
				<ClayButton
					disabled={!isValid || isLoading}
					onClick={handleSubmit(onSubmit)}
				>
					{extendOptions?.actionText}
				</ClayButton>
			</div>
		</div>
	);
};

export default ExtendSSATrialModal;
