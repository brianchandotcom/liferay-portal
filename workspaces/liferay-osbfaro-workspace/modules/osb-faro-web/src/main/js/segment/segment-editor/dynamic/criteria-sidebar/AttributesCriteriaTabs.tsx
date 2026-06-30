import ClayTabs from '@clayui/tabs';
import React, {useMemo, useState} from 'react';
import {filterPropertiesByLabel, renderProperties} from './criteriaProperties';
import {List} from 'immutable';
import {Property} from 'shared/util/records';

const DEFAULT_TAB = 0;

interface IAttributesCriteriaTabsProps {
	customProperties: List<Property>;
	defaultProperties: List<Property>;
	searchValue: string;
}

const AttributesCriteriaTabs: React.FC<IAttributesCriteriaTabsProps> = ({
	customProperties,
	defaultProperties,
	searchValue,
}) => {
	const [activeTab, setActiveTab] = useState<number>(DEFAULT_TAB);

	const properties =
		activeTab === DEFAULT_TAB ? defaultProperties : customProperties;

	const filteredProperties = useMemo(
		() => filterPropertiesByLabel(properties, searchValue),
		[properties, searchValue]
	);

	return (
		<div className="events-criteria-tabs">
			<ClayTabs active={activeTab} onActiveChange={setActiveTab}>
				<ClayTabs.Item>{Liferay.Language.get('default')}</ClayTabs.Item>

				<ClayTabs.Item>{Liferay.Language.get('custom')}</ClayTabs.Item>
			</ClayTabs>

			<div className="events-criteria-tabs-content mt-3">
				{renderProperties(filteredProperties, searchValue)}
			</div>
		</div>
	);
};

export default AttributesCriteriaTabs;
