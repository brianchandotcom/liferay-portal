import * as API from 'shared/api';
import classNames from 'classnames';
import ClayButton from '@clayui/button';
import Loading, {Align} from 'shared/components/Loading';
import React from 'react';
import {Icon, Option, Picker} from '@clayui/core';
import {sub} from 'shared/util/lang';
import {useLifecycle} from '../context/LifecycleContext';
import {useParams} from 'react-router-dom';
import {useRequest} from 'shared/hooks/useRequest';

interface ITriggerButtonProps
	extends React.ButtonHTMLAttributes<HTMLButtonElement> {
	buttonClassName?: string;
	entityLabel: string;
	label?: string;
	loading?: boolean;
}

const TriggerButton = React.forwardRef<HTMLButtonElement, ITriggerButtonProps>(
	({buttonClassName, entityLabel, label, loading, ...rest}, ref) => (
		<ClayButton
			{...rest}
			className={classNames(buttonClassName, 'rounded-lg')}
			disabled={loading}
			displayType="secondary"
			ref={ref}
			size="sm"
		>
			<Icon className="inline-item inline-item-before" symbol="filter" />

			{label || sub(Liferay.Language.get('all-x'), [entityLabel])}

			{loading ? (
				<Loading align={Align.Right} />
			) : (
				<Icon
					className="inline-item inline-item-after"
					symbol="caret-bottom"
				/>
			)}
		</ClayButton>
	)
);

interface IProps {
	className?: string;
	entityLabel: string;
	fieldMappingFieldName: string;
	filterKey: 'countryFilter' | 'industryFilter';
}

const ALL_VALUES_KEY = 'all';

const FieldValueFilter = ({
	className,
	entityLabel,
	fieldMappingFieldName,
	filterKey,
}: IProps) => {
	const {filters, updateFilters} = useLifecycle();

	const {channelId, groupId} = useParams();

	const {data, loading} = useRequest({
		dataSourceFn: API.accounts.fetchFieldValues,
		variables: {
			channelId,
			fieldMappingFieldName,
			groupId,
			query: '',
		},
	});

	return (
		<Picker
			aria-label={
				sub(Liferay.Language.get('filter-by-x'), [
					entityLabel,
				]) as string
			}
			as={TriggerButton}
			buttonClassName={className}
			className="ml-3"
			entityLabel={entityLabel}
			label={filters[filterKey]}
			loading={loading}
			onSelectionChange={(item) => {
				const value = String(item);

				updateFilters({
					[filterKey]: value === ALL_VALUES_KEY ? '' : value,
				});
			}}
			searchable
			selectedKey={filters[filterKey] || ALL_VALUES_KEY}
			triggerIcon="caret-bottom"
			width={50}
		>
			<Option key={ALL_VALUES_KEY}>
				{sub(Liferay.Language.get('all-x'), [entityLabel])}
			</Option>

			{(data?.items ?? []).map((item: string) => (
				<Option key={item}>{item}</Option>
			))}
		</Picker>
	);
};

export default FieldValueFilter;
