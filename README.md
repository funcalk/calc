Expressions BNF
---------------
* &lt;expression> ::= &lt;term> | &lt;expression> + &lt;term> | &lt;expression> - &lt;term>
* &lt;term> ::= &lt;factor> | &lt;term> * &lt;factor> | &lt;term> / &lt;factor>
* &lt;factor> ::= &lt;primary> | -&lt;primary> | +&lt;primary>
* &lt;primary> ::= [0-9]+(0.[0-9])? | (&lt;expression>)
