%product(L - list, D - integer, M - integer, First - integer, R - List)
%flow model (i, i, i, i, o)

product([], _, 0, 0, []).
product([H|T], D, M, 0, R):-
    product(T, D, NewM, 0, NewR),
    Current is H * D + NewM,
    X is Current mod 10,
    append([X], NewR, R),
    M is Current // 10.
product([H|T], D, _, 1, R):-
    product(T, D, NewM, 0, NewR),
    Current is H * D + NewM,
    X is Current mod 10,
    append([X], NewR, NewR2),
    Current > 9,
    X2 is Current // 10,
    append([X2], NewR2, R).
product([H|T], D, _, 1, R):-
    product(T, D, NewM, 0, NewR),
    Current is H * D + NewM,
    X is Current mod 10,
    append([X], NewR, R).

%getMaxPositions(L - list, Max - integer, Pos - integer, R - list)
%flow model(i, o, i, o)
getMaxPositions([], Max, _, []):-
    Max is 0.
getMaxPositions([H|T], Max, Pos, R):-
    NewPos is Pos + 1,
    getMaxPositions(T, NewMax, NewPos, _),
    H > NewMax,!,
    Max is H,
    append([Pos], [], R);
    NewPos is Pos + 1,
    getMaxPositions(T, NewMax, NewPos, NewR),
    H =:= NewMax,!,
    Max is H,
    append([Pos], NewR, R);
    NewPos is Pos + 1,
    getMaxPositions(T, NewMax, NewPos, NewR),
    H < NewMax,!,
    Max is NewMax,
    append([], NewR, R).

%replaceMaxPositions(L - list, R - list)
%flow model (i, o)
replaceMaxPositions([], []).
replaceMaxPositions([H|T], R):-
    replaceMaxPositions(T, NewR),
    is_list(H),
    getMaxPositions(H, _, 1, TempList),
    append([TempList], NewR, R);
    replaceMaxPositions(T, NewR),
    not(is_list(H)),
    append([H], NewR, R).





