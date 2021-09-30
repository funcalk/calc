Expressions BNF
---------------
* &lt;expression> ::= &lt;term> | &lt;expression> + &lt;term> | &lt;expression> - &lt;term>
* &lt;term> ::= &lt;factor> | &lt;term> * &lt;factor> | &lt;term> / &lt;factor>
* &lt;factor> ::= &lt;number> | -&lt;number> | +&lt;number>
* &lt;number> ::= [0-9]+(.[0-9])?