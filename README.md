# A Finite State Machine Language and Compiler for JFLAP

This project is actually an assignment for the Aspen University course DCS906 
Automata Complexity Theory.  This is my independent study evaluation project. 
In this course we started off with discussions of FSMs and regular expressions. 
Later in the course we discussed languages and grammars.  I have in the 
past created and implemented Domain Specific Languages (DSL) before, and I thought
this a good opportunity to apply my skills with topics in the course.  The result 
is this language and compiler.

The language is intentionally simple, as I just need to demonstrate the 
concepts of language creation, and not bog down the reader with unnecessary 
complexity.  Additionally to make the effort interesting I decided to 
test out the language by writing a compiler for it. This project includes the 
code necessary to compile a FSM definition with this language into a form
that can be consumed by the JFLAP tool.  

[JFLAP](https://www.jflap.org/) is a set of software for exploring formal languages 
including Finite State Machines.  Actually it is so much more than even that.  It is 
a wonderful tool that has helped me understand finite automata.  A great accompaniment 
to the text.

This project was developed with Eclipse (Oxygen 3 for Java) with the Antlr plugins. 
it leverages the great work of the [Antlr project](https://www.antlr.org/), however I only
touched the full capabilities of this library.  It is a ready for prime time DSL 
creation tool with bindings for many languages, not just Java.  The project structure 
is simple, since this is just one class assignment, so don't expect to see all the 
and software engineering practices that I would apply in my professional capacity.  There
just wasn't the time or need to include JUnits, Maven dependency management, etc.

