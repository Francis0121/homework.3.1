%% This is a comment line.
%%
male(bill).
male(jake).
female(mary).
female(shelley).

father(bill, jake).
father(bill, shelley).
mother(mary, jake).
mother(mary, shelley).

ancestor(marry, shelley) :- mother(mary, shelley).

parent(X, Y) :- mother(X, Y).
parent(X, Y) :- father(X, Y).
grandparent(X, Z) :- parent(X, Y), parent(Y, Z).
sibling(X, Y) :- mother(M, X), mother(M, Y),
	father(F, X), father(F, Y).
