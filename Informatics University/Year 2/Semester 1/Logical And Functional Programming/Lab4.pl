%coloring(N - integer, M - integer, C - integer, P - integer, Col - list, R - list)
%flow model (i, i, i, i, i, o)
coloring(0, _, _, _, Col, R):-!,
    R = Col.
coloring(N, M, C, _, Col, R):-
    NewN is N - 1,
    NewCol = [C|Col],
    C \== 1,
    coloring(NewN, M, 1, C, NewCol, R).
coloring(N, M, C, _, Col, R):-
    NewN is N - 1,
    NewCol = [C|Col],
    C == 1,
    coloring(NewN, M, 2, C, NewCol, R).
coloring(N, M, C, P, Col, R):-
    C < M,
    NewC is C + 1,
    P \== NewC,
    coloring(N, M, NewC, P, Col, R).
coloring(N, M, C, P, Col, R):-
    C < M - 1,
    NewC is C + 1,
    P == NewC,
    NewC2 is NewC + 1,
    coloring(N, M, NewC2, P, Col, R).

%allColoring(N - integer, M - integer, C - integer, P - integer, Col - list, R - list)
%flow model (i, i, i, i, i, o, o)

allColoring(N, M, Result):-
    findall(R, coloring(N, M, 1, 0, [], R), Result).
