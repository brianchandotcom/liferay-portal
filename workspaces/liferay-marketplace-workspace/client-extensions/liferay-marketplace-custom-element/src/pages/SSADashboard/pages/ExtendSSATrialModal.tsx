/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import {zodResolver} from '@hookform/resolvers/zod';
import classNames from 'classnames';
import {useState} from 'react';
import {useForm} from 'react-hook-form';

import BaseWrapper from '../../../components/Form/BaseWrapper';
import {OrderCustomFields} from '../../../enums/Order';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import zodSchema, {z} from '../../../schema/zod';
import trialOAuth2 from '../../../services/oauth/Trial';
import {EXTEND_OPTIONS} from '../constants';
import {TrialSettings} from '../enums/SSATrials';
import {SSASettings} from '../types';

type ExtendSSATrialModalProps = {
	onClose: () => void;
	order: PlacedOrder;
};

const ExtendSSATrialModal: React.FC<ExtendSSATrialModalProps> = ({
	onClose,
	order,
}) => {
	const {formState, handleSubmit, setValue, trigger} = useForm({
		defaultValues: {
			duration: 0,
		},
		mode: 'all',
		reValidateMode: 'onChange',
		resolver: zodResolver(zodSchema.extendSSATrial),
	});

	const {isValid} = formState;

	const trialSettings = JSON.parse(
		order?.customFields[OrderCustomFields.TRIAL_SETTINGS]
	)[TrialSettings.SSA_SETTINGS] as SSASettings;

	const extendType = trialSettings?.autoExtended
		? 'admin-request'
		: 'auto-extend';

	const extendOptions = EXTEND_OPTIONS.find(
		(option) => option.extendType === extendType
	);

	const [duration, setDuration] = useState<number | undefined>(undefined);

	const onSubmit = async (form: z.infer<typeof zodSchema.extendSSATrial>) => {
		try {
			if (extendType === 'auto-extend') {
				trialOAuth2.extendTrial(order.id, form.duration);
			}
			else {
				trialOAuth2.extendTrialRequest(order.id, form.duration);
			}
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
				<ClayForm.FeedbackItem>
					{formState.errors.duration?.message}
				</ClayForm.FeedbackItem>
			</BaseWrapper>
			<div className="d-flex justify-content-end">
				<ClayButton
					className="mr-4"
					displayType="secondary"
					onClick={onClose}
				>
					Cancel
				</ClayButton>
				<ClayButton
					disabled={!isValid}
					onClick={handleSubmit(onSubmit)}
				>
					{extendOptions?.actionText}
				</ClayButton>
			</div>
		</div>
	);
};

export default ExtendSSATrialModal;
