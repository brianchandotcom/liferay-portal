import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactoryUtil;

Trigger trigger = TriggerFactoryUtil.createTrigger(
	"test.job.name.2.persistent", "test.job.group.2.persistent", 10,
	TimeUnit.SECOND);

String script = "System.out.println(\"Persistent job 2 is triggered at \" + System.currentTimeMillis());";

try {
	SchedulerEngineHelperUtil.addScriptingJob(
		trigger, StorageType.PERSISTED, "persistent job", "groovy", script,
		0);

	out.println("Persistent job 2 is added");
}
catch (SchedulerException ex) {
	out.println(ex);
}