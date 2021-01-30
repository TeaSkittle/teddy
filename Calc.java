// (c) Travis Dowd
// (d) 6-17-2020
//
// RPN calc
//
// based off of: https://www.programcreek.com/2012/12/leetcode-evaluate-reverse-polish-notation/
// overview of stack based calc: https://medium.com/@eauditory3/what-are-stack-based-calculators-cf2dbe249264
//

package tedi;

import java.util.Stack;
import java.lang.Math;
import java.util.Scanner;
import java.util.EmptyStackException;

public class Calc {
	// Constructor
	public Calc(){}
	
	// -----------------------
	//   Single Calculation
	// -----------------------
	//
	// Caluclate an expression without the need for a REPL loop
	//
	public double run( String[] tokens ){
		try {
			return rpn( tokens );
		} catch ( EmptyStackException e ) {
			System.out.println( "[-]Error: Empty stack" );
		} return 1;
	}
	
	// ----------
	//    REPL
	// ----------
	//
	// Loop and get input from user, use intereactively
	//
	public static void repl() {
		int run = 1;
		try ( Scanner input = new Scanner( System.in )) {
			while ( run > 0 ) {
				System.out.print( "> " );
				String[] tokens = input.nextLine().split( " " );
				try { 
					System.out.println( rpn( tokens ));
				} catch ( EmptyStackException e ) {
					System.out.println( "[-]Error: Empty stack" );
				}
			}
		}
	}
	
	// -------------
	//   RPN Logic
	// -------------
	//
	// The core logic of the calculator
	//
	public static double rpn( String[] tokens ) throws EmptyStackException {
		double returnValue = 0;
		String operators = "+-*/^%!rq";
		Stack<String> stack = new Stack<String>();
		for( String t : tokens ){
			if( !operators.contains( t )){
				stack.push( t );
			} else {
				double a = Double.valueOf( stack.pop() );
				double b = Double.valueOf( stack.pop() );
				int index = operators.indexOf( t );
				switch( index ) {
					case 0: stack.push( String.valueOf( a + b )); break;
					case 1: stack.push( String.valueOf( b - a )); break;
					case 2: stack.push( String.valueOf( a * b )); break;
					case 3: stack.push( String.valueOf( b / a )); break;
					case 4: stack.push( String.valueOf(( Math.pow( b, a )))); break;
					case 5: stack.push( String.valueOf( b % a )); break;
					case 6: 
						double fact = 1;
						stack.push( String.valueOf( b ));
						for( int i = 1; i < a; i++){
							fact *= i;
						} stack.push( String.valueOf( fact ));
						break;
					case 7:
						stack.push( String.valueOf( b ));
						stack.push( String.valueOf( Math.sqrt( a )));
						break;
					case 8: System.exit(0); break;
				}
			}
		} try {
			Double.parseDouble( stack.peek() );
			returnValue =  Double.valueOf( stack.pop() );
		} catch ( NumberFormatException e ) {
			System.out.print( "[-]Error: Unknown value " );
		} stack.clear();
		return returnValue;
	}
	
	// --------
	//   Main
	// --------
	//
	// Goes straight to repl if ran by itself
	//
	public static void main( String[] args ) {
		repl();
	}
}
