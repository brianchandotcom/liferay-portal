/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput, ClayRadio, ClaySelect} from '@clayui/form';

import './BusinessEventsItemEdit.css';

import ClayMultiSelect from '@clayui/multi-select';
import {FieldArray, Formik} from 'formik';
import {useCallback, useEffect, useMemo, useState} from 'react';
import {DatePicker, Select, TimePicker} from '~/components';
import useGetBusinessEventTypesList from '~/features/project/pages/Project/BusinessEvents/hooks/useGetBusinessEventTypesList';
import useGetGMTTimeZonesList from '~/features/project/pages/Project/BusinessEvents/hooks/useGetGMTTimeZonesList';
import useGetVersionOfLiferaySoftwareList from '~/features/project/pages/Project/BusinessEvents/hooks/useGetVersionOfLiferaySoftwareList';
import {updateBusinessEventItem} from '~/services/liferay/api';
import i18n from '~/utils/I18n';
import getInitialEvent from '~/utils/getInitialEvent';
import {IBusinessEvent} from '~/utils/types';

interface IProps {
	businessEvent: IBusinessEvent;
	setFieldValue: (field: string, value: any) => void;
	setSubmitTriggered: any;
	submitTriggered: any;
	values: any;
}

const BusinessEventsItemEditForm = ({
	businessEvent,
	setFieldValue,
	setSubmitTriggered,
	submitTriggered,
	values,
}: IProps) => {
	const [hasImpactingEvents, setHasImpactingEvents] = useState<string>('no');
	const [businessEventTypesOptions, setBusinessEventTypesOptions] = useState<
		any[]
	>([]);
	const [gmtTimeZonesOptions, setGMTTimeZonesOptions] = useState<any[]>([]);
	const [
		versionOfLiferaySoftwareOptions,
		setVersionOfLiferaySoftwareOptions,
	] = useState<any[]>([]);

	const businessEventTypesList = useGetBusinessEventTypesList();
	const gmtTimeZonesList = useGetGMTTimeZonesList();
	const versionOfLiferaySoftwareList = useGetVersionOfLiferaySoftwareList();

	const handleRadioChange = (value: string) => {
		setHasImpactingEvents(value);
	};

	const handleSubmit = useCallback(
		async (values: any) => {
			const fieldsToPatch = {
				currentLiferayVersion: {
					key: values.businessEvent.currentLiferayVersion.key,
					name: values.businessEvent.currentLiferayVersion.name,
				},
				eventType: {
					key: values.businessEvent.eventType.key,
					name: values.businessEvent.eventType.name,
				},
				newLiferayVersion: {
					key: values.businessEvent.newLiferayVersion.key,
					name: values.businessEvent.newLiferayVersion.name,
				},
				targetGoLiveDateTime: values.businessEvent.targetGoLiveDateTime,
				timeZone: values.businessEvent.timeZone,
			};

			setSubmitTriggered(true);

			if (businessEvent.id) {
				await updateBusinessEventItem(businessEvent.id, fieldsToPatch);
			}
		},
		[businessEvent.id, setSubmitTriggered]
	);

	useEffect(() => {
		if (submitTriggered) {
			handleSubmit(values);
			setSubmitTriggered(false);
		}
	}, [handleSubmit, values, submitTriggered, setSubmitTriggered]);

	const emptyOption = useMemo(
		() => ({
			disabled: true,
			label: i18n.translate('select-the-option'),
			value: '',
		}),
		[]
	);

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
			setGMTTimeZonesOptions([emptyOption, ...gmtTimeZonesList]);
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
		if (businessEventTypesOptions?.length) {
			setFieldValue(
				'businessEvent.eventType',
				businessEventTypesOptions[0].value
			);
		}
	}, [businessEventTypesOptions, setFieldValue]);

	useEffect(() => {
		if (versionOfLiferaySoftwareOptions?.length) {
			setFieldValue(
				'businessEvent.currentLiferayVersion',
				versionOfLiferaySoftwareOptions[0].value
			);
		}
	}, [setFieldValue, versionOfLiferaySoftwareOptions]);

	return (
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
								options={businessEventTypesOptions}
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
								options={versionOfLiferaySoftwareOptions}
								required
							/>
						</div>

						<div className="event-edit-field mb-4">
							<Select
								badgeClassName="ml-3 mr-3"
								groupStyle="pb-1"
								label={i18n.translate('new-version')}
								name="businessEvent.newLiferayVersion"
								options={versionOfLiferaySoftwareOptions}
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
									/>
								</ClayInput.GroupItem>

								<ClayInput.GroupItem className="m-0">
									<Select
										groupStyle="pb-1"
										id="select-businessEvent.timeZone"
										label={i18n.translate('time-zone')}
										name="businessEvent.timeZone"
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
									onChange={() => handleRadioChange('yes')}
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
	);
};

const BusinessEventsItemEdit = ({ticketID, ...props}: any) => {
	return (
		<Formik
			initialValues={{businessEvent: getInitialEvent()}}
			onSubmit={() => {}}
			validateOnChange
		>
			{(formikProps) => (
				<BusinessEventsItemEditForm
					{...props}
					{...formikProps}
					ticketID={ticketID}
				/>
			)}
		</Formik>
	);
};

export default BusinessEventsItemEdit;
