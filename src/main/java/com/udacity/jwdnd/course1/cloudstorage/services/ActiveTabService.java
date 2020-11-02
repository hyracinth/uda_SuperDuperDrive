package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This service is a singleton to maintain the state of active state
 */
@Service
@Scope("singleton")
public class ActiveTabService {
    private String activeTab;
    private List<String> listTabs;

    public ActiveTabService() {
        this.activeTab = "files";
        listTabs = new ArrayList<String>();
        listTabs.add("files");
        listTabs.add("notes");
        listTabs.add("credentials");
    }

    public String getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(String tabIn) {
        if(listTabs.contains(tabIn)){
            this.activeTab = tabIn;
        }
        else {
            this.activeTab = listTabs.get(0);
        }
    }
}
