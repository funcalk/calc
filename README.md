Expressions BNF
---------------
* &lt;expression> ::= &lt;term> | &lt;expression> + &lt;term> | &lt;expression> - &lt;term>
* &lt;term> ::= &lt;number> | &lt;term> * &lt;number> | &lt;term> / &lt;number>
* &lt;number> ::= [0-9]+(.[0-9])?