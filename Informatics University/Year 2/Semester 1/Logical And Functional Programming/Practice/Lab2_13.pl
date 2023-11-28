%removeCons(L - list, P - integer, R - list)
%flow model (i, i, o)

removeCons([], _, []).
removeCons([H1, H2|T], P, R):-
    removeCons([H2|T], H1, NewR),
    H1 - P =\= 1,
    H2 - H1 =\= 1,!,
    R = [H1|NewR].
removeCons([H|[]], P, R):-
    removeCons([], H, NewR),
    H - P =\= 1,!,
    R = [H|NewR].
removeCons([H|T], _, R):-
    removeCons(T, H, NewR),
    R = NewR.

%removeFromBigList(L - list, R)
%flow model (i, o)

removeFromBigList([], []).
removeFromBigList([H|T], R):-
    is_list(H),!,
    removeCons(H, -2, PartR),
    removeFromBigList(T, NewR),
    R = [PartR|NewR].
removeFromBigList([H|T], R):-
    removeFromBigList(T, NewR),
    R = [H|NewR].

