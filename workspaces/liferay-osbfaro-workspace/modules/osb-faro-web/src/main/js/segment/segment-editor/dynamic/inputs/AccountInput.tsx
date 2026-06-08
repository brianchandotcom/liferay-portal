/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import React from 'react';
import * as API from '~/shared/api';

import {PropertyTypes} from '../utils/constants';
import {getPropertyValue} from '../utils/custom-inputs';
import {ISegmentEditorCustomInputBase} from '../utils/types';
import CustomDateInput from './CustomDateInput';
import CustomNumberInput from './CustomNumberInput';
import CustomStringInput from './CustomStringInput';

interface IAccountInputProps extends ISegmentEditorCustomInputBase {
	touched: boolean;
	valid: boolean;
}

export default class AccountInput extends React.Component<IAccountInputProps> {
	@autobind
	fieldValuesDataSourceFn(): Promise<string[]> {
		const {
			channelId,
			groupId,
			property: {id},
			value: valueIMap,
		} = this.props;

		return API.accounts
			.fetchFieldValues({
				channelId,
				fieldMappingFieldName: id,
				groupId,
				query: getPropertyValue(valueIMap, 'value', 0),
			})
			.then(({items}) => items);
	}

	render() {
		const {
			property: {type},
		} = this.props;

		if (type === PropertyTypes.AccountDate) {
			return <CustomDateInput {...this.props} />;
		}

		if (type === PropertyTypes.AccountNumber) {
			return <CustomNumberInput {...this.props} />;
		}

		return (
			<CustomStringInput
				{...this.props}
				fieldValuesDataSourceFn={this.fieldValuesDataSourceFn}
			/>
		);
	}
}
