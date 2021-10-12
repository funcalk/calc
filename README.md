funcalk
=======
**Fun**ctional **Cal**culus for **K**otlin is a library for defining and calculating
number and higher-order functions. 

Expression BNF
--------------
* &lt;expression> ::= &lt;term> | &lt;expression> + &lt;term> | &lt;expression> - &lt;term>
* &lt;term> ::= &lt;factor> | &lt;term> * &lt;factor> | &lt;term> / &lt;factor>
* &lt;factor> ::= &lt;primary> | -&lt;primary> | +&lt;primary>
* &lt;primary> ::= [0-9]+(0.[0-9])? | (&lt;expression>) | &lt;constant>
* &lt;constant>) ::= [a-zA-Z]+
