grammar Fsm;
@header {
    package net.conallen.fsmdsl;
}

machine  : LBRACE WS* statement ( ',' statement )* WS* RBRACE;     

statement:
	start_state WS* transition WS* end_state
;

transition :
	HYPHEN input ARROW
;  

input : CHAR | STRING;

start_state: state ;

end_state: state;

state:  intermediate_state | final_state;

intermediate_state: LPAREN state_name RPAREN ;

final_state: LBRACKET state_name RBRACKET ;

state_name: CHAR | STRING;

WS : [ \t\r\n]+ -> skip ; 

CHAR : ([a-z]|[A-Z]|[0-9]|SPACE) ;

STRING : CHAR CHAR* ;

LPAREN:     '(';
RPAREN:     ')';
LBRACKET:   '[';
RBRACKET:   ']';
LBRACE:     '{';
RBRACE:     '}';
ARROW:      '->';
HYPHEN:		'-';
SPACE:		' ';
COMMA:		',';
