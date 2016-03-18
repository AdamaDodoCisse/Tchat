package entity;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 */
public class Computer implements Serializable {

    private String username;

    private String country;

    private String ip;

    private String name;

    private String version;

    public Computer() throws UnknownHostException {
        this(
                System.getProperty("user.name"),
                System.getProperty("os.name"),
                System.getProperty("os.version"),
                "",
                InetAddress.getLocalHost().getHostAddress()
        );
    }

    public Computer(String username, String name, String version, String country, String ip) {
        this.username = username;
        this.name = name;
        this.version = version;
        this.country = country;
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "username='" + username + '\'' +
                ", country='" + country + '\'' +
                ", ip='" + ip + '\'' +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void update(Computer c) {
        this.setCountry(c.getCountry());
        this.setIp(c.getIp());
        this.setName(c.getName());
        this.setUsername(c.getUsername());
        this.setVersion(c.getVersion());
    }
}
