import React from 'react';
import {SummaryTitle} from './SummaryTitle';

export const SummaryParagraph = ({
	description,
	title,
}: {
	description?: React.ReactNode;
	title: React.ReactNode;
}) => (
	<>
		<SummaryTitle className="mb-4" label={title} />

		{description && (
			<>
				<p className="font-size-sm mb-0">{description}</p>
			</>
		)}
	</>
);
