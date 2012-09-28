DROP TABLE IF EXISTS db_version;

GO

CREATE TABLE db_version (
 id bigint(20) NOT NULL auto_increment,
 version int(10) NOT NULL DEFAULT 1,
 PRIMARY KEY  (id)
);

GO

INSERT INTO db_version (version) VALUES (1);

GO

