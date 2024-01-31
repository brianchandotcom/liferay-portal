/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayButtonGroup from '@clayui/button/lib/Group';
import {Body, Cell, Head, Row, Table} from '@clayui/core';
import ClayIcon from "@clayui/icon";
import ClayModal, {Context as ModalContext, useModal} from '@clayui/modal';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClayToolbar from '@clayui/toolbar';
import moment from 'moment';
import React, {useCallback, useContext, useEffect, useState} from 'react';

import {
    deleteFolderTemplateInformation,
    getAvailableTemplatesPage,
} from '../../services/TemplateListService';
import {showError} from '../../utils/util';
import Diagram from '../template-diagram/Diagram';
import TemplateItemCreateFolder from './controls/template-item-create-folder/TemplateItemCreateFolder';
import NewTemplateItem from './controls/template-item-create/NewTemplateItem';

const DELTAS = [{label: 5}, {label: 10}, {label: 20}, {label: 40}];

const MODAL_OPEN = 'OPEN';

const HEADERS = [
    {
        expanded: false,
        key: 'id',
        label: 'ID',
        wrap: false,
    },
    {
        expanded: true,
        key: 'templateName',
        label: 'Template Name',
        wrap: false,
    },
    {
        expanded: false,
        key: 'dateCreated',
        label: 'Created Date',
        wrap: false,
    },
    {
        expanded: false,
        key: 'actions',
        label: '',
        wrap: false,
    },
];

