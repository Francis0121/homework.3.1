%merge
merge2([], X, X).
merge2( [X|L1], L2, [X|L3] ) :- merge2(L1, L2, L3) .

% len
len([], 0).
len([_|T], R) :- not(atom(T)), len(T, N), R is N+1.

% flatten
flatten2([], 0).
flatten2( [H|T] , R) :- atom(T), flatten2(T, L), append([H], L, R). 