import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileVersionLocalServiceUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.auth.GuestOrUserUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.File;
import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

final long groupId = 20121;
final String pdfFilePath = "/opt/dev/projects/github/liferay-portal/portal-web/test/functional/com/liferay/portalweb/dependencies/Document_1.pdf";
final int numPDFDocumentsToAdd = 1000;

Map<String, DDMFormValues> ddmFormValuesMap = new HashMap<>(0);
Map<String, Serializable> workflowContextMap = new HashMap<>(0);
ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
long userId = GuestOrUserUtil.getUserId();

for (int i = 1; i < numPDFDocumentsToAdd + 1; i++) {
	String title = "Document_" + i;

	File file = new File(pdfFilePath);

	DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
		null, userId, groupId, groupId,
		DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, title + ".pdf",
		"application/pdf", title, StringPool.BLANK, null, StringPool.BLANK,
		DLFileEntryTypeConstants.COMPANY_ID_BASIC_DOCUMENT, ddmFormValuesMap,
		file, null, file.length(), null, null, serviceContext);

	DLFileVersion dlFileVersion =
		DLFileVersionLocalServiceUtil.fetchLatestFileVersion(
			dlFileEntry.getFileEntryId(), false);

	DLFileEntryLocalServiceUtil.updateStatus(
		userId, dlFileEntry, dlFileVersion, WorkflowConstants.STATUS_APPROVED,
		serviceContext, workflowContextMap);
}