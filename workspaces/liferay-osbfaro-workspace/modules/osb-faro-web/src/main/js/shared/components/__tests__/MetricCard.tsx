import MetricCard from '../MetricCard';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {TrendClassification} from 'segment/types';

describe('MetricCard', () => {
	afterEach(cleanup);

	const defaultProps = {
		description: 'Test description',
		renderTrendLabel: (percentageNode: React.ReactNode) => (
			<>
				{percentageNode} {'vs last period'}
			</>
		),
		title: 'Test title',
		value: 42,
	};

	it('should render', () => {
		const {container} = render(<MetricCard {...defaultProps} />);

		expect(container).toMatchSnapshot();
	});

	it('should render the title, description, and value', () => {
		const {getByText} = render(<MetricCard {...defaultProps} />);

		expect(getByText('Test title')).toBeInTheDocument();
		expect(getByText('Test description')).toBeInTheDocument();
		expect(getByText('42')).toBeInTheDocument();
	});

	it('should render Loading when loading is true', () => {
		const {container, queryByText} = render(
			<MetricCard {...defaultProps} loading />
		);

		expect(queryByText('Test title')).toBeNull();
		expect(queryByText('Test description')).toBeNull();
		expect(container.querySelector('.loading-root')).toBeTruthy();
	});

	it('should render a positive trend with the up caret icon', () => {
		const {container, getByText} = render(
			<MetricCard
				{...defaultProps}
				trend={{
					percentage: 10,
					trendClassification: TrendClassification.Positive,
				}}
			/>
		);

		expect(
			container.querySelector('.lexicon-icon-caret-top-l')
		).toBeTruthy();
		expect(getByText('10%')).toBeInTheDocument();
		expect(getByText('vs last period')).toBeInTheDocument();
	});

	it('should render a negative trend with the down caret icon', () => {
		const {container, getByText} = render(
			<MetricCard
				{...defaultProps}
				trend={{
					percentage: -5,
					trendClassification: TrendClassification.Negative,
				}}
			/>
		);

		expect(
			container.querySelector('.lexicon-icon-caret-bottom-l')
		).toBeTruthy();
		expect(getByText('5%')).toBeInTheDocument();
	});

	it('should render a neutral trend without an icon', () => {
		const {container, getByText} = render(
			<MetricCard
				{...defaultProps}
				trend={{
					percentage: 0,
					trendClassification: TrendClassification.Neutral,
				}}
			/>
		);

		expect(container.querySelector('.lexicon-icon-caret-top-l')).toBeNull();
		expect(
			container.querySelector('.lexicon-icon-caret-bottom-l')
		).toBeNull();
		expect(getByText('0%')).toBeInTheDocument();
	});

	it('should not render a trend icon when trend is undefined', () => {
		const {container} = render(<MetricCard {...defaultProps} />);

		expect(container.querySelector('.lexicon-icon-caret-top-l')).toBeNull();
		expect(
			container.querySelector('.lexicon-icon-caret-bottom-l')
		).toBeNull();
	});

	it('should not render the trend when renderTrendLabel is omitted', () => {
		const {container, getByText, queryByText} = render(
			<MetricCard
				description={defaultProps.description}
				title={defaultProps.title}
				trend={{
					percentage: 10,
					trendClassification: TrendClassification.Positive,
				}}
				value={defaultProps.value}
			/>
		);

		expect(getByText('42')).toBeInTheDocument();
		expect(queryByText('10%')).toBeNull();
		expect(queryByText('vs last period')).toBeNull();
		expect(container.querySelector('.lexicon-icon-caret-top-l')).toBeNull();
	});

	it('should keep the trend row height when renderTrendLabel is omitted so the value stays aligned', () => {
		const {getByTestId} = render(
			<MetricCard
				description={defaultProps.description}
				title={defaultProps.title}
				value={defaultProps.value}
			/>
		);

		expect(getByTestId('metric-card-trend').textContent).toBe('\u00A0');
	});

	it('should apply className and trendClassName', () => {
		const {container} = render(
			<MetricCard
				{...defaultProps}
				className="custom-card"
				trend={{
					percentage: 10,
					trendClassification: TrendClassification.Positive,
				}}
				trendClassName="custom-trend"
			/>
		);

		expect(container.querySelector('.custom-card')).toBeTruthy();
		expect(container.querySelector('.custom-trend')).toBeTruthy();
	});

	it('should render a ReactNode value', () => {
		const {getByTestId} = render(
			<MetricCard
				{...defaultProps}
				value={<span data-testid="custom-value">{'5 accounts'}</span>}
			/>
		);

		expect(getByTestId('custom-value')).toBeInTheDocument();
	});

	it('should round the percentage to one decimal', () => {
		const {getByText} = render(
			<MetricCard
				{...defaultProps}
				trend={{
					percentage: 12.3456,
					trendClassification: TrendClassification.Positive,
				}}
			/>
		);

		expect(getByText('12.3%')).toBeInTheDocument();
	});
});
