import * as data from 'test/data';
import ClayButton from '@clayui/button';
import Form, {
	validateMaxLength,
	validateMinLength,
	validateRequired
} from 'shared/components/form';
import FormSelectFieldInput from 'contacts/components/form/SelectFieldInput';
import Item from '../components/Item';
import React from 'react';
import Row from '../components/Row';
import {times} from 'lodash';

const items = times(8, i => ({name: `item${i}`, value: `value${i}`}));

const FormWrapper = ({children, initialValues = {}, title}) => (
	<Row>
		<Form
			initialValues={initialValues}
			mapPropsToValues={() => initialValues}
			onSubmit={(values, actions) => {
				alert(JSON.stringify(values, null, 2));
				actions.setSubmitting(false);
			}}
		>
			{({handleReset, handleSubmit}) => (
				<Form.Form onSubmit={handleSubmit}>
					<h2>{title}</h2>
					{React.Children.map(children, child => (
						<Item>{child}</Item>
					))}
					<ClayButton
						className='button-root'
						displayType='primary'
						type='submit'
					>
						{'Submit'}
					</ClayButton>
					<ClayButton
						className='button-root econdary'
						displayType='secondary'
						onClick={handleReset}
					>
						{'Reset'}
					</ClayButton>
				</Form.Form>
			)}
		</Form>
	</Row>
);

export default class FormKit extends React.Component {
	render() {
		const {groupId} = this.props;

		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<FormWrapper
					initialValues={{
						maxLength: '',
						minLength: '',
						name: '',
						passwordInput: 'old password'
					}}
					title='Input'
					validateOnChange={false}
				>
					<Form.Input
						label='Required'
						name='name'
						placeholder='required input'
						required
						type='text'
						validate={validateRequired}
					/>

					<Form.Input
						label='Min Length'
						name='minLength'
						placeholder='min 5 char'
						type='text'
						validate={validateMinLength(5)}
					/>

					<Form.Input
						label='Max Length'
						name='maxLength'
						placeholder='max 5 char'
						type='text'
						validate={validateMaxLength(5)}
					/>

					<Form.PasswordInput
						label='Password Input'
						name='passwordInput'
						required
						validate={validateRequired}
					/>
				</FormWrapper>

				<FormWrapper
					initialValues={{radioGroup: 'rad1'}}
					title='RadioGroups'
				>
					<Form.RadioGroup label='RadioGroup' name='radioGroup'>
						<Form.RadioGroup.Option
							key='rad1'
							label='Option 1'
							value='rad1'
						/>

						<Form.RadioGroup.Option
							key='rad2'
							label='Option 2'
							value='rad2'
						/>
					</Form.RadioGroup>
				</FormWrapper>

				<FormWrapper
					initialValues={{
						checkbox: false,
						checkbox1: true
					}}
					title='Checkbox'
				>
					<Form.Checkbox
						label='Checkbox'
						name='checkbox'
						value='human'
					/>

					<Form.Checkbox
						label='Pre-checked Checkbox'
						name='checkbox1'
						value='dog'
					/>
				</FormWrapper>

				<FormWrapper
					initialValues={{
						select: undefined,
						select1: 'green'
					}}
					title='Select'
				>
					<Form.Select
						label='Select'
						name='select'
						required
						showBlankOption
						validate={validateRequired}
					>
						<Form.Select.Item value='red'>{'red'}</Form.Select.Item>
						<Form.Select.Item value='green'>
							{'green'}
						</Form.Select.Item>
						<Form.Select.Item value='blue'>
							{'blue'}
						</Form.Select.Item>
					</Form.Select>

					<Form.Select label='Select1' name='select1'>
						<Form.Select.Item value='red'>{'red'}</Form.Select.Item>
						<Form.Select.Item value='green'>
							{'green'}
						</Form.Select.Item>
						<Form.Select.Item value='blue'>
							{'blue'}
						</Form.Select.Item>
					</Form.Select>
				</FormWrapper>

				<FormWrapper
					initialValues={{
						toggle1: false,
						toggle2: true
					}}
					title='Toggle Switch'
				>
					<Form.ToggleSwitch label='Toggle Switch' name='toggle1' />

					<Form.ToggleSwitch label='Toggle Switch' name='toggle2' />
				</FormWrapper>

				<FormWrapper initialValues={{date: ''}} title='Date Input'>
					<Form.DateInput
						label='Date'
						name='date'
						placeholder='required date'
						required
						validate={validateRequired}
					/>
				</FormWrapper>

				<FormWrapper
					initialValues={{
						searchableSelect: {name: 'item0', value: 'value0'},
						searchableSelect1: undefined
					}}
					title='Searchable Select'
				>
					<Form.SearchableSelect
						items={items}
						label='SearchableSelect'
						name='searchableSelect'
						validate={validateRequired}
					/>

					<Form.SearchableSelect
						items={items}
						label='SearchableSelect1'
						name='searchableSelect1'
						validate={validateRequired}
					/>
				</FormWrapper>

				<FormWrapper
					initialValues={{
						selectFieldInput: data.mockFieldMapping('foo')
					}}
					title='Select Field Input'
				>
					<FormSelectFieldInput
						groupId={groupId}
						label='SelectFieldInput'
						name='selectFieldInput'
					/>
				</FormWrapper>
			</div>
		);
	}
}
