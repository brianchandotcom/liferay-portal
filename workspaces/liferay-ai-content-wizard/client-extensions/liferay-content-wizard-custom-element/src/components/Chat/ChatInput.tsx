import { useEffect, useRef, useState } from 'react';
import { UseFormReturn } from 'react-hook-form';
import { useModal } from '@clayui/modal';
import ClayButton from '@clayui/button';
import ClayForm, { ClayInput } from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import DropDown, { Align } from '@clayui/drop-down';

import { Schema } from '../AIWizard';
import ChatFileModal from './ChatFileModal';
import useSiteDocuments from '../../hooks/useSiteDocuments';

type Props = {
    form: UseFormReturn<Schema>;
    onSubmit: (data: Schema) => void;
    placeholder?: string;
};

export default function ChatInput(props: Props) {
    const [selectedTree, setSelectedTree] = useState<any>();
    const { handleSubmit, formState, setValue, register, watch } = props.form;
    const formRef = useRef<HTMLFormElement>(null);
    const [image, setImage] = useState('');
    const modal = useModal();
    const files = watch('files');
    const inputRef = useRef<HTMLInputElement>(null);
    const text = watch('input');
    const { data: response } = useSiteDocuments();

    useEffect(() => {
        const modalContainer = document.querySelector('.ai-parent-modal div');

        if (!modal.open && modalContainer) {
            modalContainer.removeAttribute('inert');
        }
    }, [modal.open]);

    const handleKeyDown = (event: any) => {
        if (event.key === 'Enter') {
            if (!event.shiftKey && text.trim() !== '') {
                event.preventDefault();
                formRef.current?.requestSubmit();
            }
        }
    };

    const onChoose = () => {
        setValue('files', [
            ...files,
            { type: 'fileEntryId', value: selectedTree.image },
        ]);

        modal.onClose();
    };

    const resetInputs = () => {
        setImage('');

        if (inputRef.current) {
            inputRef.current.value = '';
        }
    };

    return (
        <ClayForm
            ref={formRef}
            className="d-flex w-100"
            onSubmit={(event) =>
                handleSubmit(props.onSubmit)(event).then(resetInputs)
            }
        >
            {modal.open && (
                <ChatFileModal
                    items={response?.data?.documents?.items ?? []}
                    modal={modal}
                    setSelectedTree={setSelectedTree}
                    onChoose={onChoose}
                    selectedTree={selectedTree}
                />
            )}

            <ClayLayout.ContentRow className="w-100">
                <ClayLayout.ContentCol expand>
                    <ClayInput
                        {...register('input')}
                        value={text}
                        onKeyDown={handleKeyDown}
                        component="textarea"
                        disabled={formState.isSubmitting || formState.isLoading}
                        placeholder={
                            props.placeholder ||
                            'Ask the Assistant to create a Liferay Asset'
                        }
                    />

                    <ClayInput
                        {...register('image')}
                        value={image}
                        id="wizard-content-image"
                        type="hidden"
                    />
                    <ClayInput
                        ref={inputRef}
                        onChange={(event) => {
                            if (event.target.files?.[0]) {
                                const reader = new FileReader();
                                reader.readAsDataURL(event.target.files[0]);
                                reader.onload = () => {
                                    setImage(reader.result as string);
                                };
                            }
                        }}
                        className="d-none"
                        type="file"
                    />

                    <div className="d-flex my-2">
                        {image ? <img width={150} src={image} /> : null}
                    </div>

                    {files.length > 0 && (
                        <div className="d-flex my-2">
                            {files.map((file, index) => (
                                <img
                                    key={index}
                                    draggable={false}
                                    className="rounded border p-1 mr-1"
                                    height={60}
                                    width={60}
                                    src={(file as any).value}
                                />
                            ))}
                        </div>
                    )}
                </ClayLayout.ContentCol>
                <ClayLayout.ContentCol>
                    <ClayLayout.ContentSection>
                        <ClayButton.Group>
                            <DropDown
                                alignmentPosition={Align.BottomLeft}
                                trigger={
                                    <ClayButton
                                        aria-label="Submit Prompt button"
                                        borderless
                                        disabled={
                                            formState.isSubmitting ||
                                            formState.isLoading
                                        }
                                    >
                                        <ClayIcon
                                            color="gray"
                                            aria-label="Submit Prompt"
                                            symbol="upload-multiple"
                                        />
                                    </ClayButton>
                                }
                            >
                                <DropDown.ItemList>
                                    <DropDown.Item
                                        onClick={() => {
                                            modal.onOpenChange(true);
                                        }}
                                    >
                                        <ClayIcon
                                            className="mr-2"
                                            aria-label="Picture Icon"
                                            symbol="picture"
                                        />
                                        Choose from Docs & Media
                                    </DropDown.Item>
                                    <DropDown.Item
                                        onClick={() => {
                                            inputRef.current?.click();
                                        }}
                                    >
                                        <ClayIcon
                                            className="mr-2"
                                            symbol="display"
                                        />
                                        Upload from your computer
                                    </DropDown.Item>
                                </DropDown.ItemList>
                            </DropDown>

                            <ClayButton
                                disabled={
                                    formState.isSubmitting ||
                                    formState.isLoading ||
                                    !text?.trim()?.length
                                }
                                displayType="primary"
                                aria-label="Submit button"
                                type="submit"
                            >
                                <ClayIcon
                                    aria-label="Submit Prompt"
                                    symbol="order-arrow-right"
                                />
                            </ClayButton>
                        </ClayButton.Group>
                    </ClayLayout.ContentSection>
                </ClayLayout.ContentCol>
            </ClayLayout.ContentRow>
        </ClayForm>
    );
}
