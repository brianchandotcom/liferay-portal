/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormikErrors} from 'formik';
import React, {useEffect} from 'react';
import client from '~/shared/apollo/client';
import Form, {
	toPromise,
	validateMaxLength,
	validateRequired,
} from '~/shared/components/form';
import {JobStatuses} from '~/shared/util/constants';
import {sequence} from '~/shared/util/promise';

import {RECOMMENDATION_BY_NAME_QUERY} from '../../queries/RecommendationQuery';
import {JOB_RUN_FREQUENCIES_LIST} from '../../utils/utils';

interface IBasicSettingsProps {
	currentStep?: number;
	disabled: boolean;
	errors: FormikErrors<any>;
	initialValues: any;
	jobRunStatus?: JobStatuses;
	name: string;
	onSetDisabled: (disabled: boolean) => void;
	runDate?: string;
	setStep?: (step: number) => void;
}

const BasicSettings: React.FC<IBasicSettingsProps> = ({
	errors,
	initialValues,
	name,
	onSetDisabled,
}) => {
	useEffect(() => {
		onSetDisabled(!name || !!errors.name);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [name, errors]);

	const validateRecommendationName = (value: string): Promise<string> => {
		let error = '';

		if (value !== initialValues.name) {
			return client
				.query({
					query: RECOMMENDATION_BY_NAME_QUERY,
					variables: {name},
				})
				.then(({data: {jobByName}}) => {
					if (jobByName) {
						error = Liferay.Language.get(
							'a-recommendation-model-already-exists-with-that-name.-please-enter-a-different-name'
						);
					}

					return error;
				});
		}
		else {
			return toPromise(error);
		}
	};

	return (
		<div className="basic-settings-root">
			<Form.Group>
				<Form.Input
					label={Liferay.Language.get('model-name')}
					name="name"
					required
					validate={sequence([
						validateRequired,
						validateMaxLength(255),
						validateRecommendationName,
					])}
				/>
			</Form.Group>

			<Form.Group>
				<Form.Select
					label={Liferay.Language.get('training-frequency')}
					name="runFrequency"
				>
					{JOB_RUN_FREQUENCIES_LIST.map(({name, value}) => (
						<Form.Select.Item key={value} value={value}>
							{name}
						</Form.Select.Item>
					))}
				</Form.Select>
			</Form.Group>
		</div>
	);
};

export default BasicSettings;
