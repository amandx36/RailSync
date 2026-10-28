# Backend API Contract

Base URL: `NEXT_PUBLIC_API_URL` or `http://localhost:8080`

All endpoints use JSON. The backend currently permits every `/api/v1/**` route, but the client supports JWT authentication with `Authorization: Bearer <token>` for future protection.

## Enums

- `TrainType`: `EXPRESS`, `SUPERFAST`, `PASSENGER`, `LOCAL`
- `TrainStatus`: `ACTIVE`, `CANCELLED`, `MAINTENANCE`
- `UserRole`: `ROLE_USER`, `ROLE_ADMIN`, `ROLE_GUEST`

## Authentication

### `POST /api/v1/auth/register`

Request:

```json
{
  "email": "user@example.com",
  "firstName": "Ada",
  "lastName": "Lovelace",
  "password": "password123"
}
```

Validation: valid email; all fields required; password length is 8 to 100 characters.

Success response:

```json
{
  "email": "user@example.com",
  "message": "Registration successful",
  "role": "ROLE_USER",
  "token": "<jwt>"
}
```

Duplicate email currently returns HTTP 200 with only `message: "Email already registered"`.

### `POST /api/v1/auth/login`

Request:

```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

Success response:

```json
{
  "token": "<jwt>",
  "email": "user@example.com",
  "role": "ROLE_USER",
  "message": "Login successful"
}
```

Unknown user and invalid password currently return HTTP 200 with only `message` (`"User not found"` or `"Invalid password"`).

## Trains

### Train request body

Used by `POST /api/v1/add-train` and `PUT /api/v1/trains/{id}`:

```json
{
  "trainNumber": 101,
  "trainName": "Morning Express",
  "trainType": "EXPRESS",
  "sourceStation": "Rabat",
  "destinationStation": "Casablanca",
  "totalSeats": 300
}
```

All fields are required. `trainNumber` and `totalSeats` must be positive. Text fields cannot be blank.

### Train response

```json
{
  "id": 1,
  "trainNumber": 101,
  "trainName": "Morning Express",
  "trainType": "EXPRESS",
  "sourceStation": "Rabat",
  "destinationStation": "Casablanca",
  "availableSeats": 300,
  "status": "ACTIVE"
}
```

The current mapper does not return `totalSeats`, `createdAt`, or `updatedAt`.

### Endpoints

| Method | Path | Request | Response |
| --- | --- | --- | --- |
| `POST` | `/api/v1/add-train` | `TrainRequest` | `TrainResponse` |
| `GET` | `/api/v1/trains` | Query params below | `SpringPage<TrainResponse>` |
| `GET` | `/api/v1/trains/{id}` | None | `TrainResponse` |
| `PUT` | `/api/v1/trains/{id}` | `TrainUpdateRequest` | `TrainResponse` |
| `DELETE` | `/api/v1/trains/{id}` | None | `204 No Content` |
| `PATCH` | `/api/v1/trains/{id}/cancel` | None | `TrainResponse` |
| `PATCH` | `/api/v1/trains/{id}/maintenance` | None | `TrainResponse` |

`GET /api/v1/trains` supports optional `source`, `destination`, `type`, `page`, `size`, and `sort` parameters. Defaults are `page=0`, `size=20`, and sort by `trainName`.

The typed definitions and ready-to-use calls are in `types/api.ts` and `lib/api-client.ts`.
