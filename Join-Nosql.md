# 🔗 Joins in MongoDB (NoSQL)

MongoDB is a NoSQL database, and while it doesn’t support traditional relational joins like SQL databases, it **does support join-like operations** using the **aggregation framework**, specifically the `$lookup` stage.

---

## ✅ Can You Perform Joins in MongoDB?

Yes! The `$lookup` stage in the aggregation pipeline acts like a **LEFT OUTER JOIN** in SQL.

---

## 🛠 Basic `$lookup` Example

### `orders` Collection:
```json
{
  "_id": 1,
  "productId": "p1",
  "quantity": 2
}
```

### `products` Collection:
```json
{
  "_id": "p1",
  "name": "Laptop",
  "price": 999
}
```

### Aggregation with `$lookup`:
```js
db.orders.aggregate([
  {
    $lookup: {
      from: "products",         // collection to join with
      localField: "productId",  // field in 'orders'
      foreignField: "_id",      // field in 'products'
      as: "productDetails"      // name of the output array field
    }
  }
])
```

### Result:
```json
{
  "_id": 1,
  "productId": "p1",
  "quantity": 2,
  "productDetails": [
    {
      "_id": "p1",
      "name": "Laptop",
      "price": 999
    }
  ]
}
```

---

## 🔁 Advanced Join with Pipeline `$lookup`

```js
db.orders.aggregate([
  {
    $lookup: {
      from: "products",
      let: { pid: "$productId" },
      pipeline: [
        { $match: { $expr: { $eq: ["$_id", "$$pid"] } } },
        { $project: { name: 1, price: 1 } }
      ],
      as: "productInfo"
    }
  }
])
```

---

## ⚠️ Limitations of Joins in MongoDB

| Limitation                     | Description                                       |
|-------------------------------|---------------------------------------------------|
| Only within the same database | `$lookup` doesn’t support cross-database joins    |
| Performance concerns          | Joins can slow down queries on large collections  |
| Complexity                    | Joins are less flexible than in relational SQL    |

---

## 🧠 Embedding vs Referencing

MongoDB encourages **denormalization** by embedding documents:

- ✅ Embed if related data is always accessed together.
- ✅ Use `$lookup` when data is reused and embedding is not feasible.

---

## 📌 Summary

| Feature                | Description                     |
|------------------------|---------------------------------|
| `$lookup`              | Basic join (like SQL LEFT JOIN) |
| Pipeline `$lookup`     | Advanced filtering and matching |
| Denormalization        | Embed related data where possible |
| Indexed foreign fields | Improve join performance        |

---

## 💡 Pro Tip

Always index the `foreignField` used in `$lookup` to speed up joins!