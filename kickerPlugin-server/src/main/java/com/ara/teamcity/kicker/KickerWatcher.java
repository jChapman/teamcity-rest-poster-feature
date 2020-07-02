package com.ara.teamcity.kicker;

import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.messages.Status;
import jetbrains.buildServer.serverSide.BuildServerAdapter;
import jetbrains.buildServer.serverSide.BuildServerListener;
import jetbrains.buildServer.serverSide.SBuildFeatureDescriptor;
import jetbrains.buildServer.serverSide.SRunningBuild;
import jetbrains.buildServer.util.EventDispatcher;
import org.apache.commons.httpclient.HttpClient;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;

import static com.ara.teamcity.kicker.KickerConsts.SERVER_PAYLOAD_KEY;
import static com.ara.teamcity.kicker.KickerConsts.SERVER_URL_KEY;

public class KickerWatcher extends BuildServerAdapter {
    public KickerWatcher(EventDispatcher<BuildServerListener> dispatcher) {
        dispatcher.addListener(this);
    }

    @Override
    public void buildFinished(@NotNull SRunningBuild build) {
        // Ignore if the build has failed
        if (build.getBuildStatus() != Status.NORMAL)
            return;

        final Collection<SBuildFeatureDescriptor> features = build.getBuildFeaturesOfType(KickerConsts.FEATURE_TYPE);
        for (SBuildFeatureDescriptor kickerFeature : features) {
            Loggers.SERVER.warn("Found a kicker feature on a build!");
            String gotURL = kickerFeature.getParameters().get(SERVER_URL_KEY);
            String payload = kickerFeature.getParameters().get(SERVER_PAYLOAD_KEY);
            if (null == gotURL || null == payload)
                return;
            URL url = null;
            try {
                url = new URL(gotURL);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                connection.setDoOutput(true);
                OutputStream stream = connection.getOutputStream();
                stream.write(payload.getBytes("utf-8"));
                Loggers.SERVER.warn("Posted '" + payload + "' to '" + gotURL);
            } catch (MalformedURLException e) {
                Loggers.SERVER.warn("Rest Poster encountered a malformed URL!");
            } catch (IOException e) {
                Loggers.SERVER.warn("Rest Poster failed to post");
            }
        }
    }
}
