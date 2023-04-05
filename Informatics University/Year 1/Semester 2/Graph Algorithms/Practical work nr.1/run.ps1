$src = "./src/main.cpp"
$src = "$src ./src/Edge.cpp"
$src = "$src ./src/Graph.cpp"
$src = "$src ./src/Ui.cpp"

$src = $($src -split " ")

g++ -Wall -g -I ./includes $src -o ./bin/main.exe
./bin/main.exe