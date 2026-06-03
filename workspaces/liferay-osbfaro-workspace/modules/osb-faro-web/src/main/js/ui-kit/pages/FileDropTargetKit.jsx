import FileDropTarget from 'shared/components/FileDropTarget';
import React from 'react';
import Row from '../components/Row';

class FileDropTargetKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<FileDropTarget uploadURL='/foo' />
				</Row>
			</div>
		);
	}
}

export default FileDropTargetKit;
