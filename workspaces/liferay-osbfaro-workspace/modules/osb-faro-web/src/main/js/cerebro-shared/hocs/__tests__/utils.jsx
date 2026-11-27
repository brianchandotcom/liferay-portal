import React from 'react';
import {render} from '@testing-library/react';
import {withEmpty, withError} from '../utils';

const MyAwesomeComponent = () => <div>{'my awesome component'}</div>;

describe('withEmpty', () => {
	it('should render with emptyTitle if query exists & items is empty', () => {
		const ComposedComponent = withEmpty({emptyTitle: 'an empty title'})(
			MyAwesomeComponent
		);

		const {getByText} = render(
			<ComposedComponent items={[]} total={0} />
		);

		expect(getByText('an empty title')).toBeInTheDocument();
	});

	it('should render with default no results display if total is 0 & items is empty and has query', () => {
		const ComposedComponent = withEmpty({emptyTitle: 'an empty title'})(
			MyAwesomeComponent
		);

		const {getByText} = render(
			<ComposedComponent items={[]} query='asdf' total={0} />
		);

		expect(getByText(/^no results were found$/i)).toBeInTheDocument();
		expect(
			getByText(/^please try a different search term\.$/i)
		).toBeInTheDocument();
	});

	it('should render with default no results display if total is 0 & items is empty and has no query', () => {
		const ComposedComponent = withEmpty({
			emptyDescription: 'an empty description',
			emptyTitle: 'an empty title'
		})(MyAwesomeComponent);

		const {getByText} = render(
			<ComposedComponent items={[]} total={0} />
		);

		expect(getByText('an empty title')).toBeInTheDocument();
		expect(getByText('an empty description')).toBeInTheDocument();
	});

	it('should not render empty state when total is greater than 0 & items is empty', () => {
		const ComposedComponent = withEmpty()(MyAwesomeComponent);

		const {getByText} = render(
			<ComposedComponent items={[]} total={1} />
		);

		expect(getByText('my awesome component')).toBeInTheDocument();
	});

	it('should not render empty state if items exists & total is equal 0', () => {
		const ComposedComponent = withEmpty({emptyTitle: 'an empty title'})(
			MyAwesomeComponent
		);

		const {getByText} = render(
			<ComposedComponent items={['test']} total={0} />
		);

		expect(getByText('my awesome component')).toBeInTheDocument();
	});

	it('should not render empty state when items is not empty', () => {
		const ComposedComponent = withEmpty()(MyAwesomeComponent);

		const {getByText} = render(
			<ComposedComponent items={['test']} total={1} />
		);

		expect(getByText('my awesome component')).toBeInTheDocument();
	});
});

describe('withError', () => {
	it('should render error state when "error" props is true', () => {
		const ComposedComponent = withError()(MyAwesomeComponent);

		const {getByText} = render(<ComposedComponent error />);

		expect(getByText(/^sorry, an error occurred\.$/i)).toBeInTheDocument();
	});

	it('should not render error state when "error" props is false', () => {
		const ComposedComponent = withError()(MyAwesomeComponent);

		const {getByText} = render(<ComposedComponent error={false} />);

		expect(getByText('my awesome component')).toBeInTheDocument();
	});

	it('should render custom error state message when "error" props is true', () => {
		const ComposedComponent = withError()(MyAwesomeComponent);

		const {getByText} = render(
			<ComposedComponent error errorMessage='Sorry, it is an error!' />
		);

		expect(getByText('Sorry, it is an error!')).toBeInTheDocument();
	});
});
