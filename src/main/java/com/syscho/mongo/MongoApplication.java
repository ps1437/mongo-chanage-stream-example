package com.syscho.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoApplication.class, args);
	}
}

///Change streams work only on replica sets or sharded clusters.
//MongoDB’s oplog (operation log) is a special capped collection (local.oplog.rs) that logs all changes on the primary node.
//Change Streams read from the oplog and filter changes for the target collection, database, or cluster.

//}
//{
//		"id": "67f762d545e6416d9ca3836a",
//		"customerName": "Rajkumar",
//		"amount": 399.99
//		}