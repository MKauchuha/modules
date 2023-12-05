package com.coox.plugins.plugin1;

import com.coox.plugins.core.Service;

public class PluginOne implements Service {

    @Override
    public void doJob() {
        System.out.println("Plugin 1 initial state");
    }
}
