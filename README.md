# 📈 MongoDB Change Stream Listener

This project demonstrates how to use **MongoDB Change Streams** in a Spring Boot application to track real-time changes (insert, update, delete) in a MongoDB collection and route them to appropriate handlers for further processing or auditing.

http://localhost:8080/swagger-ui/index.html

---

## ✅ Use Case

Modern applications often require **real-time awareness** of data changes — for example:

- Auditing any field that was updated in an order
- Triggering external workflows when data is inserted
- Syncing data to another system or cache
- Sending alerts when sensitive data changes

This application helps by **listening to changes** in a MongoDB `orders` collection and handling events based on operation type using a plug-and-play handler pattern.

---

## 🧱 Tech Stack

- Java 17+
- Spring Boot 3.x
- MongoDB Atlas (or local MongoDB)
- Change Streams API
- Spring Data MongoDB

---

## 🧠 How It Works

### 🔁 Change Stream Pipeline

```text
[MongoDB orders collection]
         ↓
[ChangeStreamDocument<Document>]
         ↓
[OperationType enum]
         ↓
[ChangeEventHandler implementation]
         ↓
[AuditService / Logger / Action]
```

---

# 📦 How to Create a Capped Collection in MongoDB

## ✅ Create a Capped Collection

```javascript
db.createCollection("orders_stream", {
  capped: true,
  size: 1048576  // 1MB or whatever you need
});

```

# 🌀 Tailable Cursors vs 🔄 Change Streams in MongoDB

| Feature                        | 🌀 **Tailable Cursors**                         | 🔄 **Change Streams**                          |
|-------------------------------|-----------------------------------------------|------------------------------------------------|
| **MongoDB Version**           | Available in older versions                   | Requires MongoDB **3.6+**                      |
| **Collection Type**           | **Capped collections** only                   | Works on **any collection**                    |
| **Data Tracked**              | **Inserts only**                              | **Inserts, updates, deletes, replacements**    |
| **Update/Delete Support**     | ❌ No                                          | ✅ Yes                                          |
| **Filtering Support**         | ❌ Limited                                     | ✅ Full support via aggregation pipeline        |
| **Resume After Disconnect**   | ❌ Manual requery logic needed                 | ✅ Resume with **resume token**                |
| **Replica Set Required**      | ❌ No                                          | ✅ Yes (even single-node replica set works)     |
| **Cross-Collection Watch**    | ❌ No                                          | ✅ Yes (watch DB or whole cluster)             |
| **Use Case**                  | Log tailing, streaming inserts                | Microservices, triggers, real-time sync        |
| **Performance**               | Lightweight                                   | Slightly heavier due to oplog inspection       |
| **Ease of Use**               | Simple, low-level                             | Higher-level, more powerful                    |
| **Language Support**          | Available in most drivers                     | Fully supported in official drivers            |

