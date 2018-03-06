package com.camilo.bean;

import java.sql.Timestamp;
import java.util.Objects;

public class IpAccess {
    private Timestamp accessTimestamp;
    private String ip;
    private String request;

    public IpAccess(Timestamp accessTimestamp, String ip, String request) {
        this.accessTimestamp = accessTimestamp;
        this.ip = ip;
        this.request = request;
    }

    public Timestamp getAccessTimestamp() {
        return accessTimestamp;
    }

    public String getIp() {
        return ip;
    }

    public String getRequest() {
        return request;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IpAccess access = (IpAccess) o;
        return Objects.equals(accessTimestamp, access.accessTimestamp) &&
                Objects.equals(ip, access.ip) &&
                Objects.equals(request, access.request);
    }

    @Override
    public int hashCode() {

        return Objects.hash(accessTimestamp, ip, request);
    }
}