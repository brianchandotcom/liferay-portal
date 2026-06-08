/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import autobind from 'autobind-decorator';
import PropTypes from 'prop-types';
import React from 'react';
import Form from '~/shared/components/form';
import omitDefinedProps from '~/shared/util/omitDefinedProps';

interface IPasswordInputProps extends React.ComponentProps<typeof Form.Input> {
	disabled?: boolean;
}

interface IPasswordInputState {
	showPassword: boolean;
}

export default class PasswordInput extends React.Component<
	IPasswordInputProps,
	IPasswordInputState
> {
	static defaultProps = {
		disabled: false,
	};

	static propTypes = {
		disabled: PropTypes.bool,
	};

	state = {
		showPassword: false,
	};

	@autobind
	handleShowPasswordToggle() {
		this.setState({
			showPassword: !this.state.showPassword,
		});
	}

	render() {
		const {
			props: {disabled, placeholder, ...otherProps},
			state: {showPassword},
		} = this;

		return (
			<Form.Input
				{...omitDefinedProps(otherProps, PasswordInput.propTypes)}
				disabled={disabled}
				inset={{
					content: (
						<ClayButton
							aria-label={
								showPassword
									? Liferay.Language.get('hidden')
									: Liferay.Language.get('watch')
							}
							className="button-root"
							disabled={disabled}
							displayType="unstyled"
							onClick={this.handleShowPasswordToggle}
						>
							<ClayIcon
								className="icon-root"
								symbol={showPassword ? 'hidden' : 'view'}
							/>
						</ClayButton>
					),
					position: 'after',
				}}
				placeholder={placeholder}
				type={showPassword ? 'text' : 'password'}
			/>
		);
	}
}
