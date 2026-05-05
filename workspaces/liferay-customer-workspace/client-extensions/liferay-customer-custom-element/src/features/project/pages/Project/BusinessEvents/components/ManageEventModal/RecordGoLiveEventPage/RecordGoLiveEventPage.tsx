/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import {useEffect, useMemo, useState} from 'react';
import {Badge, Select} from '~/components';
import DatePicker from '~/components/DatePicker/DatePicker';
import TimePicker from '~/components/TimePicker/TimePicker';
import {Liferay} from '~/services/liferay';
import {updateBusinessEvent} from '~/services/liferay/rest/jira/Jira';
import i18n from '~/utils/I18n';
import {IBusinessEvent, IOption} from '~/utils/types';

import './RecordGoLiveEventPage.css';

import {Observer} from '@clayui/modal/lib/types';
import classNames from 'classnames';
import {isValidDate} from '~/utils/validations.form';

import useGetUTCTimeZonesList from '../../../hooks/useGetUTCTimeZonesList';
import {getFormattedEventDateTime} from '../../../utils/getFormattedEventDate';
import BusinessEventsModal from '../../BusinessEventsModal/BusinessEventsModal';

interface IProps {
	accountExternalReferenceCode: string;
	businessEvent: IBusinessEvent;
	closeFunction?: (value: boolean) => void;
	errors?: Record<string, any>;
	modalType: string;
	observer: Observer;
	onCompleted: () => void;
	setFieldValue: (
		field: string,
		value: any,
		shouldValidate?: boolean
	) => void;
	touched?: any;
	values?: any;
}

