import ClayIcon from '@clayui/icon';
import React from 'react';
import {Text} from '@clayui/core';

interface ISectionHeader {
	className?: string;
	icon: string;
	title: string;
}

const SectionHeader: React.FC<ISectionHeader> = ({
	className = 'mb-3',
	icon,
	title,
}) => (
	<div className={className}>
		<span className="mr-2">
			<Text color="secondary" size={4}>
				<ClayIcon symbol={icon} />
			</Text>
		</span>

		<Text color="secondary" size={4} weight="semi-bold">
			{title.toUpperCase()}
		</Text>
	</div>
);

export {SectionHeader};
