import ClayButton from '@clayui/button';
import DatePicker from 'shared/components/date-picker';
import Overlay from 'shared/components/Overlay';
import React from 'react';
import Row from '../components/Row';

class OverlayKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<Overlay>
						<ClayButton
							className='button-root'
							displayType='secondary'
						>
							{'Hover Me'}
						</ClayButton>

						<DatePicker />
					</Overlay>
				</Row>

				<Row>
					<Overlay>
						<ClayButton
							className='button-root'
							displayType='secondary'
						>
							{'Nested'}
						</ClayButton>

						<Overlay>
							<ClayButton
								className='button-root'
								displayType='secondary'
							>
								{'Nested 2'}
							</ClayButton>

							<Overlay>
								<ClayButton
									className='button-root'
									displayType='secondary'
								>
									{'Hover Me'}
								</ClayButton>

								<DatePicker />
							</Overlay>
						</Overlay>
					</Overlay>
				</Row>
			</div>
		);
	}
}

export default OverlayKit;
