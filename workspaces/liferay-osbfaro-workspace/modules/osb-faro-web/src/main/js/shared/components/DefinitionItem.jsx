/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import autobind from 'autobind-decorator';
import {PropTypes} from 'prop-types';
import React from 'react';
import Loading, {Align} from '~/shared/components/Loading';
import TextTruncate from '~/shared/components/TextTruncate';
import Form from '~/shared/components/form';

export default class DefinitionItem extends React.Component {
	static defaultProps = {
		name: 'name',
	};

	static propTypes = {
		editable: PropTypes.bool,
		inputWidth: PropTypes.number,
		label: PropTypes.string,
		name: PropTypes.string,
		onSubmit: PropTypes.func,
		validate: PropTypes.func,
		value: PropTypes.string,
	};

	state = {
		editing: false,
	};

	constructor(props) {
		super(props);

		this._formRef = React.createRef();
	}

	@autobind
	handleSubmit(values) {
		const {name, onSubmit} = this.props;

		const {setSubmitting} = this._formRef.current;

		if (onSubmit) {
			onSubmit(values[name], name)
				.then(() => {
					setSubmitting(false);

					this.setState({editing: false});
				})
				.catch((error) => {
					if (!error.IS_CANCELLATION_ERROR) {
						setSubmitting(false);
					}
				});
		}
	}

	@autobind
	handleEditToggle() {
		this.setState({
			editing: !this.state.editing,
		});
	}

	render() {
		const {
			props: {editable, inputWidth, label, name, validate, value},
			state: {editing},
		} = this;

		return (
			<div
				className={`definition-item-root${
					this.props.className ? ` ${this.props.className}` : ''
				}`}
			>
				{label && <div className="h6">{label}</div>}

				{editing ? (
					<Form
						initialValues={{[name]: value}}
						innerRef={this._formRef}
						key="EDITING"
						onSubmit={this.handleSubmit}
					>
						{({handleSubmit, isSubmitting, isValid}) => (
							<Form.Form
								className="definition-item-editor"
								onSubmit={handleSubmit}
							>
								<Form.Group autoFit>
									<Form.Input
										name={name}
										validate={validate}
										width={inputWidth}
									/>

									<ClayButton
										aria-label={Liferay.Language.get(
											'cancel'
										)}
										className="button-root"
										displayType="secondary"
										onClick={this.handleEditToggle}
										size="sm"
									>
										<ClayIcon
											className="icon-root"
											symbol="times"
										/>
									</ClayButton>

									<ClayButton
										aria-label={Liferay.Language.get(
											'submit'
										)}
										className="button-root"
										disabled={!isValid}
										displayType="primary"
										size="sm"
										type="submit"
									>
										{isSubmitting && (
											<Loading align={Align.Left} />
										)}

										<ClayIcon
											className="icon-root"
											symbol="check"
										/>
									</ClayButton>
								</Form.Group>
							</Form.Form>
						)}
					</Form>
				) : (
					<div
						className="align-items-center d-flex"
						key="NOT_EDITING"
					>
						{value ? <TextTruncate title={value} /> : '-'}

						{editable && (
							<ClayButton
								aria-label={Liferay.Language.get('edit')}
								className="button-root"
								displayType="secondary"
								onClick={this.handleEditToggle}
								size="sm"
							>
								<ClayIcon
									className="icon-root"
									symbol="pencil"
								/>
							</ClayButton>
						)}
					</div>
				)}
			</div>
		);
	}
}
