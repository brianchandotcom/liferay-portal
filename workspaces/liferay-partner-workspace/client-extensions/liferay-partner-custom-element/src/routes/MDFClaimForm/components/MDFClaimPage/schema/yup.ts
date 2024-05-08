/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {array, boolean, mixed, number, object, string} from 'yup';

import {TypeActivityKey} from '../../../../../common/enums/TypeActivityKey';
import LiferayPicklist from '../../../../../common/interfaces/liferayPicklist';
import checkRequiredListOfQualifiedLeads from '../../../utils/checkRequiredListOfQualifiedLeads';
import {validateDocument} from './constants/validateDocument';
import {allContentsFieldsValidation} from './fieldValidation/allContentsFieldsValidation';
import {eventCollateralsValidation} from './fieldValidation/eventCollateralsValidation';
import {eventInvitationsValidation} from './fieldValidation/eventInvitationsValidation';
import {eventPhotosValidation} from './fieldValidation/eventPhotosValidation';
import {imagesValidation} from './fieldValidation/imagesValidation';

const claimSchema = object({
	activities: array()
		.of(
			object({
				budgets: array().when('selected', {
					is: (selected: boolean) => selected,
					then: (schema) =>
						schema
							.of(
								object({
									invoiceAmount: number().when('selected', {
										is: (selected: boolean) => selected,
										then: (schema) =>
											schema
												.moreThan(
													0,
													'Need be bigger than 0'
												)
												.test(
													'biggerAmount',
													'Invoice amount is larger than the MDF requested amount',
													(
														invoiceAmount,
														testContext
													) =>
														Number(invoiceAmount) <=
														Number(
															testContext.parent
																.requestAmount
														)
												)
												.required('Required'),
									}),
									invoiceFile: mixed().when('selected', {
										is: (selected: boolean) => selected,
										then: (schema) =>
											schema
												.test(
													'fileSize',
													validateDocument.fileSize
														.message,
													(invoiceFile) => {
														if (
															invoiceFile &&
															!invoiceFile.documentId
														) {
															return (
																Math.ceil(
																	invoiceFile.size /
																		1000
																) <=
																validateDocument
																	.fileSize
																	.maxSize
															);
														}

														return true;
													}
												)
												.required()
												.test(
													'fileType',
													validateDocument.document
														.message,
													(invoiceFile) => {
														if (
															invoiceFile &&
															!invoiceFile.documentId
														) {
															return validateDocument.document.types.includes(
																invoiceFile.type
															);
														}

														return true;
													}
												)
												.required('Required'),
									}),
									requestAmount: number(),
								})
							)
							.test(
								'needMoreThanOneBudgetSelected',
								'Need at least one budget selected',
								(value: any) =>
									value.some((item: any) => item.selected)
							),
				}),
				eventProgramFile: mixed().when(['selected', 'typeActivity'], {
					is: (selected: boolean, typeActivity: LiferayPicklist) =>
						selected && typeActivity.key === TypeActivityKey.EVENT,
					then: (schema) =>
						schema
							.required('Required')
							.test(
								'fileSize',
								validateDocument.fileSize.message,
								(eventProgramFile) => {
									if (
										eventProgramFile &&
										!eventProgramFile.documentId
									) {
										return (
											Math.ceil(
												eventProgramFile.size / 1000
											) <=
											validateDocument.fileSize.maxSize
										);
									}

									return true;
								}
							)
							.test(
								'fileType',
								validateDocument.document.message,
								(eventProgramFile) => {
									if (
										eventProgramFile &&
										!eventProgramFile.documentId
									) {
										return validateDocument.document.types.includes(
											eventProgramFile.type
										);
									}

									return true;
								}
							),
				}),
				listOfQualifiedLeadsFile: mixed()
					.when('selected', {
						is: (selected: boolean) => selected,
						then: (schema) =>
							schema
								.test(
									'fileSize',
									validateDocument.fileSize.message,
									(listOfQualifiedLeadsFile) => {
										if (
											listOfQualifiedLeadsFile &&
											!listOfQualifiedLeadsFile.documentId
										) {
											return (
												Math.ceil(
													listOfQualifiedLeadsFile.size /
														1000
												) <=
												validateDocument.fileSize
													.maxSize
											);
										}

										return true;
									}
								)
								.test(
									'fileType',
									validateDocument.listOfLeadsDocuments
										.message,
									(listOfQualifiedLeadsFile) => {
										if (
											listOfQualifiedLeadsFile &&
											!listOfQualifiedLeadsFile.documentId
										) {
											return validateDocument.listOfLeadsDocuments.types.includes(
												listOfQualifiedLeadsFile.type
											);
										}

										return true;
									}
								),
					})
					.when(['selected', 'typeActivity'], {
						is: (
							selected: boolean,
							typeActivity: LiferayPicklist
						) =>
							checkRequiredListOfQualifiedLeads(
								selected,
								typeActivity
							),
						then: (schema) => schema.required('Required'),
					}),
				metrics: string().when(['selected', 'typeActivity'], {
					is: (selected: boolean, typeActivity: LiferayPicklist) =>
						selected &&
						typeActivity.key === TypeActivityKey.DIGITAL_MARKETING,
					then: (schema) =>
						schema
							.required('Required')
							.max(350, 'You have exceeded the character limit'),
				}),
				proofOfPerformance: object().when(
					['selected', 'typeActivity'],
					(selected: boolean, typeActivity) => {
						let targetFields = {};

						if (selected) {
							switch (typeActivity.key) {
								case TypeActivityKey.EVENT:
									targetFields = {
										...eventInvitationsValidation,
										...eventPhotosValidation,
										...eventCollateralsValidation,
									};
									break;
								case TypeActivityKey.DIGITAL_MARKETING:
									targetFields = allContentsFieldsValidation;

									break;
								case TypeActivityKey.CONTENT_MARKETING:
									targetFields = allContentsFieldsValidation;

									break;
								default:
									targetFields = {
										...allContentsFieldsValidation,
										...imagesValidation,
									};
									break;
							}
						}

						return object(targetFields);
					}
				),
				selected: boolean(),
				telemarketingMetrics: string().when(
					['selected', 'typeActivity'],
					{
						is: (
							selected: boolean,
							typeActivity: LiferayPicklist
						) =>
							selected &&
							typeActivity.key ===
								TypeActivityKey.MISCELLANEOUS_MARKETING,
						then: (schema) =>
							schema
								.required('Required')
								.max(
									350,
									'You have exceeded the character limit'
								),
					}
				),
				telemarketingScriptFile: mixed().when('selected', {
					is: (selected: boolean) => selected,
					then: (schema) =>
						schema
							.test(
								'fileSize',
								validateDocument.fileSize.message,
								(telemarketingScriptFile) => {
									if (
										telemarketingScriptFile &&
										!telemarketingScriptFile.documentId
									) {
										return (
											Math.ceil(
												telemarketingScriptFile.size /
													1000
											) <=
											validateDocument.fileSize.maxSize
										);
									}

									return true;
								}
							)
							.test(
								'fileType',
								validateDocument.listOfLeadsDocuments.message,
								(telemarketingScriptFile) => {
									if (
										telemarketingScriptFile &&
										!telemarketingScriptFile.documentId
									) {
										return validateDocument.document.types.includes(
											telemarketingScriptFile.type
										);
									}

									return true;
								}
							),
				}),
				videoLink: string().when(['selected', 'typeActivity'], {
					is: (selected: boolean, typeActivity: LiferayPicklist) =>
						selected &&
						typeActivity.key === TypeActivityKey.CONTENT_MARKETING,
					then: (schema) =>
						schema.max(
							250,
							'You have exceeded the character limit'
						),
				}),
			})
		)
		.test(
			'needAtLeatOneSelectedActivity',
			'Need at least one activity selected',
			(activities) =>
				Boolean(activities?.some((activity) => activity.selected))
		)
		.test(
			'selectedActivityNeedsAtLeastOneBudget',
			'Need at least one budget per activity selected',
			(activities) => {
				return Boolean(
					!activities
						?.map((activity) => {
							return activity.selected
								? activity.selected &&
										activity?.budgets?.some(
											(budget) => budget.selected
										)
								: true;
						})
						.includes(false)
				);
			}
		)
		.test(
			'needMoreThanOneBudgetInvoice',
			'Need at least one budget invoice added',
			(activities) =>
				Boolean(
					activities?.some((activity) =>
						Boolean(
							activity.budgets?.some(
								(budget) => budget.invoiceFile
							)
						)
					)
				)
		),
	reimbursementInvoices: array()
		.of(
			mixed()
				.test(
					'fileSize',
					validateDocument.fileSize.message,
					(reimbursementInvoice) => {
						if (
							reimbursementInvoice &&
							!reimbursementInvoice.documentId
						) {
							return (
								Math.ceil(reimbursementInvoice.size / 1000) <=
								validateDocument.fileSize.maxSize
							);
						}

						return true;
					}
				)
				.test(
					'fileType',
					validateDocument.document.message,
					(reimbursementInvoice) => {
						if (
							reimbursementInvoice &&
							!reimbursementInvoice.documentId
						) {
							return validateDocument.document.types.includes(
								reimbursementInvoice.type
							);
						}

						return true;
					}
				)
		)
		.min(1, 'Reimbursement Invoices must have at least 1 file')
		.required('Required'),
	totalClaimAmount: number()
		.moreThan(0, 'Need be bigger than 0')
		.required('Required')
		.test(
			'is-greater-than-the-requested-amount',
			'Total Claim Amount cannot be greater than Total MDF Requested Amount',
			(totalClaimAmount, testContext) =>
				Number(totalClaimAmount) <=
				Number(testContext.parent.totalMDFRequestedAmount)
		),
});

export default claimSchema;