const RecordGoLiveEventPage: React.FC<IProps> = ({
	accountExternalReferenceCode,
	businessEvent,
	closeFunction = () => {},
	errors,
	modalType,
	observer,
	onCompleted,
	setFieldValue,
	touched,
	values,
}) => {
	const [baseButtonDisabled, setBaseButtonDisabled] = useState<boolean>(true);
	const [isLoadingSubmitButton, setIsLoadingSubmitButton] =
		useState<boolean>(false);
	const [isValidRecordDate, setIsValidRecordDate] = useState<boolean>(false);

	const emptyOption = useMemo(
		() => ({
			disabled: true,
			label: i18n.translate('select-the-option'),
			value: '',
		}),
		[]
	);

	const {utcTimeZonesList} = useGetUTCTimeZonesList();
	const [utcTimeZonesOptions, setUTCTimeZonesOptions] = useState<IOption[]>(
		[]
	);

	const handleInputChange = (event: {target: {value: string}}) => {
		setFieldValue('businessEvent.lastComment', event.target.value);
	};

	useEffect(() => {
		if (utcTimeZonesList?.length) {
			setUTCTimeZonesOptions([
				{...emptyOption, disabled: false},
				...utcTimeZonesList,
			]);
		}
	}, [emptyOption, utcTimeZonesList]);

	useEffect(() => {
		const hasError = errors && Object.keys(errors).length;
		const hasActualEventDate = values.businessEvent?.actualEventDate;
		const hasActualEventTime = values.businessEvent?.actualEventTime;
		const hasTimeZone = values.businessEvent?.timeZone?.key;

		const isValidDate = (date: string) => new Date(date) <= new Date();
		const isActualEventDateValid = isValidDate(hasActualEventDate);

		setIsValidRecordDate(isActualEventDateValid);

		const hasAllRequiredFieldsFilled =
			Boolean(hasActualEventDate) &&
			Boolean(hasActualEventTime) &&
			Boolean(hasTimeZone);

		setBaseButtonDisabled(
			!hasAllRequiredFieldsFilled ||
				Boolean(hasError) ||
				!isActualEventDateValid
		);
	}, [
		errors,
		touched,
		values.businessEvent?.actualEventDate,
		values.businessEvent?.actualEventTime,
		values.businessEvent?.timeZone?.key,
	]);

	const handleSubmit = async () => {
		const businessEventId = businessEvent.id;

		if (!businessEventId) {
			return;
		}

		const updatedBusinessEvent = {...values?.businessEvent};
		const formattedBusinessEvent = {
			...businessEvent,
			actualEventDate: getFormattedEventDateTime(
				updatedBusinessEvent.actualEventDate,
				updatedBusinessEvent.actualEventTime
			),
			currentLiferayVersion: businessEvent.currentLiferayVersion?.key,
			eventStatus: 'Completed',
			eventType: businessEvent.eventType?.key,
			lastComment: updatedBusinessEvent?.lastComment,
			newLiferayVersion: businessEvent.newLiferayVersion?.key,
			timeZone: updatedBusinessEvent.timeZone?.key,
		};

		try {
			setIsLoadingSubmitButton(true);

			await updateBusinessEvent(
				accountExternalReferenceCode,
				businessEventId,
				formattedBusinessEvent
			);

			closeFunction(false);

			onCompleted();
		}
		catch (error) {
			setIsLoadingSubmitButton(false);

			Liferay.Util.openToast({
				message: i18n.translate('an-unexpected-error-occurred'),
				type: 'danger',
			});
			console.error('Unable to record event date', error);
		}
	};

	const isEditable = ['Open', 'Overdue'].includes(
		businessEvent.eventStatus?.key!
	);

	return (
		<BusinessEventsModal
			baseButtonDisabled={baseButtonDisabled}
			handleSubmit={handleSubmit}
			headerTitle={businessEvent.name!}
			isLoadingSubmitButton={isLoadingSubmitButton}
			modalType={modalType}
			observer={observer}
			onClose={() => closeFunction(false)}
			submitButton={i18n.translate('record-actual-event-date')}
			title={i18n.translate('record-actual-event-date')}
		>
			{isEditable ? (
				<div>
					<ClayInput.Group className="business-date-container m-0">
						<ClayInput.GroupItem
							className={classNames('m-0', {
								'be-record-container': !isValidRecordDate,
							})}
						>
							<DatePicker
								badgeClassName="mr-4"
								dateFormat="MM-dd-yyyy"
								groupStyle="pb-1"
								label={i18n.translate('actual-event-date')}
								name="businessEvent.actualEventDate"
								onChange={(value) =>
									setFieldValue(
										'businessEvent.actualEventDate',
										value
									)
								}
								placeholder={i18n.translate('mm-dd-yyyy')}
								required
								validations={[isValidDate]}
							/>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem className="m-0">
							<Select
								groupStyle="pb-1"
								id="select-businessEvent.timeZone"
								label={i18n.translate('time-zone')}
								name="businessEvent.timeZone.key"
								options={utcTimeZonesOptions}
								required
							/>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem className="m-0">
							<TimePicker
								groupStyle="pb-1"
								label={i18n.translate('time')}
								name="businessEvent.actualEventTime"
								onChange={(value) =>
									setFieldValue(
										'businessEvent.actualEventTime',
										value
									)
								}
								required
							/>
						</ClayInput.GroupItem>
					</ClayInput.Group>

					<div className="font-weight-bold mb-3">
						{i18n.translate(
							'please-let-us-know-if-you-have-any-feedback-on-the-support-you-received-during-this-time'
						)}
					</div>

					<ClayInput
						component="textarea"
						onChange={handleInputChange}
						required
						type="text"
						value={values.businessEvent?.lastComment}
					/>

					<Badge alertType="info" badgeClassName="mt-3">
						<span className="pl-1 text-paragraph">
							{i18n.translate(
								'entering-an-actual-event-date-will-close-this-business-event-no-further-edits-will-be-possible'
							)}
						</span>
					</Badge>

					{values.businessEvent?.actualEventDate! &&
						!isValidRecordDate && (
							<Badge>
								<span className="pl-1">
									{i18n.translate(
										'please-select-an-actual-event-date-that-has-already-occurred-or-is-today'
									)}
								</span>
							</Badge>
						)}
				</div>
			) : (
				<div className="h6 my-4">
					{i18n.translate('cannot-edit-canceled-or-completed-events')}
				</div>
			)}
		</BusinessEventsModal>
	);
};

export default RecordGoLiveEventPage;
