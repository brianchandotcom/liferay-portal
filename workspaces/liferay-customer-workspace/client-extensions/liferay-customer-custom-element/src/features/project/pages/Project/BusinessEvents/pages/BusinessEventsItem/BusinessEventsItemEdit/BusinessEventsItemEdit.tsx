/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput, ClayRadio, ClaySelect} from '@clayui/form';

import './BusinessEventsItemEdit.css';

import {Nav} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayMultiSelect from '@clayui/multi-select';
import NavigationBar from '@clayui/navigation-bar';
import {Input as TimeInput} from '@clayui/time-picker/lib';
import {FieldArray, Formik} from 'formik';
import {useEffect, useState} from 'react';
import {Link, useNavigate, useParams} from 'react-router-dom';
import {Button, DatePicker, Select, TimePicker} from '~/components';
import {useAppPropertiesContext} from '~/contexts/AppPropertiesContext';
import {useCustomerPortal} from '~/features/project/context';
import useGetBusinessEventTypesList from '~/features/project/pages/Project/BusinessEvents/hooks/useGetBusinessEventTypesList';
import useGetGMTTimeZonesList from '~/features/project/pages/Project/BusinessEvents/hooks/useGetGMTTimeZonesList';
import useGetVersionOfLiferaySoftwareList from '~/features/project/pages/Project/BusinessEvents/hooks/useGetVersionOfLiferaySoftwareList';
import {Liferay} from '~/services/liferay';
import {getBusinessEventById} from '~/services/liferay/api';
import {updateBusinessEvent} from '~/services/liferay/graphql/queries';
import i18n from '~/utils/I18n';
import {getFormattedTime} from '~/utils/getFormattedTime';
import {IBusinessEvent} from '~/utils/types';

import useHasAllEventsPermissions from '../../../hooks/useHasAllEventsPermissions';

interface IProps {
	businessEvent: IBusinessEvent;
	errors?: Record<string, any>;
	setFieldValue: (
		field: string,
		value: any,
		shouldValidate?: boolean
	) => void;
}

