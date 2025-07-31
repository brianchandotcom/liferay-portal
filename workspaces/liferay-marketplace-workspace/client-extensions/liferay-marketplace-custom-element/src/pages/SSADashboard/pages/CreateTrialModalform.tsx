/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';

import Modal from "../../../components/Modal";
import i18n from '../../../i18n';
import { KeyedMutator } from 'swr';
import { useAccount } from '../../../hooks/data/useAccounts';
import { useCallback, useEffect, useMemo, useState } from 'react';
import { useMarketplaceContext } from '../../../context/MarketplaceContext';
import ProductPurchaseSSATrial from '../../ProductPurchase/services/ProductPurchaseSSATrial';
import trialOAuth2 from '../../../services/oauth/Trial';
import zodSchema from '../../../schema/zod';
import ClayForm, { ClayInput } from '@clayui/form';
import Label from '@clayui/label';
import { FieldGroup } from '../components/SSAForm/FieldGroup';

import { Liferay } from '../../../liferay/liferay';
import { OrderCustomFields } from '../../../enums/Order';
import HeadlessCommerceDeliveryCatalog from '../../../services/rest/HeadlessCommerceDeliveryCatalog';
import Button from '@clayui/button';
import { OrderStatus as Status } from '../../../enums/Order';

export type FormFields = {
    demoDuration: string;
    emailAddress: string;
    objective: string;
    projectId: string;
    site: string;
};

type ValidationErrors = Partial<Record<keyof FormFields, string>>;

type CreateTrialModalFormProps = {
    modal: {
        open: boolean;
        observer: any;
        onClose: () => void;
    };
    mutate: KeyedMutator<APIResponse<Order>>;
};

