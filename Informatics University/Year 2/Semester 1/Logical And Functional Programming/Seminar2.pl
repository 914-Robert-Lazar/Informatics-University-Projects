%sum(L-list, s- number)
%flow model (i, o)
suma([],0).
suma([H|T], S):-
    sum(T, S1),
    S is S1 + H.

%sumC(L-list, C-collector, s-result)
%!  flow model (i,i,o)
sumC([], C, C).
sumC([H|T], C, R):-
    NewC is C+H,
    sumC(T, NewC, R).

