"""

@author: radu

 
"""

from board.board import Board
from strategy.no_strategy import NoStrategy
from player.computer import Computer
from player.human import Human
from game import Game


if __name__ == '__main__':
    # todo: validations

    board = Board(3, 3)
    strategy = NoStrategy(board)
    player1 = Computer("X", board, strategy)
    player2 = Human("0", board)
    game = Game(board, player1, player2)
    #
    game.play()

    print("bye")
