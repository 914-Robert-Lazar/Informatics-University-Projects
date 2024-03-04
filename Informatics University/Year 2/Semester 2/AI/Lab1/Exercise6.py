import numpy as np

numbers = [0, 1, 2, 3, 4]
distribution = [0.1, 0.2, 0.3, 0.05, 0.35]

wheel = ["blue", "red", "yellow", "green"]
degrees = [45, 85, 70, 150]

def spinner(probabilityDistribution):
    return np.random.choice(numbers, size=1, p=probabilityDistribution)[0]

def spinner2(degrees):
    degreeSum = sum(degrees)
    probabilities = {wheel[i] : degrees[i] / degreeSum for i in range(len(wheel))}
    color = np.random.choice(wheel, size=1, p=list(probabilities.values()))[0]
    return (color, probabilities[color]) 

if __name__ == "__main__":
    
    # i = spinner(distribution)
    # print(f"{i}, probability: {distribution[i]}")
    selected = spinner2(degrees)
    print(f"{selected[0]}, probability: {selected[1]}")

    

