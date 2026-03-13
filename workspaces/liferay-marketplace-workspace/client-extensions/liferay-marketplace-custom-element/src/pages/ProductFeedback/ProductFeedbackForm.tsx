import ClayButton from '@clayui/button';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import {zodResolver} from '@hookform/resolvers/zod';
import {useForm} from 'react-hook-form';
import {z} from 'zod';

import {Input} from '../../components/Input/Input';
import i18n from '../../i18n';
import zodSchema from '../../schema/zod';
import classNames from 'classnames';
import {Rating} from './components/Rating';

type ProductFeedbackFormProps = {
	subtitle?: string;
	title: string;
	onSubmit: (form: z.infer<typeof zodSchema.productFeedback>) => void;
	isSubmitting?: boolean;
};

const ProductFeedbackForm = ({
	title,
	subtitle,
	onSubmit,
	isSubmitting = false,
}: ProductFeedbackFormProps) => {
	const {formState, handleSubmit, register} = useForm<
		z.infer<typeof zodSchema.productFeedback>
	>({
		defaultValues: {
			companyName: '',
			email: '',
			fullName: '',
			jobTitle: '',
			notify: false,
			ratingSatisfaction: 0,
			ratingEaseOfUse: 0,
			ratingUsefulness: 0,
			suggestionFeatures: '',
			suggestionImprovements: '',
			suggestionSatisfaction: '',
		},
		mode: 'all',
		resolver: zodResolver(zodSchema.productFeedback),
	});

	return (
		<div className="border rounded-lg my-7 p-7">
			<div className="mb-6 product-purchase-shell-heading">
				<h1 className="my-4 text-center">{title}</h1>

				{subtitle && <small>{subtitle}</small>}
			</div>

			<h4>{i18n.translate('user-information')}</h4>
			<hr />

			<ClayForm.Group className="mb-0 pt-4">
				<div className="d-flex justify-content-between">
					<div className="form-group mb-0 pr-2 w-50">
						<Input
							{...register('fullName')}
							errorMessage={formState.errors.fullName?.message}
							label={i18n.translate('full-name')}
							required
						/>
					</div>

					<div className="form-group mb-0 pl-2 w-50">
						<Input
							{...register('email')}
							errorMessage={formState.errors.email?.message}
							label={i18n.translate('email')}
							required
						/>
					</div>
				</div>
			</ClayForm.Group>

			<ClayForm.Group className="mb-0">
				<div className="d-flex justify-content-between">
					<div className="form-group mb-0 pr-2 w-50">
						<Input
							{...register('jobTitle')}
							errorMessage={formState.errors.jobTitle?.message}
							label={i18n.translate('job-title')}
						/>
					</div>

					<div className="form-group mb-0 pl-2 w-50">
						<Input
							{...register('companyName')}
							errorMessage={formState.errors.companyName?.message}
							label={i18n.translate('company-name')}
						/>
					</div>
				</div>
			</ClayForm.Group>

			<h4>{i18n.translate('overall-experience')}</h4>
			<small className="text-weight-light">
				{i18n.translate('rate-from-1-poor-to-5-excellent')}
			</small>
			<hr />

			<div
				style={{gap: 24}}
				className="d-flex flex-direction-column py-2"
			>
				<Rating
					{...register('ratingSatisfaction')}
					label={i18n.translate(
						'how-satisfied-are-you-with-the-product'
					)}
				/>

				<Rating
					{...register('ratingEaseOfUse')}
					label={i18n.translate('how-easy-to-use-is-the-product')}
				/>

				<Rating
					{...register('ratingUsefulness')}
					label={i18n.translate(
						'how-useful-is-the-product-for-your-workflow'
					)}
				/>
			</div>
			<div className="pt-7">
				<h4>{i18n.translate('suggestions-and-priorities')}</h4>

				<hr />

				<div className="pt-4">
					<Input
						{...register('suggestionFeatures')}
						errorMessage={
							formState.errors?.suggestionFeatures?.message
						}
						label={i18n.translate(
							'what-features-would-you-like-in-the-final-release'
						)}
					/>

					<Input
						{...register('suggestionImprovements')}
						errorMessage={
							formState.errors?.suggestionImprovements?.message
						}
						label={i18n.translate(
							'if-you-could-choose-3-top-priorities-for-improvements-what-would-they-be'
						)}
					/>

					<Input
						{...register('suggestionSatisfaction')}
						component="textarea"
						errorMessage={
							formState.errors?.suggestionSatisfaction?.message
						}
						label={i18n.translate(
							'could-you-elaborate-on-the-reasons-for-your-degree-of-satisfaction'
						)}
					/>
				</div>
			</div>

			<ClayForm.Group
				className={classNames('mt-4', {
					'has-error': formState.errors.notify?.message,
				})}
			>
				<ClayCheckbox
					{...({} as any)}
					{...register('notify')}
					id="accept-terms"
					label="Notify me about products, services, and events."
				/>

				<ClayForm.FeedbackItem>
					{formState.errors.notify?.message}
				</ClayForm.FeedbackItem>

				<label className="font-weight-normal" htmlFor="accept-terms">
					You can stop receiving marketing emails by clicking the
					unsubscribe link in each email or withdraw your consent at
					any time by either using opt-out functionality accessible
					through the messages you receive or via email to
					<a
						className="ml-1"
						href="mailto:dataprotection@liferay.com"
					>
						dataprotection@liferay.com
					</a>
					. See{' '}
					<a
						href="https://www.liferay.com/privacy-policy"
						target="_blank"
					>
						Privacy Policy.
					</a>{' '}
					for details.
				</label>
			</ClayForm.Group>

			<ClayButton
				disabled={!formState.isValid || isSubmitting}
				className="w-100"
				onClick={handleSubmit(onSubmit)}
				type="submit"
			>
				{isSubmitting ? 'Submitting...' : i18n.translate('submit')}
			</ClayButton>
		</div>
	);
};

export default ProductFeedbackForm;
