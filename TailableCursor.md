#  Tailable Cursor in MongoDB

## What is a Tailable Cursor?

A **Tailable Cursor** is a special cursor that behaves like the Unix `tail -f` command — it waits for more data to appear in a **capped collection** and continues reading.

- Only works on **capped collections**
- Used for **real-time streaming** of data
- Commonly used to **tail the Oplog** (`local.oplog.rs`)
- Supports continuous reads without reopening the cursor

---

## Internal Working – Step-by-Step

1. A tailable cursor is created on a **capped collection**.
2. MongoDB returns existing documents like a normal cursor.
3. Instead of closing, the cursor **waits for new inserts**.
4. New documents are **automatically pushed** to the client when inserted.

---

##  Example with Linux

```bash
# Tailing the oplog with mongosh
mongosh --quiet --eval '
  db.getSiblingDB("local").oplog.rs.find(
    {},
    { tailable: true, awaitData: true, noCursorTimeout: true }
  ).forEach(doc => printjson(doc))
'
```
---
## Tailable Cursor Summary

| Feature           | Description                          |
|-------------------|--------------------------------------|
| **Type**           | Special cursor                      |
| **Requirement**    | Only on capped collections          |
| **Used For**       | Oplog, real-time log streaming      |
| **Behavior**       | Waits for new data                  |
| **MongoDB Feature**| Replication, Change Stream, Log watchers |
