% 20093267 김성근

%merge
merge2([ ],[ ],[ ]).
merge2([X],[ ],[X]).
merge2([ ],[Y],[Y]).
merge2([X|List1], [Y|List2], [X|List]) :-
        X =< Y,
	!,
	merge2(List1,[Y|List2],List).
merge2([X|List1],[Y|List2],[Y|List]) :-
        merge2([X|List1],List2,List), 
	!. 

% len
len([H|T],N) :-
    len(H, LH),
    len(T, LT),
    !,
    N is LH + LT.
len([], 0):-!.
len(_, 1):-!.

% flatten
flatten2( [ ] , [ ] ) :- ! . 
flatten2( [ H | T ] , R ) :- 
	!,
	flatten2( H , RH ),
	flatten2( T , RT ),
	append( RH , RT , R ).
flatten2( L , [ L ] ).

%member
member2(X, [ X | _ ] ).
member2(X, [ _ | Y ] ):- 
	member2(X, Y).

%subset
subset2( [ H | T ], R) :- 
	member2(H, R), 
	subset2(T, R).
subset2( [ ] , _ ).

%union
union2( [ H | T ], O, R) :- 
	member2(H, O), 
	!, 
	union2(T, O, R).
union2( [ H | T ], O, [ H | R ]) :- 
	union2( T, O, R).
union2( [ ], R, R).

%intersection
intersection2( [ H | T ], O, R) :-
	member2( H, O),
	!,
	intersection2(T, O, R).
intersection2( [ H | T ], O, [ H | R ]) :- 
	intersection2( T, O, R).
intersection2( R, [ ] , R).


%set_diff
set_diff( [ H | T ], O, R) :- 
	member2(H, O), 
	!, 
	set_diff(T, O, R).
set_diff( [ H | T ], O, [ H | R ]) :- 
	set_diff(T, O, R).
set_diff( [ ], _, [ ] ).