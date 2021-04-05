package bzh.strawberrycorps.auth.util;

import java.util.UUID;

/*
 * This file MojangProfile is part of a project StrawAuth.
 * It was created on 05/04/2021 21:11 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawAuth author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class MojangProfile {
    private String user;
    private UUID uuid;
    private boolean onlineMode;
    private boolean error;

    public MojangProfile(String paramString) {
        this.user = paramString;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String paramString) {
        this.user = paramString;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID paramUUID) {
        this.uuid = paramUUID;
    }

    public boolean isOnlineMode() {
        return this.onlineMode;
    }

    public void setOnlineMode(boolean paramBoolean) {
        this.onlineMode = paramBoolean;
    }

    public boolean isError() {
        return this.error;
    }

    public void setError(boolean paramBoolean) {
        this.error = paramBoolean;
    }

    public String toString() {
        StringBuilder localStringBuilder = new StringBuilder("MojangProfile{");
        localStringBuilder.append("user='").append(this.user).append('\'');
        localStringBuilder.append(", uuid=").append(this.uuid);
        localStringBuilder.append(", onlineMode=").append(this.onlineMode);
        localStringBuilder.append(", error=").append(this.error);
        localStringBuilder.append('}');
        return localStringBuilder.toString();
    }
}