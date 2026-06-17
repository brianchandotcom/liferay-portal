import React from 'react';
import {Text} from '@clayui/core';

export const buildHeaderSubtitle = (individual: {
	accountName: string;
	lastSessionCountry: string;
	properties: {email: string};
}) => {
	const {email} = individual.properties;
	const {accountName, lastSessionCountry} = individual;

	return (
		<Text color="secondary" size={4}>
			{[email, accountName, lastSessionCountry]
				.filter(Boolean)
				.join(' | ')}
		</Text>
	);
};
