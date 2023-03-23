bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    a dw 1000101110101001b
    b dw 1110111011000100b
    c dd 0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov eax, 0
        
        mov ebx, 0
        mov bx, [a]
        and bx, 0000000111111000b
        ror ebx, 3
        or eax, ebx
        
        mov ebx, 0
        mov bx, [b]
        and bx, 0000000000011100b
        rol ebx, 4
        or eax, ebx
        
        mov ebx, 0
        mov bx, [a]
        and bx, 0001111111000000b
        rol ebx, 3
        or eax, ebx
        
        mov [c], eax
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
