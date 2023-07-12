import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;


def createNewMessage(object)
{
    ServiceContext serviceContext  = new ServiceContext();
    def values = object.getValues().clone();
    clientId = GetterUtil.getLong(values["toClientId"])
    values["updateOwner"] = false;
    serviceContext.setUserId(clientId);
    def newObj = com.liferay.object.service.ObjectEntryLocalServiceUtil.addObjectEntry(clientId, object.getGroupId() ,  object.getObjectDefinitionId(), values, serviceContext)
    return newObj.getObjectEntryId();
}
//clientMessageID

final long objectEntryId = GetterUtil.getLong(id);


ServiceContext serviceContext  = new ServiceContext();

serviceContext.setUserId(GetterUtil.getLong(creator));

def obj = com.liferay.object.service.ObjectEntryLocalServiceUtil.getObjectEntry(objectEntryId)


def clientMessageId =  createNewMessage(obj);


def values = obj.getValues();
values["clientMessageID"] = clientMessageId;
obj.setValues(values);
com.liferay.object.service.ObjectEntryLocalServiceUtil.updateObjectEntry(GetterUtil.getLong(creator),id,values,serviceContext);


