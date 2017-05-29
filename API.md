**Players**

----
**Get score(s) of a player**

* **Decription**

  Returns json data about a score of a single player. In case the player has submitted multiple scores, an array of scores is returned.
    
* **URL**

    /scores/player
    
* **Method:**

    `GET`

* **URL Parameters**

  **Required:**
  
  If you do not include the name you will get an empty array.
  
  `name=[String]`
  
* **Data Parameters**

  None
  
* **Success Response:**

  * **Code:** 200
  * **Content:** 
  `{
    "date": "29/05/2017 00:01:21",
    "id": 60,
    "latitude": 52.3708,
    "longitude": 4.91079,
    "name": "CrackDemon",
    "score": 10862
  }`
  
----
**Get all scores**

* **Description**

  Returns all the scores.

* **URL**

    /scores
    
* **Method:**

  `GET`

* **URL Parameters**

  None
  
* **Data Parameters**

  None
  
* **Success Response:**

  * **Code:** 200
  * **Content:** 
`[
  {
    "date": "29/05/2017 10:57:41",
    "id": 61,
    "latitude": 52.371,
    "longitude": 4.91113,
    "name": "Shitty green",
    "score": 11780
  },
  {
    "date": "29/05/2017 00:01:21",
    "id": 60,
    "latitude": 52.3708,
    "longitude": 4.91079,
    "name": "CrackDemon",
    "score": 10862
  }
  ]`
      
----
**Post a score**

* **Description**

  Posts a new score.
  
* **URL**

    /score/post
    
* **Method**

    `POST`
    
* **URL Parameters**

  None
  
* **Data Parameters**

  `{
    "name":[string],
    "score":[integer],
    "latitude":[float],
    "longitude":[float]
    }`
    
  
* **Success Response:**

  * **Code** 200 <br />
    **Content** 
    `{
      "date": "29/05/2017 11:27:15",
      "latitude": 5.8,
      "longitude": 127.9,
      "name": "Postman",
      "score": 2000
    }`
    