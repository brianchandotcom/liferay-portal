import { createPortal } from 'react-dom';
import { useEffect, useState } from 'react';
import { useModal } from '@clayui/modal';

import AIWizard from '../components/AIWizard';
import DisplayIcon from '../components/DisplayIcon';
import DisplayButton from '../components/DisplayButton';

function App() {
    const [container, setContainer] = useState<{
        sidebar: HTMLElement | null;
        topbar: HTMLElement | null;
    }>({
        sidebar: null,
        topbar: null,
    });

    const modal = useModal({ defaultOpen: false });

    useEffect(() => {
        setContainer({
            sidebar: document.querySelector('#productMenuSidebar'),
            topbar: document.querySelector(
                '.control-menu-nav-item.layout-reports-icon'
            ),
        });
    }, []);

    return (
        <>
            {modal.open && <AIWizard modal={modal} />}

            {container.topbar &&
                createPortal(
                    <DisplayIcon onClick={() => modal.onOpenChange(true)} />,
                    container.topbar
                )}

            {container.sidebar &&
                createPortal(
                    <DisplayButton onClick={() => modal.onOpenChange(true)} />,
                    container.sidebar
                )}
        </>
    );
}

export default App;
