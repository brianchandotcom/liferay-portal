/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import {Input as TimeInput} from '@clayui/time-picker/lib';
import {FieldArray, Formik} from 'formik';
import {useEffect, useMemo, useState} from 'react';
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
import {IBusinessEvent, IOption} from '~/utils/types';

import Layout from '../../../../../../../components/FormLayout';
import {PRODUCT_TYPES} from '../../../../../utils/constants';
import useGetBusinessEventTypesList from '../../hooks/useGetBusinessEventTypesList';
import useGetGMTTimeZonesList from '../../hooks/useGetGMTTimeZonesList';
import useGetVersionOfLiferaySoftwareList from '../../hooks/useGetVersionOfLiferaySoftwareList';
import useHasAllEventsPermissions from '../../hooks/useHasAllEventsPermissions';

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

	const [businessEventTypesOptions, setBusinessEventTypesOptions] = useState<
		IOption[]
	>([]);

	const [gmtTimeZonesOptions, setGMTTimeZonesOptions] = useState<IOption[]>(
		[]
	);

	const [isLoadingSubmitButton, setIsLoadingSubmitButton] =
		useState<boolean>(false);

	const [
		versionOfLiferaySoftwareOptions,
		setVersionOfLiferaySoftwareOptions,
	] = useState<IOption[]>([]);

	const {businessEventTypesList} = useGetBusinessEventTypesList();

	const emptyOption = useMemo(
		() => ({
			disabled: true,
			label: i18n.translate('select-the-option'),
			value: '',
		}),
		[]
	);

	const {gmtTimeZonesList} = useGetGMTTimeZonesList();

	const navigate = useNavigate();

	const formatDateToISO = (dateString: string) => {
		if (!dateString) {
			return '';
		}
		const [month, day, year] = dateString.split('-');

		return `${year}-${month}-${day}`;
	};

	const handleSubmit = async () => {
		const updatedBusinessEvent = {...businessEvent};

		if (updatedBusinessEvent.targetGoLiveDate) {
			const formattedDate = formatDateToISO(
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
			setIsLoadingSubmitButton(true);

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

	const {hasAllEventsPermissions} = useHasAllEventsPermissions();

	const isDescriptionRequired = useMemo(
		() => businessEvent.eventType?.key === 'otherEvent',
		[businessEvent.eventType]
	);

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

	const {versionOfLiferaySoftwareList} = useGetVersionOfLiferaySoftwareList();

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
		if (businessEventTypesList?.length) {
			setBusinessEventTypesOptions([
				emptyOption,
				...businessEventTypesList,
			]);
		}
	}, [businessEventTypesList, emptyOption]);

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

	return hasAllEventsPermissions ? (
		<Layout
			footerProps={{
				leftButton: (
					<Button
						displayType="secondary"
						onClick={() => {
							navigate(`/${project?.accountKey}/business-events`);
						}}
					>
						{i18n.translate('cancel')}
					</Button>
				),
				middleButton: (
					<Button
						disabled={baseButtonDisabled || isLoadingSubmitButton}
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
		>
			<FieldArray
				name="businessEvent"
				render={() => (
					<>
						<Input
							badgeClassName="ml-3 mr-3"
							groupStyle="pb-1"
							label={i18n.translate('event-name')}
							name="businessEvent.name"
							placeholder={i18n.translate('event-name')}
							required
							type="text"
						/>

						<Select
							badgeClassName="ml-3 mr-3"
							groupStyle="pb-1"
							label={i18n.translate('event-type')}
							link="https://help.liferay.com/hc"
							linkText="here"
							name="businessEvent.eventType.key"
							options={businessEventTypesOptions}
							required
							showPopover
							text="to-learn-more-about-types-of-business-events-please-click-x"
						/>

						{subscriptionGroups && !isSaasOnly && (
							<Select
								badgeClassName="ml-3 mr-3"
								groupStyle="pb-1"
								label={i18n.translate(
									'your-current-liferay-version'
								)}
								name="businessEvent.currentLiferayVersion.key"
								options={versionOfLiferaySoftwareOptions}
								required
							/>
						)}

						{isNewLiferayVersionRequired && (
							<Select
								badgeClassName="ml-3 mr-3"
								groupStyle="pb-1"
								label={i18n.translate('new-version')}
								name="businessEvent.newLiferayVersion.key"
								options={versionOfLiferaySoftwareOptions}
								required
							/>
						)}

						{isDescriptionRequired && (
							<Input
								badgeClassName="ml-3 mr-3"
								component="textarea"
								groupStyle="pb-1"
								label={i18n.translate('event-description')}
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
									badgeClassName="ml-3 mr-3"
									dateFormat="MM-dd-yyyy"
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
									placeholder={i18n.translate('mm-dd-yyyy')}
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
