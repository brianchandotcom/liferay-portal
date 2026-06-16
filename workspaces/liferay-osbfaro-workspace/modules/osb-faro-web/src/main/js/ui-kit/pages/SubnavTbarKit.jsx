import ClayButton from '@clayui/button';
import React from 'react';
import SubnavTbar from 'shared/components/SubnavTbar';

class SubnavTbarKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<SubnavTbar>
					<SubnavTbar.Item>
						<ClayButton
							borderless
							className='button-root'
							displayType='unstyled'
						>
							{'8 Items Selected'}
						</ClayButton>
					</SubnavTbar.Item>
					<SubnavTbar.Item expand>
						<ClayButton
							borderless
							className='button-root'
							displayType='unstyled'
						>
							{'SubnavTbar Action'}
						</ClayButton>
					</SubnavTbar.Item>
					<SubnavTbar.Item>
						<ClayButton
							borderless
							className='button-root'
							displayType='unstyled'
						>
							{'Undo All'}
						</ClayButton>
					</SubnavTbar.Item>
				</SubnavTbar>

				<SubnavTbar>
					<SubnavTbar.Item expand>
						<ClayButton
							borderless
							className='button-root'
							displayType='unstyled'
						>
							{'SubnavTbar Action'}
						</ClayButton>
					</SubnavTbar.Item>
					<SubnavTbar.Item>
						<ClayButton
							borderless
							className='button-root'
							displayType='unstyled'
						>
							{'Undo All'}
						</ClayButton>
					</SubnavTbar.Item>
				</SubnavTbar>
			</div>
		);
	}
}

export default SubnavTbarKit;
