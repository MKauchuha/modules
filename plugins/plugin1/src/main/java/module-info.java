import com.coox.plugins.core.Service;
import com.coox.plugins.plugin1.PluginOne;

module pluginOne {
    requires core;

    provides Service with PluginOne;
}