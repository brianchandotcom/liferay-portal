/* global Liferay */
import {forwardRef, useCallback, useEffect, useRef, useState} from "react";
import ClayToolbar from "@clayui/toolbar";
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {Body, Cell, Head, Row, Table} from "@clayui/core";
import ClayLoadingIndicator from "@clayui/loading-indicator";
import {ClayPaginationBarWithBasicItems} from "@clayui/pagination-bar";
import ClayEmptyState from "@clayui/empty-state";
import ClayModal, {useModal} from '@clayui/modal';
import {showError, showSuccess} from "../../utils/util";
import {
    deleteStreamConfigurationEntry,
    getStreamConfigurationEntries, standaloneStreamConfigurationEntry
} from "../../services/object/streamConfiguration";
import AddConfigurationEntry from "./AddConfigurationEntry";
import CodeWindow from "./CodeWindow";
import EditConfigurationEntry from "./EditConfigurationEntry";


const DELTAS = [{label: 5}, {label: 10}, {label: 20}, {label: 40}];

const HEADERS = [
    {
        expanded: false,
        key: 'objectDefinitionId',
        label: 'Definition Id',
        width: "20%",
    },
    {
        expanded: true,
        key: 'name',
        label: 'Name',
        width: "20%",
    },
    {
        expanded: false,
        key: 'objectActions',
        label: 'Stream Events',
        width: "40%",
    }, {
        expanded: false,
        key: 'actions',
        label: 'Actions',
        width: "20%",
    }
];

const ModelType = {
    Edit_Entry: "Edit_Entry",
    New_Entry: "New_Entry",
    Show_Code: "Show_Code",
}

