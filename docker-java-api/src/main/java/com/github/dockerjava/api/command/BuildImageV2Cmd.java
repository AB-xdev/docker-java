package com.github.dockerjava.api.command;

import com.github.dockerjava.api.model.AuthConfigurations;
import com.github.dockerjava.api.model.BuildResponseV2Item;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;
import java.util.Set;

/**
 * Build an image from Dockerfile.
 * <p>
 * TODO: http://docs.docker.com/reference/builder/#dockerignore
 *
 * @see <a
 * href="https://docs.docker.com/reference/api/docker_remote_api_v1.20/#build-image-from-a-dockerfile">build-image-from-a-dockerfile</a>
 */
public interface BuildImageV2Cmd extends AsyncDockerCmd<BuildImageV2Cmd, BuildResponseV2Item> {

    // lib specific

    @CheckForNull
    InputStream getTarInputStream();

    @CheckForNull
    AuthConfigurations getBuildAuthConfigs();

    // getters

    /**
     * Multple "t" tags.
     *
     * @since {@link RemoteApiVersion#VERSION_1_21}
     */
    @CheckForNull
    Set<String> getTags();

    /**
     * "Cache-from" in API
     */
    @CheckForNull
    Set<String> getCacheFrom();

    /**
     * "remote" in API
     */
    @CheckForNull
    URI getRemote();

    /**
     * "nocache" in API
     */
    @CheckForNull
    Boolean hasNoCacheEnabled();

    /**
     * "rm" in API
     */
    @CheckForNull
    Boolean hasRemoveEnabled();

    /**
     * "forcerm" in API
     */
    @CheckForNull
    Boolean isForcerm();

    /**
     * "q" in API
     */
    @CheckForNull
    Boolean isQuiet();

    /**
     * "pull" in API
     */
    @CheckForNull
    Boolean hasPullEnabled();

    @CheckForNull
    String getPathToDockerfile();

    @CheckForNull
    Long getMemory();

    @CheckForNull
    Long getMemswap();

    @CheckForNull
    String getCpushares();

    @CheckForNull
    String getCpusetcpus();

    /**
     * @since {@link RemoteApiVersion#VERSION_1_21}
     */
    @CheckForNull
    Map<String, String> getBuildArgs();

    /**
     * @since {@link RemoteApiVersion#VERSION_1_22}
     */
    @CheckForNull
    Long getShmsize();

    /**
     * @since {@link RemoteApiVersion#VERSION_1_23}
     */
    @CheckForNull
    Map<String, String> getLabels();

    /**
     * @since {@link RemoteApiVersion#VERSION_1_25}
     */
    @CheckForNull
    String getNetworkMode();

    /**
     * "platform" in API
     *
     * @since {@link RemoteApiVersion#VERSION_1_32}
     */
    @CheckForNull
    String getPlatform();

    /**
     * @since {@link RemoteApiVersion#VERSION_1_38}
     */
    @CheckForNull
    String getTarget();

    /**
     * @since {@link RemoteApiVersion#VERSION_1_28}
     */
    @CheckForNull
    Set<String> getExtraHosts();

    @CheckForNull
    String getOutputs();

    @CheckForNull
    String getVersion();

    // setters

    BuildImageV2Cmd withTags(Set<String> tags);

    /*
     * @since {@link RemoteApiVersion#VERSION_1_25}
     */
    BuildImageV2Cmd withCacheFrom(Set<String> cacheFrom);

    BuildImageV2Cmd withRemote(URI remote);

    BuildImageV2Cmd withBaseDirectory(File baseDirectory);

    BuildImageV2Cmd withDockerfile(File dockerfile);

    BuildImageV2Cmd withDockerfilePath(String dockerfilePath);

    BuildImageV2Cmd withNoCache(Boolean noCache);

    BuildImageV2Cmd withRemove(Boolean rm);

    BuildImageV2Cmd withForcerm(Boolean forcerm);

    BuildImageV2Cmd withQuiet(Boolean quiet);

    BuildImageV2Cmd withPull(Boolean pull);

    BuildImageV2Cmd withMemory(Long memory);

    BuildImageV2Cmd withMemswap(Long memswap);

    BuildImageV2Cmd withCpushares(String cpushares);

    BuildImageV2Cmd withCpusetcpus(String cpusetcpus);

    /**
     * @since {@link RemoteApiVersion#VERSION_1_21}
     */
    BuildImageV2Cmd withBuildArg(String key, String value);

    // setters lib specific

    BuildImageV2Cmd withBuildAuthConfigs(AuthConfigurations authConfig);

    BuildImageV2Cmd withTarInputStream(@Nonnull InputStream tarInputStream);

    /**
     * @since {@link RemoteApiVersion#VERSION_1_22}
     */
    BuildImageV2Cmd withShmsize(Long shmsize);

    /**
     * @since {@link RemoteApiVersion#VERSION_1_23}
     */
    BuildImageV2Cmd withLabels(Map<String, String> labels);

    /**
     * @since {@link RemoteApiVersion#VERSION_1_25}
     */
    BuildImageV2Cmd withNetworkMode(String networkMode);

    /**
     *@since {@link RemoteApiVersion#VERSION_1_32}
     */
    BuildImageV2Cmd withPlatform(String platform);

    /**
     * @since {@link RemoteApiVersion#VERSION_1_38}
     */
    BuildImageV2Cmd withTarget(String target);

    /**
     * @since {@link RemoteApiVersion#VERSION_1_28}
     */
    BuildImageV2Cmd withExtraHosts(Set<String> extraHosts);

    BuildImageV2Cmd withOutputs(String outputs);

    BuildImageV2Cmd withVersion(String version);

    @Override
    default BuildImageV2ResultCallback start() {
        return exec(new BuildImageV2ResultCallback());
    }

    interface Exec extends DockerCmdAsyncExec<BuildImageV2Cmd, BuildResponseV2Item> {
    }

}
