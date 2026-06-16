import ProgressBar from 'shared/components/ProgressBar';
import React from 'react';
import Row from '../components/Row';

class ProgressBarKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<ProgressBar value={0} />
				</Row>

				<Row>
					<ProgressBar value={25} />
				</Row>

				<Row>
					<ProgressBar value={50} />
				</Row>

				<Row>
					<ProgressBar value={75} />
				</Row>

				<Row>
					<ProgressBar complete />
				</Row>
			</div>
		);
	}
}

export default ProgressBarKit;