const ManageView = forwardRef((props, ref) => {

    const [data, setData] = useState(null);
    const [delta, setDelta] = useState(5);
    const [isDeleting, setIsDeleting] = useState(false);
    const [isLoading, setIsLoading] = useState(false);
    const [modelType, setModelType] = useState(null);
    const [pageIndex, setPageIndex] = useState(1);
    const [selectedEntry, setSelectedEntry] = useState(null);
    const [totalItems, setTotalItems] = useState(0);

    const addEditObjectComponentRef = useRef(null);

    const {observer, onOpenChange, open} = useModal();

    const reload = useCallback(() => {

        fetchData(pageIndex, delta);

    }, [delta, pageIndex])

    const fetchData = async (pageIndex, delta) => {

        handleShowLoadingScreen();

        const results = await getStreamConfigurationEntries(
            pageIndex,
            delta
        );

        handleHideLoadingScreen();

        setData(results.items.map(item => {
            let configuration = JSON.parse(item.configuration);

            return {
                ...item,
                configuration: configuration,
                onAfterUpdate: configuration.actionsList.find(action => action.type === "onAfterUpdate"),
                onAfterAdd: configuration.actionsList.find(action => action.type === "onAfterAdd"),
                onAfterDelete: configuration.actionsList.find(action => action.type === "onAfterDelete"),
                standalone: configuration.actionsList.find(action => action.type === "standalone"),

            }
        }));

        setTotalItems(results.totalCount);
    };

    const handleSave = () => {
        if (addEditObjectComponentRef.current) {

            handleHideLoadingScreen();

            addEditObjectComponentRef.current.handleSubmit();
        }
    }

    const handleShowLoadingScreen = () => {

        setIsLoading(true);

    }

    const handleHideLoadingScreen = () => {

        setIsLoading(false);

    }

    const handleSuccessCallback = (data) => {

        onOpenChange(false);
        reload();

    }

    const handleOpenNewEntryModel = () => {

        setModelType(ModelType.New_Entry);

        onOpenChange(true);

    }

    const handleShowCodeModel = (entry) => {

        setModelType(ModelType.Show_Code);

        setSelectedEntry(entry);

        onOpenChange(true);

    }

    const handleShowEditModel = (entry) => {

        setModelType(ModelType.Edit_Entry);

        setSelectedEntry(entry);

        onOpenChange(true);

    }

    const handleDelete = async (entry) => {

        try {
            handleShowLoadingScreen();

            await deleteStreamConfigurationEntry(entry.id);

            handleHideLoadingScreen();

            showSuccess("Stream deleted", "Stream was deleted successfully.");

            reload();

        } catch (error) {

            handleHideLoadingScreen();

            showError("Error Deleting Stream", error.message);

        }

    }

    const handleForceConfiguration = async (entry) => {

        try {
            handleShowLoadingScreen();

            await standaloneStreamConfigurationEntry(entry.id);

            handleHideLoadingScreen();

            showSuccess("Configuration Applied Successfully", "The stream configuration has been successfully applied and is now in effect.");


        } catch (error) {

            handleHideLoadingScreen();

            showError("Configuration Application Failed", "An error occurred while applying stream configuration. Please try again or check your settings.");
        }


    }

    useEffect(() => {

        fetchData(pageIndex, delta);

    }, [delta, pageIndex]);


    return <>

        <ClayToolbar className="mb-3 bg-white">

            <ClayToolbar.Nav className="container">

                <ClayToolbar.Item className="text-left" expand>

                </ClayToolbar.Item>

                <ClayToolbar.Item></ClayToolbar.Item>

                <ClayToolbar.Item>

                    <ClayToolbar.Section>
                        {Liferay.ThemeDisplay.isSignedIn() && (
                            <ClayButton onClick={handleOpenNewEntryModel}>
                                <span className="inline-item inline-item-before">
                                    <ClayIcon symbol="plus"/>
                                </span>
                                Add
                            </ClayButton>
                        )}
                    </ClayToolbar.Section>

                </ClayToolbar.Item>

            </ClayToolbar.Nav>

        </ClayToolbar>

        {totalItems > 0 && data && (
            <div className="container">
                <Table>
                    <Head>
                        {HEADERS.map(header =>
                            <Cell key={header.key} className="text-center" width={`${header.width}`}>
                                <div className="text-center mb-2">
                                    {header.label}
                                </div>
                                {header.key === "objectActions" && (
                                    <div className="row">
                                        <div className="col-3">
                                            On Create
                                        </div>
                                        <div className="col-3">
                                            On Update
                                        </div>
                                        <div className="col-3">
                                            On Delete
                                        </div>
                                        <div className="col-3">
                                            Standalone
                                        </div>
                                    </div>
                                )}
                            </Cell>
                        )}
                    </Head>
                    <Body>
                        {data.map((item, index) =>
                            <Row key={`row_${index}`}>
                                <Cell>
                                    {item.objectDefinitionId}
                                </Cell>
                                <Cell>
                                    {item.name}
                                </Cell>
                                <Cell className="text-center">

                                    <div className="row">
                                        <div className="col-3">
                                            {item.onAfterAdd ? "Yes" : "No"}
                                        </div>
                                        <div className="col-3">
                                            {item.onAfterUpdate ? "Yes" : "No"}
                                        </div>
                                        <div className="col-3">
                                            {item.onAfterDelete ? "Yes" : "No"}
                                        </div>
                                        <div className="col-3">
                                            {item.standalone ? "Yes" : "No"}
                                        </div>
                                    </div>
                                </Cell>
                                <Cell>

                                    <ClayButtonWithIcon
                                        className={"mx-1"}
                                        aria-label="info"
                                        symbol="pencil"
                                        displayType={"info"}
                                        outline={true}
                                        onClick={() => handleShowEditModel(item)}
                                        size="sm"
                                        title="Edit Configuration"
                                    />

                                    <ClayButtonWithIcon
                                        className={"mx-1"}
                                        aria-label="info"
                                        symbol="code"
                                        displayType={"info"}
                                        outline={true}
                                        onClick={() => handleShowCodeModel(item)}
                                        size="sm"
                                        title="Show code"
                                    />

                                    <ClayButtonWithIcon
                                        className={"mx-1"}
                                        aria-label="info"
                                        symbol="cog"
                                        displayType={"info"}
                                        outline={true}
                                        onClick={() => handleForceConfiguration(item)}
                                        size="sm"
                                        title="Force Configuration"
                                    />

                                    <ClayButtonWithIcon
                                        className={"mx-1"}
                                        aria-label="times"
                                        symbol="trash"
                                        displayType={"danger"}
                                        outline={true}
                                        onClick={() => handleDelete(item)}
                                        size="sm"
                                        title="Remove Configuration"
                                    />
                                </Cell>
                            </Row>
                        )}
                    </Body>
                </Table>

                <div className="container">
                    <ClayPaginationBarWithBasicItems
                        activeDelta={delta}
                        defaultActive={1}
                        deltas={DELTAS}
                        ellipsisBuffer={3}
                        onActiveChange={(page) => {
                            setPageIndex(page);
                        }}
                        onDeltaChange={(delta) => {
                            setDelta(delta);
                        }}
                        totalItems={totalItems}
                    />
                </div>
            </div>

        )}

        {totalItems <= 0 && !isLoading && (
            <div className="container">
                <ClayEmptyState
                    description={null}
                    imgProps={{
                        alt: 'No Stream Configuration Found!',
                        title: 'No Stream Configuration Found!'
                    }}
                    imgSrc={`${Liferay.ThemeDisplay.getPathThemeImages()}/states/search_state.gif`}
                    imgSrcReducedMotion={`${Liferay.ThemeDisplay.getPathThemeImages()}/states/search_state_reduced_motion.gif`}
                    title="No Configuration Found"
                >
                    {Liferay.ThemeDisplay.isSignedIn() && (
                        <ClayButton
                            aria-label="Add"
                            className="lfr-portal-tooltip"
                            disabled={isDeleting || isLoading}
                            onClick={handleOpenNewEntryModel}
                            displayType="primary"
                            size="sm"
                            title="Add"
                        >
							<span className="inline-item inline-item-before my-auto">
								<ClayIcon symbol="plus"/>
							</span>

                            <span>Add</span>

                        </ClayButton>
                    )}
                </ClayEmptyState>
            </div>
        )}
        <>
            {open && modelType === ModelType.New_Entry && (
                <ClayModal
                    observer={observer}
                    size="full-screen">
                    <ClayModal.Header>Add Events Stream</ClayModal.Header>
                    <ClayModal.Body>
                        <AddConfigurationEntry stopLoading={handleHideLoadingScreen}
                                               SuccessCallback={handleSuccessCallback}
                                               ref={addEditObjectComponentRef}></AddConfigurationEntry>
                    </ClayModal.Body>
                    <ClayModal.Footer
                        last={
                            <ClayButton.Group spaced>
                                <ClayButton
                                    displayType="secondary"
                                    onClick={() => onOpenChange(false)}
                                >
                                    Cancel
                                </ClayButton>
                                <ClayButton onClick={handleSave}>
                                    Save
                                </ClayButton>
                            </ClayButton.Group>
                        }
                    />
                </ClayModal>
            )}

            {open && modelType === ModelType.Edit_Entry && (
                <ClayModal
                    observer={observer}
                    size="full-screen">
                    <ClayModal.Header>Edit Events Stream</ClayModal.Header>
                    <ClayModal.Body>
                        <EditConfigurationEntry stopLoading={handleHideLoadingScreen} entry={selectedEntry}
                                                SuccessCallback={handleSuccessCallback}
                                                ref={addEditObjectComponentRef}></EditConfigurationEntry>
                    </ClayModal.Body>
                    <ClayModal.Footer
                        last={
                            <ClayButton.Group spaced>
                                <ClayButton
                                    displayType="secondary"
                                    onClick={() => onOpenChange(false)}
                                >
                                    Cancel
                                </ClayButton>
                                <ClayButton onClick={handleSave}>
                                    Save
                                </ClayButton>
                            </ClayButton.Group>
                        }
                    />
                </ClayModal>
            )}

            {open && modelType === ModelType.Show_Code && (
                <ClayModal
                    observer={observer}
                    size="lg">
                    <ClayModal.Header>How to use</ClayModal.Header>
                    <ClayModal.Body>
                        <CodeWindow entry={selectedEntry}></CodeWindow>
                    </ClayModal.Body>
                    <ClayModal.Footer
                        last={
                            <ClayButton.Group spaced>
                                <ClayButton
                                    displayType="secondary"
                                    onClick={() => onOpenChange(false)}
                                >
                                    Close
                                </ClayButton>
                            </ClayButton.Group>
                        }
                    />
                </ClayModal>
            )}

            {isLoading && (
                <div className={"loading-overlay"}>
                    <ClayLoadingIndicator displayType="primary" shape="squares" size="md"/>
                </div>
            )}

        </>

    </>

});

export default ManageView;
