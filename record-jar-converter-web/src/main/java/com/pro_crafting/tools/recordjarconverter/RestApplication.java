package com.pro_crafting.tools.recordjarconverter;

import io.swagger.annotations.Api;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@Api
@ApplicationPath("v1/")
public class RestApplication extends Application {
    public static String VERSION_PATH = "v1/";
}
