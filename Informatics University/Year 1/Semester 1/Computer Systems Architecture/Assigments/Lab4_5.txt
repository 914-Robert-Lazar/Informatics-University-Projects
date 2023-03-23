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
    a db 10011011b
    b db 01001110b
    c dd 0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov eax, 0
        or eax, 0ffff0000h
        
        mov ebx, 0
        mov bl, [b]
        and bl, 01111000b
        ror bl, 3
        or eax, ebx
        
        and eax, 0ffffff0fh
        
        or eax, 00000000000000000000011000000000b
        
        mov ebx, 0
        mov bl, [a]
        and bl, 00011111b
        rol ebx, 11
        or eax, ebx
        
        mov [c], eax
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
