import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil
import com.liferay.portal.kernel.service.ServiceContext
import com.liferay.portal.kernel.util.PortalUtil;

try {
	Company company = PortalUtil.getCompany(actionRequest)

	User user = UserLocalServiceUtil.getUserByEmailAddress(
		company.getCompanyId(), "test@liferay.com");

	UserLocalServiceUtil.updateUser(
		user.getUserId(), "", "",
		"", false, user.getReminderQueryQuestion(),
		user.getReminderQueryAnswer(), user.getScreenName(), "userea@liferay.com",
		user.getFacebookId(), user.getOpenId(), true, null,
		user.getLanguageId(), user.getTimeZoneId(), user.getGreeting(),
		user.getComments(), "", user.getMiddleName(), "",
		0L, 0L,user.getMale(),
		10, 10, 2019, "",
		"", "",
		"", "",
		"", null, null, null, null, null,
		new ServiceContext()
	)

}
catch (Exception e) {
	println(e)
}