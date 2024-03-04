import numpy as np
import math

def checkIfInside(coordinate: tuple, radius: float):
    return math.sqrt(coordinate[0] ** 2 + coordinate[1] ** 2) < radius
if __name__ == "__main__":
    n = int(input("Number of trials: "))

    number_of_successes  = 0
    for i in range(n):
        coordinate = (np.random.rand() - 0.5, np.random.rand() - 0.5)
        
        if checkIfInside(coordinate, 0.3):
            number_of_successes += 1

    print(f"Probability by Monte Carlo Simulation: {number_of_successes / n}")