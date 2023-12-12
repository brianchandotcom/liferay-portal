/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import React, {Fragment} from 'react';

import Form from '../../../../../../components/Form';
import {
	TestrayFactor,
	TestrayFactorOption,
} from '../../../../../../services/rest';
import StackActions from './StackActions';

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

export type StackListProps = {
	append: UseFieldArrayAppend<any>;
	displayVertical?: boolean;
	factorItems: TestrayFactor[];
	factorOptionsList: TestrayFactorOption[][];
	fields: Fields[];
	register: UseFormRegister<any>;
	remove: (index: number) => void;
	update: UseFieldArrayUpdate<any>;
};

const COLUMN_SIZE_MEDIUM = 6;
const COLUMN_SIZE_SMALL = 3;

const StackList: React.FC<StackListProps> = ({
	append,
	displayVertical,
	factorItems,
	factorOptionsList,
	fields,
	register,
	remove,
	update,
}) => {
	const factorCategories = factorItems.map(({factorCategory}) => ({
		factorCategory: factorCategory?.name,
		factorCategoryId: factorCategory?.id,
	}));

	return (
		<ClayLayout.Row>
			{fields.map((field, index) => (
				<Fragment key={field.id}>
					<ClayLayout.Col size={12}>
						<ClayLayout.Row
							className={classNames({
								'align-items-center d-flex justify-content-space-between': !displayVertical,
								'flex-column justify-content-space-between': displayVertical,
							})}
						>
							{factorItems.map((factorItem, factorIndex) => {
								const factorOptions: TestrayFactorOption[] =
									factorOptionsList[factorIndex] || [];

								const {factorOption} =
									(field as any)[factorIndex] || {};

								const currentFactorOptionId =
									Number(
										index === 0
											? factorOption?.id ??
													factorItem?.factorOption?.id
											: factorOption?.id
									) || 0;

								return (
									<ClayLayout.Col
										key={factorIndex}
										size={
											displayVertical && index === 0
												? COLUMN_SIZE_MEDIUM
												: COLUMN_SIZE_SMALL
										}
									>
										<Form.Select
											defaultValue={currentFactorOptionId}
											disabled={field.disabled}
											forceSelectOption
											label={
												factorItem.factorCategory?.name
											}
											name={`factorStacks.${index}.${factorIndex}.factorOptionId`}
											options={factorOptions.map(
												({id, name}: any) => ({
													label: name,
													value: id,
												})
											)}
											register={register}
											registerOptions={{
												onBlur: (
													event: React.FocusEvent<
														HTMLSelectElement
													>
												) => {
													const {
														target: {value},
													} = event;

													const factorOptionName =
														event.target.options[
															event.target
																.selectedIndex
														]?.text;

													const dataToUpdate = {
														...field,
														[factorIndex]: {
															...(field as any)[
																factorIndex
															],
															factorOption: factorOptionName,
															factorOptionId: Number(
																value
															),
														},
													};

													update(index, dataToUpdate);
												},
											}}
										/>
									</ClayLayout.Col>
								);
							})}

							<StackActions
								append={append}
								defaultItem={{...factorCategories} as any}
								field={field}
								index={index}
								remove={remove}
							/>
						</ClayLayout.Row>

						<Form.Divider />
					</ClayLayout.Col>
				</Fragment>
			))}
		</ClayLayout.Row>
	);
};

export default StackList;
