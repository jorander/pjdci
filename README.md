# Plain Java DCI (v1.0)

This is an attempt to implement [DCI] http://en.wikipedia.org/wiki/Data,_context_and_interaction in plain Java. This has
been said to be impossible, due to short-comings of the Java language. And indeed, this example implementation contains a few
unwanted obstacles, and will probably be regarded as not being DCI by some people. Still, I think it shows that many of
the advantages of DCI can be achieved even in a static, class-oriented, non-meta-programming language, such as Java.
Advantages that are too valuable to leave un-explored in language with such wide-spread use as Java.

#### Advantages
DCI strives to implement functionality based on the mental model of a use-case. This implementation provides possibility
to define a context, containing roles, played by pre-existing objects (role players). It is done through wrapping of
role players. The obvious problems to that approach, regarding object equality and hash codes, are handled by a small
set of abstract super classes to be used for role and role player objects. In general, frameworks forcing inheritance
 of specific abstract classes are seen as intrusive and part of the past. However, in this particular
 case I can't see any severe consequences since DCI explicitly aims to remove the use of inheritance in the domain code.

In addition DCI strives to make the implementation of a use-case clear and easy to reason about. It is achieved through
 separating the logic in the use-case from the simple data-model of the system by factoring all code implementing a
 specific use-case into a single context. This provides for no need to walk-through a numerous set of domain classes
 to trace the execution path of a single use-case. This DCI-implementation makes it possible to achieve in plain Java.

#### Issues
Since Java, the language, has its set of limitations there are a few issues with this implementation compared to
implementations in other languages.
- Object equality must be tested through the well known equals()-method, not by '=='. This is, in my opinion, not a real
issue since alla Java programmers know that idiom.
- Role player objects need to have a special implementation of the equals()-method, since the DCI-implementation, must
account for comparisons between role objects and role player objects. This is further described in the documentation of
the 'RolePlayer' interface and the 'AbstractRolePlayer'.
- Role definitions are implemented as classes (internal to the context) and us such requires a boiler-plate constructor.
This is unfortunate, but non-complex.
- Role player methods cannot be directly accessed via the role identifier. Due to role objects wrapping the role player
the role player can only be accessed from within role methods. Therefore any methods called through the role identifier
must be defined in the role. In their simplest form role methods might just delegate to the role player. This forces some
extra delegation methods on the role, if needed in the context. Also this is unfortunate, but non-complex.
- In some method calls, certainly all reaching outside the context, the role player type is needed rather than the role.
In those cases it is necessary to implement a role conversion method, typically "as<Role player type>()" to return the role
player. This is due to the static typing of Java, and a bit annoying, but easily detected since it is caught by the
compiler. In fact it is good way to make sure roles never exists outside their enclosing context.

#### Content of this example implementation
- pjdci.core: Contains one interface and two abstract classes used for roles and role players. This is the
"DCI-implementation"; the rest is convention.
- pjdci.moneytransfer: Contains an implementation of the well known money transfer example. It isa very simple example
only meant to give an idea of the syntax and conventions used in this DCI implementation.
- pjdci.dijkstra: Contains an implementation of the well known Dijkstra Manhattan example. A more involved example used
to demonstrate the capabilities (and weaknesses) of this DCI implementation.

All packages are accompanied with a set of test cases using the same package structure.

#### Future work
This example implementation is now presented to the DCI community and I hope for a constructive discussion. The planned
future work is as follows:
1. Small-grained role contracts.
2. Extended MoneyTransfer example with other contexts as role players.
3. Extended MoneyTransfer example with "surrounding" infrastructure code simulating a layered architecture with a front-end
  and back-end application server.

#### Finally
Feel happy to clone and play around with the code. If you wish to help, send a pull request, or contact me on
email at jorgen dot x dot andersson at gmail dot com or Twitter at se_thinking.

Jörgen Andersson, 2013-02-01
---

#### Java versions
Both the core classes and the examples are implemented in a plain Java project, using Junit4 to drive tests. The code
is Java 7, compliant, but can easily be tailored to Java 6 by adding full type signatures in the diamond ("<>") operations
where generic objects are instantiated.

#### Attributions
This implementation is based on Marc Grue's early work on Scala-based DCI ([Full-OO] http://fullOO.info (2013-01-25)),
the examples of the [Marvin](http://fulloo.info/Examples/Marvin/Introduction/)DCI language by Rune Funch.and
inspired by the initiated discussions on the [Object-composition](https://groups.google.com/forum/?fromgroups#!forum/object-composition) mailing-list.

#### Disclaimer
- This is just an experiment, published here in order to be discussed, refined and extended.
- _ This is NOT production ready code. _

#### Resources
[Object-composition](https://groups.google.com/forum/?fromgroups#!forum/object-composition),
[Full-OO](http://fulloo.info),
[DCI wiki](http://en.wikipedia.org/wiki/Data,_Context,_and_Interaction)