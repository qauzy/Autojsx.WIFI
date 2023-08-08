package github.zimo.autojsx.server

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

object AutojsNotifier {
    fun info(
        project: Project?,
        content: String?,
    ) {
        content?.let {
            NotificationGroupManager.getInstance() // plugin.xml里配置的id
                .getNotificationGroup("AutojsNotifier").createNotification(it, NotificationType.INFORMATION)
                .notify(project)
        }
    }

    fun error(project: Project?, content: String?,) {
        content?.let {
            NotificationGroupManager.getInstance() // plugin.xml里配置的id
                .getNotificationGroup("AutojsNotifier").createNotification(it, NotificationType.ERROR)
                .notify(project)
        }
    }
}
