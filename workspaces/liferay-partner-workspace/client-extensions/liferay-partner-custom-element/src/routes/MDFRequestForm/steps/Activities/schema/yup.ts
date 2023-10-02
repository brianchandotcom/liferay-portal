/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {array, date, number, object, string} from 'yup';

import {TypeActivityKey} from '../../../../../common/enums/TypeActivityKey';
import {TacticKeys} from '../../../../../common/enums/mdfRequestTactics';
import isObjectEmpty from '../../../../../common/utils/isObjectEmpty';
import getContentMarketingFieldsValidation from './fieldValidation/contentMarketingFields';
import getDigitalMarketingFieldsValidation from './fieldValidation/digitalMarketingFields';
import getEventFieldsValidation from './fieldValidation/eventFields';
import leadListFieldsValidation from './fieldValidation/leadListvalidation';
import getMiscellaneousMarketingFieldsValidation from './fieldValidation/miscellaneousMarketingFields';

const activitiesSchema = object({
	activities: array()
		.of(
			object({
				activityDescription: object().when(
					['typeActivity', 'tactic'],
					(typeActivity, tactic) => {
						let targetFields = {};

						switch (typeActivity.key) {
							case TypeActivityKey.EVENT:
								targetFields = getEventFieldsValidation(
									tactic.key as TacticKeys
								);
								break;
							case TypeActivityKey.DIGITAL_MARKETING:
								targetFields = getDigitalMarketingFieldsValidation(
									tactic.key as TacticKeys
								);
								break;
							case TypeActivityKey.CONTENT_MARKETING:
								targetFields = getContentMarketingFieldsValidation();
								break;
							default:
								targetFields = getMiscellaneousMarketingFieldsValidation(
									tactic.key as TacticKeys
								);
								break;
						}

						targetFields = {
							...targetFields,
							...leadListFieldsValidation,
						};

						return object(targetFields);
					}
				),
				budgets: array()
					.of(
						object({
							cost: number()
								.max(
									999999999,
									'The value cannot be greater than 9,999,999.99'
								)
								.moreThan(0, 'Required')
								.required('Required'),

							expense: object({
								key: string(),
								name: string(),
							}).test(
								'is-empty',
								'Required',
								(value) => !isObjectEmpty(value)
							),
						})
					)
					.compact((budget) => budget.removed)
					.min(1, 'Required'),
				endDate: date()
					.test(
						'end-date-six-month',
						'The activity period can not be longer than 6 months',
						(endDate, testContext) => {
							if (endDate) {
								const startDate = testContext.parent.startDate;

								return (
									endDate.getMonth() -
										startDate.getMonth() +
										12 *
											(endDate.getFullYear() -
												startDate.getFullYear()) <=
									6
								);
							}

							return false;
						}
					)
					.test(
						'end-date-less-end-date',
						'The end date cannot be before start date',
						(endDate, testContext) => {
							if (endDate && testContext.parent.startDate) {
								return testContext.parent.startDate < endDate;
							}

							return false;
						}
					)
					.test(
						'end-date-different-year',
						'The end date cannot be the year different the year of start date',
						(endDate, testContext) => {
							if (endDate && testContext.parent.startDate) {
								return (
									testContext.parent.startDate.getFullYear() ===
									endDate.getFullYear()
								);
							}

							return false;
						}
					)
					.test(
						'end-date-year-current-year',
						'The end date cannot exceed the current year',
						(endDate) => {
							const currentYear = new Date().getFullYear();

							if (endDate && currentYear) {
								return endDate.getFullYear() === currentYear;
							}

							return false;
						}
					)
					.required('Required'),
				mdfRequestAmount: number()
					.moreThan(0, 'Required')
					.required('Required')
					.test(
						'is-greater-than-the-percentage',
						'It is not possible to give a bigger discount than the Claim Percent',
						(mdfRequestAmount, testContext) => {
							if (mdfRequestAmount) {
								return !(
									+mdfRequestAmount >
									+testContext.parent.totalCostOfExpense *
										testContext.parent.claimPercent
								);
							}

							return false;
						}
					),
				name: string()
					.trim()
					.max(40, 'You have exceeded the character limit')
					.required('Required'),
				startDate: date()
					.test(
						'is-today',
						'Start date need to be after today',
						(startDate) => {
							if (startDate) {
								const currentDate = new Date();
								currentDate.setHours(0, 0, 0, 0);

								return currentDate < startDate;
							}

							return false;
						}
					)
					.test(
						'end-date-year-current-year',
						'The start date cannot exceed the current year',
						(startDate) => {
							const currentYear = new Date().getFullYear();

							if (startDate && currentYear) {
								return startDate.getFullYear() === currentYear;
							}

							return false;
						}
					)
					.required('Required'),
				tactic: object({
					id: number(),
					name: string(),
				}).test(
					'is-empty',
					'Required',
					(value) => !isObjectEmpty(value)
				),
				typeActivity: object({
					id: number(),
					name: string(),
					value: string(),
				}).test(
					'is-empty',
					'Required',
					(value) => !isObjectEmpty(value)
				),
			})
		)
		.compact((activity) => activity.removed)
		.min(1, 'Required'),
});

export default activitiesSchema;
