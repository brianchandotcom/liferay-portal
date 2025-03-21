/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput, ClayRadio, ClaySelect} from '@clayui/form';

import './BusinessEventsItemEdit.css';

import {Nav, useModal} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayMultiSelect from '@clayui/multi-select';
import NavigationBar from '@clayui/navigation-bar';
import {FieldArray, Formik} from 'formik';
import {useEffect, useMemo, useState} from 'react';
import {Link, useNavigate, useParams} from 'react-router-dom';
import {Button, DatePicker, Input, Select, TimePicker} from '~/components';
import {useAppPropertiesContext} from '~/contexts/AppPropertiesContext';
import {useCustomerPortal} from '~/features/project/context';
import useGetBusinessEventTypesList from '~/features/project/pages/Project/BusinessEvents/hooks/useGetBusinessEventTypesList';
import useGetGMTTimeZonesList from '~/features/project/pages/Project/BusinessEvents/hooks/useGetGMTTimeZonesList';
import useGetVersionOfLiferaySoftwareList from '~/features/project/pages/Project/BusinessEvents/hooks/useGetVersionOfLiferaySoftwareList';
import {PRODUCT_TYPES} from '~/features/project/utils/constants';
import {Liferay} from '~/services/liferay';
import {getBusinessEventById} from '~/services/liferay/api';
import {updateBusinessEvent} from '~/services/liferay/graphql/queries';
import i18n from '~/utils/I18n';
import {IBusinessEvent, IOption} from '~/utils/types';

import useHasAllEventsPermissions from '../../../hooks/useHasAllEventsPermissions';
import {getFormattedGoLiveDateTime} from '../../../utils/getFormattedGoLiveDate';
import BusinessEventsConfirmationPopup from './components/BusinessEventsConfirmationPopup';

interface IProps {
	businessEvent: IBusinessEvent;
	errors?: Record<string, any>;
	originalBusinessEvent: IBusinessEvent;
	setFieldValue: (
		field: string,
		value: any,
		shouldValidate?: boolean
	) => void;
	touched?: any;
	values: any;
}

