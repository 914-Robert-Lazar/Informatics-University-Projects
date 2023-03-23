class Graph:
    def __init__(self) -> None:
        self.vertices = {}
        self.edge = []

    def add_vertex(self, vertex):
        self.vertices[vertex] = []

    def add_edge(self, vertex1, vertex2):
        if (vertex1 not in self.vertices or vertex2 not in self.vertices):
            return False
        self.vertices[vertex1].append(vertex2);

    def remove_vertex(self, vertex)