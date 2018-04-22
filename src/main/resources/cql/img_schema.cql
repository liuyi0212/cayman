create keyspace if not exists img_key_space WITH replication = {'class': 'SimpleStrategy', 'replication_factor':3};

use img_key_space; 

CREATE TABLE if not exists img_data ( 
	uri text,
	domain text,
	memo text,
	data blob,
	created timestamp,
	PRIMARY KEY (uri)
); 
