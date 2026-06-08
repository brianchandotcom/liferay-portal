/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker, Text} from '@clayui/core';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useMemo, useState} from 'react';
import {useParams} from 'react-router-dom';
import {fetchPageExperience} from '~/shared/api/experiences';
import {useRequest} from '~/shared/hooks/useRequest';
import {truncateText} from '~/shared/util/util';

interface IExperienceItem {
	displayName?: string;
	id: string | null;
	name: string;
}

const ALL_EXPERIENCES_ITEM: IExperienceItem = {
	id: null,
	name: Liferay.Language.get('all-experiences'),
};

interface IExperienceDropdownProps {
	onChange: (experienceId: string | null) => void;
}

const ExperienceDropdown: React.FC<IExperienceDropdownProps> = ({onChange}) => {
	const {channelId, groupId, title, touchpoint} = useParams();

	const [selectedKey, setSelectedKey] = useState<string>('null');

	const {data} = useRequest({
		dataSourceFn: fetchPageExperience,
		variables: {
			canonicalUrl: touchpoint!,
			channelId: channelId!,
			groupId: groupId!,
			pageTitle: title!,
		},
	});

	const displayItems = useMemo(() => {
		const apiItems: IExperienceItem[] = Array.isArray(data) ? data : [];

		return [ALL_EXPERIENCES_ITEM, ...apiItems].map((item) => ({
			...item,
			displayName: truncateText(item.name, 35, null),
			id: item.id === null ? 'null' : String(item.id),
		}));
	}, [data]);

	const handleSelectionChange = (key: string) => {
		setSelectedKey(key);
		const valueForBackend = key === 'null' ? null : key;
		onChange(valueForBackend);
	};

	return (
		<ClayTooltipProvider>
			<div className="experience-dropdown">
				<Picker
					aria-label={Liferay.Language.get('all-experiences')}
					className="border-light form-control-sm"
					items={displayItems}
					onSelectionChange={(key) =>
						handleSelectionChange(String(key))
					}
					searchable
					selectedKey={selectedKey}
				>
					{(item: IExperienceItem) => (
						<Option key={String(item.id)} textValue={item.name}>
							<div
								className="w-100"
								title={
									item.name.length > 35
										? item.name
										: undefined
								}
							>
								<Text size={3}>{item.displayName}</Text>
							</div>
						</Option>
					)}
				</Picker>
			</div>
		</ClayTooltipProvider>
	);
};

export default ExperienceDropdown;
