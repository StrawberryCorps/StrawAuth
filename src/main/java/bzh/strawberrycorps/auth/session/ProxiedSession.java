package bzh.strawberrycorps.auth.session;

import bzh.strawberry.api.StrawAPIBungee;
import bzh.strawberrycorps.auth.StrawBungee;
import bzh.strawberrycorps.auth.util.Encrypt;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.UUID;

/*
 * This file ProxiedSession is part of a project StrawAuth.
 * It was created on 05/04/2021 19:40 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawAuth author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class ProxiedSession {

    private int straw_id;
    private UUID uuid;
    private String username;
    private String password;
    private String lastIP;
    private boolean premium;
    private boolean logged;
    private Timestamp firstLogin;
    private Timestamp start;
    private int version;
    private boolean inDb;


    public ProxiedSession(UUID uuid, String name, String lastIP) {
        this.uuid = uuid;
        this.username = name;
        this.lastIP = lastIP;
    }

    public ProxiedSession(UUID uuid) {
        try {
            Connection connection = StrawAPIBungee.getAPI().getDataFactory().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM players WHERE uuid = ?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                straw_id = resultSet.getInt("straw_id");
                this.uuid = UUID.fromString(resultSet.getString("uuid"));
                this.password = resultSet.getString("password");
                this.premium = resultSet.getBoolean("premium");
                this.inDb = true;
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getSessionTime() {
        return (new Timestamp(System.currentTimeMillis()).getTime() - this.start.getTime());
    }

    public int getStraw_id() {
        return straw_id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getPassword() {
        return password;
    }

    public String getLastIP() {
        return lastIP;
    }

    public boolean isPremium() {
        return premium;
    }

    public Timestamp getFirstLogin() {
        return firstLogin;
    }

    public Timestamp getStart() {
        return start;
    }

    public int getVersion() {
        return version;
    }

    public void setPassword(String password) {
        this.password = Encrypt.getSHA512(password);
    }

    public void setLastIP(String lastIP) {
        this.lastIP = lastIP;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public void setFirstLogin(Timestamp firstLogin) {
        this.firstLogin = firstLogin;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public boolean isInDb() {
        return inDb;
    }

    public void setInDb(boolean inDb) {
        this.inDb = inDb;
    }

    public void save() {
        // @TODO On save a la deconnection
        ProxyServer.getInstance().getScheduler().runAsync(StrawBungee.STRAW, () -> {
            try {
                Connection connection = StrawAPIBungee.getAPI().getDataFactory().getDataSource().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO sessions () VALUES ()");

                preparedStatement.executeUpdate();
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void insert() {
        try {
            Connection connection = StrawAPIBungee.getAPI().getDataFactory().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO players (uuid, pseudo, ip, password, premium) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, this.uuid.toString());
            preparedStatement.setString(2, this.username);
            preparedStatement.setString(3, this.lastIP);
            preparedStatement.setString(4, this.password);
            preparedStatement.setBoolean(5, this.isPremium());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                straw_id = resultSet.getInt(1);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        try {
            Connection connection = StrawAPIBungee.getAPI().getDataFactory().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players SET pseudo = ?, ip = ?, password = ? WHERE uuid = ? OR pseudo = ?");
            preparedStatement.setString(1, this.username);
            preparedStatement.setString(2, this.lastIP);
            preparedStatement.setString(3, this.password);
            preparedStatement.setString(4, this.uuid.toString());
            preparedStatement.setString(5, this.username);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean exist() {
        boolean exist = false;
        try {
            Connection connection = StrawAPIBungee.getAPI().getDataFactory().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("");
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            exist = resultSet.next();
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }
}