import {Section} from '../../components/Section/Section';
import {RadioCard} from '../RadioCard/RadioCard';

const paymentModes: PaymentMethodMode[] = ['PayPal'];

export function PaymentMethodMode({
	selectedPaymentMethod,
}: {
	selectedPaymentMethod: PaymentMethodSelector;
}) {
	return (
		<Section className="get-app-modal-section" label="Payment Method">
			{paymentModes.map((paymentMode, i) => {
				return (
					<RadioCard
						key={i}
						onChange={() => {}}
						selected={selectedPaymentMethod === 'pay'}
						small
						title={paymentMode}
					/>
				);
			})}
		</Section>
	);
}
