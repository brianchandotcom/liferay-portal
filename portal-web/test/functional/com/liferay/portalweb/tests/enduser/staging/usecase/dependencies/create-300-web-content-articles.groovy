import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.service.*
import com.liferay.portal.kernel.util.*
import com.liferay.journal.service.JournalArticleLocalServiceUtil;

import java.io.File;
import java.util.*

int startCount = 1;

int numCount = 301;

companyId = com.liferay.portal.kernel.util.PortalUtil.getCompanyId(actionRequest)
userId = com.liferay.portal.kernel.util.PortalUtil.getUserId(actionRequest)
group = com.liferay.portal.kernel.service.GroupLocalServiceUtil.getGroup(companyId, "Site Name");
groupId = group.getGroupId();

String rawContent = "hello hello hello";

long classNameId = 0;
long classPK = 0;
String articleId = "";
boolean autoArticleId = true;
double version = 1.0;

Locale locale = new Locale.Builder().setLanguage("en").setRegion("US").build();

Map<Locale, String> descriptionMap = new HashMap<Locale, String>();
descriptionMap.put(locale, "");

String content = "<?xml version='1.0' encoding='UTF-8'?><root available-locales=\"en_US\" default-locale=\"en_US\"><static-content language-id=\"en_US\"><![CDATA[" + rawContent + "]]></static-content></root>";
String type = "general";
String structureId = "";
String templateId = "";
String layoutUuid = null;
int displayDateMonth = 4;
int displayDateDay = 17;
int displayDateYear = 2016;
int displayDateHour = 10;
int displayDateMinute = 0;
int expirationDateMonth = 0;
int expirationDateDay = 0;
int expirationDateYear = 0;
int expirationDateHour = 0;
int expirationDateMinute = 0;
boolean neverExpire = true;
int reviewDateMonth = 0;
int reviewDateDay = 0;
int reviewDateYear = 0;
int reviewDateHour = 0;
int reviewDateMinute = 0;
boolean neverReview = true;
boolean indexable = true;
boolean smallImage = false;
String smallImageURL = "";
File smallImageFile = null;
long folderId=0;
Map<String, byte[]> images = new HashMap<String, byte[]>();
ServiceContext serviceContext = new ServiceContext();

serviceContext.setScopeGroupId(groupId);

for (int i = startCount; i < numCount; i++) {

	Map<Locale, String> titleMap = new HashMap<Locale, String>();
	titleMap.put(locale, "Article_" + i);

try {
JournalArticleLocalServiceUtil.addArticle(userId, groupId, folderId,
				titleMap, descriptionMap,
				content, "BASIC-WEB-CONTENT", "BASIC-WEB-CONTENT",
				serviceContext)
} catch (PortalException e) {
		e.printStackTrace();
	} catch (SystemException e) {
		e.printStackTrace();
	}
System.out.println(i + " article(s) created");
}