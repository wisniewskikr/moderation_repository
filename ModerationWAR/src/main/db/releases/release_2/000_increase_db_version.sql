DROP PROCEDURE IF EXISTS increaseDBVersion;

GO

CREATE PROCEDURE increaseDBVersion()
BEGIN
	
	set @previousDbVersion = -1;
	SELECT MAX(version) into @previousDbVersion FROM db_version;
	set @currentDbVersion = @previousDbVersion + 1;
	INSERT INTO db_version (version) VALUES (@currentDbVersion);

END;

GO

call increaseDBVersion();

GO

DROP PROCEDURE IF EXISTS increaseDBVersion;

GO