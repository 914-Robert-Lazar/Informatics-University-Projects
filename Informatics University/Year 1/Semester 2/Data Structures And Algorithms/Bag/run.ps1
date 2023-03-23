$src = ".\src\App.cpp"
$src = "$src .\src\Bag.cpp"
$src = "$src .\src\BagIterator.cpp"
$src = "$src .\src\ExtendedTest.cpp"
$src = "$src .\src\ShortTest.cpp"

$src = $($src -split " ")

g++ -g -Wall -I .\includes @src -o .\bin\main.exe
.\bin\main.exe