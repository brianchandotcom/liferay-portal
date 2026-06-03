import autobind from 'autobind-decorator';
import getCN from 'classnames';
import HelpBlock from 'shared/components/form/HelpBlock';
import Label from 'shared/components/form/Label';
import PropTypes from 'prop-types';
import React from 'react';
import SelectFieldInput from '../SelectFieldInput';
import {FieldContexts} from 'shared/util/constants';
import {withField} from 'shared/components/form';

export class FormSelectFieldInput extends React.Component {
	static defaultProps = {
		context: FieldContexts.Demographics,
		required: false,
		showHelpBlock: true
	};

	static propTypes = {
		context: PropTypes.string,
		field: PropTypes.shape({
			name: PropTypes.string,
			onBlur: PropTypes.func,
			onChange: PropTypes.func,
			value: PropTypes.any
		}),
		form: PropTypes.shape({
			errors: PropTypes.object,
			touched: PropTypes.object
		}),
		groupId: PropTypes.string.isRequired,
		label: PropTypes.string,
		onSelect: PropTypes.func,
		popover: PropTypes.shape({
			content: PropTypes.node,
			title: PropTypes.node
		}),
		required: PropTypes.bool,
		showHelpBlock: PropTypes.bool
	};

	@autobind
	handleSelect(value) {
		const {
			field: {name},
			form: {setFieldValue},
			onSelect
		} = this.props;

		setFieldValue(name, value);

		if (onSelect) {
			onSelect(value);
		}
	}

	render() {
		const {
			className,
			context,
			field: {name, onBlur, value},
			form,
			groupId,
			innerRef,
			label,
			popover,
			required,
			showHelpBlock
		} = this.props;

		const error = form.errors[name];
		const touched = form.touched[name];

		const classes = getCN(className, {
			'has-error': error && touched
		});

		return (
			<div className={classes}>
				{label && (
					<Label htmlFor={name} popover={popover} required={required}>
						{label}
					</Label>
				)}

				<SelectFieldInput
					context={context}
					groupId={groupId}
					id={name}
					name={name}
					onBlur={onBlur}
					onSelect={this.handleSelect}
					ref={innerRef}
					selectedItem={value}
				/>

				{showHelpBlock && <HelpBlock name={name} />}
			</div>
		);
	}
}

export default withField(FormSelectFieldInput);
