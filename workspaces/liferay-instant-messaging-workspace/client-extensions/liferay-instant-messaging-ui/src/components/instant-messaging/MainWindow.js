import './MainWindow.css';
import {forwardRef, useCallback, useEffect, useRef, useState} from "react";
import {getContacts} from "../../services/instant-messaging/contacts";
import ContactCard from "./ContactCard";
import ClayPanel from '@clayui/panel';
import ClayList from "@clayui/list";
import ClayTabs from '@clayui/tabs';
import ClayForm, {ClayInput} from '@clayui/form';
import {ClayButtonWithIcon} from '@clayui/button';
import ClayToolbar from "@clayui/toolbar";
import ClayIcon from "@clayui/icon";
import ChatWindow from "./ChatWindow";
import {liferayInstantMessagingConnect} from "../../services/instant-messaging/connection";
import {MessageType} from "../../utils/constants";
import ClayAlert from "@clayui/alert";
import ClayBadge from "@clayui/badge";

const MainWindow = forwardRef((props, ref) => {

    const [active, setActive] = useState(0);
    const [contacts, setContacts] = useState([]);
    const [contactsQuery, setContactsQuery] = useState('');
    const [expanded, setExpanded] = useState(false);
    const [filteredContacts, setFilteredContacts] = useState([]);
    const [isChatOpen, setIsChatOpen] = useState(false);
    const [notifications,setNotifications] = useState(null);
    const [onlineIds, setOnlineIds] = useState([]);
    const [selectedContact, setSelectedContact] = useState(null);
    const [selfContact, setSelfContact] = useState(null);
    const [socket, setSocket] = useState(null);
    const [totalNotifications, setTotalNotifications] = useState(0);

    const chatWindowRef = useRef(null);

    const loadContacts = async () => {

        setContacts(await getContacts());
        setFilteredContacts(await getContacts());

    }

    const handleCloseChat = () => {

        setIsChatOpen(false);
        setSelectedContact(null);
        setActive(0);

    }

    const handleContactClick = (contact) => {

        setIsChatOpen(true);
        setSelectedContact(contact);
        setActive(1);

    }

    const handleIMSocketMessage = useCallback((event) => {

        let message = JSON.parse(event.data);

        switch (message.type){

            case MessageType.IMCONTACTS:{

                setOnlineIds(message.data)
                break;

            }

            case MessageType.MASSAGE:{

                if (chatWindowRef.current){

                    chatWindowRef.current.handleSeen();

                    chatWindowRef.current.handleIncomingMessage(message);
                }

                break;

            }

            case MessageType.MESSAGE_NOTIFICATION:{

                setNotifications(message.data);
                break;

            }

            case MessageType.BOTSTARTTHINKING:{

                if (chatWindowRef.current){
                    chatWindowRef.current.handleBotStatus(message);
                }
                break;
            }

            case MessageType.BOTENDTHINKING:{

                if (chatWindowRef.current){
                    chatWindowRef.current.handleBotStatus(message);
                }
                break;
            }


        }

    },[selectedContact])

    const handleIsOnline = (userId) => {

        if (onlineIds) {
            return onlineIds.find(id => id === userId)?true:false
        }else{
            return false;
        }

    }

    useEffect(() => {


    }, [selectedContact])

    useEffect(() => {

        if (contactsQuery && contactsQuery.length > 0) {
            let filteredContacts = contacts.filter(contact => contact.name
                .toLowerCase().startsWith(contactsQuery.toLowerCase()));

            setFilteredContacts(filteredContacts);
        } else {

            setFilteredContacts(contacts);

        }

    }, [contactsQuery])

    useEffect(()=>{

        if (socket){
            socket.onmessage = handleIMSocketMessage;
        }

    },[socket])

    useEffect(()=>{

        if (contacts && contacts.length > 0) {

            let _self = contacts.find(contact => contact.self);

            setSelfContact(_self);

        }

    },[contacts])

    useEffect(() => {

        let total = 0;
        if (notifications){

            Object.keys(notifications).map(key => {
                total+=notifications[key];
            })

        }
        setTotalNotifications(total);

    }, [notifications]);

    useEffect(() => {

        if (!socket){
            liferayInstantMessagingConnect().then((_socket)=>{

                if (_socket){
                    setSocket(_socket);
                    loadContacts();
                }

            });
        }

    }, []);

    return (
        <div className={"bg-white"}>
            <ClayPanel
                collapsable
                displayTitle={<>
                    <ClayBadge className={"total-notification-badge"} displayType="info" label={totalNotifications} />
                    <label>Liferay Instant Messaging</label>
                </>}
                displayType="secondary"
                showCollapseIcon={true}
                expanded={expanded}
                onExpandedChange={setExpanded}>
                <ClayPanel.Body className="instant-messaging-messaging">
                    {selfContact && (
                        <>
                            <ClayTabs.Content className={"mx-auto h-100 w-100"} activeIndex={active} fade>
                                <ClayTabs.TabPane aria-labelledby="tab-1">
                                    <ClayList>
                                        <ClayForm.Group>
                                            <ClayInput.Group>
                                                <ClayInput.GroupItem>
                                                    <ClayInput placeholder="Search for contact..." onChange={(event) => {
                                                        setContactsQuery(event.target.value)
                                                    }} type="text"/>
                                                </ClayInput.GroupItem>
                                            </ClayInput.Group>
                                        </ClayForm.Group>
                                        <div className="contacts-list">

                                            {filteredContacts && filteredContacts.length > 0 && filteredContacts.filter(contact => !contact.self).map((contact, index) => (

                                                <ContactCard online={handleIsOnline(contact.userId)} notifications={notifications && notifications[contact.userId]?notifications[contact.userId]:0} detail={contact} handleClick={() => {
                                                    handleContactClick(contact)
                                                }} key={`contact_${index}`}></ContactCard>

                                            ))}

                                        </div>
                                    </ClayList>
                                </ClayTabs.TabPane>
                                <ClayTabs.TabPane aria-labelledby="tab-2">
                                    {selectedContact && (
                                        <>
                                            <ClayToolbar className="mb-3">
                                                <ClayToolbar.Nav>
                                                    <ClayToolbar.Item className="text-left" expand>
                                                        <ClayToolbar.Section>
                                                            <ClayIcon symbol="user"/>
                                                            <label
                                                                className={"mx-1 text-capitalize"}>{selectedContact.name}</label>
                                                        </ClayToolbar.Section>
                                                    </ClayToolbar.Item>

                                                    <ClayToolbar.Item>
                                                        <ClayButtonWithIcon
                                                            aria-label="Close"
                                                            symbol="times"
                                                            displayType={'unstyled'}
                                                            size={'xs'}
                                                            onClick={handleCloseChat}
                                                            title="Close"
                                                        />
                                                    </ClayToolbar.Item>
                                                </ClayToolbar.Nav>
                                            </ClayToolbar>

                                            <ChatWindow ref={chatWindowRef} self={selfContact} connection={socket} target={selectedContact}></ChatWindow>

                                        </>
                                    )}
                                </ClayTabs.TabPane>

                            </ClayTabs.Content>
                        </>
                    )}

                    {!selfContact && (
                        <div>
                            <ClayAlert displayType="warning" title="Unavailable">
                                Liferay Instant Messaging is unavailable for users who are not logged in.
                            </ClayAlert>
                        </div>

                    )}
                </ClayPanel.Body>
            </ClayPanel>
        </div>
    );

});

export default MainWindow;
