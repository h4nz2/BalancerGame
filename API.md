**Players**

----
**Get score(s) of a player**

* **Decription**

  Returns json data about a score of a single player. In case the player has submitted multiple scores, an array of scores is returned.
    
* **URL**

    /players/:name
    
* **Method:**

    `GET`

* **URL PARAMS**

  **Required:**
  
  `name=[String]`
  
* **Data Params**

  None
  
* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{ name: "MoonWalker78", score: 500, date: Jan 20 2017 19:35:27 }`

* **Error Response:**
  
  * **Code:** 400 'Player does not exist'
 
    
* **Sample Call:**

```javascript
    $.ajax({
      url: "/players/MoonWalker78",
      dataType: "json",
      type : "GET",
      success : function(r) {
        console.log(r);
      }
    });
  ```
  
----
**Get all scores**

* **Description**

  Returns all the scores.

* **URL**

    /scores
    
* **Method:**

  `GET`

* **URL Params**

  None
  
* **Data Params:**

  None
  
* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
    `[ {name: "MoonWalker78", score: 500, date: Jan 20 2017 19:35:27}, 
    {name: "Johny", score: 800, date: Jun 25 2017 09:45:33},
    {name: "Lady", score: 100, date: Feb 08 2015 20:00:00} ]`
    
* **Error Response**

* **Sample Call:**

```javascript
    $.ajax({
      url: "/scores",
      dataType: "json",
      type : "GET",
      success : function(r) {
        console.log(r);
      }
    });
  ```
