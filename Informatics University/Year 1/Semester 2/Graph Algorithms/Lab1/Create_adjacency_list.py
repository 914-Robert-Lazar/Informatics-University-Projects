
def get_number_of_vertices(graph: dict):
    return len(graph)

if __name__ == "__main__":
    graph = {}

    vertices = ['A', 'B', 'C', 'D']
    for v in vertices:
        graph[v] = []

    edges = [('A', 'B'), ('B', 'C'), ('C', 'D'), ('D', 'A')]
    for e in edges:
        v1, v2 = e
        graph[v1].append(v2)
        graph[v2].append(v1)

    print(get_number_of_vertices(graph))
    print(graph)


