import * as API from 'shared/api';
import CustomDateInput from './CustomDateInput';
import {
	IPaginatedDataSourceParams,
	IPaginatedDataSourceResult,
} from 'shared/hooks/usePaginatedRequest';
import CustomNumberInput from './CustomNumberInput';
import CustomStringInput from './CustomStringInput';
import React from 'react';
import {ISegmentEditorCustomInputBase} from '../utils/types';
import {PropertyTypes} from '../utils/constants';

interface IAccountInputProps extends ISegmentEditorCustomInputBase {
	touched: boolean;
	valid: boolean;
}

export default class AccountInput extends React.Component<IAccountInputProps> {
	constructor(props: IAccountInputProps) {
		super(props);
		this.fieldValuesDataSourceFn = this.fieldValuesDataSourceFn.bind(this);
	}

	fieldValuesDataSourceFn({
		page,
		pageSize,
		query,
	}: IPaginatedDataSourceParams): Promise<IPaginatedDataSourceResult> {
		const {
			channelId,
			groupId,
			property: {id},
		} = this.props;

		return API.accounts.fetchFieldValues({
			channelId,
			delta: pageSize,
			fieldMappingFieldName: id,
			groupId,
			page,
			query,
		});
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
