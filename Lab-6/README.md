# Lab-6: MongoDB Schema & CRUD Operations

Create a schema in MongoDB and demonstrate CRUD operations (Create, Read, Update, Delete).

## Prerequisites

- **Node.js** (v16+)
- **MongoDB** running locally, or a MongoDB Atlas connection string

## Setup & Run

1. Install dependencies:
   ```bash
   cd Lab-6
   npm install
   ```

2. (Optional) Use MongoDB Atlas instead of local MongoDB:
   - Set environment variable: `MONGODB_URI=mongodb+srv://user:pass@cluster.mongodb.net/lab6_db`

3. Start the server:
   ```bash
   npm start
   ```

4. Open http://localhost:3000 in your browser

## Schema (Product)

| Field       | Type    | Required | Notes                      |
|-------------|---------|----------|----------------------------|
| name        | String  | ✓        | max 100 chars              |
| description | String  | -        | optional                   |
| price       | Number  | ✓        | min 0                      |
| category    | String  | ✓        | enum: Electronics, Clothing, Books, Food, Other |
| inStock     | Boolean | -        | default: true              |
| quantity    | Number  | -        | default: 0, min 0          |
| timestamps  | -       | -        | createdAt, updatedAt       |

## CRUD API Endpoints

| Method | Endpoint           | Action                    |
|--------|--------------------|---------------------------|
| POST   | /api/products      | Create new product        |
| GET    | /api/products      | Read all products         |
| GET    | /api/products/:id  | Read single product       |
| PUT    | /api/products/:id  | Update product            |
| DELETE | /api/products/:id  | Delete product            |
