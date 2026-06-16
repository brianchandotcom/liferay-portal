import Checkbox from 'shared/components/Checkbox';
import Item from '../components/Item';
import React from 'react';
import Row from '../components/Row';
import {noop} from 'lodash';

class CheckboxKit extends React.Component {
	render() {
		return (
			<div>
				<Row>
					<Item>
						<Checkbox onChange={noop} />
					</Item>
					<Item>
						<Checkbox checked onChange={noop} />
					</Item>
					<Item>
						<Checkbox checked indeterminate onChange={noop} />
					</Item>
				</Row>

				<Row>
					<Item>
						<Checkbox label='Checkbox Label' onChange={noop} />
					</Item>
					<Item>
						<Checkbox checked onChange={noop} />
					</Item>
					<Item>
						<Checkbox checked indeterminate onChange={noop} />
					</Item>
				</Row>
			</div>
		);
	}
}

export default CheckboxKit;
