package com.camilo.bean;

import com.camilo.enums.Duration;

import java.time.LocalDateTime;
import java.util.Objects;

public class BlockedIp {
    private String ip;
    private int requestsMade;
    private Duration duration;
    private LocalDateTime startDate;
    private String blockedReason;

    public BlockedIp(String ip, int requestsMade, Duration duration, LocalDateTime startDate, String blockedReason) {
        this.ip = ip;
        this.requestsMade = requestsMade;
        this.duration = duration;
        this.startDate = startDate;
        this.blockedReason = blockedReason;
    }

    public String getIp() {
        return ip;
    }

    public int getRequestsMade() {
        return requestsMade;
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public String getBlockedReason() {
        return blockedReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BlockedIp blockedIp = (BlockedIp) o;
        return requestsMade == blockedIp.requestsMade &&
                Objects.equals(ip, blockedIp.ip) &&
                duration == blockedIp.duration &&
                Objects.equals(startDate, blockedIp.startDate) &&
                Objects.equals(blockedReason, blockedIp.blockedReason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, requestsMade, duration, startDate, blockedReason);
    }
}