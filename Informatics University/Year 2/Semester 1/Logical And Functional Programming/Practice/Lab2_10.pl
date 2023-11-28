%prime(N - integer, D - integer, R - integer)
%flow model(i, i, o)

prime(N, D, R):-
    D =:= N // 2,
    R is 1.
prime(N, D, R):-
    N mod D =:= 0,!,
    R is 0.
prime(N, D, NewR):-
    NewD is D + 1,
    prime(N, NewD, NewR).

%duplicatePrime(L - list, R - list)
%flow model(i, o)

duplicatePrime([], []).
duplicatePrime([H|T], R):-
    prime(H, 2, IsPrime),
    IsPrime =:= 1,!,
    duplicatePrime(T, NewR),
    R = [H,H|NewR].
duplicatePrime([H|T], R):-
    duplicatePrime(T, NewR),
    R = [H|NewR].

