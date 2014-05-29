%% Demo coming from http://clwww.essex.ac.uk/course/LG519/2-facts/index_18.html
%%
%% Please load this file into SWI-Prolog
%%
%% Sam's likes and dislikes in food
%% 
%% Considering the following will give some practice
%% in thinking about backtracking.
%% ?- likes(sam,dahl).
%% ?- likes(sam,chop_suey).
%% ?- likes(sam,pizza).
%% ?- likes(sam,chips).
%% ?- likes(sam,curry).

%likes(sam,Food) :-indian(Food), mild(Food).
	%sam은 인도음식이고 마일드하면 좋아한다.
%likes(sam,Food) :- chinese(Food).
	%sam은 중국음식을 좋아한다.
%likes(sam,Food) :-  italian(Food).
%likes(sam,chips).

%likes(sam,Food) :- indian(Food), mild(Food).
%likes(sam,chips).
%X = dahl ;
%X = tandoori ;
%X = kurma ;
%X = chips.
likes(sam,Food) :- mild(Food).
likes(sam,Food) :- indian(Food), !.
% X = dahl. 하고 아무것도 찾지 않는 BackTracking을 하지 않는다.
% ! (cut) 이 있으면 dahl이 setting이 되면 그다음 조건을 확인해서 mild 한것을 확인하고 그만둔다.
% 탐색 중지 cut 왼쪽에 있는것은 backTracking 하지 않는다.
% 앞에것을 만족하고 나면 다른 것을 고려할 필요가 없을때

% fail은 search 실패를 나타낸다. 더이상 찾아도 그 밑으로는 닶이 없다.

indian(dahl).
indian(tandoori).
indian(curry).
indian(kurma).

mild(dahl).
mild(tandoori).
mild(kurma).

chinese(chow_mein).
chinese(chop_suey).
chinese(sweet_and_sour).

italian(pizza).
italian(spaghetti).
