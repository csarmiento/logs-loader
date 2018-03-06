package com.camilo.db;

import com.camilo.bean.BlockedIp;
import com.google.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class BlockedIpsDao {
    private ConnectionFactory connectionFactory;

    private static final String INSERT_INTO_BLOCKED_IPS = "INSERT INTO loader.blocked_ips VALUES (?,?,?,?,?)";

    @Inject
    public BlockedIpsDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void insert(List<BlockedIp> blockedIps) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_INTO_BLOCKED_IPS)) {
            for (BlockedIp blockedIp : blockedIps) {
                ps.setString(1, blockedIp.getIp());
                ps.setInt(2, blockedIp.getRequestsMade());
                ps.setString(3, blockedIp.getDuration().toString());
                ps.setTimestamp(4, Timestamp.valueOf(blockedIp.getStartDate()));
                ps.setString(5, blockedIp.getBlockedReason());

                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}