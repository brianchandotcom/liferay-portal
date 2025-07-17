/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import ClayButton from '@clayui/button';
import {zodResolver} from '@hookform/resolvers/zod';
import classNames from 'classnames';
import {useState} from 'react';
import {useForm} from 'react-hook-form';
import zodSchema, {z} from '../../../schema/zod';
import {Liferay} from '../../../liferay/liferay';
import i18n from '../../../i18n';
import BaseWrapper from '../../../components/Form/BaseWrapper';
import ClayAlert from '@clayui/alert';
import {OrderCustomFields} from '../../../enums/Order';
import {TrialSettings} from '../enums/SSATrials';
import {SSASettings} from '../types';
import {EXTEND_OPTIONS} from '../constants';

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
			console.log(form.duration);
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
					onChange={(e) => {
						setDuration(
							Number(e.target.value) < 1
								? undefined
								: Number(e.target.value)
						);
						setValue('duration', Number(e.target.value));
						trigger();
					}}
					min={1}
					max={60}
					placeholder="Value between 1 and 60"
					value={duration}
					type="number"
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
