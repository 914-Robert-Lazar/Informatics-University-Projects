%countDigits(L - list, R - integer)
%flow model(i, o)
countDigits([], 0).
countDigits([_|T], R):-
    countDigits(T, NewR),
    R is NewR + 1.

%computeSum(L1 -list, L2 - list, N1 - integer, N2 - integer, C -
% integer, AtFirst - integer, R - list) flow model(i, i, i, o, i, o)
computeSum([H1|[]], [H2|[]], _, _, C, 0, R):-!,
    H is H1 + H2,
    HD is H mod 10,
    C is H // 10,
    R = [HD].
computeSum([H1|[]], [H2|[]], _, _, C, 1, R):-
    H is H1 + H2,
    HD is H mod 10,
    C is H // 10,
    C > 0,
    R = [C,HD].
computeSum([H1|[]], [H2|[]], _, _, C, 1, R):-!,
    H is H1 + H2,
    HD is H mod 10,
    C is H // 10,
    C =:= 0,
    R = [HD].
computeSum([H|T], L2, N1, N2, C, 0, R):-
    N1 > N2,!,
    NewN1 is N1 - 1,
    computeSum(T, L2, NewN1, N2, NewC, 0, NewR),
    E is H + NewC,
    ED is E mod 10,
    C is E // 10,
    R = [ED|NewR].
computeSum([H|T], L2, N1, N2, C, 1, R):-
    N1 > N2,
    NewN1 is N1 - 1,
    computeSum(T, L2, NewN1, N2, NewC, 0, NewR),
    E is H + NewC,
    ED is E mod 10,
    C is E // 10,
    C > 0,
    R = [C,ED|NewR].
computeSum([H|T], L2, N1, N2, C, 1, R):-
    N1 > N2,!,
    NewN1 is N1 - 1,
    computeSum(T, L2, NewN1, N2, NewC, 0, NewR),
    E is H + NewC,
    ED is E mod 10,
    C is E // 10,
    C =:= 0,
    R = [ED|NewR].
computeSum(L1, [H|T], N1, N2, C, 0, R):-
    N1 < N2,!,
    NewN2 is N2 - 1,
    computeSum(L1, T, N1, NewN2, NewC, 0, NewR),
    E is H + NewC,
    ED is E mod 10,
    C is E // 10,
    R = [ED|NewR].
computeSum(L1, [H|T], N1, N2, C, 1, R):-
    N1 < N2,
    NewN2 is N2 - 1,
    computeSum(L1, T, N1, NewN2, NewC, 0, NewR),
    E is H + NewC,
    ED is E mod 10,
    C is E // 10,
    C > 0,
    R = [C,ED|NewR].
computeSum(L1, [H|T], N1, N2, C, 1, R):-
    N1 < N2,!,
    NewN2 is N2 - 1,
    computeSum(L1, T, N1, NewN2, NewC, 0, NewR),
    E is H + NewC,
    ED is E mod 10,
    C is E // 10,
    C =:= 0,
    R = [ED|NewR].
computeSum([H1|T1], [H2|T2], N1, N2, C, 0, R):-
    NewN1 is N1 - 1,
    NewN2 is N2 - 1,
    computeSum(T1, T2, NewN1, NewN2, NewC, 0, NewR),
    H is H1 + H2 + NewC,
    HD is H mod 10,
    C is H // 10,
    R = [HD|NewR].
computeSum([H1|T1], [H2|T2], N1, N2, C, 1, R):-
    NewN1 is N1 - 1,
    NewN2 is N2 - 1,
    computeSum(T1, T2, NewN1, NewN2, NewC, 0, NewR),
    H is H1 + H2 + NewC,
    HD is H mod 10,
    C is H // 10,
    C > 0,
    R = [C,HD|NewR].
computeSum([H1|T1], [H2|T2], N1, N2, C, 1, R):-
    NewN1 is N1 - 1,
    NewN2 is N2 - 1,
    computeSum(T1, T2, NewN1, NewN2, NewC, 0, NewR),
    H is H1 + H2 + NewC,
    HD is H mod 10,
    C is H // 10,
    C =:= 0,
    R = [HD|NewR].

%main(L1 - list, L2 - list, C - integer, R - integer)
%flow model(i, i, o, o)

main(L1, L2, C, R):-
    countDigits(L1, N1),
    countDigits(L2, N2),
    computeSum(L1, L2, N1, N2, C, 1, R).