const BusinessEventsItemEditPage: React.FC<IProps> = ({
	businessEvent,
	errors,
	originalBusinessEvent,
	setFieldValue,
	touched,
	values,
}) => {
	const [baseButtonDisabled, setBaseButtonDisabled] = useState<boolean>(true);
	const [reason, setReason] = useState('');

	const {businessEventTypesList} = useGetBusinessEventTypesList();

	const {client} = useAppPropertiesContext();

	const emptyOption = useMemo(
		() => ({
			disabled: true,
			label: i18n.translate('select-the-option'),
			value: '',
		}),
		[]
	);

	const {gmtTimeZonesList} = useGetGMTTimeZonesList();

	const [gmtTimeZonesOptions, setGMTTimeZonesOptions] = useState<IOption[]>(
		[]
	);

	const {hasAllEventsPermissions} = useHasAllEventsPermissions();

	const [hasImpactingEvents, setHasImpactingEvents] = useState<string>('no');

	const [isLoadingSubmitButton, setIsLoadingSubmitButton] =
		useState<boolean>(false);

	const [{project, subscriptionGroups}] = useCustomerPortal();

	const isDescriptionRequired = useMemo(
		() => businessEvent.eventType?.key === 'otherEvent',
		[businessEvent.eventType]
	);

	const [isModalOpen, setIsModalOpen] = useState(false);

	const isNewLiferayVersionRequired = useMemo(
		() => ['migration', 'upgrade'].includes(businessEvent.eventType?.key!),
		[businessEvent.eventType]
	);

	const isSaasOnly = useMemo(
		() =>
			subscriptionGroups?.length === 1 &&
			subscriptionGroups[0].name === PRODUCT_TYPES.liferayExperienceCloud,
		[subscriptionGroups]
	);

	const navigate = useNavigate();

	const {observer, onClose} = useModal({
		onClose: () => setIsModalOpen(false),
	});

	const {versionOfLiferaySoftwareList} = useGetVersionOfLiferaySoftwareList();

	const [
		versionOfLiferaySoftwareOptions,
		setVersionOfLiferaySoftwareOptions,
	] = useState<IOption[]>([]);

	const handleRadioChange = (value: string) => {
		setHasImpactingEvents(value);
	};

	const handleSubmit = async () => {
		const updatedBusinessEvent = {...businessEvent};

		const formattedBusinessEvent = {
			associatedTickets: updatedBusinessEvent.associatedTickets,
			currentLiferayVersion:
				updatedBusinessEvent.currentLiferayVersion?.key,
			description: updatedBusinessEvent.description,
			eventType: updatedBusinessEvent.eventType?.key,
			lastComment: reason,
			newLiferayVersion: updatedBusinessEvent.newLiferayVersion?.key,
			targetGoLiveDateTime: getFormattedGoLiveDateTime(
				updatedBusinessEvent.targetGoLiveDate,
				updatedBusinessEvent.targetGoLiveTime
			),
			timeZone: updatedBusinessEvent.timeZone?.key,
		};

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
					businessEvent: formattedBusinessEvent,
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
			setIsLoadingSubmitButton(false);

			Liferay.Util.openToast({
				message: i18n.translate('an-unexpected-error-occurred'),
				type: 'danger',
			});
			console.error('Error adding business event', error);
		}
	};

	useEffect(() => {
		if (!isDescriptionRequired) {
			setFieldValue('businessEvent.description', '');
		}
		else {
			originalBusinessEvent.description
				? setFieldValue(
						'businessEvent.description',
						originalBusinessEvent.description
					)
				: setFieldValue('businessEvent.description', '');
		}
	}, [
		isDescriptionRequired,
		originalBusinessEvent.description,
		setFieldValue,
	]);

	useEffect(() => {
		if (!isNewLiferayVersionRequired) {
			setFieldValue('businessEvent.newLiferayVersion.key', '');
		}
		else {
			originalBusinessEvent.newLiferayVersion
				? setFieldValue(
						'businessEvent.newLiferayVersion.key',
						originalBusinessEvent.newLiferayVersion.key
					)
				: setFieldValue('businessEvent.newLiferayVersion.key', '');
		}
	}, [
		isNewLiferayVersionRequired,
		originalBusinessEvent.newLiferayVersion,
		setFieldValue,
	]);

	useEffect(() => {
		if (gmtTimeZonesList?.length) {
			setGMTTimeZonesOptions([
				{...emptyOption, disabled: false},
				...gmtTimeZonesList,
			]);
		}
	}, [emptyOption, gmtTimeZonesList]);

	useEffect(() => {
		if (versionOfLiferaySoftwareList?.length) {
			setVersionOfLiferaySoftwareOptions([
				emptyOption,
				...versionOfLiferaySoftwareList,
			]);
		}
	}, [emptyOption, versionOfLiferaySoftwareList]);

	useEffect(() => {
		const hasCurrentLiferayVersion =
			values.businessEvent.currentLiferayVersion.key;

		const hasDescription = values.businessEvent.description;
		const hasError = errors && Object.keys(errors).length;
		const hasEventName = values.businessEvent.name;
		const hasEventType = values.businessEvent.eventType.key;
		const hasNewLiferayVersion = values.businessEvent.newLiferayVersion.key;
		const hasTargetGoLiveDate = values.businessEvent.targetGoLiveDate;
		const hasTouched = Boolean(Object.keys(touched).length);

		let hasAllRequiredFieldsFilled =
			Boolean(hasEventName) &&
			Boolean(hasEventType) &&
			Boolean(hasTargetGoLiveDate);

		if (isDescriptionRequired) {
			hasAllRequiredFieldsFilled =
				hasAllRequiredFieldsFilled && hasDescription;
		}

		if (isNewLiferayVersionRequired) {
			hasAllRequiredFieldsFilled =
				hasAllRequiredFieldsFilled && hasNewLiferayVersion;
		}

		if (!isSaasOnly) {
			hasAllRequiredFieldsFilled =
				hasAllRequiredFieldsFilled && hasCurrentLiferayVersion;
		}

		setBaseButtonDisabled(
			!hasAllRequiredFieldsFilled || Boolean(hasError) || !hasTouched
		);
	}, [
		errors,
		isDescriptionRequired,
		isNewLiferayVersionRequired,
		isSaasOnly,
		touched,
		values.businessEvent.currentLiferayVersion,
		values.businessEvent.description,
		values.businessEvent.eventType,
		values.businessEvent.name,
		values.businessEvent.newLiferayVersion,
		values.businessEvent.targetGoLiveDate,
	]);

	return hasAllEventsPermissions ? (
		<div className="be-edit-page">
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
							disabled={
								baseButtonDisabled || isLoadingSubmitButton
							}
							displayType="primary"
							isLoading={isLoadingSubmitButton}
							onClick={() => {
								const newTargetGoLiveDateTime =
									getFormattedGoLiveDateTime(
										businessEvent.targetGoLiveDate,
										businessEvent.targetGoLiveTime
									);
								if (
									newTargetGoLiveDateTime !==
									originalBusinessEvent.targetGoLiveDateTime
								) {
									setIsModalOpen(true);
								}
								else {
									handleSubmit();
								}
							}}
						>
							{i18n.translate('save-changes')}
						</Button>
					</div>
				</div>
			</div>

			{isModalOpen && (
				<BusinessEventsConfirmationPopup
					handleSubmit={handleSubmit}
					message={i18n.translate(
						'we-understand-that-plans-change-please-let-us-know-why-the-target-go-live-date-for-this-event-is-being-updated'
					)}
					observer={observer}
					onClose={onClose}
					reason={reason}
					setReason={setReason}
				/>
			)}

			<div className="mb-4">
				<NavigationBar triggerLabel={i18n.translate('event-details')}>
					<Nav.Item>
						<Nav.Link
							active={true}
							aria-label={`Switch to ${i18n.translate(
								'event-details'
							)}`}
							className="be-nav-link text-neutral-10"
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
									className="ml-3 mr-3"
									groupStyle="pb-1"
									label={i18n.translate('event-type')}
									name="businessEvent.eventType.key"
									options={businessEventTypesList}
									required
								/>
							</div>

							{subscriptionGroups && !isSaasOnly && (
								<div className="event-edit-field mb-4">
									<Select
										className="ml-3 mr-3"
										groupStyle="pb-1"
										label={i18n.translate(
											'your-current-liferay-version'
										)}
										name="businessEvent.currentLiferayVersion.key"
										options={
											versionOfLiferaySoftwareOptions
										}
										required
									/>
								</div>
							)}

							{isNewLiferayVersionRequired && (
								<div className="event-edit-field mb-4">
									<Select
										badgeClassName="ml-3 mr-3"
										className="ml-3 mr-3"
										groupStyle="pb-1"
										label={i18n.translate('new-version')}
										name="businessEvent.newLiferayVersion.key"
										options={
											versionOfLiferaySoftwareOptions
										}
										required
									/>
								</div>
							)}

							{isDescriptionRequired && (
								<div className="event-edit-field mb-4">
									<Input
										badgeClassName="ml-3 mr-3"
										component="textarea"
										groupStyle="pb-1"
										label={i18n.translate(
											'event-description'
										)}
										name="businessEvent.description"
										placeholder={i18n.translate(
											'event-description'
										)}
										required
										type="text"
									/>
								</div>
							)}

							<div className="event-edit-field mb-4">
								<ClayInput.Group className="m-0">
									<ClayInput.GroupItem className="m-0">
										<DatePicker
											badgeClassName="ml-3 mr-3"
											className="ml-3 mr-3"
											dateFormat="MM-dd-yyyy"
											groupStyle="pb-1"
											label={i18n.translate(
												'target-go-live-date'
											)}
											name="businessEvent.targetGoLiveDate"
											onChange={(value: string) => {
												setFieldValue(
													'businessEvent.targetGoLiveDate',
													value
												);
											}}
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
											name="businessEvent.timeZone.key"
											options={gmtTimeZonesOptions}
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
								<div className="pb-2">
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

	const targetGoLiveTime = businessEvent.targetGoLiveDateTime
		?.split('T')[1]
		.substring(0, 5);

	return (
		<Formik
			initialValues={{
				businessEvent: {
					...businessEvent,
					currentLiferayVersion:
						businessEvent.currentLiferayVersion || {key: ''},
					newLiferayVersion: businessEvent.newLiferayVersion || {
						key: '',
					},
					targetGoLiveDate:
						businessEvent.targetGoLiveDateTime?.split('T')[0],
					targetGoLiveTime: {
						hours: targetGoLiveTime?.split(':')[0],
						minutes: targetGoLiveTime?.split(':')[1],
					},
					timeZone: businessEvent.timeZone || {key: ''},
				},
			}}
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
					originalBusinessEvent={businessEvent}
					setFieldValue={formikProps.setFieldValue}
					touched={formikProps.touched}
					values={formikProps.values}
				/>
			)}
		</Formik>
	);
};

export default BusinessEventsItemEdit;
