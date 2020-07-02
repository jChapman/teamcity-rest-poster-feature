package com.ara.teamcity.kicker;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.*;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ara.teamcity.kicker.KickerConsts.FEATURE_TYPE;
import static com.ara.teamcity.kicker.KickerConsts.SERVER_URL_KEY;

public class KickerFeature extends BuildFeature {
    private final String myEditURL;

    public KickerFeature(@NotNull final PluginDescriptor descriptor) {
        myEditURL = descriptor.getPluginResourcesPath("kickerSettings.jsp");
    }

    @NotNull
    @Override
    public String getType() {
        return FEATURE_TYPE;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "REST Poster";
    }

    @Nullable
    @Override
    public String getEditParametersUrl() {
        return myEditURL;
    }

    @NotNull
    @Override
    public String describeParameters(@NotNull Map<String, String> params) {
        String gotURL = params.get(SERVER_URL_KEY);
        if (null == gotURL) {
            return "";
        }
        return "Server: " + gotURL;
    }

    @Nullable
    @Override
    public PropertiesProcessor getParametersProcessor() {
        return incomingProps -> {
            List<InvalidProperty> badProps = new ArrayList<>();
            String gotURL = incomingProps.get(SERVER_URL_KEY);
            if (null == gotURL) {
                badProps.add(new InvalidProperty(SERVER_URL_KEY, "Server URL must be filled out"));
            }
            return badProps;
        };
    }

    @Override
    public boolean isMultipleFeaturesPerBuildTypeAllowed() {
        return true;
    }
}
