/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput, ClayRadio} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {FieldArray, Formik} from 'formik';
import {useCallback, useEffect, useMemo, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {Button, Input, Select} from '~/components';
import DatePicker from '~/components/DatePicker/DatePicker';
import TimePicker from '~/components/TimePicker/TimePicker';
import {useAppPropertiesContext} from '~/contexts/AppPropertiesContext';
import {useCustomerPortal} from '~/features/project/context';
import {Liferay} from '~/services/liferay';
import {addBusinessEvent} from '~/services/liferay/graphql/queries';
import i18n from '~/utils/I18n';
import getInitialEvent from '~/utils/getInitialEvent';
import {IBusinessEvent, IOption, ITicket} from '~/utils/types';

import Layout from '../../../../../../../components/FormLayout';
import AssociatedTicketsContainer from '../../components/AssociatedTicketsContainer';
import useAccountBusinessEvents from '../../hooks/useAccountBusinessEvents';
import useAccountTickets from '../../hooks/useAccountTickets';
import useGetBusinessEventTypesList from '../../hooks/useGetBusinessEventTypesList';
import useGetGMTTimeZonesList from '../../hooks/useGetGMTTimeZonesList';
import useGetLiferayVersions from '../../hooks/useGetLiferayVersions';
import useHasAllEventsPermissions from '../../hooks/useHasAllEventsPermissions';
import {getFormattedGoLiveDateTime} from '../../utils/getFormattedGoLiveDate';
import useIsSaasOnly from '../../utils/useIsSaasOnly';

interface IProps {
	businessEvent: IBusinessEvent;
	errors?: Record<string, any>;
	setFieldValue: (
		field: string,
		value: any,
		shouldValidate?: boolean
	) => void;
	touched?: any;
	values: any;
}

const BusinessEventsAddPage: React.FC<IProps> = ({
	businessEvent,
	errors,
	setFieldValue,
	touched,
	values,
}) => {
	const {client} = useAppPropertiesContext();

	const [{project, subscriptionGroups}] = useCustomerPortal();

	const [baseButtonDisabled, setBaseButtonDisabled] = useState<boolean>(true);

	const {businessEventTypesList, loading: loadingBusinessEventTypesList} =
		useGetBusinessEventTypesList();

	const emptyOption = useMemo(
		() => ({
			disabled: true,
			label: i18n.translate('select-the-option'),
			value: '',
		}),
		[]
	);

	const {gmtTimeZonesList, loading: loadingGMTTimeZonesList} =
		useGetGMTTimeZonesList();

	const {hasAllEventsPermissions} = useHasAllEventsPermissions();

	const [hasImpactingEvents, setHasImpactingEvents] = useState<string>('no');

	const isDescriptionRequired = useMemo(
		() => businessEvent.eventType?.key === 'otherEvent',
		[businessEvent.eventType]
	);

	const [isLoadingSubmitButton, setIsLoadingSubmitButton] =
		useState<boolean>(false);

	const isNewLiferayVersionRequired = useMemo(
		() => ['migration', 'upgrade'].includes(businessEvent.eventType?.key!),
		[businessEvent.eventType]
	);

	const {isSaasOnly} = useIsSaasOnly(subscriptionGroups);

	const {loading: loadingTickets, tickets} = useAccountTickets(
		project?.accountKey || ''
	);

	const navigate = useNavigate();

	const {updateAccountBusinessEvents} = useAccountBusinessEvents(
		project?.accountKey || '',
		businessEvent,
		false,
		false
	);

	const {
		dxpMinorVersionsAndPortalMajorVersions,
		loading: loadingLiferayVersions,
	} = useGetLiferayVersions();

	const [newLiferayVersionOptions, setNewLiferayVersionOptions] = useState<
		IOption[]
	>([]);

	const [ticketOptions, setTicketOptions] = useState<ITicket[]>([]);

	const handleRadioChange = (value: string) => {
		setHasImpactingEvents(value);
	};

	const handleRemove = useCallback((selectedTicket: ITicket) => {
		setTicketOptions((ticketOptions) => [
			...ticketOptions.map((ticket) => {
				return selectedTicket.ticketId === ticket.ticketId
					? {...ticket, selected: false}
					: {...ticket};
			}),
		]);
	}, []);

	const handleSelect = useCallback((selectedTicket: ITicket) => {
		setTicketOptions((ticketOptions) => [
			...ticketOptions.map((ticket) => {
				return selectedTicket.ticketId === ticket.ticketId
					? {...ticket, selected: true}
					: {...ticket};
			}),
		]);
	}, []);

	const handleSubmit = async () => {
		const updatedBusinessEvent = {
			...businessEvent,
			currentLiferayVersion: {
				key: businessEvent.currentLiferayVersion?.key,
			},
			newLiferayVersion: {key: businessEvent.newLiferayVersion?.key},
		};

		try {
			setIsLoadingSubmitButton(true);

			await updateAccountBusinessEvents();

			await client.mutate<{
				addBusinessEvent: IBusinessEvent;
			}>({
				context: {
					displaySuccess: false,
					type: 'liferay-rest',
				},
				mutation: addBusinessEvent,
				variables: {
					businessEvent: updatedBusinessEvent,
				},
			});

			navigate(`/${project?.accountKey}/business-events`);

			Liferay.Util.openToast({
				message: i18n.translate('business-event-created-successfully'),
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

	const loading =
		loadingBusinessEventTypesList ||
		loadingGMTTimeZonesList ||
		loadingLiferayVersions ||
		loadingTickets;

	useEffect(() => {
		if (hasImpactingEvents === 'yes') {
			const selectedTickets = ticketOptions
				.filter((ticket) => ticket.selected)
				.map((ticket) => ticket.ticketId);

			setFieldValue(
				'businessEvent.associatedTickets',
				`[${selectedTickets.length ? selectedTickets.join(', ') : ''}]`
			);
		}
		else {
			setFieldValue('businessEvent.associatedTickets', '[]');
		}
	}, [hasImpactingEvents, setFieldValue, ticketOptions]);

	useEffect(() => {
		setFieldValue(
			'businessEvent.targetGoLiveDateTime',
			getFormattedGoLiveDateTime(
				businessEvent.targetGoLiveDate,
				businessEvent.targetGoLiveTime
			)
		);
	}, [
		businessEvent.targetGoLiveDate,
		businessEvent.targetGoLiveTime,
		setFieldValue,
	]);

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

	useEffect(() => {
		if (dxpMinorVersionsAndPortalMajorVersions?.length) {
			setNewLiferayVersionOptions([
				...dxpMinorVersionsAndPortalMajorVersions.filter(
					(version, index, versions) => {
						return (
							index <
							versions.findIndex((version) => {
								return (
									version.value ===
									businessEvent.currentLiferayVersion?.key
								);
							})
						);
					}
				),
			]);
		}
	}, [
		businessEvent.currentLiferayVersion?.key,
		dxpMinorVersionsAndPortalMajorVersions,
		emptyOption,
	]);

	useEffect(() => {
		if (!isDescriptionRequired) {
			setFieldValue('businessEvent.description', '');
		}
	}, [isDescriptionRequired, setFieldValue]);

	useEffect(() => {
		if (!isNewLiferayVersionRequired) {
			setFieldValue('businessEvent.newLiferayVersion.key', '');
		}
	}, [isNewLiferayVersionRequired, setFieldValue]);

	useEffect(() => {
		setFieldValue(
			'businessEvent.r_accountEntryToBusinessEvents_accountEntryId',
			project?.id
		);
	}, [project?.id, setFieldValue]);

	useEffect(() => {
		setTicketOptions([
			...(tickets?.map((ticket) => {
				return {...ticket, selected: false};
			}) || []),
		]);
	}, [tickets]);

	return !loading ? (
		tickets ? (
			hasAllEventsPermissions ? (
				<Layout
					footerProps={{
						leftButton: (
							<Button
								displayType="secondary"
								onClick={() => {
									navigate(
										`/${project?.accountKey}/business-events`
									);
								}}
							>
								{i18n.translate('cancel')}
							</Button>
						),
						middleButton: (
							<Button
								disabled={
									baseButtonDisabled || isLoadingSubmitButton
								}
								displayType="primary"
								isLoading={isLoadingSubmitButton}
								onClick={handleSubmit}
							>
								{i18n.translate('create-event')}
							</Button>
						),
					}}
					headerProps={{
						greetings: project?.name,
						title: i18n.translate('create-business-event'),
					}}
					layoutType="cp-required-info"
				>
					<FieldArray
						name="businessEvent"
						render={() => (
							<>
								<Input
									badgeClassName="mt-1 mx-3"
									label={i18n.translate('event-name')}
									name="businessEvent.name"
									placeholder={i18n.translate('event-name')}
									required
									type="text"
								/>

								<Select
									badgeClassName="mt-1 mx-3"
									label={i18n.translate('event-type')}
									link="https://help.liferay.com/hc"
									linkText="here"
									name="businessEvent.eventType.key"
									options={businessEventTypesList}
									required
									showPopover
									text="to-learn-more-about-types-of-business-events-please-click-x"
								/>

								{subscriptionGroups && !isSaasOnly && (
									<Select
										badgeClassName="mt-1 mx-3"
										label={i18n.translate(
											'your-current-liferay-version'
										)}
										name="businessEvent.currentLiferayVersion.key"
										options={[
											emptyOption,
											...dxpMinorVersionsAndPortalMajorVersions,
										]}
										required
									/>
								)}

								{isNewLiferayVersionRequired && (
									<Select
										badgeClassName="mt-1 mx-3"
										label={i18n.translate('new-version')}
										name="businessEvent.newLiferayVersion.key"
										options={[
											emptyOption,
											...newLiferayVersionOptions,
										]}
										required
									/>
								)}

								{isDescriptionRequired && (
									<Input
										badgeClassName="mt-1 mx-3"
										component="textarea"
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
								)}

								<ClayInput.Group className="m-0">
									<ClayInput.GroupItem className="m-0">
										<DatePicker
											badgeClassName="mt-1 mx-3"
											dateFormat="MM-dd-yyyy"
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
											id="select-businessEvent.timeZone"
											label={i18n.translate('time-zone')}
											name="businessEvent.timeZone.key"
											options={gmtTimeZonesList}
										/>
									</ClayInput.GroupItem>

									<ClayInput.GroupItem className="m-0">
										<TimePicker
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

								{tickets && !!tickets.length ? (
									<>
										<div className="mx-3 pb-3">
											<label>
												{i18n.translate(
													'are-there-any-support-tickets-impacting-this-event'
												)}
											</label>

											<div className="ml-1">
												<ClayRadio
													checked={
														hasImpactingEvents ===
														'no'
													}
													label="No"
													onChange={() =>
														handleRadioChange('no')
													}
													value="no"
												/>

												<ClayRadio
													checked={
														hasImpactingEvents ===
														'yes'
													}
													label="Yes"
													onChange={() =>
														handleRadioChange('yes')
													}
													value="yes"
												/>
											</div>
										</div>

										{hasImpactingEvents === 'yes' && (
											<div className="mx-3 pb-3">
												<label>
													{i18n.translate(
														'please-select-the-tickets-that-are-impacting-this-event'
													)}
												</label>

												<AssociatedTicketsContainer
													editing
													handleRemove={handleRemove}
													handleSelect={handleSelect}
													tickets={ticketOptions}
												/>
											</div>
										)}
									</>
								) : (
									<div className="mx-3 pb-3">
										{i18n.translate(
											'there-are-currently-no-open-tickets-under-this-project'
										)}
									</div>
								)}
							</>
						)}
					/>
				</Layout>
			) : (
				<p>
					{i18n.translate(
						'make-sure-the-project-link-is-correct-and-that-you-have-access-to-this-project'
					)}
				</p>
			)
		) : (
			<p
				dangerouslySetInnerHTML={{
					__html: i18n.sub(
						'we-apologize-for-the-inconvenience-but-we-ve-detected-a-system-error-with-this-project',
						['<a href="https://help.liferay.com">', '</a>']
					),
				}}
			/>
		)
	) : (
		<div className="mx-auto">
			<ClayLoadingIndicator size="sm" />
		</div>
	);
};

const BusinessEventsAdd: React.FC = () => {
	return (
		<Formik
			initialValues={{businessEvent: getInitialEvent()}}
			onSubmit={() => {}}
			validateOnChange
		>
			{(formikProps) => (
				<BusinessEventsAddPage
					businessEvent={
						formikProps.values
							.businessEvent as unknown as IBusinessEvent
					}
					errors={formikProps.errors}
					setFieldValue={formikProps.setFieldValue}
					touched={formikProps.touched}
					values={formikProps.values}
				/>
			)}
		</Formik>
	);
};

export default BusinessEventsAdd;