const BusinessEventsItemEditPage: React.FC<IProps> = ({
	businessEvent,
	errors,
	setFieldValue,
}) => {
	const {client} = useAppPropertiesContext();

	const [{project}] = useCustomerPortal();

	const [baseButtonDisabled, setBaseButtonDisabled] = useState<boolean>(true);

	const [hasImpactingEvents, setHasImpactingEvents] = useState<string>('no');

	const handleRadioChange = (value: string) => {
		setHasImpactingEvents(value);
	};

	const navigate = useNavigate();

	const {businessEventTypesList} = useGetBusinessEventTypesList();
	const {gmtTimeZonesList} = useGetGMTTimeZonesList();
	const {hasAllEventsPermissions} = useHasAllEventsPermissions();
	const {versionOfLiferaySoftwareList} = useGetVersionOfLiferaySoftwareList();

	const handleSubmit = async () => {
		const updatedBusinessEvent = {...businessEvent};

		if (updatedBusinessEvent.targetGoLiveDate) {
			const formattedDate = getFormattedTime(
				updatedBusinessEvent.targetGoLiveDate
			);

			if (updatedBusinessEvent.targetGoLiveTime) {
				const targetGoLiveTime =
					updatedBusinessEvent.targetGoLiveTime as unknown as TimeInput;
				updatedBusinessEvent.targetGoLiveDateTime = `${formattedDate}T${targetGoLiveTime.hours}:${targetGoLiveTime.minutes}:00.000`;
			}
			else {
				updatedBusinessEvent.targetGoLiveDateTime = `${formattedDate}T00:00:00.000`;
			}
		}

		try {
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
					businessEventId: businessEvent.id,
				},
			});

			navigate(
				`/${project?.accountKey}/business-events/${businessEvent.id}`
			);

			Liferay.Util.openToast({
				message: i18n.translate('business-event-updated-successfully'),
				type: 'success',
			});
		}
		catch (error) {
			Liferay.Util.openToast({
				message: i18n.translate('an-unexpected-error-occurred'),
				type: 'danger',
			});
			console.error('Error adding business event', error);
		}
	};

	useEffect(() => {
		const hasError = errors && Object.keys(errors).length;

		setBaseButtonDisabled(!!hasError);
	}, [errors]);

	return hasAllEventsPermissions ? (
		<div>
			<div className="be-breadcrumbs font-weight-semi-bold mb-4">
				<span className="mx-2">
					<Link to={`/${project?.accountKey}/business-events/`}>
						<ClayIcon className="mr-1" symbol="order-arrow-left" />

						{i18n.translate('back-to-business-events')}
					</Link>
				</span>
			</div>

			<div>
				<div
					className={`align-items-center font-weight-semi-bold be-status be-status-${businessEvent?.eventStatus?.key} mb-1 d-inline px-2 py-1`}
				>
					{businessEvent?.eventStatus?.name}
				</div>

				<div className="align-items-center d-flex justify-content-between mb-4 mt-2">
					<div className="font-weight-bold text-neutral-10">
						<h3>{businessEvent.name}</h3>
					</div>
					<div>
						<Button
							displayType="secondary"
							onClick={() => {
								navigate(
									`/${project?.accountKey}/business-events/${businessEvent.id}`
								);
							}}
						>
							{i18n.translate('cancel')}
						</Button>
						<Button
							className="ml-3"
							disabled={baseButtonDisabled}
							displayType="primary"
							onClick={handleSubmit}
						>
							{i18n.translate('save-changes')}
						</Button>
					</div>
				</div>
			</div>

			<div className="mb-4">
				<NavigationBar
					triggerLabel={i18n.translate('activity-history')}
				>
					<Nav.Item>
						<Nav.Link
							active={true}
							aria-label={`Switch to ${i18n.translate(
								'event-details'
							)}`}
							className="be-nav-link text-neutral-10"
							onClick={() =>
								navigate(
									`/${project?.accountKey}/business-events/${businessEvent.id}`
								)
							}
						>
							{i18n.translate('event-details')}
						</Nav.Link>
					</Nav.Item>
				</NavigationBar>
			</div>
			<div className="event-edit-container">
				<FieldArray
					name="businessEvent"
					render={() => (
						<>
							<div className="event-edit-field mb-4">
								<Select
									badgeClassName="ml-3 mr-3"
									groupStyle="pb-1"
									label={i18n.translate('event-type')}
									name="businessEvent.eventType"
									onChange={(value: any) =>
										setFieldValue(
											'businessEvent.eventType',
											value
										)
									}
									options={businessEventTypesList}
									required
								/>
							</div>

							<div className="event-edit-field mb-4">
								<Select
									badgeClassName="ml-3 mr-3"
									groupStyle="pb-1"
									label={i18n.translate(
										'your-current-liferay-version'
									)}
									name="businessEvent.currentLiferayVersion"
									onChange={(value: any) =>
										setFieldValue(
											'businessEvent.currentLiferayVersion',
											value
										)
									}
									options={versionOfLiferaySoftwareList}
									required
								/>
							</div>

							<div className="event-edit-field mb-4">
								<Select
									badgeClassName="ml-3 mr-3"
									groupStyle="pb-1"
									label={i18n.translate('new-version')}
									name="businessEvent.newLiferayVersion"
									onChange={(value: any) =>
										setFieldValue(
											'businessEvent.newLiferayVersion',
											value
										)
									}
									options={versionOfLiferaySoftwareList}
									required
								/>
							</div>

							<div className="event-edit-field mb-4">
								<ClayInput.Group className="m-0">
									<ClayInput.GroupItem className="m-0">
										<DatePicker
											badgeClassName="ml-3 mr-3"
											dateFormat="MM/dd/yyyy"
											groupStyle="pb-1"
											label={i18n.translate(
												'target-go-live-date'
											)}
											name="businessEvent.targetGoLiveDate"
											onChange={(value) =>
												setFieldValue(
													'businessEvent.targetGoLiveDate',
													value
												)
											}
											placeholder={i18n.translate(
												'mm-dd-yyyy'
											)}
											required
										/>
									</ClayInput.GroupItem>

									<ClayInput.GroupItem className="m-0">
										<Select
											groupStyle="pb-1"
											id="select-businessEvent.timeZone"
											label={i18n.translate('time-zone')}
											name="businessEvent.timeZone"
											options={gmtTimeZonesList}
										/>
									</ClayInput.GroupItem>

									<ClayInput.GroupItem className="m-0">
										<TimePicker
											groupStyle="pb-1"
											label={i18n.translate('time')}
											name="businessEvent.targetGoLiveTime"
											onChange={(value) =>
												setFieldValue(
													'businessEvent.targetGoLiveTime',
													value
												)
											}
										/>
									</ClayInput.GroupItem>
								</ClayInput.Group>
							</div>

							<div className="event-edit-field mb-4">
								<div>
									{i18n.translate(
										'are-there-any-support-tickets-impacting-this-event'
									)}
								</div>
								<div>
									<ClayRadio
										checked={hasImpactingEvents === 'no'}
										label="No"
										onChange={() => handleRadioChange('no')}
										value="no"
									/>
									<ClayRadio
										checked={hasImpactingEvents === 'yes'}
										label="Yes"
										onChange={() =>
											handleRadioChange('yes')
										}
										value="yes"
									/>
								</div>
							</div>

							{hasImpactingEvents === 'yes' && (
								<div className="event-edit-field mb-4">
									<div>
										{i18n.translate(
											'please-select-the-tickets-that-are-impacting-this-event'
										)}
									</div>
									<ClayMultiSelect
										value={i18n.translate('ticket')}
									>
										<ClaySelect.Option
											label={i18n.translate('ticket-1')}
											value="ticket-1"
										/>
										<ClaySelect.Option
											label={i18n.translate('ticket-2')}
											value="ticket-2"
										/>
									</ClayMultiSelect>
								</div>
							)}
						</>
					)}
				/>
			</div>
		</div>
	) : (
		<p>
			{i18n.translate(
				'make-sure-the-project-link-is-correct-and-that-you-have-access-to-this-project'
			)}
		</p>
	);
};

const BusinessEventsItemEdit: React.FC = () => {
	const {id} = useParams<{id: string}>();
	const [businessEvent, setBusinessEvent] = useState<
		IBusinessEvent | undefined
	>(undefined);
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		if (id) {
			const fetchBusinessEvent = async () => {
				try {
					setLoading(true);

					const eventData = await getBusinessEventById(id);

					setBusinessEvent(eventData);
				}
				catch (error) {
					console.error('Error', error);

					setBusinessEvent(undefined);
				}
				finally {
					setLoading(false);
				}
			};

			fetchBusinessEvent();
		}
	}, [id]);

	if (loading) {
		return (
			<div className="mx-auto">
				<ClayLoadingIndicator size="sm" />
			</div>
		);
	}

	if (!businessEvent) {
		return <div>{i18n.translate('no-data-found')}</div>;
	}

	return (
		<Formik
			initialValues={{businessEvent}}
			onSubmit={() => {}}
			validateOnChange
		>
			{(formikProps) => (
				<BusinessEventsItemEditPage
					businessEvent={
						formikProps.values
							.businessEvent as unknown as IBusinessEvent
					}
					errors={formikProps.errors}
					setFieldValue={formikProps.setFieldValue}
				/>
			)}
		</Formik>
	);
};

export default BusinessEventsItemEdit;
