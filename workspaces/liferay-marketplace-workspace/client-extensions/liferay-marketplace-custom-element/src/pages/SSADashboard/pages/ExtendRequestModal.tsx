/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import {addDays, format} from 'date-fns';
import {KeyedMutator} from 'swr';

import BaseWrapper from '../../../components/Form/BaseWrapper';
import {OrderCustomFields} from '../../../enums/Order';
import i18n from '../../../i18n';
import HeadlessSSATrialsExtend from '../../../services/rest/HeadlessSSATrialsExtend';
import {ExtendRequestStatus} from '../enums/SSATrials';

type ExtendSSATrialModalProps = {
	onClose: () => void;
	order: PlacedOrder;
	ssaTrialExtendMutate: KeyedMutator<any>;
	trialExtend: TrialExtend;
	trialExtendCount: number;
};

const ExtendRequestModal: React.FC<ExtendSSATrialModalProps> = ({
	onClose,
	order,
	ssaTrialExtendMutate,
	trialExtend,
	trialExtendCount,
}) => {
	const trialSettings = JSON.parse(
		order?.customFields[OrderCustomFields.TRIAL_SETTINGS]
	);

	return (
		<div>
			<div className="d-flex justify-content-between">
				<div>
					<h3>{i18n.translate('details')}</h3>
					<BaseWrapper label={i18n.translate('project-id')} required>
						<ClayInput
							disabled
							value={trialSettings.projectId ?? 'PLACEHOLDER'}
						/>
					</BaseWrapper>
					<BaseWrapper label={i18n.translate('start-date')} required>
						<ClayInput
							disabled
							value={format(
								new Date(order.createDate),
								'dd MMM, yyyy'
							).toString()}
						/>
					</BaseWrapper>
					<BaseWrapper
						label={i18n.translate('expiration-date')}
						required
					>
						<ClayInput
							disabled
							value={
								trialSettings[OrderCustomFields.END_DATE]
									? format(
											new Date(
												trialSettings[
													OrderCustomFields.END_DATE
												]
											),
											'dd MMM, yyyy'
										).toString()
									: 'DNE'
							}
						/>
					</BaseWrapper>
				</div>
				<div>
					<h3>{i18n.translate('extension')}</h3>
					<BaseWrapper
						label={i18n.translate('times-already-extended')}
						required
					>
						<ClayInput disabled value={trialExtendCount} />
					</BaseWrapper>
					<BaseWrapper
						label={i18n.translate('duration-of-the-extension')}
						required
					>
						<ClayInput disabled value={trialExtend.duration} />
					</BaseWrapper>
					<BaseWrapper
						label={i18n.translate('new-potential-expiration-date')}
						required
					>
						<ClayInput
							disabled
							value={format(
								addDays(
									new Date(order.createDate),
									trialExtend.duration
								),
								'dd MMM, yyyy'
							).toString()}
						/>
					</BaseWrapper>
				</div>
			</div>
			<div className="d-flex justify-content-end pt-8">
				<ClayButton
					className="mr-4"
					displayType="secondary"
					onClick={() => {
						HeadlessSSATrialsExtend.updateSSATrialsExtend(
							trialExtend.id,
							{statusRequest: {key: ExtendRequestStatus.REJECTED}}
						);
						ssaTrialExtendMutate(
							(data: any) => {
								const updatedItems = data.items.map(
									(x: TrialExtend) => {
										if (x.id === trialExtend.id) {
											const newObject = {
												...x,
												statusRequest: {
													key: ExtendRequestStatus.REJECTED,
												},
											};

											return newObject;
										}

										return {...x};
									}
								);

								return {
									...data,
									items: updatedItems,
								};
							},
							{revalidate: false}
						);
						onClose();
					}}
				>
					{i18n.translate('reject-request')}
				</ClayButton>
				<ClayButton
					onClick={() => {
						HeadlessSSATrialsExtend.updateSSATrialsExtend(
							trialExtend.id,
							{statusRequest: {key: ExtendRequestStatus.APPROVED}}
						);

						ssaTrialExtendMutate(
							(data: any) => {
								const updatedItems = data.items.map(
									(x: TrialExtend) => {
										if (x.id === trialExtend.id) {
											const newObject = {
												...x,
												statusRequest: {
													key: ExtendRequestStatus.APPROVED,
												},
											};

											return newObject;
										}

										return {...x};
									}
								);

								return {
									...data,
									items: updatedItems,
								};
							},
							{revalidate: false}
						);

						onClose();
					}}
				>
					{i18n.translate('approve-request')}
				</ClayButton>
			</div>
		</div>
	);
};

export default ExtendRequestModal;
