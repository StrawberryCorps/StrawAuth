package bzh.strawberry.auth.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/*
 * This file Mojang is part of a project StrawAuth.
 * It was created on 05/04/2021 21:09 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawAuth author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 * En provenance de GitHub source inconnue
 */
public class Mojang {

    private final Logger logger;
    private final Gson gson = new GsonBuilder().create();
    private long lastMojangError;

    public Mojang(Logger paramLogger) {
        this.logger = paramLogger;
    }

    public bzh.strawberry.auth.util.MojangProfile getPremiumProfile(String paramString) {
        bzh.strawberry.auth.util.MojangProfile localMojangValidateProfile = new bzh.strawberry.auth.util.MojangProfile(paramString);
        ValidateProfile localValidateProfile = System.currentTimeMillis() - this.lastMojangError > TimeUnit.MINUTES.toMillis(10L) ? getValidateProfile(paramString, Api.MOJANG) : getValidateProfile(paramString, Api.CRAFTAPI);
        if ((localValidateProfile == null) || (localValidateProfile.getName() == null) || (localValidateProfile.getId() == null)) {
            return localMojangValidateProfile;
        }
        localMojangValidateProfile.setUser(localValidateProfile.getName());
        localMojangValidateProfile.setUuid(localValidateProfile.getUniqueId());
        localMojangValidateProfile.setOnlineMode(true);
        return localMojangValidateProfile;
    }

    private ValidateProfile getValidateProfile(String paramString, Api paramApi) {
        switch (paramApi) {
            case MOJANG:
                return getMojang(paramString);
            case MCAPI:
                return getMcApi(paramString);
            case CRAFTAPI:
                return getCraftApi(paramString);
        }
        return null;
    }

