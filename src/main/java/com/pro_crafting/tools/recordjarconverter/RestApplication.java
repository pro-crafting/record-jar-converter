package com.pro_crafting.tools.recordjarconverter;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath(RestApplication.VERSION_PATH)
public class RestApplication extends Application {
    public static final String VERSION_PATH = "/v1/";
}
