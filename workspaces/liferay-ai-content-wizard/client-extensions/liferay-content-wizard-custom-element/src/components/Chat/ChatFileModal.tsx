import ClayModal, { useModal } from '@clayui/modal';
import ClayButton from '@clayui/button';
import ChatFileModalExplorer from './ChatFileModalExplorer';

type Props = {
    items: any[];
    modal: ReturnType<typeof useModal>;
    onChoose: (data: any) => void;
    selectedTree: any;
    setSelectedTree: React.Dispatch<any>;
};

const ChatFileModal: React.FC<Props> = ({
    modal,
    selectedTree,
    onChoose,
    setSelectedTree,
    items,
}) => (
    <ClayModal disableAutoClose observer={modal.observer}>
        <ClayModal.Header>
            <ClayModal.Title>
                Choose the Documents and Media Files
            </ClayModal.Title>
        </ClayModal.Header>
        <ClayModal.Body>
            <ChatFileModalExplorer
                items={items}
                setSelectedTree={setSelectedTree}
            />
        </ClayModal.Body>
        <ClayModal.Footer
            last={
                <>
                    <ClayButton
                        className="mr-2"
                        onClick={modal.onClose}
                        displayType="secondary"
                    >
                        Cancel
                    </ClayButton>

                    <ClayButton disabled={!selectedTree} onClick={onChoose}>
                        Choose
                    </ClayButton>
                </>
            }
        />
    </ClayModal>
);

export default ChatFileModal;
