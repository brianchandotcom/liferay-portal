import autobind from 'autobind-decorator';
import Input from 'shared/components/Input';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import {createTextMaskInputElement} from 'text-mask-core';
import {isFunction} from 'lodash';
import {PropTypes} from 'prop-types';

class MaskedInput extends React.Component {
	static defaultProps = {
		guide: true,
		keepCharPositions: false,
		placeholderChar: '_',
		showMask: false
	};

	static propTypes = {
		guide: PropTypes.bool,
		keepCharPositions: PropTypes.bool,
		mask: PropTypes.oneOfType([
			PropTypes.array,
			PropTypes.func,
			PropTypes.bool,
			PropTypes.shape({
				mask: PropTypes.oneOfType([PropTypes.array, PropTypes.func]),
				pipe: PropTypes.func
			})
		]).isRequired,
		onChange: PropTypes.func,
		pipe: PropTypes.func,
		placeholderChar: PropTypes.string,
		showMask: PropTypes.bool,
		value: PropTypes.oneOfType([PropTypes.string, PropTypes.number])
	};

	constructor(props) {
		super(props);

		this._inputRef = React.createRef();
	}

	componentDidMount() {
		this.textMaskInputElement_ = createTextMaskInputElement({
			inputElement: this._inputRef.current._elementRef.current,
			...this.props
		});
	}

	componentDidUpdate(firstRender) {
		const {value} = this.props;

		if (!firstRender && this.textMaskInputElement_ && value) {
			this.textMaskInputElement_.update(value);
		}
	}

	@autobind
	handleChange(event) {
		this.textMaskInputElement_.update(event.target.value);

		if (isFunction(this.props.onChange)) {
			this.props.onChange(event);
		}
	}

	render() {
		const {className, value, ...otherProps} = this.props;

		return (
			<Input
				{...omitDefinedProps(otherProps, MaskedInput.propTypes)}
				className={className}
				onChange={this.handleChange}
				ref={this._inputRef}
				value={value}
			/>
		);
	}
}

export default MaskedInput;
