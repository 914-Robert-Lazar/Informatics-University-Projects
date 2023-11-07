%valley(L - list, C - string, R-integer)
%flow model(i, i, o)

valley([],_,R):-
    R is 1.
valley([_|_],"invalid", R):- !,
    R is 0.
valley([_|[]],_, R):-
    R is 1.
valley([H1, H2|T], "downhill", R):-
    H1>H2,
    valley([H2|T], "downhill", R);
    H1=:=H2,
    valley([H2|T], "downhill", R).
valley([H1, H2|T], "downhill", R):-
    H1<H2,
    valley([H2|T], "uphill", R).
valley([H1, H2|T], "uphill", R):-
    H1>H2,
    valley([H2|T], "invalid", R);
    valley([H2|T], "uphill", R).


%alternate(L - list, Flag - character, Sum - integer)
%flow model (i, i, o)

alternating([],_,0).
alternating([H|T], "+", Sum):-
    alternating(T, "-", NewSum),
    Sum is NewSum + H.
alternating([H|T], "-", Sum):-
    alternating(T, "+", NewSum),
    Sum is NewSum - H.