    private ValidateProfile getCraftApi(String paramString) {
        try {
            HttpURLConnection localHttpURLConnection = (HttpURLConnection) new URL("http://craftapi.com/api/user/uuid/" + paramString.toLowerCase()).openConnection();
            if (localHttpURLConnection.getResponseCode() != 200) {
                return null;
            }
            InputStream localInputStream = localHttpURLConnection.getInputStream(); Object localObject1 = null;

            BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localInputStream));
            StringBuilder localStringBuilder = new StringBuilder();
            String str1;
            while ((str1 = localBufferedReader.readLine()) != null) {
                localStringBuilder.append(str1);
            }
            String str2 = localStringBuilder.toString();
            ValidateProfile localValidateProfile;
            if (str2.isEmpty()) {
                return null;
            }
            localInputStream.close();
            return readProfile(CraftApiProfile.class, str2);
        } catch (Throwable localThrowable1) {
            localThrowable1.printStackTrace();
        }
        return null;
    }

    private ValidateProfile getMcApi(String paramString) {
        try {
        HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL("http://us.mc-api.net/v3/uuid/" + paramString.toLowerCase()).openConnection();
        if (localHttpURLConnection.getResponseCode() == 404) {
            return null;
        }
        InputStream localInputStream = localHttpURLConnection.getInputStream(); Object localObject1 = null;

        BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localInputStream));
        StringBuilder localStringBuilder = new StringBuilder();
        String str1;
        while ((str1 = localBufferedReader.readLine()) != null) {
            localStringBuilder.append(str1);
        }
        String str2 = localStringBuilder.toString();
        ValidateProfile localValidateProfile;
        if (str2.isEmpty()) {
            return null;
        }
        localInputStream.close();
        return readProfile(McApiProfile.class, str2);
    } catch (Throwable localThrowable1) {
        localThrowable1.printStackTrace();
    }
        return null;
    }

    private ValidateProfile getMojang(String paramString) {
        try {
            HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL("https://api.mojang.com/users/profiles/minecraft/" + paramString.toLowerCase()).openConnection();
            InputStream localInputStream = localHttpURLConnection.getInputStream(); Object localObject1 = null;

            BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localInputStream));
            StringBuilder localStringBuilder = new StringBuilder();
            String str1;
            while ((str1 = localBufferedReader.readLine()) != null) {
                localStringBuilder.append(str1);
            }
            String str2 = localStringBuilder.toString();
            ValidateProfile localValidateProfile;
            if (str2.isEmpty()) {
                return null;
            }
            localInputStream.close();
            return readProfile(MojangProfile.class, str2);
        } catch (Throwable localThrowable1) {
            localThrowable1.printStackTrace();
        }
        finally { }
        return null;
    }

    private <T extends ValidateProfile> T readProfile(Class<T> paramClass, String paramString) {
        if (paramString == null) {
            return null;
        }
        return this.gson.fromJson(paramString, paramClass);
    }

    private class CraftApiProfile implements Mojang.ValidateProfile {
        private String uuid;
        private String username;
        private Mojang.Api api = Mojang.Api.CRAFTAPI;

        private CraftApiProfile() { }
        public String getId() {
            return this.uuid;
        }

        public UUID getUniqueId() {
            return UUID.fromString(this.uuid.substring(0, 8) + "-" + this.uuid.substring(8, 12) + "-" + this.uuid.substring(12, 16) + "-" + this.uuid.substring(16, 20) + "-" + this.uuid.substring(20, 32));
        }

        public String getName() {
            return this.username;
        }

        public Mojang.Api getApi() {
            return this.api;
        }

        public void setApi(Mojang.Api paramApi) {
            this.api = paramApi;
        }

        public String toString()
        {
            StringBuilder localStringBuilder = new StringBuilder("CraftApiProfile{");
            localStringBuilder.append("uuid='").append(this.uuid).append('\'');
            localStringBuilder.append(", username='").append(this.username).append('\'');
            localStringBuilder.append(", api=").append(this.api);
            localStringBuilder.append('}');
            return localStringBuilder.toString();
        }
    }

    private class McApiProfile implements Mojang.ValidateProfile {
        private String uuid;
        private String full_uuid;
        private String name;
        private Mojang.Api api = Mojang.Api.MCAPI;

        private McApiProfile() { }
        public String getId() {
            return this.uuid;
        }

        public UUID getUniqueId() {
            return UUID.fromString(this.full_uuid);
        }

        public String getName() {
            return this.name;
        }

        public Mojang.Api getApi() {
            return this.api;
        }

        public void setApi(Mojang.Api paramApi) {
            this.api = paramApi;
        }

        public String toString()
        {
            StringBuilder localStringBuilder = new StringBuilder("McApiProfile{");
            localStringBuilder.append("uuid='").append(this.uuid).append('\'');
            localStringBuilder.append(", full_uuid='").append(this.full_uuid).append('\'');
            localStringBuilder.append(", name='").append(this.name).append('\'');
            localStringBuilder.append(", api=").append(this.api);
            localStringBuilder.append('}');
            return localStringBuilder.toString();
        }
    }

    private class MojangProfile implements Mojang.ValidateProfile {
        private String id;
        private String name;
        private Mojang.Api api = Mojang.Api.MOJANG;

        private MojangProfile() { }
        public String getId() {
            return this.id;
        }

        public UUID getUniqueId()
        {
            String str = this.id;
            return UUID.fromString(str.substring(0, 8) + "-" + str.substring(8, 12) + "-" + str.substring(12, 16) + "-" + str.substring(16, 20) + "-" + str.substring(20, 32));
        }

        public String getName() {
            return this.name;
        }

        public Mojang.Api getApi() {
            return this.api;
        }

        public void setApi(Mojang.Api paramApi) {
            this.api = paramApi;
        }

        public String toString()
        {
            StringBuilder localStringBuilder = new StringBuilder("MojangProfile{");
            localStringBuilder.append("id='").append(this.id).append('\'');
            localStringBuilder.append(", name='").append(this.name).append('\'');
            localStringBuilder.append(", api=").append(this.api);
            localStringBuilder.append('}');
            return localStringBuilder.toString();
        }
    }

    public interface ValidateProfile {
        public abstract String getId();
        public abstract UUID getUniqueId();
        public abstract String getName();
        public abstract Mojang.Api getApi();
    }

    public enum Api {
        MOJANG,
        MCAPI,
        CRAFTAPI;
    }
}