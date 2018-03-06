CREATE TABLE web_server_access (
  access_timestamp TIMESTAMP,
  ip               VARCHAR(15),
  request          TEXT
);

CREATE TABLE blocked_ips (
  ip             VARCHAR(15),
  requests_made  INT,
  duration       VARCHAR(10),
  start_date     TIMESTAMP,
  blocked_reason TEXT
);