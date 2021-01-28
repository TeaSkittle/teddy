# Teddy

### Overview

This project is a text editor with extra features and programs built in, similar in concept to GNU/Emacs, but this is no emacs and is not meant to be a replacement for one. Simply I have always wanted to create a real text editor, and this is my contribution to the other thousands that are out there. I have already created a simple line-based editor ( influenced by the classic UNIX ed ) which was written in POSIX sh. You can find the repo for that here: [ted]( https://github.com/TeaSkittle/ted ). So this project is my take on writting a GUI editor with more features and that can be actually used from day-to-day. Inspired by the naming convention used in vim ( and due to the fact this is my second text editor ), I call this editor **tedi**, which is an acronym for: **T**ravis' **ED**itor **I**mproved.

> Although don't underestimate the power of line-based editors, as most of the UNIX core was written in ed! ( don't belive me, read this: http://www.catb.org/~esr/writings/taoup/html/ch13s02.html ).

### TODO

- Split docs into several Markdown files
  - Maybe try using a GitHub wiki
  
---

## Ed

A simple text editor written in JavaFX.

> A HUGE thanks to https://github.com/mwaddo/TextEditor/, without this I would not have been able to figure out how to implement the opening and saving of files.

### Features

 *Implemented*  
- Dark theme
- Mode line
- Keyboard shortcuts
- Simple interperter in the bottom TextArea

*Not Yet Implemented*
- Working on Windows, Linux, Mac, & BSD ( need to test )

### Shortcuts

*C = Control*
- C-x Switch to bottom TexArea and input commands( see *Shell* section below )
- C-s Save as file
- C-o Open file
- C-f Move cursor forward one character
- C-b Move cursor back one character

I included a simple "Hello World" java program that I wrote in this editor to be a simple proof of concept.

> I want to add more functionality here, but JavaFX is limiting in what it can accomplish with text manipulation. If I want to make a more full fledged editor I would have to use something similar to [RichTextFX]( https://github.com/FXMisc/RichTextFX ), or avoid JavaFX all together.

---

## Shell

This is a pretty simple interpreter designed to be used in the bottom TextArea of Ed. Only has a few commands, but can easily add more in the future.

*Commands:*
- print: prints all text after the command( similar to UNIX's echo )
- calc: run a calculation in Calc.java( see section below )
- quit: successfully exit the program

---

## Calc

This is s stack based calculator designed for programmers which uses Reverse Polish Notation( RPN for short, also called: Postfix notation ). It can do all sorts of calculations, but the syntax is different from what we are normally use to. For example in normal syntax ( Infix ), a simple addition problem looks as such:
```Java
12 + 7 = 17
```
With RPN that is done like this:
```Java
12 7 + = 17
```

It takes some time to get used to but you can do complex calcutaions even without the need to use parenthesis. A good example of its capabilities is converting Celcius to Fahrenheit. Here is an example converting 20°C to 68°F:
```Lisp
Infix:
( 20 × 9/5 ) + 32 = 68

Postfix:
20 9 5 / * 32 + = 68
```

> Worth noting that this calculator handles both integers and floating point numbers.

### Operators

```Java
+ Addition
- Subtraction
* Multiplication
/ Division
^ Exponents
% Modulo
! Factorial
r Square Root
```
> Factorial and sqyare roots are the odd ones here, the calcutalor expects atlteast two numbers and an operator, so if you only want to the facotial of one number place a 0 before( although this issue is not present if more than 1 number is given ). Here is an example of finding 10 factorial ( square roots work the same way ):

```Java
// Will not work
calc 10 !
error

// This works
calc 0 10 !
362880.0

// No 0 needed if other values are present
calc 2 10 ! *
725760.0
```

### Examples

Here are simple examples showcasing each operator  
```Java
// Addition
calc 5 2 +
7

// Subtraction
calc 10 2 -
8

// Multiplication
calc 3 6 * 
18

//. Division
calc 25 5 /
5

// Exponents
calc 3 2 ^ 
9

// Modulo
calc 10 6 % 
4

// Factorial
calc 0 5 ! 
24

// Square Root
calc 0 25 r
5
```

### Use in code

To use Calc.java in another project is quite simple, create with simple contructr and either call the REPL for interactive use, or call with String[] argument for a single calcutalation.
```Java
// Create new Calc object
Calc calc = new Calc();

// Create String[] to calculate
String[] tokens = new String[] { "2", "1", "+", "3", "*" };

// Will print out calculation ( "9" in this example )
System.out.println( calc.run( tokens ));

// Call the interactive Read Eval Print Loop ( REPL )
calc.repl();

// To exit the REPL, enter: 
calc 0 0 q
```
