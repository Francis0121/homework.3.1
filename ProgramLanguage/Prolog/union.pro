len([], 0).
len([_|T], L) :- len(T, N), L is N+1.

reverse([], []).
reverse([H|T], R) :- reverse(T, L), append(L, [H], R).

member(Element, [Element | _]).
member(Element, [_ | List]) :- member(Element, List).

u1([], X).
u1([X|Y], Z) :- not member(X, Z), u1(Y, Z).

u2([], X).
u2([X|_], Z) :- member(X, Z), !, fail.
u2([X|Y], Z) :- u2(Y, Z).

nonmember(Element, []).
nonmember(Element, [Element | _]) :- !, fail.
nonmember(Element, [_ | List]) :- nonmember(Element, List).

u3([], X).
u3([X|Y], Z) :- nonmember(X, Z), u3(Y, Z).

notmember(X, Y) :- member(X, Y), !, fail.

u4([], X).
u4([X|Y], Z) :- notmember(X, Z), u4(Y, Z).
