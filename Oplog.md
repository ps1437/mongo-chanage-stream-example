#  MongoDB Oplog Explained with Diagram

##  What is the Oplog?

The **Oplog (Operations Log)** is a special capped collection in MongoDB used primarily for **replication**.

- Stores **all write operations** (insert, update, delete) done on the **Primary** node.
- Enables **replication** and **change stream** features.
- Found at: `local.oplog.rs`
- Exists **only in Replica Sets**

---

## Internal Working – Step-by-Step

1. A client sends a write operation (e.g., `insert`) to the **Primary**.
2. MongoDB performs the operation and creates a **log entry** in the `oplog`.
3. The entry is written into the **`local.oplog.rs`** capped collection.
4. **Secondary nodes** use a **tailable cursor** to **read and replay** the operations from the Oplog.
5. **Change Stream consumers** also read from the Oplog to detect real-time data changes.

---

##  Sample Oplog Entry

```json
{
  "ts": Timestamp(1640995200, 1),
  "op": "i",
  "ns": "mydb.users",
  "o": {
    "_id": 1,
    "name": "Alice"
  }
}
```
Change stream event transform
```
{
  "_id": { "ts": { "$timestamp": { "t": 1640995200, "i": 1 } } },
  "operationType": "insert",
  "fullDocument": { "_id": 1, "name": "Alice" },
  "ns": {
    "db": "mydb",
    "coll": "users"
  },
  "documentKey": { "_id": 1 }
}

```
```aiignore
Not available in standalone MongoDB deployments

Oplog size is fixed (old entries are auto-removed)

Can be viewed using:

```

## Oplog → Event Mapping Table

| Oplog Field        | Change Stream Field     | Description                    |
|--------------------|--------------------------|--------------------------------|
| `ts`               | `_id.ts`                 | Timestamp of the operation     |
| `op: i / u / d`    | `operationType`          | Type of DB operation           |
| `ns: mydb.users`   | `ns.db`, `ns.coll`       | Database and collection name   |
| `o`                | `fullDocument`           | The actual document content    |
| `_id` (in `o`)     | `documentKey._id`        | Unique document identifier     |

---
## ✅ Summary

| Feature           | Description                            |
|-------------------|----------------------------------------|
| **Location**       | `local.oplog.rs`                      |
| **Type**           | Capped Collection                     |
| **Required For**   | Replication, Change Streams, Backups  |
| **Availability**   | Only in Replica Sets                  |
| **Read Access**    | Via Tailable Cursor                   |
| **Related Feature**| Change Streams                        |
