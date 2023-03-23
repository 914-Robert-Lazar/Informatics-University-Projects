from f import f


def test_f():
    assert f([2, 1]) == True
    try:
        assert f(None) == True
    except ValueError as e:
        pass