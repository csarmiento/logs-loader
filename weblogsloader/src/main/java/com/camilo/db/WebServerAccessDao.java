package com.camilo.db;

import com.camilo.bean.BlockedIp;
import com.camilo.enums.Duration;
import com.google.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class WebServerAccessDao {
    private ConnectionFactory connectionFactory;

    @Inject
    public WebServerAccessDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public List<BlockedIp> getBlockedIps(LocalDateTime startDate, Duration duration, int threshold) throws SQLException {
        String sql = "SELECT\n" +
                "  count(1),\n" +
                "  ip\n" +
                "FROM loader.web_server_access\n" +
                "WHERE access_timestamp >= ? AND access_timestamp <= ?\n" +
                "GROUP BY ip\n" +
                "HAVING count(1) > ?;";

        LocalDateTime endDate = null;
        String timeSpan = null;
        switch (duration) {
            case DAILY:
                endDate = startDate.plusHours(24);
                timeSpan = "(24 hours)";
                break;
            case HOURLY:
                endDate = startDate.plusHours(1);
                timeSpan = "(1 hour)";
                break;
        }

        List<BlockedIp> blockedIps = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            int paramIndex = 0;

            ps.setTimestamp(++paramIndex, Timestamp.valueOf(startDate));
            ps.setTimestamp(++paramIndex, Timestamp.valueOf(endDate));
            ps.setInt(++paramIndex, threshold);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int requestsMade = resultSet.getInt(1);
                String ip = resultSet.getString(2);
                String blockedReason = format("IP made more than %d requests starting from %s to %s %s", threshold, startDate, endDate, timeSpan);

                blockedIps.add(new BlockedIp(ip, requestsMade, duration, startDate, blockedReason));
            }
        }
        return blockedIps;
    }

}