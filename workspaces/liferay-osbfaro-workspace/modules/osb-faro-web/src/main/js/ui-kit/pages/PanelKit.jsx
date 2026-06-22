import Panel from 'shared/components/Panel';
import React from 'react';

class PanelKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<div>
					<Panel title='Panel Title'>{'Panel Content'}</Panel>
				</div>
				<div>
					<Panel expandable title='Expandable Panel Title'>
						{'Expandable Panel Content'}
					</Panel>
				</div>
				<div>
					<Panel
						expandable
						initialExpanded
						title='Initially Expanded Expandable Panel Title'
					>
						{'Initially Expanded Expandable Panel Content'}
					</Panel>
				</div>
			</div>
		);
	}
}

export default PanelKit;
