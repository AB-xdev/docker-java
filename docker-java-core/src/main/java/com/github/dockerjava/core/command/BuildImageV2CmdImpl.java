package com.github.dockerjava.core.command;

import com.github.dockerjava.api.command.BuildImageV2Cmd;
import com.github.dockerjava.api.model.AuthConfigurations;
import com.github.dockerjava.api.model.BuildResponseV2Item;
import com.github.dockerjava.core.dockerfile.Dockerfile;
import com.github.dockerjava.core.util.FilePathUtil;

import javax.annotation.CheckForNull;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Build an image from Dockerfile.
 */
public class BuildImageV2CmdImpl extends AbstrAsyncDockerCmd<BuildImageV2Cmd, BuildResponseV2Item> implements BuildImageV2Cmd {

    private InputStream tarInputStream;

    private Set<String> tags;

    private Set<String> cacheFrom;

    private Boolean noCache;

    private Boolean remove = true;

    private Boolean quiet;

    private Boolean pull;

    private AuthConfigurations buildAuthConfigs;

    private File dockerFile;

    private String dockerFilePath;

    private File baseDirectory;

    private String cpusetcpus;

    private Long memory;

    private String cpushares;

    private Boolean forcerm;

    private Long memswap;

    private Long shmsize;

    private URI remote;

    private Map<String, String> buildArgs;

    private Map<String, String> labels;

    private String networkMode;

    private String platform;

    private String target;

    private Set<String> extraHosts;

    private String outputs;

    private String version;

    public BuildImageV2CmdImpl(Exec exec) {
        super(exec);
    }

    public BuildImageV2CmdImpl(Exec exec, File dockerFileOrFolder) {
        super(exec);
        Objects.requireNonNull(dockerFileOrFolder, "dockerFolder is null");

        if (dockerFileOrFolder.isDirectory()) {
            withBaseDirectory(dockerFileOrFolder);
            withDockerfile(new File(dockerFileOrFolder, "Dockerfile"));
        } else {
            withDockerfile(dockerFileOrFolder);
        }
    }

    public BuildImageV2CmdImpl(Exec exec, InputStream tarInputStream) {
        super(exec);
        Objects.requireNonNull(tarInputStream, "tarInputStream is null");
        withTarInputStream(tarInputStream);
    }

    // getters API

    @CheckForNull
    public Set<String> getTags() {
        return tags;
    }

    @CheckForNull
    public Set<String> getCacheFrom() {
       return cacheFrom;
    }

    @Override
    public URI getRemote() {
        return remote;
    }

    @Override
    public Boolean hasNoCacheEnabled() {
        return noCache;
    }

    @Override
    public Boolean hasRemoveEnabled() {
        return remove;
    }

    @Override
    public Boolean isForcerm() {
        return forcerm;
    }

    @Override
    public Boolean isQuiet() {
        return quiet;
    }

    @Override
    public Boolean hasPullEnabled() {
        return pull;
    }

    @Override
    public String getPathToDockerfile() {
        if (dockerFilePath != null) {
            return dockerFilePath;
        } else if (baseDirectory != null && dockerFile != null) {
            return FilePathUtil.relativize(baseDirectory, dockerFile);
        } else {
            return null;
        }
    }

    @Override
    public Long getMemory() {
        return memory;
    }

    @Override
    public Long getMemswap() {
        return memswap;
    }

    @Override
    public String getCpushares() {
        return cpushares;
    }

    @Override
    public String getCpusetcpus() {
        return cpusetcpus;
    }

    @Override
    public Map<String, String> getBuildArgs() {
        return buildArgs;
    }

    @Override
    public Map<String, String> getLabels() {
        return labels;
    }

    @Override
    public String getNetworkMode() {
        return networkMode;
    }

    @Override
    public String getPlatform() {
        return platform;
    }

    @Override
    public String getTarget() {
        return target;
    }

    // getter lib specific

    @Override
    public AuthConfigurations getBuildAuthConfigs() {
        return buildAuthConfigs;
    }

    @Override
    public InputStream getTarInputStream() {
        return tarInputStream;
    }

    /**
     * @see #shmsize
     */
    @Override
    public Long getShmsize() {
        return shmsize;
    }

    @Override
    public Set<String> getExtraHosts() {
        return extraHosts;
    }

    @CheckForNull
    @Override
    public String getVersion() {
        return version;
    }

    @CheckForNull
    @Override
    public String getOutputs() {
        return outputs;
    }

    // setters

    @Override
    public BuildImageV2Cmd withTags(Set<String> tags) {
        this.tags = tags;
        return this;
    }

