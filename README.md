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
