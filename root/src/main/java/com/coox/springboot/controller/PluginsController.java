package com.coox.springboot.controller;

import com.coox.plugins.core.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ServiceLoader;

@RestController
@RequestMapping("/plugins")
public class PluginsController {

    @GetMapping("job")
    public void doJob() {
        Path pluginsDir = Paths.get("/Volumes/SSDCoox/Coding/TestProjects/springboot/plugins-out");
        ModuleFinder pluginsFinder = ModuleFinder.of(pluginsDir);
        List<String> plugins = pluginsFinder
                .findAll()
                .stream()
                .map(ModuleReference::descriptor)
                .map(ModuleDescriptor::name)
                .toList();

        Configuration pluginsConfiguration = ModuleLayer
                .boot()
                .configuration()
                .resolve(pluginsFinder, ModuleFinder.of(), plugins);

        ModuleLayer layer = ModuleLayer
                .boot()
                .defineModulesWithOneLoader(pluginsConfiguration, ClassLoader.getSystemClassLoader());

        List<Service> services = ServiceLoader
                .load(layer, Service.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .toList();

        for (Service service : services) {
            service.doJob();
        }
    }

    @GetMapping("replace")
    public void replace() {

    }

}
