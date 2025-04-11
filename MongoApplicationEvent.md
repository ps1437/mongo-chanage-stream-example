
# 🌱 Spring MongoApplicationEventListener

## What is `AbstractMongoEventListener`?

In Spring Data MongoDB, `AbstractMongoEventListener<T>` is a hook that lets you **listen to MongoDB lifecycle events** for entities — like insert, update, delete.

- Useful for auditing, logging, or modifying data before/after DB operations
- Triggers within the **Spring Data Mapping layer**, **not from the Oplog**
- Events are fired even for standalone MongoDB (unlike Change Streams)

---

## 🔂 Supported Events

| Event Type              | When It Happens                              |
|--------------------------|----------------------------------------------|
| `onBeforeConvert`        | Before entity is converted to DB format      |
| `onBeforeSave`           | Before entity is saved into the collection   |
| `onAfterSave`            | After saving to the collection               |
| `onAfterLoad`            | After document is loaded from DB             |
| `onAfterConvert`         | After DB document is converted to entity     |
---

# 📦 MongoApplicationEvent Support for DB Operations

This document explains how `AbstractMongoEventListener<T>` in Spring Data MongoDB behaves with different database operations like **insert**, **update**, **upsert**, and **delete**.

---

## ✅ Supported Operations Table

| Operation     | Supported | Event Triggered                                         | Notes                                                                 |
|---------------|-----------|---------------------------------------------------------|-----------------------------------------------------------------------|
| **Insert**    | ✅ Yes     | `onBeforeConvert`, `onBeforeSave`, `onAfterSave`       | Full lifecycle supported during inserts                              |
| **Update**    | ✅ Partial | ❌ Not triggered directly                                | Updates using `MongoTemplate.update*()` **do not** trigger listeners |
| **Upsert**    | ⚠️ No      | ❌ Not triggered                                         | Upsert is treated as update → listeners will **not** fire            |
| **Delete**    | ✅ Yes     | `onBeforeDelete`, `onAfterDelete`                      | Only works if using `MongoTemplate.remove()` or `Repository.delete()`|

---

## 🔍 Why Updates and Upserts May Not Trigger Events

- Spring Data MongoDB events are **only triggered when saving or deleting an entire entity**.
- If you're using:
  ```java
  mongoTemplate.updateFirst(...)
  mongoTemplate.updateMulti(...)
  mongoTemplate.upsert(...)
  ```
  These **bypass** the Spring Data **mapping layer**, so **no lifecycle events** are triggered.

---

## ✅ Recommended Practices

To ensure events are fired:

```java
mongoTemplate.save(entity);          // for inserts/updates
mongoTemplate.remove(query, Class);  // for deletions
```

Or use `MongoRepository` methods:

```java
repository.save(entity);
repository.delete(entity);
```

---

## ✅ Summary

| Feature           | Description                                 |
|-------------------|---------------------------------------------|
| **Type**           | Application-level lifecycle events          |
| **Trigger Point**  | During Spring Data mapping                  |
| **Database Need**  | Works on standalone or replica set          |
| **Purpose**        | Hooks for pre/post DB operations            |
| **Integration**    | Extend `AbstractMongoEventListener<T>`      |