    @Override
    public BuildImageV2Cmd withCacheFrom(Set<String> cacheFrom) {
        this.cacheFrom = cacheFrom;
        return this;
    }

    @Override
    public BuildImageV2Cmd withRemote(URI remote) {
        this.remote = remote;
        return this;
    }

    @Override
    public BuildImageV2CmdImpl withNoCache(Boolean noCache) {
        this.noCache = noCache;
        return this;
    }

    @Override
    public BuildImageV2CmdImpl withRemove(Boolean rm) {
        this.remove = rm;
        return this;
    }

    @Override
    public BuildImageV2Cmd withForcerm(Boolean forcerm) {
        this.forcerm = forcerm;
        return this;
    }

    @Override
    public BuildImageV2CmdImpl withQuiet(Boolean quiet) {
        this.quiet = quiet;
        return this;
    }

    @Override
    public BuildImageV2CmdImpl withPull(Boolean pull) {
        this.pull = pull;
        return this;
    }

    @Override
    public BuildImageV2Cmd withMemory(Long memory) {
        this.memory = memory;
        return this;
    }

    @Override
    public BuildImageV2Cmd withMemswap(Long memswap) {
        this.memswap = memswap;
        return this;
    }

    @Override
    public BuildImageV2Cmd withCpushares(String cpushares) {
        this.cpushares = cpushares;
        return this;
    }

    @Override
    public BuildImageV2Cmd withCpusetcpus(String cpusetcpus) {
        this.cpusetcpus = cpusetcpus;
        return this;
    }

    @Override
    public BuildImageV2Cmd withBuildArg(String key, String value) {
        if (this.buildArgs == null) {
            this.buildArgs = new HashMap<>();
        }
        this.buildArgs.put(key, value);
        return this;
    }

    // lib specific

    @Override
    public BuildImageV2Cmd withBaseDirectory(File baseDirectory) {
        this.baseDirectory = baseDirectory;
        return this;
    }

    @Override
    public BuildImageV2CmdImpl withDockerfile(File dockerfile) {
        Objects.requireNonNull(dockerfile);
        if (!dockerfile.exists()) {
            throw new IllegalArgumentException("Dockerfile does not exist");
        }
        if (!dockerfile.isFile()) {
            throw new IllegalArgumentException("Dockerfile is not a file");
        }

        if (baseDirectory == null) {
            withBaseDirectory(dockerfile.getParentFile());
        }

        this.dockerFile = dockerfile;

        try {
            withTarInputStream(new Dockerfile(dockerfile, baseDirectory).parse().buildDockerFolderTar());
        } catch (IOException e) {
            // we just created the file this should never happen.
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public BuildImageV2Cmd withDockerfilePath(String dockerfilePath) {
        this.dockerFilePath = Objects.requireNonNull(dockerfilePath, "dockerfilePath is null");
        return this;
    }

    @Override
    public BuildImageV2CmdImpl withTarInputStream(InputStream tarInputStream) {
        this.tarInputStream = Objects.requireNonNull(tarInputStream, "tarInputStream is null");
        return this;
    }

    @Override
    public BuildImageV2Cmd withBuildAuthConfigs(AuthConfigurations authConfigs) {
        this.buildAuthConfigs = Objects.requireNonNull(authConfigs, "authConfig is null");
        return this;
    }

    /**
     * @see #shmsize
     */
    @Override
    public BuildImageV2Cmd withShmsize(Long shmsize) {
        this.shmsize = shmsize;
        return this;
    }

    /**
     * @see #labels
     */
    @Override
    public BuildImageV2Cmd withLabels(Map<String, String> labels) {
        this.labels = labels;
        return this;
    }

    @Override
    public BuildImageV2Cmd withNetworkMode(String networkMode) {
        this.networkMode = networkMode;
        return this;
    }

    @Override
    public BuildImageV2Cmd withPlatform(String platform) {
        this.platform = platform;
        return this;
    }

    @Override
    public BuildImageV2Cmd withTarget(String target) {
        this.target = target;
        return this;
    }

    @Override
    public BuildImageV2Cmd withExtraHosts(Set<String> extraHosts) {
        this.extraHosts = extraHosts;
        return this;
    }

    @Override
    public BuildImageV2Cmd withOutputs(String outputs) {
        this.outputs = outputs;
        return this;
    }

    @Override
    public BuildImageV2Cmd withVersion(String version) {
        this.version = version;
        return this;
    }

    @Override
    public void close() {
        super.close();

        try {
            tarInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
