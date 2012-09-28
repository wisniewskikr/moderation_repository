DROP TABLE IF EXISTS text_moderation;

GO

CREATE TABLE text_moderation (
  id bigint(20) NOT NULL auto_increment,
  company_id bigint(20) default NULL,
  moderation_description varchar(255) default NULL,
  moderation_status varchar(255) default NULL,
  result_description varchar(255) default NULL,
  result_status varchar(255) default NULL,
  text_content varchar(255) default NULL,
  text_id bigint(20) default NULL,
  PRIMARY KEY  (id)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;

GO