f4(1, 1):-!.
f4(K, X):-
    K1 is K - 1,
    f4(K1, Y),
    aux4(Y, X, K1).

aux4(Y, X, K1):-
    Y > 1,!,
    K2 is K1 - 1,
    X is K2.
aux4(Y, X, _):-
    Y > 0.5,!,
    X is Y.
aux4(Y, X, _):-
    X is Y - 1.
