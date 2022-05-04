// based on https://gist.github.com/DevSrSouza/b013d1a8119f50615a493b36cf0b9b56

package com.github.weisj.darkmode.platform.linux.xdg

import com.github.weisj.darkmode.platform.NativePointer
import com.github.weisj.darkmode.platform.ThemeMonitorService
import org.freedesktop.dbus.interfaces.DBusSigHandler

class XdgThemeMonitorService : ThemeMonitorService {
    private val sigHandler = SigHandler()
    override val isDarkThemeEnabled: Boolean = FreedesktopInterface.theme == ThemeMode.DARK
    override val isSupported: Boolean = FreedesktopInterface.theme != ThemeMode.ERROR
    override val isHighContrastEnabled: Boolean = false  // No xdg preference for that available

    override fun createEventHandler(callback: () -> Unit): NativePointer? {
        check(sigHandler.eventHandler == null) { "Event handler already initialized" }

        FreedesktopInterface.addSettingChangedHandler(sigHandler)
        sigHandler.eventHandler = callback
        return NativePointer(0L)
    }

    override fun deleteEventHandler(eventHandle: NativePointer) {
        FreedesktopInterface.removeSettingChangedHandler(sigHandler)
        sigHandler.eventHandler = null
    }

    private class SigHandler : DBusSigHandler<FreedesktopInterface.SettingChanged> {
        var eventHandler: (() -> Unit)? = null
        override fun handle(signal: FreedesktopInterface.SettingChanged) {
            if (signal.colorSchemeChanged) {
                eventHandler?.invoke()
            }
        }
    }
}
