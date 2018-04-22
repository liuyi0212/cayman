create keyspace if not exists stream_key_space WITH replication = {'class': 'SimpleStrategy', 'replication_factor':3};

use stream_key_space; 

CREATE TABLE if not exists stream_data ( 
	uri text,
	domain text,
	memo text,
	data blob,
	created timestamp,
	PRIMARY KEY (uri)
); 
