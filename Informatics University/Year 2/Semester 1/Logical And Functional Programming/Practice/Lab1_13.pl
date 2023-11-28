%isMember(L - list, E - integer, R - integer)
%flow model(i, i, o)

isMember([], _, R):-!,
    R is 0.
isMember([H|T], E, R):-
    H == E,!,
    R is 1;
    isMember(T, E, NewR),
    R is NewR.

%transform(L - list, R - list)
%flow model (i, o)

transform([], []).
transform([H|T], R):-
    transform(T, NewR),
    isMember(NewR, H, B),
    B == 0,!,
    R = [H|NewR].
transform([H|T], R):-
    transform(T, NewR),
    isMember(NewR, H, B),
    B == 1,
    R = NewR.
