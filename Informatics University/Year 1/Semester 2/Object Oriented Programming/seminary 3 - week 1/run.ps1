$src = "main.cpp"
$src = "$src Song.cpp"
$src = "$src Tests.cpp"

$src = $($src -split " ")

g++ -Wall $src -o main.exe
.\main.exe