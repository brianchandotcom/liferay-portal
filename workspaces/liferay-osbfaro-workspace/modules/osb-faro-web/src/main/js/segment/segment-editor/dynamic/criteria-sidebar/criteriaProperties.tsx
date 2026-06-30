import CriteriaSidebarItem from './CriteriaSidebarItem';
import EmptyState from '@clayui/empty-state';
import React from 'react';
import {getDefaultValue} from './CriteriaSidebarCollapse';
import {List} from 'immutable';
import {Property} from 'shared/util/records';

export const filterPropertiesByLabel = (
	properties: List<Property>,
	searchValue: string
): List<Property> =>
	searchValue
		? (properties.filter((property) =>
				(property?.label ?? '')
					.toLowerCase()
					.includes(searchValue.toLowerCase())
			) as List<Property>)
		: properties;

export const renderProperties = (
	properties: List<Property>,
	searchValue = ''
) => {
	if (searchValue && properties.isEmpty()) {
		return (
			<div className="empty-message">
				<EmptyState
					className="text-center"
					description={Liferay.Language.get(
						'review-your-search-and-try-again'
					)}
					title={Liferay.Language.get('no-results-found')}
				/>
			</div>
		);
	}

	return (
		<ul className="property-subgroups-list active">
			<li>
				<ul className="properties-list">
					{properties.toArray().map((property, i) => {
						const {label, name, propertyKey, type} = property;

						return (
							<CriteriaSidebarItem
								className={`color--${propertyKey}`}
								defaultValue={getDefaultValue(property)}
								key={`${name}-${i}`}
								label={label}
								name={name}
								property={property}
								propertyKey={propertyKey}
								type={type}
							/>
						);
					})}
				</ul>
			</li>
		</ul>
	);
};
