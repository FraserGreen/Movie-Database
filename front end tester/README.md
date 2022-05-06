## Patterns Example : Lecture #2 - SEF S2 2020

This example is modified from an example produced by Javalin (as you can see on the fork/commits history). It will be reviewed during Lecture 2, and **uses _the same frameworks_ that you are using during the Team Assignment**. So coming to the lecture will be valuable.


<br />

### Frameworks
Let's check some of the frameworks this uses.

## Maven

[http://maven.apache.org/](http://maven.apache.org/)

Maven is a **build automation tool** used primarily for Java projects. It manages dependencies for your project through a file named `pom.xml`. 

* Read more about `pom.xml` [here](http://maven.apache.org/pom.html#What_is_the_POM).

* The _dependencies_ are the _libraries_ that you use in your project. Instead of downloaded the `*.jar` file and putting that in your project manually, you just add some text to the `pom.xml` and Maven "manages" it for you.

* If you search for a dependency on Maven Repository, it will give you the code that you need to add to your `pom.xml` file. It is quite straightforward: [https://mvnrepository.com/](https://mvnrepository.com/).



## Javalin

[https://javalin.io/](https://javalin.io/)

There are many frameworks to code webapps, but Javalin is simple and straightforward.

* Frameworks like Javalin allow you to implement _patterns_ very easily, and in a simplified way. This will help with MVC and 3-tier.

* It works with Java8.

* There are many examples, but beware! You can also use Javalin with Kotlin, and some examples are only in Kotlin or in both languages.

    - [Tutorials](https://javalin.io/tutorials/)
    - [Documentation](https://javalin.io/documentation)



## Velocity

[https://velocity.apache.org/](https://velocity.apache.org/)

Velocity is another tool provided by Apache. Javalin uses Velocity on its MVC implementation... therefore, you have to use it as well. Think of velocity as a curated/reduced version of HTML. 
