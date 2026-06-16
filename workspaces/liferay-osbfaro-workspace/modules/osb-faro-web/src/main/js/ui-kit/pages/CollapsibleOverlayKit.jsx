import ClayButton from '@clayui/button';
import CollapsibleOverlay from 'shared/components/CollapsibleOverlay';
import React, {useState} from 'react';

export default () => {
	const [visible, setVisible] = useState(true);

	return (
		<div style={{height: '500px', position: 'relative'}}>
			<ClayButton
				className='button-root'
				displayType='secondary'
				onClick={() => setVisible(true)}
			>
				{'toggle collapsible'}
			</ClayButton>

			<CollapsibleOverlay
				onClose={() => setVisible(false)}
				title='Collapsible Title'
				visible={visible}
			>
				<div style={{padding: '16px'}}>{'Collapsible Child'}</div>
			</CollapsibleOverlay>
		</div>
	);
};
