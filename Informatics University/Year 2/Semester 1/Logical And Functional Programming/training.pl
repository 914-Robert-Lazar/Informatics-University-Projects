p(1).
p(2).
q(1).
q(2).
r(1).
r(2).

s:-!,
    p(X),
    q(Y),
    r(Z),
    write(X,Y,Z),nl.

f([], 0).
f([H|T], S):-
    f(T, S1),
    S1 < H,!,
    S is H.
f([_|T], S):-
    f(T, S1),
    S is S1.

aux(H, S1, S):-
    S1 < H,!,
    S is H.
aux(_, S1, S):-
    S is S1.

f2([], 0).
f2([H|T], S):-
    f2(T, S1),
    aux(H, S1, S).

f3([],[]).
f3([H|T], [H|S]):-
    f3(T, S).
f3([H|T], S):-
    H mod 2 =:= 0,
    f3(T, S).

fxd(L, R):-
    findall(L, f3(L, R), R).

%insert(L - list, E - integer, R - list)
%flow model (i, i, o)
insert(L, E, [E|L]).
insert([H|T], E, R):-
    insert(T, E, R1),
    R = [H|R1].



%perm(L - list, R - list)
%flow model (i, o)
permutations([], []).
permutations([H|T], R):-
    permutations(T, R1),
    insert(R1, H, R).

%max3(L - list)
%flow model (i)
max3([_]).
max3([H1, H2|T]):-
    abs(H1 - H2) =< 3,
    max3([H2|T]).

%combo(L = list, R - list)
%flow model (i, o)
combo([], []).
combo([H|T], R):-
    combo(T, R1),
    max3(H),!,
    R = [H|R1].
combo([_|T], R):-
    combo(T, R).



insertAll(L, R, R1):-
    findall(R, permutations(L, R), R1).

solve(L, R):-
    insertAll(L, _, R1),
    combo(R1, R).











