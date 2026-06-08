/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import autobind from 'autobind-decorator';
import getCN from 'classnames';
import React, {createRef} from 'react';
import {withField} from '~/shared/components/form';
import {ENTER} from '~/shared/util/key-constants';

interface ITitleEditorProps {
	editable?: boolean;
	inputName: string;
	onBlur?: (event: React.FocusEvent<HTMLInputElement>) => void;
	onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
	placeholder?: string;
	value?: string;
}

export class TitleEditor extends React.Component<
	ITitleEditorProps,
	{editing: boolean}
> {
	static defaultProps = {
		editable: true,
	};

	state = {
		editing: false,
	};

	private _titleInput = createRef<HTMLInputElement>();

	@autobind
	handleBlur(event: React.FocusEvent<HTMLInputElement>) {
		const {onBlur} = this.props;

		this.setState({editing: false});

		if (onBlur) {
			onBlur(event);
		}
	}

	@autobind
	handleEdit(event: React.MouseEvent | React.KeyboardEvent) {
		event.preventDefault();

		const {editable} = this.props;
		if (editable) {
			this.editing();
		}
	}

	@autobind
	handleKeyDown(event: React.KeyboardEvent<HTMLInputElement>) {
		if (event.keyCode === ENTER && this._titleInput.current) {
			this._titleInput.current.blur();
		}
	}

	@autobind
	handleKeyDownEdit(event: React.KeyboardEvent<HTMLSpanElement>) {
		event.preventDefault();

		if (event.keyCode === ENTER) {
			this.editing();
		}
	}

	@autobind
	editing() {
		this.setState(
			{
				editing: !this.state.editing,
			},
			() => this._titleInput.current?.select()
		);
	}

	render() {
		const {
			props: {editable, inputName, onChange, placeholder, value = ''},
			state: {editing},
		} = this;

		const rootClasses = getCN('title-editor-root', {editing});

		const inputClasses = getCN('title-input', {
			hide: !editing,
		});

		const displayClasses = getCN('title-display', {
			'hide': editing,
			'placeholder-display': !value,
		});

		return (
			<div className={rootClasses}>
				<input
					className={inputClasses}
					name={inputName}
					onBlur={this.handleBlur}
					onChange={onChange}
					onKeyDown={this.handleKeyDown}
					placeholder={placeholder}
					ref={this._titleInput}
					required
					type="text"
					value={value}
				/>

				<div className={displayClasses}>
					<span
						className="title-value"
						onClick={this.handleEdit}
						onKeyDown={this.handleKeyDownEdit}
						role="button"
						tabIndex={0}
					>
						{value || placeholder}
					</span>

					{editable && (
						<ClayButton
							aria-label={Liferay.Language.get('edit')}
							borderless
							className="button-root"
							displayType="unstyled"
							onClick={this.handleEdit}
							outline
							size="sm"
						>
							<ClayIcon className="icon-root" symbol="pencil" />
						</ClayButton>
					)}
				</div>
			</div>
		);
	}
}

export default withField(
	({field: {name, ...otherFields}, ...otherProps}: any) => {
		const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
			const {
				form: {setFieldValue},
			} = otherProps;

			setFieldValue(name, event.target.value);
		};

		return (
			<TitleEditor
				{...otherFields}
				{...otherProps}
				inputName={name}
				onChange={handleChange}
			/>
		);
	}
);
