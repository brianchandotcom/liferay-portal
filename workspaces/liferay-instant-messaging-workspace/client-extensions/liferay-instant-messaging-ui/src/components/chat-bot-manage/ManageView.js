/* global Liferay */
import {forwardRef, useCallback, useEffect, useRef, useState} from "react";
import ClayToolbar from "@clayui/toolbar";
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {getEntries,deleteEntry} from "../../services/chatbot/chat-bot-config-services";
import {Body, Cell, Head, Row, Table} from "@clayui/core";
import {ClayPaginationBarWithBasicItems} from "@clayui/pagination-bar";
import ClayEmptyState from "@clayui/empty-state";
import AddObject from "./AddObject";
import ClayModal, {useModal} from '@clayui/modal';
import {showError, showSuccess} from "../../utils/util";


const DELTAS = [{label: 5}, {label: 10}, {label: 20}, {label: 40}];

const HEADERS = [
    {
        expanded: false,
        key: 'id',
        label: 'ID',
        width: "10%",
    },
    {
        expanded: true,
        key: 'name',
        label: 'Name',
        width: "30%",
    },
    {
        expanded: false,
        key: 'contextObjectDefinitionID',
        label: 'Context Object Definition ID',
        width: "20%",
    },
    {
        expanded: false,
        key: 'contextClauseField',
        label: 'Clause Field',
        width: "30%",
    },
    {
        expanded: false,
        key: 'actions',
        label: '',
        width: "10%",
    }
];

const ChatBotManageView = forwardRef((props, ref) => {

    const [data, setData] = useState(null);
    const [delta, setDelta] = useState(5);
    const [isDeleting, setIsDeleting] = useState(false);
    const [isLoading, setIsLoading] = useState(false);
    const [pageIndex, setPageIndex] = useState(1);
    const [totalItems, setTotalItems] = useState(0);

    const addObjectComponentRef = useRef(null);

    const { observer, onOpenChange, open } = useModal();


    const reload = useCallback(() => {

        fetchData(pageIndex,delta);

    },[delta, pageIndex])

    const fetchData = async (pageIndex,delta) => {
        const results = await getEntries(
            pageIndex,
            delta
        );

        setData(results.items);

        console.log(results.items);

        setTotalItems(results.totalCount);
    };

    const handleSave = () =>{
        if (addObjectComponentRef.current) {

            addObjectComponentRef.current.handleSubmit();

        }
    }

    const handleDelete =async (entry) =>{
        await deleteEntry(entry.id).then(()=>{

            showSuccess("Success","ChatBot configuration entry successfully deleted.");

            reload();

        },error=>{
            showError("Error",error.message)
        })
    }

    useEffect(() => {

        fetchData(pageIndex,delta);

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
                            <ClayButton onClick={()=>onOpenChange(true)}>
                                <span className="inline-item inline-item-before">
                                    <ClayIcon symbol="plus"/>
                                </span>
                                Add Object
                            </ClayButton>
                        )}
                    </ClayToolbar.Section>

                </ClayToolbar.Item>

            </ClayToolbar.Nav>

        </ClayToolbar>

        {totalItems > 0 && data &&  (
            <div className="container">
                <Table>
                    <Head>
                        {HEADERS.map(header=>
                            <Cell key={header.key} className="text-center" width={`${header.width}`}>
                                <div className="text-center mb-2">
                                    {header.label}
                                </div>
                        </Cell>
                        )}
                    </Head>
                    <Body>
                        {data.map((item, index) =>
                        <Row key={`row_${index}`}>
                            <Cell>
                                {item.id}
                            </Cell>
                            <Cell>
                                {item.name}
                            </Cell>
                            <Cell>
                                {item.contextObjectDefinitionID}
                            </Cell>
                            <Cell>
                                {item.contextClauseField}
                            </Cell>
                            <Cell>
                                <ClayButtonWithIcon
                                    aria-label="times"
                                    symbol="trash"
                                    displayType={"danger"}
                                    outline={true}
                                    onClick={()=> handleDelete(item)}
                                    size="md"
                                    title="Remove ChatBot Configuration"
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
                    imgProps={{alt: 'No Object Found!', title: 'No Object Found!'}}
                    imgSrc={`${Liferay.ThemeDisplay.getPathThemeImages()}/states/search_state.gif`}
                    imgSrcReducedMotion={`${Liferay.ThemeDisplay.getPathThemeImages()}/states/search_state_reduced_motion.gif`}
                    title="No Object Found"
                >
                    {Liferay.ThemeDisplay.isSignedIn() && (
                        <ClayButton
                            aria-label="Add Object"
                            className="lfr-portal-tooltip"
                            disabled={isDeleting || isLoading}
                            onClick={()=>onOpenChange(true)}
                            displayType="primary"
                            size="sm"
                            title="Add Object"
                        >
							<span className="inline-item inline-item-before my-auto">
								<ClayIcon symbol="plus" />
							</span>

                            <span>Add Object</span>

                        </ClayButton>
                    )}
                </ClayEmptyState>
            </div>
        )}
        <>
            {open && (
                <ClayModal
                    observer={observer}
                    size="lg">
                    <ClayModal.Header>Add Object</ClayModal.Header>
                    <ClayModal.Body>
                        <AddObject handleReload={reload} handleClose={onOpenChange} ref={addObjectComponentRef}></AddObject>
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
        </>
    </>

});

export default ChatBotManageView;
