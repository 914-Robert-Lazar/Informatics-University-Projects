
insert2(L, E, [E|L]).
insert2([H|T], E, [H|R]):-
    insert(T, E, R).

%arrangement(L - list, K - integer, R - list)
arrangement([E|_], 1, [E]).
arrangement([_|T], K, R):-
    K >= 1,
    arrangement(T, K, R).
arrangement([H|T], K, R1):-
    K > 1,
    K1 is K - 1,
    arrangement(T, K1, R),
    insert2(R, H, R1).


allSol(L, K, R):-
    findall(RL, arrangement(L, K, RL), R).

f5([], 0).
f5([H|T], S):-
    f5(T, S1),
    S1 is S - H.




