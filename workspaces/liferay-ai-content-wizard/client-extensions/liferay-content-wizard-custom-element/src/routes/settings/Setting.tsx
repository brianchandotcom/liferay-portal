import { useNavigate, useOutletContext } from 'react-router-dom';
import { useState } from 'react';
import Button from '@clayui/button';
import ClayIcon from '@clayui/icon';

import { maskApiKey, providerStatuses } from '.';
import Container from '../../components/Container';
import QATable from '../../components/QATable';

export default function Setting() {
    const [hidden, setHidden] = useState(true);
    const navigate = useNavigate();
    const outletContext = useOutletContext<{ setting: any }>();

    const { setting = {} } = outletContext || {};

    return (
        <Container>
            <h1>Setting</h1>

            <QATable
                className="col-6"
                items={[
                    {
                        title: 'Active',
                        value: (
                            <>
                                {' '}
                                <ClayIcon
                                    {...(providerStatuses as any)[
                                        setting.active
                                    ]}
                                />{' '}
                                {String(setting.active)}
                            </>
                        ),
                    },
                    {
                        title: 'API Key',
                        value: (
                            <span className="d-flex align-items-center">
                                {hidden
                                    ? maskApiKey(setting.apiKey)
                                    : setting.apiKey}
                                <ClayIcon
                                    className="ml-2 cursor-pointer"
                                    fontSize={18}
                                    onClick={() => setHidden(!hidden)}
                                    symbol={hidden ? 'view' : 'hidden'}
                                />
                            </span>
                        ),
                    },
                    { title: 'Provider', value: setting.provider.name },
                    { title: 'Model', value: setting.model.name },
                    { title: 'Model Image', value: setting.imageModel.name },
                    { title: 'Description', value: setting.description },
                ]}
                orientation="HORIZONTAL"
            />

            <Button
                className="mt-4"
                displayType="secondary"
                onClick={() => navigate('/')}
            >
                Back
            </Button>
        </Container>
    );
}
