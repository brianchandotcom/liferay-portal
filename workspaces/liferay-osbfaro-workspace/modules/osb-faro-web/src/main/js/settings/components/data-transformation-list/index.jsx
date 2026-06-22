import autobind from 'autobind-decorator';
import getCN from 'classnames';
import React from 'react';
import Row from './Row';
import {fromJS, List, Map} from 'immutable';
import {noop} from 'lodash';
import {PropTypes} from 'prop-types';

function checkIfDuplicateTargetField(suggestionIMap, duplicateFieldsIMap) {
	return Boolean(duplicateFieldsIMap.get(suggestionIMap.get('name')));
}

export default class DataTransformationList extends React.Component {
	static defaultProps = {
		dataSourceFn: noop,
		duplicateTargetFieldsIMap: new Map(),
		fieldsIList: new List(),
		onChange: noop,
		readOnly: false
	};

	static propTypes = {
		dataSourceFn: PropTypes.func,
		duplicateTargetFieldsIMap: PropTypes.instanceOf(Map),
		fieldsIList: PropTypes.instanceOf(List),
		fileVersionId: PropTypes.oneOfType([
			PropTypes.string,
			PropTypes.number
		]),
		groupId: PropTypes.string.isRequired,
		hideMappedFields: PropTypes.bool,
		id: PropTypes.string,
		initialSearchedSuggestions: PropTypes.array,
		mappingSuggestions: PropTypes.object,
		name: PropTypes.string,
		onChange: PropTypes.func,
		readOnly: PropTypes.bool,
		sourceFieldPlaceholder: PropTypes.string,
		sourceFields: PropTypes.object,
		sourceTitle: PropTypes.oneOfType([PropTypes.array, PropTypes.string]),
		suggestionsTitle: PropTypes.oneOfType([
			PropTypes.array,
			PropTypes.string
		])
	};

	@autobind
	handleChange(index, item, isSource) {
		const {fieldsIList, mappingSuggestions, onChange} = this.props;

		if (isSource) {
			const mappingSuggestion = mappingSuggestions[item.name][0];

			let suggestion = {};

			if (mappingSuggestion) {
				const {name, values} = mappingSuggestion;

				suggestion = {
					name,
					value: values && values[0]
				};
			}

			onChange(
				fieldsIList.setIn(
					[index],
					fromJS({
						source: item,
						suggestion
					})
				)
			);
		} else {
			onChange(fieldsIList.setIn([index, 'suggestion'], fromJS(item)));
		}
	}

	@autobind
	handleRemove(index) {
		const {fieldsIList, onChange} = this.props;

		if (onChange) {
			onChange(fieldsIList.filter((item, i) => i !== index));
		}
	}

	render() {
		const {
			dataSourceFn,
			duplicateTargetFieldsIMap,
			fieldsIList,
			fileVersionId,
			groupId,
			hideMappedFields,
			id,
			initialSearchedSuggestions,
			mappingSuggestions,
			name,
			readOnly,
			sourceFieldPlaceholder,
			sourceFields,
			sourceTitle,
			suggestionsTitle
		} = this.props;

		return (
			<div
				className={
					getCN('data-transformation-list-root', {
						['read-only']: readOnly
					}) +
					(this.props.className ? ` ${this.props.className}` : '')
				}
			>
				<div className='data-transformation-header'>
					<div className='left'>{sourceTitle}</div>

					<div className='right'>{suggestionsTitle}</div>
				</div>

				{fieldsIList.map((fieldIMap, i) => {
					const sourceIMap = fieldIMap.get('source');
					const suggestionIMap = fieldIMap.get('suggestion');

					const isDuplicateTargetField = checkIfDuplicateTargetField(
						suggestionIMap,
						duplicateTargetFieldsIMap
					);

					const unmapped =
						!sourceIMap.get('name') ||
						!suggestionIMap.get('name') ||
						isDuplicateTargetField;

					return (
						<Row
							className={
								(hideMappedFields && !unmapped
									? 'hidden'
									: '') +
								(this.props.className
									? ` ${this.props.className}`
									: '')
							}
							dataSourceFn={dataSourceFn}
							fieldIMap={fieldIMap}
							fileVersionId={fileVersionId}
							groupId={groupId}
							id={id}
							index={i}
							initialSearchedSuggestions={
								initialSearchedSuggestions
							}
							isDuplicateTargetField={isDuplicateTargetField}
							key={i}
							mappingSuggestions={mappingSuggestions}
							onChange={this.handleChange}
							onRemove={this.handleRemove}
							readOnly={readOnly}
							sourceFieldPlaceholder={sourceFieldPlaceholder}
							sourceFields={sourceFields}
							sourceName={name}
						/>
					);
				})}
			</div>
		);
	}
}
