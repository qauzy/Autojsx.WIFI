package github.zimo.autojsx.action.run.doc

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.vfs.VirtualFile
import github.zimo.autojsx.server.VertxServer
import github.zimo.autojsx.util.logE
import github.zimo.autojsx.util.runServer

/**
 * 运行当前脚本
 */
class DocRunButton :
    AnAction(github.zimo.autojsx.icons.ICONS.START_16) {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project
        runServer(project)
        if (project != null) {
            val fileEditorManager = FileEditorManager.getInstance(project)
            //保存正在修改的文件
            fileEditorManager.selectedFiles.apply {
                val documentManager = FileDocumentManager.getInstance()
                for (file in this) {
                    val document: Document? = documentManager.getDocument(file!!)
                    if (document != null) {
                        documentManager.saveDocument(document)
                    }
                }
            }
            //获取正在编辑的文件
            val selectedEditor = fileEditorManager.selectedEditor

            if (selectedEditor != null) {
                val selectedFile: VirtualFile = fileEditorManager.selectedFiles[0]
                runCatching {
                    VertxServer.Command.rerunJS(selectedFile.path)
                }.onFailure {
                    logE("js脚本网络引擎执行失败${selectedFile.path} ", it)
                }
            }
        }
    }
}