const TemplateList = () => {
    const [data, setData] = useState([]);
    const [delta, setDelta] = useState(5);
    const [isDeletingLoading, setIsDeletingLoading] = useState(false);
    const [isLoading, setIsLoading] = useState(false);
    const [pageIndex, setPageIndex] = useState(1);
    const [selectedTemplate, setSelectedTemplate] = useState();
    const [totalItems, setTotalItems] = useState(0);

    const [modalState, dispatchModal] = useContext(ModalContext);

    const {observer, onOpenChange, open} = useModal();

    const confirmDeleteItemModal = (template) => {
        const deleteTemplate = async () => {
            setIsDeletingLoading(true);

            await deleteFolderTemplateInformation(template.id);

            setIsDeletingLoading(false);

            reload();
        };

        Liferay.Util.openConfirmModal({
            message:
                'Deleting an Template also removes its entries. This action is permanent and cannot be undone.',
            onConfirm: (isConfirmed) => {
                if (isConfirmed) {
                    deleteTemplate();
                }
            },
        });
    };

    const closeNewItemModal = (closeAndReload) => {
        if (closeAndReload) {
            reload();
        }

        modalState.onClose(true);
    };

    const openDesignerModal = (template) => {
        setSelectedTemplate(template);

        onOpenChange(true);
    };

    const openCreateFolderModal = (template) => {
        try {
            dispatchModal({
                payload: {
                    body: <TemplateItemCreateFolder templateId={template.id}/>,
                    center: true,
                    header: 'Create Folder Structure',
                    size: 'lg',
                },
                type: MODAL_OPEN,
            });
        } catch (exp) {
            showError(exp);
        }
    };

    const reload = () => {
        setTotalItems(0);

        if (pageIndex === 1) {
            loadPage();
        } else {
            setPageIndex(1);
        }
    };

    const loadPage = async () => {
        setIsLoading(true);

        const results = await getAvailableTemplatesPage(pageIndex, delta);

        setData(results.items);

        setTotalItems(results.totalCount);

        setIsLoading(false);
    };

    const loadPageCallback = useCallback(async () => {
        const results = await getAvailableTemplatesPage(pageIndex, delta);

        setData(results.items);

        setTotalItems(results.totalCount);
    }, [pageIndex, delta]);

    const openNewItemModal = () => {
        dispatchModal({
            payload: {
                body: <NewTemplateItem onClose={closeNewItemModal}/>,
                center: true,
                header: 'Create Folder Template',
                size: 'lg',
            },
            type: MODAL_OPEN,
        });
    };

    useEffect(() => {
        if (!open) {
            setSelectedTemplate(null);
        }
    }, [open]);

    useEffect(() => {
        const fetchData = async () => {
            await loadPageCallback();
        };
        fetchData();
    }, [loadPageCallback]);

    return (
        <>
            <ClayToolbar style={{marginBottom: '1rem'}}>
                <ClayToolbar.Nav>
                    <ClayToolbar.Item className="text-left" expand>
                        <ClayToolbar.Section>
                            <label className="component-title">
                                Folder Templates
                            </label>
                        </ClayToolbar.Section>
                    </ClayToolbar.Item>
                    <ClayToolbar.Item></ClayToolbar.Item>
                    <ClayToolbar.Item>
                        <ClayToolbar.Section>
                            <ClayButtonGroup spaced={true}>
                                <ClayButtonWithIcon
                                    aria-label="Reload"
                                    className="lfr-portal-tooltip"
                                    disabled={isDeletingLoading || isLoading}
                                    displayType="secondary"
                                    onClick={reload}
                                    symbol="reload"
                                    title="Reload"
                                />
                                <ClayButtonWithIcon
                                    aria-label="Create New"
                                    className="lfr-portal-tooltip"
                                    disabled={isDeletingLoading || isLoading}
                                    displayType="primary"
                                    onClick={openNewItemModal}
                                    symbol="plus"
                                    title="Create New"
                                />
                            </ClayButtonGroup>
                        </ClayToolbar.Section>
                    </ClayToolbar.Item>
                </ClayToolbar.Nav>
            </ClayToolbar>

            {totalItems > 0 && (
                <>
                    <Table>
                        <Head items={HEADERS}>
                            {(column) => (
                                <Cell
                                    expanded={column.expanded}
                                    key={column.key}
                                    wrap={column.wrap}
                                >
                                    {column.label}
                                </Cell>
                            )}
                        </Head>

                        <Body>
                            {data &&
                                data.map((row) => (
                                    <Row key={row['id']}>
                                        <Cell wrap={false}>{row['id']}</Cell>
                                        <Cell expanded={true} wrap={false}>
                                            {row['templateName']}
                                        </Cell>
                                        <Cell wrap={false}>
                                            {moment(row['dateCreated']).format(
                                                'MMMM D, YYYY'
                                            )}
                                        </Cell>
                                        <Cell textAlign="end" wrap={false}>
                                            <ClayButton.Group
                                                spaced={false}
                                                style={{minWidth: '150px'}}
                                            >
                                                <ClayButtonWithIcon
                                                    aria-label="Create Folder Structure"
                                                    className="lfr-portal-tooltip"
                                                    displayType="default"
                                                    onClick={() => {
                                                        openCreateFolderModal(
                                                            row
                                                        );
                                                    }}
                                                    size="sm"
                                                    symbol="folder"
                                                    title="Create Folder Structure"
                                                    translucent
                                                >
                                                    Create Folder
                                                </ClayButtonWithIcon>
                                                <ClayButtonWithIcon
                                                    aria-label="Design Template"
                                                    className="lfr-portal-tooltip"
                                                    displayType="default"
                                                    onClick={() => {
                                                        openDesignerModal(row);
                                                    }}
                                                    size="sm"
                                                    symbol="diagram"
                                                    title="Design Template"
                                                    translucent
                                                >
                                                    Design Template
                                                </ClayButtonWithIcon>
                                                <ClayButtonWithIcon
                                                    aria-label="Delete Template"
                                                    className="lfr-portal-tooltip"
                                                    displayType="default"
                                                    onClick={() => {
                                                        confirmDeleteItemModal(
                                                            row
                                                        );
                                                    }}
                                                    size="sm"
                                                    symbol="trash"
                                                    title="Delete Template"
                                                    translucent
                                                >
                                                    Delete
                                                </ClayButtonWithIcon>
                                            </ClayButton.Group>
                                        </Cell>
                                    </Row>
                                ))}
                        </Body>
                    </Table>

                    <ClayPaginationBarWithBasicItems
                        activeDelta={delta}
                        defaultActive={1}
                        deltas={DELTAS}
                        ellipsisBuffer={3}
                        ellipsisProps={{'aria-label': 'More', 'title': 'More'}}
                        onActiveChange={(page) => {
                            setPageIndex(page);
                        }}
                        onDeltaChange={(delta) => {
                            setDelta(delta);
                        }}
                        totalItems={totalItems}
                    />
                </>
            )}

            {totalItems <= 0 && !isLoading && (
                <div className="c-empty-state c-empty-state-animation">
                    <div className="c-empty-state-image">
                        <div className="c-empty-state-aspect-ratio">
                            <img
                                alt="empty-state-image"
                                className="aspect-ratio-item aspect-ratio-item-fluid"
                                src="/o/admin-theme/images/states/search_state.gif"
                            />
                        </div>
                    </div>
                    <div className="c-empty-state-title">
                        <span className="text-truncate-inline">
                            <span className="text-truncate">No Templates Found</span>
                        </span>
                    </div>
                    <div className="c-empty-state-footer">
                        <ClayButton
                            aria-label="Create New"
                            className="lfr-portal-tooltip"
                            disabled={isDeletingLoading || isLoading}
                            displayType="primary"
                            onClick={openNewItemModal}
                            title="Create New"
                        >
                            <span className="inline-item inline-item-before my-auto">
                                <ClayIcon symbol="plus"/>
                            </span>
                            <span>Create New Template</span>
                        </ClayButton>
                    </div>
                </div>
            )}

            {open && selectedTemplate && (
                <ClayModal observer={observer} size="full-screen">
                    <ClayModal.Header>Design Template</ClayModal.Header>
                    <ClayModal.Body className="p-0">
                        <Diagram templateId={selectedTemplate.id}/>
                    </ClayModal.Body>
                </ClayModal>
            )}
        </>
    );
};

export default TemplateList;
