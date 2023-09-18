/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import classNames from 'classnames';

import PRMFormFieldProps from '../../interfaces/prmFormFieldProps';

interface IProps {
	children?: React.ReactNode;
	error?: string;
	touched: boolean;
}

const WrapperInput = ({
	children,
	description,
	error,
	label,
	required,
	touched,
}: PRMFormFieldProps & IProps) => (
	<ClayForm.Group
		className={classNames('mb-4', {
			'has-error': touched && error,
			'has-success': touched && !error,
		})}
	>
		{label && (
			<label className="font-weight-semi-bold ml-0">
				{label}

				{required && <span className="text-danger">*</span>}
			</label>
		)}

		{children}

		{error && touched && (
			<ClayForm.FeedbackGroup>
				<ClayForm.FeedbackItem>{error}</ClayForm.FeedbackItem>
			</ClayForm.FeedbackGroup>
		)}

		{description && (
			<ClayForm.Text className="ml-0">{description}</ClayForm.Text>
		)}
	</ClayForm.Group>
);

export default WrapperInput;
