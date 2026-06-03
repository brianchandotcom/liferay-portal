import createNumberMask from 'text-mask-addons/dist/createNumberMask';
import Item from '../components/Item';
import MaskedInput from 'shared/components/MaskedInput';
import React from 'react';
import Row from '../components/Row';

const maskFn = rawValue => {
	rawValue = rawValue.replace('NUMBER', '');
	rawValue = rawValue.replace('STRING', '');

	const isNumber = !isNaN(Number(rawValue));

	if (isNumber) {
		return 'NUMBER'.split('');
	} else {
		return 'STRING'.split('');
	}
};

class MaskedInputKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<Item>
						<MaskedInput
							mask={[
								'(',
								/[1-9]/,
								/\d/,
								/\d/,
								')',
								' ',
								/\d/,
								/\d/,
								/\d/,
								'-',
								/\d/,
								/\d/,
								/\d/,
								/\d/
							]}
							placeholder='Phone Number...'
							showMask
						/>
					</Item>

					<Item>
						<MaskedInput
							mask={[
								/\d/,
								/\d/,
								'/',
								/\d/,
								/\d/,
								'/',
								/\d/,
								/\d/,
								/\d/,
								/\d/
							]}
							placeholder='Date...'
							showMask
						/>
					</Item>
				</Row>

				<Row>
					<Item>
						<MaskedInput
							mask={[
								/\d/,
								/\d/,
								/\d/,
								/\d/,
								' ',
								/\d/,
								/\d/,
								/\d/,
								/\d/,
								' ',
								/\d/,
								/\d/,
								/\d/,
								/\d/,
								' ',
								/\d/,
								/\d/,
								/\d/,
								/\d/
							]}
							placeholder='Credit Card...'
						/>
					</Item>

					<Item>
						<MaskedInput mask={maskFn} showMask />
					</Item>
				</Row>

				<Row>
					<Item>
						<MaskedInput
							mask={createNumberMask({prefix: ''})}
							placeholder='Number...'
						/>
					</Item>
				</Row>
			</div>
		);
	}
}

export default MaskedInputKit;