const CreateTrialModalForm: React.FC<CreateTrialModalFormProps> = ({ modal, mutate }) => {
    const [errors, setErrors] = useState<ValidationErrors>({});
    const [isSubmitting, setIsSubmitting] = useState(false);

    const [formData, setFormData] = useState<FormFields>({
        demoDuration: '',
        emailAddress: '',
        objective: '',
        projectId: '',
        site: '',
    });

    const { channel, properties } = useMarketplaceContext();
    const [product, setProduct] = useState<DeliveryProduct | null>(null);
    const { data: account } = useAccount();

    useEffect(() => {
        const fetchProduct = async () => {
            const product = await HeadlessCommerceDeliveryCatalog.getProduct(
                channel.channelId,
                properties.productId,
                new URLSearchParams({
                    'accountId': '-1',
                    'nestedFields': 'skus',
                    'skus.accountId': '-1',
                })
            );

            setProduct(product);
        };

        fetchProduct();
    }, [channel, properties]);

    const productPurchase = useMemo(() => {
        if (!account || !channel || !product) {
            return null;
        }

        return new ProductPurchaseSSATrial(account, channel, product);
    }, [account, channel, product]);

    const isTestTrial = formData.objective === 'Test';

    useEffect(() => {
        if (isTestTrial) {
            setFormData((prevData) => ({
                ...prevData,
                demoDuration: '1',
            }));

            setErrors((prevErrors) => ({
                ...prevErrors,
                demoDuration: undefined,
            }));
        }
    }, [isTestTrial]);

    const validateProjectId = useCallback(
        async (projectId: string) => {
            try {
                return trialOAuth2.checkDomainAvailability(projectId);
            }
            catch (error: any) {
                console.error(error.message);

                if (error.status === 409) {
                    setErrors((prevErrors) => ({
                        ...prevErrors,
                        projectId: 'Project ID already exists',
                    }));
                }

                return false;
            }
        },
        [setErrors]
    );

    const onChange = ({ label, value }: { label: string; value: string }) => {
        setFormData((prevData) => ({
            ...prevData,
            [label]: value,
        }));

        setErrors((prevErrors) => ({ ...prevErrors, [label]: undefined }));
    };

    const onSubmit = useCallback(async () => {
        setIsSubmitting(true)
        try {
            const result = zodSchema.ssaTrialForm.safeParse(formData);

            if (!result.success) {
                const fieldErrors: ValidationErrors = {};
                for (const error of result.error.errors) {
                    if (error.path.length) {
                        const fieldName = error.path[0] as keyof FormFields;
                        fieldErrors[fieldName] = error.message;
                    }
                }
                setErrors(fieldErrors);

                setIsSubmitting(false);
                return;
            }

            const data = await validateProjectId(formData.projectId);

            if (!data) {
                setIsSubmitting(false)
                return;
            }

            const trialSettings = {
                ...(formData.emailAddress
                    ? { consoleInviteEmailAddresses: [formData.emailAddress] }
                    : {}),
                duration: formData.demoDuration,
                projectId: formData.projectId,
            };

            const newOrder = await productPurchase?.createOrder({
                customFields: {
                    [OrderCustomFields.TRIAL_SETTINGS]:
                        JSON.stringify(trialSettings),
                },
            } as Cart);

            if (newOrder) {

                mutate((orders: any) => ({
                    ...orders, items: [{
                        ...newOrder, orderStatusInfo: {
                            code: 10,
                            label: Status.PROCESSING,
                            label_i18n: Status.PROCESSING
                        }
                    }, ...orders.items]
                }), { revalidate: false });

                setErrors({});

                Liferay.Util.openToast({
                    message: 'Trial is being provisioned.',
                    title: i18n.translate('success'),
                    type: 'success',
                });

                setIsSubmitting(false);

                return modal.onClose();
            }
        } catch (error) {
            console.error(error);

            setIsSubmitting(false);

            Liferay.Util.openToast({
                message: i18n.translate('an-unexpected-error-occurred'),
                type: 'danger',
            });

            modal.onClose();
        }
    }, [formData, modal, productPurchase, validateProjectId]);

    return modal.open && (
        <Modal
            observer={modal.observer}
            size={'md' as any}
            title={i18n.translate('add-new-trial')}
            visible={modal.open}
        >
            <>
                <ClayForm.Group>
                    <div className="mb-5 pr-2 w-100">
                        <h4>{i18n.translate('main')}</h4>

                        <hr className="mb-5" />

                        <Label className="mb-2" >
                            {i18n.translate('project-id')}
                        </Label>

                        <ClayInput.Group>
                            <ClayInput
                                className="bg-white input-group-inset input-group-inset-after marketplace-form-input"
                                maxLength={9}
                                onChange={({ target: { value } }) =>
                                    onChange({ label: 'projectId', value })
                                }
                            />
                            <ClayInput.GroupInsetItem after tag="span">
                                .saas.demo.lxc.liferay.com
                            </ClayInput.GroupInsetItem>
                        </ClayInput.Group>

                        {errors.projectId && (
                            <p className="mb-0 mt-1 text-danger">
                                {errors.projectId}
                            </p>
                        )}
                    </div>
                    <FieldGroup
                        primaryField={{
                            disabled: true,
                            handleChange: onChange,
                            label: 'site',
                            placeholder: i18n.translate('blank-site'),
                            title: i18n.translate('solution'),
                            tooltip: i18n.translate('blank-site'),
                        }}
                    />

                    <FieldGroup
                        primaryField={{
                            error: errors.objective || '',
                            handleChange: onChange,
                            label: 'objective',
                            options: ['Test', 'Trial'],
                            placeholder: i18n.translate('select-an-option'),
                            required: true,
                            title: i18n.translate('objective'),
                            tooltip: i18n.translate('select-an-option'),
                            type: 'select',
                            value: formData.objective,
                        }}
                        secondaryField={{
                            disabled: isTestTrial,
                            error: errors.demoDuration || '',
                            handleChange: onChange,
                            label: 'demoDuration',
                            placeholder: i18n.translate('value-between-1-and-60'),
                            required: true,
                            title: i18n.translate('duration-days'),
                            tooltip: i18n.translate('value-between-1-and-60'),
                            type: 'number',
                            value: isTestTrial ? '1' : formData.demoDuration,
                        }}
                        title="Usage"
                    />
                    <FieldGroup
                        primaryField={{
                            error: errors.emailAddress || '',
                            handleChange: onChange,
                            label: 'emailAddress',
                            title: 'Email Address',
                            tooltip: i18n.translate('email-address'),
                            value: formData.emailAddress,
                        }}
                        title={i18n.translate('additional-admin')}
                    />
                </ClayForm.Group>

                <hr />

                <div className='d-flex justify-content-end'>
                    <Button
                        disabled={isSubmitting}
                        className="mr-2"
                        displayType="secondary"
                        onClick={() => {
                            setIsSubmitting(false);
                            modal.onClose()
                        }}
                    >
                        {i18n.translate('cancel')}
                    </Button>
                    <Button
                        disabled={isSubmitting}
                        displayType="primary"
                        onClick={async () =>
                            await onSubmit()
                        }
                    >
                        <div className="align-items-center d-flex">
                            {isSubmitting && (
                                <ClayLoadingIndicator className="mr-3 my-0" />
                            )}
                            {i18n.translate('create')}
                        </div>
                    </Button>
                </div>
            </>
        </Modal>
    )


}

export default CreateTrialModalForm;