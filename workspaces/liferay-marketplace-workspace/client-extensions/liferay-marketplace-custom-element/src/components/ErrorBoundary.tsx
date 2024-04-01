import {Component, ErrorInfo} from 'react';

type ErrorBoundaryProps = {
	className?: string;
	children: any;
	errorMessage?: string;
};

type ErrorBoundaryState = {
	hasError: boolean;
};

export default class ErrorBoundary extends Component<
	ErrorBoundaryProps,
	ErrorBoundaryState
> {
	constructor(props: ErrorBoundaryProps) {
		super(props);
		this.state = {hasError: false};
	}

	componentDidCatch(error: Error, errorInfo: ErrorInfo) {
		console.error('Error caught by ErrorBoundary:', error, errorInfo);

		this.setState({hasError: true});
	}

	render() {
		if (this.state.hasError) {
			return (
				<div className={this.props.className}>
					{this.props.errorMessage || 'Something went wrong.'}
				</div>
			);
		}

		return this.props.children;
	}
}
