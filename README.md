# Parking application

### Description
Application API for reserving parking spaces.

---

## API endpoints


###User


**Registration**

**POST** - `/api/v1/user/registration`


Request body example:
```json
{
  "username" : "username",
  "password" : "password"
}
```


Fields:

| Name | Data type | Description |
| ---- | --------- | ----------- |
| username | String | New user username |
| password | String | New user password |

<br>
<br>
<br>

**Get user info**

**Only admin**

**Get** - `/api/v1/user`

<br>

**Only admin**

**Get** - `/api/v1/user/{id}`

Param:

| Name | Data type | Description |
| ---- | --------- | ----------- |
| id | Long | User id |


---

<br>
<br>
<br>


###Parking slot


**Create new parking slot**

**Only admin**

**POST** - `/api/v1/parkingSlot`


Request body example:
```json
[
  {"info" : "information"},
    //...
  {"info" : "information"}
]
```

Fields:

| Name | Data type | Description |
| ---- | --------- | ----------- |
| info | String | Some information about new slot (can be empty) |

<br>
<br>
<br>

**Delete existing parking slot**

**Only admin**

**DELETE** - `/api/v1/parkingSlot/{id}`

Fields:

| Name | Data type | Description |
| ---- | --------- | ----------- |
| id | Long | id of slot |

<br>
<br>
<br>

**Get all parking slots**

**GET** - `/api/v1/parkingSlot`


<br>
<br>
<br>

**Get existing parking slot**


**GET** - `/api/v1/parkingSlot/{id}`

Fields:

| Name | Data type | Description |
| ---- | --------- | ----------- |
| id | Long | id of slot |


---

###Parking session


**Create new parking session**

**POST** - `api/v1/parking`

Possible args:
> anySlot - boolean

Request body example:

If `anySlot` arg is true:
```json
{
  "startAt": "2020-01-01T12:00:00",
  "endAt": "2020-01-01T16:00:00"
}
```


If `anySlot` arg is false or null:
```json
{
  "slotId": 3,
  "startAt": "2020-01-01T12:00:00",
  "endAt": "2020-01-01T16:00:00"
}
```

Fields:

| Name | Data type | Description |
| ---- | --------- | ----------- |
| slotId | Long | id of slot |
| startAt | String | Date in format of "yyyy:mm:ddThh-mm-ss" |
| endAt | String | Date in format of "yyyy:mm:ddThh-mm-ss" |

Response example:
```json
{
  "ticketId": 1,
  "userPublicName": "name",
  "userId": 1,
  "slotId": 1,
  "startAt": "2020-01-01T12:00:00",
  "endAt": "2020-01-01T16:00:00"
}
```


<br>
<br>
<br>

**Cancel session**

**DELETE** - `api/v1/parking`


Cancel (delete) user sessions

<br>
<br>
<br>

**Get session by id**

**GET** - `api/v1/parking/{id}`


Fields:

| Name | Data type | Description |
| ---- | --------- | ----------- |
| id | Long | id of session |

Response example:
```json
{
  "sessionId": 1,
  "userId": 1,
  "userPublicName": "name",
  "parkingSlot": {
    "id": 1,
    "info": "info"
  },
  "startAt": "2020-01-01T12:00:00",
  "endAt": "2020-01-01T16:00:00",
  "active": false,
  "expired": false
}
```

<br>
<br>
<br>

**Get sessions**

**GET** - `api/v1/parking`

**GET** - `api/v1/parking/active`

**GET** - `api/v1/parking/nonExpired`


Response example:
```json
[
    {
      "sessionId": 1,
      "userId": 1,
      "userPublicName": "name",
      "parkingSlot": {
        "id": 1,
        "info": "info"
      },
      "startAt": "2020-01-01T12:00:00",
      "endAt": "2020-01-01T16:00:00",
      "active": false,
      "expired": false
    },
    {
      "sessionId": 2,
      "userId": 2,
      "userPublicName": "name2",
      "parkingSlot": {
        "id": 2,
        "info": "info2"
      },
      "startAt": "2020-01-01T12:00:00",
      "endAt": "2020-01-01T16:00:00",
      "active": false,
      "expired": false
    }
]
```
---
