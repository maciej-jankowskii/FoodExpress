<h1 align="center" id="title">FoodApp</h1>

<p id="description">A simple food ordering simulation application. 
  In the project, I used Spring Security, Spring Data JPA, Hibernate, Lombok, basic HTML, CSS with Thymeleaf. For testing, I utilized jUnit and Mockito.</br>
  The application allows for registering a new user. Next, from the available restaurants, we choose our favorite, add dishes to the cart, and finalize the purchase. 
  Each order can then be evaluated.</p>

<h2>🛠️ Installation Steps:</h2>

<p>1. You need to clone this repository.</p>

```
git clone https://github.com/maciej-jankowskii/foodapp.git
```

<p>2. You don't need to create an additional database on your side because the project uses an embedded H2 database, which significantly simplifies and speeds up the application launch process.</p>

<p>3. Now you can run the project. You can start using it by entering the following address in your browser:.</p>

```
localhost:8080
```

<h2>How it works ?</h2>
<p> 
  The first thing you will see is the homepage. You can register a new account. However, I recommend using my login credentials. It is an administrator account that has more permissions.
</p>

`````
email: nowak@example.com
password: hard
`````

<img src="https://github.com/maciej-jankowskii/foodapp/blob/32a2c9100dfe1f54bbad2158ae8bcda7a1076eba/foodapp.png" alt="project-screenshot" width="600" height="350/">
___________________________
<h3>Let's simulate ordering food now."</h3>
<p>
Choose your favorite restaurant, then select your favorite dish and add it to the cart. Remember that one order can only contain meals from one restaurant. </br>
You can choose two payment methods: payment on delivery or extra points payment.</br>
If you have chosen my pre-made administrator account, it has an additional 1000 points. The application, during the food ordering process, provides information on the value of your points in the lower right corner of the cart.
</br>
Place the order. Then, in a separate section, you can rate this order. Remember that you can rate each order only once.
</br>
As an administrator, you also have access to the administrator panel. There you can add new restaurants or edit existing ones.
</p>
  
___________________________
<h3>Enjoy 👋 </h3>
