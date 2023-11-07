$src = "lista.cpp"
$src = "$src main.cpp"

$src = $($src -split " ")

g++ -Wall -g -I .\includes @src -o .\main.exe
./main.exe

