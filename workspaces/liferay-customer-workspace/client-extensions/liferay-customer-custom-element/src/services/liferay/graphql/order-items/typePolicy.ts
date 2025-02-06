/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import concatPageSizePagination from '../common/utils/concatPageSizePagination';
import readPageSizePagination from '../common/utils/readPageSizePagination';

export const orderItemsTypePolicy = {
	OrderItem: {
		fields: {
			options: {
				read(options: string | Record<string, any>) {
					let finalOptions: Record<string, unknown> = {};

					if (typeof options === 'string') {
						finalOptions = JSON.parse(options);
					}
					else {
						finalOptions = options;
					}

					if (!finalOptions.instanceSize) {
						finalOptions.instanceSize = 0;
					}

					return finalOptions;
				},
			},
			reducedCustomFields: {
				read(_: unknown, {readField}: {readField: Function}) {
					const customFields = readField('customFields');

					if (Array.isArray(customFields)) {
						return customFields.reduce(
							(
								customFieldsAccumulator: Record<string, any>,
								currentCustomField: Record<string, any>
							) => ({
								...customFieldsAccumulator,
								[readField('name', currentCustomField)]:
									readField(
										'data',
										readField(
											'customValue',
											currentCustomField
										)
									),
							}),
							{}
						);
					}

					return {
						[readField('name', customFields)]: readField(
							'data',
							readField('customValue', customFields)
						),
					};
				},
			},
		},
		keyFields: ['externalReferenceCode'],
	},
	OrderItemPage: {
		fields: {
			items: {
				...concatPageSizePagination(),
				...readPageSizePagination(),
			},
		},
	},
};

export const orderItemsQueryTypePolicy = {
	orderItems: {
		keyArgs: ['filter'],
	},
};
