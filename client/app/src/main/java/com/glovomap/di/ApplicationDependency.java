package com.glovomap.di;

import com.glovomap.app.GlovoApplication;
import com.glovomap.di.components.ApplicationComponent;
import com.glovomap.di.components.DaggerApplicationComponent;
import com.glovomap.di.modules.ApplicationDIModule;


public class ApplicationDependency {

    private ApplicationComponent applicationComponent;

    public ApplicationComponent getApplicationComponent(GlovoApplication application) {
        return this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationDIModule(new ApplicationDIModule(application))
                .build();
    }

}


