/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import classNames from 'classnames';
import {addDays, format} from 'date-fns';
import {ReactElement} from 'react';
import {KeyedMutator} from 'swr';

import {OrderCustomFields, OrderStatus as Status} from '../../../enums/Order';
import i18n from '../../../i18n';
import HeadlessTrialExtensionRequest from '../../../services/rest/HeadlessTrialExtensionRequest';
import {TRIAL_STATUS_LABEL} from '../constants';
import {ExtendRequestStatus} from '../enums/SSATrials';
import trialOAuth2 from '../../../services/oauth/Trial';

type ExtendSSATrialModalProps = {
	onClose: () => void;
	order: PlacedOrder;
	ssaTrialExtendMutate: KeyedMutator<any>;
	trialExtend: TrialExtend;
	trialExtendCount: number;
};

type DetailsProps = {
	children?: ReactElement | string;
	title: string;
};

const Details: React.FC<DetailsProps> = ({children, title}) => (
	<div className="d-flex flex-column mb-4">
		<p className="font-weight-bold m-0 text-black-50">{title}</p>
		<div className="d-inline-flex">{children}</div>
	</div>
);

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
			<div className="d-flex flex-column mb-9 provisioning-details">
				<div className="align-items-center d-flex justify-content-between">
					<span className="font-weight-bold text-primary">
						{i18n.translate('extension-request').toUpperCase()}
					</span>

					<span>
						<ClayButtonWithIcon
							aria-label="Close"
							borderless
							className="text-dark"
							onClick={onClose}
							symbol="times"
							title="Close"
						/>
					</span>
				</div>

				<div className="d-flex flex-column justify-content-start">
					<h2 className="m-0 text-weight-bold">
						{JSON.parse(
							order.customFields[OrderCustomFields.TRIAL_SETTINGS]
						).projectId ?? 'N/A'}
					</h2>
					<p>{order.orderTypeExternalReferenceCode}</p>
				</div>

				<div className="d-flex flex-row mt-5">
					<div className="col-6 p-0">
						<p className="font-weight-bold">
							{i18n.translate('details')}
						</p>

						<Details title={i18n.translate('start-date')}>
							<span className="extend-request-info">
								{format(
									new Date(order.createDate),
									'dd MMM, yyyy'
								).toString()}
							</span>
						</Details>

						<Details title={i18n.translate('expiration-date')}>
							<span className="extend-request-info">
								{trialSettings[OrderCustomFields.END_DATE]
									? format(
											new Date(
												trialSettings[
													OrderCustomFields.END_DATE
												]
											),
											'dd MMM, yyyy'
										).toString()
									: 'DNE'}
							</span>
						</Details>

						<Details title={i18n.translate('status')}>
							<span
								className={classNames('extension-status', {
									'extension-status-approved': [
										Status.IN_PROGRESS,
										Status.PENDING,
									].includes(
										order.orderStatusInfo.label as Status
									),
									'extension-status-expired': [
										Status.COMPLETED,
										Status.APPROVED,
									].includes(
										order.orderStatusInfo.label as Status
									),
									'extension-status-pending':
										order.orderStatusInfo.label ===
										Status.PROCESSING,
								})}
							>
								{
									TRIAL_STATUS_LABEL[
										order.orderStatusInfo
											.label as keyof typeof TRIAL_STATUS_LABEL
									]
								}
							</span>
						</Details>
					</div>

					<div className="col-6 p-0">
						<p className="font-weight-bold">
							{i18n.translate('extension')}
						</p>

						<Details
							title={i18n.translate('times-already-extended')}
						>
							<span className="extend-request-info">
								{trialExtendCount.toString()}
							</span>
						</Details>

						<Details
							title={i18n.translate('duration-of-the-extension')}
						>
							<span className="extend-request-info">
								{trialExtend.duration.toString()}
							</span>
						</Details>

						<Details
							title={i18n.translate(
								'new-potential-expiration-date'
							)}
						>
							<span className="extend-request-info">
								{format(
									addDays(
										new Date(order.createDate),
										trialExtend.duration
									),
									'dd MMM, yyyy'
								).toString()}
							</span>
						</Details>
					</div>
				</div>
				<div className="d-flex flex-row mb-7">
					<div className="d-flex flex-column flex-grow-1 p-0">
						<p className="font-weight-bold m-0 text-black-50">
							{i18n.translate('reason')}
						</p>
						<p className="extend-request-info extend-request-reason">
							{trialExtend.reason}
						</p>
					</div>
				</div>
			</div>
			<div className="d-flex justify-content-end pt-8">
				<ClayButton
					className="mr-4"
					displayType="secondary"
					onClick={async () => {
						await HeadlessTrialExtensionRequest.updateTrialExtensionRequest(
							trialExtend.id,
							{dueStatus: {key: ExtendRequestStatus.REJECTED}}
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
					onClick={async () => {
						await HeadlessTrialExtensionRequest.updateTrialExtensionRequest(
							trialExtend.id,
							{dueStatus: {key: ExtendRequestStatus.APPROVED}}
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

						await trialOAuth2.extendTrial(trialExtend.id);

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
