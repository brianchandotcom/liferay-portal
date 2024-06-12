/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import React from 'react';

import Form from '../../../../../../components/Form';
import {
	TestrayFactor,
	TestrayFactorOption,
	TestrayOptionsByCategory,
} from '../../../../../../services/rest';
import RunsListActions from './RunsListActions';

import type {
	UseFieldArrayAppend,
	UseFieldArrayUpdate,
	UseFormRegister,
} from 'react-hook-form';

export type CategoryOptions = {
	factorCategory: string;
	factorCategoryId: number;
	factorOption: string;
	factorOptionId: number;
};

export type Category = {
	[key: number]: CategoryOptions;
};

export type Fields = {
	disabled?: boolean;
	id: string;
};

export type RunsListProps = {
	action?: string;
	append: UseFieldArrayAppend<any>;
	displayVertical?: boolean;
	factorItems?: TestrayFactor[];
	fields: Fields[];
	optionsList: TestrayOptionsByCategory[] | TestrayFactorOption[][];
	register: UseFormRegister<any>;
	remove: (index: number) => void;
	update: UseFieldArrayUpdate<any>;
};

const COLUMN_SIZE_MEDIUM = 6;
const COLUMN_SIZE_SMALL = 3;

const RunsList: React.FC<RunsListProps> = ({
	append,
	displayVertical,
	factorItems,
	fields,
	optionsList,
	register,
	remove,
	update,
}) => {
	return (
		<>
			{fields.map((field, index) => {
				return (
					<ClayLayout.Row key={field.id}>
						<ClayLayout.Col size={12}>
							<ClayLayout.Row
								className={classNames({
									'align-items-center d-flex justify-content-space-between':
										!displayVertical,
									'flex-column justify-content-space-between':
										displayVertical,
								})}
							>
								{Object.keys(field).map(
									(optionItem, optionIndex) => {
										const formatedCategoryName = optionItem
											.replace(/([a-z])([A-Z])/g, '$1 $2')
											.replace(/^./, (str) =>
												str.toUpperCase()
											);

										const defaultOption =
											field[optionItem as keyof Fields];

										const isDefaultEnvironment =
											factorItems?.find((item) =>
												item.factorCategory?.name.includes(
													formatedCategoryName
												)
											);

										return (
											<>
												{isDefaultEnvironment && (
													<ClayLayout.Col
														key={optionIndex}
														size={
															displayVertical &&
															index === 0
																? COLUMN_SIZE_MEDIUM
																: COLUMN_SIZE_SMALL
														}
													>
														<Form.Select
															defaultValue={
																(defaultOption as string) ||
																''
															}
															disabled={
																field.disabled
															}
															forceSelectOption
															label={
																formatedCategoryName
															}
															name={`runOptions.${index}.${optionItem}`}
															options={optionsList[
																formatedCategoryName as any
															]?.map(
																({
																	name,
																}: any) => ({
																	label: name,
																	value: name,
																})
															)}
															register={register}
															registerOptions={{
																onBlur: (
																	event: React.FocusEvent<HTMLSelectElement>
																) => {
																	const runOptionName =
																		event
																			.target
																			.options[
																			event
																				.target
																				.selectedIndex
																		]?.text;

																	const dataToUpdate =
																		{
																			...field,
																			[optionItem]:
																				runOptionName,
																		};

																	update(
																		index,
																		dataToUpdate
																	);
																},
															}}
														/>
													</ClayLayout.Col>
												)}
											</>
										);
									}
								)}

								<RunsListActions
									append={append}
									field={fields[index]}
									index={index}
									remove={remove}
								/>
							</ClayLayout.Row>

							<Form.Divider />
						</ClayLayout.Col>
					</ClayLayout.Row>
				);
			})}
		</>
	);
};

export default RunsList;
